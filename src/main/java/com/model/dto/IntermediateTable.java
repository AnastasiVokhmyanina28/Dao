package com.model.dto;

public class IntermediateTable {

    public IntermediateTable(long id, long word, long translation) {
    }

    public Long getId() {
        return id;
    }

    public Long getWord() {
        return word;
    }

    public Long getTranslation() {
        return translation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWord(Long word) {
        this.word = word;
    }

    public void setTranslation(Long translation) {
        this.translation = translation;
    }

    private Long id;
    private Long word;
    private Long translation;
}
