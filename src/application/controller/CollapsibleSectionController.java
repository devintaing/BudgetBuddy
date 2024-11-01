package application.controller;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CollapsibleSectionController {

    private VBox collapsibleVBox;
    private Button toggleButton;

    public CollapsibleSectionController(VBox collapsibleVBox, Button toggleButton) {
        this.collapsibleVBox = collapsibleVBox;
        this.toggleButton = toggleButton;

        // Set the initial visibility and managed property
        this.collapsibleVBox.setVisible(false);
        this.collapsibleVBox.setManaged(false);

        // Set the button action to toggle visibility
        this.toggleButton.setOnAction(event -> toggleSection());
    }

    private void toggleSection() {
        boolean isVisible = collapsibleVBox.isVisible();

        if (isVisible) {
            // Fade out the VBox
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), collapsibleVBox);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                collapsibleVBox.setVisible(false);
                collapsibleVBox.setManaged(false);
                toggleButton.setText(toggleButton.getText().replace("Hide", "Show"));
            });
            fadeOut.play();
        } else {
            // Set visible before fading in
            collapsibleVBox.setVisible(true);
            collapsibleVBox.setManaged(true);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), collapsibleVBox);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
            toggleButton.setText(toggleButton.getText().replace("Show", "Hide"));
        }
    }
}
