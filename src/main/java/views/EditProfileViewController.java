package views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

ViewTransitionalModelInterface model;

public class EditProfileViewController
{
//	StoreModel model;
//	
//	public void setModel(StoreModel newModel)
//	{
//		model = newModel;
//		
//		//bind label
//		StringConverter<Number> fmt = new NumberStringConverter();
//		
//		Bindings.bindBidirectional(cashBalanceLabel.textProperty(), model.getMoney(),fmt);
//	}
	
	@FXML
    private TextField bioTextField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label realnameLabel;

    @FXML
    private TextField realnameTextField;

    @FXML
    private ImageView userPicImageView;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    void onClickEsc(ActionEvent event) 
    {
    	//go back to group view
    }
}
