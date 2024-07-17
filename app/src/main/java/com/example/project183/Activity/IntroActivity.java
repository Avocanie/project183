package com.example.project183.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project183.Login.LoginActivity;
import com.example.project183.R;
import com.example.project183.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, LoginActivity.class)));

    }
}