package Controller.MonitorControllers;

import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.SocketException;

/**
 * Created by ankit on 13/9/14.
 */
public class StopMontior extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,SocketException {
        HttpSession sess = req.getSession();
        Jedis jedis= (Jedis)sess.getAttribute("Monitor");
        jedis.disconnect();
    }
}
