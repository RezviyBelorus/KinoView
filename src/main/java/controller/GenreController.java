package controller;

import entity.Genre;
import service.GenreService;
import web.ModelAndView;
import web.View;
import web.http.HttpMethod;
import web.http.RequestMapping;

/**
 * Created by alexfomin on 06.07.17.
 */
public class GenreController implements Controller {
    private GenreService genreService;

    @RequestMapping(method = HttpMethod.POST, url = "genres/add")
    public ModelAndView add(String genreName) {
        ModelAndView view = new ModelAndView(View.GENRE);
        Genre genre = genreService.addGenre(genreName);
        view.addParam("genre", genre);

        return view;
    }

    @RequestMapping(method = HttpMethod.DELETE, url = "genres/delete")
    public ModelAndView delete(String genreName) {
        ModelAndView view = new ModelAndView(View.GENRE);
        boolean delete = genreService.delete(genreName);
        view.addParam("isDeleted", delete);

        return view;
    }

    @RequestMapping(method = HttpMethod.GET, url = "genres/find")
    public ModelAndView find(String genreName) {
        ModelAndView view = new ModelAndView(View.GENRE);
        Genre genre = genreService.find(genreName);
        view.addParam("genre", genre);

        return view;
    }
}
