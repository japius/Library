import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MyMenuBar extends MenuBar{
	UserTable usrv;
	CategoryTable catv;
	AuthorTable autv;
	OeuvreTable oeuv;
	EditionTable ediv;
	StackPane mainPane;

	public MyMenuBar(StackPane mainPane){
		super();
		this.mainPane = mainPane;
		init();
	}

	public void createAffichage(){
		Menu viewMenu = new Menu("Afficher");

		MenuItem viewUser = new MenuItem("Utilsateurs");
		viewUser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				putUserView();
			}
		});

		MenuItem viewCat = new MenuItem("Categories");
		viewCat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				putCatView();
			}
		});

		MenuItem viewAut = new MenuItem("Auteurs");
		viewAut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				putAutView();
			}
		});


		MenuItem viewOeu = new MenuItem("Oeuvres");
		viewOeu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				putOeuView();
			}
		});

		MenuItem viewEdi = new MenuItem("Editions");
		viewEdi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				putEdiView();
			}
		});


		viewMenu.getItems().addAll(viewUser,viewCat,viewAut,viewOeu, viewEdi);
		getMenus().add(viewMenu);
	}


	public void createNouveau(){
		Menu newMenu = new Menu("Nouveau");

		MenuItem newUser = new MenuItem("Utilsateur");
		newUser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				UserTable.UpdateUser update = usrv.new UpdateUser();
			}
		});

		MenuItem newCat = new MenuItem("Categorie");
		newCat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				CategoryTable.UpdateCategory update = catv.new UpdateCategory();
			}
		});

		MenuItem newAut = new MenuItem("Auteur");
		newAut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				AuthorTable.UpdateAuthor update = autv.new UpdateAuthor();
			}
		});

		MenuItem newOeu = new MenuItem("Oeuvre");
		newOeu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				OeuvreTable.UpdateOeuvre update = oeuv.new UpdateOeuvre();
			}
		});

		MenuItem newEdi = new MenuItem("Edition");
		newEdi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				EditionTable.UpdateEdition update = ediv.new UpdateEdition();
			}
		});


		newMenu.getItems().addAll(newUser,newCat,newAut,newOeu,newEdi);
		getMenus().add(newMenu);
	}

	private void init(){
		usrv = new UserTable();
		catv = new CategoryTable();
		autv = new AuthorTable();
		oeuv = new OeuvreTable();
		ediv = new EditionTable();

		createAffichage();
		createNouveau();
	}

	private void putUserView(){
		removeChildren();
		mainPane.getChildren().add(usrv);
	}

	private void putCatView(){
		removeChildren();
		mainPane.getChildren().add(catv);
	}

	private void putAutView(){
		removeChildren();
		mainPane.getChildren().add(autv);
	}

	private void putOeuView(){
		removeChildren();
		mainPane.getChildren().add(oeuv);
	}

	private void putEdiView(){
		removeChildren();
		mainPane.getChildren().add(ediv);
	}

	private void removeChildren(){
		mainPane.getChildren().clear();
	}
}