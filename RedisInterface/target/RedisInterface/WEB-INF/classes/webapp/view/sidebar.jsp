<%@ page import="Model.RedisInstances" %><%
    RedisInstances instances = (RedisInstances)request.getAttribute("Instances");
%>
<div id="sidebar-wrapper" style="background-color: #ffffff; border-right: 1px solid">
    <ul class="sidebar-nav">
        <li>
            <form method="POST" action="addInstance.do">
                <input type="text" class="form-control" placeholder="IP Address" name="ip">
                <input type="text" class="form-control" placeholder="Port Number" name="port">
                <button class="btn btn-default btn-block" type="submit"><span class="glyphicon glyphicon-plus"></span> Add new Instance</button>
            </form>
        </li>

        <li class="sidebar-brand">
            <h4 align="center"><br>List of Redis Instances</h4>
        </li>

        <%
            for(int i=0; i<instances.getRedisInstances().size(); i++)
            {
                String ip = instances.getRedisInstances().get(i).getIpaddress() +
                        "/" + instances.getRedisInstances().get(i).getPortno();%>
        <li>
            <% if(instances.getRedisInstances().get(i).isActive())
            {
            %><a class="instance" href="#" style="color: olive">
                <%
                      }
                      else
                      {
                         %><a href="#" style="color: red">
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
