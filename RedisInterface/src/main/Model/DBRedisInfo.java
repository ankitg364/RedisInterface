package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by asad on 20/8/14.
 */
public class DBRedisInfo
{
    String myDriver;
    public Connection con;
    String url, user, pwd;

    public void insert_redisInstances(String ip, String port)
    {
        String filename= "/home/ankit/Desktop/RedisInstances";
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename,true);
            fw.write(ip+' '+port+'\n');
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}