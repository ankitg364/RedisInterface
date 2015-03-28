package Controller;

import DBRedis.Node;
import DBRedis.Nodes;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ankit on 2/5/14.
 */

public class TreeViewController extends HttpServlet
{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = req.getParameter("address");
        String str[] = address.split("/", 2);

        String ipaddress = str[0].trim();
        String portno = str[1].trim();

        JedisPool jedisPool = new JedisPool(ipaddress, Integer.parseInt(portno));
        Jedis jedis = jedisPool.getResource();

        ScanResult<String> scanResult;
        List<String> stringList=new ArrayList<String>();
        String cursor="0";
        do {
            scanResult=jedis.scan(cursor);
            cursor=scanResult.getStringCursor();
            for (String s:scanResult.getResult()) {
                stringList.add(s);
            }
        }
        while (!cursor.equals("0"));

        jedis.disconnect();
        jedisPool.returnResource(jedis);

        List<Nodes> list=buildtree(stringList);
        String json = new Gson().toJson(list);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    public List<Nodes> buildtree(List<String> stringSet)
    {
        Node root=new Node("0","","#");
        Nodes jsonroot=new Nodes("0","","#");

        List<Nodes> nodes=new ArrayList<Nodes>();
        nodes.add(jsonroot);

        Node current;
        Node node;


        for (String s:stringSet)
        {
            current=root;
            String[] splits=s.split(":");
            for(int i=0;i<splits.length;i++)
            {
                node=findnode(current,splits[i]);
                if(node==null)
                {
                    if(current.id.equals("0")) {
                        node = new Node(splits[i], splits[i], current.id);
                        nodes.add(new Nodes(splits[i], splits[i], current.id));
                    }
                    else
                    {
                        node = new Node(current.id+":"+splits[i], splits[i], current.id);
                        nodes.add(new Nodes(current.id +":" +splits[i], splits[i], current.id));
                    }

                }
                current.list.add(node);
                current=node;
            }
        }
        return nodes;
    }
    public Node findnode(Node current,String  val)
    {
        for(int i=0;i<current.list.size();i++)
        {
            if(current.list.get(i).text.equals(val))
                return current.list.get(i);
        }
        return null;
    }
}