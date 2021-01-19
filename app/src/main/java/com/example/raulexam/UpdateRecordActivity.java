package com.example.raulexam;

import android.Manifest;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateRecordActivity extends AppCompatActivity {



    private TextInputEditText txtProdName,txtProdUnit,txtProdPrice,txtProdQuantity,txtExpDate;
    private ImageView imgView;
    private boolean editMode=false;
    private Button btnAdd;

    private static final int REQUEST_STORAGE=111;
    private static final int REQUEST_FILE = 222;
    private Uri imageUri;
    private String stringPath;
    private Intent iData;
    String url;


    private String id,prodName,prodUnit,prodQty,prodPrice,prodExpDate;
    private DatabaseHelper dbHelper;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updaterecord);

        txtExpDate = findViewById(R.id.edtxtExpDate);
        txtProdName = findViewById(R.id.edtxtProdName);
        txtProdUnit = findViewById(R.id.edtxtProdUnit);
        txtProdPrice = findViewById(R.id.edtxtProdPrice);
        txtProdQuantity = findViewById(R.id.edtxtProdQty);

        imgView = findViewById(R.id.imgProduct);
        btnAdd = findViewById(R.id.btnAdd);


        Intent intent = getIntent();
        editMode = intent.getBooleanExtra("editMode",editMode);
        id = intent.getStringExtra("ID");
        prodName = intent.getStringExtra("NAME");
        prodUnit = intent.getStringExtra("UNIT");
        prodQty = intent.getStringExtra("PRICE");
        prodPrice = intent.getStringExtra("DATE");
        prodExpDate = intent.getStringExtra("QTY");
        imageUri = Uri.parse(intent.getStringExtra("IMAGE"));

        if(editMode){
            editMode = intent.getBooleanExtra("editMode",editMode);
            id = intent.getStringExtra("ID");
            prodName = intent.getStringExtra("NAME");
            prodUnit = intent.getStringExtra("UNIT");
            prodQty = intent.getStringExtra("PRICE");
            prodPrice = intent.getStringExtra("DATE");
            prodExpDate = intent.getStringExtra("QTY");
            imageUri = Uri.parse(intent.getStringExtra("IMAGE"));

            txtProdName.setText(prodName);
            txtProdUnit.setText(prodUnit);
            txtProdPrice.setText(prodPrice);
            txtExpDate.setText(prodExpDate);
            txtProdQuantity.setText(prodQty);

            if(imageUri.toString().equals("null")){
                imgView.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
            }else{
                imgView.setImageURI(imageUri);
            }

        }



        dbHelper = new DatabaseHelper(this);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(UpdateRecordActivity.this
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
                Toast.makeText(UpdateRecordActivity.this,url,Toast.LENGTH_LONG).show();
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
        if(Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(UpdateRecordActivity.this
                ,"android.permission.WRITE_EXTERNAL_STORAGE") == 0){
            return true;
        }
        return false;
    }
    private void getData(){
        if(editMode){
            dbHelper.updateProd(id,prodName, prodUnit, prodPrice,prodExpDate, prodQty);
        }

    }

}
