package controller;

import entity.Country;
import service.CountryService;
import web.ModelAndView;
import web.View;
import web.http.HttpMethod;
import web.http.RequestMapping;

/**
 * Created by alexfomin on 06.07.17.
 */
public class CountryController implements Controller{
    private CountryService countryService;

    @RequestMapping(method = HttpMethod.POST, url = "countries/add")
    public ModelAndView add(String countryName) {
        ModelAndView view = new ModelAndView(View.COUNTRY);
        Country country = countryService.addCountry(countryName);
        view.addParam("country", country);

        return view;
    }

    @RequestMapping(method = HttpMethod.DELETE, url = "countries/delete")
    public ModelAndView delete(String countryName) {
        ModelAndView view = new ModelAndView(View.COUNTRY);
        boolean isDeleted = countryService.delete(countryName);
        view.addParam("isDeleted", isDeleted);

        return view;
    }

    @RequestMapping(method = HttpMethod.GET, url = "countries/find")
    public ModelAndView find(String countryName){
        ModelAndView view = new ModelAndView(View.COUNTRY);
        Country country = countryService.find(countryName);
        view.addParam("country", country);

        return view;
    }
}
