package Attributes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public AlertBox() {
    }


    // Show a Information Alert with header Text
    public static void informationAlert(String header,String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Show a Warning Alert with header Text
    public static void warningAlert(String header,String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Show a Warning Alert without Header Text
    public static void errorAlert(String header,String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Show a Confirmation Alert without Header Text
    public static void confirmAlert(String header,String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please Confirm");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static String textDialogue(String t,String h,String c){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(t);
        dialog.setHeaderText(h);
        dialog.setContentText(c);

        dialog.showAndWait();
        String result = dialog.getEditor().getText();

        if(!result.isEmpty()){
            return result;
        }
        else
            return "-1";
    }

}
