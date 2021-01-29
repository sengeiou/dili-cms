<script>
    /*********************变量定义区 begin*************/
    //行索引计数器
    // 如 let itemIndex = 0;
    let _grid = $('#grid');
    let _form = $('#_form');
    var dia;


    //时间范围
    lay('.laydatetime').each(function () {
        laydate.render({
            elem: this
            , trigger: 'click'
            , range: false
            , theme: '#007bff'
            , type: 'datetime'
        });
    });

    /*********************变量定义区 end***************/


    /******************************驱动执行区 begin***************************/
    $(function () {
        $(window).resize(function () {
            _grid.bootstrapTable('resetView')
        });
        let size = ($(window).height() - $('#queryForm').height() - 210) / 40;
        size = size > 10 ? size : 10;
        _grid.bootstrapTable('refreshOptions', {
            url: '/annunciate/listPage.action',
            pageSize: parseInt(size)
        });
        _grid.on('load-success.bs.table', function () {
            $('[data-toggle="tooltip"]').tooltip()
        })
    });

    /******************************驱动执行区 end****************************/

    /*****************************************函数区 begin************************************/

    /**
     * 操作按钮处理
     */
    function operationFormatter(value, row, index, field){
        var operationValue='';
        if(row.$_sendState==2&&row.stickState==1){
            operationValue+='<#resource code="annunciate_stick">';
            operationValue+='<a href="#" class="mr-2" onclick="stickHandler(\'' + value+ '\')"><i class="fa fa-search">置顶</i></a>';
            operationValue+='</#resource>';
        }
        return operationValue;
    }

    /**
     * 打开新增窗口
     */
    function openInsertHandler() {
        diaAdd = bs4pop.dialog({
            title: '新增通告信息',//对话框title
            className: 'dialog-center',
            content: '${contextPath}/annunciate/add.html', //对话框内容，可以是 string、element，$object
            width: '75%',//宽度
            height: '95%',//高度
            backdrop: 'static',
            isIframe: true//默认是页面层，非iframe
        });

    }

    function deleteHandler(){
        var selectDatas=_grid.bootstrapTable('getSelections');
        if(selectDatas.length==0){
            bs4pop.alert("只能选择一条数据进行删除!", {type: 'error'});
            return;
        }
        if(selectDatas[0].$_sendState==1||selectDatas[0].$_sendState==3){
            bs4pop.confirm("确认删除当前通告信息吗？", {title: "信息确认"}, function (sure) {
                if(sure){
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: '/annunciate/delete.action',
                        data: {id: selectDatas[0].id},
                        success: function (data) {
                            bui.loading.hide();
                            if (data.code == '200') {
                                bs4pop.alert("删除成功!", {type: 'success'},function(){
                                    location.reload();
                                });
                            }
                        },
                        error: function () {
                            bui.loading.hide();
                            bs4pop.alert("删除失败!", {type: 'error'});
                        }
                    });
                }
            });
        }else{
            bs4pop.alert("只有未发布和已撤发的通告才能进行删除!", {type: 'error'});
            return;
        }
    }

    function revokeHandler(){
        var selectDatas=_grid.bootstrapTable('getSelections');
        if(selectDatas.length==0){
            bs4pop.alert("只能选择一条数据进行撤销!", {type: 'error'});
            return;
        }
        if(selectDatas[0].$_sendState==2){
            bs4pop.confirm("确认撤销当前通告信息吗？", {title: "信息确认"}, function (sure) {
                if(sure){
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: '/annunciate/revoke.action',
                        data: {id: selectDatas[0].id},
                        success: function (data) {
                            bui.loading.hide();
                            if (data.code == '200') {
                                bs4pop.alert("撤销成功!", {type: 'success'},function(){
                                    location.reload();
                                });
                            }
                        },
                        error: function () {
                            bui.loading.hide();
                            bs4pop.alert("撤销失败!", {type: 'error'});
                        }
                    });
                }
            });
        }else{
            bs4pop.alert("只有已发布的通告才能进行撤销!", {type: 'error'});
            return;
        }
    }

    function stickHandler(id) {
        bs4pop.confirm("确认置顶当前通告信息吗？", {title: "信息确认"}, function (sure) {
            if(sure){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: '/annunciate/stick.action',
                    data: {id: id},
                    success: function (data) {
                        bui.loading.hide();
                        if (data.code == '200') {
                            bs4pop.alert("置顶成功!", {type: 'success'},function(){
                                location.reload();
                            });
                        }
                    },
                    error: function () {
                        bui.loading.hide();
                        bs4pop.alert("置顶失败!", {type: 'error'});
                    }
                });
            }
        });
    }

    function openEditHandler() {
        var selectDatas=_grid.bootstrapTable('getSelections');
        if(selectDatas.length!=1){
            bs4pop.alert("只能选择一条数据进行编辑!", {type: 'error'});
            return;
        }
        if(selectDatas[0].$_sendState==1||selectDatas[0].$_sendState==3){
            diaAdd = bs4pop.dialog({
                title: '信息通告编辑',
                content: '/annunciate/add.html?id=' + selectDatas[0].id,
                isIframe: true,
                backdrop: 'static',
                width: '75%',
                height: '95%'
            });
        }else{
            bs4pop.alert("只有未发布和已撤发的通告才能进行编辑!", {type: 'error'});
            return;
        }
    }

    function openViewBtnHandler() {
        var selectDatas=_grid.bootstrapTable('getSelections');
        if(selectDatas.length!=1){
            bs4pop.alert("只能选择一条数据进行查看!", {type: 'error'});
            return;
        }
        viewHandler(selectDatas[0].id);
    }

    function viewReadedHandler(id){
        diaView = bs4pop.dialog({
            title: '已读数列表',
            content: '/annunciate/viewReaded.html?id=' + id,
            isIframe: true,
            backdrop: 'static',
            width: '75%',
            height: '95%'
        });
    }

    /** 点击已读事件*/
    function viewReaded(value, row, index) {
        return [
            '<a href=javascript:viewReadedHandler('+row.id+')>'+value+'</a>'
        ].join("")
    }

    //查看跳转
    function viewHandler(id){
        diaView = bs4pop.dialog({
            title: '信息通告详情',
            content: '/annunciate/view.html?id=' + id,
            isIframe: true,
            backdrop: 'static',
            width: '75%',
            height: '95%'
        });
    }

    /** 点击已读事件*/
    function view(value, row, index) {
        return [
            '<a href=javascript:viewReadedHandler('+row.id+')>'+value+'</a>'
        ].join("")
    }

    /**
     * 查询处理
     */
    function queryDataHandler(flag) {
        if(flag==0){
            $("#sendState").val("");
        }else if(flag==1){
            $("#sendState").val("1");
        }else if(flag==2){
            $("#sendState").val("2");
        }else if(flag==3){
            $("#sendState").val("3");
        }else if(flag==4){
            $("#sendState").val("4");
        }
        _grid.bootstrapTable('refresh');
    }

    /**
     * 虚浮处理
     */
    function suspensionFun(value, row, index, field) {
        if(typeof(value)=="undefined"){
            value='';
        }
        return "<span data-toggle='tooltip' data-placement='left' data-original-title='" + value + "'>" + value + "</span>";
    }

    /**
     * table参数组装
     * 可修改queryParams向服务器发送其余的参数
     * @param params
     */
    function queryParams(params) {
        let temp = {
            rows: params.limit,   //页面大小
            page: ((params.offset / params.limit) + 1) || 1, //页码
            sort: params.sort,
            order: params.order
        };
        return $.extend(temp, bui.util.bindGridMeta2Form('grid', 'queryForm'));
    }

    /** 点击编号查看事件*/
    function view(value, row, index) {
        return [
            '<a href=javascript:viewHandler('+row.id+')>'+value+'</a>'
        ].join("")
    }
    /*****************************************函数区 end**************************************/

</script>