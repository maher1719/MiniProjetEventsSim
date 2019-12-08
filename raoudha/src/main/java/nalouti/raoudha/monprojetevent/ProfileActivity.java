package nalouti.raoudha.monprojetevent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private CircleImageView circleImageView;
    private TextView txtName, txtEmail;
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                txtName.setText("");
                txtEmail.setText("");
                circleImageView.setImageResource(0);
                Toast.makeText(ProfileActivity.this, "User Logged out", Toast.LENGTH_LONG).show();
            } else
                loadUserProfile(currentAccessToken);
        }
    };
    private Button LogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.profile_name);
        txtEmail = findViewById(R.id.profile_email);
        circleImageView = findViewById(R.id.profile_pic);
        LogOut = findViewById(R.id.logoutButton);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // logOutButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        if (MainActivity.TypeCnx == 1) {
            checkLoginStatus();


        } else if (MainActivity.TypeCnx == 2) {
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                txtEmail.setText(personEmail);
                txtName.setText(personName + " " + personFamilyName);
                Glide.with(ProfileActivity.this).load(String.valueOf(personPhoto)).into(circleImageView);
            }

        }


        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.TypeCnx == 1) {
                    txtName.setText("");
                    txtEmail.setText("");
                    circleImageView.setImageResource(0);


                    Intent loginActivity = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(loginActivity);

                } else if (MainActivity.TypeCnx == 2) {
                    if (v.getId() == R.id.logoutButton) {
                        signOutGmail();
                        // ...
                    }


                }


            }
        });
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

                    txtEmail.setText(email);
                    txtName.setText(first_name + " " + last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(ProfileActivity.this).load(image_url).into(circleImageView);


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

    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }


    private void signOutGmail() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProfileActivity.this, "Signed out succeful!!!", Toast.LENGTH_LONG).show();
                        Intent loginActivity = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(loginActivity);
                        //ou bien finish() pour fermer cette profile activity
                    }
                });
    }

    public void AjouterEvent(View view) {
        Intent AddEventActivity = new Intent(ProfileActivity.this, AjoutEvent.class);
        startActivity(AddEventActivity);
    }
}
