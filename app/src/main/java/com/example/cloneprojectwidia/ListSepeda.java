package com.example.cloneprojectwidia;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.cloneproject_widia_12rpl2_28.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListSepeda extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerView;
    private SepedaAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<ModelMasterBarang> rentalArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        refreshLayout = findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.post(new Runnable() {

            @Override
            public void run() {
                getDataFromRemote();
            }
        });
    }

    private void getDataFromRemote() {
        refreshLayout.setRefreshing(true);
        AndroidNetworking.post(BaseUrl.url+"getsepeda.php")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        // do anything with response
                        refreshLayout.setRefreshing(false);
                        Log.d("hasiljson", "onResponse: " + response.toString());

                        rentalArraylist = new ArrayList<>();
                        try {
                            Log.d("hasiljson", "onResponse: " + response.toString());
                            JSONArray jsonArray = response.getJSONArray("result");
                            Log.d("hasiljson2", "onResponse: " + jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.i("jsonobject", "onResponse: " + jsonObject);
                                ModelMasterBarang item = new ModelMasterBarang();

                                item.setId(jsonObject.optString("id"));
                                item.setKodesepeda(jsonObject.optString("kodesepeda"));
                                item.setMerk(jsonObject.optString("merk"));
                                item.setWarna(jsonObject.optString("warna"));
                                item.setHargasewa(jsonObject.optString("hargasewa"));
                                item.setGambar(jsonObject.optString("gambar"));
                                item.setJenis(jsonObject.optString("jenis"));
                                rentalArraylist.add(item);
                            }


                            adapter = new SepedaAdapter(rentalArraylist);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListSepeda.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        refreshLayout.setRefreshing(false);
                        Log.d("errorku", "onError errorCode : " + error.getErrorCode());
                        Log.d("errorku", "onError errorBody : " + error.getErrorBody());
                        Log.d("errorku", "onError errorDetail : " + error.getErrorDetail());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23 && data.getStringExtra("refresh") != null) {
            //refresh list
            getDataFromRemote();
            Toast.makeText(this, "hihihihi", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRefresh() {
        getDataFromRemote();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);                    }
                }).create().show();
    }
}