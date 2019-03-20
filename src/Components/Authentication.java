package Components;

import Database.DBConnection;
import Scenes.*;

import javax.security.auth.login.LoginContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

public class Authentication {


    public static boolean submit(){
        String username = SignUp.getName();
        String mail = SignUp.getMail();
        String password = SignUp.getPassword();
        int avatarID = SignUp.getAvatarID();
        String hash;
        String salt;

        if(password != null) {
            //Encrypt password
            String encryptor = Encryptor.Encryptor(password, null);
            //get salt and hash
            hash = Encryptor.getHash(encryptor);
            salt = Encryptor.getSalt(encryptor);
        }else{
            SignUp.visiblePassword(true);
            hash = null;
            salt = null;
        }

        if(username == null){
            SignUp.visibleEmptyUser(true);
        } else if(username != null) {
            SignUp.visibleEmptyUser(false);
        }

        if(mail == null) {
            SignUp.visibleEmptyMail(true);
        } else if(mail != null){
            SignUp.visibleEmptyMail(false);
        }

        if(password == null){
            SignUp.visibleEmptyPassword(true);
        } else if(password != null){
            SignUp.visibleEmptyPassword(false);
        }

        if((DBConnection.exists("userName", username))||(DBConnection.exists("userMail", mail))) {
            SignUp.visibleUserMail(true);
        }else if((username != null) && (mail != null) && (hash != null) && (salt != null)) {
            DBConnection.registerUser(username, hash, salt, mail, avatarID);
            Email.sendEmail(mail);
            return true;
        }else{
            SignUp.visibleUserMail(false);
        }
        return false;
    }

    public static void logIn(double WIDTH, double HEIGHT) {
        String username = LogIn.getUserName();
        String password = LogIn.getPassword();
        //Getting salt from db using username
        String salt = DBConnection.getSalt(username);
        //generating hash using salt
        String encryptor = Encryptor.Encryptor(password, salt);
        String hash = Encryptor.getHash(encryptor);


        //Check if
        if((DBConnection.exists("userName", username))&&(DBConnection.exists("password", hash))) {
            if (!DBConnection.getLoggedIn(username)) {
                MainScene.mm = new MainMenu(WIDTH, HEIGHT);
                MainScene.setScene(MainScene.mm.getSc());
                DBConnection.setLoggedIn(username, 1);
            } else {
                // already logged in error
                LogIn.setTextLoginError("User already logged in");
                LogIn.visibleLoginError(true);
            }
        } else {
            // wrong username or password error
            LogIn.setTextLoginError("Incorrect username or password");
            LogIn.visibleLoginError(true);
        }
    }
}
