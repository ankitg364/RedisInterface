package Model;

import java.io.IOException;

/**
 * Created by asad on 26/8/14.
 */

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.ScanParams;
import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;

public class PutContent
{
    String  ipaddress;
    int portno;
    JedisPool jedisPool;
    Jedis jedis;
    public PutContent(String ipaddress,int portno)
    {
        this.ipaddress=ipaddress;
        this.portno=portno;
    }
    public void setJedis()
    {
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
    public void returnResource()
    {
        jedis.disconnect();
        jedisPool.returnResource(jedis);
    }
    void addStringContent()
    {
        String key ="key";
        String value ="value";

        for(int i=0; i<1000; i++) {
            String k = key + Integer.toString(i+1);
            String v = value +Integer.toString(i+1);
            jedis.set(k, v);
        }
    }
    void deleteString()
    {

        String key ="key";
        String value ="value";

        for(int i=0; i<1000; i++) {
            String k = key + Integer.toString(i+1);
            String v = value +Integer.toString(i+1);
            jedis.del(k);
        }
    }
    void addListContent()
    {
        String key = "list";
        String value ="value";

        for(int i=0; i<50; i++)
        {
            String k = key + Integer.toString(i+1);
            for(int j=0; j<20; j++) {
                String v = k + " "+value +Integer.toString(j+1);
                jedis.lpush(k, v);
            }
        }
    }
    void deleteList()
    {
        String key = "list";

        for(int i=0; i<50; i++)
        {
            String k = key + Integer.toString(i+1);
            jedis.del(k);
        }
    }

    void addSetContent()
    {
        String key = "set";
        String value ="value";

        for(int i=0; i<100; i++)
        {
            String k = key + Integer.toString(i+1);
            for(int j=0; j<50; j++) {
                String v = k + " "+value +Integer.toString(j+1);
                jedis.sadd(k, v);
            }
        }
    }

    void deleteSet()
    {
        String key = "set";

        for(int i=0; i<100; i++)
        {
            String k = key + Integer.toString(i+1);
            jedis.del(k);
        }
    }

    void addZSetContent()
    {
        String key = "zset";
        String value ="value";

        for(int i=0; i<100; i++)
        {
            String k = key + Integer.toString(i+1);
            for(int j=0; j<50; j++) {
                String v = k + " "+value +Integer.toString(j+1);
                jedis.zadd(k, j + 1, v);
            }
        }
    }

    void deleteZSet()
    {
        String key = "zset";

        for(int i=0; i<100; i++)
        {
            String k = key + Integer.toString(i+1);
            jedis.del(k);
        }
    }

    void addHashContent()
    {
        String key = "hash";
        String value ="value";

        for(int i=0; i<100; i++)
        {
            String k = key + Integer.toString(i+1);
            Map<String,String> map=new LinkedHashMap<String, String>();

            for(int j=0; j<50; j++) {
                String field = "field:"+key + Integer.toString(j+1);
                String v = k + " "+value +Integer.toString(j+1);

                map.put(field, v);
            }
            jedis.hmset(k, map);
        }
    }
    void deleteHash()
    {
        String key = "hash";

        for(int i=0; i<100; i++)
        {
            String k = key + Integer.toString(i+1);
            jedis.del(k);
        }
    }
    public static void main(String arg[]) throws IOException
    {
        PutContent p = new PutContent("127.0.0.1", 6379);
        p.setJedis();

        //p.addStringContent();
        p.addListContent();

        p.addSetContent();
        //p.addHashContent();
        p.returnResource();
    }
}
