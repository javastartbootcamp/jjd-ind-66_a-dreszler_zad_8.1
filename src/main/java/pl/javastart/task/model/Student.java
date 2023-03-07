package pl.javastart.task.model;

class Student extends Person {
    private int index;
    private Grade grade;

    public Student(String firstName, String lastName, int index) {
        super(firstName, lastName);
        this.index = index;
    }

    public boolean gradeForGroupExists(Group group) {
        if (grade == null) {
            return false;
        }
        if (grade.getGroup().getCode() == group.getCode()) {
            return true;
        }
        return false;
    }

    public int getIndex() {
        return index;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
