package com.alexdoub.gdaxmanager.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexdoub.gdaxmanager.R;
import com.alexdoub.gdaxmanager.models.ProductModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/3/2017.
 */

public class ProductRowView extends LinearLayout {

    @BindView(R.id.product_row_base_currency_tv) TextView baseCurrencyTv;
    @BindView(R.id.product_row_quote_currency_tv) TextView quoteCurrencyTv;

    public ProductRowView(Context context) {
        super(context);
        init(context);
    }

    public ProductRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProductRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_product_row, this, true);
        ButterKnife.bind(this);
    }

    public void bind(ProductModel productModel) {
        baseCurrencyTv.setText(productModel.baseCurrency);
        quoteCurrencyTv.setText(productModel.quoteCurrency);
    }
}
