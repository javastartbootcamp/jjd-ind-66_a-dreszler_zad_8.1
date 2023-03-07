package pl.javastart.task.model;

public class UniversityApp {

    private static final int NOT_FOUND = -1;

    /**
     * Tworzy prowadzącego zajęcia.
     * W przypadku gdy prowadzący z zadanym id już istnieje, wyświetlany jest komunikat:
     * "Prowadzący z id [id_prowadzacego] już istnieje"
     *
     * @param id        - unikalny identyfikator prowadzącego
     * @param degree    - stopień naukowy prowadzącego
     * @param firstName - imię prowadzącego
     * @param lastName  - nazwisko prowadzącego
     */
    public void createLecturer(int id, String degree, String firstName, String lastName) {
        Lecturer[] lecturers = University.lecturers;
        int lecturerNumber = University.getLecturerNumber();

        if (!University.lecturerWithFollowingIdExists(id)) {
            lecturers[lecturerNumber] = new Lecturer(firstName, lastName, degree, id);
        } else {
            lecturerAlreadyExistsPrompt(id);
        }
    }

    private void lecturerAlreadyExistsPrompt(int id) {
        System.out.println("Prowadzący z id " + id + " już istnieje");
    }

    /**
     * Tworzy grupę zajęciową.
     * W przypadku gdy grupa z zadanym kodem już istnieje, wyświetla się komunikat:
     * "Grupa [kod grupy] już istnieje"
     * W przypadku gdy prowadzący ze wskazanym id nie istnieje wyświetla się komunikat:
     * "Prowadzący o id [id prowadzacego] nie istnieje"
     *
     * @param code       - unikalny kod grupy
     * @param name       - nazwa przedmiotu (np. "Podstawy programowania")
     * @param lecturerId - identyfikator prowadzącego. Musi zostać wcześniej utworzony za pomocą metody {@link #createLecturer(int, String, String, String)}
     */
    public void createGroup(String code, String name, int lecturerId) {
        Group[] groups = University.groups;
        int groupNumber = University.getGroupNumber();
        Lecturer[] lecturers = University.lecturers;

        if (!University.groupWithFollowingCodeExists(code)) {
            checkIfLecturerExistsAndAssignHim(groups, groupNumber, lecturers, lecturerId, code, name);
        } else {
            groupAlreadyExistsPrompt(code);
        }
    }

    private void groupAlreadyExistsPrompt(String code) {
        System.out.println("Grupa " + code + " już istnieje");
    }

    private void checkIfLecturerExistsAndAssignHim(Group[] groups, int groupNumber, Lecturer[] lecturers, int lecturerId,
                                                   String code, String name) {
        if (University.lecturerWithFollowingIdExists(lecturerId)) {
            int lecturerIndex = University.fetchLecturerIndexOfId(lecturerId);
            groups[groupNumber] = new Group(name, code, lecturers[lecturerIndex]);
        } else {
            lecturerDoesntExistPrompt(lecturerId);
        }
    }

    private void lecturerDoesntExistPrompt(int lecturerId) {
        System.out.println("Prowadzący o id " + lecturerId + " nie istnieje");
    }

    /**
     * Dodaje studenta do grupy zajęciowej.
     * W przypadku gdy grupa zajęciowa nie istnieje wyświetlany jest komunikat:
     * "Grupa [kod grupy] nie istnieje
     *
     * @param index     - unikalny numer indeksu studenta
     * @param groupCode - kod grupy utworzonej wcześniej za pomocą {@link #createGroup(String, String, int)}
     * @param firstName - imię studenta
     * @param lastName  - nazwisko studenta
     */
    public void addStudentToGroup(int index, String groupCode, String firstName, String lastName) {
        Group[] groups = University.groups;
        int groupIndex = University.fetchGroupIndexOfCode(groupCode);
        if (University.groupWithFollowingCodeExists(groupCode)) {
            checkIfStudentAlreadyAssignedAndAdd(groups, groupIndex, index, firstName, lastName, groupCode);
        } else {
            groupDoesntExistPrompt(groupCode);
        }
    }

