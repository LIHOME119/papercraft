package com.example.papercraft;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;

public class customPaperCraft extends AppCompatActivity {

    ImageView CustomPCimageView;
    Button SelectCustomPC_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_paper_craft);

        CustomPCimageView = findViewById(R.id.CustomPCimage);
        SelectCustomPC_btn = findViewById(R.id.btn_SelectCustomPCimage);
        SelectCustomPC_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().start(customPaperCraft.this);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                if (result != null) {
                    Uri uri = result.getUri();
                    CustomPCimageView.setImageURI(uri);


                }

            }
        }


    }
}