package com.entertechsolutions.miruandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.entertechsolutions.miruandroid.Adapters.MainListAdapter;
import com.entertechsolutions.miruandroid.Adapters.TopicsAdapter;
import com.entertechsolutions.miruandroid.Models.GenericResponseModelArray;
import com.entertechsolutions.miruandroid.Models.HierarchyList;
import com.entertechsolutions.miruandroid.Models.HierarchyModel;
import com.entertechsolutions.miruandroid.Models.TopicsModel;
import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Storage.SharedPreffManager;
import com.entertechsolutions.miruandroid.Utils.ServiceUtils;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Topics extends AppCompatActivity {

    TopicsAdapter adapter;
    List<TopicsModel> requestlist = new ArrayList<>();
    Button back_btn;
    TopicsModel list ;
    String userToken ;
    String Rid ;
    android.app.AlertDialog waitingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        Intent intent = getIntent();
        Rid = intent.getStringExtra("Rid");
        Log.e("Id   "," " + Rid);
        userToken = SharedPreffManager.getInstance(this).getUser().getAuthToken();

        back_btn = findViewById(R.id.backTopics);
        back_btn.setOnClickListener(v -> onBackPressed());
        waitingDialog = new SpotsDialog.Builder()
                .setContext(Topics.this)
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();


        getRequest(Rid);
        RecyclerView rView = findViewById(R.id.recycleTopics);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        rView.setItemAnimator(new DefaultItemAnimator());
        rView.setLayoutManager(layoutManager);

        adapter = new TopicsAdapter(Topics.this, requestlist, new TopicsAdapter.OnItemClickListener() {
            @Override
            public void onClick(TopicsModel main_task_model) {

                Intent it = new Intent(Topics.this, Videos.class);
                it.putExtra("Rid", main_task_model.getId().toString());
                startActivity(it);
                Toast.makeText(MyApplication.getContext(),main_task_model.getId().toString(),Toast.LENGTH_LONG).show();

            }
        });
        rView.setAdapter(adapter);
    }

    public void getRequest(String rid){

        waitingDialog.show();
        String url = "api/Topics/GetByCourse?id="+rid;
        ServiceUtils.api.getTopic(url,userToken)
                .enqueue(new Callback<GenericResponseModelArray<TopicsModel>>() {
                    @Override
                    public void onResponse(Call<GenericResponseModelArray<TopicsModel>> call, Response<GenericResponseModelArray<TopicsModel>> response) {
                        // progress.setVisibility(View.GONE);
                        GenericResponseModelArray<TopicsModel>  list = response.body();

                        if (response.isSuccessful() && response.body() != null){
                            assert list != null;
                            Log.e("onResponse "," " + response);
                            // Log.d("data","main  " +list.getData().get(0).getName());
                            if (list.getStatus()){

                                Log.e("data","Token  " +list.getData());
                                //requestlist.clear();

                                if (list.getData()!=null) {
                                        requestlist.clear();
                                        requestlist.addAll(list.getData());
                                        adapter.notifyDataSetChanged();
                                }
                                else {
                                    Toast.makeText(MyApplication.getContext(),"Not Found",Toast.LENGTH_LONG).show();
                                }
                                // progressBar.setVisibility(View.GONE);
                                waitingDialog.hide();

                                // Toast.makeText(Verify_OTP.this,loginResponse.getError(),Toast.LENGTH_LONG).show();
                                /* Intent it = new Intent(Registration_one.this, Registration_one.class);
                                 startActivity(it);*/
                            }
                            else {
                                Toast.makeText(MyApplication.getContext(),list.getError(),Toast.LENGTH_LONG).show();
                            }
                        }

                        //recyclerView.setAdapter( new Topics_Recycler(topics.this,list.getTopicList()));

                        // Toast.makeText(topics.this,"success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<GenericResponseModelArray<TopicsModel>> call, Throwable t) {

                        Toast.makeText(MyApplication.getContext(),"check"+t, Toast.LENGTH_SHORT).show();
                        Log.e("failure","here................  "+t);
                        // progressBar.setVisibility(View.GONE);
                        waitingDialog.hide();
                    }
                });

    }


}
