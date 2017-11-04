package alex.com.bitcoinmanager.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import alex.com.bitcoinmanager.R;
import alex.com.bitcoinmanager.models.CurrencyModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/3/2017.
 */

public class CurrencyView extends LinearLayout {

    @BindView(R.id.currency_id_tv) TextView currencyIdTv;
    @BindView(R.id.currency_name_tv) TextView currencyNameTv;
    @BindView(R.id.currency_value_tv) TextView currencyValueTv;

    public CurrencyView(Context context) {
        super(context);
        init(context);
    }

    public CurrencyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurrencyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_currency, this, true);
        ButterKnife.bind(this);
    }

    public void bind(CurrencyModel currencyModel) {
        currencyIdTv.setText(currencyModel.id);
        currencyNameTv.setText(currencyModel.name);
        currencyValueTv.setText("0.0");
    }
}
