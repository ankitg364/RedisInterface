package DBRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;
import redis.clients.jedis.ScanParams;
import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;

/**
 * Created by ankit on 21/8/14.
 */
public class GetKeys
{
    String  ipaddress;
    int portno;
    JedisPool jedisPool;
    Jedis jedis;
    public GetKeys(String ipaddress,int portno)
    {
        this.ipaddress=ipaddress;
        this.portno=portno;
    }
    public void setJedis() {
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
    public List<String> getAllKeys(String index)
    {
        return getKeysByType("", index);
    }
    public List<String> getKeysByString(String index)
    {
        return getKeysByType("string", index);
    }
    public List<String> getKeysByList(String index)
    {
        return getKeysByType("list", index);
    }
    public List<String> getKeysBySet(String index)
    {
        return getKeysByType("set", index);
    }

    public List<String> getKeysByHash(String index)
    {
        return getKeysByType("hash", index);
    }

    public List<String> getKeysByZSet(String index)
    {
        return getKeysByType("zset", index);
    }

    public List<String> getKeysByType(String reqtype, String index)
    {
        List<String> keys=new ArrayList<String>();

        String start = index;
        boolean scanningDone = false;
        ScanParams params = new ScanParams();

        while( !scanningDone && keys.size()<15 )
        {
            ScanResult<String> scanResults = jedis.scan(start, params);
            for (String eachScanResult : scanResults.getResult())
            {
                String type=jedis.type(eachScanResult);

                if(reqtype.equals(""))
                    keys.add(eachScanResult);
                else {
                    if (type.equalsIgnoreCase(reqtype))
                        keys.add(eachScanResult);
                }
            }
            start = scanResults.getStringCursor();

            if (start.equalsIgnoreCase("0"))
                scanningDone = true;

        }
        keys.add(start);

        return  keys;
    }
    public void returnResource()
    {
        jedis.disconnect();
        jedisPool.returnResource(jedis);
    }
}
