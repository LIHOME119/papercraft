package com.example.papercraft;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends BottomSheetDialogFragment {

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    private BottomSheetListener mListener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sign_up, container, false);
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");


        EditText signupUsername =  v.findViewById(R.id.SignUpUsername);
        EditText signupMail =  v.findViewById(R.id.SignUpemail);
        EditText signupPhoneNo =  v.findViewById(R.id.SignUpPhoneNo);
        EditText signupPassword =  v.findViewById(R.id.SignPassword);
        Button comfirmSignup_btn = v.findViewById(R.id.btn_comfirmsignup);
        comfirmSignup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonClicked("Sign up successful");
                String username = signupUsername.getText().toString();
                String email = signupMail.getText().toString();
                String phoneNo = signupPhoneNo.getText().toString();
                String password = signupPassword.getText().toString();
                DatabaseReference signupRefernece = reference.child(username);
//
                UserSignupClass userSignupClass = new UserSignupClass(username, email, phoneNo, password);
                signupRefernece.setValue(userSignupClass);
                dismiss();
            }
        });
        return v;
    }
    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
            + " must implement BottomSheetListener");
        }

    }
}