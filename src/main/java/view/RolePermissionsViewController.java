package view;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

ViewTransitionalModelInterface model;

public class RolePermissionsViewController
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
