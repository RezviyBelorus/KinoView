package service;

import dao.FilmDAO;
import entity.Film;
import exception.IllegalRequestException;
import web.response.FilmDTO;

import java.time.LocalDateTime;

/**
 * Created by alexfomin on 05.07.17.
 */

//todo: correct deleteFIlm or not
    //todo: addToFavorites how to do
public class FilmService {
    FilmDAO filmDAO;

    public FilmDTO saveFilm(String filmName, int releaseYear, int qualityId, int translationId,
                         String durarion, float rating) {
        Film film = filmDAO.find(filmName);
        if (film == null) {
            film.setFilm_name(filmName);
            film.setRelease_year(releaseYear);
            film.setQuality_id(qualityId);
            film.setTranslation_id(translationId);
            film.setLength(durarion);
            film.setRating(rating);
            film.setUpload_date(LocalDateTime.now());
            film.setStatus(1);
            filmDAO.save(film);
            return new FilmDTO(film);
        }
        return null;
    }

    public FilmDTO deleteFilm(String filmName) {
        boolean isDeleted = filmDAO.setStatus(filmName, FilmStatus.DELETED.getValue());
        if (isDeleted) {
            return new FilmDTO(filmDAO.find(filmName));
        }
        throw new IllegalRequestException("");
    }

    public FilmDTO findFilmName(String filmName) {
        Film film = filmDAO.find(filmName);
        if (film.getStatus() == FilmStatus.ACTIVE.getValue()) {
            return new FilmDTO(film);
        }
        return null;
    }

    public FilmDTO setStatus(String filmName, int status) {
        filmDAO.setStatus(filmName, status);
        Film film = filmDAO.find(filmName);
        return new FilmDTO(film);
    }

    public void addToFavorites(String filmName) {
    }
}
