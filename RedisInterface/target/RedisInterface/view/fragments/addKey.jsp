<%--
  Created by IntelliJ IDEA.
  User: asad
  Date: 21/8/14
  Time: 5:50 PM
  To change this template use File | Settings | File Templates.
--%>

<script src="js/addKey.js"></script>
<script src="/js/jqBootstrapValidation.js"></script>

<div class="col-lg-12">
    <h1 style="font-family: 'Comic Sans MS' ">Add Key</h1>

    <select class="form-control" name="select_addNewKey" id="select_addNewKey"
            style="cursor: hand; font-size: 16; font-family: 'Comic Sans MS'; vertical-align: middle ">
        <option value="String">String</option>
        <option value="HSet">HSet</option>
        <option value="ZSet">ZSet</option>
        <option value="Set">Set</option>
        <option value="List">List</option>
    </select>

    <br>
    <div class="col-lg-6">
        <input type="text" class="form-control" placeholder="Key" name="key_addNewKey" id="key_addNewKey"
               style="font-size: 20; font-family: 'Comic Sans MS' " required>
    </div>
    <div class="col-lg-6">
        <input type="text" class="form-control" placeholder="" name="opt_addNewKey" id="opt_addNewKey"
               style="font-size: 20; font-family: 'Comic Sans MS';  visibility: hidden">
    </div>
    <br><br><br>
    <div class="col-lg-12">
        <input type="text" class="form-control" placeholder="Expiry in Seconds; -1 for Never Expire" name="opt_expiry" id="opt_expiry"
               style="font-size: 20; font-family: 'Comic Sans MS' ">
    </div>
    <br><br>
    <input type="text" class="form-control" placeholder="Expiry in Seconds; -1 for Never Expire" name="opt_expiry" id="opt_expiry"
           style="font-size: 20; font-family: 'Comic Sans MS' ">
    <textarea rows="8" class="form-control" placeholder="Value" name="value_addNewKey" id="value_addNewKey"
              style="font-size: 20; font-family: 'Comic Sans MS' " required></textarea>
    <br>
    <button class="btn btn-default" onclick="set_ip_port()"
            style="font-family: 'Comic Sans MS' ">Add Key</button>

</div>