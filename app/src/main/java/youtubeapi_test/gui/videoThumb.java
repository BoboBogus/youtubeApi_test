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

public class videoThumb extends Thumbnail {
    public videoThumb(String thumbnailURL, String title,  String channelName, String VideoId, String ChannelId) {
        super(thumbnailURL, title); //calls parent constructor

        
    }
}
