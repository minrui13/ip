package yappa;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's
 * face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a dialog box with the specified text and speaker image.
     *
     * @param text Text to display.
     * @param speakerImage Speaker image to display.
     */
    public DialogBox(String text, Image speakerImage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();

            // These controls are required by DialogBox.fxml for every dialog box.
            assert dialog != null && displayPicture != null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(speakerImage);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the
     * right.
     */
    private void flip() {
        ObservableList<Node> reversedChildren = FXCollections.observableArrayList(getChildren());
        Collections.reverse(reversedChildren);
        getChildren().setAll(reversedChildren);
        setAlignment(Pos.BOTTOM_LEFT);
    }

    /**
     * Creates a dialog box aligned as a user message.
     *
     * @param text Text to display.
     * @param speakerImage Speaker image to display.
     * @return User-aligned dialog box.
     */
    public static DialogBox getUserDialog(String text, Image speakerImage) {
        DialogBox dialogBox = new DialogBox(text, speakerImage);
        dialogBox.dialog.getStyleClass().add("user-dialog");
        return dialogBox;
    }

    /**
     * Creates a dialog box aligned as a Yappa message.
     *
     * @param text Text to display.
     * @param speakerImage Speaker image to display.
     * @return Yappa-aligned dialog box.
     */
    public static DialogBox getYappaDialog(String text, Image speakerImage) {
        DialogBox dialogBox = new DialogBox(text, speakerImage);
        dialogBox.dialog.getStyleClass().add("yappa-dialog");
        dialogBox.flip();
        return dialogBox;
    }
}
