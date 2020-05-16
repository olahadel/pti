package model;

import jpa.GenericJpaDao;

import javax.persistence.Persistence;

public class PlayerDAO extends GenericJpaDao<Player> {

    private static PlayerDAO instance;

    private PlayerDAO() {
        super(Player.class);
    }

    public static PlayerDAO getInstance() {
        if (instance == null) {
            instance = new PlayerDAO();
            /*
            Properties props = new Properties();
            props.load(new FileInputStream("/some/path/persistence.properties"));
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("appName", props);

             */
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
}