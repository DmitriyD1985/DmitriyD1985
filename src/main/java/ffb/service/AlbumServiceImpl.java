package ffb.service;

import ffb.dao.AlbumsDAO;
import ffb.entity.Albums;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

   private AlbumsDAO albumsDAO;

    public AlbumServiceImpl(AlbumsDAO albumsDAO) {
        this.albumsDAO = albumsDAO;
    }

    @Override
    public List<Albums> listOfAlbums() {
        return albumsDAO.listOfAlbums();
    }

    @Override
    public Albums getAlbumsByName(String name) {
        return albumsDAO.getAlbumsByName(name);
    }

    @Override
    public void insertAlbums(Albums albums) {
        albumsDAO.insertAlbums(albums);
    }

    @Override
    public void updateAlbums(Albums albums) {
        albumsDAO.updateAlbums(albums);
    }

    @Override
    public void removeAlbums(Albums albums) {
        albumsDAO.removeAlbums(albums);
    }
}
