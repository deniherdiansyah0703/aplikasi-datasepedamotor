package com.mobile.datasepedamotor.Activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class TambahDataActivity extends AppCompatActivity {
    RadioButton radioButtonTerpilih;
    private String nama, alamat, kategori, merk, deskripsi, harga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
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
        Button btnSimpan = findViewById(R.id.btnsimpan);

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

        spMerk.setAdapter(new ArrayAdapter<>(TambahDataActivity.this, android.R.layout.simple_spinner_dropdown_item,fList));
        spMerk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                alamat = etAlamat.getText().toString();
                harga = etHarga.getText().toString();
                deskripsi = etDeskripsi.getText().toString();

                int idTerpilih = rgKategori.getCheckedRadioButtonId();
                radioButtonTerpilih = (RadioButton) findViewById(idTerpilih);
                kategori = radioButtonTerpilih.getText().toString();

                merk = String.valueOf(spMerk.getSelectedItem());

                createData();
            }
        });
    }

    private void createData() {

        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardCreateData(nama, alamat,kategori, merk, deskripsi, harga);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahDataActivity.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

                Toast.makeText(TambahDataActivity.this, "Gagal Menyimpan Data"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}