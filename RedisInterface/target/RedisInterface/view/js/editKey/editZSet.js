/**
 * Created by asad on 28/8/14.
 */

function editZSet()
{
    var o=ZSetValue;
    var v=document.getElementById('ZSet-score').value;
    $.get('ZSetController.do', {address:current_address, key:current_key,old_value:o, new_value:v, operation: "edit"},function(responseText)
    {
        document.getElementById('div-edit').innerHTML=responseText;
    });
    $('#myZSetModal').modal('hide');
    bootbox.alert("Edit Successful!");

}

function deleteZSet()
{
    var o=ZSetValue;
    $.get('ZSetController.do', {address:current_address, key:current_key, old_value: o, operation: "element"},function(responseText)
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