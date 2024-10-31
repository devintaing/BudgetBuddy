package application.style;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScrollBarCSS extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ScrollPane pane = new ScrollPane();
        pane.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());

        Pane emptyPane = new Pane();
        emptyPane.setPrefSize(600, 600);

        pane.setContent(emptyPane);

        stage.setScene(new Scene(pane, 200, 200));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
