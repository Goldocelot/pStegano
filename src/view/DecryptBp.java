package view;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.SteganoDecoder;
import utils.Serialisation;

public class DecryptBp extends BorderPane{



	private Button btnFileChooser;
	private Label lblFileIsLoad;
	private TextArea lblToString;
	private Button btnDone;
	private Button btnBack;

	private Stage stage;

	private double x = 400;
	private double y = 600;

	private File file ;

	public DecryptBp(Stage stage) {
		this.stage=stage;
		this.setId("FondNoir");

		VBox vbTop = new VBox();
		vbTop.setAlignment(Pos.CENTER);
		vbTop.getChildren().addAll(getBtnFileChooser(),getLblFileIsLoad());
		vbTop.setPadding(new Insets(5,0,0,0));
		this.setTop(vbTop);

		HBox hbtxt = new HBox();
		hbtxt.setAlignment(Pos.BOTTOM_CENTER);
		hbtxt.setPadding(new Insets(0,0,10,0));
		hbtxt.getChildren().add(getLblToString());
		this.setCenter(hbtxt);

		HBox hbbtnbot = new HBox();
		hbbtnbot.setAlignment(Pos.CENTER);
		hbbtnbot.getChildren().addAll(getBtnDone(),getBtnBack());
		hbbtnbot.setPadding(new Insets(0,0,5,0));
		this.setBottom(hbbtnbot);
	}

	public Button getBtnDone() {
		if(btnDone == null) {
			btnDone = new Button("Decrypt");
			btnDone.setAlignment(Pos.CENTER);
			btnDone.setMaxSize(x*0.79,y/8);
			btnDone.setMinSize(x*0.79,y/8);
			btnDone.setId("Buttonstyl");

			//event mouse click
			btnDone.setOnMouseClicked(e->{
				//decripte ici 
				//use file
				if(file!=null) {	
					SteganoDecoder decoder = new SteganoDecoder(Serialisation.loadImageFromFile(file));
					getLblToString().setText(decoder.decode());
					
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

				if(file != null) {
					getLblFileIsLoad().setText("file is available to use");
				}
				
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

	public TextArea getLblToString() {
		if(lblToString == null) {
			lblToString = new TextArea("EnCryp Titon");
			lblToString.setId("lblTitleLight");
			//lblToString.setAlignment(Pos.CENTER);
			//UtilsValidation.setFontSize(lblToString, 25, (int)(y/3));
			lblToString.setFont(Font.font(15));
			lblToString.setMaxSize(x*0.95,y*0.30);
			lblToString.setMinSize(x*0.95,y*0.30);
		}
		return lblToString;
	}

	public void setLblToString(TextArea lblToString) {
		this.lblToString = lblToString;
	}
	
	
	public Label getLblFileIsLoad() {
		if(lblFileIsLoad == null) {
			lblFileIsLoad = new Label("No File");
			lblFileIsLoad.setAlignment(Pos.CENTER_RIGHT);
			lblFileIsLoad.setMaxSize(x*0.95,y/9);
			lblFileIsLoad.setMinSize(x*0.95,y/9);
			lblFileIsLoad.setId("txtFieldBlack");
			lblFileIsLoad.setFont(Font.font(15));
		}
		return lblFileIsLoad;
	}

	public void setLblFileIsLoad(Label lblNbofChar) {
		this.lblFileIsLoad = lblNbofChar;
	}

}
