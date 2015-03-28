package DBRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ankit on 21/8/14.
 */
public class DBHSet extends DBKey{

    public DBHSet(String ipaddress,int portno)
    {
        super(ipaddress,portno);
    }
    public String hset(String key,String field,String val)
    {
        String result=null;
        try {

            jedis.hset(key, field, val);
            result = "Field set successfully";
        }
        catch (Exception e) {
            result = "Key already exists of different type";
        }
        return result;
    }
    public Map<String, String> hgetall(String key)
    {
        Map<String,String> map=new LinkedHashMap<String, String>();
        try {
            map= jedis.hgetAll(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
    }
    public void hmset(String key,Map<String,String> hash)
    {
        jedis.hmset(key,hash);
    }
    public String hget(String key,String field)
    {
        return jedis.hget(key, field);
    }
    public Long hincrby(String key,String field,long val)
    {
        return jedis.hincrBy(key,field,val);
    }
    public boolean edit_value(String key,String field,String new_val)
    {
        if(!jedis.hexists(key,field))
            return false;
        jedis.hset(key,field,new_val);
        return true;
    }
    public boolean delete_field(String key,String field)
    {
        if(!jedis.hexists(key,field))
            return false;
        jedis.hdel(key,field);
        return true;
    }
    public long del(String key)
    {
        return jedis.del(key);
    }
    public long size(String key)
    {
        return jedis.hlen(key);
    }
}
