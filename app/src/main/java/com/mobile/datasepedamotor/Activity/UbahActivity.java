package com.mobile.datasepedamotor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobile.datasepedamotor.API.APIRequestData;
import com.mobile.datasepedamotor.API.RetroServer;
import com.mobile.datasepedamotor.Model.ResponseModel;
import com.mobile.datasepedamotor.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    RadioButton radioButtonTerpilih;
    private int xId;
    private String xNama, xAlamat, xKategori, xMerk, xDeskripsi, xHarga;
    private String yNama, yAlamat, yKategori, yMerk, yDeskripsi, yHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);
        EditText etNama = findViewById(R.id.et_nama);
        EditText etAlamat = findViewById(R.id.et_alamat);
        EditText etDeskripsi = findViewById(R.id.et_deskripsi);
        EditText etHarga = findViewById(R.id.et_harga);
        RadioGroup rgKategori = findViewById(R.id.rg_kategori);
        RadioButton rb1 = findViewById(R.id.rb_matic);
        RadioButton rb2 = findViewById(R.id.rb_Sport);
        RadioButton rb3 = findViewById(R.id.rb_touring);
        RadioButton rb4 = findViewById(R.id.rb_trail);
        Spinner spMerk = findViewById(R.id.sp_merk);
        Button btnUbah = findViewById(R.id.btnubah);

        ArrayList<String> fList=new ArrayList<>();

        fList.add("Honda");
        fList.add("Yamaha");
        fList.add("Suzuki");
        fList.add("Vespa");
        fList.add("Kawasaki");
        fList.add("Ducati");
        fList.add("Harley");
        fList.add("BMW");
        fList.add("Triumph");

        spMerk.setAdapter(new ArrayAdapter<>(UbahActivity.this, android.R.layout.simple_spinner_dropdown_item,fList));
        spMerk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Intent terima = getIntent();
        xId = terima.getIntExtra("xId", -1);
        xNama = terima.getStringExtra("xNama");
        xAlamat = terima.getStringExtra("xAlamat");
        xMerk = terima.getStringExtra("xMerk");
        xKategori = terima.getStringExtra("xKategori");
        xHarga = terima.getStringExtra("xHarga");
        xDeskripsi = terima.getStringExtra("xDeskripsi");

        etNama.setText(xNama);
        etAlamat.setText(xAlamat);
        etHarga.setText(xHarga);
        etDeskripsi.setText(xDeskripsi);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yNama = etNama.getText().toString();
                yAlamat = etAlamat.getText().toString();
                yHarga = etHarga.getText().toString();
                yDeskripsi = etDeskripsi.getText().toString();

                int idTerpilih = rgKategori.getCheckedRadioButtonId();
                radioButtonTerpilih = (RadioButton) findViewById(idTerpilih);
                yKategori = radioButtonTerpilih.getText().toString();

                yMerk = String.valueOf(spMerk.getSelectedItem());
                updateData();
            }
        });
    }

    private void updateData() {
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> updateData = ardData.ardUpdateData(xId, yNama, yAlamat, yKategori, yMerk, yDeskripsi, yHarga);

        updateData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

                Toast.makeText(UbahActivity.this, "Gagal Menyimpan Data"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}