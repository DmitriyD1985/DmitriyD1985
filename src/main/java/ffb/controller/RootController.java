package ffb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping(path = "/")
    public String getRootPage() {
        return "index.html";
    }

    @GetMapping(path = "/songs")
    public String getSongsPage() {
        return "songs.html";
    }

    @GetMapping(path = "/albums")
    public String getAlbumPage() {
        return "albums.html";
    }

    @GetMapping(path = "/users")
    public String getUserPage() {
        return "users.html";
    }
}
