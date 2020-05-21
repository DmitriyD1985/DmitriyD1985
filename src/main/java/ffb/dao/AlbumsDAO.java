package ffb.dao;

import ffb.entity.Albums;
import ffb.entity.Songs;
import ffb.entity.User;
import java.util.List;


public interface AlbumsDAO {
    List<Albums> listOfAlbums();
    public Albums getAlbumsByName(String albumsName);
    public void insertAlbums(Albums albums);
    public void updateAlbums(Albums albums);
    public void removeAlbums(Albums albums);
}
