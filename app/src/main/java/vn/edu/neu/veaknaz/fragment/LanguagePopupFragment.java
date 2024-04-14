package vn.edu.neu.veaknaz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.neu.veaknaz.R;

public class LanguagePopupFragment extends Fragment {

    private RadioGroup radioGroupLanguages;
    private Button btnCancel;
    private Button btnOk;

    // Interface for communicating language selection to the hosting Activity
    public interface LanguageSelectionListener {
        void onLanguageSelected(String language);
    }

    // Listener instance
    private LanguageSelectionListener languageSelectionListener;

    // Setter method for the listener
    public void setLanguageSelectionListener(LanguageSelectionListener listener) {
        this.languageSelectionListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_language_popup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        radioGroupLanguages = view.findViewById(R.id.radioGroupLanguages);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOk = view.findViewById(R.id.btnOk);

        // Button click listeners
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the fragment
                getParentFragmentManager().popBackStack();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected language
                int selectedId = radioGroupLanguages.getCheckedRadioButtonId();
                RadioButton radioButton = view.findViewById(selectedId);
                String selectedLanguage = radioButton.getText().toString();

                // Pass the selected language to the listener
                if (languageSelectionListener != null) {
                    languageSelectionListener.onLanguageSelected(selectedLanguage);
                }

                // Dismiss the fragment
                getParentFragmentManager().popBackStack();
            }
        });
    }
}