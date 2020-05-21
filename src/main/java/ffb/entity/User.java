package ffb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "login is required")
    private String login;

    @Size(min = 1, max = 20, message = "password should be from 1 to 20 symbols")
    @NotBlank(message = "Password is required")
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profID")
    @JsonBackReference
    private ProFile userProfile;

    @ManyToMany(fetch= FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name="choised_Albums",
            joinColumns=@JoinColumn(name="user_id", referencedColumnName = "ID"),
            inverseJoinColumns=@JoinColumn(name="album_id", referencedColumnName = "ID"))
    @JsonIgnore
    private List<Albums> userA;

    public void addAlbum(Albums albums) {
        this.userA.add(albums);
        albums.getUsers().add(this);
        albums.getUsers().add(this);
    }

    public void removeAlbum(Albums albums) {
            albums.getUsers().remove(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", userProfile=" + userProfile +
                '}';
    }
}