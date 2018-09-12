package in.surjitsingh.firebasebegan.rtdb;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.surjitsingh.firebasebegan.R;

public class RTDBActivity02 extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText uId, name, email, mess;
    Button add, show;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<DBData> dbData;
    MyAdapter myAdapter;
    ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtdb);
        uId = findViewById(R.id.uid);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mess = findViewById(R.id.mess);
        add = findViewById(R.id.add);
        show = findViewById(R.id.show);
        recyclerView = findViewById(R.id.rView);
        dbData = new ArrayList<>();
        myAdapter = new MyAdapter(this, dbData);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().getRoot();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutCompat.VERTICAL));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uId.setError(null);
                name.setError(null);
                email.setError(null);
                mess.setError(null);

                String strID = uId.getText().toString();
                String strName = name.getText().toString();
                String strEmail = email.getText().toString();
                String strMess = mess.getText().toString();

                if (TextUtils.isEmpty(strID)) {
                    uId.setError("Please enter ID");
                } else if (TextUtils.isEmpty(strName)) {
                    name.setError("Please enter Name");
                } else if (TextUtils.isEmpty(strEmail)) {
                    email.setError("Please enter Email");
                } else if (TextUtils.isEmpty(strMess)) {
                    mess.setError("Please enter Mess");
                } else {
                    DBData dbData = new DBData(strID, strName, strEmail, strMess);
                    addDataTORTDB(dbData);
                }
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });
    }

    private void fetchData() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dbData.add(dataSnapshot.getValue(DBData.class));
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String thisDbID = dataSnapshot.getValue(DBData.class).getId();
                for (DBData c : dbData){
                    if (c.getId().equals(thisDbID)){
                        dbData.remove(c);
                        myAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (listener != null)
            myRef.removeEventListener(listener);
    }

    private void addDataTORTDB(DBData dbData) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        myRef.child(dbData.getId()).setValue(dbData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RTDBActivity02.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RTDBActivity02.this, "Data Insertion Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }
}
