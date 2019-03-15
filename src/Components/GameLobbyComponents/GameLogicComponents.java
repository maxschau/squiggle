package Components.GameLobbyComponents;

import Components.UserInfo;
import Database.DBConnection;
import Scenes.GameLobby;

import static Components.GameLobbyComponents.CanvasComponents.*;

public class GameLogicComponents {

    public static void setPrivileges() {
        boolean drawing = UserInfo.getDrawing();
        swapCanvas(drawing);
    }

    public static void swapCanvas(boolean b) {
        if (b) {
            GameLobby.bp.setBottom(addDrawingUI());
            turnOfTimer();
            timer2(); // might be removed
        } else {
            GameLobby.bp.setBottom(null);
            turnOfTimer2(); // might be removed
            timer();
        }
    }

    public static void reset(){
        //DBConnection.setNewDrawer();


        //Update userInfo for drawer();

        //New canvas
        GameLobby.bp.setCenter(CanvasComponents.addDrawingUI());
        //New word
        GameLobby.setTop();
        //New chat
        GameLobby.bp.setRight(LiveChatComponents.liveChatUI());
        //new Timer
        TimerComponent.setTimeReimaing(140);
    }
}
