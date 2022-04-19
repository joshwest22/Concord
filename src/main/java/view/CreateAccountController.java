package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mainapplication.ViewTransitionalModelInterface;

public class CreateAccountController {
	
	ViewTransitionalModelInterface model;

    @FXML
    private Button createAccountButton;

    @FXML
    private TextField createAccountPassword;

    @FXML
    private TextField createAccountUsername;
    
    @FXML
    private Label createAccountLabel;
    
    @FXML
    private TextField realNameTextField;
    
    public void setModel(ViewTransitionalModelInterface model)
    {
    	this.model = model;
    }

    @FXML
    void onClickSignUp(ActionEvent event) 
    {
    	//Perform any needed validation checks here
    	String newUsername = this.createAccountPassword.getText();
    	String realName = this.realNameTextField.getText();
    	String newPassword = this.createAccountPassword.getText();
    	if((newUsername.length()==0)||(newPassword.length()==0)||(realName.length()==0))
    	{
    		this.createAccountLabel.setText("One or more field empty, try again");
    	}
    	else
    	{
    		//this.model.getClientModel().createAccount();
    		//Then, we show the login view, as every user must login after making account
        	this.model.showLoginView();
    	}
    }

}
