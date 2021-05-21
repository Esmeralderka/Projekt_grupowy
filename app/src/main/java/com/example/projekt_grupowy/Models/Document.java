package com.example.projekt_grupowy.Models;

import androidx.annotation.NonNull;

import com.example.projekt_grupowy.MainActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Document {

    private String uid;
    private FirebaseFirestore db;
    private DocumentReference documentReference;

    private HashMap<String, Object> documentFields;

    public Document() {
        this.uid = "";
        this.db = FirebaseFirestore.getInstance();
        this.documentFields = new HashMap<String, Object>();
    }

    public Document(String uid) {
        this.uid = uid;
        this.db = FirebaseFirestore.getInstance();
        this.documentFields = new HashMap<String, Object>();
    }

    public Document(String uid, HashMap<String, Object> hashMap) {
        this.uid = uid;
        this.db = FirebaseFirestore.getInstance();
        this.documentFields = hashMap;
    }

    public DocumentReference getDocumentReference() {
        return db.collection("users").document(MainActivity.appUser.getUID()).collection("documents").document(getUID());
    }

    public void setUID(String uid) {
        this.uid = uid;
    }

    public String getUID() {
        return uid;
    }

    public HashMap<String, Object> getDocumentHashMap() {
        return documentFields;
    }

    public void setDocumentHashMap(HashMap<String, Object> hashMap) {
        documentFields = hashMap;
    }

    @NonNull
    @Override
    public String toString() {
        String str = "Document " + getUID() + ":\n";

        for (String key : getDocumentHashMap().keySet()) {
            str += "\t" + key + ": " + getDocumentHashMap().get(key) + "\n";
        }

        return str;
    }
}
