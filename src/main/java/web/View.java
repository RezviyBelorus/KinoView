package web;

/**
 * Created by alexfomin on 13.07.17.
 */
public enum View {
    LOGIN("login"),
    MAIN("main"),
    ERROR("error"),
    USER("user"),
    FILM("film"),
    GENRE("genre"),
    COUNTRY("country");

    private String name;

    View(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
