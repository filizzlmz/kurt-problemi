package com.filizzlmz.kurtproblemi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.filizzlmz.kurtproblemi.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setEdittext();

        if(binding.etArraySize.getText().toString() != "" && binding.etEnterArray.getText().toString() != ""){
            onClickButton();
        }


    }

    private void onClickButton(){
        binding.btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maxLength = Integer.parseInt(binding.etArraySize.getText().toString());
                String str = binding.etEnterArray.getText().toString().replaceAll("\\.","");
                int length = str.length();
                if(length == maxLength){
                    String result = String.valueOf(calculate(binding.etEnterArray.getText().toString()));

                    result = getResources().getString(R.string.tv_result, result);

                    binding.tvResult.setText(result);
                }
                else{

                    Toast.makeText(getApplicationContext(),"Kurt türlerini eksik ya da hatalı girdiniz!",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void setEdittext(){
        binding.etArraySize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etEnterArray.setText("");

                String str = s.toString();

                if(!str.equals("") && !str.equals(null)){

                    if(str.contains(".")){
                        str = str.replaceAll("\\.","");

                        binding.etArraySize.setText(str);
                        binding.etArraySize.requestFocus();
                        int l = binding.etArraySize.getText().toString().length();
                        binding.etArraySize.setSelection(l);
                    }


                    if(!str.equals("") && !str.equals(null)){

                        binding.etEnterArray.setEnabled(true);

                        int length = Integer.parseInt(str);
                        binding.etEnterArray.setFilters(new InputFilter[] { new InputFilter.LengthFilter(length) });


                    }
                    else{
                        binding.etEnterArray.setEnabled(false);
                    }


                }

            }
        });


    }

    private char calculate(String numbers){

        int count;

        ArrayList<Integer> countList = new ArrayList<>();
        ArrayList<Integer> sequenceList = new ArrayList<>();

        //klavyeden girilen string'i parçalıyorum
        char wolfArray[] = numbers.toCharArray();

        //hangi türden kaçar tane olduğunu buluyorum
        for(int i = 0; i < wolfArray.length; i++){

            count = 1;

            for(int j = i+1; j < wolfArray.length; j++){

                if(wolfArray[i] == wolfArray[j]){
                    count++;
                }
            }

            countList.add(count);
        }

        int maxCount = countList.get(0);
        int sequence = 0;

        //en çok tekrar eden türü ve dizideki yerini buluyorum
        for(int k = 0; k < countList.size(); k++){

            if(maxCount < countList.get(k)){
                maxCount = countList.get(k);
                sequence = k;
            }
        }

        //max tekrar eden türü bulduktan sonra
        //aynı oranda tekrar eden başka sayı var mı kontrol ediyorum
        //ve bunların dizideki yerlerini tutuyorum
        for(int m = 0; m < countList.size(); m++){

            if(maxCount == countList.get(m)){

                sequenceList.add(m);
            }
        }

        char minId = wolfArray[sequenceList.get(0)];

        //aynı oranda tekrar eden sayılar arasındaki id'si en düşük olanı buluyorum
        if(sequenceList.size() > 1){
            for(int i = 0; i < sequenceList.size(); i++){
                if(minId > wolfArray[sequenceList.get(i)]){
                    minId = wolfArray[sequenceList.get(i)];
                }
            }
        }

        return minId;

    }
}