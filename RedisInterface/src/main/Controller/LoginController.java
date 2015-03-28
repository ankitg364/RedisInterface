package Controller;

import Model.RedisInstances;
import Model.ValidateLogin;
import redis.clients.jedis.JedisPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
/**
 * Created by asad on 13/8/14.
 */
public class LoginController extends HttpServlet
{
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        RedisInstances redisInstances = new RedisInstances();
        redisInstances.setRedisInstances();
        request.setAttribute("Instances", redisInstances);

        RequestDispatcher view = request.getRequestDispatcher("/view/default.jsp");
        view.forward(request,response);
    }
}
