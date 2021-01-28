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
        treeInit();
        $(window).resize(function () {
            _grid.bootstrapTable('resetView')
        });
        _grid.bootstrapTable('refreshOptions', {
            url: '/file/listPage.action',
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
                onCheck: zTreeOnCheck,
                beforeEditName: zTreeBeforeEditName,
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

    /**
     * 勾选时触发事件
     */
    function zTreeOnCheck(event, treeId, treeNode) {
        var checked = treeNode.checked;
        if (checked) {
            $("#fileType").val(treeNode.id);
        } else {
            $("#fileType").val(null);
        }
    }

    /**
     * 编辑名称前回调函数
     */
    function zTreeBeforeEditName(treeId, treeNode) {
        treeNode.treeShow = treeNode.name;
    }

    /**
     * 删除时回调
     */
    function beforeRemove(treeId, treeNode) {
        bs4pop.confirm(" 确定删除" + treeNode.name + "分类吗？", {title: "信息确认"}, function (sure) {
            if (sure) {
                deleteFileType(treeNode);
            } else {
                treeInit();
            }
        });
    }

    /**
     * 编辑名称前回调
     */
    function beforeRename(treeId, treeNode, newName) {
        if (newName.length == 0) {
            bs4pop.alert('请输入分类名称', {type: 'error'});
            return;
        }
        saveOrUpdateFileType(newName, treeNode);
    }

    /**
     * 选择查询类型，选中后勾选树节点
     */
    function chooseType() {
        var id = $("#fileType").val();
        var zTree = $.fn.zTree.getZTreeObj("fileTree");
        var node = zTree.getNodeByParam("id", id);//根据ID找到该节点
        zTree.checkNode(node);
        zTreeOnClick(null, null, node);
    }

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
                saveOrUpdateFileType(value, null);
            } else {
                return;
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
        editPrompt = bs4pop.prompt("<em style='color: red'>*</em>分类名称", nodes[0].name, {
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
     * 删除文档分类
     */
    function deleteFileTypeHandler() {
        var zTree = $.fn.zTree.getZTreeObj("fileTree");
        var nodes = zTree.getCheckedNodes(true);
        if (null == nodes || nodes.length == 0) {
            bs4pop.alert('请选中一个节点');
            return;
        }
        bs4pop.confirm(" 确定删除" + nodes[0].name + "分类吗？", {title: "信息确认"}, function (sure) {
            if (sure) {
                deleteFileType(nodes[0]);
            }
        });
    }

    /**
     * 新增或修改分类
     */
    function saveOrUpdateFileType(val, nodes) {
        bui.loading.show('努力提交中，请稍候。。。');
        if (null != nodes) {
            nodes.name = val;
            var iFileType = JSON.stringify(nodes);
        } else {
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
                            $("#fileType").val(null);
                            treeInit();
                        }
                    });
                } else {
                    bs4pop.alert(res.message, {
                        onHideStart: () => {
                            $("#fileType").val(null);
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
     * 删除分类
     */
    function deleteFileType(nodes) {
        bui.loading.show('努力提交中，请稍候。。。');
        if (null != nodes) {
            var iFileType = JSON.stringify(nodes);
        } else {
            bs4pop.alert("提交数据不能为空", {type: 'error'});
            return;
        }
        $.ajax({
            type: "POST",
            url: "/fileType/deleteFileType.action",
            processData: false,
            data: iFileType,
            contentType: false,
            dataType: "JSON",
            success: function (res) {
                bui.loading.hide();
                if (res.code == "200") {
                    bs4pop.alert(res.message, {
                        width: '350px', height: "200px", type: 'success', onHideStart: () => {
                            $("#fileType").val(null);
                            treeInit();
                        }
                    });
                } else {
                    bs4pop.alert(res.message, {
                        onHideStart: () => {
                            $("#fileType").val(null);
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
            content: '${contextPath}/file/add.html?id=' + rows[0].id, //对话框内容，可以是 string、element，$object
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
            content: '${contextPath}/file/view.html?id=' + rows[0].id, //对话框内容，可以是 string、element，$object
            width: '50%',//宽度
            height: '80%',//高度
            backdrop: 'static',
            isIframe: true//默认是页面层，非iframe
        });

    }

    /**
     * 删除文件
     */
    function deleteHandler() {
        let rows = _grid.bootstrapTable('getSelections');
        if (null == rows || rows.length == 0) {
            bs4pop.alert('请选数据');
            return;
        }
        $.ajax({
            type: "POST",
            url: "/file/delete.action?id=" + rows[0].id,
            processData: false,
            contentType: false,
            dataType: "JSON",
            success: function (res) {
                if (res.code == "200") {
                    bs4pop.alert(res.message);
                    treeInit();
                    _grid.bootstrapTable('refresh');
                } else {
                    bs4pop.alert(res.message, {type: 'error'});
                }
            },
            error: function (error) {
                bs4pop.alert(error.message, {type: 'error'});
            }
        });
    }

    /**
     * 文件下载
     */
    function downloadFile() {
        let rows = _grid.bootstrapTable('getSelections');
        if (null == rows || rows.length == 0) {
            bs4pop.alert('请选一条数据');
            return;
        }
        $.ajax({
            type: "POST",
            url: "/fileItem/listByFileId.action?fileId=" + rows[0].id,
            processData: false,
            contentType: false,
            dataType: "JSON",
            success: function (res) {
                if (res.code == "200") {
                    for (let i = 0; i < res.data.length; i++) {
                        let html = "<a class='test' download='' href='" + res.data[i].fileUrl + "'></a>"
                        $("body").append(html); // 修复firefox中无法触发click
                    }
                    $.each($(".test"), function (index, item) {
                        setTimeout(function () {
                            item.click();
                            item.remove();
                        }, 200 * index)
                    });
                } else {
                    bs4pop.alert(res.message, {type: 'error'});
                }
            },
            error: function (error) {
                bs4pop.alert(error.message, {type: 'error'});
            }
        });
    }

    /**
     * 用a标签下载文件
     */
    function aDownloadFile(url) {
        if (url) {
            var a = document.createElement("a");
            $("body").append(a); // 修复firefox中无法触发click
            a.download = '';
            a.href = url;
            a.click();
            $(a).remove();
        }
    }

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

    function imgFormatter(value, row, index) {
        var operationValue = '';
        operationValue += '<img src="' + value + '" alt="" style="height: 70px;width: 70px" />';
        return operationValue;
    }

    /*****************************************函数区 end**************************************/

</script>