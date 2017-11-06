package com.alexdoub.gdaxmanager.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.alexdoub.gdaxmanager.GDAXManagerApp;
import com.alexdoub.gdaxmanager.R;
import com.alexdoub.gdaxmanager.api.service.response.OrderBookServiceResponse;
import com.alexdoub.gdaxmanager.utilities.ViewUtils;
import com.alexdoub.gdaxmanager.views.OrderRowView;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Alex on 11/5/2017.
 */

public class OrderModel extends BaseModel {

    private static OrderModelTransformer AskTransformer = new OrderModelTransformer(true);
    private static OrderModelTransformer BidTransformer = new OrderModelTransformer(false);

    public String price;
    public String size;
    public long numOrders;
    public boolean ask;

    protected OrderModel(String price, String size, long numOrders, boolean ask) {
        this.price = price;
        this.size = size;
        this.numOrders = numOrders;
        this.ask = ask;
    }

    public View getView(Context context, View convertView, ViewGroup parent) {
        OrderRowView orderRowView;
        if (convertView != null && convertView instanceof OrderRowView) {
            orderRowView = (OrderRowView) convertView;
        } else {
            orderRowView = new OrderRowView(context);
        }
        orderRowView.bind(this);
        return orderRowView;
    }

    public static String GetName(boolean plural) {
        return plural ? GDAXManagerApp.getInstance().getString(R.string.order_name_plural) : GDAXManagerApp.getInstance().getString(R.string.order_name);
    }

    public String getStatus() {
        return ask ? ViewUtils.GetString(R.string.order_list_status_ask) : ViewUtils.GetString(R.string.order_list_status_bid);
    }

    public static Observable<OrderModel> TransformObservable(OrderBookServiceResponse responseModel) {
        Observable askObservable = Observable.fromArray(responseModel.asks).map(AskTransformer);
        Observable bidObservable = Observable.fromArray(responseModel.bids).map(BidTransformer);

        return Observable.concat(askObservable, bidObservable);
    }

}

//Transforms object array into OrderModel
class OrderModelTransformer implements io.reactivex.functions.Function {

    private boolean ask;

    protected OrderModelTransformer(boolean ask) {
        this.ask = ask;
    }

    public Object apply(Object o) throws Exception {
        //String array into OrderModel
        try {
            Object[] input = (Object[]) o;
            return new OrderModel((String) input[0], (String) input[1], ((Double) input[2]).intValue(), ask);
        } catch (Exception e) {
            Timber.e("OrderModelTransformer - exception:" + e.getLocalizedMessage());
            return null;
        }
    }
}