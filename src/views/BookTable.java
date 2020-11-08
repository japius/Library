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
import javafx.scene.control.Alert.AlertType; 


public class BookTable extends MyTableView<Book>{
  private long id_user = -1;
  private Oeuvre oeuvre = null;
  TableColumn<Book, String> borrowCol;
  TableColumn<Book, Boolean> dispoCol;
  TableColumn<Book, Void> empruntCol;

	public BookTable(){
		super();
    init();
    refill();
	}

  public BookTable(long id_user){
    super();
    init();
    this.id_user=id_user;
    refill();
  }

  public BookTable(Oeuvre oeuvre){
    super();
    init();
    this.oeuvre = oeuvre;
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

      borrowCol = new TableColumn<Book, String>("Emprunt");

      TableColumn<Book, User> userCol//
        = new TableColumn<Book, User>("Par");

      TableColumn<Book, String> beginCol//
        = new TableColumn<Book, String>("De");

      TableColumn<Book, String> endCol//
        = new TableColumn<Book, String>("A");

      dispoCol = new TableColumn<Book, Boolean>("Disponible");

      if(id_user < 0)
        empruntCol  = new TableColumn<Book, Void>("Emprunter");
      else 
        empruntCol  = new TableColumn<Book, Void>("Rendre");

      if(id_user>0)
        borrowCol.getColumns().addAll(beginCol,endCol);
      else
        borrowCol.getColumns().addAll(userCol,beginCol,endCol);
        
      fillColumn(empruntCol,"");


        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        editorCol.setCellValueFactory(new PropertyValueFactory<>("editor"));
        oeuvreCol.setCellValueFactory(new PropertyValueFactory<>("oeuvre"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("authors"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        beginCol.setCellValueFactory(new PropertyValueFactory<>("begin"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        dispoCol.setCellValueFactory(new PropertyValueFactory<>("dispo"));


        getColumns().addAll(idCol,isbnCol,editorCol,oeuvreCol,authorCol,borrowCol,dispoCol,empruntCol);
        if(id_user>0)
          userCol.setVisible(false);
        
  }

  protected void actionButton(Book data){
    int tmp = 0;
    if (id_user > 0){
      tmp = Main.library.returnBook(data);
    }else if(Main.library.isConnect()){
      tmp = Main.library.borrowBook(data,Main.library.getConnectedUser());
    }
    if(tmp<0){
      Alert a = new Alert(AlertType.WARNING);
      a.setContentText(DataTable.errorInsert(tmp));
      a.showAndWait();
    }
  }

  protected void executeOnButton(Button but, Book data){
    if(data.getDispo()){
      but.setText("Emprunter");
      but.setVisible(true);
    }else if(id_user>0){
      but.setText("Rendre");
      but.setVisible(true);
    }else{
      but.setVisible(false);
    }

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
    if(id_user > 0){
      fillView(Main.library.getBooks(id_user));
      dispoCol.setVisible(false);
      borrowCol.setVisible(true);
      empruntCol.setVisible(true);
    }else if(oeuvre != null){
      fillView(Main.library.getBooks(oeuvre));
      dispoCol.setVisible(true);
      if(Main.library.isConnect())
        empruntCol.setVisible(true);
      else
        empruntCol.setVisible(false);
      if(Main.library.isAdmin())
        borrowCol.setVisible(true);
      else
        borrowCol.setVisible(false);
    }else if(Main.library.isAdmin()){
      fillView(Main.library.getBooks());
      dispoCol.setVisible(true);
      borrowCol.setVisible(true);
      empruntCol.setVisible(true);
    }else if(Main.library.isConnect()){
      fillView(Main.library.getBooks());
      dispoCol.setVisible(true);
      borrowCol.setVisible(false);
      empruntCol.setVisible(true);
    }else{
      fillView(Main.library.getBooks());
      dispoCol.setVisible(true);
      borrowCol.setVisible(false);
      empruntCol.setVisible(false);
    }
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