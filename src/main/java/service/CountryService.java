package service;

import dao.CountryDAO;
import entity.Country;

/**
 * Created by alexfomin on 06.07.17.
 */
public class CountryService {
    private CountryDAO countryDAO;

    public Country addCountry(String countryName) {
        Country country = countryDAO.find(countryName);

        if(country==null) {
            country = new Country();
            country.setCountryName(countryName);
            countryDAO.save(country);
            return countryDAO.find(countryName);
        }
        return null;
    }

    public boolean delete(String countryName) {
        boolean isDeleted = countryDAO.delete(countryName);
        if(isDeleted) {
            return true;
        }
        return false;
    }

    public Country find(String countryName) {
        return countryDAO.find(countryName);
    }

    public Country find(int countryId){
        return countryDAO.find(countryId);
    }
}
