package Components.Threads;

import Components.GameLobbyComponents.GameLogicComponents;

import java.util.Timer;
import java.util.TimerTask;

import static Components.GameLobbyComponents.AvatarComponents.updateData;
import static Components.GameLobbyComponents.CanvasComponents.setImage;
import static Components.GameLobbyComponents.CanvasComponents.updateImage;
import static Components.GameLobbyComponents.TimerComponent.setTimerText;
import static Components.GameLobbyComponents.TimerComponent.timeRemaining;

public class Timers {

    private static Timer timer;
    private static Timer timer2;
    private static Timer timer3;
    private static Timer timer4;


    public static void timer(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Downloads and displays image from DB - Timer1");

                setImage();
            }
        };
        timer.schedule(task, 0, +7000); // was originaly 5000
    }

    public static void turnOffTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            System.out.println("Actually closed Timer 1");
        }
    }

    // Updating image with timer in stead of release stroke
    public static void timer2(){
        timer2 = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Uploads new version of drawing to DB - Timer 2");
                updateImage();
            }
        };
        timer2.schedule(task, 0, +5000);
    }

    // Method for turning off timer, used when time has run out
    public static void turnOffTimer2() {
        if (timer2 != null) {
            timer2.cancel();
            timer2.purge();
            System.out.println("Actually turned off timer 2");
        }
    }

    /*
    public static void timer3(){
        timer3 = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            }
        };
        timer3.schedule(task, 0, +10000);
    }

    public static void turnOffTimer3() {
        if (timer3 != null) {
            timer3.cancel();
        }
    }
    */

    public static void timer4(){
        timer4 = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Updates playerList");
                updateData();
                if (timeRemaining > 80) {
                    setTimerText(false);
                } else if (timeRemaining > 0) {
                    setTimerText(true);
                } else {
                    turnOffTimer2(); // Turns off timer that updates image.
                    turnOffTimer4(); // turns off countdown timer
                    GameLogicComponents.reset();
                }
            }
        };
        timer4.schedule(task, 0, +1000);
    }

    public static void turnOffTimer4() {
        if (timer4 != null) {
            timer4.cancel();
            timer4.purge();
        }
    }
}
