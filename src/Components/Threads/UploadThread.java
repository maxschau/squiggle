package Components.Threads;

import Components.GameLobbyComponents.CanvasComponents;
import Database.DBConnection;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.image.WritableImage;

import java.util.concurrent.CountDownLatch;

import static Components.GameLobbyComponents.CanvasComponents.canvasSnapshot;

public class UploadThread {


    // Method that uploads an updated version of drawing to DB
    public static void updateImage() {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    WritableImage wim = canvasSnapshot(CanvasComponents.canvas);
                                    byte[] blob = CanvasComponents.imageToByte(wim);
                                    DBConnection.updateImage(blob);
                                }finally{
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();
                        //Keep with the background work
                        return null;
                    }
                };
            }
        };
        service.start();
    }
}