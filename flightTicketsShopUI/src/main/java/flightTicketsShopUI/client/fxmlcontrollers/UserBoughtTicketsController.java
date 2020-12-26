package flightTicketsShopUI.client.fxmlcontrollers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import flightTicketsShopUI.forms.BuyTicketForm;
import flightTicketsShopUI.utils.FxmlUtils;
import flightTicketsShopUI.utils.HttpUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Component
public class UserBoughtTicketsController {

	@FXML
	private TableView<BuyTicketForm> ticketsTV;

	@FXML
	public void initialize() {

		try {

			String url = "http://localhost:8762/ticketsservice/ticketsFromUser";
			ResponseEntity<String> response = HttpUtils.sendGetReturnString(url, HttpUtils.getTOKEN());

			ObjectMapper mapper = new ObjectMapper();
			System.out.println("*****" + response.getBody() + "*****");
			List<BuyTicketForm> tickets = mapper.readValue(response.getBody(),
					new TypeReference<List<BuyTicketForm>>() {
					});
			
			for(BuyTicketForm f : tickets) {
				f.findFlight();
				f.findUserName();
			}

			ticketsTV.setItems(FXCollections.observableArrayList(tickets));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void handleOk(ActionEvent event) {
		FxmlUtils.closeStage(event);
	}

	public TableView<BuyTicketForm> getTicketsTV() {
		return ticketsTV;
	}

	public void setTicketsTV(TableView<BuyTicketForm> ticketsTV) {
		this.ticketsTV = ticketsTV;
	}

}
