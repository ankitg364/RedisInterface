package Controller.EditControllers;

import DBRedis.DBList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ankit on 28/8/14.
 */
public class ListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address=req.getParameter("address");
        String str[]= address.split("/", 2);
        String ipaddress =str[0].trim();
        String portno =str[1].trim();
        String operation=req.getParameter("operation");
        String result=null;


        if(operation.equals("index"))
        {
            String index=req.getParameter("index");
            String key=req.getParameter("key");
            DBList dbList=new DBList(ipaddress,Integer.parseInt(portno));
            boolean res=dbList.delete_at_index(key,Integer.parseInt(index));
            if(res==true)
                result="Delete successful";
            else
                result="Index out of range";
            dbList.returnResource();
        }
        else if(operation.equals("edit")) {
            String index = req.getParameter("index");
            String key = req.getParameter("key");
            String new_value = req.getParameter("new_value");
            DBList dbList = new DBList(ipaddress, Integer.parseInt(portno));

            if (dbList.edit_value(key, Integer.parseInt(index), new_value))
                result = "Edit Successful";
            else
                result = "Key doesn't exist";
            dbList.returnResource();
        }
        else if(operation.equals("delete"))
        {
            String key = req.getParameter("key");
            DBList dbList = new DBList(ipaddress, Integer.parseInt(portno));
            dbList.del(key);
            result="Key Deleted Successfully";
            dbList.returnResource();
        }
        else if(operation.equals("setExpiry"))
        {
            String key = req.getParameter("key");
            int exp = Integer.parseInt(req.getParameter("expiry"));

            DBList dbList = new DBList(ipaddress, Integer.parseInt(portno));
            dbList.setExpiry(key, exp);
            result="Expiry set Successfully";
            dbList.returnResource();
        }
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}
