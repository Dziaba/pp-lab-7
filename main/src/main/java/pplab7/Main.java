package pplab7;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.nio.file.Path;

public class Main extends Application {
    private TextField directoryPathField;
    private TextField searchField;
    private TextArea resultArea;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Browser and Search");
        directoryPathField = new TextField();
        directoryPathField.setPromptText("Enter directory path");
        searchField = new TextField();
        resultArea = new TextArea();
        resultArea.setPrefHeight(400);
        searchField.setPromptText("Enter search phrase");
        Button browseButton = new Button("Browse");
        browseButton.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                searchFiles();
            }
        });
        Button searchButton = new Button("Search");
        HBox hBox = new HBox(10, directoryPathField, browseButton);
        VBox vBox = new VBox(10, hBox, searchField, searchButton, resultArea);
        Scene scene = new Scene(vBox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void searchFiles(){
        String directoryPath = directoryPathField.getText();
        if (directoryPath.isEmpty()){
            resultArea.setText("Please provide a directory path");
            return;
        }

        File directory = new File(directoryPath);
        if (!directory.isDirectory()){
            resultArea.setText("The provided path is not a directory");
        }
        StringBuilder results = new StringBuilder();
        listFilesInDirectory(directory, results);
        resultArea.setText(results.toString());
    }
    private void listFilesInDirectory(File directory, StringBuilder results) {
        try (Stream<Path> paths = Files.walk(Paths.get(directory.getAbsolutePath()))) {
            paths.filter(Files::isRegularFile).forEach(path -> results.append(path.toString()).append("\n"));
        } catch (IOException e) {
            results.append("An error occurred while reading the directory.");
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
