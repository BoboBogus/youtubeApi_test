package youtubeapi_test;

import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import youtubeapi_test.gui.channelThumb;
import youtubeapi_test.gui.videoThumb;


public class MainApp extends Application {

    private static final String DEVELOPER_KEY = "";

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    Stage stage;

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FlowPane fp = new FlowPane();
        TextField tf = new TextField();
        fp.getChildren().add(tf);
        Button btn = new Button("search");
        HBox videoContent = new HBox();
        HBox channelContent = new HBox();
        ScrollPane videoScroll = new ScrollPane();
        ScrollPane channelScroll = new ScrollPane();
        videoScroll.setContent(videoContent);
        channelScroll.setContent(channelContent);
        btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
    
            public void handle(ActionEvent event) {

            videoContent.getChildren().clear();
            channelContent.getChildren().clear();
            YouTube youtubeService;
            try {
                youtubeService = getService();
                YouTube.Search.List request = youtubeService.search().list("snippet");
                SearchListResponse response = request.setKey(DEVELOPER_KEY)
                    .setMaxResults(25L)
                    .setQ(tf.getText())
                    .execute();
            
            JSONArray items = new JSONObject(response).getJSONArray("items");
            for(int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String kind = item.getJSONObject("id").getString("kind");
                JSONObject snippet = item.getJSONObject("snippet");
                String thumbnailUrl = snippet.getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                String title  = snippet.getString("title");
                String ChannelId = "filler";
                //snippet.getJSONObject("id").getString("channelId");
                switch(kind){
                    case "youtube#video":
                        String ChanelName = snippet.getString("channelTitle");
                        String VideoId = "filler";
                        //items.getJSONObject(i).getJSONObject("id").getString("videoId");
                        videoThumb videothumb = new videoThumb(thumbnailUrl, title, ChanelName, VideoId, ChannelId);
                        videoContent.getChildren().add(videothumb);
                        videothumb.setOnMouseClicked((MouseEvent MouseEvent) -> {
                            System.out.println(item+"\n");
                        });
                        break;
                    case "youtube#channel":
                        System.out.println(kind);
                        channelThumb channelthumb = new channelThumb(thumbnailUrl, title, ChannelId);
                        channelContent.getChildren().add(channelthumb);
                        channelthumb.setOnMouseClicked((MouseEvent MouseEvent) -> {
                            System.out.println(item+"\n");
                        });
                        break;
                }

                
            }
            // JSONReader jsonReader = JsonFactory.createReader(response)
            // JSONArray json = ;
            // for(JSONObject item : json.get("items")){

            // }


            } catch (GeneralSecurityException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }
            
        });
        fp.getChildren().add(btn);
        fp.getChildren().add(videoScroll);
        fp.getChildren().add(channelScroll);
        Scene scene = new Scene(fp, 700, 480);
        scene.getStylesheets().add(this.getClass().getResource("/styles.css").toString());

        
        fp.getStyleClass().add("main");
        videoContent.getStyleClass().add("content");
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();

        //Flow based on window width
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(stage.getWidth());
            videoScroll.setPrefWidth(stage.getWidth() -10);
        });
    }

    public void OpenVideo(String VideoID){
        FlowPane fp = new FlowPane();
        Scene scene = new Scene(fp, 100, 100);
        stage.setScene(scene);
    }
    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    public static void main(String[] args) throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        launch(args);
    }

    

}