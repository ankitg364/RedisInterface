package Controller.MonitorControllers;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

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
public class StartMonitor extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,SocketException {
        HttpSession sess = req.getSession();
        String ipaddress = (String) sess.getAttribute("CurrentIP");
        String portno = (String) sess.getAttribute("CurrentPort");
        JedisPool jedisPool = new JedisPool(ipaddress, Integer.parseInt(portno));
        Jedis jedis = jedisPool.getResource();
        JedisPool localjedisPool = new JedisPool("localhost", 6380);
        final Jedis localjedis = localjedisPool.getResource();
        sess.setAttribute("Monitor",jedis);
        final String commands=ipaddress+":"+portno+":"+"top_commands";
        final String keys=ipaddress+":"+portno+":"+"top_keys";
        JedisMonitor jedisMonitor=
                new JedisMonitor() {
                    @Override
                    public void onCommand(String s) {
                        int start=s.indexOf("\"");
                        int end=s.indexOf("\"",start+1);
                        String command=s.substring(start+1,end);
                        start=s.indexOf("\"",end+1);
                        if(start!=-1&&!(command.equals("scan")||command.equals(keys))) {
                            end = s.indexOf("\"", start + 1);
                            String key = s.substring(start + 1, end);
                            long unixTime = System.currentTimeMillis() / 1000L;
                            localjedis.zadd(commands, unixTime, unixTime + ":" + command);
                            localjedis.zadd(keys, unixTime, unixTime + ":" + key);
                        }
                    }
                };
        try {
            jedis.monitor(jedisMonitor);
        }
        catch (JedisConnectionException e) {

        }
    }
}
