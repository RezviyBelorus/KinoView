package controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import service.AdminService;
import web.ModelAndView;
import web.View;
import web.http.HttpMethod;
import web.http.RequestMapping;

import java.time.LocalTime;

public class AdminController implements Controller {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(method = HttpMethod.POST, url = "admin/startUpdate")
    public ModelAndView startUpdate() {

        adminService.startUpdate();
        return new ModelAndView(View.MAIN);
    }

    @RequestMapping(method = HttpMethod.POST, url = "admin/setUpdateTimeHour")
    public ModelAndView setUpdateTimeHour(String hour) {
        LocalTime time = adminService.setUpdateTimeHour(hour);
        ModelAndView view = new ModelAndView(View.MAIN);
        view.addParam("time", time);

        return view;
    }
}
