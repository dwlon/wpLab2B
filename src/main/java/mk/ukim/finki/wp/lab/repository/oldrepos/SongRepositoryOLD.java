package mk.ukim.finki.wp.lab.repository.oldrepos;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class SongRepositoryOLD {
    public List<Song> findAll() {
        return DataHolder.songs;
    }

    public Song findByTrackId(String trackId) {
        return DataHolder.songs.stream().filter(s -> s.getTrackId().equals(trackId)).findFirst().orElse(null);
    }

    public Song findById(Long ID) {
        return DataHolder.songs.stream().filter(s -> Objects.equals(s.getId(), ID)).findFirst().orElse(null);
    }

    public Artist addArtistToSong(Artist artist, Song song) {
        Optional<Song> track = DataHolder.songs.stream().filter(s -> s.equals(song)).findFirst();
        track.ifPresent(s -> s.addArtist(artist));
        return artist;
    }

    public Song save(String trackId, String title, String genre, int releaseYear, Long albumId, List<Long> artistIds) {
        Song s = new Song(trackId, title, genre, releaseYear);

        Song temp = DataHolder.songs.stream().filter(so -> so.getTrackId().equals(s.getTrackId())).findFirst().orElse(null);
        if (temp != null) {
            DataHolder.songs.remove(temp);
        }


        artistIds.forEach(id -> s.addArtist(DataHolder.artists.stream().filter(a -> a.getId() == id).findFirst().orElse(null)));
        s.setAlbum(DataHolder.albums.stream().filter(a -> Objects.equals(a.getId(), albumId)).findFirst().orElse(null));
        DataHolder.songs.add(s);
        return s;
    }

    public void deleteById(Long id) {
        Song song = DataHolder.songs.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        if (song != null) {
            DataHolder.songs.remove(song);
        }
    }
}
