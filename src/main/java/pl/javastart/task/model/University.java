package pl.javastart.task.model;

import java.util.Arrays;

class University {
    private static final int INITIAL_LECTURERS = 10;
    private static final int INITIAL_GROUPS = 10;
    private Lecturer[] lecturers = new Lecturer[INITIAL_LECTURERS];
    private Group[] groups = new Group[INITIAL_GROUPS];
    private int lecturerNumber = 0;
    private int groupNumber = 0;

    int getLecturerNumber() {
        return lecturerNumber;
    }

    Lecturer fetchLecturerOfId(int id) {
        for (int i = 0; i < lecturerNumber; i++) {
            if (lecturers[i] == null) {
                break;
            }
            if (lecturers[i].getId() == id) {
                return lecturers[i];
            }
        }
        return null;
    }

//    boolean groupWithFollowingCodeExists(String code) {
//        for (int i = 0; i < groupNumber; i++) {
//            if (groups[i].getCode() == code) {
//                return true;
//            }
//        }
//        return false;
//    }

    Group fetchGroupOfCode(String code) {
        for (int i = 0; i < groupNumber; i++) {
            if (groups[i] == null) {
                break;
            }
            if (groups[i].getCode().equals(code)) {
                return groups[i];
            }
        }
        return null;
    }

    void addGroup(Group group) {
        if (groupNumber >= groups.length) {
            groups = Arrays.copyOf(groups, groups.length * 2);
        }

        groups[groupNumber] = group;
        groupNumber++;
    }

    int getGroupNumber() {
        return groupNumber;
    }

    void addLecturer(Lecturer lecturer) {
        if (lecturerNumber >= lecturers.length) {
            lecturers = Arrays.copyOf(lecturers, lecturers.length * 2);
        }

        lecturers[lecturerNumber] = lecturer;
        lecturerNumber++;
    }

    Lecturer[] getLecturers() {
        return lecturers;
    }

    Group[] getGroups() {
        return groups;
    }
}