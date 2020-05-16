package model;

import jpa.GenericJpaDao;

import javax.persistence.Persistence;
import java.util.List;

public class WordSetDAO extends GenericJpaDao<WordSet> {

    private static WordSetDAO instance;

    private WordSetDAO() {
        super(WordSet.class);
    }

    public static WordSetDAO getInstance() {
        if (instance == null) {
            instance = new WordSetDAO();
            instance.setEntityManager(Persistence.createEntityManagerFactory("seektheword-mysql").createEntityManager());
        }
        return instance;
    }

    /*
    public List<Player> findBest(int n) {
        return entityManager.createQuery("SELECT r FROM GameResult r WHERE r.solved = true ORDER BY r.duration ASC, r.created DESC", Player.class)
                .setMaxResults(n)
                .getResultList();
    }
    */

    public List<String> findAllLetterSets() {
        return entityManager.createQuery("SELECT distinct letterSet FROM WordSet", String.class)
                .getResultList();
    }

    public List<String> findAllWords(String letterSet) {
        return entityManager.createQuery("SELECT possibleWord FROM WordSet WHERE letterset = :letterSet", String.class)
                .setParameter("letterSet", letterSet)
                .getResultList();
    }
}
