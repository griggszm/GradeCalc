package app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Grade;
import model.GradeComparator;

import javax.swing.*;
import java.io.IOException;

/**
 * Main UI of the app.
 * Most of this is done in the design.fxml and just loaded here.
 * This sets up all the handlers and event responses.
 */
public class Main extends Application {

    private TextField midtermGrade;
    private TextField labGrade;
    private TextField quizGrade;
    private TextField hwGrade;
    private TextField miscGrade;
    private TextField finalGrade;

    private TextField midtermScale;
    private TextField labScale;
    private TextField quizScale;
    private TextField hwScale;
    private TextField miscScale;
    private TextField finalScale;

    private Button submitGrade;
    private Button save;
    private Button load;
    private Button removeGrade;
    private Button calcGrade;
    private Button clearGrades;

    private TextField courseGrade;
    private TableView grades;

    private final ObservableList<Grade> data = FXCollections.observableArrayList();

    /**
     * Starts the main app, loading from the design and showing controls on screen.
     *
     * @param primaryStage  Stage to write on
     * @throws Exception    Unexpected error
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../resources/design.fxml"));
        primaryStage.setTitle("Hello World");
        Controller controller = new Controller();
        Scene scene = new Scene(root, 850, 775);
        primaryStage.setScene(scene);
        primaryStage.show();
        setControls(scene);
        addHandlers(controller);
        controller.readSettings();
    }

    /**
     * Gets the text from a textbox.
     *
     * @param textField Textbox to get from
     * @return          The grade/text from the box.
     */
    private String getText(TextField textField) {
        return textField.getText();
    }

    /**
     * Calculates the user's grade using all the other grades/scalings.
     *
     * @param controller    Controller who will compute these.
     */
    private void calcGrade(Controller controller) {
        courseGrade.setText(controller.calculateCourseGrade(data, getText(midtermScale), getText(labScale),
                getText(quizScale), getText(hwScale), getText(miscScale), getText(finalScale)));
    }


    private void clear(Controller controller) {
        controller.clear(data);
    }
    /**
     * Adds event handlers for buttons and textboxes.
     *
     * @param controller    Controller to respond to event.
     */
    private void addHandlers(Controller controller) {
        submitGrade.setOnAction(e -> addGrade(controller));
        calcGrade.setOnAction(e -> calcGrade(controller));
        removeGrade.setOnAction(e -> removeGrade(controller));
        save.setOnAction(e -> save(controller));
        load.setOnAction(e -> load(controller));
        clearGrades.setOnAction(e -> clear(controller));

        midtermScale.setOnKeyReleased(e -> controller.ensureNonEmpty(midtermScale));
        labScale.setOnKeyReleased(e -> controller.ensureNonEmpty(labScale));
        quizScale.setOnKeyReleased(e -> controller.ensureNonEmpty(quizScale));
        hwScale.setOnKeyReleased(e -> controller.ensureNonEmpty(hwScale));
        miscScale.setOnKeyReleased(e -> controller.ensureNonEmpty(miscScale));
        finalScale.setOnKeyReleased(e -> controller.ensureNonEmpty(finalScale));

        midtermGrade.setOnAction(e -> addGrade(controller));
        labGrade.setOnAction(e -> addGrade(controller));
        quizGrade.setOnAction(e -> addGrade(controller));
        hwGrade.setOnAction(e -> addGrade(controller));
        miscGrade.setOnAction(e -> addGrade(controller));
        finalGrade.setOnAction(e -> addGrade(controller));
    }

    /**
     * Saves the current grades/scalings to a text file.
     *
     * @param controller    Controller to handle the saving.
     */
    private void save(Controller controller) {
        controller.save(data, getText(midtermScale), getText(labScale), getText(quizScale),
                getText(hwScale), getText(miscScale), getText(finalScale));
    }

    /**
     * Loads the current grades/scalings from a text file.
     *
     * @param controller    Controller to handle the loading.
     */
    private void load(Controller controller) {
        try {
            controller.load(data, midtermScale, labScale, quizScale, hwScale, miscScale, finalScale);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "File not found");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Cannot read input file.");
        }
    }

    /**
     * Removes selected grade from the datatable.
     *
     * @param controller    Controller to handle the removal
     */
    private void removeGrade(Controller controller) {
        try {
            controller.removeGrade(data,grades.getSelectionModel().getFocusedIndex());
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "No grade to remove.");
        }
    }

    /**
     * Adds a grade to this datatable. This is a helper method to inline
     * the work of checking for validity and adding if valid.
     *
     * @param controller    Controller to handle the adding
     * @param gradeName     Type of the grade
     * @param textField     Box containing the grade
     */
    private void addGrade(Controller controller, String gradeName, TextField textField) {
        String grade = getText(textField);
        if(!grade.equals("")) {
            controller.addGrade(gradeName,grade,data);
            textField.clear();
        }
    }

    /**
     * Adds all inputed grades.
     * All grade fields that are filled in will be added.
     *
     * @param controller    Controller to handle the adding.
     */
    private void addGrade(Controller controller) {
        addGrade(controller,"Midterm",midtermGrade);
        addGrade(controller,"Final Exam",finalGrade);
        addGrade(controller,"Lab",labGrade);
        addGrade(controller,"Quiz",quizGrade);
        addGrade(controller,"Homework",hwGrade);
        addGrade(controller,"Misc",miscGrade);
        data.sort(new GradeComparator());
    }

    /**
     * Sets up the controls.
     * Reads the actual object from the scene using the IDs.
     *
     * @param scene Scene to get objects from
     */
    private void setControls(Scene scene)  {
        midtermGrade = (TextField)getControlById(scene,"midtermGrade");
        quizGrade = (TextField)getControlById(scene,"quizGrade");
        labGrade = (TextField)getControlById(scene,"labGrade");
        hwGrade = (TextField)getControlById(scene,"hwGrade");
        miscGrade = (TextField)getControlById(scene,"miscGrade");
        finalGrade = (TextField)getControlById(scene,"finalGrade");

        midtermScale = (TextField)getControlById(scene,"midtermScale");
        quizScale = (TextField)getControlById(scene,"quizScale");
        labScale = (TextField)getControlById(scene,"labScale");
        hwScale = (TextField)getControlById(scene,"hwScale");
        miscScale = (TextField)getControlById(scene,"miscScale");
        finalScale = (TextField)getControlById(scene,"finalScale");

        submitGrade = (Button)getControlById(scene,"submitGrade");
        save = (Button)getControlById(scene,"save");
        load = (Button)getControlById(scene,"load");
        removeGrade = (Button)getControlById(scene,"removeGrade");
        calcGrade = (Button)getControlById(scene,"calcGrade");
        clearGrades = (Button)getControlById(scene,"clearGrades");

        courseGrade = (TextField)getControlById(scene,"courseGrade");
        grades = (TableView)getControlById(scene,"grades");

        TableColumn firstNameCol = new TableColumn("Grade Type");
        firstNameCol.setMinWidth(320);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Grade, String>("type"));

        TableColumn lastNameCol = new TableColumn("Grade Received");
        lastNameCol.setMinWidth(320);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Grade, String>("grade"));

        grades.setItems(data);
        grades.getColumns().addAll(firstNameCol, lastNameCol);
    }

    /**
     * Hepler method to lookup an object
     *
     * @param scene Scene to lookup from
     * @param id    ID to lookup
     * @return      Object from scene
     */
    private Node getControlById(Scene scene, String id) {
        return scene.lookup("#" + id);
    }

    /**
     * Launches app.
     *
     * @param args  Ignored.
     */
    public static void main(String[] args) {
        launch();
    }
}
