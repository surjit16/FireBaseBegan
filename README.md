# FireBaseBegan
In This We Use FireBase Service and Impliment in Android

1. FireBase RealTimeDataBase 
           
           // myRef is DatabaseReference
           DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().getRoot();

In this we create two Activity in 
First We use ValueEventListener to featch/read data

           myRef.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          // my Logic Here
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {
                          // my Logic Here
                      }
                  });

in Second We use ChildEventListener to featch/read data

          myRef.addChildEventListener(new ChildEventListener() {
                      @Override
                      public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                          // my Logic Here
                      }

                      @Override
                      public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                          // my Logic Here
                      }

                      @Override
                      public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                          // my Logic Here
                      }

                      @Override
                      public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                          // my Logic Here
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {
                          // my Logic Here
                      }
                  });
                  
For write the data

                      myRef.child("key").setValue("Value To ADD")
                                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                                          @Override
                                          public void onSuccess(Void aVoid) {
                                                // my Logic Here
                                            }
                                      })
                                      .addOnFailureListener(new OnFailureListener() {
                                          @Override
                                          public void onFailure(@NonNull Exception e) {
                                                // my Logic Here
                                            }
                                      });


2. FireBase Authentication with Email amd Password

           // auth is Object of FirebaseAuth
           FirebaseAuth auth =  FirebaseAuth.getInstance();;
   
   In this We have Four Activity
   
   First(AuthActivity) Check the status of the user i.e. is already login or not 
   if login already user diverted to HomeActivity
   else to AuthLoginActivity
   
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
        // User is not Logged In 
        // Apply Logic Here
        
            Intent intent = new Intent(this, Home.class);
            intent.putExtra("is_logged_in", auth.getCurrentUser().getUid());
            startActivity(intent);
            
        } else {
         // User is already Logged In 
        // Apply Logic Here
            Intent intent = new Intent(this, AuthLoginActivity.class);
            startActivity(intent);
        }
        
        
   Second(AuthLoginActivity) in this user can login 
           if it has account else he can go to AuthRegisterActivity to Register ourself
                       
            auth.signInWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                    // Logic Here
                    } else {
                    // Logic Here
                    }
                }
            });
            
            
   Third(AuthRegisterActivity) user can create the account 
           if it alredy has can go to AuthLoginActivity
           
           auth.createUserWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                    // Logic Here
                    } else {
                    // Logic Here
                    }
                    progressDialog.dismiss();
                }
            });
            
            
   Fourth(Home) Activity in this we are showing basic information to the user and here he/she can add more informaton or logout          ourself
           
           // For Logout The User
           auth.signOut();
