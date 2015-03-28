/**
 * Created by asad on 28/8/14.
 */

function editString()
{
    var v=document.getElementById('String-value').value;

    $.get('StringController.do', {address:current_address, key:current_key, new_value:v, operation: "edit"},function(responseText)
    {
        document.getElementById('div-edit').innerHTML=responseText;
    });

    $('#myStringModal').modal('hide');
    bootbox.alert("Edit Successful!");
}

function deleteString()
{
    $.get('StringController.do', {address:current_address, key:current_key, operation:"delete"},function(responseText)
    {
        bootbox.alert(responseText);
        $('#string_button').click();
    });
}
