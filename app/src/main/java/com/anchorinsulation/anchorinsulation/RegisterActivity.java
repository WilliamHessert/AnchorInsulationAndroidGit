package com.anchorinsulation.anchorinsulation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    TextView inst;
    ProgressBar pBar;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setInitView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                RegisterActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setInitView() {
        Button phoneBtn = findViewById(R.id.registerPhoneBtn);
        final EditText p = findViewById(R.id.registerPhoneInput);
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = p.getText().toString();
                phone = phone.replace("-", "");
                phone = phone.replace("(", "");
                phone = phone.replace(")", "");
                phone = phone.replace(".", "");

                if(phone.length() == 10)
                    loadProfile(phone);
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Invalid Phone Number");
                    builder.setMessage("Please enter a valid, 10-digit phone number");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.create().show();
                }
            }
        });
    }

    private void loadProfile(final String phone) {
        pBar = findViewById(R.id.registerProgress);
        inst = findViewById(R.id.registerInstructions);
        RelativeLayout firstView = findViewById(R.id.registerPhoneView);

        inst.setVisibility(View.GONE);
        firstView.setVisibility(View.GONE);
        pBar.setVisibility(View.VISIBLE);

        db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("unclaimed").child(phone);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String fName = dataSnapshot.child("firstName").getValue(String.class);
                    String lName = dataSnapshot.child("lastName").getValue(String.class);
                    String posit = dataSnapshot.child("position").getValue(String.class);

                    if(fName.equals("undefined") || fName.equals(null))
                        unfoundException();
                    else
                        confirmUser(fName,lName, posit, phone);
                } catch (Exception e) {
                    unfoundException();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void confirmUser(
            final String fName, final String lName, final String posit, final String phone) {

        String newInst = "Please confirm if this is you:";
        inst.setText(newInst);
        inst.setVisibility(View.VISIBLE);

        final RelativeLayout confirmView = findViewById(R.id.confirmView);
        confirmView.setVisibility(View.VISIBLE);
        pBar.setVisibility(View.GONE);

        String infoString = fName+" "+lName+"\n"+posit+"\n"+phone;
        TextView info = findViewById(R.id.registerInformation);
        info.setText(infoString);

        Button conBtn = findViewById(R.id.registerConfirmBtn);
        conBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserView(confirmView, fName, lName, posit, phone);
            }
        });

        Button rejBtn = findViewById(R.id.registerRejectBtn);
        rejBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfoundException();
            }
        });
    }

    private void openUserView(RelativeLayout confirmView, final String fName,
                              final String lName, final String posit, final String phone) {

        confirmView.setVisibility(View.GONE);
        final RelativeLayout createView = findViewById(R.id.registerCreateView);
        createView.setVisibility(View.VISIBLE);

        final EditText email = findViewById(R.id.registerEmail);
        final EditText pass1 = findViewById(R.id.registerPassword);
        final EditText pass2 = findViewById(R.id.registerConfirmPassword);

        Button createBtn = findViewById(R.id.registerCreateBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString();
                String p = pass1.getText().toString();
                String c = pass2.getText().toString();

                String message = "";

                if(!e.contains("@") || !e.contains("."))
                    message += "Please enter a valid email\n";
                if(p.length() < 6)
                    message += "Please enter a valid password (at least six characters)\n";
                if(!p.equals(c))
                    message += "Please make sure your passwords match.\n";

                if(!message.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Cannot Create User");
                    builder.setMessage(message);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.create().show();
                }
                else
                    createUser(createView, e, p, fName, lName, posit, phone);
            }
        });
    }

    private void createUser(RelativeLayout createView, final String email, String passw,
                            final String fName, final String lName, final String posit, final String phone) {

        createView.setVisibility(View.GONE);
        pBar.setVisibility(View.VISIBLE);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, passw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    try {
                        String uid = auth.getUid();
                        final DatabaseReference ref = db.getReference("Users").child(uid).child("info");
                        ref.child("firstName").setValue(fName).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                ref.child("lastName").setValue(lName).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        ref.child("position").setValue(posit).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                ref.child("phone").setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        deleteUnclaimedData(email, fName, lName, posit, phone);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    } catch (Exception e) {
                        userCreationError();
                    }
                }
                else
                    userCreationError();
            }
        });
    }

    private void deleteUnclaimedData(final String email, final String fName,
                                     final String lName, final String posit, final String phone) {

        db.getReference("unclaimed").child(phone)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                signIn(email, fName, lName, posit, phone);
            }
        });
    }

    private void signIn(String email, String fName, String lName, String posit, String phone) {
        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
        i.putExtra("email", email);
        i.putExtra("fName", fName);
        i.putExtra("lName", lName);
        i.putExtra("posit", posit);
        i.putExtra("phone", phone);

        RegisterActivity.this.startActivity(i);
    }

    private void unfoundException() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Cannot Find You");
        builder.setMessage("We're sorry, we couldn't identify you in the system. Please contact " +
                "your supervisor ASAP so we can sort this issue out");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                RegisterActivity.this.finish();
            }
        });

        builder.create().show();
    }

    private void userCreationError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Cannot Find You");
        builder.setMessage("We're sorry, there was an error creating your account. Please contact " +
                "your system admin at nucleusdevelopmentinc@gmail.com for assistance");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                RegisterActivity.this.finish();
            }
        });

        builder.create().show();
    }
}
