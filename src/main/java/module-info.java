module com.bomberman.bombermanplus {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires annotations;

    opens com.bomberman.bombermanplus to javafx.fxml;
    exports com.bomberman.bombermanplus;
}