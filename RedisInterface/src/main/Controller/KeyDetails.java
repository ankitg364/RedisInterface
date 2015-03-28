package Controller;

import DBRedis.*;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by asad on 22/8/14.
 */
public class KeyDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        String address=req.getParameter("address");
        String str[]= address.split("/", 2);
        String ipaddress =str[0].trim();
        String portno =str[1].trim();
        String key=req.getParameter("key");
        String keytype=req.getParameter("type");
        Map<String, String> map = new LinkedHashMap<String, String>();
        if(keytype.equals("string"))
        {
            DBString dbString=new DBString(ipaddress,Integer.parseInt(portno));
            String value = dbString.get(key);
            long size=dbString.size(key);
            long ttl=dbString.ttl(key);
            map.put("size",Long.toString(size));
            map.put("ttl",Long.toString(ttl));
            map.put(key, value);
            dbString.returnResource();
        }
        else if(keytype.equals("set"))
        {
            DBSet dbSet =new DBSet(ipaddress,Integer.parseInt(portno));
            long size=dbSet.size(key);
            long ttl=dbSet.ttl(key);
            map.put("size",Long.toString(size));
            map.put("ttl",Long.toString(ttl));
            Set<String> members=new HashSet<String>();
            members=dbSet.members(key);
            int i=1;
            for (String s : members) {
                map.put("member"+i,s);
                i++;
            }
            dbSet.returnResource();
        }
        else if(keytype.equals("list"))
        {
            DBList dbList =new DBList(ipaddress,Integer.parseInt(portno));
            long size=dbList.size(key);
            long ttl=dbList.ttl(key);

            map.put("size",Long.toString(size));
            map.put("ttl",Long.toString(ttl));
            List<String> result=dbList.lrange(key,0,-1);
            Iterator iter=result.iterator();
            int i=0;
            for (String s : result) {
                map.put(""+i,s);
                i++;
            }
            dbList.returnResource();
        }
        else if(keytype.equals("hash"))
        {
            DBHSet dbhSet =new DBHSet(ipaddress,Integer.parseInt(portno));
            long size=dbhSet.size(key);
            long ttl=dbhSet.ttl(key);
            map.put("size",Long.toString(size));
            map.put("ttl",Long.toString(ttl));
            Map<String,String> result=dbhSet.hgetall(key);
            for (Map.Entry<String, String> entry : result.entrySet())
            {
                map.put(entry.getKey(),entry.getValue());
            }
            dbhSet.returnResource();
        }
        else if(keytype.equals("zset"))
        {
            DBZSet dbzSet =new DBZSet(ipaddress,Integer.parseInt(portno));
            long size=dbzSet.size(key);
            long ttl=dbzSet.ttl(key);

            map.put("size",Long.toString(size));
            map.put("ttl",Long.toString(ttl));
            Set<String> members=dbzSet.zrange(key,-1000000000,1000000000);
            Iterator iter = members.iterator();
            while (iter.hasNext()) {
                String v = (String)iter.next();
                double rank=dbzSet.getRank(key, v);
                map.put(v,""+rank);
            }
            dbzSet.returnResource();
        }
        String json = new Gson().toJson(map);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
