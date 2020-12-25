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
import flightTicketsShopUI.exceptions.UsedAirplaneException;
import flightTicketsShopUI.forms.NewAirplaneForm;
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
public class AdminEditAirplanesController {

	@Autowired
	MainViewManager mainViewManager;

	@FXML
	TableView<NewAirplaneForm> airplanesTV;

	@FXML
	public void initialize() {

		ResponseEntity<String> response = HttpUtils.sendGetReturnString("http://localhost:8762/flightservice/airplanes",
				"");

		if (response.getStatusCode() == HttpStatus.ACCEPTED) {

			ObjectMapper mapper = new ObjectMapper();
			try {
				List<NewAirplaneForm> formList = mapper.readValue(response.getBody(),
						new TypeReference<List<NewAirplaneForm>>() {
						});
				airplanesTV.setItems(FXCollections.observableArrayList(formList));

			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		}

	}

	public void handleAddNewAirplane(ActionEvent event) {
		mainViewManager.openModal("adminAddNewAirplane");
	}

	public void handleRemoveSelectedAirplane(ActionEvent event) {
		
		try {

			NewAirplaneForm airplane = airplanesTV.getSelectionModel().getSelectedItem();
			if (airplane == null)
				throw new NotSelectedException();
			
			ResponseEntity<String> responseFlights = HttpUtils.sendGetReturnString("http://localhost:8762/flightservice/flights", "");
			
			ObjectMapper mapper = new ObjectMapper();
			List<NewFlightForm> forms = mapper.readValue(responseFlights.getBody(), new TypeReference<List<NewFlightForm>>() {});
			
			for(NewFlightForm f : forms) {
				if(f.getAirplane().getId() == airplane.getId())
					throw new UsedAirplaneException();
			}
			
			ResponseEntity<String> responseDelete = HttpUtils.sendGetReturnString(
					"http://localhost:8762/flightservice/deleteAirplane/" + airplane.getId(), HttpUtils.getTOKEN());

			if (responseDelete.getStatusCode() == HttpStatus.ACCEPTED) {
				List<NewAirplaneForm> list = airplanesTV.getItems();
				list.remove(airplane);
				airplanesTV.setItems(FXCollections.observableArrayList(list));
				return;
			}

			throw new RuntimeException();

		} catch (NotSelectedException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("You need to select a flight first.");
			alert.showAndWait();
			return;
		}catch(UsedAirplaneException e2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Selected airplane is already being used by a flight.");
			alert.showAndWait();
			return;
			
		}catch (RuntimeException e1) {
			e1.printStackTrace();
			return;
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
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
