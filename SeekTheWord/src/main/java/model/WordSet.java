package model;

import javax.persistence.*;

@Entity
@Table(name="wordset")
public class WordSet {

    @Id
    @GeneratedValue
    private Integer wordSetId;

    @Column(nullable=false)
    private String letterSet;

    @Column(nullable=false)
    private String possibleWord;

    public WordSet(String letterSet, String possibleWord) {
        this.letterSet = letterSet;
        this.possibleWord = possibleWord;
    }

    public Integer getWordSetId() {
        return wordSetId;
    }

    public void setWordSetId(Integer wordSetId) {
        this.wordSetId = wordSetId;
    }

    public String getLetterSet() {
        return letterSet;
    }

    public void setLetterSet(String letterSet) {
        this.letterSet = letterSet;
    }

    public String getPossibleWord() {
        return possibleWord;
    }

    public void setPossibleWord(String possibleWord) {
        this.possibleWord = possibleWord;
    }
}
