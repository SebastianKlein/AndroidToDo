package com.klein.todo.Utils;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Sebastian on 18.03.2016.
 */
public class AppUtils {
    private AppUtils(){
    }


    public static void showMsg(Activity activity, String message) {
        Toast msg = Toast.makeText(activity, message, Toast.LENGTH_LONG);
        msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
                msg.getYOffset() / 2);
        msg.show();
    }
}
