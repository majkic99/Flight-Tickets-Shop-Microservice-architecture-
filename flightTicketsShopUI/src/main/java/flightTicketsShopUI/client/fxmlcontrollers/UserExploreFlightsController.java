package flightTicketsShopUI.client.fxmlcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flightTicketsShopUI.client.MainViewManager;
import javafx.event.ActionEvent;

@Component
public class UserExploreFlightsController {

	
	
	
	@Autowired
	MainViewManager mainViewManager;
	
	
	
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
}
