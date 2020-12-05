package com.example.cloneprojectwidia;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.cloneproject_widia_12rpl2_28.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Registrasi extends AppCompatActivity {

    EditText txtemail;
    EditText txtpassword;
    EditText txtnama;
    EditText txtnohp;
    EditText txtalamat;
    EditText txtnoktp;
    Button btnregister;
    Button btnlogin;
    TextView tvlogin;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        txtemail = (EditText)findViewById(R.id.txtemail);
        txtpassword = (EditText)findViewById(R.id.txtpassword);
        txtnama = (EditText)findViewById(R.id.txtnama);
        txtnohp= (EditText)findViewById(R.id.txtnohp);
//        btnlogin = findViewById(R.id.btnlogin);
        txtalamat = (EditText)findViewById(R.id.txtalamat);
        txtnoktp = (EditText)findViewById(R.id.txtnoktp);
        btnregister = (Button) findViewById(R.id.btnregister);


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = txtemail.getText().toString();
                String password = txtpassword.getText().toString();
                String nama = txtnama.getText().toString();
                String nohp = txtnohp.getText().toString();
                String alamat = txtalamat.getText().toString();
                String noktp = txtnoktp.getText().toString();

                if (email.isEmpty()){
                    Toast.makeText(Registrasi.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }
                if (password.isEmpty()){
                    Toast.makeText(Registrasi.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }
                if (nama.isEmpty()){
                    Toast.makeText(Registrasi.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }
                if (nohp.isEmpty()){
                    Toast.makeText(Registrasi.this, "Nohp tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }
                if (alamat.isEmpty()){
                    Toast.makeText(Registrasi.this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }
                if (noktp.isEmpty()){
                    Toast.makeText(Registrasi.this, "Noktp tidak boleh kosong", Toast.LENGTH_SHORT).show();

                }

                AndroidNetworking.post(BaseUrl.url+"register.php")
                        .addBodyParameter("email",email)
                        .addBodyParameter("password",password)
                        .addBodyParameter("nama",nama)
                        .addBodyParameter("nohp",nohp)
                        .addBodyParameter("alamat",alamat)
                        .addBodyParameter("noktp",noktp)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hasil", "onResponse: ");
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean respon = hasil.getBoolean("respon");

                                    Log.d("STATUS", "onResponse: " + hasil);
                                    if (respon) {
//                                        sharedPreferences.edit().putString("logged","customer").apply();
                                        Intent intent = new Intent(Registrasi.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
//                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(Registrasi.this, "gagal", Toast.LENGTH_SHORT).show();
//                                        progressDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                            @Override
                            public void onError(ANError anError) {
                                //                                progressDialog.dismiss();
                                Log.d("TAG", "onError: " + anError.getErrorDetail());
                                Log.d("TAG", "onError: " + anError.getErrorBody());
                                Log.d("TAG", "onError: " + anError.getErrorCode());
                                Log.d("TAG", "onError: " + anError.getResponse());

                                Toast.makeText(Registrasi.this, "Register Gagal", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
//        btnlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Registrasi.this,MainActivity.class);
//                startActivity(i);
//            }
//        });
    }
}