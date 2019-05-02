package uk.co.joseortale.fancybeerlist.view.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import uk.co.joseortale.fancybeerlist.R;

/**
 *
 * Methods used to view as a helper
 *
 */
public class ViewHelper {

    /**
     *
     * Shows alert dialog on the view.
     *
     * @param context
     * @return
     */
    public static AlertDialog.Builder showAlertDialog(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.view_helper_error_title)
                .setMessage(R.string.view_helper_error_message)
                .setIcon(android.R.drawable.ic_dialog_alert);

        return dialog;
    }
}
