package Scenes;

import Database.DBConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import static css.css.confirmButton;

class ConfirmBox{

    private static boolean answer;
    private static final double WIDTH = 350, HEIGHT = 150;

    static boolean display(String title, String message){
        Label label = new Label();
        label.setText(message);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        //Create to users
        Button yesButton = new Button("Yes");
        yesButton.setStyle(confirmButton());

        Button noButton = new Button("No");
        noButton.setStyle(confirmButton());

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(yesButton, noButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20, 0,0,0));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.add(label, 0,0,2,1);
        grid.add(hbox,0,1, 2, 2);

        Scene scene = new Scene(grid);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        yesButton.setOnAction(e -> {
            answer = true;
            stage.close();
            Connection con = DBConnection.getCon();
            DBConnection.setLoggedIn(con, LogIn.getUserName(), 0);
            DBConnection.closeConnection(con);
        });
        noButton.setOnAction(e -> {
            answer = false;
            stage.close();
        });
        return answer;
    }
}
