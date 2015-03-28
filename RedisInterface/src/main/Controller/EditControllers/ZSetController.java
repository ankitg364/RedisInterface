package Controller.EditControllers;

import DBRedis.DBZSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ankit on 28/8/14.
 */
public class ZSetController extends HttpServlet {
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
            DBZSet dbzSet=new DBZSet(ipaddress,Integer.parseInt(portno));
            boolean res= dbzSet.delete_element(key, old_value);
            if(res==true)
                result="Element deleted successfully";
            else
                result="Element doesn't exist";
            dbzSet.returnResource();
        }
        else if(operation.equals("edit")) {
            String old_value = req.getParameter("old_value");
            String key = req.getParameter("key");
            String new_value = req.getParameter("new_value");
            DBZSet dbzSet = new DBZSet(ipaddress, Integer.parseInt(portno));
            if (dbzSet.edit_score(key, old_value, Double.parseDouble(new_value)))
                result = "Edit Successful";
            else
                result = "Key doesn't exist";
            dbzSet.returnResource();
        }
        else if(operation.equals("delete"))
        {
            String key = req.getParameter("key");
            DBZSet dbzSet = new DBZSet(ipaddress, Integer.parseInt(portno));
            dbzSet.del(key);
            result="Key deleted successfully";
            dbzSet.returnResource();
        }
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}
