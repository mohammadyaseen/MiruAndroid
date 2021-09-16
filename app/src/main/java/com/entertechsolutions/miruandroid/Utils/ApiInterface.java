package com.entertechsolutions.miruandroid.Utils;


import com.entertechsolutions.miruandroid.Activities.Videos;
import com.entertechsolutions.miruandroid.Models.ChildListModel;
import com.entertechsolutions.miruandroid.Models.ChildRegisterResponce;
import com.entertechsolutions.miruandroid.Models.GenericResponseModelArray;
import com.entertechsolutions.miruandroid.Models.GidModel;
import com.entertechsolutions.miruandroid.Models.HierarchyModel;
import com.entertechsolutions.miruandroid.Models.LoginResponce;
import com.entertechsolutions.miruandroid.Models.SignUpResponce;
import com.entertechsolutions.miruandroid.Models.SubscriptionModel;
import com.entertechsolutions.miruandroid.Models.TopicsModel;
import com.entertechsolutions.miruandroid.Models.VerifyModel;
import com.entertechsolutions.miruandroid.Models.VideosModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {



    //Get task list
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<HierarchyModel> gettaskF(
            @Url String url,
            @Header("token") String token);

    //Registration
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Parents/Add")
    Call<SignUpResponce> register(
           // @Header("token") String token,
            @Body JsonObject body
    );

    //Login
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Parents/Login")
    Call<LoginResponce> Login(
            @Body JsonObject body
    );


    //Verify code
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Parents/Verify")
    Call<VerifyModel> getcode(
            // @Header("token") String token,
            @Body JsonObject body
    );

    //Get task list
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<TopicsModel>> getTopic(
            @Url String url,
            @Header("token") String token);

    //Get task list
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<VideosModel>> getVideos(
            @Url String url,
            @Header("token") String token);


    //Get child list
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<ChildListModel>> getChild(
            @Url String url,
            @Header("token") String token);


    //Registration
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Profiles/Add")
    Call<ChildRegisterResponce> registerChild(
            @Header("token") String token,
            @Body JsonObject body
    );


    //Get child list
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<SubscriptionModel>> getSubs(
            @Url String url,
            @Header("token") String token);


    //GUID
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/UserSubscription/Add")
    Call<GidModel> getgu(
            @Body JsonObject body,
             @Header("token") String token);


  /*  // number verification
   // @FormUrlEncoded
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/SurveyorApp/login")
    Call<GenericResponseModel<Login_Data_Model>> Login(
            @Body JsonObject body
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/SurveyorApp/UpdatePushNotificationToken")
    Call<GenericResponseModel<Token_Data>> tokenUpdate(@Header("token") String token, @Body JsonObject body);

    //Get Summary
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("api/SurveyorApp/GetTaskSummary")
    Call<GenericResponseModelArray<Summary_Data_Model>> getSummary(@Header("token") String token);

    //Get task
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<Main_Task_Data_Model>> gettask(
            @Header("token") String token,
            @Url String url
    );

    //Get task
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModel<Request_Data_Model>> getrequest(
            @Url String url,
            @Header("token") String token
    );

    //Get Cost
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModel<Get_Costing_Data_Model>> getCost(
            @Url String url,
            @Header("token") String token
    );
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/SurveyorApp/UpdateCosting")
    Call<GenericResponseModel<Get_Costing_Data_Model>> updatecost(
            @Header("token") String token,
            @Body JsonObject body
    );

    //Get Advisory
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModel<Get_Advisory_Data_Model>> getadvisory(
            @Url String url,
            @Header("token") String token
    );

    //update Advisory
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/SurveyorApp/Updateadvisory")
    Call<GenericResponseModel<Get_Advisory_Data_Model>> updatadvisory(
            @Header("token") String token,
            @Body String body *//*JsonObject*//*
    );

    //Get dropdown
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<DropDown>> getDropdown(
            @Url String url,
            @Header("token") String token
    );


    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<Weekly_Data>> getAllWeekly(
            @Url String url,
            @Header("token") String token
    );


    //Get Question
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<Question_List>> getquestion(
            @Url String url,
            @Header("token") String token);

    //get Answers
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModel<Question_Detail>> getAnswer(
            @Url String url,
            @Header("token") String token
    );

    //get Activity
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<Activity_Log>> getActivity(
            @Url String url,
            @Header("token") String token
    );

    //Images Url Create
    @Multipart
    @POST("upload/UploadFile")
    Call<GenericResponseModelArray<File_Upload_Data_Model>> getFiveImagesUrl(
            @Part MultipartBody.Part[] file
    );

    //update Request
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/SurveyorApp/UpdateRequest")
    Call<GenericResponseModel<Request_Data_Model>> updataRequest(
            @Header("token") String token,
            @Body String body *//*JsonObject*//*
    );


    //Get task
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModel<Weekly_View_Model>> getweek(
            @Url String url,
            @Header("token") String token
    );

    //Get task
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModel<Weekly>> getweekLast(
            @Url String url,
            @Header("token") String token
    );

    //Get dropdown
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<Products>> getProducts(
            @Url String url,
            @Header("token") String token
    );


    //Get task
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModel<Weekly>> getweeklast(
            @Url String url,
            @Header("token") String token
    );

    @Multipart
    @POST("upload/UploadFile")
    Call<GenericResponseModelArray<File_Upload_Data_Model>> get5Pics(
            @Part MultipartBody.Part[] file
    );

    //update Request
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/weeklyvisit/save")
    Call<GenericResponseModel<Weekly>> UpdateWeekly(
            @Header("token") String token,
            @Body String body *//*JsonObject*//*
    );

    //Get task list
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<Task_Model>> gettaskF(
            @Url String url,
            @Header("token") String token);


    // picture profile
    @Multipart
    @POST("upload/UploadFile")
    Call<GenericResponseModelArray<File_Upload_Data_Model>> uploadProfilePicture(
            @Part MultipartBody.Part file
    );

    //upload cnic
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/SurveyorApp/UpdateProfilrPic")
    Call<GenericResponseModel<Login_Data_Response>> uploadprofile(
            @Header("token") String token,
            @Body JsonObject body
    );

    //Done Task
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST
    Call<GenericResponseModel<Task_Model>> DoneTask(
            @Url String url,
            @Header("token") String token,
            @Body String body *//*JsonObject*//*);


    // number verification
    @Headers("Content-Type: application/json")
    @GET("api/User/CheckUser")
    Call<GenericResponseModel<Login_Data_Response>> getfarmer(@Header("token") String token);

    //get Villages
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<Villages>> getplaces(@Url String url, @Header("token") String token);

    @Headers("Content-Type: application/json")
    @GET
    Call<GenericResponseModelArray<Search_Farmer>> getuser(
            @Header("token") String token,
            @Url String url
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<Request>> getRequest(
            @Header("token") String token,
            @Url String url
    );


    //change password
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/User/ChangePassword")
    Call<GenericResponseModel<Object>> changePass(
            @Header("token") String token,
            @Body JsonObject body
    );


    //update Request
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST
    Call<GenericResponseModel<QuestionAdd>> updateQA(
            @Url String url,
            @Header("token") String token,
            @Body JsonObject body
    );


    //update Request
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST
    Call<GenericResponseModel<AnswerSave>> answersave(
            @Url String url,
            @Header("token") String token,
            @Body JsonObject body
    );

    //Done Task
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST
    Call<GenericResponseModel<AnswerSave>> markCorrect(
            @Url String url,
            @Header("token") String token,
            @Body String body *//*JsonObject*//*);


    //get products
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<ProductSummaryNormal>> getproducts(
            @Url String url,
            @Header("token") String token
    );


    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("api/farmerapp/GetCrops")
    Call<GenericResponseModelArray<Get_Crop_Data>> getcrops(@Header("token") String token);

    //get products
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET
    Call<GenericResponseModelArray<ProductSummaryGraph>> getproductsgraph(
            @Url String url,
            @Header("token") String token
    );


    //Done Task
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST
    Call<GenericResponseModel<Request_Data_Model>> disastermark(
            @Url String url,
            @Header("token") String token,
            @Body String body *//*JsonObject*//*);


    // get task summary
    @Headers("Content-Type: application/json")
    @GET("api/task/GetSurveyorTaskProgress")
    Call<GenericResponseModel<TaskSummary>> gettast(@Header("token") String token);


    //Get task
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("api/SurveyorApp/GetAreawiseTaskSummary")
    Call<GenericResponseModelArray<VillagesListModel>> getvillages(@Header("token") String token);


    *//* //@FormUrlEncoded
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("VerifyFarmer")
    Call<code_verify> getcode(
            @Body JsonObject body
    );
    // number verification
    @Headers("Content-Type: application/json")
    @GET("GetMyProfile")
    Call<newcl> getfarmer(@Header("token") String token);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST
    Call<Profile_Picture_Res_Model> send_profile(@Url String url,
                                                 @Body JsonObject body);

    //Registration
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("UpdateFarmer")
    Call<Register_Response_Model> register(
            @Header("token") String token,
            @Body JsonObject body
    );

    //Get Lands
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("GetAllLands")
    Call<Land_Response_Model> getland(@Header("token") String token);

    //Update LAnd
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("UpdateLand")
    Call<Update_Land_Response> updateLand(
            @Header("token") String token,
            @Body JsonObject body
    );

    //send Request
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("sendRequest")
    Call<Send_Request_Response> requestSend(
            @Header("token") String token,
            @Body JsonObject body
    );

    //Get All Request
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("GetAllRequests")
    Call<Get_Request_Response> getRequest(@Header("token") String token);

    //firebase Token
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("UpdatePushNotificationToken")
    Call<Token_Response> tokenUpdate(
            @Header("token") String token,
            @Body JsonObject body
    );*/

}
