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
public class KeyType extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        String address=req.getParameter("address");
        String str[]= address.split("/", 2);
        String ipaddress =str[0].trim();
        String portno =str[1].trim();
        String key=req.getParameter("key");
        DBKey dbKey=new DBKey(ipaddress,Integer.parseInt(portno));
        String keytype= dbKey.type(key);
        dbKey.returnResource();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(keytype);
    }
}
