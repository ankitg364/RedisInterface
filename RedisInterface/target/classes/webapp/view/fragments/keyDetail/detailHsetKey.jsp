<%--
  Created by IntelliJ IDEA.
  User: asad
  Date: 22/8/14
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>


<div class="col-lg-12" style="padding-bottom: 14px; padding-top: 12px; border: solid; border-width: 1px;
        background-color: #FAFFFF">

    <jsp:include page="headerDetail.jsp" flush="true"/>

    <div class="col-lg-12">
        <div class="col-lg-4">
            <table class="table table-hover" style="height: 150px; background-color: #ffffff" id="basic_info">
                <tr>
                    <th>Type</th>
                    <td>HSet</td>
                </tr>

            </table>

            <div class="col-lg-10">
                <button class="btn btn-default btn-block" style=" border: solid; border-width: 2px; margin-bottom: 10px"
                        onclick="setTTL()">Set Expiry</button>

                <button class="btn btn-default btn-block" style=" border: solid; border-width: 2px; margin-bottom: 20px"
                        onclick="delete_key()">Delete this key !</button>
            </div>
        </div>


        <div class="col-lg-8" style="overflow-y: auto;  height: 85%" id="Details">
            <table class="CSSTableGenerator" id="detailsTable">
                <tr>
                    <td>Field</td>
                    <td>Value</td>
                    <td>Edit</td>
                    <td>Remove</td>
                </tr>
            </table>
        </div>
    </div>
</div>