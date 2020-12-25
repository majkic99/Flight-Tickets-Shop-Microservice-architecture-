package flightTicketsShopUI.client.fxmlcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import flightTicketsShopUI.client.MainViewManager;
import flightTicketsShopUI.exceptions.PasswordsDoNotMatchException;
import flightTicketsShopUI.forms.RegistrationForm;
import flightTicketsShopUI.utils.FxmlUtils;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@Component
public class UserEditProfileController {

	@Autowired
	MainViewManager mainViewManager;
	
	@FXML
	private TextField nameTF;
	@FXML
	private TextField surnameTF;
	@FXML
	private TextField emailTF;
	@FXML
	private PasswordField passwordPF;
	@FXML
	private PasswordField repeatPasswordPF;
	@FXML
	private TextField passportTF;
	
	public void handleSave(ActionEvent event) {
		
		ResponseEntity<String> response = null;

		try {

			if (!passwordPF.getText().equals(repeatPasswordPF.getText())) {
				throw new PasswordsDoNotMatchException();
			}

			String url = "http://localhost:8762/userservice/updateUser";
			
			String name = nameTF.getText().isEmpty() ? null : nameTF.getText();
			String surname = surnameTF.getText().isEmpty() ? null : surnameTF.getText();
			String email = emailTF.getText().isEmpty() ? null : emailTF.getText();
			String password = passwordPF.getText().isEmpty() ? null : passwordPF.getText();
			String passport = passportTF.getText().isEmpty() ? null : passportTF.getText();

			RegistrationForm body = new RegistrationForm(name, surname, email, password, passport);

			response = HttpUtils.sendPutReturnString(url, body, HttpUtils.getTOKEN());

			if (response == null)
				throw new RestClientException("response je null");

		} catch (PasswordsDoNotMatchException e2) {
			e2.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Passwords do not match.");
			alert.showAndWait();
			return;
		} catch (RestClientException e3) {
			e3.printStackTrace();
			return;
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Done");
		alert.setContentText("Edit complete.");
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
}
