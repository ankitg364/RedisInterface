package Controller;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ankit on 15/9/14.
 */
public class ConnectClientController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session=req.getSession();
        Jedis currentJedis=(Jedis)session.getAttribute("CurrentJedis");
        if(currentJedis!=null)
            currentJedis.disconnect();
        String connect=req.getParameter("connect").trim();
        if(connect.equals("1"))
        {
            String address=req.getParameter("address");
            String str[]= address.split("/", 2);
            String ipaddress =str[0].trim();
            String portno =str[1].trim();
            JedisPool jedisPool=new JedisPool(ipaddress,Integer.parseInt(portno));
            Jedis jedis=jedisPool.getResource();
            session.setAttribute("CurrentJedis",jedis);
        }
    }
}