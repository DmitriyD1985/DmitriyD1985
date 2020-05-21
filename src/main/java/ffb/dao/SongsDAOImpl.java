package ffb.dao;

import ffb.entity.Songs;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Component
@Transactional
public class SongsDAOImpl implements SongsDAO {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List listOfSongs() {
        Query selectquerry = entityManager.createQuery("SELECT s FROM Songs s", Songs.class);
        return selectquerry.getResultList();
    }

    @Override
    public Songs getSongsByName(String songsNmae) {
        Query selectquerry = entityManager.createQuery("SELECT s FROM Songs s WHERE s.songName = :songs", Songs.class);
        selectquerry.setParameter("songs", songsNmae);
        return (Songs) selectquerry.getSingleResult();
    }

    @Override
    public void insertSongs(Songs songs) {
        entityManager.merge(songs);
    }

    @Override
    public void updateSongs(Songs songs) {
        Query selectquerry = entityManager.createQuery("SELECT s FROM Songs s WHERE s.id = :id", Songs.class);
        selectquerry.setParameter("id", songs.getId());
        Songs songForUpdate = (Songs)selectquerry.getSingleResult();
        songForUpdate.setSongName(songs.getSongName());
        entityManager.merge(songForUpdate);
    }

    @Override
    public void removeSongs(Songs songs) {
        Query selectquerry = entityManager.createQuery("SELECT s FROM Songs s WHERE s.songName = :songName", Songs.class);
        selectquerry.setParameter("songName", songs.getSongName());
        Songs entity = (Songs) selectquerry.getSingleResult();
        entity.setAlbums(null);
        entityManager.remove(entity);
    }
}
