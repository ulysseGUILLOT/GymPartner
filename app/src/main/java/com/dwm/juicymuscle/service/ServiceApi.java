package com.dwm.juicymuscle.service;

import android.util.JsonReader;

import com.dwm.juicymuscle.model.Exercice;
import com.dwm.juicymuscle.model.Programme;
import com.dwm.juicymuscle.model.User;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class ServiceApi {

    public ArrayList<Exercice> readJsonExercice(String in) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(in));
        try {
            return readExercicesArray(reader);
        } finally {
            reader.close();
        }
    }

    public ArrayList<Exercice> readExercicesArray(JsonReader reader) throws IOException {
        ArrayList<Exercice> exercices = new ArrayList<Exercice>();

        reader.beginArray();
        while (reader.hasNext()) {
            exercices.add(readExercice(reader));
        }
        reader.endArray();
        return exercices;
    }

    public Exercice readExercice(JsonReader reader) throws IOException {
        String id = null;
        String nom = null;
        String muscle = null;
        String description = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextString();
                    break;
                case "nom":
                    nom = reader.nextString();
                    break;
                case "muscle":
                    muscle = reader.nextString();
                    break;
                case "description":
                    description = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Exercice(id, nom, muscle, description);
    }

    public ArrayList<Programme> readJsonProgramme(String in) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(in));
        try {
            return readProgrammeArray(reader);
        } finally {
            reader.close();
        }
    }

    public ArrayList<Programme> readProgrammeArray(JsonReader reader) throws IOException {
        ArrayList<Programme> programmes = new ArrayList<Programme>();

        reader.beginArray();
        while (reader.hasNext()) {
            programmes.add(readProgramme(reader));
        }
        reader.endArray();
        return programmes;
    }

    public Programme readProgramme(JsonReader reader) throws IOException {
        String id = null;
        String idUser = null;
        String nom = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextString();
                    break;
                case "idUser":
                    idUser = reader.nextString();
                    break;
                case "nom":
                    nom = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Programme(id, idUser, nom);
    }

    public User readJsonUser(String in) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(in));
        try {
            return readUser(reader);
        } finally {
            reader.close();
        }
    }

    public User readUser(JsonReader reader) throws IOException {
        String id = null;
        String email = null;
        String username = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextString();
                    break;
                case "email":
                    email = reader.nextString();
                    break;
                case "username":
                    username = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new User(id, email, username);
    }
}
