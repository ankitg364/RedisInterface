/**
 * Created by asad on 28/8/14.
 */

function editList()
{
    var i=document.getElementById('List-index').value;
    var v=document.getElementById('List-value').value;

    $.get('ListController.do', {address:current_address, key:current_key, index:i, new_value:v, operation:"edit"},function(responseText)
    {
        document.getElementById('div-edit').innerHTML=responseText;
    });
    $('#myListModal').modal('hide');
    bootbox.alert("Edit Successful!");
}

function deleteList()
{
    var i=ListIndex;
    $.get('ListController.do', {address:current_address, key:current_key, index:i, operation: "index"},function(responseText)
    {
        bootbox.alert(responseText);

        if(current_key_type.localeCompare("string")=="0")
            $('#workspace').load('fragments/keyDetail/detailStringKey.jsp');
        else if(current_key_type.localeCompare("list")=="0")
            $('#workspace').load('fragments/keyDetail/detailListKey.jsp');
        else if(current_key_type.localeCompare("hash")=="0")
            $('#workspace').load('fragments/keyDetail/detailHsetKey.jsp');
        else if(current_key_type.localeCompare("set")=="0")
            $('#workspace').load('fragments/keyDetail/detailSetKey.jsp');
        else if(current_key_type.localeCompare("zset")=="0")
            $('#workspace').load('fragments/keyDetail/detailZsetKey.jsp');
        loadKeyDetails();

    });
}
