package entity;

import java.util.ArrayList;

/**
 * Created by alexfomin on 06.07.17.
 */
public class Country {
    private int countryId;
    private String countryName;
    private ArrayList<String> countries;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }
}
