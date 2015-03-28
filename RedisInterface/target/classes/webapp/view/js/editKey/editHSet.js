/**
 * Created by asad on 28/8/14.
 */

function editHSet()
{
    var f=document.getElementById('HSet-field').value;
    var v=document.getElementById('HSet-value').value;

    $.get('HSetController.do', {address:current_address, key:current_key, field:f, new_value:v, operation: "edit"},function(responseText)
    {
        document.getElementById('div-edit').innerHTML=responseText;
    });
    $('#myHSetModal').modal('hide');
    bootbox.alert("Edit Successful!");
}

function deleteHSet()
{
    var f=HSetField;

    $.get('HSetController.do', {address:current_address, key:current_key, field:f, operation: "field"},function(responseText)
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