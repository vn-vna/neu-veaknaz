package vn.edu.neu.veaknaz.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import vn.edu.neu.veaknaz.R;

public class LanguagePopupDialog extends DialogFragment {
    private RadioGroup radioGroupLanguages;

    public interface LanguageSelectionListener {
        void onLanguageSelected(String language);
    }

    private LanguageSelectionListener languageSelectionListener;

    public void setLanguageSelectionListener(LanguageSelectionListener listener) {
        this.languageSelectionListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Choose Language");

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_language_popup, null);
        builder.setView(view);

        radioGroupLanguages = view.findViewById(R.id.radioGroupLanguages);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnOk = view.findViewById(R.id.btnOk);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupLanguages.getCheckedRadioButtonId();
                RadioButton radioButton = view.findViewById(selectedId);
                String selectedLanguage = radioButton.getText().toString();

                if (languageSelectionListener != null) {
                    languageSelectionListener.onLanguageSelected(selectedLanguage);
                }

                dismiss();
            }
        });

        return builder.create();
    }
}