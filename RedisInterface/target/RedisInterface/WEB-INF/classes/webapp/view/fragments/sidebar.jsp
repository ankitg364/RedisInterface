<%@ page import="Model.RedisInstances" %><%
    RedisInstances instances = (RedisInstances)request.getAttribute("Instances");
%>

<div id="sidebar-wrapper" style="background-color: lightgrey; border-right: 1px solid">
    <ul class="sidebar-nav">
        <li>
            <form method="POST" action="addInstance.do">
                <input type="text" class="form-control" placeholder="IP Address" name="ip" required>
                <input type="text" class="form-control" placeholder="Port Number" name="port" required>
                <button class="btn btn-default btn-block btn-custom" type="submit"><span class="glyphicon glyphicon-plus"></span> Add new Instance</button>
            </form>
        </li>

        <li class="sidebar-brand" style="background-color: firebrick">
            <h4 align="center" style="color: #ffffff;">
                <br>List of Redis Instances</h4>
        </li>

        <%
            for(int i=0; i<instances.getRedisInstances().size(); i++)
            {
                String ip = instances.getRedisInstances().get(i).getIpaddress() +
                        "/" + instances.getRedisInstances().get(i).getPortno();%>
        <li style="background-color: white">
            <% if(instances.getRedisInstances().get(i).isActive())
            {
            %><a class="instance" href="#" style="color: #000000; font-weight: bold;
                    border:1px solid #7f0000; border-width:1px 1px 1px 1px;">
                <%
                      }
                      else
                      {
                         %><a href="#" style="color: rosybrown; border:1px solid #7f0000; border-width:1px 1px 1px 1px;">
            <%
                }
            %>
            <%=ip%>
        </a>
        </li>
        <%
            }
        %>
    </ul>
</div>
