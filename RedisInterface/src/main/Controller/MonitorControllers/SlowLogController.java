package Controller.MonitorControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.messaging.saaj.util.FinalArrayList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Slowlog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ankit on 15/9/14.
 */
public class SlowLogController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess=req.getSession();
        String ipAddress= (String)sess.getAttribute("CurrentIP");
        String portNo=(String)sess.getAttribute("CurrentPort");

        JedisPool jedisPool=new JedisPool(ipAddress,Integer.parseInt(portNo));
        Jedis jedis=jedisPool.getResource();
        List<Slowlog> list= jedis.slowlogGet();

        List<Slowlogs> slowlogsList=new ArrayList<Slowlogs>();

        for (Slowlog s: list)
        {
            Slowlogs slowlogs=new Slowlogs();
            long unixTime= s.getTimeStamp();
            Date date=new Date(unixTime*1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, h:mm:ss a");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

            String formattedDate = sdf.format(date);
            slowlogs.timestamp=formattedDate;
            long execution_time=s.getExecutionTime();

            slowlogs.execution_time=Long.toString(execution_time);
            List<String> stringList= s.getArgs();
            String command="";
            int i=0;
            for (String string: stringList)
            {
                command=command+string;
                command=command+" ";
                i++;
            }
            int len=command.length();

            slowlogs.command=command;
            slowlogsList.add(slowlogs);

            //System.out.println(slowlogs.command+" "+ slowlogs.execution_time+" "+slowlogs.timestamp);
        }
        jedis.disconnect();
        String json = new Gson().toJson(slowlogsList);
        //System.out.println(json);


        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}