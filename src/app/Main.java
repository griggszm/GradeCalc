package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private TextField courseGrade;
    private TableView<String> grades;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("design.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 850, 775);
        primaryStage.setScene(scene);
        primaryStage.show();
        setControls(scene);
    }

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

        courseGrade = (TextField)getControlById(scene,"courseGrade");
        grades = (TableView<String>)getControlById(scene,"grades");


    }

    private Node getControlById(Scene scene, String id) {
        return scene.lookup("#" + id);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
