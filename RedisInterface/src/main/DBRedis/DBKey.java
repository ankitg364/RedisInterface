package DBRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by ankit on 25/8/14.
 */
public class DBKey{
    String  ipaddress;
    int portno;
    JedisPool jedisPool;
    Jedis jedis;
    public DBKey(String ipaddress,int portno)
    {
        this.ipaddress=ipaddress;
        this.portno=portno;
        try
        {
            jedisPool=new JedisPool(ipaddress,portno);
            jedis=jedisPool.getResource();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public String type(String key)
    {
        return jedis.type(key);
    }
    public long ttl(String key)
    {
        return jedis.ttl(key);
    }
    public void returnResource()
    {
        jedis.disconnect();
        jedisPool.returnResource(jedis);
    }
}
