package nalouti.raoudha.monprojetevent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    public static int TypeCnx;
    public static String userMail;
    TextView txt_register;
    EditText email, password;
    Button btnlogin;
    String mname, memail, mpassword, mmonimg;

    ApiInterface mService;

    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    private LoginButton loginButton;
    private SignInButton signiInGmail;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = Common.getAPI();

        txt_register = findViewById(R.id.txt_register);

        email = findViewById(R.id.login);

        password = findViewById(R.id.passwd);

        btnlogin = findViewById(R.id.loginB);

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));

            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authentification(email.getText().toString(), password.getText().toString());


            }
        });

        signiInGmail = findViewById(R.id.sign_in_button_gmail);


        loginButton = findViewById(R.id.login_button);


        callbackManager = CallbackManager.Factory.create();


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                TypeCnx = 1;
                Log.d("sucess", " success login hiya hoh ");

                ///insertion dans la base de données
                checkLoginStatus();


                Intent profileActivity = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profileActivity);


            }

            @Override
            public void onCancel() {
                Log.d("cancled", " cancled login  ");


            }

            @Override
            public void onError(FacebookException error) {
                Log.d("error", " error login  ");


            }
        });

        ///pour gmail

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signiInGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch (v.getId()) {
                    case R.id.sign_in_button_gmail:
                        TypeCnx = 2;
                        signIn();
                        break;
                    // ...
                }

            }
        });


    }

    private void Authentification(String email, String password) {
        mService.loginUser(email, password)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse result = response.body();
                        if (result.getSuccess() == 0) {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // mname = response.body().getFullName();
                            memail = response.body().getMessage();
                            // mpassword = response.body().getPassword();
                            // mmonimg = response.body().getUrlimg();
                            userMail = memail;

                            Toast.makeText(MainActivity.this, memail + "  is connected !!", Toast.LENGTH_LONG).show();
                            //ici si vous voulez diriger la page vers une autre page
                            Intent ProfileActivity = new Intent(MainActivity.this, ProfileActivity.class);
                            startActivity(ProfileActivity);

                        }

                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });


    }

    private void signIn() {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            userMail = personEmail;

            saveData(personName, personEmail, personId, personPhoto.toString());

        }


        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


//pour gmail
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            //pour fb
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

        }
    }

    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                    userMail = email;

                    saveData(first_name + " " + last_name, email, id, image_url);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void saveData(String fullname, String email, String password, String urlimg) {
        mService.addUserData(fullname, email, password, urlimg)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse result = response.body();
                        if (result.getSuccess() == 1)
                            Toast.makeText(MainActivity.this, " good insert", Toast.LENGTH_LONG).show();
                        else if (result.getSuccess() == -1)
                            Toast.makeText(MainActivity.this, " user deja existe", Toast.LENGTH_LONG).show();
                        else if (result.getSuccess() == 0)
                            Toast.makeText(MainActivity.this, " bad insert", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();


                    }
                });


    }


}
