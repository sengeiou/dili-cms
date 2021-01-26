<script>
    /*********************变量定义区 begin*************/
        //行索引计数器
        // 如 let itemIndex = 0;
    let _grid = $('#grid');
    let _form = $('#_form');

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
        _grid.bootstrapTable('refreshOptions', {
            url: '/annunciateItem/listPage.action',
        });
        _grid.on('load-success.bs.table', function () {
            $('[data-toggle="tooltip"]').tooltip()
        })
    });
    /******************************驱动执行区 end****************************/

    /*****************************************函数区 begin************************************/
    /**
     * 查询处理
     */
    function queryDataHandler() {
        _grid.bootstrapTable('refresh');
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
    /*****************************************函数区 end**************************************/

</script>