/*

    Klasa wrapper do usera

 */
package com.example.projekt_grupowy.Models;


import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class User {

    private UserFirebase userFirebase;
    private String uid;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private ArrayList<Document> documents;

    public User(String uid) {
        this.uid = uid;

        this.userFirebase = new UserFirebase();

        this.db = FirebaseFirestore.getInstance();
        this.documentReference = db.collection("users").document(getUID());

        this.documents = new ArrayList<>();
    }

    public UserFirebase getUserFirebase() {
        return userFirebase;
    }

    public String getUID() {
        return uid;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public void addDocument(Document document) {
        this.documents.add(document);
    }


    @NonNull
    @Override
    public String toString() {

        String str = "User:\n" +
                "\tUID: " + getUID() + "\n" +
                "\temail: " + getUserFirebase().getEmail() + "\n" +
                "\tnumber of documents: " + getDocuments().size() + "\n" +
                "\n\nall documents: \n";

        for (Document document : getDocuments()) {
            str += document.toString() + "\n";
        }

        return str;
    }
}
