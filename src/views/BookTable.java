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


public class BookTable extends MyTableView<Book>{


	public BookTable(){
		super();
    init();
    refill();
	}


  // Book
 public void init(){
    TableColumn<Book, Long> idCol //
      = new TableColumn<Book, Long>("Id");
 
      TableColumn<Book, Long> isbnCol//
          = new TableColumn<Book, Long>("ISBN");

      TableColumn<Book, String> editorCol//
        = new TableColumn<Book, String>("Edition");

      TableColumn<Book, String> oeuvreCol//
        = new TableColumn<Book, String>("Titres");

      TableColumn<Book, Author> authorCol//
        = new TableColumn<Book, Author>("Auteur(s)");

      TableColumn<Book, String> borrowCol//
        = new TableColumn<Book, String>("Emrunt");

      TableColumn<Book, User> userCol//
        = new TableColumn<Book, User>("Par");

      TableColumn<Book, Date> beginCol//
        = new TableColumn<Book, Date>("De");

      TableColumn<Book, Date> endCol//
        = new TableColumn<Book, Date>("A");

      borrowCol.getColumns().addAll(userCol,beginCol,endCol);


        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        editorCol.setCellValueFactory(new PropertyValueFactory<>("editor"));
        oeuvreCol.setCellValueFactory(new PropertyValueFactory<>("oeuvre"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("authors"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        beginCol.setCellValueFactory(new PropertyValueFactory<>("begin"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));



        getColumns().addAll(idCol,isbnCol,editorCol,oeuvreCol,authorCol,borrowCol);
  }

  public void fillView(ArrayList<Book> items){
    ObservableList<Book> obsList = FXCollections.observableArrayList(items);
    setItems(obsList);

    setRowFactory(tv -> {
    TableRow<Book> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
             && event.getClickCount() == 2 && Main.library.isAdmin()) {

            Book selectedItem = row.getItem();
            new UpdateBook(selectedItem);
        }
    });
    return row;
  });
  }

  public void refill(){
    fillView(Main.library.getBooks());
  }

  class UpdateBook extends UpdatePopUp {
  private Book book;

  private TextField quantity;
  private ComboBox editions;

   
  UpdateBook(Book book){
    super("Modification d'un livre",book);
  }

  UpdateBook(){
    super("Creation d'un livre",new Book(),true);
  }

  protected void init(Book book){
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
    book.setBook(edition,null,null,null,myquantity);
    System.out.println(book);
    return null;
  }

  protected Book getItem(){
    return book;
  }

}

}