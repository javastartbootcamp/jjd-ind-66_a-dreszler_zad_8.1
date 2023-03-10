package pl.javastart.task.model;

class Lecturer extends Person {
    private int id;
    private String degree;

    Lecturer(String firstName, String lastName, String degree, int id) {
        super(firstName, lastName);
        this.id = id;
        this.degree = degree;
    }

    int getId() {
        return id;
    }

    String getDegree() {
        return degree;
    }

    String getInfo() {
        return degree + " " + getFirstName() + " " + getLastName();
    }
}