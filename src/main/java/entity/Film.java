package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Created by alexfomin on 01.07.17.
 */

public class Film {
    private int id;
    private String name;
    private int releaseYear;
    private int qualityId;
    private int translationId;
    private String length;
    private float rating;
    private LocalDateTime uploadDate;
    private int status;

    private ArrayList<String> countries = new ArrayList<>();
    private ArrayList<String> genres = new ArrayList<>();

    private ArrayList<Integer> countrieId = new ArrayList<>();
    private ArrayList<Integer> genreId = new ArrayList<>();

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getQuality_id() {
        return qualityId;
    }

    public void setQuality_id(int quality_id) {
        this.qualityId = quality_id;
    }

    public int getTranslationId() {
        return translationId;
    }

    public void setTranslationId(int translationId) {
        this.translationId = translationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<Integer> getCountrieId() {
        return countrieId;
    }

    public void setCountrieId(ArrayList<Integer> countrieId) {
        this.countrieId = countrieId;
    }

    public ArrayList<Integer> getGenreId() {
        return genreId;
    }

    public void setGenreId(ArrayList<Integer> genreId) {
        this.genreId = genreId;
    }
}


