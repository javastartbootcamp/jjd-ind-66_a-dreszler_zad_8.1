package pl.javastart.task.model;

public class UniversityApp {
    University university = new University();
    private static final int NOT_ASSIGNED = 0;

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
        if (university.fetchLecturerOfId(id) == null) {
            Lecturer lecturer = new Lecturer(firstName, lastName, degree, id);
            university.addLecturer(lecturer);
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
        if (university.fetchGroupOfCode(code) == null) {
            checkIfLecturerExistsAndAddGroup(lecturerId, code, name);
        } else {
            groupAlreadyExistsPrompt(code);
        }
    }

    private void groupAlreadyExistsPrompt(String code) {
        System.out.println("Grupa " + code + " już istnieje");
    }

    private void checkIfLecturerExistsAndAddGroup(int lecturerId, String code, String name) {
        Lecturer lecturer = university.fetchLecturerOfId(lecturerId);
        if (lecturer != null) {
            Group group = new Group(name, code, lecturer);
            university.addGroup(group);
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
        Group group = university.fetchGroupOfCode(groupCode);
        if (group != null) {
            Student student = new Student(firstName, lastName, index);
            checkIfStudentAlreadyAssignedAndAdd(group, student);
        } else {
            groupDoesntExistPrompt(groupCode);
        }
    }

    private void checkIfStudentAlreadyAssignedAndAdd(Group group, Student student) {
        if (group.fetchStudentOfIndex(student.getIndex()) == null) {
            group.addStudent(student);
        } else {
            studentAlreadyInGroupPrompt(student, group);
        }
    }

    private void studentAlreadyInGroupPrompt(Student student, Group group) {
        System.out.println("Student o indeksie " + student.getIndex() + " jest już w grupie " + group.getCode());
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
        Group group = university.fetchGroupOfCode(groupCode);
        if (group != null) {
            System.out.println(group.getInfo());
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
        Group group = university.fetchGroupOfCode(groupCode);
        if (group != null) {
            checkIfGradeAlreadyAssignedAndAdd(group, studentIndex, grade);
        } else {
            groupDoesntExistPrompt(groupCode);
        }
    }

    private void studentNotAssignedToGroupPrompt(int studentIndex, String groupCode) {
        System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
    }

    private void checkIfGradeAlreadyAssignedAndAdd(Group group, int studentIndex, double grade) {
        Student student = group.fetchStudentOfIndex(studentIndex);
        if (student != null) {
            if (student.getGrade() == NOT_ASSIGNED) {
                student.setGrade(grade);
            } else {
                gradeAlreadyAssignedToStudentForGroup(student, group);
            }
        } else {
            studentNotAssignedToGroupPrompt(studentIndex, group.getCode());
        }
    }

    private void gradeAlreadyAssignedToStudentForGroup(Student student, Group group) {
        System.out.println("Student o indeksie " + student.getIndex() + " ma już wystawioną ocenę dla grupy " +
                group.getCode());
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
        for (int i = 0; i < university.getGroupNumber(); i++) {
            Group group = university.getGroups()[i];
            Student student = group.fetchStudentOfIndex(index);
            if (student != null) {
                System.out.println(group.getName() + ": " + student.getGrade());
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
        Group group = university.fetchGroupOfCode(groupCode);
        if (group != null) {
            printGrades(group);
        } else {
            groupDoesntExistPrompt(groupCode);
        }
    }

    private void printGrades(Group group) {
        Student[] students = group.getStudents();
        for (int i = 0; i < group.getStudentNumber(); i++) {
            System.out.println(students[i].getIndex() + " " + students[i].getFirstName() + " " +
                    students[i].getLastName() + ": " + students[i].getGrade());
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
            student.printInfo();
        }
    }

    private void fillArrayWithStudents(Student[] allStudentsArray) {
        for (int i = 0; i < university.getGroupNumber(); i++) {
            Group[] groups = university.getGroups();
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
        Group[] groups = university.getGroups();
        int sum = 0;
        for (int i = 0; i < university.getGroupNumber(); i++) {
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
