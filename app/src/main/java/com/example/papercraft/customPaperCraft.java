package com.example.papercraft;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.papercraft.HomePage.dataRef;
import static com.example.papercraft.HomePage.user;

public class customPaperCraft extends AppCompatActivity {

    public String customResult;
    ImageView CustomPCimageView;
    Button SelectCustomPC_btn;
    TextView CustomPCimagelabelingResult;
    ExtendedFloatingActionButton customFire_fbtn, customDone_fbtn;
    InputImage inputImage;
    ImageLabeler labeler;
    DatabaseReference shoporderstatreference= dataRef.child("users").child(user).child("Order").child("order stats");
    DatabaseReference shoptotalinforeference= dataRef.child("users").child(user).child("Order").child("order informaiton");
    DatabaseReference shoptotalreference= dataRef.child("users").child(user).child("Order").child("order total");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_paper_craft);

        customFire_fbtn=findViewById(R.id.fbtn_customfire);
        customFire_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customResult=CustomPCimagelabelingResult.getText().toString();

                AlertDialog.Builder builder=new AlertDialog.Builder(customPaperCraft.this);
                builder.setMessage("Please confirm the shop cart:  \n\n(Assistant customer to do sacrificing)\n"+"\n Custom Paper Craft \n"+ CustomPCimagelabelingResult.getText().toString() + "\n\nAbout $500 \n need to be confirm by paper craft master")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                shoptotalreference.setValue("About $500 \n need to be confirm by paper craft master");
                                shoporderstatreference.setValue("Waiting to sacrifice");
                                shoptotalinforeference.setValue(CustomPCimagelabelingResult.getText().toString());
                                Toast.makeText(getApplicationContext(), "Thank you for using Sacrificing Assistant Service\nPlease check the progress in information page", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        }).setNegativeButton("No", null);

                AlertDialog aleartDialog=builder.create();
                aleartDialog.show();
            }
        });
        customDone_fbtn=findViewById(R.id.fbtn_customDone);
        customDone_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customResult=CustomPCimagelabelingResult.getText().toString();

                AlertDialog.Builder builder=new AlertDialog.Builder(customPaperCraft.this);
                builder.setMessage("Please confirm the shop cart:  \n\n(Deliver service)\n"+"\n Custom Paper Craft \n" + CustomPCimagelabelingResult.getText().toString() + "\n\nAbout $500 \n need to be confirm by paper craft master")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                shoptotalreference.setValue("About $500 \n need to be confirm by paper craft master");
                                shoporderstatreference.setValue("Waiting to deliver");
                                shoptotalinforeference.setValue(CustomPCimagelabelingResult.getText().toString());
                                Toast.makeText(getApplicationContext(), "Thank you for using delivering Service\nPlease check the progress in information page", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        }).setNegativeButton("No", null);

                AlertDialog aleartDialog=builder.create();
                aleartDialog.show();
            }
        });

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