package view;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.SteganoEncoder;
import utils.Serialisation;

public class EncryptBp extends BorderPane{

	private Button btnFileChooser;
	private Label lblNbofChar;
	private TextArea txtText;
	private Button btnDone;
	private Button btnBack;

	private Stage stage;

	private double x = 400;
	private double y = 600;
	
	private File file ;
	private SteganoEncoder encoder;
	private int nbCharFile;

	public EncryptBp(Stage stage) {
		this.stage=stage;
		this.setId("FondNoir");

		VBox vbTop = new VBox();
		vbTop.setAlignment(Pos.CENTER);
		vbTop.getChildren().addAll(getBtnFileChooser(),getLblNbofChar());
		vbTop.setPadding(new Insets(5,0,0,0));
		this.setTop(vbTop);
		
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
			txtText.setMaxSize(x*0.95,y*0.30);
			txtText.setMinSize(x*0.95,y*0.30);
			txtText.setId("lblTitleLight");
			txtText.setFont(Font.font(15));
			
			txtText.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		            // this will run whenever text is changed
		        	if(file != null) {
		        		getLblNbofChar().setText( txtText.getLength() +" / "+ nbCharFile);
		        	}
		        }
		    });
		}
		return txtText;
	}

	public void setTxtText(TextArea txtText) {
		this.txtText = txtText;
	}

	public Button getBtnDone() {
		if(btnDone == null) {
			btnDone = new Button("Encrypt");
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
				
				if(file != null) {
					encoder = new SteganoEncoder(Serialisation.loadImageFromFile(file));
					nbCharFile = encoder.getMaxNumberOfChar();
					
					if(getTxtText().getLength() != 0) {
						getLblNbofChar().setText( txtText.getLength() +" / "+ nbCharFile);
					}else {
						getLblNbofChar().setText("file Already to use");
					}
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

	public Label getLblNbofChar() {
		if(lblNbofChar == null) {
			lblNbofChar = new Label("No File");
			lblNbofChar.setAlignment(Pos.CENTER_RIGHT);
			lblNbofChar.setMaxSize(x*0.95,y/9);
			lblNbofChar.setMinSize(x*0.95,y/9);
			lblNbofChar.setId("txtFieldBlack");
			lblNbofChar.setFont(Font.font(15));
		}
		return lblNbofChar;
	}

	public void setLblNbofChar(Label lblNbofChar) {
		this.lblNbofChar = lblNbofChar;
	}



}
