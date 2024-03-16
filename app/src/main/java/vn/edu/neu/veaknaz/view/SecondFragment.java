package vn.edu.neu.veaknaz.view;

import android.os.*;
import android.view.*;

import androidx.annotation.*;
import androidx.fragment.app.*;
import androidx.navigation.fragment.*;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.databinding.*;

public class SecondFragment extends Fragment {

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState
  ) {

    binding = FragmentSecondBinding.inflate(inflater, container, false);
    return binding.getRoot();

  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        NavHostFragment.findNavController(SecondFragment.this)
            .navigate(R.id.action_SecondFragment_to_FirstFragment);
      }
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
  private FragmentSecondBinding binding;

}