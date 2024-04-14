package vn.edu.neu.veaknaz.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import vn.edu.neu.veaknaz.R;

public class LanguagePopupDialog extends DialogFragment {
  public void setLanguageSelectionListener(LanguageSelectionListener listener) {
    this.languageSelectionListener = listener;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

    LayoutInflater inflater = requireActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.fragment_language_popup, null);
    builder.setView(view);

    radioGroupLanguages = view.findViewById(R.id.radioGroupLanguages);
    Button btnCancel = view.findViewById(R.id.btnCancel);
    Button btnOk = view.findViewById(R.id.btnOk);

    btnCancel.setOnClickListener(v -> dismiss());

    btnOk.setOnClickListener(v -> {
      int selectedId = radioGroupLanguages.getCheckedRadioButtonId();

      if (selectedId == -1) {
        return;
      }

      if (Objects.isNull(languageSelectionListener)) {
        return;
      }

      if (selectedId == R.id.radioButtonEnglish) {
        languageSelectionListener.onLanguageSelected("en");
      } else if (selectedId == R.id.radioButtonVietnamese) {
        languageSelectionListener.onLanguageSelected("vi");
      } else if (selectedId == R.id.radioButtonJapanese) {
        languageSelectionListener.onLanguageSelected("ja");
      }

      dismiss();
    });

    return builder.create();
  }
  private RadioGroup radioGroupLanguages;
  private LanguageSelectionListener languageSelectionListener;

  public interface LanguageSelectionListener {
    void onLanguageSelected(String language);
  }
}