/**
 * Created by asad on 5/9/14.
 */

$('document').ready(function(){

    $.get('TreeViewController.do', {address:current_address},function(responseJson)
    {
        $('#html1').jstree({'core': {'data' : responseJson}});
        $(function () {
            $('#html1').on('changed.jstree', function (e, data){
                var i, j, r = [];
                for(i = 0, j = data.selected.length; i < j; i++) {
                    r.push(data.instance.get_node(data.selected[i]).id);
                }
                var key_name=r.join(',');
                key_name=key_name.trim();
                loadDetailsInWorkspace(key_name);

            })
            .jstree();
        });
    });
});

