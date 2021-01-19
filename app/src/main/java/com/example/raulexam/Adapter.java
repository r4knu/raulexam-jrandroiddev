package com.example.raulexam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.sql.SQLOutput;
import java.util.*;
public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context context;
    private ArrayList<Model> arrayList;
    public Adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Model model = arrayList.get(position);
        String id = model.getId();
        String image = model.getImage();
        System.out.println("IMAAAAAAAAAAAAAAAAAAAAAGE::::: "+image);
        String prodName = model.getProdName();
        String prodUnit = model.getProdUnit();
        String prodPrice = model.getProdPrice();
        String prodDate = model.getProdDate();
        String prodQty = model.getProdQty();


        holder.prodImg.setImageURI(Uri.parse(image));
        holder.prodName.setText(prodName);
        holder.prodUnit.setText(prodUnit);
        holder.prodPrice.setText(prodPrice);
        holder.prodDate.setText(prodDate);
        holder.prodQty.setText(prodQty);

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKEDEDEDASDASDSADSA");
                editDialog(
                        ""+id,
                        ""+image,
                        ""+prodName,
                        ""+prodUnit,
                        ""+prodPrice,
                        ""+prodDate,
                        ""+prodQty
                );
            }
        });

    }

    private void editDialog(String id, String prodImage,String prodName, String prodUnit, String prodPrice, String prodDate, String prodQty) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Update Product");
        builder.setMessage("Are you sure want to update?");
        builder.setCancelable(false);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context,UpdateRecordActivity.class);
                intent.putExtra("ID",id);
                intent.putExtra("IMAGE",prodImage);
                intent.putExtra("NAME",prodName);
                intent.putExtra("UNIT",prodUnit);
                intent.putExtra("PRICE",prodPrice);
                intent.putExtra("DATE",prodDate);
                intent.putExtra("QTY",prodQty);
                intent.putExtra("editMode",false);
                context.startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView prodImg;
        ImageButton editBtn;
        TextView prodName,prodUnit,prodPrice,prodDate,prodQty;
         public Holder(@NonNull View itemView) {
            super(itemView);
            editBtn = itemView.findViewById(R.id.btnEdit);
            prodImg = itemView.findViewById(R.id.viewProdImage);
            prodName = itemView.findViewById(R.id.viewProdName);
            prodUnit = itemView.findViewById(R.id.viewProdUnit);
            prodPrice = itemView.findViewById(R.id.viewProdPrice);
            prodDate = itemView.findViewById(R.id.viewProdDate);
            prodQty = itemView.findViewById(R.id.viewProdQty);
        }
    }

}
