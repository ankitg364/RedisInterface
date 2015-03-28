package DBRedis;

/**
 * Created by asad on 10/9/14.
 */
public class Nodes
{
    public String id;
    public String parent;
    public String text;
    public Nodes(String id,String text,String parent)
    {
        this.id=id;
        this.parent=parent;
        this.text=text;
    }
}
