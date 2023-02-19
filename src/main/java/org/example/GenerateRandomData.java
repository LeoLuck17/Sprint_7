package org.example;

import java.util.ArrayList;
import java.util.Random;

public class GenerateRandomData {
    public static String generateRandomData(int length) {
        String characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        StringBuilder bufferString = new StringBuilder();
        Random random = new Random();
        char[] text = new char[length];
        for (int i = 0; i < text.length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        for (char c : text) {
            bufferString.append(c);
        }
        return bufferString.toString();
    }
    public static String randomNumber(int length){
        String characters = "1234567890";
        StringBuilder bufferString = new StringBuilder();
        Random random = new Random();
        char[] text = new char[length];
        for (int i = 0; i < text.length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        for (char c : text) {
            bufferString.append(c);
        }
        return bufferString.toString();
    }
    public static String[] randomNearestStation(int length){
        ArrayList<String> numberArray = new ArrayList<>();
        for(int i = 0; i < length; i++){
            numberArray.add(randomNumber(1));
        }
        return numberArray.toArray(new String[]{});
    }
}
