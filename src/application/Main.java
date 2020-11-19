package application;
	
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.SteganoDecoder;
import model.SteganoDecoderVar;
import model.SteganoEncoder;
import utils.Serialisation;
import view.HomeBp;


public class Main extends Application {

	public void start(Stage primaryStage) {
		try {
			HomeBp root = new HomeBp(primaryStage);
			Scene scene = new Scene(root,400,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			//primaryStage.getIcons().add(new Image(new FileInputStream("resource/HomeScene/icone.png"),32,32,false,false));
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//launch(args);
		Serialisation.saveImageInFile(SteganoEncoder.encode(Serialisation.loadImageFromFile(new File("30x10.png")), "Yep"),new File("stegano30x10.png"));
		System.out.println(SteganoDecoderVar.decode(Serialisation.loadImageFromFile(new File("stegano30x10.png"))));
	}
}
