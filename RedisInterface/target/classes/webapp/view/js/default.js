/**
 * Created by asad on 21/8/14.
 */
var cursor;
var current_address;
var current_key;
var current_key_type;

var HSetField;
var HSetValue;
var SetValue;
var ListIndex;
var ListValue;
var StringKey;
var StringValue;
var ZSetScore;
var ZSetValue;

$("#menu-toggle").click(function(e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
    });




/**  For listing out the keys for a Redis Instance*/

window.addEventListener('beforeunload', function() {
    $.get("ConnectClientController.do",{connect:"0"}, function(){

    });
});

$(".instance").click(function (event) {

    document.getElementById('instance_name_display').innerHTML = this.innerHTML;
    document.getElementById('workspace').innerHTML = "";
    document.getElementById('addAnotherKey').style.visibility = "visible";
    document.getElementById('startMonitorMode').style.visibility="visible";
    document.getElementById('second-sidebar').style.visibility="visible";
    document.getElementById('menu-toggle').style.visibility="visible";

    var address = this.innerHTML;
    current_address = address;

    $.get("ConnectClientController.do",{address:current_address,connect:"1"},function(){});
    $("#paginatedView").click();
});


$('#taskDisplayButtons > button.btn').click(function(){
    $(this).addClass("active").siblings().removeClass("active");
    var type = this.innerHTML;

    if(type.localeCompare("Default")=="0")
    {
        $('#taskDisplay').load('fragments/taskDisplay/paginatedView.jsp', function()
        {
            document.getElementById('div-pager').style.visibility = "visible";
            document.getElementById('listofKeys').style.visibility = "visible";
            resetIndexPointers();
            $('#all_button').click();
        });

    }
    else if(type.localeCompare("Search Key")=="0")
    {
        document.getElementById('workspace').innerHTML="";
        getInfoTable();
        $('#taskDisplay').load('fragments/taskDisplay/searchKey.jsp');
    }
    else
    {
        document.getElementById('workspace').innerHTML="";
        getInfoTable();
        $('#taskDisplay').load('fragments/taskDisplay/treeView.jsp');
    }
});


//------------------------------------------------------------------------------------

function loadKeyType(caller)
{
    var key = caller.innerHTML;

    $(caller).addClass("selected_highlight").siblings().removeClass("selected_highlight");
    loadDetailsInWorkspace(key);
}

function loadDetailsInWorkspace(key)
{
    current_key=key;
    document.getElementById('workspace').innerHTML="";

    $.get('KeyType.do', {address:current_address, key:key},function(responseText) {
        var type=responseText;
        current_key_type=type;

        if(type.localeCompare("string")=="0")
            $('#workspace').load('fragments/keyDetail/detailStringKey.jsp', function(){
                loadKeyDetails();
            });
        else if(type.localeCompare("list")=="0")
            $('#workspace').load('fragments/keyDetail/detailListKey.jsp', function(){
                loadKeyDetails();
            });
        else if(type.localeCompare("hash")=="0")
            $('#workspace').load('fragments/keyDetail/detailHsetKey.jsp', function(){
                loadKeyDetails();
            });
        else if(type.localeCompare("set")=="0")
            $('#workspace').load('fragments/keyDetail/detailSetKey.jsp', function(){
                loadKeyDetails();
            });
        else if(type.localeCompare("zset")=="0")
            $('#workspace').load('fragments/keyDetail/detailZsetKey.jsp', function(){
                loadKeyDetails();
            });
    });
}

function loadKeyDetails()
{
    var address = current_address;
    var key = current_key;
    var type = current_key_type;

    $.get('KeyDetails.do', {address:address, key:key,type:type},function(responseJson)
    {
        var $table = document.getElementById('detailsTable');
        var $table2= document.getElementById('basic_info');
        if(type=='set')
        {
            $.each(responseJson, function(key, value)
            {
                if(key=="size")
                {
                    $('<tr>').appendTo($table2)
                        .append($('<td><b>Size</b></td>'))
                        .append($('<td>').text(value))
                }
                else if(key=="ttl")
                {
                    $('<tr>').appendTo($table2)
                        .append($('<td><b>Time to Live</b></td>'))
                        .append($('<td>').text(value))
                }
                else {
                    $('<tr>').appendTo($table)
                        .append($('<td>').text(value))
                        .append($('<td><a onclick="edit_row(this)" style="cursor: pointer">Edit</a></td>'))
                        .append($('<td><a onclick="delete_row(this)" style="cursor: pointer">Remove</a></td>'))
                }
            });
        }
        else
        {
            $.each(responseJson, function (key, value)
            {
                if(key=="size")
                {
                    $('<tr>').appendTo($table2)
                        .append($('<td><b>Size</b></td>'))
                        .append($('<td>').text(value))
                }
                else if(key=="ttl")
                {
                    $('<tr>').appendTo($table2)
                        .append($('<td><b>Time to Live</b></td>'))
                        .append($('<td>').text(value))
                }
                else {
                    $('<tr>').appendTo($table)
                        .append($('<td>').text(key))
                        .append($('<td>').text(value))
                        .append($('<td><a onclick="edit_row(this)" style="cursor:pointer;">Edit</a></td>'))
                        .append($('<td><a onclick="delete_row(this)" style="cursor: pointer">Remove</a></td>'))
                }
            });
        }
    });
}

