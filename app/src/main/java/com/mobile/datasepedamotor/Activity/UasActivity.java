package com.mobile.datasepedamotor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobile.datasepedamotor.API.APIRequestData;
import com.mobile.datasepedamotor.API.RetroServer;
import com.mobile.datasepedamotor.Adapter.AdapterData;
import com.mobile.datasepedamotor.Model.DataModel;
import com.mobile.datasepedamotor.Model.ResponseModel;
import com.mobile.datasepedamotor.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UasActivity extends AppCompatActivity {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataModel> listData = new ArrayList<>();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uas);
        rvData = findViewById(R.id.rv_data);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        fab = findViewById(R.id.fab_tambah);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TambahDataActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume(){
        super.onResume();
        retrieveData();
    }

    public void retrieveData() {
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> tampilData = ardData.ardRetrieveData();

        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                //  Toast.makeText(UasActivity.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();

                listData = response.body().getData();

                adData = new AdapterData(UasActivity.this, listData);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

                Toast.makeText(UasActivity.this, "Gagal menghubungi server" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}