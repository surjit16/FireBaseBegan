# FireBaseBegan
In This We Use FireBase Service and Impliment in Android

1. FireBase RealTimeDataBase 

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
