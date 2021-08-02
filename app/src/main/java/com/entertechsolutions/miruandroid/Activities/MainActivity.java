package com.entertechsolutions.miruandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.entertechsolutions.miruandroid.Adapters.MainListAdapter;
import com.entertechsolutions.miruandroid.Models.GenericResponseModelArray;
import com.entertechsolutions.miruandroid.Models.HierarchyList;
import com.entertechsolutions.miruandroid.Models.HierarchyModel;
import com.entertechsolutions.miruandroid.Models.Task_Model;
import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Utils.ServiceUtils;
import com.github.ayvazj.breadcrumblayout.BreadcrumbLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BreadcrumbLayout breadcrumbLayout;
    private LinearLayoutManager lLayout;
    List<Task_Model> rowListItem;
    MainListAdapter adapter;
    List<HierarchyList> requestlist = new ArrayList<>();
    Button back_btn;

    String userToken = "6bPUFHaUpxMIVeP6YCVSZg==";
    ProgressBar progressBar;
    String Rid = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        breadcrumbLayout = findViewById(R.id.breadcrumbLayout);

        //getRequest();
       // RecyclerView rView = findViewById(R.id.recycleMain);
       // LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
       /* rView.setItemAnimator(new DefaultItemAnimator());
        rView.setLayoutManager(layoutManager);



        rView.setAdapter(adapter);
*/
        rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(MainActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recycleMain);
        recyclerView.setLayoutManager(lLayout);



        MainListAdapter adapter = new MainListAdapter(MainActivity.this, rowListItem, new MainListAdapter.OnItemClickListener() {
            @Override
            public void onClick(Task_Model main_task_model) {

            }
        });

        recyclerView.setAdapter(adapter);







        // add a breadcrumb
        breadcrumbLayout.addCrumb(breadcrumbLayout.newCrumb().setText("breadcrumb text"));

        // remove a breadcrumb
        breadcrumbLayout.removeCrumbAt(breadcrumbLayout.getCrumbCount() - 1);

        // listen for selections
        breadcrumbLayout.setOnBreadcrumbSelectedListener(new BreadcrumbLayout.OnBreadcrumbSelectedListener() {
            @Override
            public void onBreadcrumbSelected(BreadcrumbLayout.Breadcrumb crumb) {
               /* if ( crumb.getTag() != null && crumb.getTag() instanceof Pokemon) {
                   // imageView.setImageResource(((Pokemon)crumb.getTag()).drawableRes);
                }
                else {
                   // imageView.setImageDrawable(null);
                }*/
            }

            @Override
            public void onBreadcrumbUnselected(BreadcrumbLayout.Breadcrumb crumb) {

            }

            @Override
            public void onBreadcrumbReselected(BreadcrumbLayout.Breadcrumb crumb) {

            }
        });


    }


    public void getRequest(){

        String url = "api/Hierarchy/GetParent?id="+Rid;

            ServiceUtils.api.gettaskF(url,userToken)
                .enqueue(new Callback<HierarchyModel>() {
                    @Override
                    public void onResponse(Call<HierarchyModel> call, Response<HierarchyModel> response) {
                        // progress.setVisibility(View.GONE);
                        HierarchyModel list = response.body();

                        if (response.isSuccessful() && response.body() != null){
                            assert list != null;
                            Log.e("onResponse "," " + response);
                            Log.e("data","main  " +list.getData());
                            if (list.getIsSuccess()){

                                Log.e("data","Token  " +list.getData());
                                requestlist.clear();
                                requestlist.addAll(list.getData());
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);

                                // Toast.makeText(Verify_OTP.this,loginResponse.getError(),Toast.LENGTH_LONG).show();
                                /* Intent it = new Intent(Registration_one.this, Registration_one.class);
                                 startActivity(it);*/
                            }
                            else {
                                Toast.makeText(MyApplication.getContext(),list.getResult(),Toast.LENGTH_LONG).show();
                            }
                        }

                        //recyclerView.setAdapter( new Topics_Recycler(topics.this,list.getTopicList()));

                        // Toast.makeText(topics.this,"success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HierarchyModel> call, Throwable t) {

                        Toast.makeText(MyApplication.getContext(),"Something Went Wrong Please Try Again", Toast.LENGTH_SHORT).show();
                        Log.e("failure","here................");
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }


    private List<Task_Model> getAllItemList(){

        List<Task_Model> allItems = new ArrayList<>();
        allItems.add(new Task_Model("Changed Plug"));
        allItems.add(new Task_Model("Clean"));
        allItems.add(new Task_Model("Change dex"));
        return allItems;
    }




}
