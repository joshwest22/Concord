package view;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mainapplication.ViewTransitionalModelInterface;

public class RolePermissionsViewController
{
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
    private Label channelNameLabel;

    @FXML
    void onClickOverviewButton(ActionEvent event) {

    }

    @FXML
    void onClickPermissionsButton(ActionEvent event) {

    }

    @FXML
    void onLockChannelButtonToggled(ActionEvent event) {

    }
}
