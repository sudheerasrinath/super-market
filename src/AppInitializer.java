import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("lk/para/superMarket/view/LoginForm.fxml"))));
        primaryStage.setResizable(false);
        //primaryStage.getIcons().add(new Image("/Assets/shopping-cart_icon-icons.com_60593.png"));
        primaryStage.setTitle("Para Supermarket");
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
