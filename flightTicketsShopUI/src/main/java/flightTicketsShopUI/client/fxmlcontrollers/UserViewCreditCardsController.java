package flightTicketsShopUI.client.fxmlcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import flightTicketsShopUI.client.MainViewManager;
import flightTicketsShopUI.exceptions.NotSelectedException;
import flightTicketsShopUI.forms.CreditCardForm;
import flightTicketsShopUI.utils.FxmlUtils;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

@Component
public class UserViewCreditCardsController {

	@Autowired
	MainViewManager mainViewManager;

	@FXML
	private TableView<CreditCardForm> cardsTV;

	@FXML
	public void initialize() {

		try {
			ResponseEntity<String> response = HttpUtils.sendGetReturnString("http://localhost:8080/creditcardsWithCVC", HttpUtils.getTOKEN());
			
			if(response.getStatusCode() == HttpStatus.ACCEPTED) {
				
				ObjectMapper mapper = new ObjectMapper();
				List<CreditCardForm> list = mapper.readValue(response.getBody(), new TypeReference<List<CreditCardForm>>() {});
				cardsTV.setItems(FXCollections.observableArrayList(list));
				
				
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

	public void handleAddNew(ActionEvent event) {
		mainViewManager.openModal("userAddNewCreditCard");
	}

	public void handleDelete(ActionEvent event) {
		
		try {
			
			if(cardsTV.getSelectionModel().getSelectedItem() == null)
				throw new NotSelectedException();
			
			CreditCardForm form = cardsTV.getSelectionModel().getSelectedItem();
			String url = "http://localhost:8762/userservice/deletecc/" + form.getId();
			HttpUtils.sendGetReturnString(url, HttpUtils.getTOKEN());
			
			List<CreditCardForm> list = cardsTV.getItems();
			list.remove(form);
			cardsTV.setItems(FXCollections.observableArrayList(list));
			
			
		} catch (NotSelectedException e){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("You need to select a credit card first.");
			alert.showAndWait();
			return;
		}
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Done");
		alert.setContentText("Credit card is deleted.");
		alert.showAndWait();
		
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

	public TableView<CreditCardForm> getCardsTV() {
		return cardsTV;
	}

	public void setCardsTV(TableView<CreditCardForm> cardsTV) {
		this.cardsTV = cardsTV;
	}
}
