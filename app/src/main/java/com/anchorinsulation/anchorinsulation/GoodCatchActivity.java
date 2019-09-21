package com.anchorinsulation.anchorinsulation;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class GoodCatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_catch);

        getSupportActionBar().setTitle("Good Catch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                GoodCatchActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setView() {
        EditText name = findViewById(R.id.goodcatchName);
        String fullName = getIntent().getExtras().getString("name");
        name.setText(fullName);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        final String dateString = sdf.format(date);

        EditText dateField = findViewById(R.id.goodcatchDate);
        dateField.setText(dateString);

        final EditText jobName = findViewById(R.id.goodcatchJobName);
        final EditText jobNumb = findViewById(R.id.goodcatchJobNumber);
        final EditText taskField = findViewById(R.id.goodcatchTask);
        final EditText deficiency = findViewById(R.id.goodcatchDeficiency);
        Button btn = findViewById(R.id.goodcatchBtn);

        if(getIntent().getExtras().getBoolean("spanish")) {
            String sjn = "Nombre de Trabajo";
            String sju = "Número de Trabajo";
            String stf = "Tarea Observada";
            String sdn = "Deficiencia Observada";

            jobName.setHint(sjn);
            jobNumb.setHint(sju);
            taskField.setHint(stf);
            deficiency.setHint(sdn);

            TextView inst = findViewById(R.id.goodCatchInstructions);
            String sInst = getResources().getString(R.string.sGoodCatchInstructions);
            inst.setText(sInst);

            String bText = "Enviar";
            btn.setText(bText);

            try {
                getSupportActionBar().setTitle("Buena Captura");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = jobName.getText().toString();
                String numb = jobNumb.getText().toString();
                String task = taskField.getText().toString();
                String defi = deficiency.getText().toString();

                if(!name.equals("") && !numb.equals("") && !task.equals("") && !defi.equals(""))
                    submitForm(dateString, name, numb, task, defi);
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GoodCatchActivity.this);
                    builder.setTitle("Incomplete");
                    builder.setMessage("Please complete all items before continuing.");

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

    private void submitForm(
            String date, String nam, final String num, final String tsk, final String def) {

        ScrollView view = findViewById(R.id.goodcatchView);
        ProgressBar pBar = findViewById(R.id.goodcatchProgress);

        view.setVisibility(View.GONE);
        pBar.setVisibility(View.VISIBLE);

        String uid = FirebaseAuth.getInstance().getUid();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(
                "Forms").child("goodcatch").child(date).child(uid).child(generateId());

        ref.child("jobName").setValue(nam).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ref.child("jobNumber").setValue(num).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ref.child("task").setValue(tsk).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                ref.child("deficiency").setValue(def).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(GoodCatchActivity.this, "Success!", Toast.LENGTH_LONG).show();
                                        GoodCatchActivity.this.finish();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private String generateId() {
        String random = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();

        while (sb.length() < 20) { // length of the random string.
            int index = (int) (rnd.nextFloat() * random.length());
            sb.append(random.charAt(index));
        }

        return sb.toString();
    }
}
