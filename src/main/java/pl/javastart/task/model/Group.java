package pl.javastart.task.model;

class Group {
    private static final int MAX_STUDENTS_IN_GROUP = 100;
    private static final int NOT_FOUND = -1;
    private String name;
    private String code;
    private Lecturer lecturer;
    private Student[] students = new Student[MAX_STUDENTS_IN_GROUP];
    private int studentNumber = 0;

    Group(String name, String code, Lecturer lecturer) {
        this.name = name;
        this.code = code;
        this.lecturer = lecturer;
        University.addGroup();
    }

    void addStudent() {
        studentNumber++;
    }

    boolean studentWithFollowingIndexExists(int index) {
        for (int i = 0; i < studentNumber; i++) {
            if (students[i].getIndex() == index) {
                return true;
            }
        }
        return false;
    }

    int fetchStudentIndexOfIndex(int index) {
        for (int i = 0; i < studentNumber; i++) {
            if (students[i].getIndex() == index) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    String getInfo() {
        String studentsInGroup = "";
        for (int i = 0; i < studentNumber; i++) {
            studentsInGroup += students[i].getIndex() + " " + students[i].getFirstName() + " " +
                    students[i].getLastName() + "\n";
        }
        return "Kod: " + code + "\nNazwa: " + name + "\nProwadzący: " + lecturer.getDegree() + " " +
                lecturer.getFirstName() + " " + lecturer.getLastName() + "\nUczestnicy:\n" + studentsInGroup;
    }

    int getStudentNumber() {
        return studentNumber;
    }

    String getName() {
        return name;
    }

    String getCode() {
        return code;
    }

    Student[] getStudents() {
        return students;
    }
}
