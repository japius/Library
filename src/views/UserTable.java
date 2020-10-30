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

import java.util.ArrayList;

public class UserTable extends MyTableView<User>{


	public UserTable(){
		super();
    init();
    refill();
	}


  // Utilisateurs
  public void init(){
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

      TableColumn<User, Long> idCategoryCol //
        = new TableColumn<User, Long>("Id categorie");
  
      // Add sub columns to the FullName
      fullNameCol.getColumns().addAll(firstNameCol, lastNameCol);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("surename"));
        idCategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        //userNameCol.setSortType(TableColumn.SortType.DESCENDING);
        //lastNameCol.setSortable(false);

        getColumns().addAll(idCol,emailCol,fullNameCol,idCategoryCol);
  }

	public void fillView(ArrayList<User> users){
		ObservableList<User> obsList = FXCollections.observableArrayList(users);
		setItems(obsList);

    setRowFactory(tv -> {
    TableRow<User> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
             && event.getClickCount() == 2) {

            User selectedUser = row.getItem();
            new UpdateUser(selectedUser);
        }
    });
    return row;
  });

	}

  public void refill(){
    fillView(Main.library.getUsers());
  }

class UpdateUser extends UpdatePopUp {
  private User user;
  private TextField firstName;
  private TextField lastName;
  private TextField email;
  private ComboBox categories;
   
  UpdateUser(User user){
    super("Modification utilisateur",user);
  }
  protected void init(User user){
    //init champs
    this.user = user;
    firstName = new TextField(user.getName());
    lastName = new TextField(user.getSurename());
    email = new TextField(user.getMail());
    categories = new ComboBox();
    System.out.println(firstName);
  
    ArrayList<Category> listCat = Main.library.getCategories();
    int index = -1;
    for(int i = 0;i<listCat.size();i++){
      if(listCat.get(i).getId() == user.getCategory()){
        index=i;
        break;
      }
    }
   
    ObservableList<Category> obsList = FXCollections.observableArrayList(listCat);
    categories.setItems(obsList);
    categories.getSelectionModel().select(index);

  }
    
  protected Control[] getControls(){
    Control con[] = {firstName,lastName,email,categories};
    return con;
  }

  protected String[] getNames(){
    String names[] = {"Prenom", "Nom", "Email", "Categorie"};
    return names;
  }

  protected String changeItem(){
    Category tmp = (Category) categories.getSelectionModel().getSelectedItem();
    String mail = email.getText();
    if(!User.isEmailAdress(mail)){
      return "L'adresse email n'a pas un format valide.";
    }
    user.setUser(firstName.getText(),lastName.getText(),email.getText(),tmp.getId());
    return null;
  }

  protected User getItem(){
    return user;
  }

}

}
