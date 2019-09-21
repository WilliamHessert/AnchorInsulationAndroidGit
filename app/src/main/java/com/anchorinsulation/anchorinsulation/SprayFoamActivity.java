package com.anchorinsulation.anchorinsulation;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

public class SprayFoamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spray_foam);

        getSupportActionBar().setTitle("Job Hazard Awareness- Spray Foam Insulation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SprayFoamActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setView() {
        EditText name = findViewById(R.id.sprayfoamName);
        String fullName = getIntent().getExtras().getString("name");
        name.setText(fullName);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        final String dateString = sdf.format(date);

        EditText dateField = findViewById(R.id.sprayfoamDate);
        dateField.setText(dateString);

        TextView q1 = findViewById(R.id.sprayfoamQuestion1);
        TextView q2 = findViewById(R.id.sprayfoamQuestion2);
        TextView q3 = findViewById(R.id.sprayfoamQuestion3);
        TextView q4 = findViewById(R.id.sprayfoamQuestion4);
        TextView q5 = findViewById(R.id.sprayfoamQuestion5);
        TextView q6 = findViewById(R.id.sprayfoamQuestion6);
        TextView q7 = findViewById(R.id.sprayfoamQuestion7);
        TextView q8 = findViewById(R.id.sprayfoamQuestion8);
        TextView q9 = findViewById(R.id.sprayfoamQuestion9);
        TextView q10 = findViewById(R.id.sprayfoamQuestion10);
        TextView q11 = findViewById(R.id.sprayfoamQuestion11);

        final EditText jobName = findViewById(R.id.sprayfoamJobName);
        final EditText jobNumb = findViewById(R.id.sprayfoamJobNumber);
        final Button btn = findViewById(R.id.sprayfoamBtn);

        boolean spanish = getIntent().getExtras().getBoolean("spanish");

        if(spanish) {
            q1.setText(getResources().getString(R.string.span_cell1));
            q2.setText(getResources().getString(R.string.span_cell2));
            q3.setText(getResources().getString(R.string.span_cell3));
            q4.setText(getResources().getString(R.string.span_cell4));
            q5.setText(getResources().getString(R.string.span_cell5));
            q6.setText(getResources().getString(R.string.span_cell6));
            q7.setText(getResources().getString(R.string.span_cell7));
            q8.setText(getResources().getString(R.string.span_cell8));
            q9.setText(getResources().getString(R.string.span_cell9));
            q10.setText(getResources().getString(R.string.span_cell10));
            q11.setText(getResources().getString(R.string.sFoam11));

            String sjn = "Nombre de Trabajo";
            String sju = "Número de Trabajo";
            String bText = "Enviar";

            jobName.setHint(sjn);
            jobNumb.setHint(sju);
            btn.setText(bText);

            try {
                getSupportActionBar().setTitle("Espuma del Aerosol Materiales Peligrosos");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            q1.setText(getResources().getString(R.string.cellulose1));
            q2.setText(getResources().getString(R.string.cellulose2));
            q3.setText(getResources().getString(R.string.cellulose3));
            q4.setText(getResources().getString(R.string.cellulose4));
            q5.setText(getResources().getString(R.string.cellulose5));
            q6.setText(getResources().getString(R.string.cellulose6));
            q7.setText(getResources().getString(R.string.cellulose7));
            q8.setText(getResources().getString(R.string.cellulose8));
            q9.setText(getResources().getString(R.string.cellulose9));
            q10.setText(getResources().getString(R.string.cellulose10));
            q11.setText(getResources().getString(R.string.spray_foam11));
        }
        
        final CheckBox c1 = findViewById(R.id.sprayfoamCheckbox1);
        final CheckBox c2 = findViewById(R.id.sprayfoamCheckbox2);
        final CheckBox c3 = findViewById(R.id.sprayfoamCheckbox3);
        final CheckBox c4 = findViewById(R.id.sprayfoamCheckbox4);
        final CheckBox c5 = findViewById(R.id.sprayfoamCheckbox5);
        final CheckBox c6 = findViewById(R.id.sprayfoamCheckbox6);
        final CheckBox c7 = findViewById(R.id.sprayfoamCheckbox7);
        final CheckBox c8 = findViewById(R.id.sprayfoamCheckbox8);
        final CheckBox c9 = findViewById(R.id.sprayfoamCheckbox9);
        final CheckBox c10 = findViewById(R.id.sprayfoamCheckbox10);
        final CheckBox c11 = findViewById(R.id.sprayfoamCheckbox11);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jName = jobName.getText().toString();
                final String jNumb = jobNumb.getText().toString();

                boolean allChecked = c1.isChecked()&&c2.isChecked()
                        &&c3.isChecked()&&c4.isChecked()&&c5.isChecked()&&c6.isChecked()
                        &&c7.isChecked()&&c8.isChecked()&&c9.isChecked()&&c10.isChecked()&&c11.isChecked();

                if(allChecked && !jName.equals("") && !jNumb.equals(""))
                    uploadForm(dateString);
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SprayFoamActivity.this);
                    builder.setTitle("Incomplete");
                    builder.setMessage("Please complete all items before submitting");

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

    private void uploadForm(final String date) {
        ScrollView view = findViewById(R.id.sprayfoamView);
        final ProgressBar pBar = findViewById(R.id.sprayfoamProgress);

        view.setVisibility(View.GONE);
        pBar.setVisibility(View.VISIBLE);

        EditText jobName = findViewById(R.id.sprayfoamJobName);
        EditText jobNumb = findViewById(R.id.sprayfoamJobNumber);

        String jName = jobName.getText().toString();
        final String jNumb = jobNumb.getText().toString();

        String uid = FirebaseAuth.getInstance().getUid();
        final DatabaseReference ref = FirebaseDatabase
                .getInstance().getReference("Forms").child("sprayfoam").child(date).child(uid);

        ref.child("jobName").setValue(jName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ref.child("jobNumber").setValue(jNumb).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        openAddView(pBar, date);
                    }
                });
            }
        });
    }

    private void openAddView(ProgressBar pBar, final String date) {
        RelativeLayout addView = findViewById(R.id.sAdditionalView);
        addView.setVisibility(View.VISIBLE);
        pBar.setVisibility(View.GONE);

        Button btn = findViewById(R.id.sAddSubmitBtn);
        final EditText a = findViewById(R.id.sAddEditText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAdditional(a.getText().toString(), date);
            }
        });
    }

    private void submitAdditional(String a, String date) {
        String uid = FirebaseAuth.getInstance().getUid();
        final DatabaseReference ref = FirebaseDatabase
                .getInstance().getReference("Forms").child("sprayfoam").child(date).child(uid);

        ref.child("additionalCrewman").setValue(a).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SprayFoamActivity.this, "Success!", Toast.LENGTH_LONG).show();
                SprayFoamActivity.this.finish();
            }
        });
    }
}
