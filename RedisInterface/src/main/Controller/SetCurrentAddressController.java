package Controller;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asad on 9/9/14.
 */
public class SetCurrentAddressController extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address=req.getParameter("address");
        String str[] = address.split("/", 2);
        String ipaddress = str[0].trim();
        String portno = str[1].trim();

        HttpSession sess = req.getSession();
        sess.setAttribute("CurrentIP", ipaddress);
        sess.setAttribute("CurrentPort", portno);

        String dummy = "";

        String json = new Gson().toJson(dummy);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
