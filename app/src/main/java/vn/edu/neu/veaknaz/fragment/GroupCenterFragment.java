package vn.edu.neu.veaknaz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import vn.edu.neu.veaknaz.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupCenterFragment extends Fragment {

  public GroupCenterFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_group_center, container, false);
  }
}