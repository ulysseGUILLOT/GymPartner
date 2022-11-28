package com.dwm.juicymuscle.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dwm.juicymuscle.R;
import com.dwm.juicymuscle.adapter.AdapterListExercices;
import com.dwm.juicymuscle.model.Exercice;
import com.dwm.juicymuscle.model.PutData;
import com.dwm.juicymuscle.service.ServiceApi;

import java.io.IOException;
import java.util.ArrayList;

public class EditTrainingActivity extends AppCompatActivity {
    private ArrayList<Exercice> listeExercices = new ArrayList<Exercice>();

    private Button retourButton;
    private TextView titleTextView;
    private RecyclerView.Adapter adapter;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String IDUSER_KEY = "iduser_key";
    public static final String USERNAME_KEY = "username_key";
    SharedPreferences sharedpreferences;
    String username, iduser;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_training);

        RecyclerView recyclerView = findViewById(R.id.edittraining_recyclerview_listexercice);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterListExercices(listeExercices);
        recyclerView.setAdapter(adapter);

        retourButton = findViewById(R.id.edittraining_button_retour);
        titleTextView = findViewById(R.id.edittraining_textview_title);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        iduser = sharedpreferences.getString(IDUSER_KEY, null);
        username = sharedpreferences.getString(USERNAME_KEY, null);
        titleTextView.setText("Exercices disponibles pour " + username + " :" );

        retourButton.setOnClickListener(new View.OnClickListener() { //retour a la page de training
            @Override
            public void onClick(View v) {
                Intent trainingActivityIntent = new Intent(EditTrainingActivity.this, TrainingActivity.class);
                startActivity(trainingActivityIntent);
            }
        });

        Handler handler = new Handler(); //recuperation des exercices et appel du recycler
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[0];
                String[] data = new String[0];
                PutData putData = new PutData("http://ulysseguillot.fr/apiLoginJuicyMuscle/getExercices.php", "GET", field, data);

                if (putData.startPut()) {
                    if(putData.onComplete()){
                        String result = putData.getResult();

                        ServiceApi jsonToObject = new ServiceApi();
                        try {
                            listeExercices.addAll(jsonToObject.readJsonExercice(result));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}