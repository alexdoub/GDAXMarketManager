package com.alexdoub.gdaxmanager.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.alexdoub.gdaxmanager.GDAXManagerApp;
import com.alexdoub.gdaxmanager.R;
import com.alexdoub.gdaxmanager.views.CurrencyRowView;

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
        return plural ? GDAXManagerApp.getInstance().getString(R.string.currency_name_plural) : GDAXManagerApp.getInstance().getString(R.string.currency_name);
    }
}
