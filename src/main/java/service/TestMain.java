package service;

import dao.FilmDAO;
import entity.Film;
import org.jsoup.Jsoup;
import util.SiteConnector;
import web.response.FilmDTO;

import javax.swing.text.DateFormatter;
import javax.swing.text.Document;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestMain {

    public boolean equals(LocalTime time1, LocalTime time2) {
        if(time1.getHour() == time2.getHour() && time1.getMinute()==time2.getMinute()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {

        TaskManager tas = TaskManager.getInstance(new FilmService());
        tas.setAdminWantsToRunUpdate(true);
        tas.run();
    }
}
