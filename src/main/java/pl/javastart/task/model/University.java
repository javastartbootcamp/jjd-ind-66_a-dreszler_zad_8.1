package pl.javastart.task.model;

class University {
    private static final int MAX_LECTURERS = 1000;
    private static final int MAX_GROUPS = 1000;
    private static final int NOT_FOUND = -1;
    static Lecturer[] lecturers = new Lecturer[MAX_LECTURERS];
    static Group[] groups = new Group[MAX_GROUPS];
    private static int lecturerNumber = 0;
    private static int groupNumber = 0;

    static int getLecturerNumber() {
        return lecturerNumber;
    }

    static boolean lecturerWithFollowingIdExists(int id) {
        for (int i = 0; i < lecturerNumber; i++) {
            if (lecturers[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    static int fetchLecturerIndexOfId(int id) {
        for (int i = 0; i < lecturerNumber; i++) {
            if (lecturers[i].getId() == id) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    static boolean groupWithFollowingCodeExists(String code) {
        for (int i = 0; i < groupNumber; i++) {
            if (groups[i].getCode() == code) {
                return true;
            }
        }
        return false;
    }

    static int fetchGroupIndexOfCode(String code) {
        for (int i = 0; i < groupNumber; i++) {
            if (groups[i].getCode() == code) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    static int getGroupNumber() {
        return groupNumber;
    }

    static void addLecturer() {
        lecturerNumber++;
    }

    static void addGroup() {
        groupNumber++;
    }
}