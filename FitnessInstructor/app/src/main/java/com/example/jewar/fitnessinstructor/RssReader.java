package com.example.jewar.fitnessinstructor;

/**
 * Created by jewar
 */
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import com.example.jewar.fitnessinstructor.RssItem;

public class RssReader {
    // Our class has an attribute which represents RSS Feed URL
    private String rssUrl;
    //We set this URL with the constructor
    public RssReader(String rssUrl) {
        this.rssUrl = rssUrl;
    }
    // Get RSS items. This method will be called to get the parsing process result.
    public List<RssItem> getItems() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        RssParseHandler handler = new RssParseHandler();
        saxParser.parse(rssUrl, handler);
        return handler.getItems();
    }
}
