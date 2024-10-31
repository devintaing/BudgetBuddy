package application.style;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class ScrollBarCSS extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ScrollPane fillPane = new ScrollPane();
        fillPane.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm());

        Pane scrollPane = new Pane();
        scrollPane.setPrefSize(600, 600);

        fillPane.setContent(scrollPane);

        stage.setScene(new Scene(fillPane, 200, 200));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
