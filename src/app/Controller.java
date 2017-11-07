package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class Controller {

    public void addGrade(String gradeType, String grade, ObservableList<Grade> data ) {
        data.add(new Grade(gradeType,grade));
    }
}
