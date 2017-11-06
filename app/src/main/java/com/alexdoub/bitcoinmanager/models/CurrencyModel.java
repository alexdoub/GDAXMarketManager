package com.alexdoub.bitcoinmanager.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.alexdoub.bitcoinmanager.BitcoinManagerApp;
import com.alexdoub.bitcoinmanager.R;
import com.alexdoub.bitcoinmanager.views.CurrencyRowView;

/**
 * Created by Alex on 11/3/2017.
 */

public class CurrencyModel extends BaseModel {

    public String name;

    public View getView(Context context, View convertView, ViewGroup parent) {
        CurrencyRowView currencyRowView;
        if (convertView != null && convertView instanceof CurrencyRowView) {
            currencyRowView = (CurrencyRowView) convertView;
        } else {
            currencyRowView = new CurrencyRowView(context);
        }
        currencyRowView.bind(this);
        return currencyRowView;
    }

    public static String GetName(boolean plural) {
        return plural ? BitcoinManagerApp.getInstance().getString(R.string.currency_name_plural) : BitcoinManagerApp.getInstance().getString(R.string.currency_name);
    }
}
