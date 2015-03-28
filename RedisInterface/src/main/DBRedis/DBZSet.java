package DBRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * Created by ankit on 21/8/14.
 */
public class DBZSet extends DBKey{
    public DBZSet(String ipaddress,int portno)
    {
        super(ipaddress,portno);
    }
    public String zadd(String key,Double score,String member)
    {
        String result=null;
        try
        {
            long res;
            res=jedis.zadd(key,score,member);
            if(res==1)
            {
                result="Key added successfuly";
            }
            else
            {
                result=member+" is already a member of "+key;
            }
        }
        catch(Exception e)
        {
            result="Key already exists of different type";
        }
        return result;
    }
    public Set<String> zrange(String key, long start, long end)
    {
        return jedis.zrange(key, start, end);
    }
    public double getRank(String key,String member)
    {
       return jedis.zscore(key,member);
    }
    public boolean edit_score(String key,String member,double new_score)
    {
        double old_score;
        try {
            old_score=jedis.zscore(key,member);
        }
        catch (Exception e) {
            return false;
        }
        jedis.zincrby(key,new_score-old_score,member);
        return true;
    }
    public boolean edit_value(String key,String member,String new_value)
    {
        double old_score;
        try {
            old_score=jedis.zscore(key,member);
        }
        catch (Exception e) {
            return false;
        }
        jedis.zrem(key,member);
        jedis.zadd(key,old_score,new_value);
        return true;
    }
    public boolean delete_element(String key,String member)
    {
        double old_score;
        try {
            old_score=jedis.zscore(key,member);
        }
        catch (Exception e) {
            return false;
        }
        jedis.zrem(key,member);
        return true;
    }
    public long del(String key)
    {
        return jedis.del(key);
    }
    public long size(String key)
    {
        return jedis.zcard(key);
    }
}
