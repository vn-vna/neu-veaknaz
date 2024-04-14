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

public class ColorModeDialog extends DialogFragment {
    private RadioGroup radioGroupModes;

    public interface ModeSelectionListener {
        void onModeSelected(String Mode);
    }

    private ModeSelectionListener ModeSelectionListener;

    public void setModeSelectionListener(ModeSelectionListener listener) {
        this.ModeSelectionListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_color_mode, null);
        builder.setView(view);

        radioGroupModes = view.findViewById(R.id.radioGroupModes);
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
                int selectedId = radioGroupModes.getCheckedRadioButtonId();
                RadioButton radioButton = view.findViewById(selectedId);
                String selectedMode = radioButton.getText().toString();

                if (ModeSelectionListener != null) {
                    ModeSelectionListener.onModeSelected(selectedMode);
                }

                dismiss();
            }
        });

        return builder.create();
    }
}