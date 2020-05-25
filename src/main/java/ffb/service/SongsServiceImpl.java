package ffb.service;

import ffb.dao.SongsDAO;
import ffb.entity.Songs;
import ffb.repository.SongsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SongsServiceImpl implements SongService {

    SongsDAO songsDAO;

    @Autowired
    public SongsServiceImpl(SongsDAO songsDAO) {
        this.songsDAO = songsDAO;
    }

    @Override
    public List<Songs> listOfSongs() {
        return songsDAO.listOfSongs();
    }

    @Override
    public Songs getSongsByName(String songsNmae) {
        return songsDAO.getSongsByName(songsNmae);
    }

    @Override
    public void insertSongs(Songs songs) {
        songsDAO.insertSongs(songs);
    }

    @Override
    public void updateSongs(Songs songs) {
        songsDAO.updateSongs(songs);
    }

    @Override
    public void removeSongs(Songs songs) {
        songsDAO.removeSongs(songs);
    }
}

