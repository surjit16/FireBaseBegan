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

import in.surjitsingh.firebasebegan.R;

public class AuthLoginActivity extends AppCompatActivity {

    TextView email, pass, goReg;
    String strEmail, strPass;
    Button submit;

    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);
        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = email.getText().toString();
                strPass = pass.getText().toString();
                loginUser();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        goReg = findViewById(R.id.goReg);
        goReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthLoginActivity.this, AuthRegisterActivity.class));
            }
        });
    }

    private void loginUser() {
        progressDialog.show();
        if (TextUtils.isEmpty(strEmail)) {
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
        } else {
            auth.signInWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AuthLoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AuthLoginActivity.this, Home.class);
                        intent.putExtra("is_logged_in", auth.getCurrentUser().getUid());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AuthLoginActivity.this, "Exception " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }
}
