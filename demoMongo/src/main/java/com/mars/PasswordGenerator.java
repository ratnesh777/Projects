package com.mars;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordGenerator
{

    public static void main(String aa[]){
    
      String password = "Welcome12#";
   
        System.out.println(BCrypt.hashpw(password, BCrypt.gensalt()));
    }
}
