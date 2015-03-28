package Controller.MonitorControllers;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ankit on 13/9/14.
 */
public class TopCommandsGraph extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession();
        String ipaddress = (String) sess.getAttribute("CurrentIP");
        String portno = (String) sess.getAttribute("CurrentPort");
        int duration = Integer.parseInt(req.getParameter("type").trim());
        JedisPool jedisPool = new JedisPool("localhost", 6380);
        Jedis jedis = jedisPool.getResource();
        String key = ipaddress + ":" + portno + ":" + "top_commands";
        long unixTime = System.currentTimeMillis() / 1000L;
        Set<String> data = jedis.zrangeByScore(key, unixTime-duration, unixTime);
        Map<String,String> map=new LinkedHashMap<String, String>();
        for(String s:data)
        {

            String splits[]=s.split(":");
            if(map.containsKey(splits[1])) {
                int val =Integer.parseInt( map.get(splits[1]));
                map.remove(splits[1]);
                map.put(splits[1],Integer.toString(val+1));
            }
            else
            {
                map.put(splits[1],"1");
            }
        }
        jedis.disconnect();
        jedisPool.returnResource(jedis);
        String json = new Gson().toJson(map);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}