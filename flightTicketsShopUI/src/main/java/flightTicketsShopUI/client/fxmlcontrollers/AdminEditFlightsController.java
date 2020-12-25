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
import flightTicketsShopUI.forms.NewFlightForm;
import flightTicketsShopUI.utils.FxmlUtils;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

@Component
public class AdminEditFlightsController {

	@Autowired
	MainViewManager mainViewManager;

	@FXML
	TableView<NewFlightForm> flightsTV;

	@FXML
	public void initialize() {

		ResponseEntity<String> response = HttpUtils.sendGetReturnString("http://localhost:8762/flightservice/flights",
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

	}

	public void handleAddNewFlight(ActionEvent event) {
		mainViewManager.openModal("adminAddNewFlight");
	}

	public void handleRemoveSelectedFlight(ActionEvent event) {

		try {

			NewFlightForm flight = flightsTV.getSelectionModel().getSelectedItem();
			if (flight == null)
				throw new NotSelectedException();

			ResponseEntity<String> response = HttpUtils.sendGetReturnString(
					"http://localhost:8762/flightservice/deleteFlight/" + flight.getId(), HttpUtils.getTOKEN());

			if (response.getStatusCode() == HttpStatus.ACCEPTED) {
				List<NewFlightForm> list = flightsTV.getItems();
				list.remove(flight);
				flightsTV.setItems(FXCollections.observableArrayList(list));
				return;
			}
			
			throw new RuntimeException();

		} catch (NotSelectedException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("You need to select a flight first.");
			alert.showAndWait();
			return;
		} catch (RuntimeException e1) {
			e1.printStackTrace();
			return;
		}

	}

	public void handleEditAirplanes(ActionEvent event) {

		mainViewManager.changeRoot("adminEditAirplanes");

	}

	public void handleEditFlights(ActionEvent event) {

		mainViewManager.changeRoot("adminEditFlights");

	}

	public void handleLogOut(ActionEvent event) {

		FxmlUtils.logOut(mainViewManager);

	}
}
