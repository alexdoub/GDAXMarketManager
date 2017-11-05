package alex.com.bitcoinmanager.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import alex.com.bitcoinmanager.R;
import alex.com.bitcoinmanager.models.CurrencyModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/3/2017.
 */

public class CurrencyHeaderView extends LinearLayout {

    public CurrencyHeaderView(Context context) {
        super(context);
        init(context);
    }

    public CurrencyHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurrencyHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_currency_header, this, true);
    }
}
