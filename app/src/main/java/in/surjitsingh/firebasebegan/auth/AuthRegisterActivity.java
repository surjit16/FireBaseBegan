package in.surjitsingh.firebasebegan.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.surjitsingh.firebasebegan.R;

public class AuthRegisterActivity extends AppCompatActivity {

    TextView name, email, pass, confPass, goLogin;
    String strName, strEmail, strPass, confstrPass;
    Button submit;

    FirebaseAuth auth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_register);
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        confPass = findViewById(R.id.confpass);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = email.getText().toString();
                strName = name.getText().toString();
                strPass = pass.getText().toString();
                confstrPass = confPass.getText().toString();
                createUser();
            }
        });
        goLogin = findViewById(R.id.goLogin);

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthRegisterActivity.this, AuthLoginActivity.class));
            }
        });

    }

    private void createUser() {
        progressDialog.show();
        if (TextUtils.isEmpty(strName)) {
            name.setError("Please enter name");
            name.requestFocus();
            progressDialog.dismiss();
        } else if (TextUtils.isEmpty(strEmail)) {
            email.setError("Please enter email");
            email.requestFocus();
            progressDialog.dismiss();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            email.setError("Enter valid email");
            email.requestFocus();
            progressDialog.dismiss();
        } else if (TextUtils.isEmpty(strPass)) {
            pass.setError("Please enter password");
            pass.requestFocus();
            progressDialog.dismiss();
        } else if (strPass.length() < 6) {
            pass.setError("Password should have minimum 6 character");
            pass.requestFocus();
            progressDialog.dismiss();
        } else if (!strPass.equals(confstrPass)) {
            confPass.setError("Password & Confirm password doesn't match");
            confPass.requestFocus();
            progressDialog.dismiss();
        } else {
            auth.createUserWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AuthRegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        reference = firebaseDatabase.getReference("users/");
                        DatabaseReference dr = reference.child(auth.getCurrentUser().getUid());
                        dr.child("name").setValue(strName);
                        Intent intent = new Intent(AuthRegisterActivity.this, Home.class);
                        intent.putExtra("is_logged_in", auth.getCurrentUser().getUid());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AuthRegisterActivity.this, "Exception " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }
}
