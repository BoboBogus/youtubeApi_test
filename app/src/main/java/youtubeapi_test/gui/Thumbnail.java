package youtubeapi_test.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import youtubeapi_test.MainApp;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class Thumbnail extends VBox {
    public Thumbnail(String thumbnailURL, String title){
        getStyleClass().add("thumbnail");
        Image image = new Image(thumbnailURL);
        ImageView imageView = new ImageView(image);
        Label titleLabel = new Label(title);
        getChildren().addAll(imageView, titleLabel);

    }
}
