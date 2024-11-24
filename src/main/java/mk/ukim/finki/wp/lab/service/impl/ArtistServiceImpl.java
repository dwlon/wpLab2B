package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Artist> listArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist findById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }

    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist update(Long id, String firstName, String lastName, String bio) {
        Artist artist = this.artistRepository.findById(id).orElse(null);

        artist.setFirstName(firstName);
        artist.setBio(bio);
        artist.setLastName(lastName);

        return artistRepository.save(artist);
    }

}
