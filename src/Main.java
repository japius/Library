import java.sql.*; 

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
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class Main extends Application{

    static Library library;
    static boolean noGraphic = false;
	
	public static void main(String[] args) throws SQLException{
		library = new Library();

        if(noGraphic)
            noGraphicVersion(args);
        else
            graphicVersion(args);

	}


    public static void noGraphicVersion(String[] args) throws SQLException{
        /*ArrayList<User> users = User.getAllUsers(mainDatabase);
        for(User e : users){
            System.out.println("------");
            e.print();
        }
        System.out.println("-------------");
        ArrayList<Book> books = Book.getAllBooks(mainDatabase);
        for(Book e : books){
            System.out.println("------");
            e.print();
        }*/
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

        VBox rootAndBar = new VBox();
        StackPane root = new StackPane();

        MyMenuBar menubar = new MyMenuBar(root);

        rootAndBar.getChildren().add(menubar);
        rootAndBar.getChildren().add(root);

        primaryStage.setTitle("Bibliotheque de la mort");
        primaryStage.setScene(new Scene(rootAndBar, 700, 700));
        primaryStage.show();
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        library.close();

    }
}