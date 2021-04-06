package com.zndev.zn_inventory.helpers;

import com.zndev.zn_inventory.models.other.Response;
import java.util.List;
import java.util.Random;

public class Helper {

    public Helper(){
    }

    public static Response createResponse(String message, Boolean status){
        Response response=new Response();
        response.setMessage(message);
        response.setStatus(status);
        return response;
    }

    public static Response createResponse(String message, Boolean status, List<?> list){
        Response response=new Response();
        response.setMessage(message);
        response.setStatus(status);
        response.setList(list);
        return response;
    }

    public static final String GenerateRandomString(int lenght){

        // create a string of uppercase and lowercase characters and numbers
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        // combine all strings
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 10;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

    return sb.toString();
    }
}
