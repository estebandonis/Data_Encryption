module com.laboratories.one {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens one to javafx.fxml;
    exports one;
}