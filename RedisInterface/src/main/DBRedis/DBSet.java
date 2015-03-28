package DBRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * Created by ankit on 21/8/14.
 */
public class DBSet extends DBKey {

    public DBSet(String ipaddress,int portno)
    {
        super(ipaddress,portno);
    }
    public String sadd(String key,String member)
    {
        String result=null;
        try {
            if (jedis.sismember(key, member)) {
                result = member + " is already an element of " + key;
                return result;
            }
            jedis.sadd(key, member);
            result = "Element added succesfully in set";
        }
        catch (Exception e)
        {
            result="Key already exists of different type";
        }
        return result;
    }
    public boolean srem(String key,String member)
    {
        if(!jedis.sismember(key, member))
            return false;
        jedis.srem(key, member);
        return true;
    }
    public Set<String> members(String key)
    {
        return jedis.smembers(key);
    }
    public boolean ismember(String key,String member)
    {
        return jedis.sismember(key,member);
    }
    public void sunion(String key1,String key2)
    {
        jedis.sunion(key1,key2);
    }
    public long del(String key)
    {
        return jedis.del(key);
    }
    public boolean edit_value(String key,String initial_value,String final_value)
    {
        if(!ismember(key,initial_value))
            return false;
        jedis.srem(key,initial_value);
        jedis.sadd(key,final_value);
        return true;
    }
    public long size(String key)
    {
        return jedis.scard(key);
    }
}
