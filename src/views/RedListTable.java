import java.util.ArrayList;
import java.util.Date;

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


public class RedListTable extends MyTableView<RedList>{
  private long id_user;
  private boolean actual;


	public RedListTable(){
		this(-1,false);
	}

  public RedListTable(long id_user, boolean actual){
    super();
    init();
    this.id_user = id_user;
    this.actual = actual;
    refill();
  }


  // RedList
 public void init(){
 
      TableColumn<RedList, String> userCol//
        = new TableColumn<RedList, String>("Utilisateur");

      TableColumn<RedList, String> beginCol//
        = new TableColumn<RedList, String>("De");

      TableColumn<RedList, String> endCol//
        = new TableColumn<RedList, String>("A");

      userCol.setCellValueFactory(new PropertyValueFactory<>("user"));
      beginCol.setCellValueFactory(new PropertyValueFactory<>("begin"));
      endCol.setCellValueFactory(new PropertyValueFactory<>("end"));



      getColumns().addAll(userCol,beginCol,endCol);
      if(id_user>0)
        userCol.setVisible(false);
  }

  public void fillView(ArrayList<RedList> items){
    ObservableList<RedList> obsList = FXCollections.observableArrayList(items);
    setItems(obsList);

    /*setRowFactory(tv -> {
    TableRow<RedList> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
             && event.getClickCount() == 2 && Main.library.isAdmin()) {

            RedList selectedItem = row.getItem();
            new UpdateRedList(selectedItem);
        }
    });
    return row;
  });*/

  }

  public void refill(){
    if(id_user > 0)
      fillView(Main.library.getRedLists(id_user));
    else if(actual)
      fillView(Main.library.getActualRedLists());
    else 
      fillView(Main.library.getRedLists());
  }

  /*class UpdateRedList extends UpdatePopUp {
  private RedList book;

  private TextField quantity;
  private ComboBox editions;

   
  UpdateRedList(RedList book){
    super("Modification d'un livre",book);
  }

  UpdateRedList(){
    super("Creation d'un livre",new RedList(),true);
  }

  protected void init(RedList book){
    //init champs
    this.book = book;
    quantity = new TextField("1");
    editions = new ComboBox();

    ArrayList<Edition> listEdi = Main.library.getEditions();
   
    ObservableList<Edition> obsList = FXCollections.observableArrayList(listEdi);
    editions.setItems(obsList);

    if(newItem) return;

    int index = 0;
    for(Edition elem : obsList){
      if(elem.equals(book.getEdition()))
        break;
      index++;
    }
    editions.getSelectionModel().select(index);

  }
    
  protected Control[] getControls(){

    Control con[];
    if(newItem){ 
      con = new Control[2];
      con[0] = quantity;
    }else con = new Control[1];
    con[con.length-1] = editions;
    return con;
  }

  protected String[] getNames(){
    String names[];
    if(newItem){ 
      names = new String[2];
      names[0] = "Quantite de nouveau livre";
    }else names = new String[1]; 
    names[names.length-1] = "Edition";
    return names;
  }

  protected String changeItem(){
    Edition edition = (Edition) editions.getSelectionModel().getSelectedItem();
    int myquantity =  0;
    try{
      myquantity = Integer.parseInt(this.quantity.getText());
    }catch(NumberFormatException e){
      return "La quantite doit etre un entier.";
    }
    book.setRedList(edition,null,null,null,myquantity);
    System.out.println(book);
    return null;
  }

  protected RedList getItem(){
    return book;
  }

}*/

}