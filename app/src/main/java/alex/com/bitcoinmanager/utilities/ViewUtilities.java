package alex.com.bitcoinmanager.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Alex on 11/5/2017.
 */

public class ViewUtilities {

    public static void ShowToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
