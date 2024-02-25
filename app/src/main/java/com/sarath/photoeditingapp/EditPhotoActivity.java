package com.sarath.photoeditingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

import java.io.File;

public class EditPhotoActivity extends AppCompatActivity {
    ImageView imgedit;
    String path="";
    public static Uri inputImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        imgedit=(ImageView)findViewById(R.id.imgedit);
        path=getIntent().getExtras().getString("path");
        File imgfile=new File(path);
        if(imgfile.exists())
        {
            Bitmap mybitmap= BitmapFactory.decodeFile(imgfile.getAbsolutePath());
            imgedit.setImageBitmap(mybitmap);
        }
        inputImageUri=Uri.fromFile(new File(path));
        inputImageUri= FileProvider.getUriForFile(this, "com.sarath.photoeditingapp.provider", new File(path));
        edit_trail();
    }
    public void edit_trail()
    {
        Intent dsPhotoEditorIntent=new Intent( this, DsPhotoEditorActivity.class);
        dsPhotoEditorIntent.setData(inputImageUri);
// =========== now below Lines are optional to set edited image to your directory
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Editify Photo Directory");
        int[] toolsToHide={DsPhotoEditorActivity.TOOL_ORIENTATION,DsPhotoEditorActivity.TOOL_CROP};
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE,toolsToHide);
        startActivityForResult(dsPhotoEditorIntent, 200);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            switch (requestCode){
                case 200:
                    Uri outputUri=data.getData();
                    imgedit.setImageURI(outputUri);
                    break;

        }
        }
    }
}