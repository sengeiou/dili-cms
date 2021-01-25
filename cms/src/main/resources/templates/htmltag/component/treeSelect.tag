<head>
    <link rel="stylesheet" href="/resources/css/zTree/metroStyle.css">
    <link rel="stylesheet" href="/resources/css/zTree/treeSelectStyle.css">
</head>
<script src="/resources/js/jquery.ztree.all.js"></script>
<script src="/resources/js/treeSelect.2.0.js"></script>
<div id="${_id!}" style="width: 300px;overflow:hidden;cursor: pointer" checks="${_checks!}"
     readonly>
</div>
<script type="text/javascript">
    var $treeData = {
        // 父页面挂在子页面的元素id
        id: "#${_id!}",
        //组件的id标识
        el: "#${_el!}",
        // 放结果选择结果的参数名
        resultData: ""
    };
    // 树的数据
    let zNodes = [];
    <% if( isNotEmpty(_zNodes) ) {%>
    zNodes =${_zNodes};
    <% } %>
    // 数的数据规则
    let dataOptions = {
        key: {
            name: "name",
            isParent: "isParent"
        },
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: 0
        }
    };
    <% if( isNotEmpty(_dataOptions) ) {%>
    dataOptions =${_dataOptions};
    <% } %>
    var defaults = {
        zNodes: zNodes,
        height: 233,
        filter: false,
        searchShowParent: false,
        data: dataOptions,
    };
    var ${_resultData!};
    $(function () {
        // 文档api地址https://www.jq22.com/jquery-info22644
        ${_resultData!} = $($treeData.id).treeSelect(defaults);
        $($treeData.el).click(function () {
            $($treeData.id).click();
        })
    })
</script>