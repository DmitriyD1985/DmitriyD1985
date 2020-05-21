package ffb.config;

import ffb.repository.AlbumsRepository;
import ffb.repository.SongsRepository;
import ffb.repository.UsersRepository;
import org.springframework.context.annotation.Configuration;


@Configuration
public class InitConfig {
    private AlbumsRepository albumsRepository;
    private SongsRepository songsRepository;
    private UsersRepository usersRepository;

    public InitConfig(AlbumsRepository albumsRepository, SongsRepository songsRepository, UsersRepository usersRepository) {
        this.albumsRepository = albumsRepository;
        this.songsRepository = songsRepository;
        this.usersRepository = usersRepository;
    }
}