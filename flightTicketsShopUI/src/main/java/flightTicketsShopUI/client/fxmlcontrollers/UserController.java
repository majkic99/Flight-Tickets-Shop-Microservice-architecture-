package flightTicketsShopUI.client.fxmlcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flightTicketsShopUI.client.MainViewManager;
import flightTicketsShopUI.utils.FxmlUtils;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class UserController {

	@Autowired
	private MainViewManager mainViewManager;

	@FXML
	private Label welcomeLBL;
	@FXML
	private Label statusLBL;

	@FXML
	public void initialize() {

		welcomeLBL.setText(welcomeLBL.getText() + HttpUtils.getUSER_NAME());

		String status = HttpUtils.isIS_ADMIN() ? "ADMIN" : "CUSTOMER";

		statusLBL.setText(statusLBL.getText() + status);

	}

	public void handleViewProfile(ActionEvent event) {

		mainViewManager.changeRoot("userViewProfile");

	}

	public void handleEditProfile(ActionEvent event) {

		mainViewManager.changeRoot("userEditProfile");

	}

	public void handleViewCreditCards(ActionEvent event) {

		mainViewManager.changeRoot("userViewCreditCards");

	}

	public void handleExploreFlights(ActionEvent event) {

		mainViewManager.changeRoot("userExploreFlights");

	}
	
	public void handleLogOut(ActionEvent event) {
		
		FxmlUtils.logOut(mainViewManager);
		
	}
 
}
