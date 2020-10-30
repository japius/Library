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
	StackPane mainPane;
	Menu viewMenu;

	public MyMenuBar(StackPane mainPane){
		super();
		this.mainPane = mainPane;
		init();
	}

	public void createAffichage(){
		viewMenu = new Menu("Affichage");

		MenuItem viewUser = new MenuItem("Utiisateurs");
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


		viewMenu.getItems().addAll(viewUser,viewCat,viewAut);
		getMenus().add(viewMenu);
	}


	private void init(){
		usrv = new UserTable();
		catv = new CategoryTable();
		autv = new AuthorTable();

		createAffichage();
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

	private void removeChildren(){
		mainPane.getChildren().clear();
	}
}