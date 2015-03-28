/**
 * Created by asad on 21/8/14.
 */

$('[name="select_addNewKey"]').change(function() {

    var v = document.getElementById("select_addNewKey");
    var str = v.options[v.selectedIndex].value;
    var temp = document.getElementsByName("opt_addNewKey")[0];
    var expiry = document.getElementsByName("opt_expiry")[0];

    if( str == "HSet")
    {

        temp.placeholder = 'Hash Key';
        temp.style.visibility = "visible";
        temp.required="true";

        expiry.style.visibility= "hidden";
    }
    else if( str == "ZSet")
    {
        temp.placeholder = 'Index';
        temp.style.visibility = "visible";
        temp.required="true";

        expiry.style.visibility= "hidden";
    }
    else if( str == "List")
    {
        temp.placeholder = '0 to append, -1 to prepend';
        temp.style.visibility = "visible";
        temp.required="true";

        expiry.style.visibility= "hidden";
    }
    else if(str == "Set")
    {
        temp.required="false";
        temp.style.visibility = "hidden";

        expiry.style.visibility= "hidden";
    }
    else
    {
        temp.required="false";
        temp.style.visibility = "hidden";

        expiry.style.visibility= "visible";
    }
});

function set_ip_port()
{
    var type=document.getElementById("select_addNewKey").value;
    var key = document.getElementById("key_addNewKey").value;
    var opt =document.getElementById("opt_addNewKey").value;
    var value =document.getElementById("value_addNewKey").value;
    var expiry = document.getElementById("opt_expiry").value;

    var address = current_address;
    $.get('addNewKey.do', {address:address, type:type, key:key, opt:opt, value:value, expiry: expiry},function(responseText)
    {
        document.getElementById('workspace').innerHTML=responseText;
    });
}