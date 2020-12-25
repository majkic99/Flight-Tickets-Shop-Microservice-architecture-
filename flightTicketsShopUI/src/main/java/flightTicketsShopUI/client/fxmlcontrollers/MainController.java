package flightTicketsShopUI.client.fxmlcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.google.gson.Gson;

import flightTicketsShopUI.client.MainViewManager;
import flightTicketsShopUI.exceptions.BlankFieldsException;
import flightTicketsShopUI.exceptions.PasswordsDoNotMatchException;
import flightTicketsShopUI.exceptions.WrongDetailsException;
import flightTicketsShopUI.forms.LoginForm;
import flightTicketsShopUI.forms.NameSurnameForm;
import flightTicketsShopUI.forms.RegistrationForm;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@Component
public class MainController {

	@Autowired
	private MainViewManager mainViewManager;

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

	@FXML
	private TextField emailLoginTF;
	@FXML
	private PasswordField passwordLoginPF;

	public void handleRegister(ActionEvent event) {

		ResponseEntity<String> response = null;

		try {

			if (nameTF.getText().isEmpty() || surnameTF.getText().isEmpty() || emailTF.getText().isEmpty()
					|| passwordPF.getText().isEmpty() || repeatPasswordPF.getText().isEmpty()
					|| passportTF.getText().isEmpty()) {
				throw new BlankFieldsException();
			}

			if (passportTF.getText().equals(repeatPasswordPF.getText())) {
				throw new PasswordsDoNotMatchException();
			}

			String url = "http://localhost:8762/userservice/register";

			RegistrationForm body = new RegistrationForm(nameTF.getText(), surnameTF.getText(), emailTF.getText(),
					passwordPF.getText(), passportTF.getText());

			response = HttpUtils.sendPostReturnString(url, body, "");

			if (response == null)
				throw new RestClientException("response je null");

		} catch (BlankFieldsException e1) {

			e1.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("You need to fill all the fields.");
			alert.showAndWait();
			return;

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

		System.out.println(response.getBody());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Done");
		alert.setContentText("Registration complete. Check your email, then log in.");
		alert.showAndWait();

	}

	public void handleLogin(ActionEvent event) throws Exception {

		ResponseEntity<String> response = null;

		try {

			if (emailLoginTF.getText().isEmpty() || passwordLoginPF.getText().isEmpty())
				throw new BlankFieldsException();

			String url = "http://localhost:8762/userservice/login";

			LoginForm body = new LoginForm(emailLoginTF.getText(), passwordLoginPF.getText());

			response = HttpUtils.sendPostReturnString(url, body, "");

			if (response.getStatusCode() != HttpStatus.OK)
				throw new WrongDetailsException();

		} catch (BlankFieldsException e) {

			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("You need to fill all the fields.");
			alert.showAndWait();
			return;

		} catch (WrongDetailsException e2) {
			e2.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Wrong email or password.");
			alert.showAndWait();
			return;
		}

		String token = response.getHeaders().get("Authorization").get(0);

		try {
			HttpUtils.setTOKEN(token);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResponseEntity<String> responseAdmin = HttpUtils
				.sendGetReturnString("http://localhost:8762/userservice/isAdmin", token);
		if (responseAdmin.getBody().equals("YES ADMIN")) {

			HttpUtils.setIS_ADMIN(true);

			mainViewManager.changeRoot("admin");

		} else if (responseAdmin.getBody().equals("NO ADMIN")) {

			HttpUtils.setIS_ADMIN(false);

			ResponseEntity<String> responseName = HttpUtils
					.sendGetReturnString("http://localhost:8762/userservice/whoAmI", token);

			Gson gson = new Gson();
			NameSurnameForm form = gson.fromJson(responseName.getBody(), NameSurnameForm.class);

			HttpUtils.setUSER_NAME(form.getIme() + " " + form.getPrezime());

			mainViewManager.changeRoot("user");

		} else {
			throw new Exception();
		}

	}

}
