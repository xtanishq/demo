package com.example.img;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter <ImageViewHolder>{
    private static final String TAG ="tanishqraja" ;
    private  Context context;
    private List<ModelImage> imageList;
    private Activity activity;


    public MyAdapter(Context context, List<ModelImage> imageList, Activity activity) {
        this.context = context;
        this.imageList = imageList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false);
       return new ImageViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Glide.with(context).load(imageList.get(position).getImageUrl()).into(holder.imageView);
        holder.btndwnld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Log.d(TAG,"save image:downloading selected image");
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                FileOutputStream fileOutputStream =null;
                File file = getDisc();
                if (!file.exists() && !file.mkdirs()){

                    file.mkdirs();
                }

                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyymmsshhmmss");
                String date = simpleDateFormat.format(new Date());
                String name = "img" + date+ ".jpg";
                String file_name = file.getAbsolutePath()+"/"+name;
                File new_file = new File(file_name);
                try{
                    BitmapDrawable draw= (BitmapDrawable) holder.imageView.getDrawable();
                    Bitmap bitmap = draw.getBitmap();
                    fileOutputStream = new FileOutputStream(new_file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    Toast.makeText(context, "save image success", Toast.LENGTH_SHORT).show();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                refreshGallery(new_file);
            }
        });



    }
    private File getDisc(){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        return new File(file,"FATHER/SON");
    }
      private  void  refreshGallery(File file){

          Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
          intent.setData(Uri.fromFile(file));
          context.sendBroadcast(intent);
      }
    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
    class ImageViewHolder extends RecyclerView.ViewHolder{

ImageView imageView,btndwnld;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            btndwnld = (ImageView) itemView.findViewById(R.id.btndwnld);
        }
    }
