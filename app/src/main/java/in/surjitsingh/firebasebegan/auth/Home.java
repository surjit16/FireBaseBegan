package in.surjitsingh.firebasebegan.auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.surjitsingh.firebasebegan.R;

public class Home extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference currChild;
    private String uid;

    private TextView welcomeUser, userInfo;
    private Button addInfo, logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseDatabase = FirebaseDatabase.getInstance();

        if (getIntent().hasExtra("is_logged_in")) {
            uid = getIntent().getStringExtra(("is_logged_in"));
            if (uid == null || uid.length() == 0) {
                startActivity(new Intent(this, AuthLoginActivity.class));
            }
        } else
            startActivity(new Intent(this, AuthLoginActivity.class));

        welcomeUser = findViewById(R.id.welcome_user);
        addInfo = findViewById(R.id.addInfo);
        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBox();
            }
        });
        logOut = findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finishAffinity();
                startActivity(new Intent(Home.this, AuthLoginActivity.class));
            }
        });

        welcomeUser.setText("Welcome\n" + "--");
        userInfo = findViewById(R.id.user_info);
        currChild = firebaseDatabase.getReference("users/" + uid);
        currChild.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getKey().equalsIgnoreCase("name")) {
                    String name = dataSnapshot.getValue(String.class);
                    welcomeUser.setText("Welcome\n" + name.toUpperCase());
                } else {
                    userInfo.setText(userInfo.getText().toString() + "\n" + dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String name = dataSnapshot.getValue(String.class);
                welcomeUser.setText("Welcome\n" + name.toUpperCase());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                welcomeUser.setText("Welcome\n" + name.toUpperCase());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    AlertDialog.Builder alertDialog;

    private void showBox() {
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);

        View view1 = getLayoutInflater().inflate(R.layout.custom_alert_dialog_edit_text, null);
        alertDialog.setView(view1);

        final TextView title = view1.findViewById(R.id.title);
        final TextView description = view1.findViewById(R.id.description);

        alertDialog.setTitle("Custom View");

        alertDialog.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveToRDB(title.getText().toString(), description.getText().toString());
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void saveToRDB(String s, String s1) {
        currChild.push().setValue(s + "\n" + s1);
    }
}



