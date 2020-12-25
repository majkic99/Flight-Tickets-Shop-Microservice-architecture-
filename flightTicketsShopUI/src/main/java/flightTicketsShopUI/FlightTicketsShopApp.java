package flightTicketsShopUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import flightTicketsShopUI.client.MainViewManager;

@SpringBootApplication
public class FlightTicketsShopApp extends Application {

	protected ConfigurableApplicationContext springContext;

	public static void main(String[] args) {
		launch(FlightTicketsShopApp.class);
	}

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(FlightTicketsShopApp.class);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		primaryStage.setTitle("Flight Tickets Shop");

		MainViewManager mainView = springContext.getBean(MainViewManager.class);
		primaryStage.setScene(mainView.createScene());
		primaryStage.getIcons().add(new Image(
				new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/images/icon.jpg"))));
		mainView.setMainStage(primaryStage);
		primaryStage.show();

	}

	@Override
	public void stop() throws Exception {
		springContext.close();
		Platform.exit();

	}

}