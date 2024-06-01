module pplab7 {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens pplab7 to javafx.fxml;
    exports pplab7;
}
