import java.util.ArrayList;
import java.util.Date;
import java.time.*;

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
  }

  public void refill(){
    if(id_user > 0)
      fillView(Main.library.getRedLists(id_user));
    else if(actual)
      fillView(Main.library.getActualRedLists());
    else 
      fillView(Main.library.getRedLists());
  }

  class UpdateRedList extends UpdatePopUp {
  private RedList redList;

  private DatePicker date;

   
  UpdateRedList(RedList redList){
    super("Mise sur liste rouge",redList,true);
  }

  UpdateRedList(){
    super("Mise sur liste rouge",new RedList(),true);
  }

  protected void init(RedList redList){
    //init champs
    this.redList = redList;
    date = new DatePicker();
    date.setDayCellFactory(picker -> new DateCell() {
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            LocalDate today = LocalDate.now();

            setDisable(empty || date.compareTo(today) < 0 );
        }
    });

  }
    
  protected Control[] getControls(){

    Control con[] = {date};
    return con;
  }

  protected String[] getNames(){
    String names[] = {"Date de fin :"};
    return names;
  }

  protected String changeItem(){
    LocalDate localDate = date.getValue();
    Date actualDate = new Date();
    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
    System.out.println(actualDate);
    System.out.println(Date.from(instant));
    redList.setRedList(actualDate,Date.from(instant));
    return null;
  }

  protected RedList getItem(){
    return redList;
  }

}

}