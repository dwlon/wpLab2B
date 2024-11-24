package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.AlbumRepository;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.SongRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Artist> artists = new ArrayList<>();

    public static List<Song> songs = new ArrayList<>();

    public static List<Album> albums = new ArrayList<>();

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public DataHolder(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    @PostConstruct
    public void init() {
        artists = new ArrayList<>();

        if (artistRepository.count() == 0) {
            artists.add(new Artist("The Weeknd", " ", " "));
            artists.add(new Artist("Kendrick", "Lamar", " "));
            artists.add(new Artist("Coldplay", " ", " "));
            artists.add(new Artist("The Beatles", " ", " "));
            artists.add(new Artist("MGMT", " ", " "));
            artistRepository.saveAll(artists);
        }


        albums = new ArrayList<>();

        if (albumRepository.count() == 0) {
            albums.add(new Album("Starboy", "RNB", "2016"));
            albums.add(new Album("TPAB", "RAP", "2015"));
            albums.add(new Album("VLVODAHF", "IND", "2008"));
            albums.add(new Album("Abbey Road", "ROCK", "1969"));
            albums.add(new Album("Oracular Spectacular", "POP", "2007"));
            albumRepository.saveAll(albums);
        }

        songs = new ArrayList<>();

        if (songRepository.count() == 0) {
            songs.add(new Song("STB", "Starboy", "RNB", 2016));
            songs.add(new Song("ALR", "Alright", "RAP", 2015));
            songs.add(new Song("VLV", "Viva la Vida", "IND", 2008));
            songs.add(new Song("NGM", "You Never Give Me Your Money", "ROCK", 1969));
            songs.add(new Song("ELF", "Electric Feel", "POP", 2007));

            for (int i = 0; i < 5; i++) {
                songs.get(i).addArtist(artists.get(i));
                songs.get(i).setAlbum(albums.get(i));
            }

            songRepository.saveAll(songs);
        }


    }
}
