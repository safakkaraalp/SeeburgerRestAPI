package com.example.clientcleaningcenter.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientcleaningcenter.R;
import com.example.clientcleaningcenter.repository.InstanceRepository;
import com.example.clientcleaningcenter.repository.LoginRepository;
import com.example.clientcleaningcenter.util.TokenManager;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;

    private LoginRepository loginRepository;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        loginRepository = new LoginRepository();
        tokenManager = new TokenManager(this);

        loginButton.setOnClickListener(view -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                        LoginActivity.this,
                        "Bitte Benutzername und Passwort eingeben.",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            loginButton.setEnabled(false);
            loginButton.setText("Anmelden...");

            loginRepository.login(username, password, new LoginRepository.LoginCallback() {
                @Override
                public void onSuccess(String token) {
                    tokenManager.saveToken(token);

                    //Token wird auch an das Repository übergeben.
                    //Dadurch kann InstanceRepository den Token beim REST-Aufruf mitsenden.
                    InstanceRepository.setAuthToken(token);

                    loginButton.setEnabled(true);
                    loginButton.setText("Login");

                    Intent intent = new Intent(LoginActivity.this, InstanceActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onError(String message) {
                    loginButton.setEnabled(true);
                    loginButton.setText("Login");

                    Toast.makeText(
                            LoginActivity.this,
                            message,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });
    }
}