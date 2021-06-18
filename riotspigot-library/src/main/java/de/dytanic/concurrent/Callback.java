package de.dytanic.concurrent;

/**
 * Created by Tareko on 19.01.2018.
 */
public interface Callback<T> {

    void call(T t);

}