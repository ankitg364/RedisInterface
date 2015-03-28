package Controller.MonitorControllers;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Time;

/**
 * Created by ankit on 10/9/14.
 */
public class StartMonitorController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession();
        String ipaddress=(String)sess.getAttribute("CurrentIP");
        String portno=(String)sess.getAttribute("CurrentPort");
        JedisPool jedisPool=new JedisPool(ipaddress,Integer.parseInt(portno));
        Jedis jedis=jedisPool.getResource();
        JedisPool localjedisPool=new JedisPool("localhost",6380);
        Jedis localjedis=localjedisPool.getResource();
        sess.setAttribute("isOn",true);
        String key=ipaddress+":"+portno+":"+"used_memory";
        String keycommands=ipaddress+":"+portno+":total_commands_processed";
        String keyclients=ipaddress+":"+portno+":connected_clients";
        String used_memory="used_memory:";
        String total_commands_processed="total_commands_processed:";
        long unixTime = System.currentTimeMillis() / 1000L;
        localjedis.zadd(key,unixTime,Long.toString(unixTime)+":"+0);
        String info= jedis.info();
        int ind=info.indexOf(total_commands_processed);
        ind=ind+total_commands_processed.length();
        int j;
        for(j=ind; info.charAt(j)!='\n' && info.charAt(j)!=','; j++);
        String valcommands=info.substring(ind,j);
        String val=null;
        String valclients=null;
        localjedis.zadd(keycommands,unixTime,Long.toString(unixTime)+":"+ valcommands);
        localjedis.zadd(keyclients,unixTime,Long.toString(unixTime)+":0");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while((Boolean)sess.getAttribute("isOn")==true)
        {
            info= jedis.info();
            ind= info.indexOf(used_memory);
            ind=ind+used_memory.length();
            for(j=ind; info.charAt(j)!='\n' && info.charAt(j)!=','; j++);
            val=info.substring(ind,j);
            ind=info.indexOf(total_commands_processed);
            ind=ind+total_commands_processed.length();
            for(j=ind; info.charAt(j)!='\n' && info.charAt(j)!=','; j++);
            valcommands=info.substring(ind,j);
            ind=info.indexOf("connected_clients:");
            ind=ind+"connected_clients:".length();
            for(j=ind; info.charAt(j)!='\n' && info.charAt(j)!=','; j++);
            valclients=info.substring(ind,j);
            unixTime = System.currentTimeMillis() / 1000L;
            localjedis.zadd(key, unixTime, Long.toString(unixTime) + ":" + val);
            localjedis.zadd(keycommands, unixTime, Long.toString(unixTime)+":"+valcommands);
            localjedis.zadd(keyclients, unixTime, Long.toString(unixTime) + ":" + valclients);
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        unixTime = System.currentTimeMillis() / 1000L;
        localjedis.zadd(key,unixTime,Long.toString(unixTime)+":0");
        localjedis.zadd(keycommands,unixTime,Long.toString(unixTime)+":"+valcommands);
        localjedis.zadd(keyclients,unixTime,Long.toString(unixTime)+":0");
        jedis.disconnect();
        localjedis.disconnect();
        jedisPool.returnResource(jedis);
        localjedisPool.returnResource(localjedis);
    }
}