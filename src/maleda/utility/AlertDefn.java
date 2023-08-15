package maleda.utility;

import static java.lang.Boolean.FALSE;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class AlertDefn {

 
    static public void methodCONFIRMATION(String info) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONIFIRM");
        alert.setHeaderText(info);
        //alert.setContentText(info);
        alert.showAndWait();
        
    }

    
    static public void methodERROR(String info) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(info);
        // alert.setContentText(info);

//        Screen screen = Screen.getPrimary();
//        Rectangle2D bounds = screen.getVisualBounds();
//        double screenWidth = bounds.getWidth();
//        double screenHeight = bounds.getHeight();
//        double centerX = screenWidth / 2;
//        double centerY = screenHeight / 2;
//        double alertX = centerX - (188.0);
//        double alertY = centerY - (92.5);
//        alert.setX(alertX);
//        alert.setY(alertY);
        
        alert.showAndWait();
    }

   
    static public void methodWARNING(String info) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(info);
        //alert.setContentText("what ??");
        alert.showAndWait();
    }

   
   static public void methodINFORMATION(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(info);
        //alert.setContentText(info);
        alert.showAndWait();
    }
    static public void showNotification(String title, String text) {
        try{
        Image image = new Image( System.getProperty("user.dir")+"\\src\\maleda\\maleda_images\\succces.png" );
                
        ImageView imageview = new ImageView(image);
        imageview.setFitWidth(20);
        imageview.setFitHeight(20);
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(imageview) // You can set a graphic if desired
                .hideAfter(Duration.seconds(5)) // Auto-hide after 5 seconds
                .position(Pos.BOTTOM_RIGHT);// Position the notification

        notificationBuilder.darkStyle();
        notificationBuilder.show();
        }catch (IllegalArgumentException e) {
    System.err.println("Invalid URL or resource not found: " + e.getMessage());
    
}
    }

}
