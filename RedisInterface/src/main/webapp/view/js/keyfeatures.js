function loadKeyType(caller)
{
    var address = current_address;
    var key = caller.innerHTML;
    current_key=key;
    document.getElementById('workspace').innerHTML="";
    $.get('KeyType.do', {address:address, key:key},function(responseText) {
        var type=responseText;
        current_key_type=type;

        if(type.localeCompare("string")=="0")
            $('#workspace').load('fragments/keyDetail/detailStringKey.jsp');
        else if(type.localeCompare("list")=="0")
            $('#workspace').load('fragments/keyDetail/detailListKey.jsp');
        else if(type.localeCompare("hash")=="0")
            $('#workspace').load('fragments/keyDetail/detailHsetKey.jsp');
        else if(type.localeCompare("set")=="0")
            $('#workspace').load('fragments/keyDetail/detailSetKey.jsp');
        else if(type.localeCompare("zset")=="0")
            $('#workspace').load('fragments/keyDetail/detailZsetKey.jsp');
        loadKeyDetails();
    });
}

function loadKeyDetails()
{
    var address = current_address;
    var key = current_key;
    var type = current_key_type;
    $.get('KeyDetails.do', {address:address, key:key,type:type},function(responseJson)
    {
        var $table = document.getElementById('detailsTable')
        if(type=='set')
        {
            $.each(responseJson, function(key, value)
            {
                $('<tr>').appendTo($table)
                    .append($('<td>').text(value))
                //.append($('<td><button onclick="edit_row(this)">Edit</button></td>'))
            });
        }
        else
        {
            $.each(responseJson, function(key, value)
            {
                $('<tr>').appendTo($table)
                    .append($('<td>').text(key))
                    .append($('<td>').text(value))
                //.append($('<td><button onclick="edit_row(this)">Edit</button></td>'))
            });
        }
    });
}

function loadAddKey()
{
    document.getElementById('workspace').innerHTML="";
    $('#workspace').load('fragments/addKey.jsp');
}

function edit_row(el)
{
    if(current_key_type=='string')
    {
        var key = $(el).closest("tr").find('td:eq(0)').text();
        var value = $(el).closest("tr").find('td:eq(1)').text();
        var temp = document.getElementsByName("opt_addNewKey");
    }
    else if(current_key_type=='list')
    {
        var key=current_key;
        var index = $(el).closest("tr").find('td:eq(0)').text();
        var value = $(el).closest("tr").find('td:eq(1)').text();
    }
    else if(current_key_type=='hash')
    {
        var key=current_key;
        var field = $(el).closest("tr").find('td:eq(0)').text();
        var value = $(el).closest("tr").find('td:eq(1)').text();
    }
    else if(current_address=='set')
    {
        var key=current_key;
        var member = $(el).closest("tr").find('td:eq(0)').text();
    }
    else
    {
        var key=current_key;
        var score = $(el).closest("tr").find('td:eq(0)').text();
        var value = $(el).closest("tr").find('td:eq(1)').text();
    }
}