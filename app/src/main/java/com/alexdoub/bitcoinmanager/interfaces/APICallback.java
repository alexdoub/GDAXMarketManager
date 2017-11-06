package com.alexdoub.bitcoinmanager.interfaces;

/**
 * Created by Alex on 11/3/2017.
 */

public interface APICallback<T> {

    void success(T model);

    void failure(Throwable throwable);
}
