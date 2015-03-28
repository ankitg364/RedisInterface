package Model;

import Model.RedisInstance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit on 14/8/14.
 */
public class RedisInstances {
    private List<RedisInstance> redisInstances=new ArrayList<RedisInstance>();
    private RedisInstance redisInstance;
    public void setRedisInstances()
    {
        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader("/home/ankit/Desktop/RedisInstances"));
            String sCurrentLine = br.readLine();
            while (sCurrentLine != null) {
                String[] parts = sCurrentLine.split(" ");
                String ipAddress = parts[0];
                int portNo = Integer.parseInt(parts[1]);
                redisInstance = new RedisInstance(ipAddress,portNo);
                redisInstance.setActive();
                redisInstances.add(redisInstance);
                sCurrentLine = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public List<RedisInstance> getRedisInstances()
    {
        return redisInstances;
    }
}
