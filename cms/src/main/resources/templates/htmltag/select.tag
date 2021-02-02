<% if (isNotEmpty(_escape) && _escape == "true") {%>
&lt;script&gt;
<% } else {%>
<script>
    <% }%>
    $(document).ready(function () {
        var configAjax = {
            <% if( isNotEmpty(_provider) ) {%>
            type: "post",
            url: '/provider/getLookupList.action',
            data: {
                provider: '${_provider}',
                <% if( isNotEmpty(_queryParams) ) {%>
                queryParams: '${_queryParams!}'
                <% } %>
            },
            <% } %>
            dataType: "json",
            async: true,
            success: function (result) {
                let data;
                if (result instanceof Array) {
                    data = result;
                } else if (typeof (result) == 'object') {
                    if (result.success) {
                        data = result.data;
                    } else {
                        bs4pop.alert(result.message, {type: 'error'});
                        return;
                    }
                }

                let _selectIndex = 0;
                <% if( isNotEmpty(_selectIndex) ) {%>
                _selectIndex =${_selectIndex};
                <% } %>
                $.map(data, function (dataItem, index) {
                    const selected = index == _selectIndex;
                    var option = new Option(dataItem["${_textField!'text'}"], index == 0 ? dataItem["value"] : dataItem["${_valueField!'value'}"], false, selected);
                    $('#${_id}').append(option);
                });
                <% if( isNotEmpty(_callbackFunc) ) {%>
                ${_callbackFunc}
                <% } %>
            }
        }
        $.ajax(configAjax);
        $("#fileType").on("refreshSelect", function () {
            $("#fileType").empty();
            $.ajax(configAjax);
        });
    })
    <% if(isNotEmpty(_escape) && _escape == "true") {%>
    &lt; /script&gt;
    <% }else {%>
</script>
<% }%>