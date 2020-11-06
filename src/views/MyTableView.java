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
import javafx.util.*;
import javafx.scene.control.TableColumn.*;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;


public abstract class MyTableView<K extends DataTable> extends TableView<K>{

	public abstract void init();

	public abstract void fillView(ArrayList<K> list);

	public abstract void refill();

	protected void actionButton(K item){
		System.out.println("Ce bouton ne fait rien, pense a ajouter un actionButton");
	}

	public void fillColumn(TableColumn<K,Void> myCol, String btnText){
		Callback<TableColumn<K, Void>, TableCell<K, Void>> cellFactory = new Callback<TableColumn<K, Void>, TableCell<K, Void>>() {
            @Override
            public TableCell<K, Void> call(final TableColumn<K, Void> param) {
                final TableCell<K, Void> cell = new TableCell<K, Void>() {

                    private final Button btn = new Button(btnText);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            K data = getTableView().getItems().get(getIndex());

                        	actionButton(data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }

                };
                return cell;
            }
        };
         myCol.setCellFactory(cellFactory);
	}

	public abstract class UpdatePopUp{

		protected Stage popupwindow;
		protected Label errorLabel;
		protected boolean newItem;
		
		protected UpdatePopUp(String name, K item, boolean newItem){
			this.newItem = newItem;
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

		protected UpdatePopUp(String name, K item){
			this(name,item,false);
		}

		protected abstract void init(K item);
		protected abstract Control[] getControls();
		protected abstract String[] getNames();
		protected abstract String changeItem();
		protected abstract K getItem(); 


		protected void validate(){
		    String error = changeItem();
		    if(error != null){
		    	errorLabel.setText(error);
		    	return;
		    }
		    int i = 0;
		    if(newItem){
		    	i = Main.library.insertData(getItem());
		    }else{
		    	i = Main.library.updateData(getItem());
		    }
		    if(i < 0){
		    	errorLabel.setText(DataTable.errorInsert(i));
		    	return;
		    }

		    refill();
		    refresh();
		    close();

		}

		protected void close(){
			popupwindow.close();
		}

		protected GridPane makeGrid(Control[] inputs, String[] names, Button validate, Button close){
			GridPane layout= new GridPane();
			int i;
  			for(i = 0; i<inputs.length;i++){
  				layout.add(new Label(names[i]+": "),0,i);
  				layout.add(inputs[i],1,i);
  			}
  			if(validate!=null)
  				layout.add(validate,1,i);
  			if(close!=null)
  				layout.add(close,1,i+1);
 			return layout;
		}
	}


}
