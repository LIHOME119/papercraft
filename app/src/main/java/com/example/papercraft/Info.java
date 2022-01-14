package com.example.papercraft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    TextView infousername_tv, infoemail_tv, infoorderinfo_tv;
    EditText infophoneno_tv, infoaddress_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }
}