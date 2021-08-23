package com.entertechsolutions.miruandroid.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.entertechsolutions.miruandroid.Models.ChildRegisterResponce;
import com.entertechsolutions.miruandroid.Models.SignUpResponce;
import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Storage.SharedPreffManager;
import com.entertechsolutions.miruandroid.Utils.ServiceUtils;
import com.entertechsolutions.miruandroid.Utils.Utility;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import id.zelory.compressor.Compressor;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterChild extends AppCompatActivity {

    Button back_btn,sign_up;
    CircleImageView profileimg;
    String imageFilePath;
    Uri photoURI;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int PICK_IMAGE = 1;
    public static final int SELECT_PICK = 1;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS= 7;
    File imageFile;
    String encodstring ;
    EditText childname ;
    String parentId;
    String userToken;
    android.app.AlertDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_child);
        profileimg = findViewById(R.id.user);
        checkAndroidVersion();
        parentId = SharedPreffManager.getInstance(this).getUser().getId().toString();
        userToken = SharedPreffManager.getInstance(this).getUser().getAuthToken();


        waitingDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();

        childname = findViewById(R.id.childnamereal);
        back_btn = findViewById(R.id.backBtnAddChild);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sign_up = findViewById(R.id.submitChild);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_signup();
              /*  Intent intent = new Intent(RegisterChild.this, MainActivity.class);
                //waitingDialog.hide();
                startActivity(intent);*/
            }
        });

        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageFilePath == null) {

                    selectImage();


                }
                else {
                    androidx.appcompat.app.AlertDialog.Builder mBuilder = new androidx.appcompat.app.AlertDialog.Builder(RegisterChild.this);
                    View mView = getLayoutInflater().inflate(R.layout.layout_for_profile_view, null);
                    PhotoView photoView = mView.findViewById(R.id.imagepreview);
                    Button close,remove;
                    close = mView.findViewById(R.id.closep);
                    remove = mView.findViewById(R.id.removep);

                    photoView.setImageURI(photoURI);
                    mBuilder.setView(mView);
                    androidx.appcompat.app.AlertDialog mDialog = mBuilder.create();
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                            profileimg.setImageDrawable(getDrawable(R.drawable.ic_person_black_24dp));
                            //takepc.setImageBitmap(null);
                            imageFilePath = null;
                            profileimg.setBorderColor(getResources().getColor(R.color.light_gray));
                            profileimg.setBorderWidth(5);
                        }
                    });
                    mDialog.show();
                }

            }
        });

    }

    private static String encodeFileToBase64Binary(File file){
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.getEncoder().encodeToString(bytes);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }

    private void user_signup() {

        String Fname = childname.getText().toString().trim();

        if (Fname.isEmpty()) {
            childname.setError("Child Name Is Required");
            childname.requestFocus();
            return;
        }

        if(imageFilePath == null ){
            Toast.makeText(this,"Please Select Profile Picture",Toast.LENGTH_LONG).show();
            return;
        }



        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", Fname);
        jsonObject.addProperty("base64", encodstring);
        jsonObject.addProperty("parentId", parentId);

        Log.e("onResponse ", " " + jsonObject);
        ServiceUtils.api.registerChild(userToken,jsonObject)
                .enqueue(new Callback<ChildRegisterResponce>() {
                    @Override
                    public void onResponse(retrofit2.Call<ChildRegisterResponce> call, Response<ChildRegisterResponce> response) {
                        // progressBar.setVisibility(View.GONE);
                        waitingDialog.hide();
                        ChildRegisterResponce loginResponse = response.body();
                        // Log.e("onResponse ", " " + loginResponse.getError());

                        if (response.isSuccessful() && response.body() != null) {
                            assert loginResponse != null;
                            Log.e("onResponse ", " " + response);
                            if (loginResponse.getIsSuccess()) {
                                //loginResponse.ge().setAuthToken(userToken);
                                Log.e("data", "Token  " + loginResponse.getMessage());
                                Toast.makeText(RegisterChild.this,"Child Added ",Toast.LENGTH_LONG).show();
                                onBackPressed();
                       /* Intent it = new Intent(Registration_one.this, Registration_Two.class);
                        startActivity(it);*/
                            } else {
                                Toast.makeText(RegisterChild.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(retrofit2.Call<ChildRegisterResponce> call, Throwable t) {
                        //progressBar.setVisibility(View.GONE);
                        waitingDialog.hide();
                        Toast.makeText(RegisterChild.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
       /* Intent it = new Intent(Verify_OTP.this, Registration_one.class);
        startActivity(it);
        finish();*/

    }


    private void selectImage() {
        CharSequence[] items ;
        if (MyApplication.local==1){
            items = new CharSequence[]{"Take Photo", "Choose from Library",
                    "Cancel"};
        }
        else {
            items = new CharSequence[]{"Take Photo", "Cancel"};
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterChild.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(RegisterChild.this);
                if (MyApplication.local==1){
                    if (items[item].equals("Take Photo")) {
                        // userChoosenTask="Take Photo";
                        if(result)
                            cameraIntent();
                    }
                    else if (items[item].equals("Choose from Library")) {
                        //userChoosenTask="Choose from Library";
                        if(result)
                            galleryIntent();
                    }
                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
                else {
                    if (items[item].equals("Take Photo")) {
                        //userChoosenTask="Choose from Library";
                        if(result)
                            cameraIntent();
                    }
                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAPTURE_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    imageFile = new Compressor(getApplicationContext()).compressToFile(new File(imageFilePath));
                    encodstring = encodeFileToBase64Binary(imageFile);
                   // Toast.makeText(RegisterChild.this, encodstring, Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                CircleImageView croppedImageView = findViewById(R.id.user);

                croppedImageView.setImageURI(photoURI);
                // uploadProfileImage();
            }
        }

        else if (requestCode == SELECT_PICK){
            Bitmap bm = null;
            if (data != null) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    // Log.e("new ", "Next" + fp.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imageFilePath = getRealPathFromURI( data.getData());
                try {
                    imageFile = new Compressor(getApplicationContext()).compressToFile(new File(imageFilePath));
                    Log.e("new ", "Next" + data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("new ", "Next  " + imageFilePath);
                CircleImageView croppedImageView = findViewById(R.id.user);

                croppedImageView.setImageBitmap(bm);
            }
        }
    }


    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void cameraIntent(){
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        getPackageName() + ".fileprovider",
                        photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("in fragment on request", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("in fragment on request", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterChild.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(RegisterChild.this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(RegisterChild.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(RegisterChild.this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();

        } else {
            // code for lollipop and pre-lollipop devices
        }

    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(RegisterChild.this,
                Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(RegisterChild.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(RegisterChild.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(RegisterChild.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(RegisterChild.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }


}
