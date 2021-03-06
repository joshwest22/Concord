package mainapplication;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import backend.Client;
import backend.ClientSubstitute;
import backend.Server;
import backend.ServerInterface;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.LoginController;


public class Main extends Application 
{

    @Override
    public void start(Stage primaryStage) throws Exception {
    	//Server myServer = new Server();
		//Registry registry = LocateRegistry.createRegistry(1099);
		//registry.rebind("SERVER", myServer);
		//ServerInterface theServer = (ServerInterface) Naming.lookup("rmi://127.0.0.1/SERVER");
		//Client newClient = new Client(theServer);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/LoginView.fxml"));
		BorderPane view = loader.load();
		LoginController cont = loader.getController();
		ClientSubstitute myClient = new ClientSubstitute("TestName","TestPass");
		myClient.addGroupID(12);
		myClient.addGroupID(5);
		ViewTransitionalModel vm =new ViewTransitionalModel(view,myClient); 
	    cont.setModel(vm);
	   // vm.showMainView();
	    
	    
	    Scene s = new Scene(view);
	    primaryStage.setScene(s);
	    primaryStage.show();	
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
