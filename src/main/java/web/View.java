package web;

/**
 * Created by alexfomin on 13.07.17.
 */
public enum View {
    LOGIN("login"),
    SIGN_UP("signUp"),
    MAIN("main"),
    ERROR("error"),
    USER("user"),
    FILM("film"),
    GENRE("genre"),
    COUNTRY("country");

    private String name;
    private String fullName;
    private static final String BASE_DIRECTORY = "/WEB-INF/view/";
    private static final String JSP_SUFFIX = ".jsp";

    View(String name) {
        this.name = name;
        this.fullName = BASE_DIRECTORY + name + JSP_SUFFIX;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public static String getBaseDirectory() {
        return BASE_DIRECTORY;
    }
}
