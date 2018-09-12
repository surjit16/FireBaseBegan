package in.surjitsingh.firebasebegan.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import in.surjitsingh.firebasebegan.R;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(this, Home.class);
            intent.putExtra("is_logged_in", auth.getCurrentUser().getUid());
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AuthLoginActivity.class);
            startActivity(intent);
        }

    }
}
