package service;

import entity.Film;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.SiteConnector;
import util.Validator;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KinogoPageParser {
    //todo: слелать класс сервис который implements runable
    //todo: отдельный класс для апдейта базы фильмов

    private static final String YEAR = "Год выпуска: ";
    private static final String COUNTRY = " Страна: ";
    private static final String GENRE = " Жанр: ";
    private static final String QUALITY = " Качество: ";
    private static final String TRANSLATION = " Перевод: ";
    private static final String DURATION = " Продолжительность: ";
    private static final String PREMIERE = " Премьера \\(РФ\\): ";

    private static Pattern pattern;
    private static Matcher matcher;


    public static List<Film> parseFilms(Document document, int kinogoPage) throws IOException {

//        SiteConnector siteConnector = new SiteConnector(page);

//        Document document = siteConnector.getPage();

        List<String> filmsNames = getFilmsName(document);

        List<String> filmsDescription = getFilmsDescription(document);

        List<String> rating = getRating(document);

        List<String> imgLink = getImgLink(document);

        List<String> watchLink = getWatchLink(document);

        List<Film> films = fillListByFilms(filmsNames, filmsDescription, rating, imgLink, watchLink, kinogoPage);

        return films;
    }

//    public static boolean isPageFound(int pageNumber) {
//        String siteUrl = "http://kinogo.club/page/";
//
//        Connection connect = Jsoup.connect(siteUrl + pageNumber);
//        try {
//            Document document = connect.get();
//        } catch (IOException e) {
//            return false;
//        }
//        return true;
//    }

    private static List<String> getImgLink(Document document) {
        List<String> imgLink = new ArrayList<>();
        Elements shortmigClass = document.select(".shortimg");
        Elements imgSelector = shortmigClass.select("img");
        for (Element element : imgSelector) {
            String stringLink = "http://kinogo.club" + element.attr("src");
            imgLink.add(stringLink);
        }
        return imgLink;
    }

    private static List<String> getWatchLink(Document document) {
        List<String> watchLink = new ArrayList<>();
        Elements zagolovkiClass = document.select(".zagolovki");
        for (Element aClass : zagolovkiClass) {
            watchLink.add(aClass.select("a").attr("href"));
        }
        return watchLink;
    }

    private static List<String> getRating(Document document) {
        List<String> rating = new ArrayList<>();
        Elements ratingAttr = document.select(".current-rating");
        for (Element element : ratingAttr) {
            rating.add(element.text());
        }
        return rating;
    }

    private static List<String> getFilmsDescription(Document document) {
        List<String> filmsDescription = new ArrayList<>();
        Elements shortmigClass = document.select(".shortimg");
        for (Element element : shortmigClass) {
            filmsDescription.add(element.text());
        }
        return filmsDescription;
    }

    private static List<String> getFilmsName(Document document) {
        List<String> filmsNames = new ArrayList<>();
        Elements filmTitles = document.select("h2");
        for (Element title : filmTitles) {
            filmsNames.add(title.text());
        }
        return filmsNames;
    }

    private static List<Film> fillListByFilms(List<String> filmsName, List<String>
            filmsDescription, List<String> rating, List<String> imgLink, List<String> watchLink, int kinogoPage) {
        List<Film> films = new ArrayList<>();
        for (int i = 0; i < filmsDescription.size(); i++) {
            Film film = new Film();

            film.setName(filmsName.get(i));
            film.setReleaseYear(Validator.validateInt(getReleaseYear(filmsDescription.get(i))));
            film.setCountries(getCountries(filmsDescription.get(i)));
            film.setGenres(getGenries(filmsDescription.get(i)));
            film.setQuality(getQuality(filmsDescription.get(i)));
            film.setTranslation(getTranslation(filmsDescription.get(i)));
            film.setDuration(getDuration(filmsDescription.get(i)));
            film.setShortStory(getShortStory(filmsDescription.get(i)));
            film.setRating(Validator.validateFloat(rating.get(i)));
            film.setImgLink(imgLink.get(i));
            film.setWatchLink(watchLink.get(i));
            film.setKinogoPage(kinogoPage);

            films.add(film);
        }
        return films;
    }

    private static String getShortStory(String filmDescription) {

        int begin = 0;
        pattern = Pattern.compile(YEAR);
        matcher = pattern.matcher(filmDescription);

        int end = 0;
        if (matcher.find()) {
            end = matcher.start();
        }
        String shortStory = filmDescription.substring(begin, end);

        return shortStory;
    }

    private static String getReleaseYear(String filmDescription) {
        pattern = Pattern.compile(YEAR);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(COUNTRY);
        matcher = pattern.matcher(filmDescription);
        int end = 0;
        if (matcher.find()) {
            end = matcher.start();
        }

        String year = filmDescription.substring(begin, end);

        return year;
    }

    private static List<String> getCountries(String filmDescription) {
        pattern = Pattern.compile(COUNTRY);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(GENRE);
        matcher = pattern.matcher(filmDescription);
        int end = 0;

        if (matcher.find()) {
            end = matcher.start();
        }

        String countries = filmDescription.substring(begin, end);
        String[] byCountry = countries.split(", ");
        return Arrays.asList(byCountry);
    }

    private static List<String> getGenries(String filmDescription) {
        pattern = Pattern.compile(GENRE);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(QUALITY);
        matcher = pattern.matcher(filmDescription);
        int end = 0;

        if (matcher.find()) {
            end = matcher.start();
        }

        String genries = filmDescription.substring(begin, end);
        String[] byGenre = genries.split(", ");

        return Arrays.asList(byGenre);
    }

    private static String getQuality(String filmDescription) {
        pattern = Pattern.compile(QUALITY);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(TRANSLATION);
        matcher = pattern.matcher(filmDescription);
        int end = 0;

        if (matcher.find()) {
            end = matcher.start();
        }

        String quality = filmDescription.substring(begin, end);
        return quality;
    }

    private static String getTranslation(String filmDescription) {
        pattern = Pattern.compile(TRANSLATION);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(DURATION);
        matcher = pattern.matcher(filmDescription);
        int end = 0;

        if (matcher.find()) {
            end = matcher.start();
        }

        String translation = filmDescription.substring(begin, end);
        return translation;
    }

    private static String getDuration(String filmDescription) {
        pattern = Pattern.compile(DURATION);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(PREMIERE);
        matcher = pattern.matcher(filmDescription);
        int end = 0;

        if (matcher.find()) {
            end = matcher.start();
        }

        String duration = filmDescription.substring(begin, end);
        return duration;
    }
}
