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


public class EditionTable extends MyTableView<Edition>{


	public EditionTable(){
		super();
    init();
    refill();
	}


  // Edition
  @SuppressWarnings("unchecked")
 public void init(){
    TableColumn<Edition, Long> idCol //
      = new TableColumn<Edition, Long>("ISBN");
 
      TableColumn<Edition, String> editorCol//
          = new TableColumn<Edition, String>("Editeur");

      TableColumn<Edition, Integer> yearCol//
        = new TableColumn<Edition, Integer>("Annee de parution");

      TableColumn<Edition, Oeuvre> oeuvreCol//
        = new TableColumn<Edition, Oeuvre>("Titres");

      TableColumn<Edition, Author> authorCol//
        = new TableColumn<Edition, Author>("Auteur(s)");
  

        idCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        editorCol.setCellValueFactory(new PropertyValueFactory<>("editor"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        oeuvreCol.setCellValueFactory(new PropertyValueFactory<>("oeuvre"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("authors"));

        getColumns().addAll(idCol,editorCol,yearCol,authorCol,oeuvreCol);
  }

  public void fillView(ArrayList<Edition> items){
    ObservableList<Edition> obsList = FXCollections.observableArrayList(items);
    setItems(obsList);

    setRowFactory(tv -> {
    TableRow<Edition> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
             && event.getClickCount() == 2 && Main.library.isAdmin()) {

            Edition selectedItem = row.getItem();
            new UpdateEdition(selectedItem);
        }
    });
    return row;
  });
  }

  public void refill(){
    fillView(Main.library.getEditions());
  }

  class UpdateEdition extends UpdatePopUp {
  private Edition edition;
  private TextField isbn;
  private TextField editor;
  private TextField year;
  private ComboBox oeuvres;

   
  UpdateEdition(Edition edition){
    super("Modification d'une edition",edition);
  }

  UpdateEdition(){
    super("Creation d'une edition",new Edition(),true);
  }

  @SuppressWarnings("unchecked")
  protected void init(Edition edition){
    //init champs
    this.edition = edition;
    isbn = new TextField(edition.getIsbn()+"");
    if(!newItem){
      isbn.setDisable(true);
    }
    year = new TextField(edition.getYear()+"");
    editor = new TextField(edition.getEditor());
    oeuvres = new ComboBox();

    ArrayList<Oeuvre> listOeuv = Main.library.getOeuvres();
   
    ObservableList<Oeuvre> obsList = FXCollections.observableArrayList(listOeuv);
    oeuvres.setItems(obsList);

    if(newItem) return;

    int index = 0;
    for(Oeuvre elem : obsList){
      if(elem.equals(edition.getOeuvre()))
        break;
      index++;
    }
    oeuvres.getSelectionModel().select(index);

  }
    
  protected Control[] getControls(){
    Control con[] = {isbn,year,editor,oeuvres};
    return con;
  }

  protected String[] getNames(){
    String names[] = {"ISBN","Date de parution","Edition","Titre"};
    return names;
  }

  protected String changeItem(){
    Oeuvre oeuv = (Oeuvre) oeuvres.getSelectionModel().getSelectedItem();
    int myyear = 0;
    int myisbn = 0;
    try{
      myyear = Integer.parseInt(this.year.getText());
    }catch(NumberFormatException e){
      return "L'annee doit etre un entier.";
    }

    try{
      myisbn = Integer.parseInt(this.isbn.getText());
      if(myisbn < 0)
        return "Le numero ISBN doit etre un entier positif.";
    }catch(NumberFormatException e){
      return "Le numero ISBN doit etre un entier positif.";
    }
    edition.setEdition(myisbn,editor.getText(),myyear,oeuv);
    return null;
  }

  protected Edition getItem(){
    return edition;
  }

}

}