package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String genre;
    private int releaseYear;

    @OneToMany(mappedBy = "album")
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(song);
        song.setAlbum(this);
    }

    public void removeSong(Song song) {
        songs.remove(song);
        song.setAlbum(null);
    }
    public Album(String name, String genre, int releaseYear) {
        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }
}
