import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.VPos;
import javafx.geometry.HPos;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ConnectView extends GridPane{
	public ConnectView(){
		super();
		setAlignment(Pos.CENTER);
		Text scenetitle = new Text("Bienvenue");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		add(scenetitle, 0, 0, 2, 1);

		Label errorLabel = new Label("");
		add(errorLabel, 0, 1,2,1);
		
		Label userMail = new Label("Email :");
		add(userMail, 0, 2);
		
		TextField userTextField = new TextField();
		add(userTextField, 1, 2);
		
		Label pw = new Label("Mot de passe:");
		add(pw, 0, 3);
		
		PasswordField pwBox = new PasswordField();
		add(pwBox, 1, 3);

		Button validate = new Button("Se connecter");
		add(validate,0,4,2,1);

		validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(!Main.library.connect(userTextField.getText(),pwBox.getText()))
            		errorLabel.setText("L'adresse mail ou le mot de passe est incorrect.");
            	else{
            		getChildren().clear();
            		Text scenetitle = new Text(String.format("Bienvenue %s %s",Main.library.getConnectedUser().getName(),Main.library.getConnectedUser().getSurename()));
					scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
					add(scenetitle, 0, 0, 2, 1);
					Main.menubar.maj();
            	}
            }
        });
	}

}