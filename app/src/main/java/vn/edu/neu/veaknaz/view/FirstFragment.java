package vn.edu.neu.veaknaz.view;

import android.os.*;
import android.view.*;

import androidx.annotation.*;
import androidx.fragment.app.*;
import androidx.navigation.fragment.*;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.databinding.*;

public class FirstFragment extends Fragment {

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState
  ) {

    binding = FragmentFirstBinding.inflate(inflater, container, false);
    return binding.getRoot();

  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        NavHostFragment.findNavController(FirstFragment.this)
            .navigate(R.id.action_FirstFragment_to_SecondFragment);
      }
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
  private FragmentFirstBinding binding;

}