package flightTicketsShopUI.client.fxmlcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import flightTicketsShopUI.client.MainViewManager;
import flightTicketsShopUI.forms.UserViewForm;
import flightTicketsShopUI.utils.FxmlUtils;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class UserViewProfileController {

	@Autowired
	private MainViewManager mainViewManager;
	
	@FXML
	private Label nameLBL;
	@FXML
	private Label surnameLBL;
	@FXML
	private Label emailLBL;
	@FXML
	private Label traveledLBL;
	@FXML
	private Label passportLBL;
	@FXML
	private Label userStatusLBL;
	@FXML
	private Label rankLBL;
	
	
	@FXML
	public void initialize() {
		
		try {
			ResponseEntity<String> responseUser = HttpUtils
					.sendGetReturnString("http://localhost:8762/userservice/whoAmIAll", HttpUtils.getTOKEN());
			
			
			if(responseUser.getStatusCode() == HttpStatus.ACCEPTED) {
				
				Gson gson = new Gson();
				UserViewForm form = gson.fromJson(responseUser.getBody(), UserViewForm.class);
				nameLBL.setText(form.getName());
				surnameLBL.setText(form.getSurname());
				emailLBL.setText(form.getEmail());
				traveledLBL.setText(String.valueOf(form.getTraveled()));
				passportLBL.setText(form.getPassportNumber());
				userStatusLBL.setText("Customer");
				rankLBL.setText(form.getRank());
				
			} else {
				throw new Exception();
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
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
