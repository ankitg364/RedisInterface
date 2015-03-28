/**
 * Created by asad on 3/9/14.
 */



// for listing out the keys for a particular type.
$('#keyListType > button.btn').click(function()
{

    $(this).addClass("active").siblings().removeClass("active");
    var type = this.innerHTML;
    var address = current_address;

    document.getElementById('workspace').innerHTML = "";
    getPaginatedKeys(address, type);
    getInfoTable();
});


function goNextPage()
{
    // console.log(prevIndexList);

    var address = current_address;
    var type = $('#keyListType > .btn.active').html();

    if(type == "All") {
        if (nextIndex[currIndex].localeCompare("0") != "0")
            currIndex = nextIndex[currIndex];
    }
    else if(type == "String") {
        if (nextIndexString[currIndexString].localeCompare("0") != "0")
            currIndexString = nextIndexString[currIndexString];
    }
    else if(type == "List") {
        if (nextIndexList[currIndexList].localeCompare("0") != "0")
            currIndexList = nextIndexList[currIndexList];
    }
    else if(type == "Set") {
        if (nextIndexSet[currIndexSet].localeCompare("0") != "0")
            currIndexSet = nextIndexSet[currIndexSet];
    }
    else if(type == "ZSet") {
        if (nextIndexZSet[currIndexZSet].localeCompare("0") != "0")
            currIndexZSet = nextIndexZSet[currIndexZSet];
    }
    else if(type == "Hash") {
        if (nextIndexHash[currIndexHash].localeCompare("0") != "0")
            currIndexHash = nextIndexHash[currIndexHash];
    }

    getPaginatedKeys(address, type);
}

function goPrevPage()
{
    var address = current_address;
    var type = $('#keyListType > .btn.active').html();

    if(type == "All") {
        if (currIndex.localeCompare("0") != "0")
            currIndex = prevIndex[currIndex];
    }
    else if(type == "String") {
        if (currIndexString.localeCompare("0") != "0")
            currIndexString = prevIndexString[currIndexString];
    }
    else if(type == "List") {
        if(currIndexList.localeCompare("0") != "0")
            currIndexList = prevIndexList[currIndexList];
    }
    else if(type == "Set") {
        if (currIndexSet.localeCompare("0") != "0")
            currIndexSet = prevIndexSet[currIndexSet];
    }
    else if(type == "ZSet") {
        if (currIndexZSet.localeCompare("0") != "0")
            currIndexZSet = prevIndexZSet[currIndexZSet];
    }
    else if(type == "Hash") {
        if (currIndexHash.localeCompare("0") != "0")
            currIndexHash = prevIndexHash[currIndexHash];
    }

    getPaginatedKeys(address, type);
}


function getPaginatedKeys(address, type)
{
    var index;
    if(type == "All")
        index = currIndex;
    else if(type == "String")
        index = currIndexString;
    else if(type == "List")
        index = currIndexList;
    else if(type == "Set")
        index = currIndexSet;
    else if(type == "ZSet")
        index = currIndexZSet;
    else if(type == "Hash")
        index = currIndexHash;

    $.get('KeysController.do', {address:address, type:type, currIndex: index},function(responseJson)
    {
        document.getElementById("keys").innerHTML = "";
        var i = 0;
        var len = 0;

        $.each(responseJson, function(index, item)
        { len++; });

        var $ul = $('<div class="list-group" id="keyList">').appendTo($('#keys'));

        $.each(responseJson, function(index, item)
        {   // Iterate over the JSON array.
            i = i+1;
            var key = "key" + i;

            if(i != len) {
                $('<a href="#" class="list-group-item rangila-list" id=key onclick="loadKeyType(this)">').text(item).appendTo($ul);
            }
            else
            {
                if(type == "All")
                {
                    nextIndex[currIndex] = item;
                    prevIndex[item] = currIndex;
                }
                else if(type == "String")
                {
                    nextIndexString[currIndexString] = item;
                    prevIndexString[item] = currIndexString;
                }
                else if(type == "List")
                {
                    nextIndexList[currIndexList] = item;
                    prevIndexList[item] = currIndexList;
                }
                else if(type == "Set")
                {
                    nextIndexSet[currIndexSet] = item;
                    prevIndexSet[item] = currIndexSet;
                }
                else if(type == "Hash")
                {
                    nextIndexHash[currIndexHash] = item;
                    prevIndexHash[item]= currIndexHash;
                }
                else if(type == "ZSet")
                {
                    nextIndexZSet[currIndexZSet] = item;
                    prevIndexZSet[item] = currIndexZSet;
                }
            }
        });
    });
}