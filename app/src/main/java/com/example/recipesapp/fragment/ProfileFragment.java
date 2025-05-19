package com.example.recipesapp.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.recipes.R;
import com.example.recipes.databinding.FragmentProfileBinding;
import com.example.recipesapp.SettingActivity;
import com.example.recipesapp.adapters.RecipeAdapter;
import com.example.recipesapp.models.Recipe;
import com.example.recipesapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            if (isSafe()) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Login Required")
                        .setMessage("You need to login to view your profile")
                        .setPositiveButton("OK", null)
                        .show();
            }
        } else {
            loadProfile();
            init();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserRecipes();
    }

    private void init() {
        binding.imgEditProfile.setOnClickListener(v -> {
            PickImageDialog.build(new PickSetup())
                    .setOnPickResult(r -> {
                        if (!isSafe()) return;
                        Log.e("ProfileFragment", "onPickResult: " + r.getUri());
                        binding.imgProfile.setImageBitmap(r.getBitmap());
                        uploadImage(r.getBitmap());
                    })
                    .setOnPickCancel(() -> {
                        if (getContextSafe() != null) {
                            Toast.makeText(getContextSafe(), "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show(requireActivity());
        });

        binding.imgEditCover.setOnClickListener(view -> {
            PickImageDialog.build(new PickSetup())
                    .setOnPickResult(r -> {
                        if (!isSafe()) return;
                        Log.e("ProfileFragment", "onPickResult: " + r.getUri());
                        binding.imgCover.setImageBitmap(r.getBitmap());
                        binding.imgCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        uploadCoverImage(r.getBitmap());
                    })
                    .setOnPickCancel(() -> {
                        if (getContextSafe() != null) {
                            Toast.makeText(getContextSafe(), "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show(requireActivity());
        });

        binding.btnSetting.setOnClickListener(view -> {
            if (isSafe()) {
                startActivity(new Intent(requireContext(), SettingActivity.class));
            }
        });
    }

    private void uploadCoverImage(Bitmap bitmap) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images/" + FirebaseAuth.getInstance().getUid() + "cover.jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return storageRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (!isSafe()) return;

            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                if (user != null) {
                    user.setCover(Objects.requireNonNull(downloadUri).toString());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(user);
                    Toast.makeText(getContextSafe(), "Cover Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("ProfileFragment", "uploadCoverImage: user is null");
                }
            } else {
                Log.e("ProfileFragment", "uploadCoverImage failed: " + Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    private void uploadImage(Bitmap bitmap) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images/" + FirebaseAuth.getInstance().getUid() + "image.jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return storageRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (!isSafe()) return;

            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                if (user != null) {
                    user.setImage(Objects.requireNonNull(downloadUri).toString());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(user);
                    Toast.makeText(getContextSafe(), "Profile Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("ProfileFragment", "uploadImage: user is null");
                }
            } else {
                Log.e("ProfileFragment", "uploadImage failed: " + Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    private void loadUserRecipes() {
        binding.rvProfile.setLayoutManager(new GridLayoutManager(getContext(), 2));
        RecipeAdapter adapter = new RecipeAdapter();
        binding.rvProfile.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("Recipes")
                .orderByChild("authorId")
                .equalTo(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Recipe> recipes = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Recipe recipe = dataSnapshot.getValue(Recipe.class);
                            if (recipe != null) recipes.add(recipe);
                        }
                        adapter.setRecipeList(recipes);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("ProfileFragment", "Load recipes cancelled: " + error.getMessage());
                    }
                });
    }

    private void loadProfile() {
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(User.class);
                        if (user != null && isSafe()) {
                            binding.tvUserName.setText(user.getName());
                            binding.tvEmail.setText(user.getEmail());

                            Glide.with(getContextSafe())
                                    .load(user.getImage())
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(binding.imgProfile);

                            Glide.with(getContextSafe())
                                    .load(user.getCover())
                                    .centerCrop()
                                    .placeholder(R.drawable.bg_default_recipe)
                                    .into(binding.imgCover);
                        } else {
                            Log.e("ProfileFragment", "User is null or fragment not attached");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("ProfileFragment", "Load profile cancelled: " + error.getMessage());
                    }
                });
    }

    private Context getContextSafe() {
        return isAdded() ? requireContext() : null;
    }

    private boolean isSafe() {
        return isAdded() && getContextSafe() != null && binding != null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
