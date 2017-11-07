package app;

import javafx.collections.ObservableList;
import model.Grade;

public class Controller {

    public void addGrade(String gradeType, String grade, ObservableList<Grade> data ) {
        data.add(new Grade(gradeType,grade));
    }
}
