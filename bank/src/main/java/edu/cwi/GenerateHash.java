package edu.cwi;

import org.mindrot.jbcrypt.BCrypt;

public class GenerateHash {

    public static void main(String[] args) {

        String username = "Admin";
        String password = "admin1";

        String hash = BCrypt.hashpw(password, BCrypt.gensalt());

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Hash: " + hash);
    }
}