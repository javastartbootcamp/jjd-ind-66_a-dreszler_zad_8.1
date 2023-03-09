package pl.javastart.task.model;

import java.util.Arrays;

class Group {
    private static final int INITIAL_STUDENTS_IN_GROUP = 10;
    private String name;
    private String code;
    private Lecturer lecturer;
    private Student[] students = new Student[INITIAL_STUDENTS_IN_GROUP];
    private int studentNumber = 0;

    Group(String name, String code, Lecturer lecturer) {
        this.name = name;
        this.code = code;
        this.lecturer = lecturer;
    }

    void addStudent(Student student) {
        if (studentNumber >= students.length) {
            Arrays.copyOf(students, students.length * 2);
        }

        students[studentNumber] = student;
        studentNumber++;
    }

//    boolean studentWithFollowingIndexExists(int index) {
//        for (int i = 0; i < studentNumber; i++) {
//            if (students[i].getIndex() == index) {
//                return true;
//            }
//        }
//        return false;
//    }

    Student fetchStudentOfIndex(int index) {
        for (int i = 0; i < studentNumber; i++) {
            if (students[i] == null) {
                break;
            }
            if (students[i].getIndex() == index) {
                return students[i];
            }
        }
        return null;
    }

    String getInfo() {
        String studentsInGroup = "";
        for (int i = 0; i < studentNumber; i++) {
            if (students[i] == null) {
                break;
            }
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