    private void checkIfStudentAlreadyAssignedAndAdd(Group[] groups, int groupIndex, int studentIndex, String firstName,
                                                     String lastName, String groupCode) {
        if (!groups[groupIndex].studentWithFollowingIndexExists(studentIndex)) {
            Student[] students = groups[groupIndex].getStudents();
            int studentNumber = groups[groupIndex].getStudentNumber();
            students[studentNumber] = new Student(firstName, lastName, studentIndex);
            groups[groupIndex].addStudent();
        } else {
            studentAlreadyInGroupPrompt(studentIndex, groupCode);
        }
    }

    private void studentAlreadyInGroupPrompt(int studentIndex, String groupCode) {
        System.out.println("Student o indeksie " + studentIndex + " jest już w grupie " + groupCode);
    }

    /**
     * Wyświetla informacje o grupie w zadanym formacie.
     * Oczekiwany format:
     * Kod: [kod_grupy]
     * Nazwa: [nazwa przedmiotu]
     * Prowadzący: [stopień naukowy] [imię] [nazwisko]
     * Uczestnicy:
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * W przypadku gdy grupa nie istnieje, wyświetlany jest komunikat w postaci: "Grupa [kod] nie znaleziona"
     *
     * @param groupCode - kod grupy, dla której wyświetlić informacje
     */
    public void printGroupInfo(String groupCode) {
        if (University.groupWithFollowingCodeExists(groupCode)) {
            int groupIndex = University.fetchGroupIndexOfCode(groupCode);
            System.out.println(University.groups[groupIndex].getInfo());
        } else {
            groupDoesntExistPrompt(groupCode);
        }
    }

    /**
     * Dodaje ocenę końcową dla wskazanego studenta i grupy.
     * Student musi być wcześniej zapisany do grupy za pomocą {@link #addStudentToGroup(int, String, String, String)}
     * W przypadku, gdy grupa o wskazanym kodzie nie istnieje, wyświetlany jest komunikat postaci:
     * "Grupa pp-2022 nie istnieje"
     * W przypadku gdy student nie jest zapisany do grupy, wyświetlany jest komunikat w
     * postaci: "Student o indeksie 179128 nie jest zapisany do grupy pp-2022"
     * W przypadku gdy ocena końcowa już istnieje, wyświetlany jest komunikat w postaci:
     * "Student o indeksie 179128 ma już wystawioną ocenę dla grupy pp-2022"
     *
     * @param studentIndex - numer indeksu studenta
     * @param groupCode    - kod grupy
     * @param grade        - ocena
     */
    public void addGrade(int studentIndex, String groupCode, double grade) {
        if (University.groupWithFollowingCodeExists(groupCode)) {
            int groupIndex = University.fetchGroupIndexOfCode(groupCode);
            Group group = University.groups[groupIndex];
            checkIfGradeAlreadyAssignedAndAdd(group, studentIndex, grade, groupCode);
        } else {
            groupDoesntExistPrompt(groupCode);
        }
    }

    private void studentNotAssignedToGroupPrompt(int studentIndex, String groupCode) {
        System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
    }

    private void checkIfGradeAlreadyAssignedAndAdd(Group group, int studentIndex, double grade, String groupCode) {
        if (group.studentWithFollowingIndexExists(studentIndex)) {
            int studentIndexInArray = group.fetchStudentIndexOfIndex(studentIndex);
            Student student = group.getStudents()[studentIndexInArray];
            if (!student.gradeForGroupExists(group)) {
                student.setGrade(new Grade(grade, group));
            } else {
                gradeAlreadyAssignedToStudentForGroup(studentIndex, groupCode);
            }
        } else {
            studentNotAssignedToGroupPrompt(studentIndex, groupCode);
        }
    }

    private void gradeAlreadyAssignedToStudentForGroup(int studentIndex, String groupCode) {
        System.out.println("Student o indeksie " + studentIndex + " ma już wystawioną ocenę dla grupy " +
                groupCode);
    }

    private void groupDoesntExistPrompt(String groupCode) {
        System.out.println("Grupa " + groupCode + " nie istnieje");
    }

