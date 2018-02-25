package saxparserdemo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

/**
 *
 * @author Connor Penrod
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    private XMLParser parser;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select File");
        
        File selectedFile = new File("C:\\Users\\Conno\\Classes\\CS4330\\ChallengeSAXParser\\SAXParserDemo\\src\\saxparserdemo\\test.xml");
        parser = new XMLParser(selectedFile);
        parser.parse();
        System.out.println(parser.getOutput());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
