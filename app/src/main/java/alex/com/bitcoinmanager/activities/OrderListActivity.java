package alex.com.bitcoinmanager.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import alex.com.bitcoinmanager.R;
import alex.com.bitcoinmanager.adapters.GenericListAdapter;
import alex.com.bitcoinmanager.api.APIClient;
import alex.com.bitcoinmanager.interfaces.APICallback;
import alex.com.bitcoinmanager.models.OrderModel;
import alex.com.bitcoinmanager.models.ProductModel;
import alex.com.bitcoinmanager.utilities.ViewUtils;
import alex.com.bitcoinmanager.views.OrderHeaderView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by Alex on 11/5/2017.
 */

public class OrderListActivity extends AppCompatActivity {

    static String KEY_PRODUCT_ID;

    @BindView(R.id.message_view) TextView messageTv;
    @BindView(R.id.order_list_lv) ListView orderListLv;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    GenericListAdapter orderListAdapter;
    String productId = null;

    public static void StartActivity(Context c, ProductModel productModel) {
        Intent intent = new Intent(c, OrderListActivity.class);
        intent.putExtra(KEY_PRODUCT_ID, productModel.id);
        c.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);

        productId = getIntent().getStringExtra(KEY_PRODUCT_ID);
        if (productId == null) {
            Timber.e("OrderListActivity - currency null!");
        }

        setTitle(productId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderListAdapter = new GenericListAdapter();
        orderListLv.addHeaderView(new OrderHeaderView(this));
        orderListLv.setAdapter(orderListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (orderListAdapter.getCount() == 0) {
            refreshProducts();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @OnClick(R.id.refresh_button)
    protected void refreshProducts() {

        showLoading(true);
        orderListAdapter.clearListContent();

        APIClient.getInstance().getOrderBook(productId, new APICallback<Observable<OrderModel>>() {
            @Override
            public void success(Observable<OrderModel> observable) {
                showLoading(false);
                observable.doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (orderListAdapter.getCount() == 0) {
                            messageTv.setText(R.string.message_no_results);
                        }
                    }
                }).subscribe(new Consumer<OrderModel>() {
                    @Override
                    public void accept(OrderModel orderModel) throws Exception {
                        orderListAdapter.addListContent(orderModel);
                    }
                });
            }

            @Override
            public void failure(Throwable throwable) {
                showLoading(false);
                ViewUtils.ShowToast(OrderListActivity.this, "Failed getting order book for product: " + productId);
            }
        });
    }

    protected void showLoading(boolean loading) {

        orderListLv.setVisibility(!loading ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);

        messageTv.setText(loading ? getString(R.string.message_loading_for, OrderModel.GetName(true), productId) : "");
    }


    ////////////////////////////////////////
    ///// ListView.OnItemClickListener /////
    ////////////////////////////////////////
    @OnItemClick(R.id.order_list_lv)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }
}
