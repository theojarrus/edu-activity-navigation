package com.example.activitynavigation;

import static android.content.Intent.ACTION_GET_CONTENT;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activitynavigation.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static final String RESULT_TEXT_KEY = "result_text_key";

    private ActivityMainBinding binding;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                showResultCode(result.getResultCode());
                if (result.getData() != null) {
                    String text = result.getData().getStringExtra(RESULT_TEXT_KEY);
                    binding.field.setText(text);
                    binding.field.clearFocus(); // Don't open keyboard
                }
            }
    );

    private final ActivityResultLauncher<String> filesResultLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            result -> showSnackBar(result.getPath())
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        setContentView(binding.getRoot());

        binding.openActivity.setOnClickListener(v -> {
            String text = binding.field.getText().toString();
            Intent intent = ExampleActivity.createIntent(this, text, RESULT_TEXT_KEY);
            activityResultLauncher.launch(intent);
        });

        binding.openCamera.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        });

        binding.chooseFile.setOnClickListener(v -> {
            // Intent intent = new Intent();
            // intent.setAction(Intent.ACTION_GET_CONTENT);
            // intent.setType("*/*");
            // startActivity(intent);
            filesResultLauncher.launch("*/*");
        });
    }

    private void showResultCode(int resultCode) {
        if (resultCode == RESULT_OK) {
            showToast("RESULT_OK");
        } else if (resultCode == RESULT_CANCELED) {
            showToast("RESULT_CANCELED");
        } else {
            showToast(String.valueOf(resultCode));
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSnackBar(String message) {
        Snackbar.make(this, binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }
}
