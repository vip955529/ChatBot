package com.example.chatbot;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chatbot.databinding.FragmentProfileBinding;
import com.example.chatbot.utills.Constants;
import com.example.chatbot.utills.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class ProfileFragment extends Fragment {

  FragmentProfileBinding binding;
  FirebaseAuth mAuth;
    PreferenceManager preferenceManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);
       View view = binding.getRoot();
       mAuth = FirebaseAuth.getInstance();
         preferenceManager = new PreferenceManager(Constants.USER,getContext());
         binding.phoneNo.setEnabled(false);

       // Checkout.preload(getContext());

      setProdile();

        binding.updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getEditText().getText().toString();
                String name = binding.name.getEditText().getText().toString();

                preferenceManager.putString(Constants.EMAIL,email);
                preferenceManager.putString(Constants.FULLNAME,name);
                Toast.makeText(getContext(), "Profile Updated successfully", Toast.LENGTH_SHORT).show();
                setProdile();

            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceManager.clearPreferences();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(),SignIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
               getActivity().finish();

            }
        });

        binding.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout = new Checkout();

                checkout.setKeyID("rzp_test_Xqs8uX9dwr6HqI");

                checkout.setImage(R.drawable.logo);

                try {
                    JSONObject options = new JSONObject();

                    options.put("name", "Merchant Name");
                    options.put("description", "Reference No. #123456");
                    options.put("theme.color", "#3399cc");
                    options.put("currency", "INR");
                    options.put("amount", "50000");//pass amount in currency subunits
//                    options.put("prefill.email", "gaurav.kumar@example.com");
//                    options.put("prefill.contact","9988776655");
//                    JSONObject retryObj = new JSONObject();
//                    retryObj.put("enabled", true);
//                    retryObj.put("max_count", 4);
//                    options.put("retry", retryObj);

                    checkout.open(getActivity(), options);

                } catch(Exception e) {
                    
                }
            }
        });
       return view;
    }

    private void setProdile() {

        binding.phoneNo.getEditText().setText(preferenceManager.getString(Constants.PHONE));
        binding.email.getEditText().setText(preferenceManager.getString(Constants.EMAIL));
        binding.name.getEditText().setText(preferenceManager.getString(Constants.FULLNAME));


    }


}