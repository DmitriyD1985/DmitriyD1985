package ffb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Songs")
public class Songs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "songName is required")
    private String songName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id")
    @JsonIgnore
    Albums albums;

    @Override
    public String toString() {
        return "Songs{" +
                "id=" + id +
                ", songName='" + songName + '\'' +
                ", albums=" + albums +
                '}';
    }
}