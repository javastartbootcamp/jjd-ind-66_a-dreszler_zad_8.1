package pl.javastart.task.model;

class Student extends Person {
    private int index;
    private double grade;

    Student(String firstName, String lastName, int index) {
        super(firstName, lastName);
        this.index = index;
    }

    int getIndex() {
        return index;
    }

    double getGrade() {
        return grade;
    }

    void setGrade(double grade) {
        this.grade = grade;
    }
}
