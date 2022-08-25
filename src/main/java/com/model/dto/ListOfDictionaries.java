package com.model.dto;

import com.sun.jdi.PrimitiveValue;

import javax.persistence.*;

//@Entity
//@Table(name = "dictionaries")
public class ListOfDictionaries {
    //    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "dictionary")
    private String dictionary;
    private String template;

    public ListOfDictionaries(Long id, String dictionary, String template) {
        this.id = id;
        this.dictionary = dictionary;
        this.template = template;
    }

    public ListOfDictionaries(long id) {
    }

    public String getDictionary() {
        return dictionary;
    }

    public String getTemplate() {
        return template;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ListOfDictionaries{" +
                "id=" + id +
                ", dictionary='" + dictionary + '\'' +
                ", template='" + template + '\'' +
                '}';
    }
}
