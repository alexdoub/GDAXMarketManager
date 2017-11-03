package alex.com.bitcoinmanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import alex.com.bitcoinmanager.R;
import alex.com.bitcoinmanager.api.APICallback;
import alex.com.bitcoinmanager.api.APIClient;
import alex.com.bitcoinmanager.models.CoinbasePriceModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PriceCheckerActivity extends AppCompatActivity {


    @BindView(R.id.current_price_tv) TextView currentPriceTv;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.refresh_button) Button refreshButton;

    double latestPrices = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_price_checker);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setLoading(false);
//        if (latestPrices <= 0) {
//            refreshPrices();
//        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @OnClick(R.id.refresh_button)
    protected void refreshPrices() {

        setLoading(true);

        //do api call
        APIClient.getInstance().getPrices(new APICallback<CoinbasePriceModel>() {
            @Override
            public void success(CoinbasePriceModel model) {
                latestPrices = model.price;
                setLoading(false);
            }

            @Override
            public void failure(Throwable throwable) {
                currentPriceTv.setText(getString(R.string.current_price_error));
            }
        });
    }

    protected void setLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
            refreshButton.setEnabled(false);
            currentPriceTv.setText(getResources().getText(R.string.current_price_loading));
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            refreshButton.setEnabled(true);
            currentPriceTv.setText(String.format(getString(R.string.current_price), latestPrices));
        }
    }

}
