package dao;

import entity.User;
import exception.IllegalRequestException;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by alexfomin on 30.06.17.
 */

public class UserDAO extends AbstractDAO {
    private Logger logger = Logger.getLogger(this.getClass());

    private String SAVE_USER_QUERY = "INSERT INTO users (login, password, f_name, l_name, email, status, create_date)" +
            " VALUES (?,?,?,?,?,?,?)";

    private String DELETE_BY_ID_QUERY = "DELETE FROM users WHERE user_id = ?";
    private String DELETE_BY_EMAIL_QUERY = "DELETE FROM users WHERE email = ?";

    private String SELECT_BY_ID_QUERY = "SELECT user_id, login, password, f_name, l_name, email, status, create_date" +
            " FROM users WHERE user_id = ?";

    private String SELECT_BY_EMAIL_OR_LOGIN_QUERY = "SELECT user_id, login, password, f_name, l_name, email, status, create_date " +
            "FROM users WHERE email = ? OR login = ?";

    private String UPDATE_STATUS_BY_EMAIL_OR_LOGIN_QUERY = "UPDATE users SET status = ? WHERE email = ? OR login = ?";

    public boolean save(User user) {

        try (PreparedStatement prs = connection.prepareStatement(SAVE_USER_QUERY)) {
            prs.setString(1, user.getLogin());
            prs.setString(2, user.getPassword());
            prs.setString(3, user.getfName());
            prs.setString(4, user.getlName());
            prs.setString(5, user.getEmail());
            prs.setInt(6, user.getStatus());
            prs.setTimestamp(7, Timestamp.valueOf(user.getCreateDate()));

            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("This user already exists " + user.getLogin());
            throw new IllegalRequestException("");
        }
    }

    public boolean delete(int id) {
        try (PreparedStatement prs = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            prs.setInt(1, id);
            prs.executeUpdate();
            return true;

        } catch (SQLException e) {
            logger.error("User not found ID: " + id);
            throw new IllegalRequestException("");
        }
    }

    public boolean delete(String email) {
        try (PreparedStatement prs = connection.prepareStatement(DELETE_BY_EMAIL_QUERY)) {
            prs.setString(1, email);
            prs.executeUpdate();
            return true;

        } catch (SQLException e) {
            logger.error("User not found email: " + email);
            throw new IllegalRequestException("");
        }
    }

    public User find(int id) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            prs.setInt(1, id);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setfName(rs.getString(4));
                user.setlName(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setStatus(rs.getInt(7));
                user.setCreateDate(rs.getTimestamp(8).toLocalDateTime());
                return user;
            }
            return null;
        } catch (SQLException e) {
            logger.error("User not found id: " + id);
            throw new IllegalRequestException("");
        }
    }

    public User find(String emailOrLogin) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_BY_EMAIL_OR_LOGIN_QUERY)) {
            prs.setString(1, emailOrLogin);
            prs.setString(2, emailOrLogin);

            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setfName(rs.getString(4));
                user.setlName(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setStatus(rs.getInt(7));
                user.setCreateDate(rs.getTimestamp(8).toLocalDateTime());
                return user;
            }
            return null;
        } catch (SQLException e) {
            logger.error("User not found email or login: " + emailOrLogin);
            throw new IllegalRequestException("");
        }
    }

    public boolean setStatus(String emailOrLogin, int status) {
        try (PreparedStatement prs = connection.prepareStatement(UPDATE_STATUS_BY_EMAIL_OR_LOGIN_QUERY)) {
            prs.setInt(1, status);
            prs.setString(2, emailOrLogin);
            prs.setString(3, emailOrLogin);
            prs.executeUpdate();
            return true;

        } catch (SQLException e) {
            logger.error("Can't change status: " + status);
            throw new IllegalRequestException("");
        }
    }
}
