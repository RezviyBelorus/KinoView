package web.http;

import controller.*;
import dao.UserDAO;
import exception.IllegalRequestException;
import org.apache.commons.lang3.StringUtils;
import service.UserService;
import web.HttpStatus;
import web.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;
import java.lang.reflect.Parameter;

/**
 * Created by alexfomin on 14.07.17.
 */
public class Dispatcher {
    private final List<Controller> controllers;
    private Invoker invoker = new Invoker();
    private static Dispatcher dispatcher;

    private Dispatcher() {
        final List<Controller> controllersList = new ArrayList<>();
        controllersList.add(new CountryController());
        controllersList.add(new FilmController());
        controllersList.add(new GenreController());
        controllersList.add(new UserController());

        controllers = Collections.unmodifiableList(controllersList);
    }

    public static Dispatcher getInstance() {
        if (dispatcher == null) {
            dispatcher = new Dispatcher();
        }
        return dispatcher;
    }

    public ModelAndView dispatch(String url, String method, Map<String, String[]> parametersMap,
                                 HttpServletRequest req, HttpServletResponse resp) {
        HttpMethod httpMethod = HttpMethod.valueOf(method);
        Target target = getTargetForInvoke(url, httpMethod);
        if (target != null) {
            fillTargetByParams(target, parametersMap, req, resp);
            Object result = invoker.invoke(target);
            return (ModelAndView) result;
        }
        return new ModelAndView(HttpStatus.PAGE_NOT_FOUND);
    }

    private void fillTargetByParams(Target target, Map<String, String[]> stringParametersMap,
                                    HttpServletRequest req, HttpServletResponse resp) {
        HashMap<String, Object> parametersMap = new HashMap<>();
        //v[0] - here only the one first element
        stringParametersMap.forEach((k, v) -> parametersMap.put(k, v[0]));
        parametersMap.put("request", req);
        parametersMap.put("response", resp);

        Arrays.stream(target.method.getParameters())
                .map(e -> parametersMap.get(e.getName()))
                .filter(Objects::nonNull)
                .forEach(e -> target.parameters.add(e));
    }

    private Target getTargetForInvoke(String requestedUrl, HttpMethod requestedHttpMethod) {
        Target target = null;
        for (Controller controller : controllers) {
            Method[] methods = controller.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping current = method.getAnnotation(RequestMapping.class);
                    if (requestedHttpMethod == current.method() && StringUtils.equals(requestedUrl, current.url())) {
                        target = new Target(controller, method);
                        break;
                    }
                }
            }
            if (target != null) {
                break;
            }
        }
        return target;
    }

    private static class Invoker {
        private Object invoke(Target target) {
            try {
                target.method.setAccessible(true);
                return target.method.invoke(target.controller, target.parameters.toArray());
            } catch (Exception e) {
                throw new IllegalRequestException(e.getMessage());
            }
        }
    }

    private static class Target {
        private Controller controller;
        private Method method;
        private List<Object> parameters = new ArrayList<>();

        public Target(Controller controller, Method method) {
            this.controller = controller;
            this.method = method;
        }
    }
}
