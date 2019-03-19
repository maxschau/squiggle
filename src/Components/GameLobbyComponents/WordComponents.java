package Components.GameLobbyComponents;

import Components.UserInfo;
import Database.DBConnection;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class WordComponents {
    private static String word = generateWord();

    public static HBox addWordUI(){
        HBox hb = new HBox();
        Label word = new Label("Word: " + showWord());
        word.setFont(new Font(20));
        hb.getChildren().add(word);
        return hb;
    }

    public static String getWord(){
        return word;
    }

    public static String generateWord(){
        return DBConnection.getRandomWord();
    }

    public static String showWord(){
        String line = "___  ";
        String space = "   ";
        String period = ".";
        String result ="";
        boolean drawing = UserInfo.getDrawing();
        String word = getWord();
        if(drawing){
            return word;
        }else{
            for(int i = 0; i < word.length(); i++){
                char letter = word.charAt(i);
                if(letter != ' ') {
                    result += line;
                }else if(letter == '.'){
                    result += period;
                }else{
                    result += space;
                }
            }
            return result;
        }
    }
}
