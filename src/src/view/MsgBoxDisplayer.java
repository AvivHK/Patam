package view;

import javafx.scene.control.Alert;

public class MsgBoxDisplayer {
    public static String empty="";

    public static void show(Alert.AlertType kind, String emptyA, String emptyB, String message) {
        Alert alert = new Alert(kind);
        alert.setHeaderText(empty);
        alert.setTitle(empty);
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> { });
    }


    public static void showConf(String message)
    {
    	show(Alert.AlertType.CONFIRMATION ,empty,empty,message);
    }

    public static void showInfo(String message)
    {
    	show(Alert.AlertType.INFORMATION,empty,empty,message);
    }

    public static void showWarn(String message)
    {
        show(Alert.AlertType.WARNING,empty,empty, message);
    }
    
   
    
}