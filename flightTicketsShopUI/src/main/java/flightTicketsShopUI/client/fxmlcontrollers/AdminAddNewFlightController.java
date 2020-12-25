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
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import flightTicketsShopUI.exceptions.BlankFieldsException;
import flightTicketsShopUI.forms.NewAirplaneForm;
import flightTicketsShopUI.forms.NewFlightForm;
import flightTicketsShopUI.utils.FxmlUtils;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@Component
public class AdminAddNewFlightController {
	
	@Autowired
	AdminEditFlightsController adminEditFlightsController;

	@FXML
	TextField startTF;
	@FXML
	TextField endTF;
	@FXML
	TextField lengthTF;
	@FXML
	TextField priceTF;
	@FXML
	ComboBox<NewAirplaneForm> airplanesCB;

	@FXML
	public void initialize() {

		ResponseEntity<String> response = HttpUtils.sendGetReturnString("http://localhost:8762/flightservice/airplanes",
				"");

		if (response.getStatusCode() == HttpStatus.ACCEPTED) {
			ObjectMapper mapper = new ObjectMapper();
			List<NewAirplaneForm> formList;
			try {

				formList = mapper.readValue(response.getBody(), new TypeReference<List<NewAirplaneForm>>() {
				});

				airplanesCB.setItems(FXCollections.observableArrayList(formList));

			} catch (JsonMappingException e) {
				e.printStackTrace();
				return;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return;
			}

		}

	}

	public void handleSave(ActionEvent event) {

		try {

			if (startTF.getText().isEmpty() || endTF.getText().isEmpty() || lengthTF.getText().isEmpty()
					|| priceTF.getText().isEmpty() || airplanesCB.getSelectionModel().getSelectedItem() == null) {
				throw new BlankFieldsException();
			}

			NewFlightForm flight = new NewFlightForm(startTF.getText(), endTF.getText(),
					Integer.parseInt(lengthTF.getText()), Integer.parseInt(priceTF.getText()), false,
					airplanesCB.getSelectionModel().getSelectedItem());

			JsonObject o = new JsonObject();
			o.add("airplaneID", new JsonPrimitive(flight.getAirplane().getId()));
			o.add("start", new JsonPrimitive(flight.getStart()));
			o.add("end", new JsonPrimitive(flight.getEnd()));
			o.add("lengthinKM", new JsonPrimitive(flight.getLengthinKM()));
			o.add("price", new JsonPrimitive(flight.getPrice()));

			ResponseEntity<String> response = HttpUtils.sendPostReturnString(
					"http://localhost:8762/flightservice/addFlight", o.toString(), HttpUtils.getTOKEN());
			
			if(response.getStatusCode() == HttpStatus.ACCEPTED) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Done");
				alert.setContentText("The flight is saved.");
				alert.showAndWait();
			} else {
				throw new Exception();
			}

		} catch (BlankFieldsException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("You need to fill all the fields.");
			alert.showAndWait();
			return;
		} catch (Exception e2) {
			e2.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Unknown error.");
			alert.showAndWait();
			return;
		}
		FxmlUtils.closeStage(event);
		adminEditFlightsController.initialize();
		
	}

}
