package com.entertechsolutions.miruandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.abdshammout.UBV.OnClickListenerBreadcrumbs;
import com.abdshammout.UBV.UltimateBreadcrumbsView;
import com.abdshammout.UBV.model.PathItem;
import com.abdshammout.UBV.model.PathItemStyle;
import com.entertechsolutions.miruandroid.Adapters.MainListAdapter;
import com.entertechsolutions.miruandroid.Models.HierarchyList;
import com.entertechsolutions.miruandroid.Models.HierarchyModel;
import com.entertechsolutions.miruandroid.Models.Task_Model;
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

public class MainActivity extends AppCompatActivity {

    UltimateBreadcrumbsView ultimateBreadcrumbsView;
    private LinearLayoutManager lLayout;
    List<Task_Model> rowListItem;
    MainListAdapter adapter;
    List<HierarchyList> requestlist = new ArrayList<>();
    Button back_btn;
    HierarchyModel list ;
    String userToken ;
    String Rid = "0";
    android.app.AlertDialog waitingDialog;
    ArrayList<String> stringsBreadcrumb = new ArrayList<>();
    PathItem pathItem ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        back_btn = findViewById(R.id.back_btn_l);
        back_btn.setOnClickListener(v -> onBackPressed());
        stringsBreadcrumb.add("System");
        userToken = SharedPreffManager.getInstance(this).getUser().getAuthToken();


        ultimateBreadcrumbsView = findViewById(R.id.ultimateBreadcrumbsView);

        PathItemStyle pathItemStyle = new PathItemStyle();
        pathItemStyle.setPathItemBackgroundResId(R.drawable.bg_two_corner);
        pathItemStyle.setActivePathItemBackgroundResId(R.drawable.bg_two_corner_active);

        pathItemStyle.setPathItemTextColor(getResources().getColor(android.R.color.white));//or Color.WHITE
        pathItemStyle.setActivePathItemTextColor(Color.WHITE);


        ultimateBreadcrumbsView.setPathItemStyle(pathItemStyle);

        ultimateBreadcrumbsView.setBackButtonBackgroundRes(R.drawable.bg_two_corner);
        ultimateBreadcrumbsView.setBackButtonIconRes(R.drawable.ic_home_black_24dp);





        ultimateBreadcrumbsView.setOnClickListenerBreadcrumbs(new OnClickListenerBreadcrumbs() {
            @Override
            public void onBackClick() {
                getRequest("0");
                ultimateBreadcrumbsView.back();
                Toast.makeText(MainActivity.this,
                        "onBackClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPathItemClick(int index, String title, int id) {
                getRequest(Integer.toString(id));
                Toast.makeText(MainActivity.this,
                        index+"  onPathItemClick = "+title, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPathItemLongClick(int index, String title, int id) {
                Toast.makeText(MainActivity.this,
                        index+"  onPathItemLongClick = "+title, Toast.LENGTH_SHORT).show();
            }
        });


        ultimateBreadcrumbsView.initUltimateBreadcrumbsView();

       // breadcrumbLayout.setBreadCrumbItems();path.map { BreadCrumb(it) }))

        waitingDialog = new SpotsDialog.Builder()
                .setContext(MainActivity.this)
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();

        if (Rid=="0"){
            getRequest(Rid);
        }

        RecyclerView rView = findViewById(R.id.recycleMain);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        rView.setItemAnimator(new DefaultItemAnimator());
        rView.setLayoutManager(layoutManager);



       /* rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(MainActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recycleMain);
        recyclerView.setLayoutManager(lLayout);*/


          adapter = new MainListAdapter(MainActivity.this, requestlist, new MainListAdapter.OnItemClickListener() {
            @Override
            public void onClick(HierarchyList main_task_model) {
                getRequest(main_task_model.getId().toString());

                if (!list.getData().isEmpty()) {
                    pathItem = new PathItem(main_task_model.getName(), main_task_model.getId());

                    ultimateBreadcrumbsView.addToPath(pathItem);

                }
               // breadcrumbLayout.addCrumb(breadcrumbLayout.newCrumb().setText(main_task_model.getName()));
            }

              @Override
              public void topic(HierarchyList hierarchyList) {
                  Intent it = new Intent(MainActivity.this, Topics.class);
                  it.putExtra("Rid", hierarchyList.getId().toString());
                  startActivity(it);
                  Toast.makeText(MyApplication.getContext(),hierarchyList.getId().toString(),Toast.LENGTH_LONG).show();
              }
          });

        rView.setAdapter(adapter);

       // recyclerView.setAdapter(adapter);



    }


    public void getRequest(String rid){

        waitingDialog.show();
        String url = "api/Hierarchy/GetByParent?id="+rid;
        ServiceUtils.api.gettaskF(url,userToken)
                .enqueue(new Callback<HierarchyModel>() {
                    @Override
                    public void onResponse(Call<HierarchyModel> call, Response<HierarchyModel> response) {
                        // progress.setVisibility(View.GONE);
                         list = response.body();

                        if (response.isSuccessful() && response.body() != null){
                            assert list != null;
                            Log.e("onResponse "," " + response);
                           // Log.d("data","main  " +list.getData().get(0).getName());
                            if (list.getIsSuccess()){

                                if (!list.getData().isEmpty()) {

                                    Log.e("data", "Token  " + list.getData());
                                    //requestlist.clear();

                                    if (list.getData() != null) {

                                        if (rid == "0") {
                                            requestlist.clear();
                                            requestlist.addAll(list.getData());
                                            adapter.notifyDataSetChanged();
                                        } else {
                                            requestlist.clear();
                                            requestlist.addAll(list.getData());
                                            adapter.notifyDataSetChanged();
                                            if (requestlist.isEmpty()) {
                                                Toast.makeText(MyApplication.getContext(), "Not Found", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    } else {
                                        Toast.makeText(MyApplication.getContext(), "Not Found", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(MyApplication.getContext(), "Not Found", Toast.LENGTH_LONG).show();
                                }
                               // progressBar.setVisibility(View.GONE);
                                waitingDialog.hide();

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

                        Toast.makeText(MyApplication.getContext(),"check"+t, Toast.LENGTH_SHORT).show();
                        Log.e("failure","here................  "+t);
                       // progressBar.setVisibility(View.GONE);
                        waitingDialog.hide();
                    }
                });

    }


    private List<Task_Model> getAllItemList(){

        List<Task_Model> allItems = new ArrayList<>();
        allItems.add(new Task_Model("British Curriculum"));
        allItems.add(new Task_Model("International Baccalaureate"));
        allItems.add(new Task_Model("American Curriculum"));
        return allItems;
    }




}
