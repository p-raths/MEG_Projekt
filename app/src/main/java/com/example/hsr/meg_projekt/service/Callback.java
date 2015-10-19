package com.example.hsr.meg_projekt.service;

/**
 * Created by praths on 19.10.15.
 */
public interface Callback<T> {
    void onCompletion(T input);
    void onError(String message);
}