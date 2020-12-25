package flightTicketsShopUI.client.fxmlcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flightTicketsShopUI.client.MainViewManager;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class AdminController {

	@Autowired
	MainViewManager mainViewManager;

	@FXML
	private Label welcomeLBL;
	@FXML
	private Label statusLBL;

	@FXML
	public void initialize() {

		welcomeLBL.setText(welcomeLBL.getText());

		String status = HttpUtils.isIS_ADMIN() ? "ADMIN" : "CUSTOMER";

		statusLBL.setText(statusLBL.getText() + status);

	}

	public void handleEditAirplanes(ActionEvent event) {

		mainViewManager.changeRoot("adminEditAirplanes");

	}

	public void handleEditFlights(ActionEvent event) {

		mainViewManager.changeRoot("adminEditFlights");

	}

}
