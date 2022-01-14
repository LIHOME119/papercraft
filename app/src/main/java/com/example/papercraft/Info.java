package com.example.papercraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static com.example.papercraft.HomePage.dataRef;
import static com.example.papercraft.HomePage.user;

public class Info extends AppCompatActivity {

    public String str_orderstat, str_orderinfo, str_ordertotal;
    TextView infousername_tv, infoemail_tv, infoorderinfo_tv;
    EditText infophoneno_edt, infoaddress_edt;
    ExtendedFloatingActionButton infoEdit_fbtn;
    DatabaseReference infousernamereference= dataRef.child("users").child(user).child("username");
    DatabaseReference infoemailreference= dataRef.child("users").child(user).child("email");
    DatabaseReference infophoneNoreference= dataRef.child("users").child(user).child("phoneNo");
    DatabaseReference infoaddressreference= dataRef.child("users").child(user).child("address");
    DatabaseReference shoporderstatreference= dataRef.child("users").child(user).child("Order").child("order stats");
    DatabaseReference shoptotalinforeference= dataRef.child("users").child(user).child("Order").child("order informaiton");
    DatabaseReference shoptotalreference= dataRef.child("users").child(user).child("Order").child("order total");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        infousername_tv=findViewById(R.id.tv_infousername);
        infoemail_tv=findViewById(R.id.tv_infoemail);
        infoorderinfo_tv=findViewById(R.id.tv_infoOrderInfo);
        infophoneno_edt=findViewById(R.id.edt_infophoneno);
        infoaddress_edt=findViewById(R.id.edt_infoaddress);
        infoEdit_fbtn=findViewById(R.id.fbtn_infoEdit);

        infousernamereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String value=snapshot.getValue(String.class);
                infousername_tv.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        infoemailreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value=snapshot.getValue(String.class);

                infoemail_tv.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        infophoneNoreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value=snapshot.getValue(String.class);

                infophoneno_edt.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        infoaddressreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value=snapshot.getValue(String.class);

                infoaddress_edt.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        shoporderstatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value=snapshot.getValue(String.class);

                str_orderstat=value;
                updateOrder();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        shoptotalinforeference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value=snapshot.getValue(String.class);

                str_orderinfo=value;
                updateOrder();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        shoptotalreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value=snapshot.getValue(String.class);

                str_ordertotal=value;
                updateOrder();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        infoEdit_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoaddressreference.setValue(infoaddress_edt.getText().toString());
                infophoneNoreference.setValue(infophoneno_edt.getText().toString());
                Toast.makeText(getApplicationContext(), "You have edited your information", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void updateOrder() {
        infoorderinfo_tv.setText(str_orderstat +"\n\n"+ str_orderinfo+ "\n\n\n"+ str_ordertotal+"\n\n\n");
    }
}