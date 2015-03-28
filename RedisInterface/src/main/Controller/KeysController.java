package Controller;

import DBRedis.GetKeys;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import redis.clients.jedis.ScanParams;
import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;

/**
 * Created by ankit on 19/8/14.
 */
public class KeysController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //super.doGet(req, resp);
        String address=req.getParameter("address");
        String type = req.getParameter("type");
        String currIndex = req.getParameter("currIndex");

        String str[]= address.split("/", 2);
        String ipaddress =str[0].trim();
        String portno =str[1].trim();
        String json=null;
        try
        {
                List<String> arr = new ArrayList<String>();
                GetKeys getKeys = new GetKeys(ipaddress, Integer.parseInt(portno) );
                getKeys.setJedis();

                if(type.equals("All")) {
                    arr = getKeys.getAllKeys(currIndex);
                }
                else if(type.equals("List")) {
                    arr = getKeys.getKeysByList(currIndex);
                }
                else if(type.equals("String")) {
                    arr = getKeys.getKeysByString(currIndex);
                }
                else if(type.equals("Set")) {
                    arr = getKeys.getKeysBySet(currIndex);
                }
                else if(type.equals("ZSet")) {
                    arr = getKeys.getKeysByZSet(currIndex);
                }
                else {
                    arr = getKeys.getKeysByHash(currIndex);
                }
                getKeys.returnResource();
                json= new Gson().toJson(arr);
            }
            catch (Exception e) {
                System.out.print(e.getStackTrace());
            }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}

