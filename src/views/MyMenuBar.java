import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.LinkedList;

public class MyMenuBar extends HBox{
	private UserTable usrv;
	private CategoryTable catv;
	private AuthorTable autv;
	private OeuvreTable oeuv;
	private EditionTable ediv;
	private BookTable bokv;
	private RedListTable redv;

	private LinkedList<MenuItem> adminOnly = new LinkedList<>();

	//ProfileView profileView;

	private MenuBar leftBar;

	private MenuBar rightBar;
	private Menu connect;
	private Menu profile;
	private Menu disconnect;
	private Menu inscription;


	private StackPane mainPane;
	private Node tmpView = null;

	private Menu newMenu;

	public MyMenuBar(StackPane mainPane){
		super();
		this.mainPane = mainPane;
		init();
	}

	private void createAffichage(){
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

		MenuItem viewBok = new MenuItem("Livres");
		viewBok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				putBokView();
			}
		});

		MenuItem viewRed = new MenuItem("Liste rouge");
		viewRed.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				putRedView();
			}
		});

		adminOnly.add(viewUser);
		adminOnly.add(viewCat);
		adminOnly.add(viewRed);
		viewMenu.getItems().addAll(viewUser,viewCat,viewAut,viewOeu, viewEdi,viewBok,viewRed);
		leftBar.getMenus().add(viewMenu);
	}

	public void createTheme(){
		Menu themeMenu = new Menu("Theme");

		MenuItem sombre = new MenuItem("Sombre");
		sombre.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				Main.changeTheme(true);
			}
		});

		MenuItem claire = new MenuItem("Claire");
		claire.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				Main.changeTheme(false);
			}
		});
		themeMenu.getItems().addAll(sombre,claire);
		leftBar.getMenus().add(themeMenu);
	}


	private void createConnect(){
		Label connect = new Label("Connexion");
		connect.setOnMouseClicked(new EventHandler<MouseEvent>() {
 
            @Override
            public void handle(MouseEvent event) {
                ConnectView conv = new ConnectView();
                putTmpView(conv);
                maj();
            }
        });

        Label inscription = new Label("Inscription");
        inscription.setOnMouseClicked(new EventHandler<MouseEvent>() {
 
            @Override
            public void handle(MouseEvent event) {
            	UserTable.UpdateUser update = usrv.new UpdateUser();
                maj();
            }
        });

		Label profile = new Label("Profil");
		profile.setOnMouseClicked(new EventHandler<MouseEvent>() {
 
            @Override
            public void handle(MouseEvent event) {
            	ProfileView profileView = new ProfileView(Main.library.getConnectedUser());
            	putTmpView(profileView);
            }
        });

        Label disconnect = new Label("Deconnexion");
		disconnect.setOnMouseClicked(new EventHandler<MouseEvent>() {
 
            @Override
            public void handle(MouseEvent event) {
            	Main.library.disconnect();
            	BorderPane bor = new BorderPane();
            	Text scenetitle = new Text("Au revoir");
				scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
				bor.setCenter(scenetitle);
				putTmpView(bor);
            	maj();
            }
        });

        this.connect.setGraphic(connect);
        this.profile.setGraphic(profile);
        this.disconnect.setGraphic(disconnect);
        this.inscription.setGraphic(inscription);
        rightBar.getMenus().addAll(this.inscription,this.connect,this.profile,this.disconnect);

	}

	private void createNouveau(){
		newMenu = new Menu("Nouveau");

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

		MenuItem newBok = new MenuItem("Livre");
		newBok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				BookTable.UpdateBook update = bokv.new UpdateBook();
			}
		});

		newMenu.getItems().addAll(newUser,newCat,newAut,newOeu,newEdi, newBok);
		leftBar.getMenus().add(newMenu);
	}


	public void maj(){
		if(Main.library.isAdmin()){
			newMenu.setVisible(true);
			for(MenuItem e : adminOnly)
				e.setVisible(true);
		}else{
			newMenu.setVisible(false);
			for(MenuItem e : adminOnly)
				e.setVisible(false);
		}

		hideRight();
        if(Main.library.isConnect()){
        	profile.setVisible(true);
        	disconnect.setVisible(true);
        }

        else{
        	connect.setVisible(true);
        	inscription.setVisible(true);
        }
	}


	public void majTheme(){
		if(tmpView == null)
			return;
		if(Main.darkMode)
			tmpView.setStyle("-fx-background-color: #252525");
		else
			tmpView.setStyle("-fx-background-color: #f3f3f3");

	}

	private void init(){
		usrv = new UserTable();
		catv = new CategoryTable();
		autv = new AuthorTable();
		oeuv = new OeuvreTable();
		ediv = new EditionTable();
		bokv = new BookTable();
		redv = new RedListTable();

		//profileView = new ProfileView();

		mainPane.getChildren().addAll(usrv,catv,autv,oeuv,ediv,bokv,redv);
		hideViews();

		leftBar = new MenuBar();
		rightBar = new MenuBar();

		connect = new Menu();
		profile = new Menu();
		disconnect = new Menu();
		inscription = new Menu();


		createConnect();
		createAffichage();
		createNouveau();
		createTheme();

		Region spacer = new Region();
		spacer.getStyleClass().add("menu-bar");
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		getChildren().addAll(leftBar, spacer, rightBar);
		maj();
	}

	private void hideRight(){
		connect.setVisible(false);
		profile.setVisible(false);
		disconnect.setVisible(false);
		inscription.setVisible(false);
	}

	private void hideViews(){
		if(tmpView != null){
			mainPane.getChildren().remove(tmpView);
			tmpView = null;
		}

		usrv.setVisible(false);
		catv.setVisible(false);
		autv.setVisible(false);
		oeuv.setVisible(false);
		ediv.setVisible(false);
		bokv.setVisible(false);
		redv.setVisible(false);
		//profileView.setVisible(false);

	}

	public void putTmpView(Node tmpView){
		hideViews();
		this.tmpView = tmpView;
		mainPane.getChildren().add(tmpView);
		majTheme();
	}

	private void putUserView(){
		hideViews();
		usrv.setVisible(true);
		usrv.refill();
	}

	private void putCatView(){
		hideViews();
		catv.setVisible(true);
		catv.refill();
	}

	private void putAutView(){
		hideViews();
		autv.setVisible(true);
		autv.refill();
	}

	private void putOeuView(){
		hideViews();
		oeuv.setVisible(true);
		oeuv.refill();
	}

	private void putEdiView(){
		hideViews();
		ediv.setVisible(true);
		ediv.refill();
	}

	private void putBokView(){
		hideViews();
		bokv.setVisible(true);
		bokv.refill();
	}

	private void putRedView(){
		hideViews();
		redv.setVisible(true);
		redv.refill();
	}

}