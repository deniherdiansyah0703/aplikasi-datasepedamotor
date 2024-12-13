package com.mobile.datasepedamotor.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.datasepedamotor.API.APIRequestData;
import com.mobile.datasepedamotor.API.RetroServer;
import com.mobile.datasepedamotor.Activity.UasActivity;
import com.mobile.datasepedamotor.Activity.UbahActivity;
import com.mobile.datasepedamotor.Model.DataModel;
import com.mobile.datasepedamotor.Model.ResponseModel;
import com.mobile.datasepedamotor.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private Context ctx;
    private List<DataModel> listData;
    private List<DataModel> listLaundry;
    private int idLaundry;

    public AdapterData(Context ctx, List<DataModel> listData) {
        this.ctx = ctx;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {

        DataModel dm = listData.get(position);

        holder.tvId.setText((String.valueOf(dm.getId())));
        holder.tvNama.setText("Nama : " +dm.getNama());
        holder.tvAlamat.setText("Alamat : " +dm.getAlamat());
        holder.tvKategori.setText("Kategori Sepeda Motor " +dm.getKategori());
        holder.tvMerk.setText("Merek Sepeda : "+dm.getMerk());
        holder.tvDeskripsi.setText("Deskripsi : " + dm.getDeskripsi());
        holder.tvHarga.setText("Harga : " + dm.getHarga());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {


        TextView tvId, tvNama, tvAlamat, tvKategori, tvMerk, tvDeskripsi, tvHarga;
        Button btnEdit, btnDelete;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvKategori = itemView.findViewById(R.id.tv_kategori);
            tvMerk = itemView.findViewById(R.id.tv_merk);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            tvHarga = itemView.findViewById(R.id.tv_harga);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);


            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    idLaundry = Integer.parseInt(tvId.getText().toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setTitle("Update Confirmation");
                    builder.setMessage("Are you sure you want to update this item?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getData();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked No, dismiss the dialog
                            dialog.dismiss();
                        }
                    });
                    builder.show();

                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    idLaundry = Integer.parseInt(tvId.getText().toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setTitle("Delete Confirmation");
                    builder.setMessage("Are you sure you want to delete this item?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteItem();
                            Handler hand = new Handler();
                            hand.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((UasActivity) ctx).retrieveData();
                                }
                            }, 500);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked No, dismiss the dialog
                            dialog.dismiss();
                        }
                    });
                    builder.show();


                }
            });

        }

        private void getData() {
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> ambilData = ardData.ardGetData(idLaundry);

            ambilData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listLaundry = response.body().getData();

                    int varId = listLaundry.get(0).getId();
                    String varNama = listLaundry.get(0).getNama();
                    String varAlamat = listLaundry.get(0).getAlamat();
                    String varKategori = listLaundry.get(0).getKategori();
                    String varKategori2 = listLaundry.get(0).getKategori();
                    String varKategori3 = listLaundry.get(0).getKategori();
                    String varKategori4 = listLaundry.get(0).getKategori();
                    String varMerk = listLaundry.get(0).getMerk();
                    String varDeskripsi = listLaundry.get(0).getDeskripsi();
                    String varHarga = listLaundry.get(0).getHarga();



                    //Toast.makeText(ctx, "Kode :"+kode+" | Pesan : "+pesan+ "Data : "+varIdLaundry+" " +
                    //      "| "+varNamaLaundry +
                    // " | "+varBeratLaundry+
                    // " | "+varRateLaundry+
                    //" | "+varAlamatLaundry, Toast.LENGTH_SHORT).show();


                    // TextView tvId, tvNama, tvAlamat, tvKategori, tvMerk, tvDeskripsi, tvHarga;
                    Intent kirim = new Intent(ctx, UbahActivity.class);
                    kirim.putExtra("xId", varId);
                    kirim.putExtra("xNama", varNama);
                    kirim.putExtra("xAlamat", varAlamat);
                    kirim.putExtra("xKategori", varKategori);
                    kirim.putExtra("xKategori", varKategori2);
                    kirim.putExtra("xKategori", varKategori3);
                    kirim.putExtra("xKategori", varKategori4);
                    kirim.putExtra("xMerk", varMerk);
                    kirim.putExtra("xDeskripsi", varDeskripsi);
                    kirim.putExtra("xHarga", varHarga);

                    ctx.startActivity(kirim);

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Update Data" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }

        private void deleteItem() {
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.ardDeleteData(idLaundry);
            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode :" + kode + " | Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghapus Data" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}