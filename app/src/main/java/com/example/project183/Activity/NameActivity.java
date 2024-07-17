package com.example.project183.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project183.R;

public class NameActivity extends AppCompatActivity {

        private EditText nameEditText;
        private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_name);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameEditText = findViewById(R.id.nameEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> {
            String enteredName = nameEditText.getText().toString();
            if (!enteredName.isEmpty()) {
                Intent intent = new Intent(NameActivity.this, MainActivity.class);
                intent.putExtra("NAME", enteredName);
                startActivity(intent);
            } else {
                Toast.makeText(NameActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

