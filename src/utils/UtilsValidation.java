package utils;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UtilsValidation {


	//creation of the invalid popup
	public static void popupBuild(String text,int width,int height) {
		Stage popup = new Stage();
		Button btnOk = new Button("EXIT");
		btnOk.setAlignment(Pos.CENTER);
		btnOk.setId("ButtonstylPopup");
		btnOk.setOnAction(e->{
			popup.close();
		});


		//create the element and put it in the right place
		VBox dialogVbox = new VBox(20);
		Label lbl = new Label(text);
		lbl.setId("lblLight");
		dialogVbox.getChildren().add(lbl);
		dialogVbox.getChildren().add(btnOk);
		dialogVbox.setAlignment(Pos.CENTER);
		dialogVbox.setId("popup");

		//set this new scene 
		Scene dialogScene = new Scene(dialogVbox, width, height);
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setScene(dialogScene);
		popup.setResizable(false);
		dialogScene.getStylesheets().add(UtilsValidation.class.getResource("/application/application.css").toExternalForm());
		popup.show();
	}

	
	

	public static void setFontSize(Label label,double size,int maxWidth){

		label.setFont(Font.font(size));
		label.applyCss();

		double width = label.getLayoutBounds().getWidth();

		if(width > maxWidth){
			size = size - 0.25;
			setFontSize(label,size,maxWidth);
		}
	}

}
