package com.anchorinsulation.anchorinsulation;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class VehicleActivity extends AppCompatActivity {

    boolean spanish;
    String vNumber, vReportDate, vReportGuid;

    ProgressBar pBar;
    ScrollView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        getSupportActionBar().setTitle("Daily Vehicle Inspection");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        checkExistingReport();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                VehicleActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkExistingReport() {
        mainView = findViewById(R.id.vehicleView);
        pBar = findViewById(R.id.vehicleProgress);
        spanish = getIntent().getExtras().getBoolean("spanish", false);

        SharedPreferences settings = getApplicationContext()
                .getSharedPreferences("ANCHOR_INSULATION_DATA", 0);
        vNumber = settings.getString("vNumber", "");
        vReportDate = settings.getString("vReportDate", "");
        vReportGuid = settings.getString("vReportGuid", "");

        if(!vNumber.equals("") && !vReportDate.equals("") && !vReportGuid.equals("")) {
            String dOption = "Delete";
            String title = "Finish Report";
            String message = "You haven't entered the ending mileage for your last report" +
                    " for vehicle "+vNumber+" on "+vReportDate+". If you click \"OK\", you " +
                    "will be asked to enter the ending mileage. If you click Delete, the report " +
                    "will be deleted and you will not receive credit for it.";

            if(spanish) {
                dOption = "Borrar";
                title = "Terminar el Informe";
                message = message = "No has entraste el kilometraje final por tu informe por " +
                        "vehículo "+vNumber+" en "+vReportDate+". Si presionas \"Ok\", " +
                        "necesitas entrar el kilometraje final. Si presionas \"Borrar\", " +
                        "tu informe será borrado y no recibirás crédito";
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(VehicleActivity.this);
            builder.setMessage(message);
            builder.setTitle(title);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openEndingMileageView();
                }
            });

            builder.setNegativeButton(dOption, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    clearSavedValues(false);
                }
            });

            builder.create().show();
        }
        else
            setView();
    }

    private void setView() {
        EditText name = findViewById(R.id.vehicleName);
        String fullName = getIntent().getExtras().getString("name");
        name.setText(fullName);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        final String dateString = sdf.format(date);

        EditText dateField = findViewById(R.id.vehicleDate);
        dateField.setText(dateString);

        final TextView q1 = findViewById(R.id.VehicleQuestion1);
        final TextView q2 = findViewById(R.id.VehicleQuestion2);
        final TextView q3 = findViewById(R.id.VehicleQuestion3);
        final TextView q4 = findViewById(R.id.VehicleQuestion4);
        final TextView q5 = findViewById(R.id.VehicleQuestion5);
        final TextView q6 = findViewById(R.id.VehicleQuestion6);
        final TextView q7 = findViewById(R.id.VehicleQuestion7);
        final TextView q8 = findViewById(R.id.VehicleQuestion8);
        final TextView q9 = findViewById(R.id.VehicleQuestion9);
        final TextView q10 = findViewById(R.id.VehicleQuestion10);
        final TextView q11 = findViewById(R.id.VehicleQuestion11);
        final TextView q12 = findViewById(R.id.VehicleQuestion12);
        final TextView q13 = findViewById(R.id.VehicleQuestion13);
        final TextView q14 = findViewById(R.id.VehicleQuestion14);
        final TextView q15 = findViewById(R.id.VehicleQuestion15);
        final TextView q16 = findViewById(R.id.VehicleQuestion16);
        final TextView q17 = findViewById(R.id.VehicleQuestion17);
        final TextView q18 = findViewById(R.id.VehicleQuestion18);
        final TextView q19 = findViewById(R.id.VehicleQuestion19);
        final TextView q20 = findViewById(R.id.VehicleQuestion20);
        final TextView q21 = findViewById(R.id.VehicleQuestion21);
        final TextView q22 = findViewById(R.id.VehicleQuestion22);
        final TextView q23 = findViewById(R.id.VehicleQuestion23);
        final TextView q24 = findViewById(R.id.VehicleQuestion24);
        final TextView q25 = findViewById(R.id.VehicleQuestion25);
        final TextView q26 = findViewById(R.id.VehicleQuestion26);
        final TextView q27 = findViewById(R.id.VehicleQuestion27);
        final TextView q28 = findViewById(R.id.VehicleQuestion28);
        final TextView q29 = findViewById(R.id.VehicleQuestion29);
        final TextView q30 = findViewById(R.id.VehicleQuestion30);

        final Button d1 = findViewById(R.id.vehicleDefect1);
        final Button d2 = findViewById(R.id.vehicleDefect2);
        final Button d3 = findViewById(R.id.vehicleDefect3);
        final Button d4 = findViewById(R.id.vehicleDefect4);
        final Button d5 = findViewById(R.id.vehicleDefect5);
        final Button d6 = findViewById(R.id.vehicleDefect6);
        final Button d7 = findViewById(R.id.vehicleDefect7);
        final Button d8 = findViewById(R.id.vehicleDefect8);
        final Button d9 = findViewById(R.id.vehicleDefect9);
        final Button d10 = findViewById(R.id.vehicleDefect10);
        final Button d11 = findViewById(R.id.vehicleDefect11);
        final Button d12 = findViewById(R.id.vehicleDefect12);
        final Button d13 = findViewById(R.id.vehicleDefect13);
        final Button d14 = findViewById(R.id.vehicleDefect14);
        final Button d15 = findViewById(R.id.vehicleDefect15);
        final Button d16 = findViewById(R.id.vehicleDefect16);
        final Button d17 = findViewById(R.id.vehicleDefect17);
        final Button d18 = findViewById(R.id.vehicleDefect18);
        final Button d19 = findViewById(R.id.vehicleDefect19);
        final Button d20 = findViewById(R.id.vehicleDefect20);
        final Button d21 = findViewById(R.id.vehicleDefect21);
        final Button d22 = findViewById(R.id.vehicleDefect22);
        final Button d23 = findViewById(R.id.vehicleDefect23);
        final Button d24 = findViewById(R.id.vehicleDefect24);
        final Button d25 = findViewById(R.id.vehicleDefect25);
        final Button d26 = findViewById(R.id.vehicleDefect26);
        final Button d27 = findViewById(R.id.vehicleDefect27);
        final Button d28 = findViewById(R.id.vehicleDefect28);
        final Button d29 = findViewById(R.id.vehicleDefect29);
        final Button d30 = findViewById(R.id.vehicleDefect30);

        final EditText n = findViewById(R.id.vehicleNumber);
        final EditText s = findViewById(R.id.vehicleStartingMileage);

        final Button btn = findViewById(R.id.vehicleBtn);


        if(spanish) {
            q1.setText(getResources().getString(R.string.sVehicle1));
            q2.setText(getResources().getString(R.string.sVehicle2));
            q3.setText(getResources().getString(R.string.sVehicle3));
            q4.setText(getResources().getString(R.string.sVehicle4));
            q5.setText(getResources().getString(R.string.sVehicle5));
            q6.setText(getResources().getString(R.string.sVehicle6));
            q7.setText(getResources().getString(R.string.sVehicle7));
            q8.setText(getResources().getString(R.string.sVehicle8));
            q9.setText(getResources().getString(R.string.sVehicle9));
            q10.setText(getResources().getString(R.string.sVehicle10));
            q11.setText(getResources().getString(R.string.sVehicle11));
            q12.setText(getResources().getString(R.string.sVehicle12));
            q13.setText(getResources().getString(R.string.sVehicle13));
            q14.setText(getResources().getString(R.string.sVehicle14));
            q15.setText(getResources().getString(R.string.sVehicle15));
            q16.setText(getResources().getString(R.string.sVehicle16));
            q17.setText(getResources().getString(R.string.sVehicle17));
            q18.setText(getResources().getString(R.string.sVehicle18));
            q19.setText(getResources().getString(R.string.sVehicle19));
            q20.setText(getResources().getString(R.string.sVehicle20));
            q21.setText(getResources().getString(R.string.sVehicle21));
            q22.setText(getResources().getString(R.string.sVehicle22));
            q23.setText(getResources().getString(R.string.sVehicle23));
            q24.setText(getResources().getString(R.string.sVehicle24));
            q25.setText(getResources().getString(R.string.sVehicle25));
            q26.setText(getResources().getString(R.string.sVehicle26));
            q27.setText(getResources().getString(R.string.sVehicle27));
            q28.setText(getResources().getString(R.string.sVehicle28));
            q29.setText(getResources().getString(R.string.sVehicle29));
            q30.setText(getResources().getString(R.string.sVehicle30));

            String bText = "Defecto";
            d1.setText(bText);
            d2.setText(bText);
            d3.setText(bText);
            d4.setText(bText);
            d5.setText(bText);
            d6.setText(bText);
            d7.setText(bText);
            d8.setText(bText);
            d9.setText(bText);
            d10.setText(bText);
            d11.setText(bText);
            d12.setText(bText);
            d13.setText(bText);
            d14.setText(bText);
            d15.setText(bText);
            d16.setText(bText);
            d17.setText(bText);
            d18.setText(bText);
            d19.setText(bText);
            d20.setText(bText);
            d21.setText(bText);
            d22.setText(bText);
            d23.setText(bText);
            d24.setText(bText);
            d25.setText(bText);
            d26.setText(bText);
            d27.setText(bText);
            d28.setText(bText);
            d29.setText(bText);
            d30.setText(bText);

            try {
                getSupportActionBar().setTitle("Informe de Vehículo");
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }

            String sText = "Enviar";
            btn.setText(sText);

            String nString = "Número de Vehículo";
            String sString = "Kilometraje Inicial de Vehículo";

            n.setHint(nString);
            s.setHint(sString);

            TextView inst = findViewById(R.id.vehicleInstructions);
            String sInst = getResources().getString(R.string.sVehicleInstructions1);
            inst.setText(sInst);

            TextView inst2 = findViewById(R.id.vehicleInstructions2);
            String sInst2 = getResources().getString(R.string.sVehicleInstructions2);
            inst2.setText(sInst2);
        }
        else {
            q1.setText(getResources().getString(R.string.vehicle1));
            q2.setText(getResources().getString(R.string.vehicle2));
            q3.setText(getResources().getString(R.string.vehicle3));
            q4.setText(getResources().getString(R.string.vehicle4));
            q5.setText(getResources().getString(R.string.vehicle5));
            q6.setText(getResources().getString(R.string.vehicle6));
            q7.setText(getResources().getString(R.string.vehicle7));
            q8.setText(getResources().getString(R.string.vehicle8));
            q9.setText(getResources().getString(R.string.vehicle9));
            q10.setText(getResources().getString(R.string.vehicle10));
            q11.setText(getResources().getString(R.string.vehicle11));
            q12.setText(getResources().getString(R.string.vehicle12));
            q13.setText(getResources().getString(R.string.vehicle13));
            q14.setText(getResources().getString(R.string.vehicle14));
            q15.setText(getResources().getString(R.string.vehicle15));
            q16.setText(getResources().getString(R.string.vehicle16));
            q17.setText(getResources().getString(R.string.vehicle17));
            q18.setText(getResources().getString(R.string.vehicle18));
            q19.setText(getResources().getString(R.string.vehicle19));
            q20.setText(getResources().getString(R.string.vehicle20));
            q21.setText(getResources().getString(R.string.vehicle21));
            q22.setText(getResources().getString(R.string.vehicle22));
            q23.setText(getResources().getString(R.string.vehicle23));
            q24.setText(getResources().getString(R.string.vehicle24));
            q25.setText(getResources().getString(R.string.vehicle25));
            q26.setText(getResources().getString(R.string.vehicle26));
            q27.setText(getResources().getString(R.string.vehicle27));
            q28.setText(getResources().getString(R.string.vehicle28));
            q29.setText(getResources().getString(R.string.vehicle29));
            q30.setText(getResources().getString(R.string.vehicle30));
        }

        final ArrayList<String> defectsList = new ArrayList<>();

        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q1.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("1");
                    q1.setTextColor(Color.BLACK);
                    d1.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("1");
                    q1.setTextColor(Color.RED);
                    d1.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q2.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("2");
                    q2.setTextColor(Color.BLACK);
                    d2.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("2");
                    q2.setTextColor(Color.RED);
                    d2.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q3.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("3");
                    q3.setTextColor(Color.BLACK);
                    d3.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("3");
                    q3.setTextColor(Color.RED);
                    d3.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q4.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("4");
                    q4.setTextColor(Color.BLACK);
                    d4.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("4");
                    q4.setTextColor(Color.RED);
                    d4.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q5.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("5");
                    q5.setTextColor(Color.BLACK);
                    d5.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("5");
                    q5.setTextColor(Color.RED);
                    d5.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q6.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("6");
                    q6.setTextColor(Color.BLACK);
                    d6.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("6");
                    q6.setTextColor(Color.RED);
                    d6.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q7.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("7");
                    q7.setTextColor(Color.BLACK);
                    d7.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("7");
                    q7.setTextColor(Color.RED);
                    d7.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q8.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("8");
                    q8.setTextColor(Color.BLACK);
                    d8.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("8");
                    q8.setTextColor(Color.RED);
                    d8.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q9.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("9");
                    q9.setTextColor(Color.BLACK);
                    d9.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("9");
                    q9.setTextColor(Color.RED);
                    d9.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q10.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("10");
                    q10.setTextColor(Color.BLACK);
                    d10.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("10");
                    q10.setTextColor(Color.RED);
                    d10.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q11.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("11");
                    q11.setTextColor(Color.BLACK);
                    d11.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("11");
                    q11.setTextColor(Color.RED);
                    d11.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q12.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("12");
                    q12.setTextColor(Color.BLACK);
                    d12.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("12");
                    q12.setTextColor(Color.RED);
                    d12.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q13.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("13");
                    q13.setTextColor(Color.BLACK);
                    d13.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("13");
                    q13.setTextColor(Color.RED);
                    d13.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q14.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("14");
                    q14.setTextColor(Color.BLACK);
                    d14.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("14");
                    q14.setTextColor(Color.RED);
                    d14.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q15.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("15");
                    q15.setTextColor(Color.BLACK);
                    d15.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("15");
                    q15.setTextColor(Color.RED);
                    d15.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q16.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("16");
                    q16.setTextColor(Color.BLACK);
                    d16.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("16");
                    q16.setTextColor(Color.RED);
                    d16.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q17.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("17");
                    q17.setTextColor(Color.BLACK);
                    d17.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("17");
                    q17.setTextColor(Color.RED);
                    d17.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q18.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("18");
                    q18.setTextColor(Color.BLACK);
                    d18.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("18");
                    q18.setTextColor(Color.RED);
                    d18.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q19.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("19");
                    q19.setTextColor(Color.BLACK);
                    d19.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("19");
                    q19.setTextColor(Color.RED);
                    d19.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q20.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("20");
                    q20.setTextColor(Color.BLACK);
                    d20.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("20");
                    q20.setTextColor(Color.RED);
                    d20.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q21.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("21");
                    q21.setTextColor(Color.BLACK);
                    d21.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("21");
                    q21.setTextColor(Color.RED);
                    d21.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q22.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("22");
                    q22.setTextColor(Color.BLACK);
                    d22.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("22");
                    q22.setTextColor(Color.RED);
                    d22.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q23.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("23");
                    q23.setTextColor(Color.BLACK);
                    d23.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("23");
                    q23.setTextColor(Color.RED);
                    d23.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q24.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("24");
                    q24.setTextColor(Color.BLACK);
                    d24.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("24");
                    q24.setTextColor(Color.RED);
                    d24.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q25.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("25");
                    q25.setTextColor(Color.BLACK);
                    d25.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("25");
                    q25.setTextColor(Color.RED);
                    d25.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q26.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("26");
                    q26.setTextColor(Color.BLACK);
                    d26.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("26");
                    q26.setTextColor(Color.RED);
                    d26.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q27.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("27");
                    q27.setTextColor(Color.BLACK);
                    d27.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("27");
                    q27.setTextColor(Color.RED);
                    d27.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q28.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("28");
                    q28.setTextColor(Color.BLACK);
                    d28.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("28");
                    q28.setTextColor(Color.RED);
                    d28.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q29.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("29");
                    q29.setTextColor(Color.BLACK);
                    d29.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("29");
                    q29.setTextColor(Color.RED);
                    d29.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        d30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q30.getCurrentTextColor() == Color.RED) {
                    defectsList.remove("30");
                    q30.setTextColor(Color.BLACK);
                    d30.setBackground(getResources().getDrawable(R.drawable.box_filled, null));
                }
                else {
                    defectsList.add("30");
                    q30.setTextColor(Color.RED);
                    d30.setBackground(getResources().getDrawable(R.drawable.box_filled_red, null));
                }
            }
        });

        final CheckBox c1 = findViewById(R.id.vehicleCheckbox1);
        final CheckBox c2 = findViewById(R.id.vehicleCheckbox2);
        final CheckBox c3 = findViewById(R.id.vehicleCheckbox3);
        final CheckBox c4 = findViewById(R.id.vehicleCheckbox4);
        final CheckBox c5 = findViewById(R.id.vehicleCheckbox5);
        final CheckBox c6 = findViewById(R.id.vehicleCheckbox6);
        final CheckBox c7 = findViewById(R.id.vehicleCheckbox7);
        final CheckBox c8 = findViewById(R.id.vehicleCheckbox8);
        final CheckBox c9 = findViewById(R.id.vehicleCheckbox9);
        final CheckBox c10 = findViewById(R.id.vehicleCheckbox10);
        final CheckBox c11 = findViewById(R.id.vehicleCheckbox11);
        final CheckBox c12 = findViewById(R.id.vehicleCheckbox12);
        final CheckBox c13 = findViewById(R.id.vehicleCheckbox13);
        final CheckBox c14 = findViewById(R.id.vehicleCheckbox14);
        final CheckBox c15 = findViewById(R.id.vehicleCheckbox15);
        final CheckBox c16 = findViewById(R.id.vehicleCheckbox16);
        final CheckBox c17 = findViewById(R.id.vehicleCheckbox17);
        final CheckBox c18 = findViewById(R.id.vehicleCheckbox18);
        final CheckBox c19 = findViewById(R.id.vehicleCheckbox19);
        final CheckBox c20 = findViewById(R.id.vehicleCheckbox20);
        final CheckBox c21 = findViewById(R.id.vehicleCheckbox21);
        final CheckBox c22 = findViewById(R.id.vehicleCheckbox22);
        final CheckBox c23 = findViewById(R.id.vehicleCheckbox23);
        final CheckBox c24 = findViewById(R.id.vehicleCheckbox24);
        final CheckBox c25 = findViewById(R.id.vehicleCheckbox25);
        final CheckBox c26 = findViewById(R.id.vehicleCheckbox26);
        final CheckBox c27 = findViewById(R.id.vehicleCheckbox27);
        final CheckBox c28 = findViewById(R.id.vehicleCheckbox28);
        final CheckBox c29 = findViewById(R.id.vehicleCheckbox29);
        final CheckBox c30 = findViewById(R.id.vehicleCheckbox30);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = n.getText().toString();
                String str = s.getText().toString();

                if(c1.isChecked()
                        && c2.isChecked()
                        && c3.isChecked()
                        && c4.isChecked()
                        && c5.isChecked()
                        && c6.isChecked()
                        && c7.isChecked()
                        && c8.isChecked()
                        && c9.isChecked()
                        && c10.isChecked()
                        && c11.isChecked()
                        && c12.isChecked()
                        && c13.isChecked()
                        && c14.isChecked()
                        && c15.isChecked()
                        && c16.isChecked()
                        && c17.isChecked()
                        && c18.isChecked()
                        && c19.isChecked()
                        && c20.isChecked()
                        && c21.isChecked()
                        && c22.isChecked()
                        && c23.isChecked()
                        && c24.isChecked()
                        && c25.isChecked()
                        && c26.isChecked()
                        && c27.isChecked()
                        && c28.isChecked()
                        && c29.isChecked()
                        && c30.isChecked()
                        && !num.equals("")
                        && !str.equals(""))
                    submitReport(dateString, num, str, defectsList);
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VehicleActivity.this);
                    builder.setTitle("Incomplete");
                    builder.setMessage("Please complete items before continuing");

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


    private void submitReport(final String date, final String n,
                              final String s, final ArrayList<String> defectsList) {

        final String fid = generateId();
        pBar.setVisibility(View.VISIBLE);
        mainView.setVisibility(View.GONE);

        String uid = FirebaseAuth.getInstance().getUid();
        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("Forms").child("vehicle").child(date).child(uid).child(fid);

        ref.child("vehicleNumber").setValue(n).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ref.child("startingMileage").setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        saveValues(n, date, ref, defectsList, fid);
                    }
                });
            }
        });
    }

    private void saveValues(String n, String d, final DatabaseReference ref, final ArrayList<String> defectsList, String fid) {
        vNumber = n;
        vReportDate = d;
        vReportGuid = fid;

        SharedPreferences settings = getApplicationContext()
                .getSharedPreferences("ANCHOR_INSULATION_DATA", 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("vNumber", vNumber);
        editor.putString("vReportDate", vReportDate);
        editor.putString("vReportGuid", vReportGuid);

        editor.apply();
        uploadNextDefect(ref, defectsList, 0);
    }

    private void uploadNextDefect(
            final DatabaseReference ref, final ArrayList<String> defects, final int i) {

        if(i == defects.size())
            openEndingMileageView();
        else {
            ref.child("defects").child(""+i).setValue(defects.get(i))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    uploadNextDefect(ref, defects, i+1);
                }
            });
        }
    }

    private void openEndingMileageView() {
        pBar.setVisibility(View.GONE);
        mainView.setVisibility(View.GONE);

        RelativeLayout eView = findViewById(R.id.endingMileageView);
        eView.setVisibility(View.VISIBLE);

        final Button btn = findViewById(R.id.submitEndingMileage);
        final EditText e = findViewById(R.id.vehicleEndingMileage);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = e.getText().toString();

                if(em.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VehicleActivity.this);
                    builder.setMessage("Please enter ending mileage " +
                            "(por favor entra el kilometraje final)");
                    builder.setTitle("Error");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.create().show();
                }
                else
                    submitEndingMileage(e.getText().toString());
            }
        });
    }

    private void submitEndingMileage(String e) {
        String uid = FirebaseAuth.getInstance().getUid();
        final DatabaseReference ref = FirebaseDatabase
                .getInstance().getReference("Forms")
                .child("vehicle").child(vReportDate).child(uid).child(vReportGuid);

        ref.child("endingMileage").setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                clearSavedValues(true);
            }
        });
    }

    private void clearSavedValues(boolean close) {
        SharedPreferences settings = getApplicationContext()
                .getSharedPreferences("ANCHOR_INSULATION_DATA", 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("vNumber", "");
        editor.putString("vReportDate", "");
        editor.putString("vReportGuid", "");

        editor.apply();

        if(close)
            finishAndExit();
        else
            setView();
    }

    private void finishAndExit() {
        Toast.makeText(VehicleActivity.this, "Success", Toast.LENGTH_LONG).show();
        VehicleActivity.this.finish();
    }

    private String generateId() {
        String random = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();

        while (sb.length() < 20) {
            int index = (int) (rnd.nextFloat() * random.length());
            sb.append(random.charAt(index));
        }

        return sb.toString();
    }
}
