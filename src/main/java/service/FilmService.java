package service;

import dao.CountryDAO;
import dao.FilmDAO;
import dao.GenreDAO;
import dao.UserDAO;
import entity.Country;
import entity.Film;
import entity.Genre;
import exception.IllegalRequestException;
import web.response.FilmDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.Validator.validateFloat;
import static util.Validator.validateInt;


/**
 * Created by alexfomin on 05.07.17.
 */

public class FilmService {
    private FilmDAO filmDAO;
    private UserDAO userDAO;
    private GenreDAO genreDAO;
    private CountryDAO countryDAO;

    public FilmService() {
        filmDAO = new FilmDAO();
        userDAO = new UserDAO();
        genreDAO = new GenreDAO();
        countryDAO = new CountryDAO();
    }

    public FilmDTO save(String filmName, String releaseYear, String quality, String translation,
                        String duration, String rating, String imgLink, String watchLink, String shortStory,
                        int kinogoPage, String genres, String countries) {
        Film film = filmDAO.find(filmName);
        if (film == null) {
            film = new Film();
            film.setName(filmName);
            film.setReleaseYear(validateInt(releaseYear));
            film.setQuality(quality);
            film.setTranslation(translation);
            film.setDuration(duration);
            film.setRating(validateFloat(rating));
            film.setUploadDate(LocalDateTime.now());
            film.setStatus(1);
            film.setImgLink(imgLink);
            film.setWatchLink(watchLink);
            film.setShortStory(shortStory);
            film.setKinogoPage(kinogoPage);
            filmDAO.save(film);

            String[] byGenre = genres.split(", ");
            String[] byCountry = countries.split(", ");

            saveGenres(filmName, Arrays.asList(byGenre));
            saveCountries(filmName, Arrays.asList(byCountry));

            film = filmDAO.find(filmName);
            return new FilmDTO(film);
        }
        return null;
    }

    public List<Film> saveBatch(List<Film> films) {
        List<Film> filmsToSave = new ArrayList<>();
        for (Film film : films) {
            Film filmDao = filmDAO.find(film.getName());
            if (filmDao == null) {
                filmsToSave.add(film);
            }
        }

        boolean isSaved = filmDAO.saveBatch(filmsToSave);

        if (isSaved) {
            return filmsToSave;
        }
        return null;
    }

    public FilmDTO delete(String filmName) {
        boolean isDeleted = filmDAO.setStatus(filmName, FilmStatus.DELETED.getValue());
        if (isDeleted) {
            return new FilmDTO(filmDAO.find(filmName));
        }
        throw new IllegalRequestException("");
    }

    public FilmDTO find(String filmName) {
        Film film = filmDAO.find(filmName);
        if (film.getStatus() == FilmStatus.ACTIVE.getValue()) {

            film.setGenres(findGenres(film.getId()));
            film.setCountries(findCountries(film.getId()));

            return new FilmDTO(film);
        }
        return null;
    }

    public List<Film> findLoadedFilms() {

        return filmDAO.findLoadedFilms();
    }

    public List<String> findGenres(int filmId) {
        return genreDAO.findAllByFilm(filmId);
    }

    public List<String> findCountries(int filmId) {
        return countryDAO.findAllByFilm(filmId);
    }

    public FilmDTO setStatus(String filmName, String status) {
        filmDAO.setStatus(filmName, validateInt(status));
        Film film = filmDAO.find(filmName);
        return new FilmDTO(film);
    }

    public boolean addToFavorites(String userEmailOrLogin, String filmName) {
        int userId = userDAO.find(userEmailOrLogin).getId();
        int filmId = filmDAO.find(filmName).getId();
        boolean isAdded = filmDAO.addFilmToFavorites(userId, filmId);
        if (isAdded) {
            return true;
        }
        return false;
    }

    private boolean saveGenres(String filmName, List<String> genres) {
        Film film = filmDAO.find(filmName);
        int filmId = film.getId();

        int[] genresId = new int[genres.size()];

        for (int i = 0; i < genres.size(); i++) {
            Genre genre = genreDAO.find(genres.get(i));
            if (genre != null) {
                genresId[i] = genre.getGenreId();
            }
        }

        boolean isSaved = genreDAO.saveFilmToGenre(filmId, genresId);
        if (isSaved) {
            return true;
        }
        return false;
    }

    private boolean saveCountries(String filmName, List<String> countries) {
        int filmId = filmDAO.find(filmName).getId();
        int[] countriesId = new int[countries.size()];
        for (int i = 0; i < countries.size(); i++) {
            Country country = countryDAO.find(countries.get(i));
            if (country != null) {
                countriesId[i] = country.getCountryId();
            }
        }
        boolean isSaved = countryDAO.saveFilmToCountries(filmId, countriesId);
        if (isSaved) {
            return true;
        }
        return false;
    }
}
