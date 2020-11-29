package com.example.pasiencovid_utsalif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class detailpasien extends AppCompatActivity {
    ProgressBar pb;
    EditText et_id_pasien,et_nama_pasien, et_alamat_pasien,et_jk_pasien,et_umur_pasien,et_telp_pasien,et_status_pasien;
    Button bt_hapus, bt_ubah;
    String id_pasien, nama_pasien, alamat_pasien,jk_pasien,telp_pasien,umur_pasien,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpasien);

        pb = (ProgressBar) findViewById(R.id.pb);
        et_id_pasien =(EditText) findViewById(R.id.et_id_pasien);
        et_nama_pasien =(EditText) findViewById(R.id.et_nama_pasien);
        et_alamat_pasien =(EditText) findViewById(R.id.et_alamat_pasien);
        et_jk_pasien =(EditText) findViewById(R.id.et_jk_pasien);
        et_umur_pasien =(EditText) findViewById(R.id.et_umur_pasien);
        et_telp_pasien =(EditText) findViewById(R.id.et_telp_pasien);
        et_status_pasien =(EditText) findViewById(R.id.et_status_pasien);
        bt_ubah = (Button) findViewById(R.id.bt_ubah);
        bt_hapus = (Button) findViewById(R.id.bt_hapus);

        //tombol back
        getSupportActionBar().setTitle("Detail Data"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action back

        //tangkap bundle
        Bundle bundle = null;
        bundle = this.getIntent().getExtras();

        //letakkan isi bundle
        //letakkan isi bundle
        id_pasien = bundle.getString("b_id_pasien");
        nama_pasien = bundle.getString("b_nama_pasien");
        alamat_pasien = bundle.getString("b_alamat_pasien");
        jk_pasien = bundle.getString("b_jk_pasien");
        umur_pasien = bundle.getString("b_umur_pasien");
        telp_pasien = bundle.getString("b_telp_pasien");
        status = bundle.getString("b_status");

        //pada textview
        et_id_pasien.setText(id_pasien);
        et_nama_pasien.setText(nama_pasien);
        et_alamat_pasien.setText(alamat_pasien);
        et_jk_pasien.setText(jk_pasien);
        et_umur_pasien.setText(umur_pasien);
        et_telp_pasien.setText(telp_pasien);
        et_status_pasien.setText(status);

        //operasi ubah data

        bt_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_pasien = et_id_pasien.getText().toString();
                nama_pasien = et_nama_pasien.getText().toString();
                alamat_pasien = et_alamat_pasien.getText().toString();
                jk_pasien = et_jk_pasien.getText().toString();
                umur_pasien = et_umur_pasien.getText().toString();
                telp_pasien = et_telp_pasien.getText().toString();
                status = et_status_pasien.getText().toString();

                pb.setVisibility(ProgressBar.VISIBLE); //munculkan progressbar

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://webwidyantoalif.000webhostapp.com/datacovid.php?action=ubah&id_pasien="+id_pasien+"&nama_pasien="+nama_pasien+"&alamat_pasien="+alamat_pasien+"&jk_pasien="+jk_pasien+"&umur_pasien="+umur_pasien+"&telp_pasien="+telp_pasien+"&status="+status;

                Log.d("Hendro ", "onClick: " + url);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                if (response.optString("result").equals("true")){
                                    Toast.makeText(getApplicationContext(), "Data"+ id_pasien+"terubah!", Toast.LENGTH_SHORT).show();

                                    finish(); //tutup activity
                                }else{
                                    Toast.makeText(getApplicationContext(), "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Events: ", error.toString());

                        Toast.makeText(getApplicationContext(), "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();

                        pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                    }
                });

                queue.add(jsObjRequest);
            }
        });

        bt_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(ProgressBar.VISIBLE); //tampilkan progress bar

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://webwidyantoalif.000webhostapp.com/datacovid.php?action=hapus&id_pasien="+ id_pasien;

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                if (response.optString("result").equals("true")){
                                    Toast.makeText(getApplicationContext(), "Data terhapus!", Toast.LENGTH_SHORT).show();

                                    finish(); //tutup activity
                                }else{
                                    Toast.makeText(getApplicationContext(), "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Events: ", error.toString());

                        pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar

                        Toast.makeText(getApplicationContext(), "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsObjRequest);
            }
        });
    }

}






