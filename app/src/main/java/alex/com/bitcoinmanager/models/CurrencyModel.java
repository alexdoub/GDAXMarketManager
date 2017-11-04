package alex.com.bitcoinmanager.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import alex.com.bitcoinmanager.R;
import alex.com.bitcoinmanager.interfaces.Listable;
import alex.com.bitcoinmanager.views.CurrencyView;

/**
 * Created by Alex on 11/3/2017.
 */

public class CurrencyModel implements Listable {

    public String id;
    public String name;
    public String min_size;

    public View getView(Context context, View convertView, ViewGroup parent) {
        CurrencyView currencyView;
        if (convertView != null && convertView instanceof CurrencyView) {
            currencyView = (CurrencyView) convertView;
        } else {
            currencyView = new CurrencyView(context);
        }
        currencyView.bind(this);
        return currencyView;
    }
}
