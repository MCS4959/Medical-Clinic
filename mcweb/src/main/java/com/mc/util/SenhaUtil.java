package com.mc.util;

import org.mindrot.jbcrypt.BCrypt;

public class SenhaUtil {


    public static String criptografar(String senhaPlana) {
        return BCrypt.hashpw(senhaPlana, BCrypt.gensalt());
    }

    
    public static boolean verificar(String senhaPlana, String senhaHashed) {
        try {
            return BCrypt.checkpw(senhaPlana, senhaHashed);
        } catch (Exception e) {
            return false;
        }
    }
}