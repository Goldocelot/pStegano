package view;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.SteganoEncoder;
import utils.Serialisation;

public class EncryptBp extends BorderPane{

	private Button btnFileChooser;
	private TextArea txtText;
	private Button btnDone;
	private Button btnBack;

	private Stage stage;

	private double x = 400;
	private double y = 600;
	
	private File file ;

	public EncryptBp(Stage stage) {
		this.stage=stage;
		this.setId("FondNoir");

		HBox hbbtn = new HBox();
		hbbtn.setAlignment(Pos.CENTER);
		hbbtn.getChildren().add(getBtnFileChooser());
		hbbtn.setPadding(new Insets(5,0,0,0));
		this.setTop(hbbtn);
		
		HBox hbtxt = new HBox();
		hbtxt.setAlignment(Pos.BOTTOM_CENTER);
		hbtxt.setPadding(new Insets(0,0,10,0));
		hbtxt.getChildren().add(getTxtText());
		this.setCenter(hbtxt);
		
		HBox hbbtnbot = new HBox();
		hbbtnbot.setAlignment(Pos.CENTER);
		hbbtnbot.getChildren().addAll(getBtnDone(),getBtnBack());
		hbbtnbot.setPadding(new Insets(0,0,5,0));
		this.setBottom(hbbtnbot);
	}

	public TextArea getTxtText() {
		if(txtText == null) {
			txtText = new TextArea();
			//txtText.setAlignment(Pos.CENTER);
			txtText.setMaxSize(x*0.80,y/9);
			txtText.setMinSize(x*0.80,y/9);
			txtText.setId("txtFieldBlack");
			txtText.setFont(Font.font(15));
		}
		return txtText;
	}

	public void setTxtText(TextArea txtText) {
		this.txtText = txtText;
	}

	public Button getBtnDone() {
		if(btnDone == null) {
			btnDone = new Button("Done");
			btnDone.setAlignment(Pos.CENTER);
			btnDone.setMaxSize(x*0.79,y/8);
			btnDone.setMinSize(x*0.79,y/8);
			btnDone.setId("Buttonstyl");

			//event mouse click
			btnDone.setOnMouseClicked(e->{
				//cripte ici 
				//use file
				if(file==null)return;
				if(getTxtText().getText().equals("")) return;
				
				FileChooser chooser = new FileChooser();
				chooser.getExtensionFilters().addAll(new ExtensionFilter("png","*.png"));
				File fileSave = chooser.showSaveDialog(this.getScene().getWindow());
				
				if(fileSave!=null) {	
					
					SteganoEncoder encoder = new SteganoEncoder(Serialisation.loadImageFromFile(file));
					Serialisation.saveImageInFile(encoder.encode(getTxtText().getText()),fileSave);
	
				}
			});
		}
		return btnDone;
	}

	public void setBtnDone(Button btnDone) {
		this.btnDone = btnDone;
	}



	public Button getBtnFileChooser() {
		if(btnFileChooser == null) {
			btnFileChooser = new Button("Choose file");
			btnFileChooser.setAlignment(Pos.CENTER);
			btnFileChooser.setMaxSize(x*0.8,y/8);
			btnFileChooser.setMinSize(x*0.8,y/8);
			btnFileChooser.setId("Buttonstyl");

			//event mouse click
			btnFileChooser.setOnMouseClicked(e->{
				FileChooser chooser = new FileChooser();
				chooser.getExtensionFilters().addAll(new ExtensionFilter("png","*.png"));
				file = chooser.showOpenDialog(this.getScene().getWindow());
				
			});
		}
		return btnFileChooser;
	}

	public void setBtnFileChooser(Button btnFileChooser) {
		this.btnFileChooser = btnFileChooser;
	}

	public Button getBtnBack() {
		if(btnBack == null) {
			btnBack = new Button("Back");
			btnBack.setAlignment(Pos.CENTER);
			btnBack.setMaxSize(x*0.19,y/8);
			btnBack.setMinSize(x*0.19,y/8);
			btnBack.setId("Buttonstyl");

			//event mouse click
			btnBack.setOnMouseClicked(e->{
				Scene scene = new Scene(new HomeBp(this.stage),x,y);
				scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
				this.stage.setScene(scene);
			});
		}
		return btnBack;
	}

	public void setBtnBack(Button btnBack) {
		this.btnBack = btnBack;
	}



}