    /**
     * Wyświetla wszystkie oceny studenta.
     * Przykładowy wydruk:
     * Podstawy programowania: 5.0
     * Programowanie obiektowe: 5.5
     *
     * @param index - numer indesku studenta dla którego wyświetlić oceny
     */
    public void printGradesForStudent(int index) {
        Group[] groups = University.groups;

        for (int i = 0; i < University.getGroupNumber(); i++) {
            int studentArrayIndex = groups[i].fetchStudentIndexOfIndex(index);
            if (studentArrayIndex != NOT_FOUND) {
                Student student = groups[i].getStudents()[studentArrayIndex];
                Grade grade = student.getGrade();
                System.out.println(grade.getGroup().getName() + ": " + grade.getGrade());
            }
        }
    }

    /**
     * Wyświetla oceny studentów dla wskazanej grupy.
     * Przykładowy wydruk:
     * 179128 Marcin Abacki: 5.0
     * 179234 Dawid Donald: 4.5
     * 189521 Anna Kowalska: 5.5
     *
     * @param groupCode - kod grupy, dla której wyświetlić oceny
     */
    public void printGradesForGroup(String groupCode) {
        int groupIndex = University.fetchGroupIndexOfCode(groupCode);
        if (groupIndex != NOT_FOUND) {
            printGrades(groupIndex);
        } else {
            groupDoesntExistPrompt(groupCode);
        }
    }

    private void printGrades(int groupIndex) {
        Group group = University.groups[groupIndex];
        Student[] students = group.getStudents();
        for (int i = 0; i < group.getStudentNumber(); i++) {
            System.out.println(students[i].getIndex() + " " + students[i].getFirstName() + " " +
                    students[i].getLastName() + ": " + students[i].getGrade().getGrade());
        }
    }

    /**
     * Wyświetla wszystkich studentów. Każdy student powinien zostać wyświetlony tylko raz.
     * Każdy student drukowany jest w nowej linii w formacie [nr_indesku] [imie] [nazwisko]
     * Przykładowy wydruk:
     * 179128 Marcin Abacki
     * 179234 Dawid Donald
     * 189521 Anna Kowalska
     */
    public void printAllStudents() {
        int studentsInAllGroups = sumOfStudentsInGroups();
        Student[] allStudentsArray = new Student[studentsInAllGroups];

        fillArrayWithStudents(allStudentsArray);
        allStudentsArray = trimArray(allStudentsArray);

        printStudentArray(allStudentsArray);
    }

    private void printStudentArray(Student[] allStudentsArray) {
        for (Student student : allStudentsArray) {
            System.out.println(student.getIndex() + " " + student.getFirstName() + " " +
                    student.getLastName());
        }
    }

    private void fillArrayWithStudents(Student[] allStudentsArray) {
        for (int i = 0; i < University.getGroupNumber(); i++) {
            Group[] groups = University.groups;
            Student[] students = groups[i].getStudents();
            for (int j = 0; j < groups[i].getStudentNumber(); j++) {
                Student student = students[j];
                if (!contains(allStudentsArray, student)) {
                    allStudentsArray[j] = student;
                }
            }
        }
    }

    private int sumOfStudentsInGroups() {
        Group[] groups = University.groups;
        int sum = 0;
        for (int i = 0; i < University.getGroupNumber(); i++) {
            sum += groups[i].getStudentNumber();
        }
        return sum;
    }

    private boolean contains(Student[] students, Student student) {
        for (Student st : students) {
            if (st == null) {
                break;
            }
            if (st.getIndex() == student.getIndex()) {
                return true;
            }
        }
        return false;
    }

    private Student[] trimArray(Student[] studentArray) {
        int lengthAfterTrim = 0;
        for (Student student : studentArray) {
            if (student != null) {
                lengthAfterTrim++;
            }
        }
        Student[] trimmedArray = new Student[lengthAfterTrim];
        for (int i = 0; i < trimmedArray.length; i++) {
            trimmedArray[i] = studentArray[i];
        }

        return trimmedArray;
    }
}
