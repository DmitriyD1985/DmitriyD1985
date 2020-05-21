package ffb.service;

import ffb.entity.Albums;
import ffb.entity.Songs;

import java.util.List;


public interface SongService {
    List<Songs> listOfSongs();
    public Songs getSongsByName(String songsNmae);
    public void insertSongs(Songs songs);
    public void updateSongs(Songs songs);
    public void removeSongs(Songs songs);
}
