package com.example.img;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImagesShow extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<ModelImage> imageList;
    ModelImage modelImage;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_show);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageList = new ArrayList<>();
        myAdapter = new MyAdapter(this,imageList,ImagesShow.this);
        recyclerView.setAdapter(myAdapter);
        fetchImages();
    }
    public void fetchImages(){
        StringRequest request = new StringRequest(Request.Method.POST, "https://demovisitor.000webhostapp.com/fetchImages.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(success.equals("1")){
                                for (int i=0;i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String imageurl = object.getString("image");
                                    String url = "https://demovisitor.000webhostapp.com/Images/"+imageurl;
                                    modelImage = new ModelImage(id,url);
                                    imageList.add(modelImage);
                                    myAdapter.notifyDataSetChanged();

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ImagesShow.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}