package com.entertechsolutions.miruandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.entertechsolutions.miruandroid.Adapters.SubscriptionAdapter;
import com.entertechsolutions.miruandroid.Models.ChildListAdapter;
import com.entertechsolutions.miruandroid.Models.ChildListModel;
import com.entertechsolutions.miruandroid.Models.GenericResponseModelArray;
import com.entertechsolutions.miruandroid.Models.GidModel;
import com.entertechsolutions.miruandroid.Models.LoginResponce;
import com.entertechsolutions.miruandroid.Models.SubscriptionModel;
import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Storage.SharedPreffManager;
import com.entertechsolutions.miruandroid.Utils.ServiceUtils;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Subscriptions extends AppCompatActivity {

    SubscriptionAdapter adapter;
    List<SubscriptionModel> requestlist = new ArrayList<>();
    String userToken ;
    String Rid ;
    android.app.AlertDialog waitingDialog;
    Button back_btn;
    int profileid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        RecyclerView recyclerView = findViewById(R.id.recycleSubs);
        Intent intent = getIntent();
        userToken = SharedPreffManager.getInstance(this).getUser().getAuthToken();
        Rid = SharedPreffManager.getInstance(this).getUser().getId().toString();
        profileid = intent.getIntExtra("profile",-1);

        back_btn = findViewById(R.id.backBtnSubs);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        waitingDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();

        getRequest();
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SubscriptionAdapter(Subscriptions.this, requestlist, new SubscriptionAdapter.OnItemClickListener() {
            @Override
            public void onClick(SubscriptionModel main_task_model) {
                if (main_task_model.getIsTrail()) {
                    Intent intent = new Intent(Subscriptions.this, MainActivity.class);
                    intent.putExtra("systemId",main_task_model.getSystemId());
                    intent.putExtra("text", "s");
                    //waitingDialog.hide();
                    startActivity(intent);
                   // UserLogin(main_task_model.getId(),main_task_model.getAmount(),main_task_model.getSystemId());
                }
                else {
                   UserLogin(main_task_model.getId(),main_task_model.getAmount(),main_task_model.getSystemId());
                }

            }
        });
        recyclerView.setAdapter(adapter);
    }


    public void getRequest(){

        waitingDialog.show();
        String url = "api/Subscription/Get";
        ServiceUtils.api.getSubs(url,userToken)
                .enqueue(new Callback<GenericResponseModelArray<SubscriptionModel>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<GenericResponseModelArray<SubscriptionModel>> call, Response<GenericResponseModelArray<SubscriptionModel>> response) {
                        // progress.setVisibility(View.GONE);
                        GenericResponseModelArray<SubscriptionModel>  list = response.body();

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
                                    Toast.makeText(MyApplication.getContext(),"HERE !",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(MyApplication.getContext(),"Not Found",Toast.LENGTH_LONG).show();
                                    waitingDialog.hide();
                                }
                                // progressBar.setVisibility(View.GONE);
                                waitingDialog.hide();

                                // Toast.makeText(Verify_OTP.this,loginResponse.getError(),Toast.LENGTH_LONG).show();
                                /* Intent it = new Intent(Registration_one.this, Registration_one.class);
                                 startActivity(it);*/
                            }
                            else {
                                Toast.makeText(MyApplication.getContext(),list.getError(),Toast.LENGTH_LONG).show();
                                waitingDialog.hide();
                            }
                        }

                        //recyclerView.setAdapter( new Topics_Recycler(topics.this,list.getTopicList()));

                        // Toast.makeText(topics.this,"success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<GenericResponseModelArray<SubscriptionModel>> call, Throwable t) {

                        Toast.makeText(MyApplication.getContext(),"check"+t, Toast.LENGTH_SHORT).show();
                        Log.e("failure","here................  "+t);
                        // progressBar.setVisibility(View.GONE);
                        waitingDialog.hide();
                    }
                });

    }


    public void  UserLogin(int subsId,Double amount,int systemid){
        waitingDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("profileId", profileid);
        jsonObject.addProperty("parentId", Rid);
        jsonObject.addProperty("subscriptionId", subsId);
        jsonObject.addProperty("remarks", "");

        ServiceUtils.api.getgu(jsonObject,userToken)
                .enqueue(new Callback<GidModel>() {
                    @Override
                    public void onResponse(@NonNull Call<GidModel> call, @NonNull Response<GidModel> response) {
                        //progressBar.setVisibility(View.GONE);
                        GidModel loginResponse = response.body();
                        waitingDialog.hide();
                        if (response.isSuccessful() && response.body() != null) {
                            assert loginResponse != null;
                            if (loginResponse.getIsSuccess()) {
                                Toast.makeText(Subscriptions.this, loginResponse.getData(), Toast.LENGTH_LONG).show();
                                Intent it = new Intent(Subscriptions.this, PaymantWebView.class);
                                 it.putExtra("guid", loginResponse.getData());
                                 it.putExtra("amount",amount);
                                 it.putExtra("systemId",systemid);
                                startActivity(it);
                                finish();
                            } else {
                                Toast.makeText(Subscriptions.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<GidModel> call, Throwable t) {
                        Toast.makeText(Subscriptions.this, "Something Went Wrong Please Try Again", Toast.LENGTH_SHORT).show();
                        waitingDialog.hide();


                    }
                });
    }

}