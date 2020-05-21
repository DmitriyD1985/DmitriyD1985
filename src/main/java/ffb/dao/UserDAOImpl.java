package ffb.dao;

import ffb.entity.Songs;
import ffb.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Component
@Transactional(readOnly = true)
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;


    @Override
    public List listOfUsre() {
        Query selectquerry = entityManager.createQuery("SELECT u FROM User u", User.class);
        return selectquerry.getResultList();
    }

    @Override
    public void insertUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        Query selectquerry = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        selectquerry.setParameter("id", user.getId());
        User userForUpdate = (User)selectquerry.getSingleResult();
        userForUpdate.setLogin(user.getLogin());
        userForUpdate.setPassword(user.getPassword());
        entityManager.merge(userForUpdate);
    }

    @Override
    public void removeUser(User user) {
        Query selectquerry = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :songName", User.class);
        selectquerry.setParameter("songName", user.getLogin());
        User entity = (User) selectquerry.getSingleResult();
        entity.setUserProfile(null);
        entity.setUserA(null);
        entityManager.remove(entity);
    }
}
