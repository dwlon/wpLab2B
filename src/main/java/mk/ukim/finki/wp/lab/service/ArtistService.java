package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Artist;

import java.util.List;

public interface ArtistService {
    List<Artist> listArtists();
    Artist findById(Long id);

    public Artist save(Artist artist);

    public Artist update(Long id, String firstName, String lastName, String bio);
}
