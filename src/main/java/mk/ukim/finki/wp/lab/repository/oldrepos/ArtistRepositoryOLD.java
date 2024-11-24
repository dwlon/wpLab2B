package mk.ukim.finki.wp.lab.repository.oldrepos;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Artist;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtistRepositoryOLD {
    public List<Artist> findAll() {
        return DataHolder.artists;
    }

    public Artist findByID(long id) {
        return DataHolder.artists.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    public void addArtist(Artist artist) {
        if (DataHolder.artists.contains(artist)) {
            DataHolder.artists.remove(artist);
        }
        DataHolder.artists.add(artist);
    }
}
