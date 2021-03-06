package application;
	
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.HomeBp;


public class Main extends Application {

	public void start(Stage primaryStage) {
		try {
			HomeBp root = new HomeBp(primaryStage);
			Scene scene = new Scene(root,400,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image(new FileInputStream("resource/image/icone.jpg"),32,32,false,false));
			primaryStage.setTitle("Cryp Titon");
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
