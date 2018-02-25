package saxparserdemo;

import java.io.File;
import java.util.Hashtable;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Connor Penrod
 */
public class XMLParser {
    
    private File file;
    private DefaultHandler handler;
    
    private int indent = 0;
    private String hierarchyStr;
    private StringBuffer contentBuffer;
    private String doubleChecker;
        
    public XMLParser(File file)
    {
        this.file = file;
        handler = setupHandler();
        hierarchyStr = "";
        doubleChecker = "";
    }
    
    private DefaultHandler setupHandler()
    {
        return new DefaultHandler(){
            @Override
            public void startDocument() throws SAXException {
                System.out.println("Started parsing.");
            }
            
            public void startElement(String namespaceURI,
                         String localName,
                         String qName, 
                         Attributes atts) throws SAXException {
                indent += 2;
                hierarchyStr += "- [" + qName + "]" + "\n" + new String(new char[indent]).replace("\0", " "); //this is some black magic shit i found on stackoverflow
                contentBuffer = new StringBuffer();
            }
            
            public void characters(char ch[],int start,int length){ 
                contentBuffer.append(new String(ch,start,length));
            } 
            
            @Override
            public void endElement(String namespaceURI,
                         String localName,
                         String qName)
            {
                indent -= 2;
                hierarchyStr = hierarchyStr.substring(0, hierarchyStr.length() - (indent+3));
                if(!doubleChecker.equals(contentBuffer.toString().replaceAll("\\s+","")))
                {
                    hierarchyStr += " --> " + "\"" + contentBuffer + "\"" + "\n" + new String(new char[indent]).replace("\0", " ");
                    doubleChecker = contentBuffer.toString().replaceAll("\\s+","");
                }
                else
                {
                    hierarchyStr += "\n" + new String(new char[indent]).replace("\0", " ");
                }
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
    
    public String getOutput()
    {
        return hierarchyStr;
    }
}
