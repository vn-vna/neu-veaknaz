package vn.edu.neu.veaknaz.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.edu.neu.veaknaz.R;
import vn.edu.neu.veaknaz.fragment.CreateGroupDialog;
import vn.edu.neu.veaknaz.fragment.InviteUserDialog;
import vn.edu.neu.veaknaz.fragment.LanguagePopupDialog;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button buttonLanguage = findViewById(R.id.ButtonLanguage);
        Button buttongroup = findViewById(R.id.CreateGroup);
        Button buttonInvite = findViewById(R.id.btnInviteUser);
        buttonLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguagePopupDialog languagePopupDialog = new LanguagePopupDialog();
                languagePopupDialog.show(getSupportFragmentManager(), "LanguagePopupDialog");
            }
        });

        buttongroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateGroupDialog createGroupDialog = new CreateGroupDialog();
                createGroupDialog.show(getSupportFragmentManager(), "CreateGroupPopupDialog");
            }
        });

        buttongroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateGroupDialog createGroupDialog = new CreateGroupDialog();
                createGroupDialog.show(getSupportFragmentManager(), "CreateGroupPopupDialog");
            }
        });

        buttonInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InviteUserDialog inviteUserDialog = new InviteUserDialog();
                inviteUserDialog.show(getSupportFragmentManager(), "InviteMemberPopupDialog");
            }
        });


    }




}