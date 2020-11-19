package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HomeBp extends BorderPane{
	
	private Label lblAppName;
	
	private Button btnEncrypt;
	private Button btnDecrypt;
	
	private Stage stage;
	
	private double x = 400;
	private double y = 600;
	
	public HomeBp(Stage stage) {
		this.stage=stage;
		this.setId("FondNoir");
		
		this.setTop(getLblAppName());
		
		HBox hbbtn = new HBox();
		hbbtn.getChildren().add(getBtnEncrypt());
		hbbtn.getChildren().add(getBtnDecrypt());
		hbbtn.setAlignment(Pos.CENTER);
		hbbtn.setPadding(new Insets(0,0,5,0));
		
		this.setBottom(hbbtn);
	
		
	}

	
	
	public Label getLblAppName() {
		if(lblAppName == null) {
			lblAppName = new Label("Cryp Titon");
			lblAppName.setId("lblTitleLight");
			lblAppName.setAlignment(Pos.CENTER);
			lblAppName.setFont(Font.font(35));
			lblAppName.setMaxSize(x,y/7);
			lblAppName.setMinSize(x,y/7);
		}
		return lblAppName;
	}

	public void setLblAppName(Label lblAppName) {
		this.lblAppName = lblAppName;
	}

	public Button getBtnEncrypt() {
		if(btnEncrypt == null) {
			btnEncrypt = new Button("Encrypt");
			btnEncrypt.setAlignment(Pos.CENTER);
			btnEncrypt.setMaxSize(x*0.49,y/8);
			btnEncrypt.setMinSize(x*0.49,y/8);
			btnEncrypt.setId("Buttonstyl");
			
			//event mouse click
			btnEncrypt.setOnMouseClicked(e->{
				Scene scene = new Scene(new EncryptBp(this.stage),x,y);
				scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
				this.stage.setScene(scene);
			});
		}
		return btnEncrypt;
	}

	public void setBtnEncrypt(Button btnEncrypt) {
		this.btnEncrypt = btnEncrypt;
	}

	public Button getBtnDecrypt() {
		if(btnDecrypt == null) {
			btnDecrypt = new Button("Decrypt");
			btnDecrypt.setAlignment(Pos.CENTER);
			btnDecrypt.setMaxSize(x*0.49,y/8);
			btnDecrypt.setMinSize(x*0.49,y/8);
			btnDecrypt.setId("Buttonstyl");
			
			//event mouse click
			btnDecrypt.setOnMouseClicked(e->{
				Scene scene = new Scene(new DecryptBp(stage),x,y);
				scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
				stage.setScene(scene);
			});
		}
		return btnDecrypt;
	}

	public void setBtnDecrypt(Button btnDecrypt) {
		this.btnDecrypt = btnDecrypt;
	}
	

}
