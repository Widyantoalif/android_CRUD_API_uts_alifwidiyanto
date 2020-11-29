package com.example.pasiencovid_utsalif;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class pasienadapter extends RecyclerView.Adapter<pasienadapter.GridViewHolder> {

    private List<pasien> pasiens;
    private Context context;

    public pasienadapter(Context context, List<pasien> produks) {
        this.pasiens = produks;
        this.context = context;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pasien_item_layout, parent, false);
        GridViewHolder viewHolder = new GridViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        final int pos = position;
        final String id_pasien = pasiens.get(position).getIdPasien();
        final String nama_pasien = pasiens.get(position).getNamaPasien();
        final String alamat_pasien = pasiens.get(position).getAlamatPasien();
        final String jk_pasien = pasiens.get(position).getJkPasien();
        final String umur_pasien = pasiens.get(position).getUmurPasien();
        final String telp_pasien = pasiens.get(position).getTelpPasien();
        final String status = pasiens.get(position).getStatus();


        holder.tvIdPasien.setText(id_pasien);
        holder.tvNamaPasien.setText(nama_pasien);
        holder.tvAlamatPasien.setText(alamat_pasien);
        holder.tvJkPasien.setText(jk_pasien);
        holder.tvUmurPasien.setText(umur_pasien);
        holder.tvTelpPasien.setText(telp_pasien);
        holder.tvStatus.setText(status);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Operasi data");
                alertDialog.setMessage(id_pasien + " - " + nama_pasien);
                alertDialog.setPositiveButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.setNegativeButton("LIHAT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Bundle b = new Bundle();
                        b.putString("b_id_pasien", id_pasien);
                        b.putString("b_nama_pasien", nama_pasien);
                        b.putString("b_alamat_pasien", alamat_pasien);
                        b.putString("b_jk_pasien", jk_pasien);
                        b.putString("b_umur_pasien", umur_pasien);
                        b.putString("b_telp_pasien", telp_pasien);
                        b.putString("b_status", status);

                        Intent intent = new Intent(context,detailpasien.class);
                        intent.putExtras(b);

                        //context.startActivity(intent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ((Activity) context).startActivityForResult(intent, 1, b);
                        }
                    }
                });

                alertDialog.setNeutralButton("HAPUS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = "https://webwidyantoalif.000webhostapp.com/datacovid.php?action=hapus&id_pasien=" + id_pasien;
                        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                url,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        String id, nama, deskripsi;

                                        if (response.optString("result").equals("true")) {
                                            Toast.makeText(context, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();

                                            pasiens.remove(pos); //hapus baris customers
                                            notifyItemRemoved(pos); //refresh customer list ( ada animasinya )
                                            notifyDataSetChanged();

                                        } else {
                                            Toast.makeText(context, "Gagal hapus data", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.d("Events: ", error.toString());

                                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show();
                            }
                        });

                        queue.add(jsObjRequest);
                    }
                });


                AlertDialog dialog = alertDialog.create();
                dialog.show();

                //untuk ubah warna tombol dialog
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.teal_200));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.yellow));
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.red));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pasiens.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdPasien, tvNamaPasien, tvAlamatPasien, tvJkPasien,tvUmurPasien, tvTelpPasien, tvStatus;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdPasien = (TextView) itemView.findViewById(R.id.tv_id_pasien);
            tvNamaPasien = (TextView) itemView.findViewById(R.id.tv_nama_pasien);
            tvAlamatPasien = (TextView) itemView.findViewById(R.id.tv_alamat_pasien);
            tvJkPasien = (TextView) itemView.findViewById(R.id.tv_jk_pasien);
            tvUmurPasien = (TextView) itemView.findViewById(R.id.tv_umur_pasien);
            tvTelpPasien = (TextView) itemView.findViewById(R.id.tv_telp_pasien);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status_pasien);
        }
    }
}