function loadAddKey()
{
    document.getElementById('workspace').innerHTML="";
    $('#workspace').load('fragments/addKey.jsp');
}

function loadMonitor()
{
    $.get('SetCurrentAddressController.do', {address:current_address},function(responseJson)
    {

    });

    var win = window.open("monitorPage.jsp", '_blank');
    win.focus();
}

function edit_row(el)
{
    if(current_key_type.localeCompare("string")=="0")
    {
        StringKey = $(el).closest("tr").find('td:eq(0)').text();
        StringValue = $(el).closest("tr").find('td:eq(1)').text();

        $('#String-key').val(StringKey);
        $('#String-value').val(StringValue);

        $('#myStringModal').modal();
    }
    else if(current_key_type.localeCompare("list")=="0")
    {
        ListIndex = $(el).closest("tr").find('td:eq(0)').text();
        ListValue = $(el).closest("tr").find('td:eq(1)').text();

        $('#List-index').val(ListIndex);
        $('#List-value').val(ListValue);

        $('#myListModal').modal();
    }
    else if(current_key_type.localeCompare("hash")=="0")
    {
        HSetField = $(el).closest("tr").find('td:eq(0)').text();
        HSetValue = $(el).closest("tr").find('td:eq(1)').text();

        $('#HSet-field').val(HSetField);
        $('#HSet-value').val(HSetValue);

        $('#myHSetModal').modal();

    }
    else if(current_key_type.localeCompare("set")=="0")
    {
        SetValue = $(el).closest("tr").find('td:eq(0)').text();

        $('#Set-value').val(SetValue);

        $('#mySetModal').modal();

    }
    else
    {
        ZSetScore = $(el).closest("tr").find('td:eq(1)').text();
        ZSetValue = $(el).closest("tr").find('td:eq(0)').text();

        $('#ZSet-score').val(ZSetScore);
        $('#ZSet-value').val(ZSetValue);

        $('#myZSetModal').modal();
    }
}

function delete_row(el)
{
    if(current_key_type.localeCompare("string")=="0")
    {
        StringKey = $(el).closest("tr").find('td:eq(0)').text();
        StringValue = $(el).closest("tr").find('td:eq(1)').text();

        bootbox.confirm("The Entity will be deleted permanently. Do you want to continue?", function(result) {
            if (result) {
                deleteString();
            }
        });
    }
    else if(current_key_type.localeCompare("list")=="0")
    {
        ListIndex = $(el).closest("tr").find('td:eq(0)').text();
        ListValue = $(el).closest("tr").find('td:eq(1)').text();

        bootbox.confirm("The Entity will be deleted permanently. Do you want to continue?", function(result) {
            if (result) {
                deleteList();
            }
        });
    }
    else if(current_key_type.localeCompare("hash")=="0")
    {
        HSetField = $(el).closest("tr").find('td:eq(0)').text();
        HSetValue = $(el).closest("tr").find('td:eq(1)').text();

        bootbox.confirm("The Entity will be deleted permanently. Do you want to continue?", function(result) {
            if (result) {
                deleteHSet();
            }
        });
    }
    else if(current_key_type.localeCompare("set")=="0")
    {
        SetValue = $(el).closest("tr").find('td:eq(0)').text();

        bootbox.confirm("The Entity will be deleted permanently. Do you want to continue?", function(result) {
            if (result) {
                deleteSet();
            }
        });
    }
    else
    {
        ZSetScore = $(el).closest("tr").find('td:eq(1)').text();
        ZSetValue = $(el).closest("tr").find('td:eq(0)').text();

        bootbox.confirm("The Entity will be deleted permanently. Do you want to continue?", function(result) {
            if (result) {
                deleteZSet();
            }
        });
    }
}


function delete_key()
{
    bootbox.confirm("The Entity will be deleted permanently. Do you want to continue?", function(result) {
        if(result)
        {
            $.get('ListController.do', {address:current_address, key:current_key, operation: "delete"},function(responseText)
            {
                $('#all_button').click();
                $('#workspace').innerHTML = responseText;
            });
        }
    });
}

function setTTL()
{
    bootbox.prompt("Enter the TTL to be Set", function(result) {
        if (result === null) {}
        else
        {
            $.get('ListController.do', {address:current_address, key:current_key, operation: "setExpiry", expiry: result},function(responseText)
            {
                loadDetailsInWorkspace(current_key);
            });
        }
    });
}
function getInfoTable()
{

    $.get('InfoController.do', {address:current_address},function(responseJson)
    {
        var $table=$('<table class="table">').appendTo($('#workspace'));
        $('<tr>').appendTo($table)
            .append($('<td style="font-weight: bold">').text("Name"))
            .append($('<td style="font-weight: bold">').text("Value"))

        $.each(responseJson, function(index, item)
        {
            $('<tr>').appendTo($table)
                .append($('<td>').text(index))
                .append($('<td>').text(item))
        });
    });
}