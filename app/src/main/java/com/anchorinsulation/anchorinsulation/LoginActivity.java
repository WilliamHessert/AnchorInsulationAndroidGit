package com.anchorinsulation.anchorinsulation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class LoginActivity extends AppCompatActivity {

    boolean haveAttemptedLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();
    }

    private void setViews() {
        Button log = findViewById(R.id.loginBtn);
        Button reg = findViewById(R.id.registerBtn);

        TextView privacyPolicy = findViewById(R.id.privacyText);
        TextView fPass = findViewById(R.id.forgotPassword);

        final EditText e = findViewById(R.id.loginEmail);
        final EditText p = findViewById(R.id.loginPassword);
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.iubenda.com/privacy-policy/83531408"));
                startActivity(browserIntent);
            }
        });

        fPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword(auth);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin(e, p, auth);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(i);
            }
        });

        checkRemember(e, p, auth);
    }

    private void attemptLogin(EditText e, EditText p, final FirebaseAuth auth) {
        final RelativeLayout mainView = findViewById(R.id.loginView);
        final ProgressBar progressBar = findViewById(R.id.loginProgress);

        mainView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        final String email = e.getText().toString();
        final String passw = p.getText().toString();
        auth.signInWithEmailAndPassword(email, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    signIn(auth, email, passw);
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Could not log in. Please make sure your username " +
                            "and password are correct");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.create().show();
                    progressBar.setVisibility(View.GONE);
                    mainView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void checkRemember(EditText e, EditText p, FirebaseAuth auth) {
        SharedPreferences settings = getApplicationContext()
                .getSharedPreferences("ANCHOR_INSULATION_DATA", 0);
        String username = settings.getString("username", "");
        String password = settings.getString("password", "");

        if(!username.equals("") && !password.equals("")) {
            findViewById(R.id.rememberBox).performClick();

            e.setText(username);
            p.setText(password);
            haveAttemptedLogin = getIntent()
                    .getBooleanExtra("hasLoggedIn", false);

            if(!haveAttemptedLogin)
                attemptLogin(e, p, auth);
        }
    }

    private void forgotPassword(final FirebaseAuth auth) {
        final Context c = LoginActivity.this;

        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Reset Password");
        builder.setMessage("Are you sure you want to reset your password?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                promptForEmail(c, auth);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void promptForEmail(final Context c, final FirebaseAuth auth) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Enter Email");

        View dialogView = LayoutInflater.from(c).inflate(R.layout.dialog_email, null);
        final EditText eText = dialogView.findViewById(R.id.emailDialog);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = eText.getText().toString();

                if(email.contains("@")) {
                    sendForgotPasswordEmail(email, c, auth);
                }
                else {
                    AlertDialog.Builder eBuilder = new AlertDialog.Builder(c);
                    eBuilder.setTitle("Invalid Email");
                    eBuilder.setMessage("Please enter a valid email address");

                    eBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            promptForEmail(c, auth);
                            dialog.dismiss();
                        }
                    });

                    eBuilder.create().show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void sendForgotPasswordEmail(String email, final Context c, FirebaseAuth auth) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(c);
                        String title = "Error";
                        String message = "We're sorry, there was an error sending you a reset " +
                                "password email. Please confirm you entered the correct email." +
                                " If you're experiencing technical difficulties, please contact" +
                                " your supervisor";

                        if (task.isSuccessful()) {
                            title = "Email Sent";
                            message = "An email has been sent to you with a link to reset your " +
                                    "password.";
                        }

                        builder.setTitle(title);
                        builder.setMessage(message);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.create().show();
                    }
                });
    }

    private void signIn(FirebaseAuth auth, final String email, final String passw) {
        try {
            String uid = auth.getUid();
            final DatabaseReference ref = FirebaseDatabase
                    .getInstance().getReference("Users").child(uid).child("info");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    rememberMe(email, passw, dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("Could Not Sign In");
            builder.setMessage("Please make sure your username and password are correct. If you " +
                    "continue to get this error, please contact nucleusdevelopmentinc@gmail.com " +
                    "for assistance");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.create().show();
        }
    }

    private void rememberMe(String email, String passw, DataSnapshot dataSnapshot) {
        String username = "";
        String password = "";
        CheckBox remBox = findViewById(R.id.rememberBox);

        if(remBox.isChecked()) {
            username = email;
            password = passw;
        }

        SharedPreferences settings = getApplicationContext()
                .getSharedPreferences("ANCHOR_INSULATION_DATA", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        openMainActivity(dataSnapshot, email);
    }

    private void openMainActivity(DataSnapshot dataSnapshot, String email) {
        String fName = dataSnapshot.child("firstName").getValue(String.class);
        String lName = dataSnapshot.child("lastName").getValue(String.class);
        String posit = dataSnapshot.child("position").getValue(String.class);
        String phone = dataSnapshot.child("phone").getValue(String.class);

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("email", email);
        i.putExtra("fName", fName);
        i.putExtra("lName", lName);
        i.putExtra("posit", posit);
        i.putExtra("phone", phone);
        LoginActivity.this.startActivity(i);
    }
}
