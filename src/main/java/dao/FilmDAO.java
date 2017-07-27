package dao;

import entity.Film;
import exception.IllegalRequestException;
import org.apache.log4j.Logger;

import java.sql.*;


/**
 * Created by alexfomin on 02.07.17.
 */
public class FilmDAO extends AbstractDAO {

    Logger logger = Logger.getLogger(this.getClass());

    private String SAVE_FILM_QUERY = "INSERT INTO films (film_name, release_year, quality_id, translation_id, " +
            "length, rating, upload_date, status) VALUES (?,?,?,?,?,?,?,?)";

    private String DELETE_BY_ID_QUARY = "DELETE FROM films WHERE film_id = ?";

    private String DELETE_BY_FILM_NAME_QUARY = "DELETE FROM films WHERE film_name = ?";

    private String SELECT_FILM_BY_ID_QUERY = "SELECT film_id, film_name, release_year, quality_id," +
            "translation_id, length, rating, upload_date, status FROM films WHERE film_id = ?";

    private String SELECT_FILM_BY_FILM_NAME_QUERY = "SELECT film_id, film_name, release_year, quality_id," +
            "translation_id, length, rating, upload_date, status FROM films WHERE film_name = ?";

    private String INSERT_FILMS_TO_GENRES = "INSERT INTO films_to_genres VALUES (?, ?)";
    private String INSERT_FILMS_TO_COUNTRIES = "INSERT INTO films_to_countries VALUES (?, ?)";

    private String UPDATE_STATUS_BY_FILM_NAME_QUERY = "UPDATE films SET status = ? WHERE film_name = ?";

    private String ADD_TO_FAVORITES_QUERY = "INSERT INTO favorites_by_user VALUES (?,?)";

    public boolean save(Film film) {
        try (PreparedStatement prsFilm = connection.prepareStatement(SAVE_FILM_QUERY);) {

            prsFilm.setString(1, film.getName());
            prsFilm.setInt(2, film.getReleaseYear());
            prsFilm.setInt(3, film.getQuality_id());
            prsFilm.setInt(4, film.getTranslationId());
            prsFilm.setString(5, film.getLength());
            prsFilm.setFloat(6, film.getRating());
            prsFilm.setTimestamp(7, Timestamp.valueOf(film.getUploadDate()));
            prsFilm.setInt(8, film.getStatus());
            prsFilm.executeUpdate();

            return true;

        } catch (SQLException e) {
            logger.error("This film already exists" + film.getName());
            throw new IllegalRequestException("");
        }
    }

    public boolean delete(int id) {
        try (PreparedStatement prs = connection.prepareStatement(DELETE_BY_ID_QUARY)) {
            prs.setInt(1, id);
            prs.executeUpdate();

            return true;
        } catch (SQLException e) {
            logger.error("Film not found ID: " + id);
            throw new IllegalRequestException("");
        }
    }

    public boolean delete(String filmName) {
        try (PreparedStatement prs = connection.prepareStatement(DELETE_BY_FILM_NAME_QUARY)) {
            prs.setString(1, filmName);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Film not found: " + filmName);
            throw new IllegalRequestException("");
        }
    }

    public Film find(int id) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_FILM_BY_ID_QUERY)) {
            prs.setInt(1, id);
            ResultSet rs = prs.executeQuery();

            Film film = new Film();

            while (rs.next()) {
                film.setId(rs.getInt(1));
                film.setName(rs.getString(2));
                film.setReleaseYear(rs.getInt(3));
                film.setQuality_id(rs.getInt(4));
                film.setTranslationId(rs.getInt(5));
                film.setLength(rs.getString(6));
                film.setRating(rs.getFloat(7));
                film.setUploadDate(rs.getTimestamp(8).toLocalDateTime());
                film.setStatus(rs.getInt(9));
            }
            return film;
        } catch (SQLException e) {
            logger.error("Film not found: " + id);
            throw new IllegalRequestException("");
        }
    }

    public Film find(String filmName) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_FILM_BY_FILM_NAME_QUERY)) {
            prs.setString(1, filmName);
            ResultSet rs = prs.executeQuery();

            Film film = new Film();

            while (rs.next()) {
                film.setId(rs.getInt(1));
                film.setName(rs.getString(2));
                film.setReleaseYear(rs.getInt(3));
                film.setQuality_id(rs.getInt(4));
                film.setTranslationId(rs.getInt(5));
                film.setLength(rs.getString(6));
                film.setRating(rs.getFloat(7));
                film.setUploadDate(rs.getTimestamp(8).toLocalDateTime());
                film.setStatus(rs.getInt(9));
            }
            return film;
        } catch (SQLException e) {
            logger.error("Film not found: " + filmName);
            throw new IllegalRequestException("");
        }
    }

    public boolean setStatus(String filmName, int status){
        try (PreparedStatement prs = connection.prepareStatement(UPDATE_STATUS_BY_FILM_NAME_QUERY)){
            prs.setInt(1, status);
            prs.setString(2, filmName);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't change status: " + status);
            throw new IllegalRequestException("");
        }
    }

    public boolean addFilmToFavorites(int userId, int filmId){
        try (PreparedStatement prs = connection.prepareStatement(ADD_TO_FAVORITES_QUERY)){
            prs.setInt(1, userId);
            prs.setInt(2, filmId);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't add film to favorites:");
            throw new IllegalRequestException("");
        }
    }
}
