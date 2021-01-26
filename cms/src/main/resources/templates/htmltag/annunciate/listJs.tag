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
        operationValue+='<a href="#" class="mr-2" onclick="openViewHandler(\'' + value+ '\')"><i class="fa fa-search"></i></a>';
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
            width: '95%',//宽度
            height: '95%',//高度
            backdrop: 'static',
            isIframe: true//默认是页面层，非iframe
        });

    }



    /**
     * 查询处理
     */
    function queryDataHandler() {
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
            '<a href=javascript:openViewHandler('+row.id+')>'+value+'</a>'
        ].join("")
    }
    /*****************************************函数区 end**************************************/

</script>