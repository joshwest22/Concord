package view;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import mainapplication.ViewTransitionalModelInterface;


public class EditChannelViewController {
	ViewTransitionalModelInterface model;
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
    private Button PermissionsButton;

    @FXML
    private Label channelNameLabel;

    @FXML
    private TextField editChannelNameTextField;

    @FXML
    void onClickPermissionsButton(ActionEvent event) 
    {
    	//switch to permission view
    }

    @FXML
    void onDeleteChannelClicked(ActionEvent event) 
    {
    	//delete channel from group
    }

}
