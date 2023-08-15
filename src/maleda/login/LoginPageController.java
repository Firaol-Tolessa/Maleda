package maleda.login;

import maleda.utility.AlertDefn;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static maleda.utility.DBConection.getData;
import static maleda.utility.Encryption.hashData;
import java.sql.*;
import static maleda.utility.DBConection.rs;

public class LoginPageController implements Initializable {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    private TextField adminname;
    @FXML
    private PasswordField password;

    @FXML
    public void switchToReg(ActionEvent event) throws IOException {

        String admin = adminname.getText();
        admin = admin.toLowerCase();
        String pass = password.getText();
        String hashedPass = hashData(pass);

        try {
            String input = admin;  // The input can be either username or userID

            // Check if the input is numeric (userID) or not (username)
            String column = Character.isDigit(input.charAt(0)) ? "empid" : "username";
            String q = "SELECT * FROM admin WHERE " + column + " = '" + input + "'";
            
            getData(q);
           
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String role = rs.getString("role");
                
                if (hashedPass.equals(storedPassword) && role.equals("localadmin")) {
                    AlertDefn.showNotification("LOGIN SUCCESS", "Loged in successfuly");
                    System.out.println("Login successful!");
                    root = FXMLLoader.load(getClass().getResource("../local_admin/LocalAdminPage.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();

                } else if (hashedPass.equals(storedPassword) && role.equals("admin")) {
                    AlertDefn.showNotification("LOGIN SUCCESS", "Loged in successfuly");
                    System.out.println("Login successful!");
                    root = FXMLLoader.load(getClass().getResource("../admin/AdminPage.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();

                } else {
                    System.out.println("Incorrect password!");
                    AlertDefn.methodERROR("The password or username is WRONG!!");
                    root = FXMLLoader.load(getClass().getResource("../login/LoginPage.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();
                }
            } else {
                System.out.println("Username or empid not found!");
                AlertDefn.methodERROR("The password or username is WRONG!!");
                root = FXMLLoader.load(getClass().getResource("../login/LoginPage.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // DBConection.closeResources();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
