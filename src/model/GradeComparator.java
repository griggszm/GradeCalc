package model;

import java.util.Comparator;

public class GradeComparator implements Comparator<Grade> {

    @Override
    public int compare(Grade o1, Grade o2) {
        return o1.getType().compareTo(o2.getType());
    }
}
