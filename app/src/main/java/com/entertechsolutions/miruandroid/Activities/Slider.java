package com.entertechsolutions.miruandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.entertechsolutions.miruandroid.Adapters.Sliding_image_adapter;
import com.entertechsolutions.miruandroid.Models.image_Model;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Storage.SharedPreffManager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Slider extends AppCompatActivity {

    Button sign_in,sign_up;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<image_Model> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.british,R.drawable.american,R.drawable.international};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Slider.this, Registration.class);
                startActivity(intent);
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Slider.this, Login.class);
                startActivity(intent);
            }
        });

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();

        // Log.e("naam", "Name  " + SharedPreffManager.getInstance(this).getUser().getFullName());
       // Log.e("naam", "id  " + SharedPreffManager.getInstance(this).getUser().getPhoneNo());

        init();

    }


    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPreffManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, ChildList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    private ArrayList<image_Model> populateList(){

        ArrayList<image_Model> list = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            image_Model imageModel = new image_Model();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }


    private void init() {

        mPager =  findViewById(R.id.pager);
        mPager.setAdapter(new Sliding_image_adapter(Slider.this,imageModelArrayList));

        CirclePageIndicator indicator = findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
}
