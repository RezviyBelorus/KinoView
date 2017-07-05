package controller;

import entity.Film;
import service.FilmService;

/**
 * Created by alexfomin on 04.07.17.
 */
//todo: сделать норм возращение для всех методов контроллера
public class FilmController {

    private FilmService filmService;

    public boolean uploadFilm(String filmName, int releaseYear, int qualityId, int translationId,
                              String durarion, float rating) {
        filmService.saveFilm(filmName, releaseYear, qualityId, translationId, durarion, rating);
        return true;
    }

    public boolean deleteFilm(String filmName) {
        filmService.deleteFilm(filmName);
        return true;
    }

    public boolean findFilmName(String filmName){
        filmService.findFilmName(filmName);

        return true;
    }

    public boolean addToFavorites(String filmName){
        filmService.addToFavorites(filmName);

        return true;
    }

    public boolean setStatus(String filmName, int status) {
        filmService.setStatus(filmName, status);
        return true;
    }
}
