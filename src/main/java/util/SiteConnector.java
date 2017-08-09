package util;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SiteConnector {
    private final String SITE_URL;
    Logger logger = Logger.getLogger(SiteConnector.class);

    public SiteConnector(int page) {
        SITE_URL = "http://kinogo.club/page/" + page;
    }

    public Document getPage() {
        try {
            return Jsoup.connect(SITE_URL).get();

        } catch (IOException e) {
            logger.error("Page not found");
            return null;
        }
    }
}
