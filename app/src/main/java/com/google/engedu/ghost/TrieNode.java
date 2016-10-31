package com.google.engedu.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private char value;
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        value = '\0';
        children = new HashMap<>();
        isWord = false;
    }
    public TrieNode(char c)
    {
        value = c;
        children = new HashMap<>();
        isWord = false;
    }

    //Inserts the string s into the trie structure.
    public void add(String s) {
        int length = s.length();
        TrieNode temp = this;
        HashMap<Character,TrieNode> child;
        for(int i =0;i<length;i++)
        {
            child = temp.children;
            char curr = s.charAt(i);

            if(child.containsKey(curr))
                temp = child.get(curr);
            else
            {
                TrieNode leaf = new TrieNode(curr);
                child.put(curr,leaf);
                temp = leaf;
            }
            if(i == length -1)
                temp.isWord = true;
        }

    }

    //Returns the TrieNode Object that corresponds to last character of string s, or null if none is found
    public TrieNode findNode(String s)
    {
        HashMap<Character,TrieNode> child  = this.children;
        TrieNode t = null;
        int length = s.length();
        for(int i = 0; i<length;i++){
            char c = s.charAt(i);

            if(child.containsKey(c))
            {
                t=child.get(c);//get the node of next character in the word
                child = t.children;
            }
            else
                return null;
        }
        return t;
    }

    //Returns true if word s is in the trie, else false
    public boolean isWord(String s) {
        if(s== null)
            return false;
        TrieNode temp = findNode(s);
        return temp != null && temp.isWord;
    }

    public void findWords(String prefix,TrieNode temp,ArrayList<String> words)
    {
        for(TrieNode x : temp.children.values())
        {
            String currentStr = prefix+x.value;
            if(x.isWord) words.add(currentStr);
            if(x.children.size()>0) findWords(currentStr,x,words);
        }

    }
    public String getAnyWordStartingWith(String s) {
        // Initialize resultant string
        ArrayList<String> words = new ArrayList<>();
        Random r = new Random();
        String prefix;
        if(s==null){
            char randchar = (char)(r.nextInt(26) + 'a');
            prefix = Character.toString(randchar);
        }
        else
            prefix = s;
        // Initialize reference to traverse through Trie
        TrieNode temp = findNode(prefix);
        if(temp!=null)
        {
            //add prefix to list if its a word in itself
            if(temp.isWord) words.add(prefix);
            //Recursively find all the words that start with prefix
            findWords(prefix,temp,words);

            if(words.size()!=0)//return a random word from found list
                return words.get(r.nextInt(words.size()));
            else
                return null;
        }
        else
            return null;
    }

    public String getGoodWordStartingWith(String s) {
        ArrayList<String> words = new ArrayList<>();
        boolean selected = false;
        Random r = new Random();
        String prefix;
        if(s.equals("")){
            char randchar = (char)(r.nextInt(26) + 'a');
            prefix = Character.toString(randchar);
        }
        else
            prefix = s;
        // Initialize reference to traverse through Trie
        TrieNode temp = findNode(prefix);
        if(temp!=null) {
            //add prefix to list if its a word in itself
            if (temp.isWord) words.add(prefix);
            //Recurrsively find all the words that start with prefix
            findWords(prefix, temp, words);
            if(words.size()!=0){
                String toReturn = "";
                //choose a word in a smarter way
                while(!selected)
                {
                    int wIndex = r.nextInt(words.size());
                    String result = words.get(wIndex);
                    String afterPrefix;
                    if(prefix.length()<result.length())
                        afterPrefix = prefix + result.substring(prefix.length(), prefix.length() + 1);
                    else
                        afterPrefix = result;
                    if(isWord(afterPrefix))
                    {
                        selected = false;
                        toReturn = result;
                        words.remove(wIndex);
                        if(words.isEmpty())break;
                    }
                    else
                    {
                        selected = true;
                        toReturn = result;
                    }
                }
                return toReturn;
            }
            else
                return null;
        }
        else
            return null;
    }
}