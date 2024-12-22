package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;

import java.util.List;

public interface SongService {
    List<Song> listSongs();
    public Song findByTrackId(String trackId);

    public Song findById(Long id);

    public Song save(String trackId, String title, String genre, int releaseYear, Long albumId, List<Long> artistIds);

    public Song update(Long id, String trackId, String title, String genre, int releaseYear, Long albumId, List<Long> artistIds);
    public void deleteById(Long id);

    List<Song> findAllByAlbum_Id(Long albumId, int releaseYear);

    List<Song> findAll();
}
