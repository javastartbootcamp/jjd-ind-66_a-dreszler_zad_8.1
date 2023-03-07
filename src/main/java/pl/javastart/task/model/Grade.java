package pl.javastart.task.model;

class Grade {
    private double grade;
    private Group group;

    public Grade(double grade, Group group) {
        this.grade = grade;
        this.group = group;
    }

    public double getGrade() {
        return grade;
    }

    public Group getGroup() {
        return group;
    }

}
