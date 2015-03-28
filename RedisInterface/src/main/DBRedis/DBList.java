package DBRedis;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import sun.org.mozilla.javascript.EcmaError;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit on 21/8/14.
 */
public class DBList extends DBKey{
    public DBList(String ipaddress,int portno)
    {
        super(ipaddress,portno);
    }

    public String rpush(String key,String val)
    {
        String result=null;
        try
        {
            jedis.rpush(key,val);
            result="Key added sucessfully";
        }
        catch(Exception e)
        {
            result="Key already exists of different type";
        }
        return result;
    }
    public String lpush(String key,String val)
    {
        String result=null;
        try
        {
            jedis.lpush(key,val);
            result="Key added sucessfully";
        }
        catch(Exception e)
        {
            result="Key already exists of different type";
        }
        return result;
    }

    public List<String> lrange(String key,int start_ind,int end_ind)
    {
        List<String> result=new ArrayList<String>();
        try
        {
             result=jedis.lrange(key, start_ind, end_ind);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    public boolean lpop(String key)
    {
        if(jedis.llen(key)<=0)
            return false;
        jedis.rpop(key);
        return true;
    }
    public boolean insert_after_index(String key,String value,long index)
    {
        if((jedis.llen(key)>(index+1))||(index<0))
            return false;
        String pivot=jedis.lindex(key,index);
        BinaryClient.LIST_POSITION list_position= BinaryClient.LIST_POSITION.AFTER;
        jedis.linsert(key,list_position,pivot,value);
        return true;
    }

    public boolean delete_at_index(String key,int index)
    {
        if((jedis.llen(key)<(index+1))||(index<0))
            return false;
        edit_value(key,index,"ueirowfhskjsfjkjsfdjkjdflyyuu");
        String value=jedis.lindex(key,index);
        jedis.lrem(key,1,value);
        return true;
    }
    public boolean edit_value(String key,int index,String value)
    {
        if((jedis.llen(key)<(index+1))||(index<0))
            return false;
        jedis.lset(key,index,value);
        return true;
    }
    public boolean rpop(String key)
    {
        if(jedis.llen(key)<=0)
            return false;
        jedis.rpop(key);
        return true;
    }
    public long size(String key)
    {
        return jedis.llen(key);
    }
    public long del(String key)
    {
        return jedis.del(key);
    }

    public long setExpiry(String key, int exp)
    {
        return jedis.expire(key, exp);
    }
}
