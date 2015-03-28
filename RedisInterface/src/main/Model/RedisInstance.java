package Model;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by ankit on 14/8/14.
 */
public class RedisInstance
{
    private int portno;
    private String ipaddress;
    private boolean active=false;

    public RedisInstance()
    {
        //donothing
    }

    public RedisInstance(String ipaddress,int portno)
    {
        this.ipaddress=ipaddress;
        this.portno=portno;
    }

    public boolean isActive()
    {
        return active;
    }

    public String getIpaddress()
    {
        return ipaddress;
    }

    public int getPortno()
    {
        return portno;
    }

    public void setIpaddress(String ipaddress)
    {
        this.ipaddress=ipaddress;
    }

    public void setPortno(int portno)
    {
        this.portno=portno;
    }

    public void setActive() {
        try {
            JedisPool jedisPool;
            jedisPool = new JedisPool(ipaddress, portno);
            Jedis jedis = jedisPool.getResource();
            jedis.disconnect();
            jedisPool.returnResource(jedis);
            active = true;
        } catch (Exception e) {
            active = false;
        }
    }
}
