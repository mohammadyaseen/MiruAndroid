package com.entertechsolutions.miruandroid.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.entertechsolutions.miruandroid.Adapters.TopicsAdapter;
import com.entertechsolutions.miruandroid.Models.ChildListAdapter;
import com.entertechsolutions.miruandroid.Models.ChildListModel;
import com.entertechsolutions.miruandroid.Models.GenericResponseModelArray;
import com.entertechsolutions.miruandroid.Models.Task_Model;
import com.entertechsolutions.miruandroid.Models.TopicsModel;
import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Storage.SharedPreffManager;
import com.entertechsolutions.miruandroid.Utils.ServiceUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.CirclePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.CirclePromptFocal;

public class ChildList extends AppCompatActivity {

    Button back_btn;
    private MaterialTapTargetPrompt mFabPrompt;
    ChildListAdapter adapter;
    List<ChildListModel> requestlist = new ArrayList<>();
    String userToken ;
    String Rid ;
    android.app.AlertDialog waitingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);
        RecyclerView recyclerView = findViewById(R.id.recycleChild);

        userToken = SharedPreffManager.getInstance(this).getUser().getAuthToken();
        Rid = SharedPreffManager.getInstance(this).getUser().getId().toString();

        back_btn = findViewById(R.id.backBtnChild);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ChildList.this);
                alert.setTitle("Do you want to logout?");
                // alert.setMessage("Message");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SharedPreffManager.getInstance(ChildList.this).clear();
                        Intent Log_out = new Intent(ChildList.this, Slider.class);
                        startActivity(Log_out);
                        finish();
                        Toast.makeText(ChildList.this,"Logged Out",Toast.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });
                alert.show();
            }
        });
        waitingDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();


       /* rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(ChildList.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(lLayout);*/

       getRequest(Rid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

         adapter = new ChildListAdapter(ChildList.this, requestlist, new ChildListAdapter.OnItemClickListener() {
            @Override
            public void onClick(ChildListModel main_task_model) {

                if (main_task_model.getPaymentModel().getStatus().equals("Paid")){
                    Intent it = new Intent(MyApplication.getContext(), MainActivity.class);
                    //it.putExtra("guid", loginResponse.getData());
                    it.putExtra("systemId",main_task_model.getPaymentModel().getSystemId());
                    it.putExtra("text", "s");
                    startActivity(it);
                }
                else {
                    Intent intent = new Intent(ChildList.this, Subscriptions.class);
                    intent.putExtra("profile",main_task_model.getId());
                    //waitingDialog.hide();
                    startActivity(intent);

                }

               /* */
            }
        });


        recyclerView.setAdapter(adapter);

        FloatingActionButton fab =  findViewById(R.id.addChild);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(ChildList.this, RegisterChild.class);
               // intent.putExtra("token",userToken);
                startActivity(intent);
            }
        });
    }

    public void showFabPrompt() {
        if (mFabPrompt != null)
        {
            return;
        }
        mFabPrompt = new MaterialTapTargetPrompt.Builder(ChildList.this)
                .setTarget(findViewById(R.id.addChild))
                .setFocalPadding(R.dimen.dp40)
                .setPrimaryTextColour(getResources().getColor(R.color.black))
                .setSecondaryTextColour(getResources().getColor(R.color.black))

                .setBackgroundColour(getResources().getColor(R.color.lightYellow))
                .setPrimaryText("Add Your Child Here")
                .setSecondaryText("Tap the add button and add your child")
                .setBackButtonDismissEnabled(true)
                .setPromptBackground(new CirclePromptBackground())
                .setPromptFocal(new CirclePromptFocal())
                /* .setAnimationInterpolator(new FastOutSlowInInterpolator())
                 .setPromptStateChangeListener((prompt, state) -> {
                     if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED || state == MaterialTapTargetPrompt.STATE_DISMISSING )
                     {
                         mFabPrompt = null;
                         //Do something such as storing a value so that this prompt is never shown again
                     }
                 })*/
                .create();
        mFabPrompt.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getRequest(Rid);
    }

    public void getRequest(String rid){

        waitingDialog.show();
        String url = "api/Profiles/GetByParent?id="+rid;
        ServiceUtils.api.getChild(url,userToken)
                .enqueue(new Callback<GenericResponseModelArray<ChildListModel>>() {
                    @Override
                    public void onResponse(Call<GenericResponseModelArray<ChildListModel>> call, Response<GenericResponseModelArray<ChildListModel>> response) {
                        // progress.setVisibility(View.GONE);
                        GenericResponseModelArray<ChildListModel>  list = response.body();

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
                                    if(requestlist.size() == 0){
                                        showFabPrompt();
                                    }
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
                    public void onFailure(Call<GenericResponseModelArray<ChildListModel>> call, Throwable t) {

                        Toast.makeText(MyApplication.getContext(),"check"+t, Toast.LENGTH_SHORT).show();
                        Log.e("failure","here................  "+t);
                        // progressBar.setVisibility(View.GONE);
                        waitingDialog.hide();
                    }
                });

    }



    private List<Task_Model> getAllItemList(){

        List<Task_Model> allItems = new ArrayList<>();
        allItems.add(new Task_Model("Muhammad yaseen"));
        allItems.add(new Task_Model("Muhammad muzammil"));
        allItems.add(new Task_Model("Muhammad haris"));
        return allItems;
    }

}
