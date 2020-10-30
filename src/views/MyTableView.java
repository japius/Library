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
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import javafx.event.EventHandler;


public abstract class MyTableView<K extends DataTable> extends TableView<K>{

	public abstract void init();

	public abstract void fillView(ArrayList<K> list);

	public abstract void refill();

	public abstract class UpdatePopUp{

		protected Stage popupwindow;
		protected Label errorLabel;
		
		protected UpdatePopUp(String name, K item){
			popupwindow=new Stage();
        	popupwindow.initModality(Modality.APPLICATION_MODAL);
  			popupwindow.setTitle(name);

  			errorLabel = new Label("");
  			errorLabel.setTextFill(Color.web("#eb1c1c"));

  			init(item);

  			Button close = new Button("Annuler");
  			close.setOnAction(e -> close());

  			Button validate = new Button("Valider");
  			validate.setOnAction(e-> validate());

			GridPane form = makeGrid(getControls(),getNames(),validate,null);

			VBox layout = new VBox();
			layout.getChildren().add(errorLabel);
			layout.getChildren().add(form);

			layout.setAlignment(Pos.CENTER);
			Scene scene= new Scene(layout, 600, 600);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

        		public void handle(KeyEvent ke) {
        		    if (ke.getCode() == KeyCode.ENTER) {
        		        validate();
        		    }
        		}
  			});

  			popupwindow.setScene(scene);
        	popupwindow.showAndWait();
		}

		protected abstract void init(K item);
		protected abstract Control[] getControls();
		protected abstract String[] getNames();
		protected abstract String changeItem();
		protected abstract K getItem(); 


		protected void validate(){
		    String error = changeItem();
		    if(error == null){
		    	int i = Main.library.updateData(getItem());
		    	//refill();
		    	refresh();
		    	close();
		    }else{
		    	errorLabel.setText(error);
		    }
		}

		protected void close(){
			popupwindow.close();
		}

		protected GridPane makeGrid(Control[] inputs, String[] names, Button validate, Button close){
			GridPane layout= new GridPane();
			int i;
  			for(i = 0; i<inputs.length;i++){
  				System.out.println(names[i]);
  				System.out.println(inputs[i]);
  				layout.add(new Label(names[i]+": "),0,i);
  				layout.add(inputs[i],1,i);
  			}
  			layout.add(validate,1,i);
  			if(close!=null)
  				layout.add(close,0,i);
 			return layout;
		}
	}


}
