package Controller;

import Model.DBRedisInfo;
import Model.RedisInstance;
import Model.RedisInstances;
import Model.ValidateLogin;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

/**
 * Created by asad on 20/8/14.
 */
public class AddInstance extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String ip= request.getParameter("ip");
        String port=request.getParameter("port");

        DBRedisInfo D = new DBRedisInfo();
        try {
            D.insert_redisInstances(ip, port);
        }
        catch (Exception e)
        {
            out.print(e.getMessage());
        }

        RedisInstances redisInstances = new RedisInstances();
        redisInstances.setRedisInstances();
        request.setAttribute("Instances", redisInstances);

        RequestDispatcher view = request.getRequestDispatcher("/view/default.jsp");
        view.forward(request,response);
    }
}