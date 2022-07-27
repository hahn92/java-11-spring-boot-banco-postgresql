package com.hahn.banco.configuration;


public enum ConstantsJWT {
    EXPIRATION_TIME("3600000"), //60*60*1000
    TOKEN_PREFIX("Bearer "),
    HEADER_STRING("Authorization"),
    ISSUER("banco"),
    SECRET("secret");    
    
    public final String label;

    private ConstantsJWT(String label) {
        this.label = label;
    }
}
