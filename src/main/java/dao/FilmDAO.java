package dao;

import entity.Film;
import exception.IllegalRequestException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by alexfomin on 02.07.17.
 */
public class FilmDAO extends AbstractDAO {

    Logger logger = Logger.getLogger(this.getClass());

    private String SAVE_FILM_QUERY = "INSERT INTO films (film_name, release_year, quality, translation, " +
            "length, rating, upload_date, status, img_link, watch_link, short_story, kinogo_page) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

    private String DELETE_BY_ID_QUARY = "DELETE FROM films WHERE film_id = ?";

    private String DELETE_BY_FILM_NAME_QUARY = "DELETE FROM films WHERE film_name = ?";

    private String SELECT_FILM_BY_ID_QUERY = "SELECT film_id, film_name, release_year, quality," +
            "translation, length, rating, upload_date, status, img_link, watch_link, short_story, kinogo_page FROM films WHERE film_id = ?";

    private String SELECT_FILM_BY_FILM_NAME_QUERY = "SELECT film_id, film_name, release_year, quality," +
            "translation, length, rating, upload_date, status, img_link, watch_link, short_story, kinogo_page FROM films WHERE film_name = ?";

    private String SELECT_ALL_FILMS_QUERY = "SELECT film_id, film_name, release_year, quality," +
            "translation, length, rating, upload_date, status, img_link, watch_link, short_story, kinogo_page FROM films";

    private String INSERT_FILMS_TO_GENRES = "INSERT INTO films_to_genres VALUES (?, ?)";
    private String INSERT_FILMS_TO_COUNTRIES = "INSERT INTO films_to_countries VALUES (?, ?)";

    private String UPDATE_STATUS_BY_FILM_NAME_QUERY = "UPDATE films SET status = ? WHERE film_name = ?";

    private String UPDATE_FILMS_QUERY = "UPDATE films SET quality = ?, translation = ? " +
            "WHERE film_name = ? AND release_year = ? AND short_story = ?";

    private String ADD_TO_FAVORITES_QUERY = "INSERT INTO favorites_by_user VALUES (?,?)";

    private String SET_FOREIGN_KEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = ?";
    private String TRUNCATE_TABLE_FILMS_QUERY = "TRUNCATE TABLE films";


    public boolean save(Film film) {
        try (PreparedStatement prsFilm = connection.prepareStatement(SAVE_FILM_QUERY);) {

            prsFilm.setString(1, film.getName());
            prsFilm.setInt(2, film.getReleaseYear());
            prsFilm.setString(3, film.getQuality());
            prsFilm.setString(4, film.getTranslation());
            prsFilm.setString(5, film.getDuration());
            prsFilm.setFloat(6, film.getRating());
            prsFilm.setTimestamp(7, Timestamp.valueOf(film.getUploadDate()));
            prsFilm.setInt(8, film.getStatus());
            prsFilm.setString(9, film.getImgLink());
            prsFilm.setString(10, film.getWatchLink());
            prsFilm.setString(11, film.getShortStory());
            prsFilm.setInt(12, film.getKinogoPage());
            prsFilm.executeUpdate();

            return true;

        } catch (SQLException e) {
            logger.error("This film already exists" + film.getName());
            throw new IllegalRequestException("");
        }
    }

    public boolean saveBatch(List<Film> films) {
        try (PreparedStatement prsFilmsBatch = connection.prepareStatement(SAVE_FILM_QUERY)) {
            int counter = 0;
            for (Film film : films) {
                prsFilmsBatch.setString(1, film.getName());
                prsFilmsBatch.setInt(2, film.getReleaseYear());
                prsFilmsBatch.setString(3, film.getQuality());
                prsFilmsBatch.setString(4, film.getTranslation());
                prsFilmsBatch.setString(5, film.getDuration());
                prsFilmsBatch.setFloat(6, film.getRating());
                prsFilmsBatch.setTimestamp(7, Timestamp.valueOf(film.getUploadDate()));
                prsFilmsBatch.setInt(8, film.getStatus());
                prsFilmsBatch.setString(9, film.getImgLink());
                prsFilmsBatch.setString(10, film.getWatchLink());
                prsFilmsBatch.setString(11, film.getShortStory());
                prsFilmsBatch.setInt(12, film.getKinogoPage());

                prsFilmsBatch.addBatch();
                counter++;

                if (counter % 100 == 0 || counter == films.size()) {
                    System.out.println(counter);
                    prsFilmsBatch.executeBatch();
                }
            }
            return true;
        } catch (SQLException e) {
            logger.error("Can't save batch of films");
            throw new IllegalRequestException("");
        }
    }

