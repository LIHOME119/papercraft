package com.example.papercraft;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

public class customPaperCraft extends AppCompatActivity {

    ImageView CustomPCimageView;
    Button SelectCustomPC_btn;
    TextView CustomPCimagelabelingResult;
    InputImage inputImage;
    ImageLabeler labeler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_paper_craft);

        CustomPCimageView = findViewById(R.id.CustomPCimage);
        SelectCustomPC_btn = findViewById(R.id.btn_SelectCustomPCimage);
        CustomPCimagelabelingResult = findViewById(R.id.tv_CustomPCimageLabelingResult);
        labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
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
                    try {
                        inputImage = InputImage.fromFilePath(customPaperCraft.this, uri);
                        processImage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        }


    }

    private void processImage() {
        labeler.process(inputImage).addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
            @Override
            public void onSuccess(@NonNull List<ImageLabel> imageLabels) {
                String result = "";
                for (ImageLabel label : imageLabels){
                    result=result+"\n"+label.getText();
                }
                CustomPCimagelabelingResult.setText(result);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e.getMessage());

            }
        });
    }
}