package net.formula97.android.NumbersCalc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by HAJIME on 2015/01/18.
 */
public class SelectDialog extends DialogFragment {

    public static final String FRAGMENT_TAG = SelectDialog.class.getName() + ".FRAGMENT_TAG";

    public SelectDialog() { }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String[] items = {
                getString(R.string.numbers_3),
                getString(R.string.numbers_4)
        };
        builder.setTitle(R.string.winning_number_info)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = "";
                        if (which == 0) {
                            url = "http://www.mizuhobank.co.jp/takarakuji/numbers/numbers3/index.html";
                        } else {
                            url = "http://www.mizuhobank.co.jp/takarakuji/numbers/numbers4/index.html";
                        }
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        getDialog().dismiss();
                    }
                });

        return builder.create();
    }
}
