/*

    Klasa usera

 */
package com.example.projekt_grupowy.Models;

import java.util.Map;

public class UserFirebase {

    private String email;

    public UserFirebase() {
        this.email = "";
    }

    public void set(Map<String, Object> data) throws NullPointerException {
        setEmail(data.get("email").toString());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
