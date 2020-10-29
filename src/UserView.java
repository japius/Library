import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UserView extends TableView<User>{


	public UserView(){
		super();
		TableColumn<User, Integer> idCol //
            = new TableColumn<User, Integer>("Id");
 
      	// Create column Email (Data type of String).
      	TableColumn<User, String> emailCol//
      	    = new TableColumn<User, String>("Email");
 	
 	    // Create column FullName (Data type of String).
 	    TableColumn<User, String> fullNameCol//
 	        = new TableColumn<User, String>("Nom complet");
 	
 	    // Create 2 sub column for FullName.
 	    TableColumn<User, String> firstNameCol//
 	     	= new TableColumn<User, String>("Prenom");
 	
 	    TableColumn<User, String> lastNameCol //
 	     	= new TableColumn<User, String>("Nom");
 	
 	    // Add sub columns to the FullName
 	    fullNameCol.getColumns().addAll(firstNameCol, lastNameCol);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("surename"));

        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
      	//lastNameCol.setSortable(false);

      	getColumns().addAll(idCol,emailCol,fullNameCol);

	}

	public void fillView(ArrayList<User> users){
		ObservableList<User> obsUsers = FXCollections.observableArrayList(users);
		setItems(obsUsers);
	}
}