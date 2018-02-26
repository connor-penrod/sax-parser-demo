package saxparserdemo;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Connor Penrod
 */

//this is an XML SAX-style parser
//it takes a file to be parsed, and optionally an indent size for the ouput
public class XMLParser {
    
    private File file;
    private DefaultHandler handler;
    
    private int indent = 0;
    private int indentSize;
    private String hierarchyStr;
    private StringBuffer contentBuffer;
    private String doubleChecker;
        
    public XMLParser(File file, int indentSize)
    {
        this.file = file;
        handler = setupHandler();
        hierarchyStr = "";
        doubleChecker = "";
        this.indentSize = indentSize;
    }
    
    public XMLParser(File file)
    {
        this(file, 4);
    }
    
    private DefaultHandler setupHandler()
    {
        return new DefaultHandler(){
            @Override
            public void startDocument() throws SAXException {
                System.out.println("Started parsing.");
            }
            
            //on encountering XML element, increase indent and start a StringBuffer for the element's value
            @Override
            public void startElement(String namespaceURI,
                         String localName,
                         String qName, 
                         Attributes atts) throws SAXException {
                indent += indentSize;
                //this next line is some black magic shit i found on stackoverflow
                hierarchyStr += "- [" + qName + "]" + "\n" + new String(new char[indent]).replace("\0", " ");
                contentBuffer = new StringBuffer();
            }
            
            //characters is called when part of a value is found in an XML element.
            //it's impossible to know exactly when this is called, so I store what it gets in a StringBuffer to be used in endElement
            @Override
            public void characters(char ch[],int start,int length){ 
                contentBuffer.append(new String(ch,start,length));
            } 
            
            //on endElement, display value and decrease indent
            @Override
            public void endElement(String namespaceURI,
                         String localName,
                         String qName)
            {
                indent -= indentSize;
                hierarchyStr = hierarchyStr.substring(0, hierarchyStr.length() - (indent+indentSize+1));
                //before doing things, check that endElement wasn't called twice on the same element
                //this happens on the last child element of another element (for some reason)
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
            
            @Override
            public void endDocument()
            {
                System.out.println("Parsing finished.");
            }
        };
    }
    
    //builds a SAXParser object and starts parsing process using handler defined in setupHandler()
    public void parse() throws SAXException
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
            if(e instanceof SAXException)
            {
                throw new SAXException();
            }
        }
    }
    
    //return string output of XML parsing as tree representation of the DOM
    public String getOutput()
    {
        return hierarchyStr;
    }
}
