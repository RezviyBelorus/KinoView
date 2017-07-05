package web.response;

import dao.AbstractDAO;
import dao.UserDAO;
import entity.User;
import exception.IllegalRequestException;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Created by alexfomin on 03.07.17.
 */
public class UserDTO implements Serializable{
    private int id;
    private String login;

    private String f_name;
    private String l_name;

    private String email;
    private int status;
    private LocalDateTime create_date;

    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.f_name = user.getF_name();
        this.l_name = user.getL_name();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.create_date = user.getCreate_date();
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getF_name() {
        return f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public String getEmail() {
        return email;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }
}
