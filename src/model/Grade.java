package model;

import javafx.beans.property.SimpleStringProperty;

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

    public String getGrade() {
        return grade.get();
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }
}
