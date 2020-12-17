package com.example.beverageapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class UserDialog extends AppCompatDialogFragment {
    private EditText id;
    private ExampleDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view1 = inflater.inflate(R.layout.input_layout, null);
        dialog.setView(view1)
                .setTitle("Log in/Sign up")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String myId = id.getText().toString();
                        listener.applyTexts(myId);
                    }
                });
        id = view1.findViewById(R.id.etEmail);
        return dialog.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (ExampleDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement example dialog");
        }
    }
    public interface ExampleDialogListener{
        void applyTexts(String id);
    }
}
