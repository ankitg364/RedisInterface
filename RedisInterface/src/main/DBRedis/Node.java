package DBRedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit on 2/5/14.
 */
public class Node {
    public String text;
    public String parent;
    public String id;
    public List<Node> list=new ArrayList<Node>();
    public Node(String id, String text,String parent)
    {
        this.id=id;
        this.text=text;
        this.parent=parent;
    }
}
