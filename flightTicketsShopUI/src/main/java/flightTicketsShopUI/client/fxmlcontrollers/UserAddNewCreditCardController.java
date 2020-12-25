package flightTicketsShopUI.client.fxmlcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import flightTicketsShopUI.exceptions.BlankFieldsException;
import flightTicketsShopUI.forms.CreditCardForm;
import flightTicketsShopUI.utils.FxmlUtils;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@Component
public class UserAddNewCreditCardController {

	@Autowired
	private UserViewCreditCardsController userViewCreditCardsController;

	@FXML
	private TextField nameTF;
	@FXML
	private TextField surnameTF;
	@FXML
	private TextField cardNumberTF;
	@FXML
	private PasswordField cvcPF;

	public void handleSave(ActionEvent event) {

		try {
			if (nameTF.getText().isEmpty() || surnameTF.getText().isEmpty() || cardNumberTF.getText().isEmpty()
					|| cvcPF.getText().isEmpty()) {
				throw new BlankFieldsException();
			}

			CreditCardForm form = new CreditCardForm(nameTF.getText(), surnameTF.getText(), cardNumberTF.getText(),
					Integer.parseInt(cvcPF.getText()));
			
			String url = "http://localhost:8762/userservice/addcc";
			
			
			ResponseEntity<Integer> response = HttpUtils.sendPostReturnInteger(url, form, HttpUtils.getTOKEN());
			
			if(response.getStatusCode() == HttpStatus.ACCEPTED) {
				
				form.setId(response.getBody());
				TableView<CreditCardForm> tv = userViewCreditCardsController.getCardsTV();
				
				List<CreditCardForm> list = tv.getItems();
				list.add(form);
				tv.setItems(FXCollections.observableArrayList(list));
				
			} else {
				throw new Exception();
			}
			

		} catch (BlankFieldsException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("You need to fill all the fields.");
			alert.showAndWait();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Unknown error.");
			alert.showAndWait();
			return;
		}
		FxmlUtils.closeStage(event);

	}

}
