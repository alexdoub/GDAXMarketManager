package com.alexdoub.bitcoinmanager.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexdoub.bitcoinmanager.R;
import com.alexdoub.bitcoinmanager.models.OrderModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/3/2017.
 */

public class OrderRowView extends LinearLayout {

    @BindView(R.id.order_row_status_tv) TextView statusTv;
    @BindView(R.id.order_row_orders_tv) TextView ordersTv;
    @BindView(R.id.order_row_size_tv) TextView sizeTv;
    @BindView(R.id.order_row_price_tv) TextView priceTv;

    public OrderRowView(Context context) {
        super(context);
        init(context);
    }

    public OrderRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OrderRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_order_row, this, true);
        ButterKnife.bind(this);
    }

    public void bind(OrderModel orderModel) {
        statusTv.setText(orderModel.getStatus());
        ordersTv.setText("" + orderModel.numOrders);
        sizeTv.setText(orderModel.size);
        priceTv.setText(orderModel.price);
    }
}
