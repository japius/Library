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


public class CategoryTable extends MyTableView<Category>{


	public CategoryTable(){
		super();
    init();
    refill();
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

    setRowFactory(tv -> {
    TableRow<Category> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
             && event.getClickCount() == 2) {

            Category selectedCat = row.getItem();
            new UpdateCategory(selectedCat);
        }
    });
    return row;
  });
  }

  public void refill(){
    fillView(Main.library.getCategories());
  }

  class UpdateCategory extends UpdatePopUp {
  private Category category;
  private TextField name;
  private TextField time;
  private TextField borrowing;

   
  UpdateCategory(Category category){
    super("Modification d'une categorie",category);
  }

  UpdateCategory(){
    super("Creation d'une categorie",new Category(),true);
  }


  protected void init(Category category){
    //init champs
    this.category = category;
    name = new TextField(category.getName());
    time = new TextField(category.getTime()+"");
    borrowing = new TextField(category.getBorrowing()+"");

  }
    
  protected Control[] getControls(){
    Control con[] = {name,time,borrowing};
    return con;
  }

  protected String[] getNames(){
    String names[] = {"Nom", "Duree d'emprunt (jours)", "Nombre d'emprunt"};
    return names;
  }

  protected String changeItem(){
    int mytime = 0;
    int myborr = 0;
    try{
      mytime = Integer.parseInt(time.getText());
      myborr = Integer.parseInt(borrowing.getText());
      if(mytime<0 || myborr < 0)
        return "La duree d'emprunt et le nombre d'emprunt doivent etre des entiers positifs.";
    }catch(NumberFormatException e){
      return "La duree d'emprunt et le nombre d'emprunt doivent etre des entiers positifs.";
    }
    category.setCategory(name.getText(),mytime,myborr);
    return null;
  }

  protected Category getItem(){
    return category;
  }

}

}