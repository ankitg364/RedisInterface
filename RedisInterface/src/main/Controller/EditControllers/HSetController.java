package Controller.EditControllers;

import DBRedis.DBHSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ankit on 28/8/14.
 */
public class HSetController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address=req.getParameter("address");
        String str[]= address.split("/", 2);
        String ipaddress =str[0].trim();
        String portno =str[1].trim();

        String operation=req.getParameter("operation");
        String result=null;

        if(operation.equals("field"))
        {

            String field=req.getParameter("field");
            String key=req.getParameter("key");
            DBHSet dbhSet=new DBHSet(ipaddress,Integer.parseInt(portno));
            boolean res= dbhSet.delete_field(key,field);
            if(res==true)
               result="Field deleted successfully";
            else
                result="Field doesn't exist";
            dbhSet.returnResource();
        }
        else if(operation.equals("edit"))
        {
            String field=req.getParameter("field");
            String key=req.getParameter("key");
            String new_value=req.getParameter("new_value");
            DBHSet dbhSet=new DBHSet(ipaddress,Integer.parseInt(portno));

            if(dbhSet.edit_value(key,field,new_value))
                result="Edit Successful";
            else
                result="Key or field doesn't exist";
            dbhSet.returnResource();
        }
        else if(operation.equals("delete"))
        {
            String key=req.getParameter("key");
            DBHSet dbhSet=new DBHSet(ipaddress, Integer.parseInt(portno));

            dbhSet.del(key);
            dbhSet.returnResource();
            result="Delete Successful";
        }
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}
