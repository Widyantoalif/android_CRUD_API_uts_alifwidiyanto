package com.example.pasiencovid_utsalif;

import android.content.DialogInterface;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pasien");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final androidx.appcompat.app.AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                // Tarik Layout
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.form_input_data, null);


                dialog.setView(view);
                dialog.setCancelable(true);

                // Definisi Objek
                final EditText et_id_pasien = (EditText) view.findViewById(R.id.et_id_pasien);
                final EditText et_nama_pasien = (EditText) view.findViewById(R.id.et_nama_pasien);
                final EditText et_alamat_pasien = (EditText) view.findViewById(R.id.et_alamat_pasien);
                final EditText et_jk_pasien = (EditText) view.findViewById(R.id.et_jenis_kelamin);
                final EditText et_umur_pasien = (EditText) view.findViewById(R.id.et_umur_pasien);
                final EditText et_telp_pasien = (EditText) view.findViewById(R.id.et_telp_pasien);
                final EditText et_status = (EditText) view.findViewById(R.id.et_status_pasien);

                dialog.setPositiveButton("SIMPAN",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String id_pasien, nama_pasien, alamat_pasien,jk_pasien,umur_pasien,telp_pasien,status;

                                id_pasien = et_id_pasien.getText().toString();
                                nama_pasien = et_nama_pasien.getText().toString();
                                alamat_pasien = et_alamat_pasien.getText().toString();
                                jk_pasien = et_jk_pasien.getText().toString();
                                umur_pasien = et_umur_pasien.getText().toString();
                                telp_pasien = et_telp_pasien.getText().toString();
                                status = et_status.getText().toString();

                                // Simpan pasien
                                simpandata(id_pasien, nama_pasien, alamat_pasien, jk_pasien, umur_pasien, telp_pasien,status);
                            }


                        });

                                dialog.setNegativeButton("BATAL",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                dialog.show();
                            }
                        });
            }


            private void simpandata(String id_pasien, String nama_pasien, String alamat_pasien, String jk_pasien, String umur_pasien, String telp_pasien, String status) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://webwidyantoalif.000webhostapp.com/datacovid.php?action=simpan&id_pasien=" + id_pasien + "&nama_pasien=" + nama_pasien + "&alamat_pasien=" + alamat_pasien+ "&jk_pasien=" + jk_pasien+ "&umur_pasien=" + umur_pasien+ "&telp_pasien=" + telp_pasien+ "&status=" + status;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                if (response.optString("result").equals("true")) {
                                    Toast.makeText(getApplicationContext(), "Data Berhasil Ditambah!", Toast.LENGTH_SHORT).show();

                                    // panggil fungsi load pada fragment
                                    loadFragment(new FirstFragment());
                                } else {
                                    Toast.makeText(getApplicationContext(), "0 ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Events: ", error.toString());

                        Toast.makeText(getApplicationContext(), "Masalah internet atau data yang kamu masukan", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            }



    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}