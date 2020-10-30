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


public class OeuvreTable extends MyTableView<Oeuvre>{


	public OeuvreTable(){
		super();
    init();
    refill();
	}


  // Oeuvre
 public void init(){
    TableColumn<Oeuvre, Long> idCol //
      = new TableColumn<Oeuvre, Long>("Id");
 
      TableColumn<Oeuvre, String> titleCol//
          = new TableColumn<Oeuvre, String>("Titre");

      TableColumn<Oeuvre, Integer> yearCol//
        = new TableColumn<Oeuvre, Integer>("Annee");

      TableColumn<Oeuvre, Author> authorCol//
        = new TableColumn<Oeuvre, Author>("Auteur");
  

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        getColumns().addAll(idCol,titleCol,yearCol,authorCol);
  }

  public void fillView(ArrayList<Oeuvre> items){
    ObservableList<Oeuvre> obsList = FXCollections.observableArrayList(items);
    setItems(obsList);

    setRowFactory(tv -> {
    TableRow<Oeuvre> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
             && event.getClickCount() == 2) {

            Oeuvre selectedItem = row.getItem();
            new UpdateOeuvre(selectedItem);
        }
    });
    return row;
  });
  }

  public void refill(){
    fillView(Main.library.getOeuvres());
  }

  class UpdateOeuvre extends UpdatePopUp {
  private Oeuvre oeuvre;
  private TextField title;
  private TextField year;
  private ComboBox authors;

   
  UpdateOeuvre(Oeuvre oeuvre){
    super("Modification d'une oeuvre",oeuvre);
  }

  UpdateOeuvre(){
    super("Creation d'une oeuvre",new Oeuvre(),true);
  }

  protected void init(Oeuvre oeuvre){
    //init champs
    this.oeuvre = oeuvre;
    title = new TextField(oeuvre.getTitle());
    year = new TextField(oeuvre.getYear()+"");
    authors = new ComboBox();

    ArrayList<Author> listAuth = Main.library.getAuthors();
   
    ObservableList<Author> obsList = FXCollections.observableArrayList(listAuth);
    authors.setItems(obsList);

    if(newItem) return ;
    
    int index = 0;
    for(Author elem : obsList){
      if(elem.equals(oeuvre.getAuthor()))
        break;
      index++;
    }

    authors.getSelectionModel().select(index);

  }
    
  protected Control[] getControls(){
    Control con[] = {title,year,authors};
    return con;
  }

  protected String[] getNames(){
    String names[] = {"Titre","Date","Auteur"};
    return names;
  }

  protected String changeItem(){
    Author auth = (Author) authors.getSelectionModel().getSelectedItem();
    int myyear = 0;
    try{
      myyear = Integer.parseInt(this.year.getText());
    }catch(NumberFormatException e){
      return "L'annee doit etre un entier.";
    }
    oeuvre.setOeuvre(title.getText(),myyear,auth);
    return null;
  }

  protected Oeuvre getItem(){
    return oeuvre;
  }

}

}