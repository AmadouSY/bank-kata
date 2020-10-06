package fr.arolla.bank;

import java.util.Random;

public class IBAN {

    String value;

    IBAN(){
        this.value = buildIBAN();
    }

    private String buildIBAN(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("FR76");

        Random random = new Random();
        for(int i = 0; i < 23; i++){
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
