package saxparserdemo;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Connor Penrod
 */
public class XMLParser {
    
    private File file;
    private DefaultHandler handler;
    
    public XMLParser(File file)
    {
        this.file = file;
        handler = setupHandler();
    }
    
    private DefaultHandler setupHandler()
    {
        return new DefaultHandler(){
            @Override
            
            public void startDocument() throws SAXException {
                System.out.println("Started.");
            }
        };
    }
    
    public void parse()
    {
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            saxParser.parse(file, handler);
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.toString());
        }
    }
    
}
