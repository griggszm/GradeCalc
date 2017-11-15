package model;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class is needed to populate the ListView.
 */
public class Grade {
    private final SimpleStringProperty type;
    private final SimpleStringProperty grade;

    public Grade(String type, String grade) {
        this.type = new SimpleStringProperty(type);
        this.grade = new SimpleStringProperty(grade);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    @Override
    public String toString() {
        return "Grade{" +
                "type=" + type +
                ", grade=" + grade +
                '}';
    }

    public String getGrade() {
        return grade.get();
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    public static Grade fromString(String grade) {
        if(!grade.startsWith("Grade")) {
            throw new IllegalArgumentException("Not a grade object.");
        }
        String type = "";
        String value = "";

        String parts[] = grade.split("value: ");
        type = parts[1].substring(0,parts[1].indexOf("]"));
        value = parts[2].substring(0,parts[2].indexOf("]"));
        return new Grade(type,value);
    }
}
