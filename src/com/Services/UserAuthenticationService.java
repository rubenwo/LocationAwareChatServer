package com.Services;


import com.Entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public class UserAuthenticationService {

    public static User authenticate(String fireBaseToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(fireBaseToken);
            return new User(decodedToken.getName(), decodedToken.getEmail(), decodedToken.getUid());
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return null;
        }
    }
}
