package com.alexdoub.bitcoinmanager.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexdoub.bitcoinmanager.R;
import com.alexdoub.bitcoinmanager.models.CurrencyModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/3/2017.
 */

public class CurrencyRowView extends LinearLayout {

    @BindView(R.id.currency_row_id_tv) TextView currencyIdTv;
    @BindView(R.id.currency_row_name_tv) TextView currencyNameTv;

    public CurrencyRowView(Context context) {
        super(context);
        init(context);
    }

    public CurrencyRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurrencyRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_currency_row, this, true);
        ButterKnife.bind(this);
    }

    public void bind(CurrencyModel currencyModel) {
        currencyIdTv.setText(currencyModel.id);
        currencyNameTv.setText(currencyModel.name);
    }
}
