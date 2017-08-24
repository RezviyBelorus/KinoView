package service;

import dao.CountryDAO;
import dao.FilmDAO;
import dao.GenreDAO;
import dao.UserDAO;
import entity.Country;
import entity.Film;
import entity.Genre;
import exception.IllegalRequestException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import web.response.FilmDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.Validator.validateFloat;
import static util.Validator.validateInt;


/**
 * Created by alexfomin on 05.07.17.
 */

public class FilmService {
    private static final Logger logger = Logger.getLogger(FilmService.class);
    private FilmDAO filmDAO;
    private UserDAO userDAO;
    private GenreDAO genreDAO;
    private CountryDAO countryDAO;

    public FilmService() {
        filmDAO = new FilmDAO();
        userDAO = new UserDAO();
        genreDAO = new GenreDAO();
        countryDAO = new CountryDAO();
    }

    public FilmDTO save(String filmName, String releaseYear, String quality, String translation,
                        String duration, String rating, String imgLink, String watchLink, String shortStory,
                        int kinogoPage, String genres, String countries) {
        Film film = filmDAO.find(filmName);
        if (film == null) {
            film = new Film();
            film.setName(filmName);
            film.setReleaseYear(validateInt(releaseYear));
            film.setQuality(quality);
            film.setTranslation(translation);
            film.setDuration(duration);
            film.setRating(validateFloat(rating));
            film.setUploadDate(LocalDateTime.now());
            film.setStatus(1);
            film.setImgLink(imgLink);
            film.setWatchLink(watchLink);
            film.setShortStory(shortStory);
            film.setKinogoPage(kinogoPage);
            filmDAO.save(film);

            String[] byGenre = genres.split(", ");
            String[] byCountry = countries.split(", ");

            saveGenres(filmName, Arrays.asList(byGenre));
            saveCountries(filmName, Arrays.asList(byCountry));

            film = filmDAO.find(filmName);
            return new FilmDTO(film);
        }
        return null;
    }

    public List<Film> saveBatch(List<Film> films) {
        List<Film> filmsToSave = new ArrayList<>();
        filmsToSave.addAll(films);
        List<Film> filmsInDB = filmDAO.findLoadedFilms();
        filmsToSave.removeAll(filmsInDB);
        for (Film film : filmsToSave) {
            saveGenres(film.getName(), film.getGenres());
            saveCountries(film.getName(), film.getCountries());
        }
        boolean isSaved = filmDAO.saveBatch(filmsToSave);
        if (isSaved) {
            return filmsToSave;
        }
        logger.error("Can't save batch of films");
        throw new IllegalRequestException("");
    }

    public FilmDTO updateFilm(Film film) {
        Film dao = filmDAO.find(film.getName());
        if (film.getName() == dao.getName() && film.getReleaseYear() == dao.getReleaseYear() && film.getKinogoPage() < dao.getKinogoPage()) {
            filmDAO.updateFilm(film);
        }
        Film updatedFilm = filmDAO.find(film.getName());
        if(updatedFilm.getName() !=null) {
            updatedFilm.setCountries(findCountries(updatedFilm.getId()));
            updatedFilm.setGenres(findGenres(updatedFilm.getId()));
            return new FilmDTO(updatedFilm);
        }
        logger.error("Can't update film");
        throw new IllegalRequestException("");
    }

    public List<Film> updateBatchFilms(List<Film> films) {
        List<Film> filmsToUpdate = new ArrayList<>();
        List<Film> filmsInDB = filmDAO.findLoadedFilms();

        if (filmsInDB.size() == 0) {
            return filmsToUpdate;
        }
        for (Film potentialToUpdateFilm : films) {
            for (Film filmInDB : filmsInDB) {
                if (potentialToUpdateFilm.equals(filmInDB)
                        && potentialToUpdateFilm.getKinogoPage() < filmInDB.getKinogoPage()) {
                    filmsToUpdate.add(potentialToUpdateFilm);
                }
            }
        }
        filmDAO.updateBatchFilms(filmsToUpdate);
        List<Film> updatedFilms = new ArrayList<>();
        filmsToUpdate.forEach(film -> {
            Film updatedFilm = filmDAO.find(film.getName());
            if(updatedFilm.getName() != null) {
                updatedFilm.setCountries(findCountries(updatedFilm.getId()));
                updatedFilm.setGenres(findGenres(updatedFilm.getId()));
                updatedFilms.add(updatedFilm);
            }
        });
        return updatedFilms;
    }


    public FilmDTO delete(String filmName) {
        boolean isDeleted = filmDAO.setStatus(filmName, FilmStatus.DELETED.getValue());
        if (isDeleted) {
            return new FilmDTO(filmDAO.find(filmName));
        }
        logger.error("Can't delete film");
        throw new IllegalRequestException("");
    }

    public FilmDTO find(String filmName) {
        Film film = filmDAO.find(filmName);
        if (film.getStatus() == FilmStatus.ACTIVE.getValue()) {

            film.setGenres(findGenres(film.getId()));
            film.setCountries(findCountries(film.getId()));

            return new FilmDTO(film);
        }
        return null;
    }

    public List<Film> findLoadedFilms() {
        List<Film> loadedFilms = new ArrayList<>();
        loadedFilms.addAll(filmDAO.findLoadedFilms());
        for (Film loadedFilm : loadedFilms) {
            loadedFilm.setGenres(findGenres(loadedFilm.getId()));
            loadedFilm.setCountries(findCountries(loadedFilm.getId()));
        }
        return loadedFilms;
    }

    public List<String> findGenres(int filmId) {
        return genreDAO.findAllByFilm(filmId);
    }

    public List<String> findCountries(int filmId) {
        return countryDAO.findAllByFilm(filmId);
    }

    public FilmDTO setStatus(String filmName, String status) {
        filmDAO.setStatus(filmName, validateInt(status));
        Film film = filmDAO.find(filmName);
        return new FilmDTO(film);
    }

    public boolean addToFavorites(String userEmailOrLogin, String filmName) {
        int userId = userDAO.find(userEmailOrLogin).getId();
        int filmId = filmDAO.find(filmName).getId();
        boolean isAdded = filmDAO.addFilmToFavorites(userId, filmId);
        if (isAdded) {
            return true;
        }
        return false;
    }

    public boolean clearTableFilms() {
        boolean isKeyChecksDisable = filmDAO.setForeignKeyChecks(0);
        if (isKeyChecksDisable) {
            filmDAO.clearDatabase();
            filmDAO.setForeignKeyChecks(1);
            return true;
        }
        return false;
    }

    private boolean saveGenres(String filmName, List<String> filmGenres) {
        Film film = filmDAO.find(filmName);
        int filmId = film.getId();

        List<Integer> genresId = new ArrayList<>();
        List<Genre> allGenres = genreDAO.findAllGenres();
        for (String filmGenre : filmGenres) {
            for (Genre genreInDB : allGenres) {
                if (filmGenre.equals(genreInDB.getGenreName())) {
                    genresId.add(genreInDB.getGenreId());
                }
            }
        }

        return genreDAO.saveFilmToGenre(filmId, genresId);
    }

    private boolean saveCountries(String filmName, List<String> countries) {
        int filmId = filmDAO.find(filmName).getId();
        List<Integer> countriesId = new ArrayList<>();
        List<Country> allCountries = countryDAO.findAllCountries();
        for (String country : countries) {
            for (Country countryInDB : allCountries) {
                if (country.equals(countryInDB.getCountryName())) {
                    countriesId.add(countryInDB.getCountryId());
                }
            }
        }
        return countryDAO.saveFilmToCountries(filmId, countriesId);
    }
}
