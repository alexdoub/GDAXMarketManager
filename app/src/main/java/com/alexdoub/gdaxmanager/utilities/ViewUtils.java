package com.alexdoub.gdaxmanager.utilities;

import android.content.Context;
import android.widget.Toast;

import com.alexdoub.gdaxmanager.GDAXManagerApp;

/**
 * Created by Alex on 11/5/2017.
 */

public class ViewUtils {

    public static String GetString(int id) {
        return GDAXManagerApp.getInstance().getString(id);
    }

    public static void ShowToast(Context context, String message) {
        ShowToast(context, message, true);
    }

    public static void ShowToast(Context context, String message, boolean isShort) {
        Toast.makeText(context, message, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }
}
