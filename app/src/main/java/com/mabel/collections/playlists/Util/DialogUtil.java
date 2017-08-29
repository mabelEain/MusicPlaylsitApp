package com.mabel.collections.playlists.Util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by MabelEain on 8/27/2017.
 */
public class DialogUtil extends ProgressDialog {

    public static ProgressDialog myProgressDialog(Context context) {
        DialogUtil dialog = new DialogUtil(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }

    public DialogUtil(Context context) {
        super(context);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
