import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Launcher extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        // Instantiate application
        VBox root = new VBox();
        var scene = new Scene(root, (double) 1920/2, (double) 1080/2);
        stage.setScene(scene);
        stage.show();

        var state = new WorldState();
    }

    public static void main(String[] args){
        launch(args);
    }
}
