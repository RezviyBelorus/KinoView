package service;

import dao.GenreDAO;
import entity.Genre;

/**
 * Created by alexfomin on 06.07.17.
 */
public class GenreService {
    private GenreDAO genreDAO;

    public Genre addGenre(String genreName) {
        Genre genre = genreDAO.find(genreName);

        if(genre==null) {
            genre = new Genre();
            genre.setGenreName(genreName);
            genreDAO.save(genre);
            return genreDAO.find(genreName);
        }
        return null;
    }

    public boolean delete(String genreName) {
        boolean isDeleted = genreDAO.delete(genreName);
        if(isDeleted) {
            return true;
        }
        return false;
    }

    public Genre find(String genreName) {
        return genreDAO.find(genreName);
    }

    public Genre find(int genreId){
        return genreDAO.find(genreId);
    }
}
