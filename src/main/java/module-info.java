module com.andrewcomputsci.pathfinderfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens com.andrewcomputsci.pathfinderfx to javafx.fxml;
    exports com.andrewcomputsci.pathfinderfx;
}