
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class LoadFromXML {

    protected File xmlFile;
    //<load>
    protected Element load;
    protected Boolean loadEnable;
    protected String loadFile;
    protected String loadFormat;

    //<save>
    protected Element save;
    protected Boolean saveEnable;
    protected String saveFile;
    protected String saveFormat;

    //<log>
    protected Element log;
    protected Boolean logEnable;
    protected String logFile;



    public LoadFromXML(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    public void readXML() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        Element root = (Element) document.getFirstChild();

        load = (Element) root.getElementsByTagName("load").item(0);
        save = (Element) root.getElementsByTagName("save").item(0);
        log = (Element) root.getElementsByTagName("log").item(0);

        loadElement();
        saveElement();
        logElement();
    }

    private void loadElement() {
        loadEnable = Boolean.valueOf(load.getElementsByTagName("enabled").item(0).getTextContent());
        loadFile = load.getElementsByTagName("fileName").item(0).getTextContent();
        loadFormat = load.getElementsByTagName("format").item(0).getTextContent();
    }

    private void saveElement() {
        saveEnable = Boolean.valueOf(save.getElementsByTagName("enabled").item(0).getTextContent());
        saveFile = save.getElementsByTagName("fileName").item(0).getTextContent();
        saveFormat = save.getElementsByTagName("format").item(0).getTextContent();
    }

    private void logElement() {
        logEnable = Boolean.valueOf(log.getElementsByTagName("enabled").item(0).getTextContent());
        logFile = log.getElementsByTagName("fileName").item(0).getTextContent();
    }


}
