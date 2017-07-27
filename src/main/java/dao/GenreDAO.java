package dao;

import entity.Genre;
import exception.IllegalRequestException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by alexfomin on 03.07.17.
 */
public class GenreDAO extends AbstractDAO {
    private Logger logger = Logger.getLogger(this.getClass());

    private String SAVE_GENRE_QUERY = "INSERT INTO genre_type (genre_name) VALUES (?)";

    private String DELETE_GENRE_BY_ID_QUERY = "DELETE FROM genre_type WHERE genre_id = ?";

    private String DELETE_GENRE_BY_NAME_QUERY = "DELETE FROM genre_type WHERE genre_name = ?";

    private String SELECT_GENRE_BY_ID_QUERY = "SELECT genre_id, genre_name FROM genre_type WHERE genre_id = ?";

    private String SELECT_GENRE_BY_GENRE_NAME_QUERY = "SELECT genre_id, genre_name FROM genre_type WHERE genre_name = ?";

    private String SELECT_ALL_GENRES_BY_FILM_QUERY = "SELECT n.genre_name FROM genre_type AS n " +
            "INNER JOIN films_to_genres AS f ON n.genre_id = f.genre_id WHERE f.film_id=?";

    private String INSERT_FILMS_TO_GENRES_QUERY = "INSERT INTO films_to_genres VALUES (?, ?)";

    public boolean save(Genre genre) {
        try (PreparedStatement prs = connection.prepareStatement(SAVE_GENRE_QUERY)) {
            prs.setString(1, genre.getGenreName());
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't save genre: " + genre.getGenreName());
            throw new IllegalRequestException("");
        }
    }

    public boolean saveFilmToGenre(int filmId, int[] genres) {
        try (PreparedStatement prs = connection.prepareStatement(INSERT_FILMS_TO_GENRES_QUERY)) {
            int counter = 0;

            for (int genreId : genres) {
                prs.setInt(1, filmId);
                prs.setInt(2, genreId);
                prs.addBatch();
                counter++;
                if (counter % 1000 == 0 || counter == genres.length) {
                    prs.executeBatch();
                }
            }
            return true;
        } catch (SQLException e) {
            logger.error("Can't save film to genres: " + filmId);
            throw new IllegalRequestException("");
        }
    }

    public boolean delete(int id) {
        try (PreparedStatement prs = connection.prepareStatement(DELETE_GENRE_BY_ID_QUERY)) {
            prs.setInt(1, id);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't delete genre: " + id);
            throw new IllegalRequestException("");
        }
    }

    public boolean delete(String genreName) {
        try (PreparedStatement prs = connection.prepareStatement(DELETE_GENRE_BY_NAME_QUERY)) {
            prs.setString(1, genreName);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't delete genre: " + genreName);
            throw new IllegalRequestException("");
        }
    }

    public Genre find(int id) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_GENRE_BY_ID_QUERY)) {
            prs.setInt(1, id);

            Genre genre = new Genre();

            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                genre.setGenreId(rs.getInt(1));
                genre.setGenreName(rs.getString(2));
            }
            return genre;
        } catch (SQLException e) {
            logger.error("Can't find genre: " + id);
            throw new IllegalRequestException("");
        }
    }

    public Genre find(String genreName) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_GENRE_BY_GENRE_NAME_QUERY)) {
            prs.setString(1, genreName);

            Genre genre = new Genre();

            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                genre.setGenreId(rs.getInt(1));
                genre.setGenreName(rs.getString(2));
            }
            return genre;
        } catch (SQLException e) {
            logger.error("Can't find genre: " + genreName);
            throw new IllegalRequestException("");
        }
    }

    public ArrayList<String> findAllByFilm(int filmId) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_ALL_GENRES_BY_FILM_QUERY)) {
            prs.setInt(1, filmId);
            ResultSet rs = prs.executeQuery();
            ArrayList<String> genres = new ArrayList<>();

            while (rs.next()) {
                genres.add(rs.getString(1));
            }
            return genres;
        } catch (SQLException e) {
            logger.error("Can't get genres by film: " + filmId);
            throw new IllegalRequestException("");
        }
    }
}
