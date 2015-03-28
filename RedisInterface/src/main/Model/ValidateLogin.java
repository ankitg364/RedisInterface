package Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by asad on 17/8/14.
 */
public class ValidateLogin
{
    String myDriver;
    Connection con;
    String url, user, pwd;

    public ValidateLogin()
    {
         myDriver = "org.gjt.mm.mysql.Driver";
         con = null;
         url = "jdbc:mysql://localhost:3306/RedisInfo";
         user = "root";
         pwd = "root";
    }

    public boolean validate(String username, String password) throws IOException
    {
        try
        {
            Class.forName(myDriver);
            con = DriverManager.getConnection(url, user, pwd);
            Statement st = (Statement) con.createStatement();

            String sqlOption="SELECT* FROM loginInfo where username=? and password=?";
            //st.executeUpdate("INSERT INTO loginInfo " + "VALUES ('abc', 'gh')");

            PreparedStatement ps=con.prepareStatement(sqlOption);
            ps.setString(1,username);
            ps.setString(2,password);

            ResultSet rs=ps.executeQuery();

            if(rs.last())
            {
                con.close() ;
                return true;
            }
            else
            {
                con.close();
                System.out.println("login not found !!");
                return false;
            }
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            e.printStackTrace();
        }
        return false;
    }

    public Connection getConnection() throws IOException
    {

        try {
            Class.forName(myDriver);
            con = DriverManager.getConnection(url, user, pwd);
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            e.printStackTrace();
        }
        return con;
    }

}