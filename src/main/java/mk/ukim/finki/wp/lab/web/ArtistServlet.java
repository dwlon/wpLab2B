package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name="artist-servlet", urlPatterns = "/artist")
public class ArtistServlet extends HttpServlet {
    private final SpringTemplateEngine ste;
    private final ArtistService artistService;
    private final SongService songService;

    public ArtistServlet(SpringTemplateEngine ste, ArtistService artistService, SongService songService) {
        this.ste = ste;
        this.artistService = artistService;
        this.songService = songService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext webContext = new WebContext(webExchange);

        webContext.setVariable("artists", artistService.listArtists());

        String songId = req.getParameter("songId");

        webContext.setVariable("song", songService.findByTrackId(songId));

        ste.process("artistsList.html", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String artistName = req.getParameter("artistName");
        String songId = req.getParameter("songId");

        Song song = songService.findByTrackId(songId);
        Artist artist = artistService.findById(Long.parseLong(artistName));

        song.addArtist(artist);

        resp.sendRedirect("/details?songId=" + songId);
    }
}
