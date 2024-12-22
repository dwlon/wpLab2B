package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.AlbumRepository;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;
    private final AlbumRepository albumRepository;
    private final ArtistService artistService;

    public SongController(SongService songService, AlbumRepository albumRepository, ArtistService artistService) {
        this.songService = songService;
        this.albumRepository = albumRepository;
        this.artistService = artistService;
    }

    @GetMapping
    public String getSongsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Album> albums = this.albumRepository.findAll();
        model.addAttribute("albums", albums);

        List<Song> songs = this.songService.listSongs();
        model.addAttribute("songs", songs);
        return "songs";
    }

    @GetMapping("/access_denied")
    public String getAccessDeniedPage(Model model) {
        model.addAttribute("bodyContent", "access-denied");
        return "master-template";
    }


    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editSongPage(@PathVariable Long id, Model model) {
        Song song = this.songService.findById(id);
        if (song != null) {
            List<Album> albums = this.albumRepository.findAll();
            List<Artist> artists = artistService.listArtists();

            model.addAttribute("song", song);
            model.addAttribute("albums", albums);
            model.addAttribute("artists", artists);
            return "add-song";
        }
        return "redirect:/songs";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String addSongPage(Model model) {
        List<Album> albums = this.albumRepository.findAll();
        List<Artist> artists = this.artistService.listArtists();

        model.addAttribute("albums", albums);
        model.addAttribute("artists", artists);

        return "add-song";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveSong(@RequestParam(required = false) Long id,
                           @RequestParam String trackId,
                           @RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam int releaseYear,
                           @RequestParam Long albumId,
                           @RequestParam List<Long> artistIds) {

        if (id != null && id > 0) {
            this.songService.update(id, trackId, title, genre, releaseYear, albumId, artistIds);
        } else {
            this.songService.save(trackId, title, genre, releaseYear, albumId, artistIds);
        }

        return "redirect:/songs";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteSong(@PathVariable Long id) {
        this.songService.deleteById(id);
        return "redirect:/songs";
    }

    @GetMapping("/filter")
    public String filterSongsByAlbum(@RequestParam Long albumId, @RequestParam Integer year, Model model) {
        List<Song> filtered = this.songService.findAllByAlbum_Id(albumId, year);
        List<Album> albums = this.albumRepository.findAll();

        model.addAttribute("songs", filtered);
        model.addAttribute("albums", albums);
        model.addAttribute("year", year);

        return "songs";
    }

}
