<head>
    <link rel="stylesheet" href="/resources/css/upload.css">
</head>
<body>
<#impFileupload/>

<div id="${_cid!}" class='form-inline'>
    <div id="uploadBtn">
        <i class="fa fa-cloud-upload fa-3x"></i>
    </div>
    <input id="fileupload" style="display: none;" type="file" name="file" multiple
           data-url="${_uploadUrl!}">
</div>
<script>
    $(function () {
        var $data = {
            //组件的唯一标识
            $cid: "${_cid!}",
            // 文件类型限制
            $types: "${_types}".trim().length == 0 ? new Set() : new Set("${_types}".split(";")),
            //文件最大限制
            $maxFileCount: Number("${_maxFileCount}"),
            //文件上传成功或失败回调函数
            $uploadCallback: (state = true, data = {}) => {
                let functionName = "${_uploadCallback!}";
                if (functionName.trim()) {
                    eval(functionName + '(state,data)');
                }
            },
            //删除文件回调函数
            $deleteCallback: (fid) => {
                let functionName = "${_deleteCallback!}";
                if (functionName.trim()) {
                    eval(functionName + '(fid)');
                }
            },
            // 已经上传成功的数量
            uploadSuccCount: 0,
            // 组件的id
            componentIdSelect: function () {
                return "#" + this.$cid;
            },
            // 当前上传的文件id集合
            currentFileItemIndexs: new Array(),
            // 删除文件上传按钮
            deleteUploadElement: function () {
                if ($(this.componentIdSelect() + " #uploadBtn").length > 0) {
                    $(this.componentIdSelect() + " #uploadBtn").remove();
                }
            },
            //添加文件上传按钮
            addUploadElement: function () {
                if ($(this.componentIdSelect() + " #uploadBtn").length <= 0) {
                    let uploadHtml = "<div class='col-sm-1' id='uploadBtn'>" +
                        "<i class='fa fa-cloud-upload fa-3x'></i>" +
                        " </div>";
                    $(this.currentFileItemsIdSelect()).prepend(uploadHtml);
                    window.addUploadBtnClick();
                }
            },
            // 当前上传的文件容器的id选择器
            currentFileItemsIdSelect: function () {
                return this.componentIdSelect() + ' .file-items';
            },
            //获取当前组件中的文件上传组件元素对象
            getFileUploadIdSelect: function () {
                return this.componentIdSelect() + ' #fileupload';
            },
            //检查大小和类型限制
            filterFile: function (file) {
                return this.$types.has(file.type) || this.$types.size == 0
            }
        }

        //申明文件上传按钮点击事件
        let addUploadBtnClick = () => {
            //点击上传
            $($data.componentIdSelect() + " #uploadBtn").click(() => {
                $($data.getFileUploadIdSelect()).click();
            });
        }
        window.addUploadBtnClick = addUploadBtnClick;
        window.addUploadBtnClick();

        //申明文件添加上传按钮事件
        window.add${_cid!}UploadElement = $data.addUploadElement();

        $($data.getFileUploadIdSelect()).fileupload({
            dataType: 'json',
            singleFileUploads: true,
            maxNumberOfFiles: 1,//最大上传文件数目
            done: function (e, data) {
                if (data.result.code != '200') {
                    let resultData = {
                        name: data.files[0].name,
                        size: data.files[0].size,
                        type: data.files[0].type,
                        fid: $data.currentFileItemIndexs.pop()
                    }
                    //失败回调函数
                    $data.$uploadCallback(false, resultData);
                    return;
                }
                $.each(data.files, function (index, file) {
                    $data.uploadSuccCount++;
                    //取一个文件唯一标识出来，先进先出
                    let fid = $data.currentFileItemIndexs.pop();
                    //返回结果数据
                    let result = data.result ? data.result : {};
                    result.fid = fid;
                    result.name = file.name;
                    result.size = file.size;
                    result.type = file.type;
                    //成功回调函数
                    $data.$uploadCallback(true, result);
                });
                //判断文件上传的数量有没有超过限制
                if ($data.uploadSuccCount >= $data.$maxFileCount) {
                    $data.deleteUploadElement();
                }
            },
            fail: function (e, data) {
                let resultData = {
                    name: data.files[0].name,
                    size: data.files[0].size,
                    type: data.files[0].type,
                    fid: $data.currentFileItemIndexs.pop()
                }
                //失败回调函数
                $data.$uploadCallback(false, resultData);
            },
            /**
             * 回掉
             */
            add: function (e, data) {
                //检查文件数量限制
                if (($data.uploadSuccCount + data.originalFiles.length) > $data.$maxFileCount) {
                    debounce(() => {
                        alert("上传文件数量超出最大限制，最多只能上传：" + $data.$maxFileCount + "个文件");
                    })
                    return;
                }
                //检查文件类型限制和文件大小限制
                for (let index = 0; index < data.files.length; index++) {
                    if (!$data.filterFile(data.files[index])) {
                        debounce(() => {
                            alert("请检查文件限制，支持的文件类型：" + [...$data.$types])
                        })
                        return;
                    }
                }
                data.submit();
                $data.currentFileItemIndexs.push(Math.floor(Math.random() * 100000 + 1));
            },
        });


        //防抖函数
        var iTime;
        let debounce = (func, time = 500) => {
            clearTimeout(iTime);
            iTime = setTimeout(func, time);
        }

    });
</script>