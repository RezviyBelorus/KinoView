package dao;

import entity.Genre;
import exception.IllegalRequestException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alexfomin on 03.07.17.
 */
public class GenreDAO extends AbstractDAO{
    private Logger logger = Logger.getLogger(this.getClass());

    private String SAVE_GENRE_QUERY = "INSERT INTO genre_type (genre_name) VALUES (?)";

    private String DELETE_GENRE_BY_ID_QUERY = "DELETE from genre_type WHERE genre_id = ?";

    private String DELETE_GENRE_BY_NAME_QUERY = "DELETE from genre_type WHERE genre_name = ?";

    private String SELECT_GENRE_BY_ID_QUERY = "SELECT genre_id, genre_name FROM genre_type WHERE genre_id = ?";

    private String SELECT_GENRE_BY_GENRE_NAME_QUERY = "SELECT genre_id, genre_name FROM genre_type WHERE genre_name = ?";

    public boolean save(Genre genre){
        try (PreparedStatement prs = connection.prepareStatement(SAVE_GENRE_QUERY)){
            prs.setString(1, genre.getGenre_name());
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't save genre: " + genre.getGenre_name());
            throw new IllegalRequestException("");
        }
    }

    public boolean delete(int id){
        try (PreparedStatement prs = connection.prepareStatement(DELETE_GENRE_BY_ID_QUERY)){
            prs.setInt(1, id);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't delete genre: " + id);
            throw new IllegalRequestException("");
        }
    }

    public boolean delete(String genreName){
        try (PreparedStatement prs = connection.prepareStatement(DELETE_GENRE_BY_NAME_QUERY)){
            prs.setString(1, genreName);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't delete genre: " + genreName);
            throw new IllegalRequestException("");
        }
    }

    public Genre find(int id) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_GENRE_BY_ID_QUERY)){
            prs.setInt(1, id);

            Genre genres = new Genre();

            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                genres.setGenre_id(rs.getInt(1));
                genres.setGenre_name(rs.getString(2));
            }
            return genres;
        } catch (SQLException e) {
            logger.error("Can't find genre: " + id);
            throw new IllegalRequestException("");
        }
    }

    public Genre find(String genreName) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_GENRE_BY_GENRE_NAME_QUERY)){
            prs.setString(1, genreName);

            Genre genres = new Genre();

            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                genres.setGenre_id(rs.getInt(1));
                genres.setGenre_name(rs.getString(2));
            }
            return genres;
        } catch (SQLException e) {
            logger.error("Can't find genre: " + genreName);
            throw new IllegalRequestException("");
        }
    }
}
