package service;

import entity.Film;

/**
 * Created by alexfomin on 05.07.17.
 */
public enum FilmStatus {
    ACTIVE(1), INACTIVE(2), DELETED(3);

    private int value;

    FilmStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
