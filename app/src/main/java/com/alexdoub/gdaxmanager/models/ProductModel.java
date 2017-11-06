package com.alexdoub.gdaxmanager.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.annotations.SerializedName;

import com.alexdoub.gdaxmanager.GDAXManagerApp;
import com.alexdoub.gdaxmanager.R;
import com.alexdoub.gdaxmanager.views.ProductRowView;

/**
 * Created by Alex on 11/3/2017.
 */

public class ProductModel extends BaseModel {

    @SerializedName("base_currency") public String baseCurrency;
    @SerializedName("quote_currency") public String quoteCurrency;
    @SerializedName("base_min_size") public String baseMinSize;
    @SerializedName("base_max_size") public String baseMaxSize;
    @SerializedName("quote_increment") public String quoteIncrement;

    public View getView(Context context, View convertView, ViewGroup parent) {
        ProductRowView productRowView;
        if (convertView != null && convertView instanceof ProductRowView) {
            productRowView = (ProductRowView) convertView;
        } else {
            productRowView = new ProductRowView(context);
        }
        productRowView.bind(this);
        return productRowView;
    }

    public static String GetName(boolean plural) {
        return plural ? GDAXManagerApp.getInstance().getString(R.string.product_name_plural) : GDAXManagerApp.getInstance().getString(R.string.product_name);
    }
}
