package com.example.recipesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import com.example.recipes.R;
import com.example.recipes.databinding.ActivitySignUpBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSignup.setOnClickListener(view -> signup());
        binding.tvLogin.setOnClickListener(view -> finish());
    }

    private void signup() {
        String name = Objects.requireNonNull(binding.etName.getText()).toString().trim();
        String email = Objects.requireNonNull(binding.etEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.etPassword.getText()).toString().trim();
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter your name, email and password", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
        } else {
           createNewUser(name, email, password);
        }
    }

    private void createNewUser(String name, String email, String password) {
    FirebaseApp.initializeApp(this);
    FirebaseAuth auth = FirebaseAuth.getInstance();
    auth.createUserWithEmailAndPassword(email, password);
    }
}