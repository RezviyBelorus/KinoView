package service;

import dao.UserDAO;
import entity.User;
import exception.IllegalRequestException;
import web.response.UserDTO;

import java.time.LocalDateTime;

import static util.Validator.validateInt;

/**
 * Created by alexfomin on 04.07.17.
 */
public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDTO login(String emailOrLogin, String password) {
        User user = userDAO.find(emailOrLogin);
        if (user == null) {
            return null;
        }
        boolean isLogined = user.getPassword().equals(password);
        if (isLogined) {
            return new UserDTO(user);
        }
        return null;
    }

    //todo: у ДАО два разных. две переменные юзер - я оставил как есть
    public UserDTO save(String login, String password, String f_name, String l_name, String email) {
        User user = userDAO.find(email);
        User user1 = userDAO.find(login);

        if (user == null && user1 == null) {
            User dto = new User();
            dto.setLogin(login);
            dto.setPassword(password);
            dto.setfName(f_name);
            dto.setLogin(l_name);
            dto.setEmail(email);
            dto.setStatus(1);
            dto.setCreateDate(LocalDateTime.now());
            userDAO.save(dto);

            user = userDAO.find(email);

            return new UserDTO(user);
        }
        return null;
    }

    public User find(int id) {
        User user = userDAO.find(id);
        if (user.getStatus() < UserStatus.INACTIVE.getValue()) {
            return user;
        }
        return null;
    }

    public User find(String emailOrLogin) {
        User user = userDAO.find(emailOrLogin);
        if (user.getStatus() < UserStatus.INACTIVE.getValue()) {
            return user;
        }
        return null;
    }


    public UserDTO delete(String emailOrLogin) {
        boolean isDeleted = userDAO.setStatus(emailOrLogin, UserStatus.DELETED.getValue());
        if (isDeleted) {
            return new UserDTO(userDAO.find(emailOrLogin));
        }
        throw new IllegalRequestException("");
    }

    public UserDTO setStatus(String emailOrLogin, String status) {
        userDAO.setStatus(emailOrLogin, validateInt(status));
        User user = userDAO.find(emailOrLogin);
        if (user.getStatus() == validateInt(status)) {
            return new UserDTO(user);
        } else return null;
    }
}
