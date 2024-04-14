package vn.edu.neu.veaknaz.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.fragment.CreateGroupFragment;
import vn.edu.neu.veaknaz.fragment.LanguagePopupFragment;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button buttonLanguage = findViewById(R.id.ButtonLanguage);
        Button buttongroup = findViewById(R.id.CreateGroup);
        buttonLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguagePopupFragment languagePopupFragment = new LanguagePopupFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, languagePopupFragment).addToBackStack(null).commit();
            }
        });

        buttongroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateGroupFragment CreateGroupFragment = new CreateGroupFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, CreateGroupFragment).addToBackStack(null).commit();
            }
        });
    }




}