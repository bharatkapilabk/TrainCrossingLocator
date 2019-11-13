package com.example.traincrossinglocator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
RecyclerView r1;
LinearLayoutManager l1;
TextView textView;
Calendar calendar;
TrainData trainData;
NavigationView navigation_view;
ImageView nav;
DrawerLayout drawer_layout;
ActionBarDrawerToggle mDrawerToggle;
LinearLayout shareapp,viewtrains,viewcrossings,iiiii;
FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
DatabaseReference databaseReference=firebaseDatabase.getReference("Data").child("Status");
DatabaseReference databaseReference1=firebaseDatabase.getReference("Data").child("Time");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        trainData=new TrainData("closed","2:00");

        databaseReference1.setValue(trainData);


        r1=findViewById(R.id.r1);
        l1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        r1.setLayoutManager(l1);
        TrainAdapter trainAdapter=new TrainAdapter(this);
        r1.setAdapter(trainAdapter);
        nav=findViewById(R.id.nav);
        navigation_view=findViewById(R.id.navigation_view);
        drawer_layout=findViewById(R.id.drawer_layout);
        shareapp=findViewById(R.id.shareapp);
        viewcrossings=findViewById(R.id.viewcrossings);
        viewtrains=findViewById(R.id.viewtrains);


        calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        String timeUntilClose=simpleDateFormat.format(calendar.getTime());
        Toast.makeText(this, timeUntilClose, Toast.LENGTH_SHORT).show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status=dataSnapshot.getValue(String.class);

                textView = r1.findViewById(R.id.t1);
                textView.setText(status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

nav.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        drawer_layout.closeDrawer(navigation_view);
        drawer_layout.openDrawer(navigation_view);

        mDrawerToggle = new ActionBarDrawerToggle(Home.this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @SuppressLint("NewApi")
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                float min = 0.9f;
                float max = 1.0f;
                float scaleFactor = (max - ((max - min) * slideOffset));
                }
        };
        /*mNavItemListner=new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Toast.makeText(Home.this,"View Trains", Toast.LENGTH_LONG).show();
                Log.d("sd", "xgfdgd");
            return true;
            }
        };*/
        drawer_layout.addDrawerListener(mDrawerToggle);
    }
});
        navigation_view.setNavigationItemSelectedListener(this);
            }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("checkkro", "chalpaya");
        int id=item.getItemId();
        if(id==R.id.viewtrains){
            Toast.makeText(Home.this,"View Trains", Toast.LENGTH_LONG).show();
            Log.d("sd", "xgfdgd");
            Intent intent=new Intent(Home.this,ViewTrains.class);
            startActivity(intent);
        }
else if(id==R.id.viewcrossings){
            Toast.makeText(Home.this,"View Crossings", Toast.LENGTH_LONG).show();
        }
else if(id==R.id.feedback){
            Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.google.com"));
            startActivity(browserIntent);
        }
else if(id==R.id.shareapp){
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        }
return true;
    }
}