$(function () {

    // init date tables
    var jobTable = $("#job_list").dataTable({
        "deferRender": true,
        "processing": true,
        "serverSide": true,
        "ajax": {
            url: base_url + "/hostinfo/getConfig",
            type: "post",
            data: function (d) {
                var obj = {};
                obj.host = $('#host').val();
                obj.port = $('#port').val();
                obj.enabled = $('#enabled').val();
                obj.liveStatus = $('#liveStatus').val();
                obj.startTime = $('#startTime').val();
                obj.lastAliveTime = $('#lastAliveTime').val();
                obj.execTimeout = $('#execTimeout').val();
                obj.alarmTimeout = $('#alarmTimeout').val();
                obj.maxQueueSize = $('#maxQueueSize').val();
                obj.maxRetry = $('#maxRetry').val();
                obj.queue = $('#queue').val();
                obj.run = $('#run').val();
                obj.start = d.start;
                obj.length = d.length;
                return obj;
            }
        },
        "searching": false,
        "ordering": false,
        //"scrollX": true,	// scroll x，close self-adaption
        "columns": [
            {
                "data": 'id',
                "bSortable": false,
                "visible": true,
                "width": '3%'
            },
            {
                "data": 'host',
                "visible": true,
                "width": '6%'
            },
            {
                "data": 'port',
                "visible": true,
                "width": '3%'
            },
            {
                "data": 'enabled',
                "visible": true,
                "width": '4%',
                "render": function (data, type, row) {
                    // status
                    if (data) {
                        return '<small class="label label-success" >ENABLE</small>';
                    } else {
                        return '<small class="label label-default" >DISABLE</small>';
                    }
                }
            },
            {
                "data": 'liveStatus',
                "visible": true,
                "width": '4%',
                "render": function (data, type, row) {
                    // status
                    if (0 == data) {
                        return '<small class="label label-success" >ALIVE</small>';
                    } else {
                        return '<small class="label label-default" >DEAD</small>';
                    }
                }
            },
            {
                "data": 'startTime',
                "visible": true,
                "width": '10%'
            },
            {
                "data": 'lastAliveTime',
                "visible": true,
                "width": '10%'
            },
            {
                "data": 'execTimeout',
                "visible": true,
                "width": '8%'
            },
            {
                "data": 'alarmTimeout',
                "visible": true,
                "width": '8%'
            },
            {
                "data": 'maxQueueSize',
                "visible": true,
                "width": '7%'
            },
            {
                "data": 'maxRetry',
                "visible": true,
                "width": '7%'
            },
            {
                "data": 'queue',
                "visible": true,
                "width": '7%'
            },
            {
                "data": 'run',
                "visible": true,
                "width": '7%'
            },
            {
                "data": '操作',
                "width": '10%',
                "render": function (data, type, row) {
                    return function () {
                        // data
                        // tableData['key' + row.id] = row;
                        tableData[row.host + '_' + row.port] = row;
                        // status
                        var start_stop_div = "";
                        if (row.enabled) {
                            start_stop_div = '<li><a href="javascript:void(0);" class="job_operate" _type="disabled" >' + '禁用' + '</a></li>\n';
                        } else {
                            start_stop_div = '<li><a href="javascript:void(0);" class="job_operate" _type="enabled" >' + '启用' + '</a></li>\n';
                        }

                        // opt
                        var html = '<div class="btn-group">\n' +
                            '     <button type="button" class="btn btn-primary btn-sm">' + '操作' + '</button>\n' +
                            '     <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">\n' +
                            '       <span class="caret"></span>\n' +
                            '       <span class="sr-only">Toggle Dropdown</span>\n' +
                            '     </button>\n' +
                            '     <ul class="dropdown-menu" role="menu" _id="' + row.host + '_' + row.port + '" >\n' +
                            start_stop_div +
                            '       <li><a href="javascript:void(0);" class="update" >' + '编辑' + '</a></li>\n' +
                            '       <li><a href="javascript:void(0);" class="job_operate" _type="delete" >' + '删除' + '</a></li>\n' +
                            '     </ul>\n' +
                            '   </div>';

                        return html;
                    };
                }
            }
        ],
        "language": {
            "sProcessing": '处理中...',
            "sLengthMenu": '每页 _MENU_ 条记录',
            "sZeroRecords": '没有匹配结果',
            "sInfo": '第 _PAGE_ 页 ( 总共 _PAGES_ 页，_TOTAL_ 条记录 )',
            "sInfoEmpty": '无记录',
            "sInfoFiltered": '(由 _MAX_ 项结果过滤)',
            "sInfoPostFix": "",
            "sSearch": '搜索',
            "sUrl": "",
            "sEmptyTable": '表中数据为空',
            "sLoadingRecords": '载入中...',
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": '首页',
                "sPrevious": '上页',
                "sNext": '下页',
                "sLast": '末页'
            },
            "oAria": {
                "sSortAscending": ': 以升序排列此列',
                "sSortDescending": ': 以降序排列此列'
            }
        }
    });

    // table data
    var tableData = {};

    // job operate
    $("#job_list").on('click', '.job_operate', function () {
        var typeName;
        var url = base_url + "/hostinfo/setConfig";
        var needFresh = false;

        var type = $(this).attr("_type");
        if ("disabled" == type) {
            typeName = '禁用';
            needFresh = true;
        } else if ("enabled" == type) {
            typeName = '启用';
            needFresh = true;
        } else if ("delete" == type) {
            typeName = '删除';
            needFresh = true;
        } else {
            return;
        }

        var id = $(this).parents('ul').attr("_id");

        layer.confirm('确定' + typeName + '?', {
            icon: 3,
            title: '系统提示',
            btn: ['确定', '取消']
        }, function (index) {
            layer.close(index);

            $.ajax({
                type: 'POST',
                url: url,
                data: {
                    "id": id,
                    "type": type
                },
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        layer.msg(typeName + '成功');
                        if (needFresh) {
                            //window.location.reload();
                            jobTable.fnDraw(false);
                        }
                    } else {
                        layer.msg(data.msg || typeName + '失败');
                    }
                }
            });
        });
    });

    // update
    $("#job_list").on('click', '.update', function () {

        var id = $(this).parents('ul').attr("_id");
        // var row = tableData['key' + id];
        var row = tableData[id];
        // fill base
        $("#updateModal .form input[name='id']").val(id);
        $("#updateModal .form input[name='host']").val(row.host).attr("readonly", "readonly")
        $("#updateModal .form input[name='port']").val(row.port).attr("readonly", "readonly")
        $("#updateModal .form input[name='enabled']").val(row.enabled ? "ENABLE" : "DISABLE").attr("readonly", "readonly")
        $("#updateModal .form input[name='liveStatus']").val(row.liveStatus == 0 ? "ALIVE" : "DEAD").attr("readonly", "readonly")
        $("#updateModal .form input[name='startTime']").val(row.startTime).attr("readonly", "readonly")
        $("#updateModal .form input[name='lastAliveTime']").val(row.lastAliveTime).attr("readonly", "readonly")
        $("#updateModal .form input[name='execTimeout']").val(row.execTimeout)
        $("#updateModal .form input[name='alarmTimeout']").val(row.alarmTimeout)
        $("#updateModal .form input[name='maxQueueSize']").val(row.maxQueueSize)
        $("#updateModal .form input[name='maxRetry']").val(row.maxRetry)
        // show
        $('#updateModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    var updateModalValidate = $("#updateModal .form").validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        submitHandler: function (form) {
            $("#updateModal .form input[name='enabled']").val($("#updateModal .form input[name='enabled']").val() == "ENABLE")
            $("#updateModal .form input[name='liveStatus']").val($("#updateModal .form input[name='liveStatus']").val() == "ALIVE" ? 0 : 1)
            // post
            $.post(base_url + "/hostinfo/update", $("#updateModal .form").serialize(), function (data, status) {
                if (data.code == "200") {
                    $('#updateModal').modal('hide');
                    layer.open({
                        title: '系统提示',
                        btn: ['确定'],
                        content: '更新成功',
                        icon: '1',
                        end: function (layero, index) {
                            //window.location.reload();
                            jobTable.fnDraw();
                        }
                    });
                } else {
                    layer.open({
                        title: '系统提示',
                        btn: ['确定'],
                        content: (data.msg || '更新失败'),
                        icon: '2'
                    });
                }
            });
        }
    });

});
