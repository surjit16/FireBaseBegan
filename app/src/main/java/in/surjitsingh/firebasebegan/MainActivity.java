package in.surjitsingh.firebasebegan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.surjitsingh.firebasebegan.auth.AuthActivity;
import in.surjitsingh.firebasebegan.rtdb.RTDBActivity;
import in.surjitsingh.firebasebegan.rtdb.RTDBActivity02;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fRTDB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RTDBActivity.class));
            }
        });

        findViewById(R.id.fRTDB02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RTDBActivity02.class));
            }
        });

        findViewById(R.id.fAuth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AuthActivity.class));
            }
        });
    }
}
