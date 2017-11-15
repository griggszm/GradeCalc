package model;

import java.util.Comparator;

/**
 * Compares two grades.
 * Currently uses String's comparator. This should be reworked sometime.
 */
public class GradeComparator implements Comparator<Grade> {

    @Override
    public int compare(Grade o1, Grade o2) {
        return o1.getType().compareTo(o2.getType());
    }
}
