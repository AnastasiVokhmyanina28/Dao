package com.model.dto;

public class MeaningsLyingInTheDictionary {
    private Long id;
    private Long id_words;
    private String word;
    private String translation;

    public MeaningsLyingInTheDictionary(int id_word) {
    }
    public MeaningsLyingInTheDictionary(String word) {
    }

    public MeaningsLyingInTheDictionary(String word, String translation) {
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setId_words(Long id_words) {
        this.id_words = id_words;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Long getId() {
        return id;
    }

    public Long getId_words() {
        return id_words;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }
}
