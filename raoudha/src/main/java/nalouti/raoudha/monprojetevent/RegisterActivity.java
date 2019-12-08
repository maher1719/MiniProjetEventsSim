package nalouti.raoudha.monprojetevent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText fullname, email, password;
    Button btnInsert;

    String mname, memail, mpassword, mmonimg;
    TextView txt_login;

    ApiInterface mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        btnInsert = findViewById(R.id.adduser);

        mService = Common.getAPI();

        txt_login = findViewById(R.id.txt_login);

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));

            }
        });


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mname = fullname.getText().toString();
                memail = email.getText().toString();
                mpassword = password.getText().toString();
                mmonimg = "image n'existe pas pour le moument";
                // Toast.makeText(RegisterActivity.this, memail, Toast.LENGTH_LONG).show();


                saveData(mname, memail, mpassword, mmonimg);

            }
        });


    }

    private void saveData(String fullName, String email, String password, String urlimg) {

        mService.addUserData(fullName, email, password, urlimg)

                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse result = response.body();
                        if (result.getSuccess() == 1)

                            Toast.makeText(RegisterActivity.this, " good insert", Toast.LENGTH_LONG).show();

                        else if (result.getSuccess() == -1)

                            Toast.makeText(RegisterActivity.this, " user deja existe", Toast.LENGTH_LONG).show();

                        else if (result.getSuccess() == 0)

                            Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();


                    }
                });


    }
}
