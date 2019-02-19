package com.example.basiclogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private Button signup;

    public static final String EXTRA_USERNAME = "the username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireWidgets();

        //initialize Backendless connection
        Backendless.initApp(this, Credentials.APP_ID, Credentials.API_KEY);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToBackendless();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendUsername = new Intent(LoginActivity.this, CreateAccountActivity.class);
                String message = username.getText().toString();
                sendUsername.putExtra(EXTRA_USERNAME, message);
                startActivity(sendUsername);
            }
        });
    }

    private void loginToBackendless() {
        String login = username.getText().toString();
        String password = this.password.getText().toString();
        Backendless.UserService.login(login, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                //Start new activity here because this method called when login is complete + successful

                Toast.makeText(LoginActivity.this, response.getEmail() + "Logged In", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RestaurantListActivity.class);
                startActivity(intent);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void wireWidgets() {
        username = findViewById(R.id.editText_loginactivity_user);
        password = findViewById(R.id.editText_loginactivity_pass);
        login = findViewById(R.id.button_loginactivity_login);
        signup = findViewById(R.id.button_loginactivity_singup);
    }
}
