package maleda.admin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import maleda.utility.AlertDefn;

public class AdminPageController implements Initializable {

    @FXML 
    private Button logoutbn;
    
    @FXML
    
    private void logout(ActionEvent event){
        boolean logoutStatus = AlertDefn.methodCONFIRMATION("Are you sure you want to logout?");
        if(logoutStatus == true)
        {}
        
    
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
