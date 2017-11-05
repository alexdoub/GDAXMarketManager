package alex.com.bitcoinmanager.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import alex.com.bitcoinmanager.R;
import alex.com.bitcoinmanager.models.CurrencyModel;
import alex.com.bitcoinmanager.models.ProductModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/3/2017.
 */

public class ProductRowView extends LinearLayout {

    @BindView(R.id.currency_id_tv) TextView currencyIdTv;
    @BindView(R.id.currency_name_tv) TextView currencyNameTv;

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
        currencyIdTv.setText(productModel.baseCurrency);
        currencyNameTv.setText(productModel.quoteCurrency);
    }
}
