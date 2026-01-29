package Geonauts;

import Geonauts.Entities.Player;
import Items.PoisonSword;
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
        var level = new LevelState();
        var camera = new CameraState();
        var renderer = new Renderer(level, camera, canvas);
        new InputHandler(renderer);
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                level.tick();
                renderer.update();
            }
        }.start();
        // Spawn a player
        var player = new Player();
        level.grid.getSquare(0,0).addChild(player);
        level.player = player;
        var square = level.grid.getSquare(0, 1);
        level.grid.getSquare(0, 1).addChild(new PoisonSword());
        System.out.println("Here");

        // Add scroll wheel handler
        canvas.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            float zoomModifier = (float) (deltaY > 0 ? .10 : -.10);
            camera.cameraZoom += zoomModifier;
            if(camera.cameraZoom <= 0)
                camera.cameraZoom = 0.1f;
            renderer.update();
        });

    }

    public static void main(String[] args){
        launch(args);
    }
}
