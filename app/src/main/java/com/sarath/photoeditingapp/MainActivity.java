package com.sarath.photoeditingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView img_cam;
    ImageView img_gallery;
    private int REQUEST_CODE_PERMISSION = 101;
    private static final int PICK_IMAGE_REQUEST = 102;

    private final String[] REQUIRED_PERMISSIONS = new String[]{
        "android.permission.CAMERA",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_cam=(ImageView)findViewById(R.id.img_cam);
        img_gallery=(ImageView)findViewById(R.id.img_gallery);
        if(allPermissionsGranted()){

        }
        else
        {
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS,REQUEST_CODE_PERMISSION);
        }
        img_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });
        img_gallery .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
            }
        });
    }
    public String getRealPathFromURI(Context context, Uri contentUri)
    {
        Cursor cursor=getContentResolver().query(contentUri,null,null,null,null);
        cursor.moveToFirst();
        String document_id=cursor.getString( 0);
        document_id=document_id.substring(document_id.lastIndexOf( ":") +1);
        cursor.close();
        cursor=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Images.Media._ID+"=?",new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range") String path= cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    switch(requestCode)
    {
        case PICK_IMAGE_REQUEST:
            if(resultCode==RESULT_OK)
            {
                try{
                    Uri uri=data.getData();
                    String path=getRealPathFromURI(getApplicationContext(),uri);
                    Toast.makeText(this, path+ "", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,ShowPhotoActivity.class);
                i.putExtra("path",path+ "");
                startActivity(i);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            break;
    }
    }

    private boolean allPermissionsGranted()
    {
        for(String permission:REQUIRED_PERMISSIONS)
        {
            if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
        }
        return true;
    }

}