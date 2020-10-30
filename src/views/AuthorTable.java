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


public class AuthorTable extends MyTableView<Author>{


	public AuthorTable(){
		super();
    init();
    refill();
	}


  // Author
 public void init(){
    TableColumn<Author, Integer> idCol //
      = new TableColumn<Author, Integer>("Id");
 
      TableColumn<Author, String> firstnameCol//
          = new TableColumn<Author, String>("Prenom");
  
 
      TableColumn<Author, String> lastnameCol//
          = new TableColumn<Author, String>("Nom");
  

      TableColumn<Author, Integer> yearCol//
        = new TableColumn<Author, Integer>("Anne de naissance");
  

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        getColumns().addAll(idCol,firstnameCol,lastnameCol,yearCol);
  }

  public void fillView(ArrayList<Author> items){
    ObservableList<Author> obsList = FXCollections.observableArrayList(items);
    setItems(obsList);

    setRowFactory(tv -> {
    TableRow<Author> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
             && event.getClickCount() == 2) {

            Author selectedItem = row.getItem();
            new UpdateAuthor(selectedItem);
        }
    });
    return row;
  });
  }

  public void refill(){
    fillView(Main.library.getAuthors());
  }

  class UpdateAuthor extends UpdatePopUp {
  private Author author;
  private TextField firstname;
  private TextField lastname;
  private TextField year;

   
  UpdateAuthor(Author author){
    super("Modification d'un auteur",author);
  }

  UpdateAuthor(){
    super("Creation d'un auteur",new Author(),true);
  }

  protected void init(Author author){
    //init champs
    this.author = author;
    firstname = new TextField(author.getFirstname());
    lastname = new TextField(author.getLastname());
    year = new TextField(author.getYear()+"");

  }
    
  protected Control[] getControls(){
    Control con[] = {firstname,lastname,year};
    return con;
  }

  protected String[] getNames(){
    String names[] = {"Prenom","Nom","Annee de naissance"};
    return names;
  }

  protected String changeItem(){
    int myyear = 0;
    try{
      myyear = Integer.parseInt(this.year.getText());
    }catch(NumberFormatException e){
      return "L'annee de naissance doit etre un entier.";
    }
    author.setAuthor(firstname.getText(),lastname.getText(),myyear);
    return null;
  }

  protected Author getItem(){
    return author;
  }

}

}