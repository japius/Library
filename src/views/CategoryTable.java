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

import java.util.ArrayList;

public class CategoryTable extends MyTableView<Category>{


	public CategoryTable(){
		super();
    init();
	}


  // Category
 public void init(){
    TableColumn<Category, Integer> idCol //
      = new TableColumn<Category, Integer>("Id");
 
      // Create column Email (Data type of String).
      TableColumn<Category, String> nameCol//
          = new TableColumn<Category, String>("nom");
  
      // Create column FullName (Data type of String).
      TableColumn<Category, Integer> timeCol//
          = new TableColumn<Category, Integer>("Duree");
  
      // Create 2 sub column for FullName.
      TableColumn<Category, Integer> borrowingCol//
        = new TableColumn<Category, Integer>("Nombre d'emprunt");
  

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        borrowingCol.setCellValueFactory(new PropertyValueFactory<>("borrowing"));

        getColumns().addAll(idCol,nameCol,timeCol,borrowingCol);
  }

  public void fillView(ArrayList<Category> cat){
    ObservableList<Category> obsList = FXCollections.observableArrayList(cat);
    setItems(obsList);
  }

  public void refill(){
    fillView(Main.library.getCategories());
  }

}