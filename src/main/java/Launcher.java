import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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

        // Create Canvas
        var canvas = new Canvas();
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());
        root.getChildren().add(canvas);

        // Initialize the game
        var state = new WorldState();
        var renderer = new Renderer(state);
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                renderer.update(canvas);
            }
        }.start();
        var player = new Player();
        state.grid.getSquare(0,0).addChild(player);
        state.player = player;

        // Add scroll wheel handler
        canvas.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            float zoomModifier = (float) (deltaY > 0 ? .10 : -.10);
            state.cameraZoom += zoomModifier;
            if(state.cameraZoom <= 0)
                state.cameraZoom = 0.1f;
            renderer.update(canvas);
        });

    }

    public static void main(String[] args){
        launch(args);
    }
}
