package com.alexdoub.gdaxmanager.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import com.alexdoub.gdaxmanager.R;
import com.alexdoub.gdaxmanager.adapters.GenericListAdapter;
import com.alexdoub.gdaxmanager.interfaces.APICallback;
import com.alexdoub.gdaxmanager.api.APIClient;
import com.alexdoub.gdaxmanager.models.CurrencyModel;
import com.alexdoub.gdaxmanager.models.ProductModel;
import com.alexdoub.gdaxmanager.utilities.ViewUtils;
import com.alexdoub.gdaxmanager.views.ProductHeaderView;
import com.alexdoub.gdaxmanager.views.ProductRowView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Alex on 11/5/2017.
 */

public class ProductListActivity extends AppCompatActivity {

    static String KEY_CURRENCY_ID;

    @BindView(R.id.product_list_lv) ListView productListLv;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    GenericListAdapter productListAdapter;
    String currencyId = null;

    public static void StartActivity(Context c, CurrencyModel currencyModel) {
        Intent intent = new Intent(c, ProductListActivity.class);
        intent.putExtra(KEY_CURRENCY_ID, currencyModel.id);
        c.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        currencyId = getIntent().getStringExtra(KEY_CURRENCY_ID);
        if (currencyId == null) {
            Timber.e("ProductListActivity - currency null!");
        }

        setTitle(currencyId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productListAdapter = new GenericListAdapter();
        productListLv.addHeaderView(new ProductHeaderView(this));
        productListLv.setAdapter(productListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (productListAdapter.getCount() == 0) {
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
        productListAdapter.clearListContent();

        APIClient.getInstance().getProducts(new APICallback<List<ProductModel>>() {
            @Override
            public void success(List<ProductModel> list) {

                //Filter results to only ones that involve our currency
                Observable.fromArray(list.toArray())
                        .filter(new io.reactivex.functions.Predicate() {
                            @Override
                            public boolean test(Object o) throws Exception {
                                ProductModel thisProductModel = (ProductModel) o;
                                return (thisProductModel.baseCurrency.equalsIgnoreCase(currencyId) || thisProductModel.quoteCurrency.equalsIgnoreCase(currencyId));
                            }
                        }).subscribe(new io.reactivex.functions.Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        productListAdapter.addListContent((ProductModel) o);
                    }
                });

                showLoading(false);
            }

            @Override
            public void failure(Throwable throwable) {
                ViewUtils.ShowToast(ProductListActivity.this, "Failed loading " + ProductModel.GetName(true));
                showLoading(false);
            }
        });
    }

    protected void showLoading(boolean loading) {
        productListLv.setVisibility(!loading ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }


    ////////////////////////////////////////
    ///// ListView.OnItemClickListener /////
    ////////////////////////////////////////
    @OnItemClick(R.id.product_list_lv)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (view instanceof ProductRowView) {
            ProductModel productModel = (ProductModel) productListAdapter.getItem(position - 1);
            OrderListActivity.StartActivity(ProductListActivity.this, productModel);
        }
    }
}
