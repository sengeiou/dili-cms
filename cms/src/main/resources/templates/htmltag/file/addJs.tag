<script>
    let fileDto = {
        coverImg: "",
        typeId: "",
        isDownload: 1,
        fileAuthList: [],
        fileItemList: []
    };
    //已经添加了得用户id
    let fileUserIds = new Set();
    // 已经选择的数据
    let userFileAuthList = [];

    let _grid;

    // 新增或者更新
    function saveOrUpdate(successfn) {
        let _form = $("#form");
        if (!_form.validate().form()) {
            return false;
        }
        // 获取部门权限
        if ($(':radio[name="authTypeId"]:checked').val() == 10) {
            departmentAuthResult.getCheckedObject().forEach(item => {
                fileDto.fileAuthList.push({authType: 10, authValue: item.id, authText: item.name});
            });
        }
        // 获取用户权限
        if ($(':radio[name="authTypeId"]:checked').val() == 20) {
            let users = _grid.bootstrapTable("getData");
            users.forEach(item => {
                fileDto.fileAuthList.push({authType: 20, authValue: item.id, authText: item.realName});
            });
        }
        let formData = Object.assign(fileDto, _form.serializeObject());
        //判断有没有上传文件
        if (formData.fileItemList.length <= 0) {
            bs4pop.alert('请上传文件!', {type: 'error'});
            return;
        }
        //判断有没有选择文件类型
        if (!formData.typeId) {
            bs4pop.alert('请选择文件类型!', {type: 'error'});
            return;
        }
        //判断有没有选择文件权限
        if (!formData.authTypeId || (formData.authTypeId != 0 && formData.fileAuthList.length <= 0)) {
            bs4pop.alert('请选择文件权限!', {type: 'error'});
            return;
        }
        let url = fileDto.id ? "update" : "insert";
        $.ajax({
            type: "POST",
            url: "/file/" + url + ".action",
            data: JSON.stringify(formData),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (result.success) {
                    bs4pop.alert(result.result, {
                        width: '350px', height: "200px", type: 'success', onHideStart: () => {
                            if (url == "insert") {
                                parent.diaAdd.hide();
                            } else {
                                parent.diaEdit.hide();
                            }
                            parent.treeInit();
                            parent.queryDataHandler();
                        }
                    });
                } else {
                    bs4pop.alert(result.result, {type: 'error'});
                }
            },
            error: function () {
            }
        });
    }

    var departmentAuthResult;
    var fileTypeResult;
    $(function () {
        _grid = $('#grid');
        _grid.bootstrapTable('refreshOptions', {
            datatype: 'local',
        });
        $(window).resize(function () {
            _grid.bootstrapTable('resetView')
        });
        //初始化
        init();
    });

    //初始化
    function init() {

        //文件部门权限树配置
        let departmentTree = Object.assign({}, zTreeConfig);
        //文件类型树配置
        let fileTypeTree = Object.assign({}, zTreeConfig);
        //判读是不是编辑页面
        <% if (isNotEmpty(fileInfo)) {%>
        fileDto = ${fileInfo!};
        //显示已经存在的文件信息
        fileDto.fileItemList.forEach(item => {
            item.fid = randomID(10);
            appendFileHtml(item);
        });
        //显示图片
        appendImgHtml({fid: randomID(10), coverImg: fileDto.coverImg});
        //显示选中的文件类型
        fileTypeTree.checks = [fileDto.typeId];
        //显示部门类型
        departmentTree.checks = fileDto.fileAuthList.filter(ele => ele.authType == 10).map(ele => {
            return ele.authValue
        });
        //加载默认选中的权限用户
        userFileAuthList = fileDto.fileAuthList.filter(ele => ele.authType == 20).map(ele => {
            fileUserIds.add(ele.authValue);
            return {realName: ele.authText, id: ele.authValue, position: null, departmentId: null};
        });
        setTimeout(() => {
            _grid.bootstrapTable('load', userFileAuthList);
        }, 500);
        <% } %>
        //初始化文本上的的值
        $('input:radio[name="isDownload"][value="' + fileDto.isDownload + '"]').prop('checked', true);
        $('input:radio[name="authTypeId"][value="' + fileDto.authTypeId + '"]').prop('checked', true);
        $("#fileName").val(fileDto.fileName);
        $("#remark").val(fileDto.remark);
        //加载部门树
        departmentTree.zNodes =${departmentList!};
        departmentTree.chkStyle = "checkbox";
        departmentTree.callback.onCheck = function (e, treeNode) {
        };
        departmentAuthResult = $("#departmentTree").treeSelect(departmentTree);
        $("#department").click(function () {
            $("#departmentTree").click();
        });
        //权限类型改变监听事件 如果当前是部门权限重新选择了其它权限则需要重新初始化权限
        $('input:radio[name="authTypeId"]').click(function () {
            fileDto.fileAuthList = [];
            //如果选择的时候用户权限 则需要显示或者隐藏用户列表
            if ($(':radio[name="authTypeId"]:checked').val() == 20) {
                $("#systemUserAndDepartDev").show();
            } else {
                $("#systemUserAndDepartDev").hide();
            }
        });
        //加载文件类型树
        fileTypeTree.zNodes =${fileTypeList!};
        fileTypeTree.chkStyle = "radio";
        fileTypeTree.callback.onCheck = fileTypeCallback;
        fileTypeResult = $("#fileTypeTree").treeSelect(fileTypeTree);
        $("#fileType").val(fileTypeResult.text())
        $("#fileType").click(function () {
            $("#fileTypeTree").click();
        });
    }

    //文件分类选择回掉函数
    function fileTypeCallback(e, data) {
        if (data == undefined) return;
        if (data && data.checked) {
            fileDto.typeId = data.id;
            $("#fileType").val(fileTypeResult.text());
        } else {
            fileDto.typeId = "";
            $("#fileType").val("");
        }
    }

    //弹出用户选择界面
    function openUserPageHandler() {
        dia = bs4pop.dialog({
            title: '选择人员',// 对话框title
            content: '${contextPath}/component/userSelect.html?ids=' + Array.from(fileUserIds), // 对话框内容，可以是
            width: '85%',// 宽度
            height: '95%',// 高度
            isIframe: true,// 默认是页面层，非iframe
            backdrop: 'static',
        });
    }

    //选择用户回调函数
    function selectUsersCallback(items) {
        if (items) {
            items.forEach((item) => {
                if (!fileUserIds.has(item.id)) {
                    fileUserIds.add(item.id);
                    userFileAuthList.push({
                        id: item.id,
                        realName: item.realName,
                        position: item.position,
                        departmentId: item.departmentId
                    });
                }
            });
            _grid.bootstrapTable('load', userFileAuthList);
        }
    }


    //删除用户
    function deleteUser() {
        let rows = _grid.bootstrapTable('getSelections');
        if (!rows || rows.length <= 0) {
            bs4pop.alert('请选择数据进行删除!');
            return;
        }
        var ids = [];
        rows.forEach((row) => {
            ids.push(row.id);
            //删除已经选中的用户
            fileUserIds.delete(row.id);
        });
        _grid.bootstrapTable('remove', {
            field: 'id',//对应该字段ID的columns的field
            values: ids//字段ID的值
        })
        _grid.bootstrapTable('refreshOptions', {});
    }

    //上传文件回掉
    function uploadFileCallback(state, data) {
        // 获取上传成功的文件数据
        if (!state || !data.success) {
            alert("文件：" + data.name + "上传失败!");
            return;
        }
        let file = {
            fileUrl: data.data.data,
            fileType: data.type,
            fileName: data.name,
            fileSize: data.size,
            //页面生成的文件唯一标识 可用于删除
            fid: data.fid
        };
        //添加文件数据
        fileDto.fileItemList.push(file);
        appendFileHtml(file);
    }

    //添加一个文件元素
    function appendFileHtml(data) {
        let fileItemHtml = "<span style='margin: 0 15px 0 0; ' id='" + data.fid + "'>" +
            "<i onclick='deleteFileHandler(\"" + data.fid + "\")' class=\"fa fa-close\"></i>" +
            "<a href=\"#\" class=\"badge badge-info\" onclick='openFile(\"" + data.fileUrl + "\")'>" +
            data.fileName + "</a>" +
            "</span>";
        $("#file-group").append(fileItemHtml);
    }


    //删除文件
    function deleteFileHandler(fid) {
        $("#" + fid).remove();
        fileDto.fileItemList.forEach((item, index) => {
            if (item.fid == fid) {
                fileDto.fileItemList.splice(index, 1);
            }
        });
        $("#bbb").triggerHandler("updateCount");
    }

    //树
    let zTreeConfig = {
        //默认选中
        checks: [],
        height: 233,
        filter: false,
        searchShowParent: false,
        // 单选还是多选 radio checkbox
        chkStyle: "checkbox",
        callback: {
            onCheck: function (e, treeNode) {

            } /*选中事件的回调*/
        },
        //自定义数据格式
        data: {
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
        },
        zNodes: []
    };


    //上传图片文件回掉
    function uploadImgCallback(state, data) {
        // 获取上传成功的文件数据
        if (!state || !data.success) {
            alert("文件：" + data.name + "上传失败!");
            return;
        }
        //设置封面图片url
        fileDto.coverImg = data.data;
        appendImgHtml({coverImg: fileDto.coverImg, fid: data.fid});
    }

    //在页面上添加一个图片
    function appendImgHtml(data) {
        let fileItemHtml = "<div class='file-item' id='" + data.fid + "'>" +
            "<span class='recommends-content-item__info' onclick='deleteImgFileHandler(\"" + data.fid + "\")'>删除</span>" +
            "<img class='img-thumbnail' onclick='openFile(\"" + data.coverImg + "\")' src='" + data.coverImg + "'></div>";
        $("#aaa").append(fileItemHtml);
    }

    //删除封面
    function deleteImgFileHandler(fid) {
        $("#" + fid).remove();
        $("#aaa").triggerHandler("updateCount");
        fileDto.coverImg = "";
    }

    //预览文件
    function openFile(url) {
        window.open("https://dfs.diligrp.com/file/view/" + url.substring(url.lastIndexOf("/") + 1));
    }

    //随机生成Id
    function randomID(randomLength) {
        return Number(Math.random().toString().substr(3, randomLength) + new Date().getTime()).toString(36)
    }
</script>