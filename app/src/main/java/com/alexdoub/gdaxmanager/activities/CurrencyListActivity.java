package com.alexdoub.gdaxmanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import com.alexdoub.gdaxmanager.R;
import com.alexdoub.gdaxmanager.adapters.GenericListAdapter;
import com.alexdoub.gdaxmanager.interfaces.APICallback;
import com.alexdoub.gdaxmanager.api.APIClient;
import com.alexdoub.gdaxmanager.models.CurrencyModel;
import com.alexdoub.gdaxmanager.utilities.ViewUtils;
import com.alexdoub.gdaxmanager.views.CurrencyHeaderView;
import com.alexdoub.gdaxmanager.views.CurrencyRowView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.reactivex.functions.Consumer;


public class CurrencyListActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    @BindView(R.id.currency_list_instructions_tv) TextView instructionsTv;
    @BindView(R.id.currency_list_lv) ListView currencyListLv;
    @BindView(R.id.message_view) TextView messageViewTv;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.refresh_button) Button refreshButton;

    GenericListAdapter currencyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_currency_list);
        ButterKnife.bind(this);

        currencyListAdapter = new GenericListAdapter();
        currencyListLv.setAdapter(currencyListAdapter);
        currencyListLv.addHeaderView(new CurrencyHeaderView(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (currencyListAdapter.getCount() == 0) {
            refreshCurrencies();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @OnClick(R.id.refresh_button)
    protected void refreshCurrencies() {

        setLoading(true);

        APIClient.getInstance().getCurrencies(new APICallback<List<CurrencyModel>>() {
            @Override
            public void success(List<CurrencyModel> list) {
                setLoading(false);
                currencyListAdapter.clearListContent();

                io.reactivex.Observable.fromArray(list.toArray())
                        .subscribe(new Consumer<Object>() {
                            public void accept(Object o) throws Exception {
                                currencyListAdapter.addListContent((CurrencyModel) o);
                            }
                        });
            }

            @Override
            public void failure(Throwable throwable) {
                setLoading(false);
                showErrorText(getString(R.string.message_loading_error, CurrencyModel.GetName(true)));
                ViewUtils.ShowToast(CurrencyListActivity.this, "Failed loading " + CurrencyModel.GetName(true));
            }
        });
    }

    void setLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
            currencyListLv.setVisibility(View.INVISIBLE);
            instructionsTv.setVisibility(View.INVISIBLE);
            refreshButton.setEnabled(false);
            messageViewTv.setText(getString(R.string.message_loading, CurrencyModel.GetName(true)));
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            currencyListLv.setVisibility(View.VISIBLE);
            instructionsTv.setVisibility(View.VISIBLE);
            refreshButton.setEnabled(true);
            messageViewTv.setText("");
        }
    }

    public void showErrorText(String msg) {
        currencyListLv.setVisibility(View.INVISIBLE);
        messageViewTv.setText(msg);
    }


    ////////////////////////////////////////
    ///// ListView.OnItemClickListener /////
    ////////////////////////////////////////
    @OnItemClick(R.id.currency_list_lv)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (view instanceof CurrencyRowView) {
            CurrencyModel currencyModel = (CurrencyModel) currencyListAdapter.getItem(position - 1);
            ProductListActivity.StartActivity(this, currencyModel);
        }
    }
}
