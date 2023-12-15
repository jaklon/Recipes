package com.example.recipesapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipes.R;
import com.example.recipes.databinding.ActivitySettingBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.liniearLayoutShare.setOnClickListener(view -> shareApp());
        binding.liniearLayoutRate.setOnClickListener(view -> rateApp());
        binding.liniearLayoutFeedback.setOnClickListener(view -> sendFeedback());
        binding.liniearLayoutViewMoreApps.setOnClickListener(view -> moreApps());
        binding.linearLayoutPrivacy.setOnClickListener(view -> privacyPolicy());
        binding.btnSignout.setOnClickListener(v -> signOut());
    }

    private void signOut() {
        new AlertDialog.Builder(this)
                .setTitle("SignOut")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Sign Out", (dialogInterface, i) -> {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                    finishAffinity();
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                }).show();
    }

    private void sendFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.developer_email)});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for " + getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, "Hi " + getString(R.string.developer_name) + ",");
        startActivity(Intent.createChooser(intent, "Send Feedback"));
    }

    private void moreApps() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse("https:" + getString(R.string.developer_id)));
    }

    private void privacyPolicy() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.google.com"));
        startActivity(intent);
    }

    private void rateApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse("https:" + getPackageName()));
        startActivity(intent);
    }

    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "CullinaryConnect");
        intent.putExtra(Intent.EXTRA_TEXT, "Get " + getString(R.string.app_name) + " to get the best foods for you: ");
        startActivity(Intent.createChooser(intent, "ShareApp"));
    }
}