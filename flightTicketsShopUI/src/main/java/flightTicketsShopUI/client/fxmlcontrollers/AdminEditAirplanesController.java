package flightTicketsShopUI.client.fxmlcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flightTicketsShopUI.client.MainViewManager;
import javafx.event.ActionEvent;

@Component
public class AdminEditAirplanesController {

	@Autowired
	MainViewManager mainViewManager;
	
	
	

	public void handleEditAirplanes(ActionEvent event) {

		mainViewManager.changeRoot("adminEditAirplanes");

	}

	public void handleEditFlights(ActionEvent event) {

		mainViewManager.changeRoot("adminEditFlights");

	}

}
