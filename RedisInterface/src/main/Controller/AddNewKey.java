package Controller;

import DBRedis.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;

/**
 * Created by asad on 22/8/14.
 */
public class AddNewKey extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String type = request.getParameter("type");
        String key = request.getParameter("key");
        String opt = request.getParameter("opt");
        String value = request.getParameter("value");
        String address = request.getParameter("address");
        String expiry = request.getParameter("expiry");

        String str[] = address.split("/", 2);
        String ipaddress = str[0].trim();
        String portno = str[1].trim();
        opt = opt.trim();
        String result=null;
        if (type.equals("String"))
        {
            DBString db = new DBString(ipaddress, Integer.parseInt(portno));
            result=db.set(key, value, Integer.parseInt(expiry));
            db.returnResource();
        }
        else if (type.equals("List"))
        {
            DBList db = new DBList(ipaddress, Integer.parseInt(portno));
            if (opt.equals("-1")) {
                result=db.lpush(key, value);
            } else {
                result=db.rpush(key, value);
            }
            db.returnResource();
        }
        else if (type.equals("HSet"))
        {
            DBHSet db = new DBHSet(ipaddress, Integer.parseInt(portno));
            result=db.hset(key, opt, value);
            db.returnResource();
        }
        else if (type.equals("ZSet"))
        {
            DBZSet db = new DBZSet(ipaddress, Integer.parseInt(portno));
            result=db.zadd(key, Double.parseDouble(opt), value);
            db.returnResource();
        }
        else if (type.equals("Set"))
        {
            DBSet db = new DBSet(ipaddress, Integer.parseInt(portno));
            result=db.sadd(key, value);
            db.returnResource();
        }
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }
}
