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
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.VPos;
import javafx.geometry.HPos;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;

public class ProfileView extends GridPane{
	private User user;

	private GridPane info_user;
	private TextField[] text_user;
	private Label errorLabelUser = new Label();
	private Button validate_user;


	private GridPane info_cat;
	private ArrayList<Category> list_cat;
	private ComboBox list_cat_visual;
	private Label[] text_cat;

	private BorderPane info_emprunt;
	private BorderPane hist_rouge;

	public ProfileView(){
		super();
	}


	public ProfileView(User user){
		this();
		init(user);
	}


	public void init(User user){
		this.user=user;
		info_user = new GridPane();
		info_cat = new GridPane();
		info_emprunt = new BorderPane();
		hist_rouge = new BorderPane();

		init_emprunt();
		init_user();
		init_cat();
		init_rouge();

		BorderPane tmpUser = new BorderPane();
		tmpUser.setTop(errorLabelUser);
		tmpUser.setCenter(info_user);
		errorLabelUser.setTextFill(Color.web("#eb1c1c"));
		tmpUser.setBottom(validate_user);

		ColumnConstraints c1 = new ColumnConstraints();
		c1.setPercentWidth(50);
		c1.setHalignment(HPos.CENTER);
		getColumnConstraints().add(c1);
		RowConstraints r1 = new RowConstraints();
		r1.setPercentHeight(50);
		r1.setValignment(VPos.CENTER);
		getRowConstraints().add(r1);


		add(tmpUser, 0, 0);
		add(info_cat,0,1);
		add(info_emprunt, 1, 0);
		add(hist_rouge, 1,1);

	}

	private void init_emprunt(){
		info_emprunt.setTop(new Label("Liste des emprunts"));
	}

	private void init_cat(){
    	info_cat.add(new Label("Nombre d'emprunt autorisee :"),0,0);
    	info_cat.add(new Label("Duree maximale d'un emprunt :"),0,1);
    	info_cat.add(text_cat[0],1,0);
    	info_cat.add(text_cat[1],1,1);
    	maj_cat();
	}

	private void maj_cat(){
		Category selectedCat = (Category) list_cat_visual.getSelectionModel().getSelectedItem();
		text_cat[0].setText(selectedCat.getBorrowing()+"");
		text_cat[1].setText(selectedCat.getTime()+"");
	}


	private void init_rouge(){
		hist_rouge.setTop(new Label("Historique de la liste rouge"));
	}

	private void init_user(){
		text_user = new TextField[5];

		info_user.add(new Label("Prenom"),0,0);
		text_user[0] = new  TextField(user.getName());
		info_user.add(text_user[0],1,0);

		info_user.add(new Label("Nom"),0,1);
		text_user[1] = new  TextField(user.getSurename());
		info_user.add(text_user[1],1,1);

		info_user.add(new Label("Email"),0,2);
		text_user[2] = new  TextField(user.getMail());
		info_user.add(text_user[2],1,2);

		list_cat_visual = new ComboBox();
		text_cat = new Label[2];
		text_cat[0] = new  Label();
		text_cat[1] = new  Label();
		list_cat = Main.library.getCategories();
		ObservableList<Category> obsList = FXCollections.observableArrayList(list_cat);
    	list_cat_visual.setItems(obsList);
    	int i = 0;
    	for(Category tmp : obsList){
    		if(tmp.getId()==user.getCategory())
    			break;
    		i++;
    	}
    	list_cat_visual.getSelectionModel().select(i);


    	if(Main.library.isAdmin()){
    		info_user.add(new Label("Categorie :"),0,3);
    		info_user.add(list_cat_visual,1,3);
    	}


    	list_cat_visual.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                maj_cat();
            }
        });

        validate_user = new Button("Enregister les modifications");
        validate_user.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Category selectedCat = (Category) list_cat_visual.getSelectionModel().getSelectedItem();
            	System.out.println(text_user[0]);
            	System.out.println(text_user[1]);
            	System.out.println(text_user[2]);
            	System.out.println(selectedCat);
                user.setUser(text_user[0].getText(),text_user[1].getText(),text_user[2].getText(),selectedCat.getId());
            	int tmp = Main.library.updateData(user);
            	if(tmp<0){
            		errorLabelUser.setText(DataTable.errorInsert(tmp));
            	}
            }
        });
	}

}