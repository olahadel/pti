package model;

import javax.persistence.*;

@Entity
@Table(name="wordset")
public class WordSet {

    @Id
    @GeneratedValue
    private String wordSetId;

    @Column(nullable=false)
    private String letterSet;

    @Column(nullable=false)
    private String possibleWord;

    public WordSet(String letterSet, String possibleWord) {
        this.letterSet = letterSet;
        this.possibleWord = possibleWord;
    }
}
