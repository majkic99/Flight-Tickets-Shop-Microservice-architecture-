package flightTicketsShopUI.client.fxmlcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import flightTicketsShopUI.exceptions.BlankFieldsException;
import flightTicketsShopUI.forms.NewAirplaneForm;
import flightTicketsShopUI.utils.FxmlUtils;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@Component
public class AdminAddNewAirplaneController {
	
	@Autowired
	AdminEditAirplanesController adminEditAirplanesController;
	

	@FXML
	private TextField nameTF;
	@FXML
	private TextField capacityTF;

	public void handleSave(ActionEvent event) {
		try {

			if (nameTF.getText().isEmpty() || capacityTF.getText().isEmpty()) {
				throw new BlankFieldsException();
			}

			NewAirplaneForm airplane = new NewAirplaneForm(nameTF.getText(), Integer.parseInt(capacityTF.getText()));

			JsonObject o = new JsonObject();
			o.add("name", new JsonPrimitive(airplane.getName()));
			o.add("capacity", new JsonPrimitive(airplane.getCapacity()));

			ResponseEntity<String> response = HttpUtils.sendPostReturnString(
					"http://localhost:8762/flightservice/addAirplane", o.toString(), HttpUtils.getTOKEN());

			if (response.getStatusCode() == HttpStatus.ACCEPTED) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Done");
				alert.setContentText("The airplane is saved.");
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
		adminEditAirplanesController.initialize();
	}

}