    public boolean updateFilm(Film film) {
        try (PreparedStatement prs = connection.prepareStatement(UPDATE_FILMS_QUERY)) {
            prs.setString(1, film.getQuality());
            prs.setString(2, film.getTranslation());
            prs.setString(3, film.getName());
            prs.setInt(4, film.getReleaseYear());
            prs.setString(5, film.getShortStory());
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't update film: " + film.getName() + " release year: " + film.getReleaseYear() +
            " story: " + film.getShortStory());
            throw new IllegalRequestException("");
        }
    }

    public boolean updateBatchFilms(List<Film> films) {
        try (PreparedStatement prs = connection.prepareStatement(UPDATE_FILMS_QUERY)) {
            int counter = 0;
            for (Film film : films) {
                prs.setString(1, film.getQuality());
                prs.setString(2, film.getTranslation());
                prs.setString(3, film.getName());
                prs.setInt(4, film.getReleaseYear());
                prs.addBatch();
                counter++;
            }
            if (counter % 100 == 0 || counter == films.size()) {
                prs.executeBatch();
            }
            return true;
        } catch (SQLException e) {
            logger.error("Can't update films: " + films.toString());
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
                film.setQuality(rs.getString(4));
                film.setTranslation(rs.getString(5));
                film.setDuration(rs.getString(6));
                film.setRating(rs.getFloat(7));
                film.setUploadDate(rs.getTimestamp(8).toLocalDateTime());
                film.setStatus(rs.getInt(9));
                film.setImgLink(rs.getString(10));
                film.setWatchLink(rs.getString(11));
                film.setShortStory(rs.getString(12));
                film.setKinogoPage(rs.getInt(13));
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
                film.setQuality(rs.getString(4));
                film.setTranslation(rs.getString(5));
                film.setDuration(rs.getString(6));
                film.setRating(rs.getFloat(7));
                film.setUploadDate(rs.getTimestamp(8).toLocalDateTime());
                film.setStatus(rs.getInt(9));
                film.setImgLink(rs.getString(10));
                film.setWatchLink(rs.getString(11));
                film.setShortStory(rs.getString(12));
                film.setKinogoPage(rs.getInt(13));
            }
            return film;
        } catch (SQLException e) {
            logger.error("Film not found: " + filmName);
            throw new IllegalRequestException("");
        }
    }

    public List<Film> findLoadedFilms() {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_ALL_FILMS_QUERY)) {
            ResultSet rs = prs.executeQuery();
            List<Film> films = new ArrayList<>();
            while (rs.next()) {
                Film film = new Film();
                film.setId(rs.getInt(1));
                film.setName(rs.getString(2));
                film.setReleaseYear(rs.getInt(3));
                film.setQuality(rs.getString(4));
                film.setTranslation(rs.getString(5));
                film.setDuration(rs.getString(6));
                film.setRating(rs.getFloat(7));
                film.setUploadDate(rs.getTimestamp(8).toLocalDateTime());
                film.setStatus(rs.getInt(9));
                film.setImgLink(rs.getString(10));
                film.setWatchLink(rs.getString(11));
                film.setShortStory(rs.getString(12));
                film.setKinogoPage(rs.getInt(13));
                films.add(film);
            }
            return films;

        } catch (SQLException e) {
            logger.error("Can't select films");
            throw new IllegalRequestException("");
        }
    }

    public boolean setStatus(String filmName, int status) {
        try (PreparedStatement prs = connection.prepareStatement(UPDATE_STATUS_BY_FILM_NAME_QUERY)) {
            prs.setInt(1, status);
            prs.setString(2, filmName);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't change status: " + status);
            throw new IllegalRequestException("");
        }
    }

    public boolean addFilmToFavorites(int userId, int filmId) {
        try (PreparedStatement prs = connection.prepareStatement(ADD_TO_FAVORITES_QUERY)) {
            prs.setInt(1, userId);
            prs.setInt(2, filmId);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't add film to favorites:");
            throw new IllegalRequestException("");
        }
    }

    public boolean setForeignKeyChecks(int param) {
        try (PreparedStatement prs = connection.prepareStatement(SET_FOREIGN_KEY_CHECKS_QUERY)) {
            prs.setInt(1, param);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't set foreign key checks");
            throw new IllegalRequestException("");
        }
    }

    public void clearDatabase() {
        try (PreparedStatement prs = connection.prepareStatement(TRUNCATE_TABLE_FILMS_QUERY)) {
            prs.executeUpdate();

        } catch (SQLException e) {
            logger.error("Can't clear table films");
            throw new IllegalRequestException("");
        }
    }
}
