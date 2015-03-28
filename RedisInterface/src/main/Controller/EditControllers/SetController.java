package Controller.EditControllers;

import DBRedis.DBSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ankit on 28/8/14.
 */
public class SetController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address=req.getParameter("address");
        String str[]= address.split("/", 2);
        String ipaddress =str[0].trim();
        String portno =str[1].trim();
        String operation=req.getParameter("operation");
        String result=null;
        if(operation.equals("element"))
        {
            String old_value = req.getParameter("old_value");
            String key=req.getParameter("key");
            DBSet dbSet=new DBSet(ipaddress,Integer.parseInt(portno));
            boolean res= dbSet.srem(key, old_value);
            if(res==true)
                result="Element deleted successfully";
            else
                result="Element doesn't exist";
            dbSet.returnResource();
        }
        else if(operation.equals("edit")) {
            String old_value = req.getParameter("old_value");
            String key = req.getParameter("key");
            String new_value = req.getParameter("new_value");
            DBSet dbSet = new DBSet(ipaddress, Integer.parseInt(portno));
            if (dbSet.edit_value(key, old_value, new_value))
                result = "Edit Successful";
            else
                result = "Key or member doesn't exist";
            dbSet.returnResource();
        }
        else
        {
            DBSet dbSet = new DBSet(ipaddress, Integer.parseInt(portno));
            String key = req.getParameter("key");
            dbSet.del(key);
            result="Key deleted successfully";
            dbSet.returnResource();
        }
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}
