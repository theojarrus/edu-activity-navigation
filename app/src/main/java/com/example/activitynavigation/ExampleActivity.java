package com.example.activitynavigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activitynavigation.databinding.ActivityExampleBinding;

public class ExampleActivity extends AppCompatActivity {

    private static final String EXTRA_TEXT_KEY = "extra_text_key";
    private static final String EXTRA_RESULT_KEY = "extra_result_key";

    private ActivityExampleBinding binding;

    public static Intent createIntent(Context context, String text, String resultKey) {
        Intent intent = new Intent(context, ExampleActivity.class);
        intent.putExtra(EXTRA_TEXT_KEY, text);
        intent.putExtra(EXTRA_RESULT_KEY, resultKey);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExampleBinding.inflate(getLayoutInflater(), null, false);
        setContentView(binding.getRoot());

        String text = getIntent().getStringExtra(EXTRA_TEXT_KEY);
        String resultKey = getIntent().getStringExtra(EXTRA_RESULT_KEY);

        binding.field.setText(text);

        binding.save.setOnClickListener(v -> {
            String changedText = binding.field.getText().toString();
            Intent intent = new Intent();
            intent.putExtra(resultKey, changedText);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
