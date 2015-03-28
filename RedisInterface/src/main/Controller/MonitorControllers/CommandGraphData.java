package Controller.MonitorControllers;

import DBRedis.DBZSet;
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
 * Created by ankit on 11/9/14.
 */
public class CommandGraphData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession();
        String ipaddress=(String)sess.getAttribute("CurrentIP");
        String portno=(String)sess.getAttribute("CurrentPort");
        int duration= Integer.parseInt(req.getParameter("type").trim());
        JedisPool jedisPool=new JedisPool("localhost",6380);
        Jedis jedis=jedisPool.getResource();
        String key=ipaddress+":"+portno+":"+"total_commands_processed";
        Map<String,String> map=new LinkedHashMap<String, String>();
        long unixTime = System.currentTimeMillis() / 1000L;
        SimpleDateFormat sdf=null;
        Set<String> data=data= jedis.zrangeByScore(key,unixTime-duration,unixTime);

        if(duration<=3600)
            sdf = new SimpleDateFormat("HH:mm:ss a");
        else if(duration<=86400)
            sdf = new SimpleDateFormat("h:mm a");
        else if(duration<=604800)
            sdf = new SimpleDateFormat("MMM d, h a");
        else
            sdf = new SimpleDateFormat("MMM d");

        sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        int it=0;
        long prev=0;
        long prevtime=0L;
        String prevDate="";
        for (String s:data)
        {
            String splits[]= s.split(":");
            splits[0].trim();
            splits[1].trim();

            long index=(long)(Double.parseDouble(splits[0]));
            long value=(long)Double.parseDouble(splits[1]);

            Date date = new Date(index * 1000L);
            String formattedDate = sdf.format(date);
            if((it==0)||(index-prevtime)>65)
                if(formattedDate.equals(prevDate))
                    map.put(formattedDate,Double.toString((value-prev)+Double.parseDouble(map.get(prevDate))));
                else
                map.put(formattedDate,"0");
            else {
                if(formattedDate.equals(prevDate))
                    map.put(formattedDate,Double.toString((value-prev)+Double.parseDouble(map.get(prevDate))));
                else
                map.put(formattedDate, Double.toString(value-prev));
            }
            it++;
            prevtime=index;
            prev = value;
            prevDate=formattedDate.trim();
        }
        jedis.disconnect();
        jedisPool.returnResource(jedis);
        String json = new Gson().toJson(map);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}