package Controller.MonitorControllers;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by ankit on 10/9/14.
 */
public class MonitorController extends HttpServlet {
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
        long unixTime;
        String sub[]={"used_memory:","keys=","connected_clients:","total_commands_processed:","uptime_in_seconds:"};
        for(int i=0;i<sub.length;i++)
        {
            int ind=info.indexOf(sub[i]);
            ind=ind+sub[i].length();
            int j;
            for(j=ind;info.charAt(j)!='\n'&&info.charAt(j)!=',';j++);
            String val=info.substring(ind,j);
            if(i==0)
            {
                long value=(long)(Double.parseDouble(val)/1000L);
                map.put(sub[i],value+"kBs");
            }
            else if(i==4)
            {
                unixTime = System.currentTimeMillis() / 1000L;
                unixTime=(long)(unixTime-Double.parseDouble(val));
                Date date=new Date(unixTime*1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                String formattedDate = sdf.format(date);
                map.put(sub[i],formattedDate);
            }
            else
                map.put(sub[i], val);
        }
        String json = new Gson().toJson(map);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
