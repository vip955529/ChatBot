package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chatbot.databinding.ActivitySignInBinding;

public class SignIn extends AppCompatActivity {

    private ActivitySignInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.otpSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validatePhone()){
                    return;
                }

                String phoneNumber = binding.inputPhone.getEditText().getText().toString();

                Intent intent = new Intent(SignIn.this,VerifyOTP.class);
                intent.putExtra("phone",phoneNumber);
                startActivity(intent);


            }
        });


    }

    private boolean validatePhone() {
        String val = binding.inputPhone.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            binding.inputPhone.setError("Please enter phone number");
            return false;
        }if (val.length() < 10) {
            binding.inputPhone.setError("Please enter valid number");
            return false;
        } else {
            binding.inputPhone.setError(null);
            binding.inputPhone.setErrorEnabled(false);
            return true;
        }
    }
}