package DBRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by ankit on 21/8/14.
 */
public class DBString extends DBKey{

    public DBString(String ipaddress,int portno)
    {
        super(ipaddress,portno);
    }

    public String get(String key)
    {
       return jedis.get(key);
    }
    public boolean edit_value(String key,String value)
    {
        if(get(key)==null)
            return false;
        jedis.set(key,value);
        return true;
    }
    public String set(String key,String  val, int expiry)
    {
        String result=null;
        try {

                jedis.set(key, val);
                if(expiry!=-1)
                    jedis.expire(key, expiry);

                result = "Key added successfully";

        }
        catch (Exception e)
        {
            result="Error in adding Key :(";
        }
        return result;
    }
    public long del(String key)
    {
        return jedis.del(key);
    }
    public long size(String key)
    {
        String value=jedis.get(key);
        return value.length();
    }


    public void incrby(String key,long val)
    {
        jedis.incrBy(key,val);
    }
}
