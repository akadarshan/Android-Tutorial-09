package com.darshan.tutorial09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    TextInputLayout tl_notes;
    TextInputEditText tv_notes;
    TextView txtHeading,txtData;
    final String FILE_ASSETS = "data.json";
    final String FILE_INTERNAL = "notes.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tl_notes=findViewById(R.id.tl_notes);
        tv_notes=findViewById(R.id.tv_notes);
        txtHeading=findViewById(R.id.txtHeading);
        txtData=findViewById(R.id.txtData);
    }

    public void ReadAssets(View view) {
        try {
            InputStream inputStream = getAssets().open(FILE_ASSETS);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder= new StringBuilder();
            String mLine;
            while((mLine = bufferedReader.readLine())!=null){
                stringBuilder.append(mLine+"\n");
            }
            txtHeading.setText("Assets JSON Data");
            txtData.setText(stringBuilder.toString());
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteFile(View view) {
        if(tv_notes.getText().toString().equals(""))
        {
            tl_notes.setError("Please Enter any Notes:");
            tv_notes.requestFocus();
        }else {
            tl_notes.setErrorEnabled(false);

            try {
                FileOutputStream fileOutputStream = openFileOutput(FILE_INTERNAL, Context.MODE_PRIVATE);
                String data=tv_notes.getText().toString();
                fileOutputStream.write(data.getBytes());
                fileOutputStream.close();
                Toast.makeText(this, "Successfully Saved Notes", Toast.LENGTH_SHORT).show();
                tv_notes.setText("");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void ReadFile(View view) {
        try {
            FileInputStream fileInputStream = openFileInput(FILE_INTERNAL);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder= new StringBuilder();
            String mLine;
            while((mLine = bufferedReader.readLine())!=null){
                stringBuilder.append(mLine+"\n");
            }
            txtHeading.setText("Your Notes :");
            txtData.setText(stringBuilder);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"There is no file",Toast.LENGTH_SHORT).show();
        }
    }
}