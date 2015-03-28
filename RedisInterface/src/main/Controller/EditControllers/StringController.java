package Controller.EditControllers;

import DBRedis.DBString;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ankit on 28/8/14.
 */
public class StringController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address=req.getParameter("address");
        String str[]= address.split("/", 2);
        String ipaddress =str[0].trim();
        String portno =str[1].trim();
        String operation=req.getParameter("operation");
        String result=null;
        if(operation.equals("delete"))
        {
            String key=req.getParameter("key");
            DBString dbString=new DBString(ipaddress,Integer.parseInt(portno));
            dbString.del(key);
            result="Key deleted successfully";
            dbString.returnResource();
        }
        else if(operation.equals("edit")) {
            String key = req.getParameter("key");
            String new_value = req.getParameter("new_value");
            DBString dbString = new DBString(ipaddress, Integer.parseInt(portno));

            if (dbString.edit_value(key, new_value))
                result = "Edit Successful";
            else
                result = "Key doesn't exist";
            dbString.returnResource();
        }
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}
