<%--
  Created by IntelliJ IDEA.
  User: asad
  Date: 3/9/14
  Time: 1:56 PM
  To change this template use File | Settings | File Templates.
--%>

<div id="listofKeys" style="visibility: hidden"><span class="glyphicon glyphicon-th"></span>
    <b><u> List of Keys</u></b>
    <div class="btn-group btn-group-xs" id="keyListType">
        <button type="button" class="btn btn-default rangila-button" id="all_button">All</button>
        <button type="button" class="btn btn-default rangila-button" id="string_button">String</button>
        <button type="button" class="btn btn-default rangila-button" id="list_button">List</button>
        <button type="button" class="btn btn-default rangila-button" id="set_button">Set</button>
        <button type="button" class="btn btn-default rangila-button" id="zset_button">ZSet</button>
        <button type="button" class="btn btn-default rangila-button" id="hash_button">Hash</button>
    </div>
</div>
<!-- Division to list the keys -->
<div  style="overflow: auto; height: 63%" id="keys"></div>

<!-- Division for the pagination button -->
<div style="visibility: hidden" id="div-pager">
    <ul class="pager">
        <li class="previous"><a href="#" onclick="goPrevPage()"
                                class="rangila-button">&larr; Prev</a></li>
        <li class="next"><a href="#" onclick="goNextPage()"
                            class="rangila-button">Next &rarr;</a></li>
    </ul>
</div>

<script src="js/taskDisplay/paginatedView.js"></script>