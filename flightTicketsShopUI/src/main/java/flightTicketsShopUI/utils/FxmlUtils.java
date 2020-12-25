package flightTicketsShopUI.utils;


import flightTicketsShopUI.client.MainViewManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class FxmlUtils {
	
	
	public static void logOut(MainViewManager mainViewManager) {
		
		
		HttpUtils.setIS_ADMIN(false);
		HttpUtils.setTOKEN(null);
		HttpUtils.setUSER_NAME(null);
		mainViewManager.changeRoot("main");
		
	}

	public static void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
	
}
