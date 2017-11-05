package alex.com.bitcoinmanager.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import alex.com.bitcoinmanager.R;
import alex.com.bitcoinmanager.models.CurrencyModel;
import timber.log.Timber;

/**
 * Created by Alex on 11/5/2017.
 */

public class ProductListActivity extends AppCompatActivity {

    static String KEY_CURRENCY_ID;

    String currencyId = null;

    public static void StartActivity(Context c, CurrencyModel currencyModel) {
        Intent intent = new Intent(c, ProductListActivity.class);
        intent.putExtra(KEY_CURRENCY_ID, currencyModel.id);
        c.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_product_list);


        currencyId = getIntent().getStringExtra(KEY_CURRENCY_ID);
        if (currencyId == null) {
            Timber.e("ProductListActivity - currency null!");
        }

        setTitle(currencyId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
