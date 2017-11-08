package app;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import model.Grade;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Controller {

    public void addGrade(String gradeType, String grade, ObservableList<Grade> data) {
        data.add(new Grade(gradeType, grade));
    }

    public String calculateFinalGrade(ObservableList<Grade> data, String midtermScale, String labScale,
                                      String quizScale, String hwScale, String miscScale, String finalScale) {
        double midtermGrade = getGradeFor(data, midtermScale, "midterm");
        double labGrade = getGradeFor(data, labScale, "lab");
        double quizGrade = getGradeFor(data, quizScale, "quiz");
        double hwGrade = getGradeFor(data, hwScale, "homework");
        double miscGrade = getGradeFor(data, miscScale, "misc");
        double finalGrade = getGradeFor(data, finalScale, "final exam");

        double maxGradePossible = Double.parseDouble(midtermScale) + Double.parseDouble(labScale) + Double.parseDouble(quizScale)
                + Double.parseDouble(hwScale) + Double.parseDouble(miscScale) + Double.parseDouble(finalScale);
        double receivedGrade = ((midtermGrade + labGrade + quizGrade + hwGrade + miscGrade + finalGrade) / maxGradePossible);
        return "" + receivedGrade;
    }

    private double getGradeFor(ObservableList<Grade> data, String scale, String key) {
        if (!scale.equals("0")) {
            return Double.parseDouble(scale) * average(data, key);
        }
        return 0;
    }

    private int average(ObservableList<Grade> data, String key) {
        int total = 0;
        int count = 0;
        for (Grade grade : data) {
            if (grade.getType().toLowerCase().equals(key.toLowerCase())) {
                total += Integer.parseInt(grade.getGrade());
                count++;
            }
        }
        return total / count;
    }

    public void removeGrade(ObservableList<Grade> data, int which) {
        data.remove(which);
    }

    public void save(ObservableList<Grade> data, String midtermScale, String labScale,
                     String quizScale, String hwScale, String miscScale, String finalScale) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showDialog(null, "Submit");
        File writeTo = fileChooser.getSelectedFile();
        if (writeTo.exists()) {
            writeTo.delete();
        }
        try {
            FileWriter fw = new FileWriter(writeTo);
            StringBuilder text = new StringBuilder();
            text.append(midtermScale).append(",").append(labScale).append(",").append(quizScale).append(",")
                    .append(hwScale).append(",").append(miscScale).append(",").append(finalScale).append(",")
                    .append("\r\n");
            for (Grade g : data) {
                text.append(g.toString()).append("\r\n");
            }
            fw.write(text.toString());
            fw.flush();
        } catch (IOException ex) {
        }
    }

    public void load(ObservableList<Grade> data, TextField midtermScale, TextField labScale,
                     TextField quizScale, TextField hwScale, TextField miscScale, TextField finalScaleg) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showDialog(null, "Submit");
        File writeTo = fileChooser.getSelectedFile();
        if(writeTo.exists()) {
            try {
                Scanner in = new Scanner(writeTo);
                String scalings = in.nextLine();
                String[] scalingParts = scalings.split(",");
                midtermScale.setText(scalingParts[0]);
                labScale.setText(scalingParts[1]);
                quizScale.setText(scalingParts[2]);
                hwScale.setText(scalingParts[3]);
                miscScale.setText(scalingParts[4]);
                finalScaleg.setText(scalingParts[5]);
                while(in.hasNextLine()) {
                    Grade g = Grade.fromString(in.nextLine());
                    data.add(g);
                }
            } catch (IOException ex) {
            }
        }
    }
}
