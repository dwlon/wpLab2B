package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/add-artist")
public class ArtistController {
    private final ArtistService artistService;
    private final SongService songService;

    public ArtistController(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String addArtistPage(Model model) {
        List<Song> songs = songService.listSongs();
        List<Artist> artists = artistService.listArtists();

        model.addAttribute("songs", songs);
        model.addAttribute("artists", artists);

        return "add-artist";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveArtist(@RequestParam(required = false) Long id,
                             @RequestParam Long songId,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String bio) {

        Artist artist = new Artist(firstName, lastName, bio);

        if (id != null && id > 0) {
            this.artistService.update(artist.getId(), firstName, lastName, bio);
        } else {
            this.artistService.save(artist);
        }


        Song song = songService.findById(songId);

        song.addArtist(artist);

        return "redirect:/songs";
    }
}
