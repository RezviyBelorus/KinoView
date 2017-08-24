package controller;

import dao.UserDAO;
import entity.Film;
import service.FilmService;
import service.UserService;
import web.ModelAndView;
import web.View;
import web.http.HttpMethod;
import web.http.RequestMapping;
import web.response.FilmDTO;

import java.util.List;

/**
 * Created by alexfomin on 04.07.17.
 */

public class FilmController implements Controller {

    private FilmService filmService;
    private UserService userService;

    public FilmController() {
        filmService = new FilmService();
        userService = new UserService(new UserDAO());
    }

    @RequestMapping(method = HttpMethod.POST, url = "films/save")
    public ModelAndView save(String filmName, String releaseYear, String quality, String translation,
                             String duration, String rating, String imgLink, String watchLink,
                             String shortStory, int kinogoPage, String genres, String countries) {
        ModelAndView view = new ModelAndView(View.FILM);
        FilmDTO film = filmService.save(filmName, releaseYear, quality, translation, duration, rating,
                imgLink, watchLink, shortStory, kinogoPage, genres, countries);
        view.addParam("film", film);
        return view;
    }

    @RequestMapping(method = HttpMethod.POST, url = "films/saveBatch")
    public ModelAndView saveBatch(List<Film> films) {
        ModelAndView view = new ModelAndView(View.MAIN);
        List<Film> savedFilms = filmService.saveBatch(films);
        view.addParam("films", savedFilms);
        return view;
    }

    @RequestMapping(method = HttpMethod.DELETE, url = "films/delete")
    public ModelAndView delete(String filmName) {
        ModelAndView view = new ModelAndView(View.FILM);
        FilmDTO isDeleted = filmService.delete(filmName);
        view.addParam("isDeleted", isDeleted);
        return view;
    }

    @RequestMapping(method = HttpMethod.GET, url = "films/find")
    public ModelAndView find(String filmName) {
        ModelAndView view = new ModelAndView(View.FILM);
        FilmDTO film = filmService.find(filmName);
        view.addParam("film", film);

        return view;
    }

    @RequestMapping(method = HttpMethod.POST, url = "films/addToFavorites")
    public ModelAndView addToFavorites(String userLoginOrEmail, String filmName) {
        ModelAndView view = new ModelAndView(View.FILM);
        boolean isAdded = filmService.addToFavorites(userLoginOrEmail, filmName);
        view.addParam("isAdded", isAdded);

        return view;
    }

    @RequestMapping(method = HttpMethod.POST, url = "films/setStatus")
    public ModelAndView setStatus(String filmName, String status) {
        ModelAndView view = new ModelAndView(View.FILM);
        FilmDTO filmDTO = filmService.setStatus(filmName, status);
        view.addParam("film", filmDTO);

        return view;
    }

    @RequestMapping(method = HttpMethod.DELETE, url = "films/clearTableFilms")
    public ModelAndView clearTableFilms(){
        filmService.clearTableFilms();
        return new ModelAndView(View.FILM);
    }
}
