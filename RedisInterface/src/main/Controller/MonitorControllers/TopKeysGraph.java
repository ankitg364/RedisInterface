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
public class TopKeysGraph extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession();
        String ipaddress = (String) sess.getAttribute("CurrentIP");
        String portno = (String) sess.getAttribute("CurrentPort");
        int duration = Integer.parseInt(req.getParameter("type").trim());
        JedisPool jedisPool = new JedisPool("localhost", 6380);
        Jedis jedis = jedisPool.getResource();
        String key = ipaddress + ":" + portno + ":" + "top_keys";
        long unixTime = System.currentTimeMillis() / 1000L;
        SimpleDateFormat sdf = null;
        Set<String> data = jedis.zrangeByScore(key, unixTime - duration, unixTime);
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(String s:data)
        {
            String splits[]=s.split(":",2);
            if(map.containsKey(splits[1])) {
                int val =map.get(splits[1]);
                map.remove(splits[1]);
                map.put(splits[1],val+1);
            }
            else
            {
                map.put(splits[1],1);
            }
        }
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        int i=0;
        Map<String,Integer> map1=new LinkedHashMap<String, Integer>();
        for(Map.Entry<String, Integer> entry:list){
            map1.put(entry.getKey(),entry.getValue());
            i++;
            if(i>10)
                break;
        }
        jedis.disconnect();
        jedisPool.returnResource(jedis);
        String json = new Gson().toJson(map1);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
