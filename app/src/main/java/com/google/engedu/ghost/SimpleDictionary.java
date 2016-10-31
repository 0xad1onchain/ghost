package com.google.engedu.ghost;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private Random random = new Random();
    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
                words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        String word;
        if (TextUtils.isEmpty(prefix)) {
            int wordListSize = words.size();
            int index = random.nextInt(wordListSize);
            word = words.get(index);
        } else {
            word = searchForWord(prefix);
        }
        return word;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }

    private String searchForWord(String prefix) {
        int min = 0;
        int max = words.size();
        while (min <= max) {
            int mid = (min + max) / 2;
            String currentWord = words.get(mid);
            if (currentWord.startsWith(prefix)) {
                return currentWord;
            } else if (currentWord.compareTo(prefix) < 0) {
                min = mid + 1;
            } else if (currentWord.compareTo(prefix) > 0) {
                max = mid - 1;
            }
        }
        return null;
    }
}