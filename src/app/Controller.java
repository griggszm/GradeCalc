package app;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import model.Grade;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Controller class to handle the internal logic of the main class.
 */
public class Controller {

    private File directory = new File("/");

    /**
     * Adds a grade to the data list.
     *
     * @param gradeType Type of grade (for example, "Lab")
     * @param grade     Grade received (for example, "90")
     * @param data      Data to add to.
     */
    public void addGrade(String gradeType, String grade, ObservableList<Grade> data) {
        data.add(new Grade(gradeType, grade));
    }

    /**
     * Calculates the course grade for the user based on the entered scalings.
     * This works with any total scaling (i.e. your total scaling can be less than 1.00)
     *
     * @param data          Data to read from
     * @return              Grade for the entire course.
     */
    public String calculateCourseGrade(ObservableList<Grade> data, String midtermScale, String labScale,
                                       String quizScale, String hwScale, String miscScale, String finalScale) {
        double midtermGrade = getGradeFor(data, midtermScale, "midterm");
        double labGrade = getGradeFor(data, labScale, "lab");
        double quizGrade = getGradeFor(data, quizScale, "quiz");
        double hwGrade = getGradeFor(data, hwScale, "homework");
        double miscGrade = getGradeFor(data, miscScale, "misc");
        double finalGrade = getGradeFor(data, finalScale, "final exam");

        double maxGradePossible = Double.parseDouble(midtermScale) + Double.parseDouble(labScale) + Double.parseDouble(quizScale)
                + Double.parseDouble(hwScale) + Double.parseDouble(miscScale) + Double.parseDouble(finalScale);
        double receivedGrade = 0;
        if(maxGradePossible != 0) {
            receivedGrade = ((midtermGrade + labGrade + quizGrade + hwGrade + miscGrade + finalGrade) / maxGradePossible);
        }

        DecimalFormat format = new DecimalFormat("#.##");
        return format.format(receivedGrade) + " " + getLetterGrade(receivedGrade);
    }

    /**
     * Gets the grade for a single type of grade.
     *
     * @param data      Data to read from
     * @param scale     Scale for this grade type
     * @param key       Grade type
     * @return          Total points earned for this type.
     */
    private double getGradeFor(ObservableList<Grade> data, String scale, String key) {
        return Double.parseDouble(scale) * average(data, key);
    }

    /**
     * Converts a numeric grade into a letter grade.
     *
     * @param grade Numeric grade.
     * @return      Letter grade
     */
    private String getLetterGrade(Double grade) {
        if(grade >= 93.00) {
            return "A";
        } else if(grade >= 89.00) {
            return "AB";
        } else if(grade >= 85.00) {
            return "B";
        } else if(grade >= 81.00) {
            return "BC";
        } else if(grade >= 77.00) {
            return "C";
        } else if(grade >= 74.00) {
            return "CD";
        } else if(grade >= 70.00) {
            return "D";
        } else {
            return "F";
        }

    }

    /**
     * Averages a type of grade.
     *
     * @param data  Data to read from
     * @param key   Name of grade type
     * @return      Average grade for this type
     */
    private int average(ObservableList<Grade> data, String key) {
        int total = 0;
        int count = 0;
        for (Grade grade : data) {
            if (grade.getType().toLowerCase().equals(key.toLowerCase())) {
                total += Integer.parseInt(grade.getGrade());
                count++;
            }
        }
        if(count == 0) {
            return 0;
        }
        return total / count;
    }

    /**
     * Ensures that the text field is not empty or invalid.
     *
     * @param textField Text field to check
     */
    public void ensureNonEmpty(TextField textField) {
        if(textField.getText().equals("")) {
            textField.setText("0.00");
        }
        try {
            if (Double.parseDouble(textField.getText()) < 0.00 || Double.parseDouble(textField.getText()) > 1.00) {
                textField.setText("0.00");
            }
        } catch (NumberFormatException ex) {
            textField.setText("0.00");
        }
    }

    /**
     * Removes a grade from the datalist.
     *
     * @param data  Data to read from
     * @param which Which index to remove
     */
    public void removeGrade(ObservableList<Grade> data, int which) {
        data.remove(which);
    }

    /**
     * Saves the grades and scalings
     *
     * @param data          Grades data to read
     */
    public void save(ObservableList<Grade> data, String midtermScale, String labScale,
                     String quizScale, String hwScale, String miscScale, String finalScale) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(directory);
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
            fw.close();
            writeSettings(fileChooser.getCurrentDirectory().getAbsolutePath());
        } catch (IOException ex) {
        }
    }

    /**
     * Sets the grades data and scalings
     *
     * @param data  Grades data
     */
    public void load(ObservableList<Grade> data, TextField midtermScale, TextField labScale,
                     TextField quizScale, TextField hwScale, TextField miscScale, TextField finalScaleg) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(directory);
        fileChooser.showDialog(null, "Submit");
        File writeTo = fileChooser.getSelectedFile();
        if(writeTo.exists()) {
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
                in.close();
                writeSettings(fileChooser.getCurrentDirectory().getAbsolutePath());
        }

    }

    public void clear(ObservableList<Grade> data) {
        data.clear();
    }

    public void writeSettings(String content) throws IOException {
        File settings = new File("settings.txt");
        FileWriter writer = new FileWriter(settings);
        writer.write(content);
        writer.flush();
        writer.close();
    }

    public void readSettings() {
        File settings = new File("settings.txt");
        if(settings.exists()) {
            try {
                Scanner in = new Scanner(settings);
                directory = new File(in.nextLine());
                in.close();
            } catch (IOException ex) {

            }
        }
    }
}
