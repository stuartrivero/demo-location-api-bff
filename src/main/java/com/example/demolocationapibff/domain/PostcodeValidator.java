package com.example.demolocationapibff.domain;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PostcodeValidator {


    public static final Pattern REGEX = Pattern.compile("^(([A-Z]{1,2}[0-9][A-Z0-9]?|ASCN|STHL|TDCU|BBND|[BFS]IQQ|PCRN|TKCA) ?[0-9][A-Z]{2}|BFPO ?[0-9]{1,4}|(KY[0-9]|MSR|VG|AI)[ -]?[0-9]{4}|[A-Z]{2} ?[0-9]{2}|GE ?CX|GIR ?0A{2}|SAN ?TA1)$");

    //This is not a real validation - it would need  to be changed to be real life
    public boolean isValid(String postcode) {
        return REGEX.matcher(postcode).matches();
    }
}
