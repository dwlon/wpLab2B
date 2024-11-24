package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.AlbumRepository;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.SongRepository;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }


    @Override
    public Song findByTrackId(String trackId) {
        return songRepository.findByTrackId(trackId);
    }

    @Override
    public Song findById(Long id) {
        return songRepository.findById(id).orElse(null);
    }

    @Override
    public Song save(String trackId, String title, String genre, int releaseYear, Long albumId, List<Long> artistIds) {
        Song song = new Song(trackId, title, genre, releaseYear);

        song.setAlbum(albumRepository.findById(albumId).orElse(null));

        for (long artist : artistIds) {
            song.addArtist(artistRepository.findById(artist).orElse(null));
        }

        return songRepository.save(song);
    }

    @Override
    public Song update(Long id, String trackId, String title, String genre, int releaseYear, Long albumId, List<Long> artistIds) {
        Song song = this.songRepository.findById(id).orElse(null);

        song.setId(id);
        song.setTitle(title);
        song.setTrackId(trackId);
        song.setGenre(genre);
        song.setReleaseYear(releaseYear);
        song.setAlbum(albumRepository.findById(albumId).orElse(null));

        return songRepository.save(song);
    }

    @Override
    public void deleteById(Long id) {
        songRepository.deleteById(id);
    }

    @Override
    public List<Song> findAllByAlbum_Id(Long albumId) {
        return songRepository.findAllByAlbum_Id(albumId);
    }
}
