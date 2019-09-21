package com.anchorinsulation.anchorinsulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String fullName;
    boolean spanishMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadNavigationView(navigationView);
    }

    private void loadNavigationView(NavigationView nView) {
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email", "");
        String fName = extras.getString("fName", "");
        String lName = extras.getString("lName", "");
        String posit = extras.getString("posit", "");
        String phone = extras.getString("phone", "");

        View header = nView.getHeaderView(0);
        TextView nm = header.findViewById(R.id.profileName);
        TextView po = header.findViewById(R.id.profilePosition);
        TextView em = header.findViewById(R.id.profileEmail);
        TextView ph = header.findViewById(R.id.profilePhone);

        fullName = fName+" "+lName;
        String p = "("+phone.substring(0, 3)+") "+phone.substring(3, 6)+"-"+phone.substring(6);
        nm.setText(fullName);
        po.setText(posit);
        em.setText(email);
        ph.setText(p);

        TextView wel = findViewById(R.id.welcomeText);
        String w = "Welcome, "+fName+"!";
        wel.setText(w);

        Button vBtn = findViewById(R.id.vBtn);
        Button gBtn = findViewById(R.id.gBtn);
        Button cBtn = findViewById(R.id.cBtn);
        Button sBtn = findViewById(R.id.sBtn);

        vBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChecklist("vehicle");
            }
        });

        gBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoodCatch();
            }
        });

        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChecklist("cellulose");
            }
        });

        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChecklist("spray_foam");
            }
        });

        final ToggleButton togBtn = findViewById(R.id.spanishBtn);
        togBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    switchSpanish();
                else
                    switchEnglish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.putExtra("hasLoggedIn", true);
            MainActivity.this.startActivity(i);
            MainActivity.this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_vehicle) {
            openChecklist("vehicle");
        }
        else if (id == R.id.nav_good_catch) {
            openGoodCatch();
        }
        else if (id == R.id.nav_cellulose) {
            openChecklist("cellulose");
        }
        else if (id == R.id.nav_spray_foam) {
            openChecklist("spray_foam");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openChecklist(String type) {
        Intent i;

        if(type.equals("cellulose"))
            i = new Intent(MainActivity.this, CelluloseActivity.class);
        else if(type.equals("vehicle"))
            i = new Intent(MainActivity.this, VehicleActivity.class);
        else
            i = new Intent(MainActivity.this, SprayFoamActivity.class);


        i.putExtra("name", fullName);
        i.putExtra("spanish", spanishMode);
        MainActivity.this.startActivity(i);
    }

    private void openGoodCatch() {
        Intent i = new Intent(MainActivity.this, GoodCatchActivity.class);
        i.putExtra("name", fullName);
        i.putExtra("spanish", spanishMode);
        MainActivity.this.startActivity(i);
    }

    private void switchSpanish() {
        spanishMode = true;
        TextView wText = findViewById(R.id.welcomeText);
        TextView rText = findViewById(R.id.reportText);

        wText.setText(wText.getText().toString().replaceAll("Welcome", "Bienvenido"));
        String r = "Selecciona un informe por favor:";
        rText.setText(r);

        Button vBtn = findViewById(R.id.vBtn);
        Button gBtn = findViewById(R.id.gBtn);
        Button cBtn = findViewById(R.id.cBtn);
        Button sBtn = findViewById(R.id.sBtn);

        String v = "Informe de Vehículo";
        String g = "Buena Captura";
        String c = "Cellulosa\nMateriales Peligrosos";
        String s = "Espuma del Aerosol\nMateriales Peligrosos";

        vBtn.setText(v);
        gBtn.setText(g);
        cBtn.setText(c);
        sBtn.setText(s);

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        MenuItem vm = menu.findItem(R.id.nav_vehicle);
        MenuItem gm = menu.findItem(R.id.nav_good_catch);
        MenuItem cm = menu.findItem(R.id.nav_cellulose);
        MenuItem sm = menu.findItem(R.id.nav_spray_foam);

        vm.setTitle(v);
        gm.setTitle(g);
        cm.setTitle(c);
        sm.setTitle(s);
    }

    private void switchEnglish() {
        spanishMode = false;
        TextView wText = findViewById(R.id.welcomeText);
        TextView rText = findViewById(R.id.reportText);

        wText.setText(wText.getText().toString().replaceAll("Bienvenido", "Welcome"));
        String r = "Please select a report:";
        rText.setText(r);

        Button vBtn = findViewById(R.id.vBtn);
        Button gBtn = findViewById(R.id.gBtn);
        Button cBtn = findViewById(R.id.cBtn);
        Button sBtn = findViewById(R.id.sBtn);

        String v = "Vehicle Report";
        String g = "Good Catch";
        String c = "Cellulose Hazmat";
        String s = "Spray Foam Hazmat";

        vBtn.setText(v);
        gBtn.setText(g);
        cBtn.setText(c);
        sBtn.setText(s);

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        MenuItem vm = menu.findItem(R.id.nav_vehicle);
        MenuItem gm = menu.findItem(R.id.nav_good_catch);
        MenuItem cm = menu.findItem(R.id.nav_cellulose);
        MenuItem sm = menu.findItem(R.id.nav_spray_foam);

        vm.setTitle("Vehicle Report");
        gm.setTitle("Good Catch");
        cm.setTitle("Cellulose Hazmat");
        sm.setTitle("Spray Foam Hazmat");
    }
}
