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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;

/**
 * Created by ankit on 3/9/14.
 */
public class SearchController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address=req.getParameter("address");
        String pattern=req.getParameter("pattern");
        List<String> matches=new ArrayList<String>();

        if(!pattern.equals("")) {
            String str[] = address.split("/", 2);
            String ipaddress = str[0].trim();
            String portno = str[1].trim();
            JedisPool jedisPool = new JedisPool(ipaddress, Integer.parseInt(portno));
            Jedis jedis = jedisPool.getResource();
            ScanParams scanParams = new ScanParams();
            scanParams.match(pattern + "*");
            ScanResult<String> scanResult;
            String cursor = "0";
            int i = 0;
            while (i < 8) {
                scanResult = jedis.scan(cursor, scanParams);
                cursor = scanResult.getStringCursor();
                if(cursor.equals("0"))
                    break;
                for (String s : scanResult.getResult()) {
                    if (i >= 8)
                        break;
                    matches.add(s);
                    i++;
                }
            }
            jedis.disconnect();
            jedisPool.returnResource(jedis);
        }

        String json = new Gson().toJson(matches);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}