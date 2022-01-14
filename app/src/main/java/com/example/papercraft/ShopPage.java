package com.example.papercraft;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ShopPage extends AppCompatActivity {

    ImageButton god1_ibtn, god1_ibtnA, god2_ibtn, god2_ibtnA,god3_ibtn, god3_ibtnA;
    TextView god1_edt,god2_edt,god3_edt, total_tv;
    public static int defaltnum1, defaltnum2, defaltnum3, totalnum;
    ExtendedFloatingActionButton shopFire_fbtn, shopDone_fbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop_page);
        god1_ibtn=findViewById(R.id.imgbtn_god1);
        god1_ibtnA=findViewById(R.id.imgbtn_god1A);
        god2_ibtn=findViewById(R.id.imgbtn_god2);
        god2_ibtnA=findViewById(R.id.imgbtn_god2A);
        god3_ibtn=findViewById(R.id.imgbtn_god3);
        god3_ibtnA=findViewById(R.id.imgbtn_god3A);
        god1_edt=findViewById(R.id.edt_god1);
        god2_edt=findViewById(R.id.edt_god2);
        god3_edt=findViewById(R.id.edt_god3);
        total_tv=findViewById(R.id.tv_shopTotal);
        shopDone_fbtn=findViewById(R.id.fbtn_shopDone);
        shopFire_fbtn=findViewById(R.id.fbtn_shopfire);
        shopFire_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ShopPage.this);
                builder.setMessage("Please confirm the shop cart:  \n\n(Assistant customer to do sacrificing)\n"+
                        "Paper Craft Package 1 x"+defaltnum1+"\n"+
                        "Paper Craft Package 2 x"+defaltnum2+"\n"+
                        "Paper Craft Package 3 x"+defaltnum3+"\n"+
                        "\n Total $"+ totalnum)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                finish();
                            }
                        }).setNegativeButton("No", null);

                AlertDialog aleartDialog=builder.create();
                aleartDialog.show();
            }
        });
        shopDone_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateTotal();

                AlertDialog.Builder builder=new AlertDialog.Builder(ShopPage.this);
                builder.setMessage("Please confirm the shop cart:  \n\n(Deliver service)\n"+
                        "Paper Craft Package 1 x"+defaltnum1+"\n"+
                        "Paper Craft Package 2 x"+defaltnum2+"\n"+
                        "Paper Craft Package 3 x"+defaltnum3+"\n"+
                        "\n Total $"+ totalnum)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {



                                finish();
                            }
                        }).setNegativeButton("No", null);

                AlertDialog aleartDialog=builder.create();
                aleartDialog.show();
            }
        });
        intstartCounter();


        god1_ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MinusCounter1();
            }
        });
        god1_ibtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCounter1();
            }
        });
        god2_ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MinusCounter2();
            }
        });
        god2_ibtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCounter2();
            }
        });
        god3_ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MinusCounter3();
            }
        });
        god3_ibtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCounter3();
            }
        });




    }

    private void intstartCounter() {
        defaltnum1=0;
        defaltnum2=0;
        defaltnum3=0;
        totalnum=0;
        total_tv.setText("$"+totalnum);
        god1_edt.setText(defaltnum1+"");
        god2_edt.setText(defaltnum2+"");
        god3_edt.setText(defaltnum3+"");

    }
    private void AddCounter1(){
        defaltnum1++;
        god1_edt.setText(defaltnum1+"");
        UpdateTotal();

    }
    private void MinusCounter1(){
        defaltnum1--;
        god1_edt.setText(defaltnum1+"");
        UpdateTotal();

    }
    private void AddCounter2(){
        defaltnum2++;
        god2_edt.setText(defaltnum2+"");
        UpdateTotal();

    }
    private void MinusCounter2(){
        defaltnum2--;
        god2_edt.setText(defaltnum2+"");
        UpdateTotal();

    }
    private void AddCounter3(){
        defaltnum3++;
        god3_edt.setText(defaltnum3+"");
        UpdateTotal();

    }
    private void MinusCounter3(){
        defaltnum3--;
        god3_edt.setText(defaltnum3+"");
        UpdateTotal();
    }
    private void UpdateTotal(){
        totalnum = defaltnum1*125 + defaltnum2*398 + defaltnum3*888;
        total_tv.setText("$"+totalnum);
    }
}