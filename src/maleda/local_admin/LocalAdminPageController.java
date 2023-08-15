package maleda.local_admin;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import maleda.utility.AlertDefn;

public class LocalAdminPageController implements Initializable {
    
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    
    @FXML
    public Button logoutbn;
       
   @FXML
   private void logout(ActionEvent event) throws IOException{
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("CONIFIRM");
        alert.setHeaderText("Are you sure you want to leave?");
        if (alert.showAndWait().get() == ButtonType.OK) {
        
        root = FXMLLoader.load(getClass().getResource("../login/LoginPage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
              AlertDefn.showNotification("LOGOUT SECCESS", "You logout seccesfuly!!");
        
        }
       
       
   }

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
