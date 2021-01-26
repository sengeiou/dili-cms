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
        treeInit();
        $(window).resize(function () {
            _grid.bootstrapTable('resetView')
        });
        let size = ($(window).height() - $('#queryForm').height() - 210) / 40;
        size = size > 10 ? size : 10;
        _grid.bootstrapTable('refreshOptions', {
            url: '/file/listPage.action',
            pageSize: parseInt(size)
        });
        _grid.on('load-success.bs.table', function () {
            $('[data-toggle="tooltip"]').tooltip()
        })
    });


    /******************************驱动执行区 end****************************/

    /*****************************************函数区 begin************************************/

    /**
     * 树初始化
     */
    function treeInit() {
        var treeUrl = "${contextPath}/fileType/getAllFileToTree.action";
        var setting = {
            view: {
                selectedMulti: false,
                showIcon: true,
                selectedMulti: false,
            },
            edit: {
                enable: true,
            },
            check: {
                enable: true,
                chkStyle: "radio",
                chkboxType: {"Y": "", "N": ""},
                radioType: "all"
            },
            data: {
                key: {
                    name: "treeShow"
                },
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId",
                }
            },
            callback: {
                onClick: zTreeOnClick,
                beforeRemove: beforeRemove,
                beforeRename: beforeRename,
            }
        };
        $.ajax({
            url: treeUrl,
            dataType: 'json',
            type: 'post',
            async: false,
            success: function (data) {
                var treeObj = $.fn.zTree.init($("#fileTree"), setting, data.data);
                treeObj.expandAll(true);
            }
        });
    }

    /**
     * 点击树形节点加载右侧列表数据
     */
    function zTreeOnClick(event, menuTree, treeNode) {
        var typeId = treeNode.id;
        $('#typeId').val(typeId);
        $(window).resize(function () {
            _grid.bootstrapTable('resetView')
        });
        _grid.bootstrapTable('refreshOptions', {
            url: '/file/listPage.action',
        });
    }

    function beforeRemove(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("fileTree");
        zTree.selectNode(treeNode);
        alert(treeNode.Action);
        return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
    }

    function beforeRename(treeId, treeNode, newName) {
        if (newName.length == 0) {
            alert("节点名称不能为空!");
            return false;
        }
        return true;
    }

    var newCount = 1;

    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='add node' onfocus='this.blur();' ></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) btn.bind("click", function () {
            var zTree = $.fn.zTree.getZTreeObj("fileTree");
            zTree.addNodes(treeNode, {
                id: (treeNode.id + newCount),
                parentid: treeNode.id,
                name: "new node" + (newCount++)
            });
            return false;
        });
    };

    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
    };

    /**
     * 新增文档分类
     */
    function addFileTypeHandler() {
        addPrompt = bs4pop.prompt("<em style='color: red'>*</em>分类名称", '', {
            title: '新增文档分类',
            hideRemove: true,
            width: 380,
        }, function (sure, value) {
            if (sure == true) {
                if ($.trim(value) == '') {
                    bs4pop.alert('请输入分类名称', {type: 'error'});
                    return;
                }
                saveOrUpdateFileType(value,null);
            } else {
                return;
            }
        });
    }

    /**
     * 添加分类
     */
    function addFileType(val) {
        let iFileType = JSON.stringify({name: val});
        $.ajax({
            type: "POST",
            url: "/fileType/addFileType.action",
            processData: false,
            data: iFileType,
            contentType: false,
            dataType: "JSON",
            success: function (res) {
                bui.loading.hide();
                if (res.code == "200") {
                    bs4pop.alert(res.message, {
                        width: '350px', height: "200px", type: 'success', onHideStart: () => {
                            treeInit();
                        }
                    });
                } else {
                    bs4pop.alert(res.message, {
                        onHideStart: () => {
                            addPrompt.hide()
                            treeInit();
                        }
                    });
                }
            },
            error: function (error) {
                bui.loading.hide();
                bs4pop.alert(error.message, {type: 'error'});
            }
        });
    }
    /**
     * 编辑文档分类
     */
    function editFileTypeHandler() {
        var zTree = $.fn.zTree.getZTreeObj("fileTree");
        var nodes = zTree.getCheckedNodes(true);
        if (null == nodes || nodes.length == 0) {
            bs4pop.alert('请选中一个节点');
            return;
        }
        addPrompt = bs4pop.prompt("<em style='color: red'>*</em>分类名称", nodes[0].name, {
            title: '编辑文档分类',
            hideRemove: true,
            width: 380,
        }, function (sure, value) {
            if (sure == true) {
                if ($.trim(value) == '') {
                    bs4pop.alert('请输入分类名称', {type: 'error'});
                    return;
                }
                saveOrUpdateFileType(value, nodes[0]);
            } else {
                return;
            }
        });
    }

    /**
     * 新增或修改分类
     */
    function saveOrUpdateFileType(val,nodes) {
        if(null!=nodes){
            nodes.name = val;
            var iFileType = JSON.stringify(nodes);
        }else{
            var iFileType = JSON.stringify({name: val});
        }
        $.ajax({
            type: "POST",
            url: "/fileType/saveOrUpdateFileType.action",
            processData: false,
            data: iFileType,
            contentType: false,
            dataType: "JSON",
            success: function (res) {
                bui.loading.hide();
                if (res.code == "200") {
                    bs4pop.alert(res.message, {
                        width: '350px', height: "200px", type: 'success', onHideStart: () => {
                            treeInit();
                        }
                    });
                } else {
                    bs4pop.alert(res.message, {
                        onHideStart: () => {
                            addPrompt.hide()
                            treeInit();
                        }
                    });
                }
            },
            error: function (error) {
                bui.loading.hide();
                bs4pop.alert(error.message, {type: 'error'});
            }
        });
    }

    /**
     * 打开上传窗口
     */
    function openUploadHandler() {
        diaAdd = bs4pop.dialog({
            title: '上传文件',//对话框title
            className: 'dialog-center',
            content: '${contextPath}/file/add.html', //对话框内容，可以是 string、element，$object
            width: '50%',//宽度
            height: '80%',//高度
            backdrop: 'static',
            isIframe: true//默认是页面层，非iframe
        });

    }

    /**
     * 打开编辑窗口
     */
    function openEditHandler() {
        let rows = _grid.bootstrapTable('getSelections');
        if (null == rows || rows.length == 0) {
            bs4pop.alert('请选中一条数据');
            return;
        }
        diaEdit = bs4pop.dialog({
            title: '编辑文件',//对话框title
            className: 'dialog-center',
            content: '${contextPath}/file/add.html', //对话框内容，可以是 string、element，$object
            width: '50%',//宽度
            height: '80%',//高度
            backdrop: 'static',
            isIframe: true//默认是页面层，非iframe
        });

    }

    /**
     * 打开详情窗口
     */
    function openDetailHandler() {
        let rows = _grid.bootstrapTable('getSelections');
        if (null == rows || rows.length == 0) {
            bs4pop.alert('请选中一条数据');
            return;
        }
        diaView = bs4pop.dialog({
            title: '文件详情',//对话框title
            className: 'dialog-center',
            content: '${contextPath}/file/add.html', //对话框内容，可以是 string、element，$object
            width: '50%',//宽度
            height: '80%',//高度
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
        if (typeof (value) == "undefined") {
            value = '';
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

    function imgFormatter(value, row, index) {
        var operationValue = '';
        operationValue += '<img src="' + value + '" alt="" style="height: 100px;width: 100px" />';
        return operationValue;
    }

    /*****************************************函数区 end**************************************/

</script>