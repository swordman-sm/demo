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
                "width": '7%'
            },
            {
                "data": 'port',
                "visible": true,
                "width": '5%'
            },
            {
                "data": 'enabled',
                "visible": true,
                "width": '7%',
                "render": function (data, type, row) {
                    // status
                    if (data) {
                        return '<small class="label label-success" >ENABLE</small>';
                    } else {
                        return '<small class="label label-default" >DISABLE</small>';
                    }
                    return data;
                }
            },
            {
                "data": 'liveStatus',
                "visible": true,
                "width": '7%',
                "render": function (data, type, row) {
                    // status
                    if (0 == data) {
                        return '<small class="label label-success" >ALIVE</small>';
                    } else {
                        return '<small class="label label-default" >DEAD</small>';
                    }
                    return data;
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

    // // job trigger
    // $("#job_list").on('click', '.job_trigger', function () {
    //     var id = $(this).parents('ul').attr("_id");
    //     var row = tableData['key' + id];
    //
    //     $("#jobTriggerModal .form input[name='id']").val(row.id);
    //     $("#jobTriggerModal .form textarea[name='executorParam']").val(row.executorParam);
    //
    //     $('#jobTriggerModal').modal({backdrop: false, keyboard: false}).modal('show');
    // });
    // $("#jobTriggerModal .ok").on('click', function () {
    //     $.ajax({
    //         type: 'POST',
    //         url: base_url + "/jobinfo/trigger",
    //         data: {
    //             "id": $("#jobTriggerModal .form input[name='id']").val(),
    //             "executorParam": $("#jobTriggerModal .textarea[name='executorParam']").val(),
    //             "addressList": $("#jobTriggerModal .textarea[name='addressList']").val()
    //         },
    //         dataType: "json",
    //         success: function (data) {
    //             if (data.code == 200) {
    //                 $('#jobTriggerModal').modal('hide');
    //
    //                 layer.msg(I18n.jobinfo_opt_run + I18n.system_success);
    //             } else {
    //                 layer.msg(data.msg || I18n.jobinfo_opt_run + I18n.system_fail);
    //             }
    //         }
    //     });
    // });
    $("#jobTriggerModal").on('hide.bs.modal', function () {
        $("#jobTriggerModal .form")[0].reset();
    });
    //
    //
    // // job registryinfo
    // $("#job_list").on('click', '.job_registryinfo', function () {
    //     var id = $(this).parents('ul').attr("_id");
    //     var row = tableData['key' + id];
    //
    //     var jobGroup = row.jobGroup;
    //
    //     $.ajax({
    //         type: 'POST',
    //         url: base_url + "/jobgroup/loadById",
    //         data: {
    //             "id": jobGroup
    //         },
    //         dataType: "json",
    //         success: function (data) {
    //
    //             var html = '<div>';
    //             if (data.code == 200 && data.content.registryList) {
    //                 for (var index in data.content.registryList) {
    //                     html += (parseInt(index) + 1) + '. <span class="badge bg-green" >' + data.content.registryList[index] + '</span><br>';
    //                 }
    //             }
    //             html += '</div>';
    //
    //             layer.open({
    //                 title: I18n.jobinfo_opt_registryinfo,
    //                 btn: [I18n.system_ok],
    //                 content: html
    //             });
    //
    //         }
    //     });
    //
    // });
    //
    // // job_next_time
    // $("#job_list").on('click', '.job_next_time', function () {
    //     var id = $(this).parents('ul').attr("_id");
    //     var row = tableData['key' + id];
    //
    //     $.ajax({
    //         type: 'POST',
    //         url: base_url + "/jobinfo/nextTriggerTime",
    //         data: {
    //             "scheduleType": row.scheduleType,
    //             "scheduleConf": row.scheduleConf
    //         },
    //         dataType: "json",
    //         success: function (data) {
    //
    //             if (data.code != 200) {
    //                 layer.open({
    //                     title: I18n.jobinfo_opt_next_time,
    //                     btn: [I18n.system_ok],
    //                     content: data.msg
    //                 });
    //             } else {
    //                 var html = '<center>';
    //                 if (data.code == 200 && data.content) {
    //                     for (var index in data.content) {
    //                         html += '<span>' + data.content[index] + '</span><br>';
    //                     }
    //                 }
    //                 html += '</center>';
    //
    //                 layer.open({
    //                     title: I18n.jobinfo_opt_next_time,
    //                     btn: [I18n.system_ok],
    //                     content: html
    //                 });
    //             }
    //
    //         }
    //     });
    //
    // });
    //
    // // add
    // $(".add").click(function () {
    //
    //     // init-cronGen
    //     $("#addModal .form input[name='schedule_conf_CRON']").show().siblings().remove();
    //     $("#addModal .form input[name='schedule_conf_CRON']").cronGen({});
    //
    //     // 》init scheduleType
    //     $("#updateModal .form select[name=scheduleType]").change();
    //
    //     // 》init glueType
    //     $("#updateModal .form select[name=glueType]").change();
    //
    //     $('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
    // });
    // var addModalValidate = $("#addModal .form").validate({
    //     errorElement: 'span',
    //     errorClass: 'help-block',
    //     focusInvalid: true,
    //     rules: {
    //         jobDesc: {
    //             required: true,
    //             maxlength: 50
    //         },
    //         author: {
    //             required: true
    //         }/*,
    //         executorTimeout : {
    //             digits:true
    //         },
    //         executorFailRetryCount : {
    //             digits:true
    //         }*/
    //     },
    //     messages: {
    //         jobDesc: {
    //             required: I18n.system_please_input + I18n.jobinfo_field_jobdesc
    //         },
    //         author: {
    //             required: I18n.system_please_input + I18n.jobinfo_field_author
    //         }/*,
    //         executorTimeout : {
    //             digits: I18n.system_please_input + I18n.system_digits
    //         },
    //         executorFailRetryCount : {
    //             digits: I18n.system_please_input + I18n.system_digits
    //         }*/
    //     },
    //     highlight: function (element) {
    //         $(element).closest('.form-group').addClass('has-error');
    //     },
    //     success: function (label) {
    //         label.closest('.form-group').removeClass('has-error');
    //         label.remove();
    //     },
    //     errorPlacement: function (error, element) {
    //         element.parent('div').append(error);
    //     },
    //     submitHandler: function (form) {
    //
    //         // process executorTimeout+executorFailRetryCount
    //         var executorTimeout = $("#addModal .form input[name='executorTimeout']").val();
    //         if (!/^\d+$/.test(executorTimeout)) {
    //             executorTimeout = 0;
    //         }
    //         $("#addModal .form input[name='executorTimeout']").val(executorTimeout);
    //         var executorFailRetryCount = $("#addModal .form input[name='executorFailRetryCount']").val();
    //         if (!/^\d+$/.test(executorFailRetryCount)) {
    //             executorFailRetryCount = 0;
    //         }
    //         $("#addModal .form input[name='executorFailRetryCount']").val(executorFailRetryCount);
    //
    //         // process schedule_conf
    //         var scheduleType = $("#addModal .form select[name='scheduleType']").val();
    //         var scheduleConf;
    //         if (scheduleType == 'CRON') {
    //             scheduleConf = $("#addModal .form input[name='cronGen_display']").val();
    //         } else if (scheduleType == 'FIX_RATE') {
    //             scheduleConf = $("#addModal .form input[name='schedule_conf_FIX_RATE']").val();
    //         } else if (scheduleType == 'FIX_DELAY') {
    //             scheduleConf = $("#addModal .form input[name='schedule_conf_FIX_DELAY']").val();
    //         }
    //         $("#addModal .form input[name='scheduleConf']").val(scheduleConf);
    //
    //         $.post(base_url + "/jobinfo/add", $("#addModal .form").serialize(), function (data, status) {
    //             if (data.code == "200") {
    //                 $('#addModal').modal('hide');
    //                 layer.open({
    //                     title: I18n.system_tips,
    //                     btn: [I18n.system_ok],
    //                     content: I18n.system_add_suc,
    //                     icon: '1',
    //                     end: function (layero, index) {
    //                         jobTable.fnDraw();
    //                         //window.location.reload();
    //                     }
    //                 });
    //             } else {
    //                 layer.open({
    //                     title: I18n.system_tips,
    //                     btn: [I18n.system_ok],
    //                     content: (data.msg || I18n.system_add_fail),
    //                     icon: '2'
    //                 });
    //             }
    //         });
    //     }
    // });
    // $("#addModal").on('hide.bs.modal', function () {
    //     addModalValidate.resetForm();
    //     $("#addModal .form")[0].reset();
    //     $("#addModal .form .form-group").removeClass("has-error");
    //     $(".remote_panel").show();	// remote
    //
    //     $("#addModal .form input[name='executorHandler']").removeAttr("readonly");
    // });
    //
    // // scheduleType change
    // $(".scheduleType").change(function () {
    //     var scheduleType = $(this).val();
    //     $(this).parents("form").find(".schedule_conf").hide();
    //     $(this).parents("form").find(".schedule_conf_" + scheduleType).show();
    //
    // });
    //
    // // glueType change
    // $(".glueType").change(function () {
    //     // executorHandler
    //     var $executorHandler = $(this).parents("form").find("input[name='executorHandler']");
    //     var glueType = $(this).val();
    //     if ('BEAN' != glueType) {
    //         $executorHandler.val("");
    //         $executorHandler.attr("readonly", "readonly");
    //     } else {
    //         $executorHandler.removeAttr("readonly");
    //     }
    // });
    //
    // $("#addModal .glueType").change(function () {
    //     // glueSource
    //     var glueType = $(this).val();
    //     if ('GLUE_GROOVY' == glueType) {
    //         $("#addModal .form textarea[name='glueSource']").val($("#addModal .form .glueSource_java").val());
    //     } else if ('GLUE_SHELL' == glueType) {
    //         $("#addModal .form textarea[name='glueSource']").val($("#addModal .form .glueSource_shell").val());
    //     } else if ('GLUE_PYTHON' == glueType) {
    //         $("#addModal .form textarea[name='glueSource']").val($("#addModal .form .glueSource_python").val());
    //     } else if ('GLUE_PHP' == glueType) {
    //         $("#addModal .form textarea[name='glueSource']").val($("#addModal .form .glueSource_php").val());
    //     } else if ('GLUE_NODEJS' == glueType) {
    //         $("#addModal .form textarea[name='glueSource']").val($("#addModal .form .glueSource_nodejs").val());
    //     } else if ('GLUE_POWERSHELL' == glueType) {
    //         $("#addModal .form textarea[name='glueSource']").val($("#addModal .form .glueSource_powershell").val());
    //     } else {
    //         $("#addModal .form textarea[name='glueSource']").val("");
    //     }
    // });
    //
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

    // $("#updateModal .form").on('click', ':submit', function () {
    //
    //     var host = $("#updateModal .form input[name='host']").val()
    //     var port = $("#updateModal .form input[name='port']").val()
    //     var enabled = $("#updateModal .form input[name='enabled']").val() == "ENABLE"
    //     var liveStatus = $("#updateModal .form input[name='liveStatus']").val() == "ALIVE" ? 0 : 1
    //     var startTime = $("#updateModal .form input[name='startTime']").val()
    //     var lastAliveTime = $("#updateModal .form input[name='lastAliveTime']").val()
    //     var execTimeout = $("#updateModal .form input[name='execTimeout']").val()
    //     var alarmTimeout = $("#updateModal .form input[name='alarmTimeout']").val()
    //     var maxQueueSize = $("#updateModal .form input[name='maxQueueSize']").val()
    //     var maxRetry = $("#updateModal .form input[name='maxRetry']").val()
    //
    //     layer.confirm('确定更新?', {
    //         icon: 3,
    //         title: '系统提示',
    //         btn: ['确定', '取消']
    //     }, function () {
    //
    //         $.ajax({
    //             type: 'POST',
    //             url: base_url + "/jobinfo/update",
    //             data: {
    //                 "host": host,
    //                 "port": port,
    //                 "enabled": enabled,
    //                 "liveStatus": liveStatus,
    //                 "startTime": startTime,
    //                 "lastAliveTime": lastAliveTime,
    //                 "execTimeout": execTimeout,
    //                 "alarmTimeout": alarmTimeout,
    //                 "maxQueueSize": maxQueueSize,
    //                 "maxRetry": maxRetry
    //             },
    //             dataType: "json",
    //             success: function (data) {
    //                 console.log("====================================================")
    //                 if (data.code == 200) {
    //                     layer.msg('更新成功');
    //                     // if (needFresh) {
    //                     //     //window.location.reload();
    //                     //     // jobTable.fnDraw(false);
    //                     // }
    //                 } else {
    //                     layer.msg(data.msg || '更新失败');
    //                 }
    //             }
    //         });
    //     });
    //
    //     // post
    //     // $.post(base_url + "/jobinfo/update", $("#updateModal .form").serialize(), function (data, status) {
    //     //     console.log("data")
    //     //     console.log(data)
    //     //     if (data.code == "200") {
    //     //         // $('#updateModal').modal('hide');
    //     //         layer.open({
    //     //             title: '系统提示',
    //     //             btn: ['确定'],
    //     //             content: '更新成功',
    //     //             icon: '1',
    //     //             end: function (layero, index) {
    //     //                 //window.location.reload();
    //     //                 // jobTable.fnDraw();
    //     //             }
    //     //         });
    //     //     } else {
    //     //         layer.open({
    //     //             title: '系统提示',
    //     //             btn: ['确定'],
    //     //             content: (data.msg || '更新失败'),
    //     //             icon: '2'
    //     //         });
    //     //     }
    //     // });
    //
    //
    // });

    var updateModalValidate = $("#updateModal .form").validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,

        // rules: {
        //     jobDesc: {
        //         required: true,
        //         maxlength: 50
        //     },
        //     author: {
        //         required: true
        //     }
        // },
        // messages: {
        //     jobDesc: {
        //         required: '请输入任务描述'
        //     },
        //     author: {
        //         required: '请输入负责人'
        //     }
        // },

        submitHandler: function (form) {

            // process executorTimeout + executorFailRetryCount
            // var executorTimeout = $("#updateModal .form input[name='executorTimeout']").val();
            // if (!/^\d+$/.test(executorTimeout)) {
            //     executorTimeout = 0;
            // }
            // $("#updateModal .form input[name='executorTimeout']").val(executorTimeout);
            // var executorFailRetryCount = $("#updateModal .form input[name='executorFailRetryCount']").val();
            // if (!/^\d+$/.test(executorFailRetryCount)) {
            //     executorFailRetryCount = 0;
            // }
            // $("#updateModal .form input[name='executorFailRetryCount']").val(executorFailRetryCount);
            //
            //
            // // process schedule_conf
            // var scheduleType = $("#updateModal .form select[name='scheduleType']").val();
            // var scheduleConf;
            // if (scheduleType == 'CRON') {
            //     scheduleConf = $("#updateModal .form input[name='cronGen_display']").val();
            // } else if (scheduleType == 'FIX_RATE') {
            //     scheduleConf = $("#updateModal .form input[name='schedule_conf_FIX_RATE']").val();
            // } else if (scheduleType == 'FIX_DELAY') {
            //     scheduleConf = $("#updateModal .form input[name='schedule_conf_FIX_DELAY']").val();
            // }
            // $("#updateModal .form input[name='scheduleConf']").val(scheduleConf);

            // var host = $("#updateModal .form input[name='host']").val()
            // var port = $("#updateModal .form input[name='port']").val()
            $("#updateModal .form input[name='enabled']").val($("#updateModal .form input[name='enabled']").val() == "ENABLE")
            $("#updateModal .form input[name='liveStatus']").val($("#updateModal .form input[name='liveStatus']").val() == "ALIVE" ? 0 : 1)
            // var startTime = $("#updateModal .form input[name='startTime']").val()
            // var lastAliveTime = $("#updateModal .form input[name='lastAliveTime']").val()
            // var execTimeout = $("#updateModal .form input[name='execTimeout']").val()
            // var alarmTimeout = $("#updateModal .form input[name='alarmTimeout']").val()
            // var maxQueueSize = $("#updateModal .form input[name='maxQueueSize']").val()
            // var maxRetry = $("#updateModal .form input[name='maxRetry']").val()
            // var param = $("#updateModal .form").serialize();
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
    // $("#updateModal").on('hide.bs.modal', function () {
    //     updateModalValidate.resetForm();
    //     $("#updateModal .form")[0].reset();
    //     $("#updateModal .form .form-group").removeClass("has-error");
    // });

    /**
     * find title by name, GlueType
     */
    function findGlueTypeTitle(glueType) {
        var glueTypeTitle;
        $("#addModal .form select[name=glueType] option").each(function () {
            var name = $(this).val();
            var title = $(this).text();
            if (glueType == name) {
                glueTypeTitle = title;
                return false
            }
        });
        return glueTypeTitle;
    }

    //
    // // job_copy
    // $("#job_list").on('click', '.job_copy', function () {
    //
    //     var id = $(this).parents('ul').attr("_id");
    //     var row = tableData['key' + id];
    //
    //     // fill base
    //     $('#addModal .form select[name=jobGroup] option[value=' + row.jobGroup + ']').prop('selected', true);
    //     $("#addModal .form input[name='jobDesc']").val(row.jobDesc);
    //     $("#addModal .form input[name='author']").val(row.author);
    //     $("#addModal .form input[name='alarmEmail']").val(row.alarmEmail);
    //
    //     // fill trigger
    //     $('#addModal .form select[name=scheduleType] option[value=' + row.scheduleType + ']').prop('selected', true);
    //     $("#addModal .form input[name='scheduleConf']").val(row.scheduleConf);
    //     if (row.scheduleType == 'CRON') {
    //         $("#addModal .form input[name='schedule_conf_CRON']").val(row.scheduleConf);
    //     } else if (row.scheduleType == 'FIX_RATE') {
    //         $("#addModal .form input[name='schedule_conf_FIX_RATE']").val(row.scheduleConf);
    //     } else if (row.scheduleType == 'FIX_DELAY') {
    //         $("#addModal .form input[name='schedule_conf_FIX_DELAY']").val(row.scheduleConf);
    //     }
    //
    //     // 》init scheduleType
    //     $("#addModal .form select[name=scheduleType]").change();
    //
    //     // fill job
    //     $('#addModal .form select[name=glueType] option[value=' + row.glueType + ']').prop('selected', true);
    //     $("#addModal .form input[name='executorHandler']").val(row.executorHandler);
    //     $("#addModal .form textarea[name='executorParam']").val(row.executorParam);
    //
    //     // 》init glueType
    //     $("#addModal .form select[name=glueType]").change();
    //
    //     // 》init-cronGen
    //     $("#addModal .form input[name='schedule_conf_CRON']").show().siblings().remove();
    //     $("#addModal .form input[name='schedule_conf_CRON']").cronGen({});
    //
    //     // fill advanced
    //     $('#addModal .form select[name=executorRouteStrategy] option[value=' + row.executorRouteStrategy + ']').prop('selected', true);
    //     $("#addModal .form input[name='childJobId']").val(row.childJobId);
    //     $('#addModal .form select[name=misfireStrategy] option[value=' + row.misfireStrategy + ']').prop('selected', true);
    //     $('#addModal .form select[name=executorBlockStrategy] option[value=' + row.executorBlockStrategy + ']').prop('selected', true);
    //     $("#addModal .form input[name='executorTimeout']").val(row.executorTimeout);
    //     $("#addModal .form input[name='executorFailRetryCount']").val(row.executorFailRetryCount);
    //
    //     // show
    //     $('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
    // });

});
