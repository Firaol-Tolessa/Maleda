package maleda.local_admin;

import maleda.utility.AlertDefn;
import maleda.utility.Encryption;
import maleda.utility.DBConection;
import maleda.utility.APICall;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RegistrationGUIController implements Initializable {

     @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    
    @FXML
    private ImageView Emp_Photo;
    @FXML
    private TextField firstname, middlename, lastname;
    @FXML
    private TextField empid;
    @FXML
    private String biodata1, biodata2, biodata;
    @FXML
    private String capdate1, capdate2, capdate;
    @FXML
    private String devid1, devid2, devid;

    @FXML
    public void spellCouonter(InputMethodEvent event) {
        // String fname = firstname.getText();
        //System.out.println("string ->" + fname);

    }

    @FXML
    public void uploadPhoto() {
        //Photo will bo uploaded and save it
        FileChooser fileChooser = new FileChooser();

        // Set the initial directory for the FileChooser
        File initialDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(initialDirectory);
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPEG files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif");
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Create a new Image object from the selected file
            ImageView imageView = new ImageView();
            Image image = new Image(selectedFile.toURI().toString());

            // Set the Image object as the image property of the ImageView control
            Emp_Photo.setImage(image);
            System.out.println("Photo uploded succesfully");
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
    }

    @FXML
    public void scannFingerprint1() {
        //API will be called to scann the fingerptint1
        APICall api1 = new APICall();
        api1.Call();
        biodata1 = api1.biometricData;
        capdate1 = api1.captureDate;
        devid1 = api1.deviceId;

         String filePath = (System.getProperty("user.dir") + "\\data\\bio1.xyt"); 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(biodata1);
            System.out.println("Biodata1 saved to file bio1.txt successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void scannFingerprint2() {
        //API will be called to scann the fingerptint2
        //compare the the fingerprint1 and fingerprint2 
        //store to the database if fp1 and fp2 matches
        APICall api2 = new APICall();
        api2.Call();
        biodata2 = api2.biometricData;
        capdate2 = api2.captureDate;
        devid2 = api2.deviceId;
        
        String filePath1 = (System.getProperty("user.dir") + "\\data\\bio2.xyt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath1))) {
            writer.write(biodata1);
            System.out.println("Biodata1 saved to file bio2.xyt successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProcessBuilder pb = new ProcessBuilder(System.getProperty("user.dir") + "\\exec\\bozorth3.exe", System.getProperty("user.dir") + "\\data\\bio1.xyt", System.getProperty("user.dir") + "\\data\\bio2.xyt");
        File outputFile = new File(System.getProperty("user.dir") + "\\data\\output.txt");
//File errorFile = new File("error.txt");
        pb.redirectOutput(outputFile);
//pb.redirectError(errorFile);
        Process p;
        try {
            p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
                String filePath = (System.getProperty("user.dir") + "\\data\\output.txt");
                String result = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                   result = line;                          
            }
            
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        System.out.println("result"+result);
        int intResult = Integer.parseInt(result);
        if (intResult > 50) {
            biodata = biodata2;
            capdate = capdate2;
            devid = devid2; 
            
        } else {
            AlertDefn alertObject = new AlertDefn();
            alertObject.methodWARNING("The two finger print DOESNOT match. scan the fingerprint again!!!");
                        
        }
    }
    
    

    @FXML
    public void register(ActionEvent event) {
        //check all the data are correct and unique
        //Send the data to the database
        String fname = firstname.getText();
        String mname = middlename.getText();
        String lname = lastname.getText();
        String employeeid = empid.getText();
        String hashInput = fname+mname+lname+employeeid+employeeid+".jpg"+biodata+capdate+devid;
        String maledaHashValue = Encryption.hashData(hashInput);
        if(fname.length() != 0 && mname.length()!= 0 && lname.length() != 0 && employeeid.length() !=0 && biodata.length() != 0 && capdate.length() != 0 && devid.length() !=0){
        DBConection.updateData("insert into employee values('" + employeeid + "','" + fname + "','" + mname + "','" + lname + "','" + employeeid + ".jpg','" + biodata + "','" + capdate + "','" + devid + "','"+maledaHashValue+"')");
        }
        else{
        AlertDefn.methodERROR("Fill all the required data first!!");
        }
        
        
         try {
             root = FXMLLoader.load(getClass().getResource("MaledaRegistrationGUI.fxml"));
         } catch (IOException ex) {
             Logger.getLogger(RegistrationGUIController.class.getName()).log(Level.SEVERE, null, ex);
         }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

}
