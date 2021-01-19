package com.example.raulexam;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddRecordActivity extends AppCompatActivity {



    private TextInputEditText txtProdName,txtProdUnit,txtProdPrice,txtProdQuantity,txtExpDate;
    private ImageView imgView;
    private Button btnAdd;

    private static final int REQUEST_STORAGE=111;
    private static final int REQUEST_FILE = 222;
    private Uri imageUri;
    private String stringPath;
    private Intent iData;
    String url;

    private String prodName,prodUnit,prodQty,prodPrice,prodExpDate;
    private DatabaseHelper dbHelper;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrecord);


        txtExpDate = findViewById(R.id.edtxtExpDate);
        txtProdName = findViewById(R.id.edtxtProdName);
        txtProdUnit = findViewById(R.id.edtxtProdUnit);
        txtProdPrice = findViewById(R.id.edtxtProdPrice);
        txtProdQuantity = findViewById(R.id.edtxtProdQty);


        imgView = findViewById(R.id.imgProduct);
        btnAdd = findViewById(R.id.btnAdd);


        dbHelper=new DatabaseHelper(this);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddRecordActivity.this
                    ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_STORAGE);
                }else{
                    selectImage();
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

    }
    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_FILE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_FILE  && resultCode == RESULT_OK){
            if(data != null){
                imageUri = data.getData();
                iData = data;
                url = getStringPath(imageUri);
                Toast.makeText(AddRecordActivity.this,url,Toast.LENGTH_LONG).show();
                    try{
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imgView.setImageBitmap(bitmap);
                    }catch (FileNotFoundException ex){
                        ex.printStackTrace();
                    }

            }
        }
    }

    private String getStringPath(Uri myUri){
        String[] filePathCol = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(myUri,filePathCol,null,null,null);
        if(cursor == null){
            stringPath = myUri.getPath();
        }else{
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathCol[0]);
            stringPath = cursor.getString(columnIndex);
            cursor.close();
        }
        return stringPath;
    }
    public boolean checkReadStorageAllowed(){
        if(Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(AddRecordActivity.this
                ,"android.permission.WRITE_EXTERNAL_STORAGE") == 0){
            return true;
        }
        return false;
    }
    private void getData(){

        prodName = ""+txtProdName.getText().toString().trim();
        prodUnit = ""+txtProdUnit.getText().toString().trim();
        prodPrice = ""+txtProdPrice.getText().toString().trim();
        prodExpDate = ""+txtExpDate.getText().toString().trim();
        prodQty =  ""+txtProdQuantity.getText().toString().trim();
        boolean add = dbHelper.insertProd(url,prodName, prodUnit, prodPrice,prodExpDate, prodQty);
        if(add){
            imgView.setImageBitmap(null);
            txtProdName.setText("");
            txtProdUnit.setText("");
            txtProdPrice.setText("");
            txtExpDate.setText("");
            txtProdQuantity.setText("");
            startActivity(new Intent(AddRecordActivity.this,ViewRecordActivity.class));
        }else{
            Toast.makeText(this,"FAILED Added!!! ",Toast.LENGTH_LONG).show();
        }

    }

}
