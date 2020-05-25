package ffb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Albums")
public class Albums {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String albumName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            mappedBy = "userA")
    @JsonIgnore
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "albums", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Songs> songs;

    public void setSongs(List<Songs> songs) {
        if(songs != null){
            songs.forEach(a->a.setAlbums(this));
        }
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Albums{" +
                "id=" + id +
                ", albumName='" + albumName + '\'' +
                ", users=" + users +
                ", songs=" + songs +
                '}';
    }
}
