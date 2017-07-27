package service;

import dao.CountryDAO;
import dao.FilmDAO;
import dao.GenreDAO;
import dao.UserDAO;
import entity.Film;
import exception.IllegalRequestException;
import web.response.FilmDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public FilmDTO save(String filmName, String releaseYear, String qualityId, String translationId,
                        String duration, String rating, String genres, String countries) {
        Film film = filmDAO.find(filmName);
        if (film == null) {
            film = new Film();
            film.setName(filmName);
            film.setReleaseYear(validateInt(releaseYear));
            film.setQuality_id(validateInt(qualityId));
            film.setTranslationId(validateInt(translationId));
            film.setLength(duration);
            film.setRating(validateFloat(rating));
            film.setUploadDate(LocalDateTime.now());
            film.setStatus(1);
            filmDAO.save(film);
            saveGenres(filmName, genres);
            saveCountries(filmName, countries);

            film = filmDAO.find(filmName);
            return new FilmDTO(film);
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

    public ArrayList<String> findGenres(int filmId) {
        return genreDAO.findAllByFilm(filmId);
    }

    public ArrayList<String> findCountries(int filmId) {
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

    public boolean saveGenres(String filmName, String genres) {
        Film film = filmDAO.find(filmName);
        int filmId = film.getId();
        String[] genresArray = genres.split(",");
        int[] genresIdArray = new int[genresArray.length];

        for (int i = 0; i < genresArray.length; i++) {
            genresIdArray[i] = Integer.parseInt(genresArray[i]);
        }
        boolean isSaved = genreDAO.saveFilmToGenre(filmId, genresIdArray);
        if (isSaved) {
            return true;
        }
        return false;
    }

    public boolean saveCountries(String filmName, String countries) {
        int filmId = filmDAO.find(filmName).getId();
        String[] countriesArray = countries.split(",");
        int[] countriesIdArray = new int[countriesArray.length];
        for (int i = 0; i < countriesArray.length; i++) {
            countriesIdArray[i] = Integer.parseInt(countriesArray[i]);
        }
        boolean isSaved = countryDAO.saveFilmToCountries(filmId, countriesIdArray);
        if (isSaved) {
            return true;
        }
        return false;
    }
}
