package flightTicketsShopUI.client.fxmlcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import flightTicketsShopUI.client.MainViewManager;
import flightTicketsShopUI.exceptions.NotSelectedException;
import flightTicketsShopUI.forms.BuyTicketRequestForm;
import flightTicketsShopUI.forms.CreditCardForm;
import flightTicketsShopUI.forms.NewFlightForm;
import flightTicketsShopUI.utils.FxmlUtils;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

@Component
public class UserExploreFlightsController {

	@Autowired
	private MainViewManager mainViewManager;

	@FXML
	private TableView<NewFlightForm> flightsTV;

	@FXML
	private ComboBox<CreditCardForm> creditCardsCB;
	
	private int currPage = 0;

	@FXML
	public void initialize() {

		
		String url = "http://localhost:8762/flightservice/flights/" + this.getCurrPage() + "/5";
		
		ResponseEntity<String> response = HttpUtils.sendGetReturnString(url,
				"");

		if (response.getStatusCode() == HttpStatus.ACCEPTED) {

			ObjectMapper mapper = new ObjectMapper();
			try {
				List<NewFlightForm> formList = mapper.readValue(response.getBody(),
						new TypeReference<List<NewFlightForm>>() {
						});
				flightsTV.setItems(FXCollections.observableArrayList(formList));

			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		}

		try {
			ResponseEntity<String> responseCC = HttpUtils
					.sendGetReturnString("http://localhost:8080/creditcardsWithCVC", HttpUtils.getTOKEN());

			if (responseCC.getStatusCode() == HttpStatus.ACCEPTED) {

				ObjectMapper mapper = new ObjectMapper();
				List<CreditCardForm> list = mapper.readValue(responseCC.getBody(),
						new TypeReference<List<CreditCardForm>>() {
						});
				creditCardsCB.setItems(FXCollections.observableArrayList(list));

			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Unknown error.");
			alert.showAndWait();
			return;
		}

	}

	public void handleBuyTicket(ActionEvent event) {

		try {

			if (flightsTV.getSelectionModel().getSelectedItem() == null
					|| creditCardsCB.getSelectionModel().getSelectedItem() == null) {
				throw new NotSelectedException();
			}

			NewFlightForm flight = flightsTV.getSelectionModel().getSelectedItem();
			CreditCardForm card = creditCardsCB.getSelectionModel().getSelectedItem();

			BuyTicketRequestForm ticketRequest = new BuyTicketRequestForm(flight.getId(), card.getId());

			String url = "http://localhost:8762/ticketsservice/buyTicket";

			ResponseEntity<String> response = HttpUtils.sendPostReturnString(url, ticketRequest, HttpUtils.getTOKEN());

			if (response.getStatusCode() == HttpStatus.ACCEPTED) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Done");
				alert.setContentText(
						"Ticket is added to your list. Congratualtions, have a pleasurable and safe flight!");
				alert.showAndWait();
				return;

			} else {
				throw new Exception();
			}

		} catch (NotSelectedException e1) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Please select credit card and flight (from the table).");
			alert.showAndWait();
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void handleViewTickets(ActionEvent event) {
		mainViewManager.openModal("userBoughtTickets");
	}

	public void handleLeft(ActionEvent event) {
		
		this.setCurrPage(getCurrPage()-1);
		if(this.getCurrPage() < 0) {
			this.setCurrPage(0);
		}
		System.out.println(getCurrPage());
		this.initialize();
	}

	public void handleRight(ActionEvent event) {
		this.setCurrPage(getCurrPage()+1);
		System.out.println(getCurrPage());
		this.initialize();
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

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	
	
}
