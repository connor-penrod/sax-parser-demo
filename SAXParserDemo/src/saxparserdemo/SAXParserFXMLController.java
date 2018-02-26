package saxparserdemo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.xml.sax.SAXException;

/**
 *
 * @author Connor Penrod
 */
public class SAXParserFXMLController implements Initializable {
    
    private XMLParser parser;
    
    @FXML
    public TextArea textArea;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        //handle loading of XML file, instantiate parser and attempt to parse
        
        Button btn = (Button)event.getSource();
        
        FileChooser fc = new FileChooser();
        fc.setTitle("Select File");
        fc.getExtensionFilters().add(new ExtensionFilter("XML Documents", "*.xml", "*.XML"));
        File selectedFile = fc.showOpenDialog(btn.getScene().getWindow());
        //File selectedFile = new File("C:\\Users\\Conno\\Classes\\CS4330\\ChallengeSAXParser\\SAXParserDemo\\src\\saxparserdemo\\test2.xml");
        
        parser = new XMLParser(selectedFile, 4);
        
        try{
            parser.parse();
            textArea.setText(parser.getOutput());
        }
        //if parsing error occurred
        catch (SAXException e){
            textArea.setText("Parsing error occurred in selected XML.\nPlease make sure XML is properly formed.");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
