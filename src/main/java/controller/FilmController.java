package controller;

import service.FilmService;
import service.UserService;
import web.ModelAndView;
import web.View;
import web.http.HttpMethod;
import web.http.RequestMapping;
import web.response.FilmDTO;

/**
 * Created by alexfomin on 04.07.17.
 */

public class FilmController implements Controller {

    private FilmService filmService;
    private UserService userService;

    @RequestMapping(method = HttpMethod.POST, url = "films/upload")
    public ModelAndView upload(String filmName, String releaseYear, String qualityId, String translationId,
                               String duration, String rating, String genres, String countries) {
        ModelAndView view = new ModelAndView(View.FILM);
        FilmDTO film = filmService.save(filmName, releaseYear, qualityId, translationId, duration, rating, genres, countries);
        view.addParam("film", film);
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
}
