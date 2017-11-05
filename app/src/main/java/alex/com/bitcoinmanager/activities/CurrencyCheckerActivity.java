package alex.com.bitcoinmanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import alex.com.bitcoinmanager.R;
import alex.com.bitcoinmanager.adapters.GenericListAdapter;
import alex.com.bitcoinmanager.api.APICallback;
import alex.com.bitcoinmanager.api.APIClient;
import alex.com.bitcoinmanager.interfaces.Listable;
import alex.com.bitcoinmanager.models.CurrencyModel;
import alex.com.bitcoinmanager.views.CurrencyHeader;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CurrencyCheckerActivity extends AppCompatActivity {


    @BindView(R.id.currency_list_lv) ListView currencyListLv;
    @BindView(R.id.message_view) TextView messageViewTv;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.refresh_button) Button refreshButton;

    GenericListAdapter currencyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_price_checker);
        ButterKnife.bind(this);

        currencyListAdapter = new GenericListAdapter();
        currencyListLv.setAdapter(currencyListAdapter);
        currencyListLv.addHeaderView(new CurrencyHeader(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPrices();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @OnClick(R.id.refresh_button)
    protected void refreshPrices() {

        setLoading(true);

        APIClient.getInstance().getCurrencies(new APICallback<List<CurrencyModel>>() {
            @Override
            public void success(List<CurrencyModel> list) {
                setLoading(false);
                currencyListAdapter.setListContent(list);
            }

            @Override
            public void failure(Throwable throwable) {
                setLoading(false);
                showErrorText(getString(R.string.message_error));
            }
        });
    }

    void setLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
            currencyListLv.setVisibility(View.INVISIBLE);
            refreshButton.setEnabled(false);
            messageViewTv.setText(getString(R.string.message_loading));
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            currencyListLv.setVisibility(View.VISIBLE);
            refreshButton.setEnabled(true);
            messageViewTv.setText("");
        }
    }

    public void showErrorText(String msg) {
        currencyListLv.setVisibility(View.INVISIBLE);
        messageViewTv.setText(msg);
    }
}
