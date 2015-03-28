/**
 * Created by asad on 3/9/14.
 */


$('#searchKey-input').bind("change paste keyup", function(){
    getSearchedKeys(current_address, $(this).val());
});

function getSearchedKeys(address, pattern)
{
    $.get('SearchController.do', {address:address, pattern: pattern},function(responseJson)
    {
        document.getElementById("keys").innerHTML = "";
        var i = 0;

        var $ul = $('<div class="list-group" id="keyList">').appendTo($('#keys'));

        $.each(responseJson, function(index, item)
        {   // Iterate over the JSON array.
            i = i+1;
            var key = "key" + i;

            $('<a href="#" class="list-group-item rangila-list" id=key onclick="loadKeyType(this)">').text(item).appendTo($ul);
        });
    });
}