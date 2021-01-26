<script>
    let fileDto = {
        coverImg: "",
        typeId: "",
        isDownload: 1,
        fileAuthList: [],
        fileItemList: []
    };

    // 新增或者更新
    function saveOrUpdate(successfn) {
        let _form = $("#form");
        if (!_form.validate().form()) {
            return false;
        }
        // 获取部门权限
        departmentAuthResult.val().forEach(item => {
            fileDto.fileAuthList.push({authType: 10, authValue: item});
        });
        let formData = Object.assign(fileDto, _form.serializeObject());
        let url = fileDto.id ? "update" : "insert";
        $.ajax({
            type: "POST",
            url: "/file/" + url + ".action",
            data: JSON.stringify(formData),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (result.success) {
                    bs4pop.alert(result.result);
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
            appendFileHtml(item);
        });
        //显示图片
        appendImgHtml({fid: 123, coverImg: fileDto.coverImg});
        //显示选中的文件类型
        fileTypeTree.checks = [fileDto.typeId];
        //显示部门类型
        departmentTree.checks = fileDto.fileAuthList.filter(ele => ele.authType == 10).map(ele => {
            return ele.authValue
        });
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
        let ids = fileDto.fileAuthList.filter(ele => ele.authType == 30).map(ele => {
            return ele.authValue
        });
        dia = bs4pop.dialog({
            title: '选择库存商品',// 对话框title
            content: '${contextPath}/component/userSelect.html?ids=' + ids, // 对话框内容，可以是
            width: '85%',// 宽度
            height: '95%',// 高度
            isIframe: true,// 默认是页面层，非iframe
            backdrop: 'static',
        });
    }

    //选择用户回调函数
    function selectUsersCallback(items) {
        fileDto.fileAuthList = [];
        if (items) {
            items.forEach((item) => {
                fileDto.fileAuthList.push({authType: 30, authValue: item.id});
            });
        }
    }

    //上传文件回掉
    function uploadFileCallback(state, data) {
        // 获取上传成功的文件数据
        if (!state || data.data.code != '200') {
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
    function appendFileHtml(file) {
        let isAppend = false;
        let fileItemHtml = "<li class='list-group-item' style='padding: 0 !important;' id='" + file.fid + "'>" +
            "<button type='button' class='btn btn-default badge' onclick='deleteFileHandler(" + file.fid + ")'>X</button>\n" +
            file.fileName +
            "</li>";
        //获取到所有group
        $.each($(".file-group"), (i, item) => {
            if ($(item).find("li").length < 4) {
                $(item).append(fileItemHtml);
                isAppend = true;
                return;
            }
        });
        if (isAppend) return;
        //计算获取到当前是属于哪个group的
        let fileCount = $("#files").find("li").length;
        let groupElement = "file-group-" + parseInt(fileCount / 4);
        //如果group不存在则需要添加一个group
        if ($("#" + groupElement).length <= 0) {
            $("#file-groups").append("<div class='col-sm-4' id='" + groupElement + "'></div>")
        }
        $("#" + groupElement).append(fileItemHtml);
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
        if (!state || data.data.code != '200') {
            alert("文件：" + data.name + "上传失败!");
            return;
        }
        //设置封面图片url
        fileDto.coverImg = data.data.data;
        appendFileHtml({coverImg: data.coverImg, fid: data.fid});
    }

    //在页面上添加一个图片
    function appendImgHtml(data) {
        let fileItemHtml = "<div class='file-item' id='" + data.fid + "'>" +
            "<span class='recommends-content-item__info' onclick='deleteImgFileHandler(" + data.fid + ")'>删除</span>" +
            "<img class='img-thumbnail' src='" + data.coverImg + "'></div>";
        $("#aaa").append(fileItemHtml);
    }

    //删除封面
    function deleteImgFileHandler(fid) {
        $("#" + fid).remove();
        $("#aaa").triggerHandler("updateCount");
        fileDto.coverImg = "";
    }
</script>