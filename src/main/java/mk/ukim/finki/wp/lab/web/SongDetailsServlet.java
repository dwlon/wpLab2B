package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.SongService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name="details-servlet", urlPatterns = "/details")
public class SongDetailsServlet extends HttpServlet {
    private final SpringTemplateEngine ste;
    private final SongService service;

    public SongDetailsServlet(SpringTemplateEngine ste, SongService service) {
        this.ste = ste;
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext webContext = new WebContext(webExchange);

        String songId = req.getParameter("songId");

        Song song = service.findByTrackId(songId);

        webContext.setVariable("song", song);

        ste.process("songDetails.html", webContext, resp.getWriter());
    }

}
