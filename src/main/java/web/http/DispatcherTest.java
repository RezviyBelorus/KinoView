package web.http;

import controller.*;
import exception.IllegalRequestException;
import org.apache.commons.lang3.StringUtils;
import web.HttpStatus;
import web.ModelAndView;
import web.View;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Parameter;

/**
 * Created by alexfomin on 14.07.17.
 */
public class DispatcherTest {
    private static DispatcherTest dispatcher;

    private DispatcherTest() {

    }

    public static DispatcherTest getInstance() {
        if (dispatcher == null) {
            dispatcher = new DispatcherTest();
        }
        return dispatcher;
    }

    public ModelAndView dispatch(String url, String method, Map<String, String[]> parametersMap) {
        View view = View.USER;
        ModelAndView modelAndView = new ModelAndView(view);
        return modelAndView;
    }
}
