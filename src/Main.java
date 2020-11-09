import java.sql.*; 

import java.util.ArrayList;
import java.util.Date;

import jfxtras.styles.jmetro.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.css.*;
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
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class Main extends Application{

    static Library library;
    static MyMenuBar menubar;
    static boolean noGraphic = false;
    static boolean darkMode;
    private static Scene scene;
	
	public static void main(String[] args) throws SQLException{

        if(args.length < 2){
            System.out.println("Veuillez donner en argument l'username pour la base de donnée et le mot de passe");
        }

        System.out.println(args[0]);
        library = new Library(args[0], args[1]);

        if(noGraphic)
            noGraphicVersion(args);
        else
            graphicVersion(args);

	}


    public static void noGraphicVersion(String[] args) throws SQLException{
        Date d = new Date();
        System.out.println(d);
        System.exit(0);
    }

    // Graphics version below

    public static void graphicVersion(String[] args){
        launch(args);
    }

	@Override
    public void start(Stage primaryStage) {
        //Stage primaryStage = primaryStage();
        /*Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                mainDatabase.testSelect("utilisateur");
            }
        });
        */
        MyTableView table = new CategoryTable();

        BorderPane rootAndBar = new BorderPane();
        StackPane root = new StackPane();

        menubar = new MyMenuBar(root);

        rootAndBar.setTop(menubar);
        rootAndBar.setCenter(root);

        menubar.putTmpView(new ConnectView());

        primaryStage.setTitle("Bibliotheque de la mort");
        scene = new Scene(rootAndBar, 1000, 700);
        changeTheme(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void changeTheme(boolean dark){
        darkMode = dark;
        if(darkMode){
            new JMetro(scene, Style.DARK);
        }
        else
            new JMetro(scene, Style.LIGHT);

        menubar.majTheme();
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        library.close();

    }
}