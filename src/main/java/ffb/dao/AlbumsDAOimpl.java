package ffb.dao;

import ffb.entity.Albums;
import ffb.entity.Songs;
import ffb.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Component
@Transactional
public class AlbumsDAOimpl implements AlbumsDAO {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Albums> listOfAlbums() {
        Query selectquerry = entityManager.createQuery("SELECT a FROM Albums a", Albums.class);
       return selectquerry.getResultList();
    }

    @Override
    public Albums getAlbumsByName(String albumsName) {
        Query selectquerry = entityManager.createQuery("SELECT a FROM Albums a WHERE a.albumName = :albumName", Albums.class);
        selectquerry.setParameter("albumName", albumsName);
        return (Albums) selectquerry.getSingleResult();
    }

    @Override
    public void insertAlbums(Albums albums) {
        entityManager.merge(albums);
    }

    @Override
    public void updateAlbums(Albums albums) {
        Query selectquerry = entityManager.createQuery("SELECT a FROM Albums a WHERE a.id = :id", Albums.class);
        selectquerry.setParameter("id", albums.getId());
        Albums albumForUpdate = (Albums) selectquerry.getSingleResult();
        albumForUpdate.setAlbumName(albums.getAlbumName());
        entityManager.merge(albumForUpdate);
    }

    @Override
    public void removeAlbums(Albums albums) {
        Query selectquerry = entityManager.createQuery("SELECT a FROM Albums a WHERE a.id = :id", Albums.class);
        selectquerry.setParameter("id", albums.getId());
        Albums albumForRemove = (Albums) selectquerry.getSingleResult();
        List <Songs> listOfReferenceSongs = albumForRemove.getSongs();
        List <User> listOfReferenceUsers = albumForRemove.getUsers();
        entityManager.remove(albumForRemove);
    }

}
