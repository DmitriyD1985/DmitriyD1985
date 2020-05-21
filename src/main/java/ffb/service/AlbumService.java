package ffb.service;

import ffb.entity.Albums;

import java.util.List;

public interface AlbumService {
    List<Albums> listOfAlbums();
    public Albums getAlbumsByName(String name);
    public void insertAlbums(Albums albums);
    public void updateAlbums(Albums albums);
    public void removeAlbums(Albums albums);
}
