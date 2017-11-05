package alex.com.bitcoinmanager.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import alex.com.bitcoinmanager.R;

/**
 * Created by Alex on 11/3/2017.
 */

public class ProductHeaderView extends LinearLayout {

    public ProductHeaderView(Context context) {
        super(context);
        init(context);
    }

    public ProductHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProductHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_product_header, this, true);
    }
}
