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
import javafx.scene.layout.Region;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;

public class OeuvreView extends GridPane{
	private Oeuvre oeuvre;

	private GridPane info_oeuvre;
	private TextField[] text_oeuvre;

	private BorderPane info_author;
	private BorderPane info_book;

	private OeuvreView(){
		super();
	}


	public OeuvreView(Oeuvre oeuvre){
		this();
		init(oeuvre);
	}


	public void init(Oeuvre oeuvre){
		this.oeuvre=oeuvre;
		info_oeuvre = new GridPane();
		info_author = new BorderPane();
		info_book = new BorderPane();


		init_oeuvre();
		init_author();
		init_book();

		/*ColumnConstraints c1 = new ColumnConstraints();
		c1.setPercentWidth(50);
		c1.setHalignment(HPos.CENTER);
		getColumnConstraints().add(c1);
		RowConstraints r1 = new RowConstraints();
		r1.setPercentHeight(50);
		r1.setValignment(VPos.CENTER);
		getRowConstraints().add(r1);*/


		add(info_oeuvre, 0, 0);
		add(info_book, 0, 1,2,1);
		add(info_author, 1,0);
	}

	private void init_book(){
		info_book.setTop(new Label("Liste des livres"));
		BookTable bookt = new BookTable(oeuvre);
		info_book.setCenter(bookt);
	}


	private void init_author(){
		HBox box = new HBox();
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		info_author.setTop(box);

		ComboBox authors = new ComboBox();
		if(!Main.library.isAdmin())
			authors.setVisible(false);
		ArrayList<Author> authorList = Main.library.getNotAuthors(oeuvre.getId());
		ObservableList<Author> obsList = FXCollections.observableArrayList(authorList);
    	authors.setItems(obsList);

		AuthorTable autht = new AuthorTable(oeuvre);
		info_author.setCenter(autht);

		Button addAuthor = new Button("Ajouter l'auteur");
		if(!Main.library.isAdmin())
			addAuthor.setVisible(false);
		addAuthor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Author auth = (Author) authors.getSelectionModel().getSelectedItem();
            	Main.library.addAuthor(auth.getId(),oeuvre.getId());
            	init_author();
            }
        });

        ArrayList<Category> listCat = Main.library.getCategories();


		box.getChildren().addAll(new Label("Liste des auteurs"),spacer,authors,addAuthor);

	}


	private void init_oeuvre(){

		info_oeuvre.setAlignment(Pos.CENTER);
		text_oeuvre = new TextField[5];
		Label errorLabel = new Label();

		info_oeuvre.add(new Label("Informaton sur l'oeuvre"),0,0,2,1);
		info_oeuvre.add(errorLabel,0,1,2,1);

		info_oeuvre.add(new Label("Titre :"),0,2);
		info_oeuvre.add(new Label("Annee :"),0,3);

		if(Main.library.isAdmin()){
			text_oeuvre[0] = new  TextField(oeuvre.getTitle());
			info_oeuvre.add(text_oeuvre[0],1,2);

			text_oeuvre[1] = new  TextField(oeuvre.getYear()+"");
			info_oeuvre.add(text_oeuvre[1],1,3);
		}else{
			info_oeuvre.add(new Label(oeuvre.getTitle()),1,2);
			info_oeuvre.add(new Label(oeuvre.getYear()+""),1,3);
		}

        Button validate_oeuvre = new Button("Enregister les modifications");
        if(!Main.library.isAdmin())
        	validate_oeuvre.setVisible(false);

        info_oeuvre.add(validate_oeuvre,0,4,2,1);

        validate_oeuvre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	int myyear = 0;
    			try{
    			  myyear = Integer.parseInt(text_oeuvre[1].getText());
    			}catch(NumberFormatException e){
    			  errorLabel.setText("L'annee doit etre un entier.");
    			  return;
    			}

                oeuvre.setOeuvre(text_oeuvre[0].getText(),myyear);
            	int tmp = Main.library.updateData(oeuvre);
            	if(tmp<0){
            		errorLabel.setText(DataTable.errorInsert(tmp));
            	}
            }
        });



	}

}