package alex.com.bitcoinmanager.interfaces;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alex on 11/3/2017.
 */

public interface Listable {

    View getView(Context context, View convertView, ViewGroup parent);
}
