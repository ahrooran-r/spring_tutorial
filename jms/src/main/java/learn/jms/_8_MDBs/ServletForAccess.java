package learn.jms._8_MDBs;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class ServletForAccess extends HttpServlet {

    @EJB
    MessageProducer producer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = "Hello JavaEE from Servlet";
        producer.sendMessage(message);
        resp.getWriter().write("Published the message: " + message);
    }
}
