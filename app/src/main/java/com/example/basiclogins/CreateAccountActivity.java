package com.example.basiclogins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirm;
    private EditText editTextEmail;
    private Button editTextSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        wireWidgets();

        editTextSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccountOnBackendless();
            }
        });
    }

    private void registerAccountOnBackendless() {
        //verify all fields are filled out and passwords are the same
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirm.getText().toString();
        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String name = editTextName.getText().toString();


        if(allFieldsValid(password, confirmPassword, username, email, name)) {
            //make the registration call
            BackendlessUser user = new BackendlessUser();
            user.setProperty("email", email);
            user.setProperty("username", username);
            user.setProperty("name", name);
            user.setPassword(password);

            Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser registeredUser) {
                    // user has been registered and now can login
                    Toast.makeText(CreateAccountActivity.this,
                            registeredUser.getUserId() + " has registered.",
                            Toast.LENGTH_SHORT).show();
                    finish(); // ends the activity
                    // TODO would be nice to return the username to the loginactivity
                    // we would need to call setResult.  see startActivityForResult
                    // documentation
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                    Toast.makeText(CreateAccountActivity.this, fault.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        //make the registration call

        //return to the LoginActivity in the handleResponse
    }

    private boolean allFieldsValid(String password, String confirmPassword, String username, String email, String name) {
        return password.equals(confirmPassword) && username.length() > 0;
    }

    private void wireWidgets() {
        editTextName = findViewById(R.id.editText_creataccountactivity_name);
        editTextUsername = findViewById(R.id.editText_createaccountactivity_user);
        editTextUsername.setText(getIntent().getStringExtra(LoginActivity.EXTRA_USERNAME));
        editTextPassword = findViewById(R.id.editText_createaccountactivity_pass);
        editTextConfirm = findViewById(R.id.editText_createaccountdactivity_passconfirm);
        editTextEmail = findViewById(R.id.editText_createaccountactivity_email);
        editTextSignup = findViewById(R.id.button_createaccountactivity_signup);
    }
}
