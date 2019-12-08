package nalouti.raoudha.monprojetevent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AjoutEvent extends AppCompatActivity {


    EditText titre, description, dateDeb, dateFin, timeDeb, timeFin, dureee, capacite, montant;
    Button addEvent;
    Switch CapNolLimit, MontGratuit;
    int duration;
    String dateFinEvent, TimeFinEvent, TimeDebEvent, dateDebEvent, urlImage, Montant, Capacitee, Description, Titre, organisateur, TypeChoisi, Adressechoisi, categoChoisi;
    ApiInterface mService;
    private Spinner customSpinnerCateg;
    private Spinner customSpinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_event);
        mService = Common.getAPI();


        titre = findViewById(R.id.titre);
        description = findViewById(R.id.description);
        dateDeb = findViewById(R.id.dateDeb);
        dateFin = findViewById(R.id.dateFin);
        timeDeb = findViewById(R.id.timeDeb);
        timeFin = findViewById(R.id.timeFin);
        dureee = findViewById(R.id.duree);
        capacite = findViewById(R.id.capacite);
        montant = findViewById(R.id.montant);
        CapNolLimit = findViewById(R.id.switch1Capacite);
        MontGratuit = findViewById(R.id.switch2);


        CapNolLimit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    capacite.setVisibility(View.INVISIBLE);
                    Capacitee = CapNolLimit.getText().toString();
                    Toast.makeText(getBaseContext(), Capacitee, Toast.LENGTH_LONG).show();


                } else {
                    capacite.setVisibility(View.VISIBLE);

                    Capacitee = capacite.getText().toString();
                    Toast.makeText(getBaseContext(), Capacitee, Toast.LENGTH_LONG).show();

                }
            }
        });
        MontGratuit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    montant.setVisibility(View.INVISIBLE);
                    Montant = MontGratuit.getText().toString();
                    Toast.makeText(getBaseContext(), Montant, Toast.LENGTH_LONG).show();


                } else {
                    montant.setVisibility(View.VISIBLE);

                    Montant = montant.getText().toString();
                    Toast.makeText(getBaseContext(), Montant, Toast.LENGTH_LONG).show();

                }

            }
        });


        Spinner spinner = findViewById(R.id.spinner1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Gouvernerat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Adressechoisi = text;

                Toast.makeText(AjoutEvent.this, text, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        customSpinnerCateg = findViewById(R.id.spinner2Categ);

        ArrayList<CustomItemsCat> customList = new ArrayList<>();
        customList.add(new CustomItemsCat("Musique", R.drawable.ic_music_note_black_24dp));
        customList.add(new CustomItemsCat("Affaires et Professionnel", R.drawable.ic_business_center_black_24dp));
        customList.add(new CustomItemsCat("Nourriture", R.drawable.ic_restaurant_menu_black_24dp));
        customList.add(new CustomItemsCat("Gouvernements & Politique", R.drawable.ic_account_balance_black_24dp));
        customList.add(new CustomItemsCat("Voyage", R.drawable.ic_airplanemode_active_black_24dp));
        customList.add(new CustomItemsCat("Sports & Fitness", R.drawable.ic_fitness_center_black_24dp));
        customList.add(new CustomItemsCat("Mode & Beauté", R.drawable.ic_face_black_24dp));
        customList.add(new CustomItemsCat("Maison & Mode de vie", R.drawable.ic_home_black_24dp));
        customList.add(new CustomItemsCat("Science & Technologie", R.drawable.ic_laptop_mac_black_24dp));
        customList.add(new CustomItemsCat("Santé & Bien-être", R.drawable.ic_local_pharmacy_black_24dp));
        customList.add(new CustomItemsCat("Charité", R.drawable.ic_people_black_24dp));
        customList.add(new CustomItemsCat("Loisir & centres d'interets", R.drawable.ic_palette_black_24dp));
        customList.add(new CustomItemsCat("Films & Médias", R.drawable.ic_perm_media_black_24dp));
        customList.add(new CustomItemsCat("Culture & Communauté", R.drawable.ic_public_black_24dp));
        customList.add(new CustomItemsCat("Famille & Éducation", R.drawable.ic_pregnant_woman_black_24dp));
        customList.add(new CustomItemsCat("Religion & Spiritualité", R.drawable.ic_spa_black_24dp));
        customList.add(new CustomItemsCat("Transports", R.drawable.ic_train_black_24dp));
        customList.add(new CustomItemsCat("Arts visiuels & Performance", R.drawable.ic_camera_black_24dp));
        customList.add(new CustomItemsCat("Autres", R.drawable.ic_priority_high_black_24dp));
        CustomAdapter customAdapter = new CustomAdapter(this, customList);

        if (customSpinnerCateg != null) {
            customSpinnerCateg.setAdapter(customAdapter);
            customSpinnerCateg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CustomItemsCat items = (CustomItemsCat) parent.getSelectedItem();
                    categoChoisi = items.getSpinnerText();
                    Toast.makeText(AjoutEvent.this, categoChoisi, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }

        customSpinnerType = findViewById(R.id.spinner3Type);
        ArrayList<CustomItemsCat> customListt = new ArrayList<>();
        customListt.add(new CustomItemsCat("Concerts", R.drawable.ic_music_note_black_24dp));
        customListt.add(new CustomItemsCat("Conférence", R.drawable.ic_business_center_black_24dp));
        customListt.add(new CustomItemsCat("Compétitions & Tournoi", R.drawable.ic_pool_black_24dp));
        customListt.add(new CustomItemsCat("Exposition", R.drawable.ic_account_balance_black_24dp));
        customListt.add(new CustomItemsCat("Formation & Workshop", R.drawable.ic_school_black_24dp));
        customListt.add(new CustomItemsCat("Foire", R.drawable.ic_perm_media_black_24dp));
        customListt.add(new CustomItemsCat("Festival & Défilé ", R.drawable.ic_wc_black_24dp));
        customListt.add(new CustomItemsCat("Fêtes & Soirées", R.drawable.ic_brightness_3_black_24dp));
        customListt.add(new CustomItemsCat("Projection", R.drawable.ic_laptop_mac_black_24dp));
        customListt.add(new CustomItemsCat("Réunion", R.drawable.ic_people_black_24dp));
        customListt.add(new CustomItemsCat("Rassemblement", R.drawable.ic_record_voice_over_black_24dp));
        customListt.add(new CustomItemsCat("Séminaire", R.drawable.ic_public_black_24dp));
        customListt.add(new CustomItemsCat("Voyage,camp,tour", R.drawable.ic_airplanemode_active_black_24dp));
        customListt.add(new CustomItemsCat("Autres", R.drawable.ic_priority_high_black_24dp));
        CustomAdapter customAdapterr = new CustomAdapter(this, customListt);

        if (customSpinnerType != null) {
            customSpinnerType.setAdapter(customAdapterr);

            customSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CustomItemsCat itemss = (CustomItemsCat) parent.getSelectedItem();
                    TypeChoisi = itemss.getSpinnerText();
                    Toast.makeText(AjoutEvent.this, TypeChoisi, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }


        addEvent = findViewById(R.id.addEvent);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Titre = titre.getText().toString();
                Description = description.getText().toString();
                dateFinEvent = dateFin.getText().toString();
                dateDebEvent = dateDeb.getText().toString();
                TimeDebEvent = timeDeb.getText().toString();
                TimeFinEvent = timeFin.getText().toString();
                duration = Integer.parseInt(dureee.getText().toString());
                urlImage = "vide pour le moument ";
                organisateur = MainActivity.userMail;

                saveDataEvent(Titre, categoChoisi, TypeChoisi, urlImage, Montant, Description, Capacitee, dateDebEvent, TimeDebEvent, duration, dateFinEvent, TimeFinEvent, Adressechoisi, organisateur);


            }
        });


    }

    private void saveDataEvent(String titre, String categoChoisi, String typeChoisi, String urlImage, String montant, String description, String capaciteee, String dateDebEvent, String timeDebEvent, int duration, String dateFinEvent, String timeFinEvent, String adressechoisi, String organisateur) {
        mService.addEventData(titre, categoChoisi, typeChoisi, urlImage, montant, description, capaciteee, dateDebEvent, timeDebEvent, duration, dateFinEvent, timeFinEvent, adressechoisi, organisateur)
                .enqueue(new Callback<APIResponse>() {


                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

                        APIResponse result = response.body();
                        if (result.getSuccess() == 1)
                            Toast.makeText(AjoutEvent.this, " good insert OF EVENT", Toast.LENGTH_LONG).show();
                        else if (result.getSuccess() == 0)
                            Toast.makeText(AjoutEvent.this, " bad insert OF EVENT", Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                        Toast.makeText(AjoutEvent.this, t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

    }

}
