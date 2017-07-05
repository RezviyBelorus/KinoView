package service;

import dao.UserDAO;
import entity.User;
import exception.IllegalRequestException;
import web.response.UserDTO;

import java.time.LocalDateTime;

/**
 * Created by alexfomin on 04.07.17.
 */
//todo: что делать с дефолтным статусом?
//todo: method setStatus is correct?
//todo: method deleteUser correct or not
public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDTO login(String email, String password) {
        User user = userDAO.find(email);
        if (user == null) {
            return null;
        }
        boolean isLogined = user.getPassword().equals(password);
        if (isLogined) {
            return new UserDTO(user);
        }
        return null;
    }

    public UserDTO saveUser(String login, String password, String f_name, String l_name, String email) {
        User user = userDAO.find(email);

        if (user == null) {
            User dto = new User();
            dto.setLogin(login);
            dto.setPassword(password);
            dto.setF_name(f_name);
            dto.setLogin(l_name);
            dto.setEmail(email);
            dto.setStatus(1);
            dto.setCreate_date(LocalDateTime.now());
            userDAO.save(dto);

            user = userDAO.find(email);

            return new UserDTO(user);
        }
        return null;
    }

    public User findUserById(int id) {
        User user = userDAO.find(id);
        if (user.getStatus() < UserStatus.INACTIVE.getValue()) {
            return user;
        }
        return null;
    }

    public User findUserByLoginOrEmail(String emailOrLogin) {
        User user = userDAO.find(emailOrLogin);
        if (user.getStatus() < UserStatus.INACTIVE.getValue()) {
            return user;
        }
        return null;
    }


    public UserDTO deleteUser(String emailOrLogin) {
        boolean isDeleted = userDAO.setStatus(emailOrLogin, UserStatus.DELETED.getValue());
        if (isDeleted) {
            return new UserDTO(userDAO.find(emailOrLogin));
        }
        throw new IllegalRequestException("");
    }

    public UserDTO setStatus(String emailOrLogin, int status) {
        userDAO.setStatus(emailOrLogin, status);
        User user = userDAO.find(emailOrLogin);
        if (user.getStatus() == status) {
            return new UserDTO(user);
        } else return null;
    }

}
