package mainapplication;

import concord.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.LoginController;


public class Main extends Application 
{

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
    	//attempting to set custom icon in taskbar
    	//primaryStage.getIcons().add(new Image("file:src/main/java/concord/concordLogo.png"));
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/LoginView.fxml"));
		BorderPane view = loader.load();
		LoginController cont = loader.getController();
		Client myClient = new Client("TestName","TestPass");
		myClient.addGroupID(12);
		myClient.addGroupID(5);
		ViewTransitionalModel vm =new ViewTransitionalModel(view,myClient,primaryStage); 
	    cont.setModel(vm);
	   // vm.showMainView();
	    
	    
	    Scene s = new Scene(view);
	    primaryStage.setScene(s);
	    primaryStage.show();
	    
	    //handle close clicked BUT JavaFX program still running in background	    
	    primaryStage.setOnCloseRequest(e->
	    {
	    	Platform.exit();
	    	primaryStage.close();
	    });
    }


	public static void main(String[] args) {
        Application.launch(args);
    }
}
