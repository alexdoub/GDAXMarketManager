package com.alexdoub.bitcoinmanager.utilities;

import android.content.Context;
import android.widget.Toast;

import com.alexdoub.bitcoinmanager.BitcoinManagerApp;

/**
 * Created by Alex on 11/5/2017.
 */

public class ViewUtils {

    public static String GetString(int id) {
        return BitcoinManagerApp.getInstance().getString(id);
    }

    public static void ShowToast(Context context, String message) {
        ShowToast(context, message, true);
    }

    public static void ShowToast(Context context, String message, boolean isShort) {
        Toast.makeText(context, message, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }
}
