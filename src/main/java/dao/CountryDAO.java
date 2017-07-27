package dao;

import entity.Country;
import exception.IllegalRequestException;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by alexfomin on 06.07.17.
 */
public class CountryDAO extends AbstractDAO {
    Logger logger = Logger.getLogger(this.getClass());

    private String SAVE_COUNTRY_QUERY = "INSERT INTO countries (country_name) VALUES (?)";

    private String DELETE_COUNTRY_BY_NAME_QUERY = "DELETE FROM countries WHERE country_name = ?";

    private String DELETE_COUNTRY_BY_ID_QUERY = "DELETE FROM countries WHERE country_id = ?";

    private String SELECT_COUNTRY_BY_NAME_QUERY = "SELECT * FROM countries WHERE country_name = ?";

    private String SELECT_COUNTRY_BY_ID_QUERY = "SELECT * FROM countries WHERE country_id = ?";

    private String INSERT_FILM_TO_COUNTRIES_QUERY = "INSERT INTO films_to_countries VALUES (?,?)";

    private String SELECT_ALL_COUNTRIES_BY_FILM_QUERY = "SELECT c.country_name FROM countries AS c " +
            "INNER JOIN films_to_countries AS f ON c.country_id = f.country_id WHERE f.film_id = ?";

    public boolean save(Country country) {
        try (PreparedStatement prs = connection.prepareStatement(SAVE_COUNTRY_QUERY)) {
            prs.setString(1, country.getCountryName());
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't save country: " + country.getCountryName());
            throw new IllegalRequestException("");
        }
    }

    public boolean saveFilmToCountries(int filmId, int[] countries) {
        try (PreparedStatement prs = connection.prepareStatement(INSERT_FILM_TO_COUNTRIES_QUERY)) {
            int counter = 0;
            for (int countryId : countries) {
                prs.setInt(1, filmId);
                prs.setInt(2, countryId);
                prs.addBatch();
                counter++;
                if (counter % 1000 == 0 || counter == countries.length) {
                    prs.executeBatch();
                }
            }
            return true;
        } catch (SQLException e) {
            logger.error("Can't save film to countries: " + filmId);
            throw new IllegalRequestException("");
        }
    }

    public boolean delete(int id) {
        try (PreparedStatement prs = connection.prepareStatement(DELETE_COUNTRY_BY_ID_QUERY)) {
            prs.setInt(1, id);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't delete country: " + id);
            throw new IllegalRequestException("");
        }
    }

    public boolean delete(String countryName) {
        try (PreparedStatement prs = connection.prepareStatement(DELETE_COUNTRY_BY_NAME_QUERY)) {
            prs.setString(1, countryName);
            prs.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't delete country: " + countryName);
            throw new IllegalRequestException("");
        }
    }

    public Country find(int id) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_COUNTRY_BY_ID_QUERY)) {
            prs.setInt(1, id);
            ResultSet rs = prs.executeQuery();
            Country country = new Country();
            while (rs.next()) {
                country.setCountryId(rs.getInt(1));
                country.setCountryName(rs.getString(2));
            }
            return country;
        } catch (SQLException e) {
            logger.error("Can't find country: " + id);
            throw new IllegalRequestException("");
        }
    }

    public ArrayList<String> findAllByFilm(int filmId) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_ALL_COUNTRIES_BY_FILM_QUERY)) {
            prs.setInt(1, filmId);
            ResultSet rs = prs.executeQuery();
            ArrayList<String> countries = new ArrayList<>();
            while (rs.next()) {
                countries.add(rs.getString(1));
            }
            return countries;
        } catch (SQLException e) {
            logger.error("Can't get countries by film: " + filmId);
            throw new IllegalRequestException("");
        }
    }

    public Country find(String countryName) {
        try (PreparedStatement prs = connection.prepareStatement(SELECT_COUNTRY_BY_NAME_QUERY)) {
            prs.setString(1, countryName);
            ResultSet rs = prs.executeQuery();
            Country country = new Country();
            while (rs.next()) {
                country.setCountryId(rs.getInt(1));
                country.setCountryName(rs.getString(2));
            }
            return country;
        } catch (SQLException e) {
            logger.error("Can't find country: " + countryName);
            throw new IllegalRequestException("");
        }
    }
}
