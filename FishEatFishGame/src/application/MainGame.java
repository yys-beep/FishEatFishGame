package application;
	
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class MainGame extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LoginScene1.fxml"));
			//load scene
			Scene scene = new Scene(root,1000,700);
			scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());
			//for decorating the gui
			primaryStage.setScene(scene);
			primaryStage.setX(0);
			primaryStage.setY(0);
			primaryStage.setTitle("Fish Eat Fish Game");
			
			Image icon = new Image(MainGame.class.getResourceAsStream("logofish.png"));
			primaryStage.getIcons().add(icon);
			
			primaryStage.show();
			primaryStage.setResizable(false);
			
			primaryStage.setOnShown(event -> {
				root.requestFocus();
				});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
