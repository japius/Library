import java.sql.*; 

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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
        library.addUser("Pierre", "MEJANE", "pierre.mejane@student-cs.fr", "pass", 1);
        library.addUser("Pete", "MEJ", "pete.mej@student-cs.fr", "pass", 1);
        library.addUser("JM", "MENE", "pierjane@student-cs.fr", "pass", 1);
        library.addUser("Pieautre", "MEJvdsfeANE", "pierre.mejane@stsfsefsudent-cs.fr", "pass", 1);
        library.addCategory("Categorie ultime", 50, 20);
        library.addUser("Pieautre", "MEJvdffsfeANE", "pierre.mejane@stsfmailt-cs.fr", "pass", 3);
        System.exit(0);
    }

    // Graphics version below

    public static void graphicVersion(String[] args){
        launch(args);
    }

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        /*Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                mainDatabase.testSelect("utilisateur");
            }
        });
        */
        UserTable table = new UserTable();
        StackPane root = new StackPane();
        root.getChildren().add(table);

        primaryStage.setTitle("Bibliotheque de la mort");
        primaryStage.setScene(new Scene(root, 700, 700));
        primaryStage.show();
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        library.close();

    }
}