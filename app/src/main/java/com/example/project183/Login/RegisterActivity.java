package com.example.project183.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project183.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, "LoginActivity onCreate called");

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button regButton = findViewById(R.id.registerButton);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            Log.d(TAG, "Login button clicked in RegisterActivity");
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        regButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add more validation if needed (e.g., email format, password strength)
            if (!isValidEmail(email)) {
                Toast.makeText(RegisterActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                return;
            }

            createAccount(email, password);
        });
    }
    // Add this method to validate email format
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        String errorMessage = getErrorMessage(task);
                        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        updateUI(null);
                    }
                });
    }

    private static @NonNull String getErrorMessage(Task<AuthResult> task) {
        String errorMessage = "Registration failed: ";
        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
            errorMessage += "Email already in use.";
        } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
            errorMessage += "Password is too weak.";
        } else {
            errorMessage += Objects.requireNonNull(task.getException()).getMessage();
        }
        return errorMessage;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in, perhaps navigate to main activity
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            // Intent to next activity or update UI elements
        } else {
            // User is null, show failed registration message
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "RegisterActivity onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "RegisterActivity onPause called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "RegisterActivity onDestroy called");
    }
}
