package Controller;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ankit on 4/9/14.
 */
public class InfoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = req.getParameter("address");
        String str[] = address.split("/", 2);
        String ipaddress = str[0].trim();
        String portno = str[1].trim();
        Map<String, String> map = new LinkedHashMap<String, String>();
        JedisPool jedisPool = new JedisPool(ipaddress, Integer.parseInt(portno));
        Jedis jedis = jedisPool.getResource();
        String info = jedis.info();
        jedis.disconnect();
        jedisPool.returnResource(jedis);
        info = info.replaceAll("_", " ");
        int i = 0;
        int p = 0;
        String key = "";
        String val = "";

        int len = info.length();

        while (i < len) {
            if (info.charAt(i) == '\n') {

                val = info.substring(p, i);
                p = i + 1;
                if (key.equals("")) {
                    key = val;
                    val = "";
                }
                if (!key.equals("")) {
                    key = Character.toUpperCase(key.charAt(0)) + key.substring(1);
                    map.put(key, val);
                    //System.out.println(key + "\t\t" + val);
                }
                key = "";
                val = "";
            } else if (info.charAt(i) == ':') {
                key = info.substring(p, i);
                p = i + 1;
            }
            i++;
        }
        String json = new Gson().toJson(map);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
