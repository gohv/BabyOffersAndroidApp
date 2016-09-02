package gohv.github.com.babyoffers.AlertDialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import gohv.github.com.babyoffers.R;

/**
 * Created by gohv on 02.09.16.
 */
public class AlertDialogNoNetwork extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection!")
                .setMessage("Моля проверете дали имете интернет на телефона!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        exit();
                    }
                });

        android.app.AlertDialog dialog = builder.create();
        return dialog;
    }

    private void exit() {
        System.exit(1);
    }
}
