$(function () {
    var jstat = $("#jobStatus").val()
    console.log("jstat===" + jstat)
    var url = base_url + "/jobinfo/" + jstat

    // init date tables

    function initTable(url) {
        return $("#job_list").dataTable({
            "deferRender": true,
            "processing": true,
            "serverSide": true,
            "ajax": {
                url: url,
                type: "post",
                data: function (d) {
                    console.log(d)
                    var obj = {};
                    obj.taskId = $('#taskId').val();
                    obj.dpId = $('#dpId').val();
                    obj.date = $('#date').val();
                    obj.priority = $('#priority').val();
                    obj.host = $('#host').val();
                    obj.port = $('#port').val();
                    obj.status = $('#status').val();
                    obj.autoRetry = $('#autoRetry').val();
                    obj.retry = $('#retry').val();
                    obj.token = $('#token').val();
                    obj.remark = $('#remark').val();
                    obj.extendPara = $('#extendPara').val();
                    obj.createTime = $('#createTime').val();
                    obj.updateTime = $('#updateTime').val();
                    obj.start = d.start;
                    obj.length = d.length;
                    obj.done = $('#done').val();
                    console.log(obj.start)
                    console.log(obj.length)
                    console.log(obj.done)
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
                    "data": 'taskId',
                    "visible": true,
                    "width": '4%'
                },
                {
                    "data": 'dpId',
                    "visible": true,
                    "width": '4%'
                },
                {
                    "data": 'date',
                    "visible": true,
                    "width": '6%',
                    'createdCell': function (td, cellData, rowData, row, col) {
                        $(td).attr("style","overflow:hidden;white-space:nowrap;text-overflow:ellipsis;")
                        $(td).attr('title', cellData);
                    }
                },
                {
                    "data": 'priority',
                    "visible": true,
                    "width": '4%'
                },
                {
                    "data": 'host',
                    "visible": true,
                    "width": '5%',
                    'createdCell': function (td, cellData, rowData, row, col) {
                        $(td).attr("style","overflow:hidden;white-space:nowrap;text-overflow:ellipsis;")
                        $(td).attr('title', cellData);
                    }
                },
                {
                    "data": 'port',
                    "visible": true,
                    "width": '4%',
                    'createdCell': function (td, cellData, rowData, row, col) {
                        $(td).attr("style","overflow:hidden;white-space:nowrap;text-overflow:ellipsis;")
                        $(td).attr('title', cellData);
                    }
                },
                {
                    "data": 'extendPara',
                    "visible": true,
                    "width": '7%',
                    'createdCell': function (td, cellData, rowData, row, col) {
                        $(td).attr("style","overflow:hidden;white-space:nowrap;text-overflow:ellipsis;")
                        $(td).attr('title', cellData);
                    }
                },
                {
                    "data": 'status',
                    "visible": true,
                    "width": '7%',
                    'createdCell': function (td, cellData, rowData, row, col) {
                        $(td).attr("style","overflow:hidden;white-space:nowrap;text-overflow:ellipsis;")
                        $(td).attr('title', cellData);
                    }
                },
                {
                    "data": 'remark',
                    "visible": true,
                    "width": '7%',
                    'createdCell': function (td, cellData, rowData, row, col) {
                        // $(td).css("width", "100%").css("word-break", "keep-all")
                        //     .css("white-space", "nowrap").css("overflow", "hidden")
                        //     .css("text-overflow", "ellipsis").css("-o-text-overflow", "ellipsis")
                        //     .css("-icab-text-overflow", "ellipsis").css("-khtml-text-overflow", "ellipsis")
                        //     .css("-moz-text-overflow", "ellipsis").css("-webkit-text-overflow", "ellipsis");
                        $(td).attr("style","overflow:hidden;white-space:nowrap;text-overflow:ellipsis;")
                        $(td).attr('title', cellData);
                    }
                },
                {
                    "data": 'token',
                    "visible": true,
                    "width": '7%',
                    'createdCell': function (td, cellData, rowData, row, col) {
                        $(td).attr("style","overflow:hidden;white-space:nowrap;text-overflow:ellipsis;")
                        $(td).attr('title', cellData);
                    }
                },
                {
                    "data": 'autoRetry',
                    "visible": true,
                    "width": '5%',
                    'createdCell': function (td, cellData, rowData, row, col) {
                        $(td).attr("style","overflow:hidden;white-space:nowrap;text-overflow:ellipsis;")
                        $(td).attr('title', cellData);
                    }
                },
                {
                    "data": 'retry',
                    "visible": true,
                    "width": '5%',
                    'createdCell': function (td, cellData, rowData, row, col) {
                        $(td).attr("style","overflow:hidden;white-space:nowrap;text-overflow:ellipsis;")
                        $(td).attr('title', cellData);
                    }
                },
                {
                    "data": 'createTime',
                    "visible": true,
                    "width": '8%',
                    'createdCell': function (td, cellData, rowData, row, col) {
                        $(td).attr("style","overflow:hidden;white-space:nowrap;text-overflow:ellipsis;")
                        $(td).attr('title', cellData);
                    }
                },
                {
                    "data": 'updateTime',
                    "visible": true,
                    "width": '8%',
                    'createdCell': function (td, cellData, rowData, row, col) {
                        $(td).attr("style","overflow:hidden;white-space:nowrap;text-overflow:ellipsis;")
                        $(td).attr('title', cellData);
                    }
                },
                {
                    "data": '操作',
                    "width": '10%',
                    "render": function (data, type, row) {
                        return function () {

                            // data
                            tableData['key' + row.id] = row;

                            // opt
                            var html = '<div class="btn-group">\n' +
                                '     <button type="button" class="btn btn-primary btn-sm">' + '操作' + '</button>\n' +
                                '     <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">\n' +
                                '       <span class="caret"></span>\n' +
                                '       <span class="sr-only">Toggle Dropdown</span>\n' +
                                '     </button>\n' +
                                '     <ul class="dropdown-menu" role="menu" _id="' + row.id + '" >\n' +
                                // '       <li><a href="javascript:void(0);" class="job_trigger" >' + '执行一次' + '</a></li>\n' +
                                // '       <li><a href="' + logHref + '">' + '查询日志' + '</a></li>\n' +
                                // '       <li><a href="javascript:void(0);" class="job_registryinfo" >' + '注册节点' + '</a></li>\n' +
                                // job_next_time_html +
                                // '       <li class="divider"></li>\n' +
                                // codeBtn +
                                // start_stop_div +
                                // '       <li><a href="javascript:void(0);" class="update" >' + '编辑' + '</a></li>\n' +
                                '       <li><a href="javascript:void(0);" class="job_delete">' + '删除' + '</a></li>\n' +
                                '       <li><a href="javascript:void(0);" class="job_retry">' + '重试' + '</a></li>\n' +
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
    }

    var jobTable = initTable(url)

    // table data
    var tableData = {};


    // jobGroup change
    $('#jobStatus').on('change', function () {
        var jstat = $("#jobStatus").val()
        if (jstat == "stop") {
            console.log("stop...................")
            $("#doned").show()
        } else {
            $("#doned").hide()
        }
        var change_url = base_url + "/jobinfo/" + jstat
        jobTable.fnClearTable();
        jobTable.fnDestroy();
        jobTable = null
        jobTable = initTable(change_url)
        jobTable.fnDraw();
    });

    // // search btn
    $('#searchBtn').on('click', function () {
        jobTable.fnDraw();
    });
    //
    // // jobGroup change
    // $('#jobGroup').on('change', function () {
    //     //reload
    //     var jobGroup = $('#jobGroup').val();
    //     window.location.href = base_url + "/jobinfo?jobGroup=" + jobGroup;
    // });
    //
    // // job operate
    // $("#job_list").on('click', '.job_operate', function () {
    //     var typeName;
    //     var url;
    //     var needFresh = false;
    //
    //     var type = $(this).attr("_type");
    //     if ("job_pause" == type) {
    //         typeName = I18n.jobinfo_opt_stop;
    //         url = base_url + "/jobinfo/stop";
    //         needFresh = true;
    //     } else if ("job_resume" == type) {
    //         typeName = I18n.jobinfo_opt_start;
    //         url = base_url + "/jobinfo/start";
    //         needFresh = true;
    //     } else if ("job_del" == type) {
    //         typeName = I18n.system_opt_del;
    //         url = base_url + "/jobinfo/remove";
    //         needFresh = true;
    //     } else {
    //         return;
    //     }
    //
    //     var id = $(this).parents('ul').attr("_id");
    //
    //     layer.confirm(I18n.system_ok + typeName + '?', {
    //         icon: 3,
    //         title: I18n.system_tips,
    //         btn: [I18n.system_ok, I18n.system_cancel]
    //     }, function (index) {
    //         layer.close(index);
    //
    //         $.ajax({
    //             type: 'POST',
    //             url: url,
    //             data: {
    //                 "id": id
    //             },
    //             dataType: "json",
    //             success: function (data) {
    //                 if (data.code == 200) {
    //                     layer.msg(typeName + I18n.system_success);
    //                     if (needFresh) {
    //                         //window.location.reload();
    //                         jobTable.fnDraw(false);
    //                     }
    //                 } else {
    //                     layer.msg(data.msg || typeName + I18n.system_fail);
    //                 }
    //             }
    //         });
    //     });
    // });

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
    // $("#jobTriggerModal").on('hide.bs.modal', function () {
    //     $("#jobTriggerModal .form")[0].reset();
    // });

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

    // add
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
    //             required: '请输入任务描述'
    //         },
    //         author: {
    //             required: '请输入负责人'
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


    // job_delete
    $("#job_list").on('click', '.job_delete', function () {

        var id = $(this).parents('ul').attr("_id");
        var row = tableData['key' + id];
        var date = row.date
        var taskId = row.taskId
        var dpId = row.dpId
        var needFresh = true;
        var typeName = '删除'
        var url = base_url + "/jobinfo/deleteTask";
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
                    "date": date,
                    "taskId": taskId,
                    "dpId": dpId,
                    "type": "delete"
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

    // job_delete
    $("#job_list").on('click', '.job_retry', function () {

        var id = $(this).parents('ul').attr("_id");
        var row = tableData['key' + id];
        var needFresh = true;
        var typeName = '重试'
        var url = base_url + "/jobinfo/retryTask";
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
                    "date": row.date,
                    "taskId": row.taskId,
                    "dpId": row.dpId,
                    "priority": row.priority,
                    "extendPara": row.extendPara,
                    "token": row.token,
                    "autoRetry": row.autoRetry,
                    "retry": 0
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


    // // update
    // $("#job_list").on('click', '.update', function () {
    //
    //     var id = $(this).parents('ul').attr("_id");
    //     var row = tableData['key' + id];
    //
    //     // fill base
    //     $("#updateModal .form input[name='id']").val(row.id);
    //     $('#updateModal .form select[name=jobGroup] option[value=' + row.jobGroup + ']').prop('selected', true);
    //     $("#updateModal .form input[name='jobDesc']").val(row.jobDesc);
    //     $("#updateModal .form input[name='author']").val(row.author);
    //     $("#updateModal .form input[name='alarmEmail']").val(row.alarmEmail);
    //
    //     // fill trigger
    //     $('#updateModal .form select[name=scheduleType] option[value=' + row.scheduleType + ']').prop('selected', true);
    //     $("#updateModal .form input[name='scheduleConf']").val(row.scheduleConf);
    //     if (row.scheduleType == 'CRON') {
    //         $("#updateModal .form input[name='schedule_conf_CRON']").val(row.scheduleConf);
    //     } else if (row.scheduleType == 'FIX_RATE') {
    //         $("#updateModal .form input[name='schedule_conf_FIX_RATE']").val(row.scheduleConf);
    //     } else if (row.scheduleType == 'FIX_DELAY') {
    //         $("#updateModal .form input[name='schedule_conf_FIX_DELAY']").val(row.scheduleConf);
    //     }
    //
    //     // 》init scheduleType
    //     $("#updateModal .form select[name=scheduleType]").change();
    //
    //     // fill job
    //     $('#updateModal .form select[name=glueType] option[value=' + row.glueType + ']').prop('selected', true);
    //     $("#updateModal .form input[name='executorHandler']").val(row.executorHandler);
    //     $("#updateModal .form textarea[name='executorParam']").val(row.executorParam);
    //
    //     // 》init glueType
    //     $("#updateModal .form select[name=glueType]").change();
    //
    //     // 》init-cronGen
    //     $("#updateModal .form input[name='schedule_conf_CRON']").show().siblings().remove();
    //     $("#updateModal .form input[name='schedule_conf_CRON']").cronGen({});
    //
    //     // fill advanced
    //     $('#updateModal .form select[name=executorRouteStrategy] option[value=' + row.executorRouteStrategy + ']').prop('selected', true);
    //     $("#updateModal .form input[name='childJobId']").val(row.childJobId);
    //     $('#updateModal .form select[name=misfireStrategy] option[value=' + row.misfireStrategy + ']').prop('selected', true);
    //     $('#updateModal .form select[name=executorBlockStrategy] option[value=' + row.executorBlockStrategy + ']').prop('selected', true);
    //     $("#updateModal .form input[name='executorTimeout']").val(row.executorTimeout);
    //     $("#updateModal .form input[name='executorFailRetryCount']").val(row.executorFailRetryCount);
    //
    //     // show
    //     $('#updateModal').modal({backdrop: false, keyboard: false}).modal('show');
    // });
    // var updateModalValidate = $("#updateModal .form").validate({
    //     errorElement: 'span',
    //     errorClass: 'help-block',
    //     focusInvalid: true,
    //
    //     rules: {
    //         jobDesc: {
    //             required: true,
    //             maxlength: 50
    //         },
    //         author: {
    //             required: true
    //         }
    //     },
    //     messages: {
    //         jobDesc: {
    //             required: I18n.system_please_input + I18n.jobinfo_field_jobdesc
    //         },
    //         author: {
    //             required: I18n.system_please_input + I18n.jobinfo_field_author
    //         }
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
    //         // process executorTimeout + executorFailRetryCount
    //         var executorTimeout = $("#updateModal .form input[name='executorTimeout']").val();
    //         if (!/^\d+$/.test(executorTimeout)) {
    //             executorTimeout = 0;
    //         }
    //         $("#updateModal .form input[name='executorTimeout']").val(executorTimeout);
    //         var executorFailRetryCount = $("#updateModal .form input[name='executorFailRetryCount']").val();
    //         if (!/^\d+$/.test(executorFailRetryCount)) {
    //             executorFailRetryCount = 0;
    //         }
    //         $("#updateModal .form input[name='executorFailRetryCount']").val(executorFailRetryCount);
    //
    //
    //         // process schedule_conf
    //         var scheduleType = $("#updateModal .form select[name='scheduleType']").val();
    //         var scheduleConf;
    //         if (scheduleType == 'CRON') {
    //             scheduleConf = $("#updateModal .form input[name='cronGen_display']").val();
    //         } else if (scheduleType == 'FIX_RATE') {
    //             scheduleConf = $("#updateModal .form input[name='schedule_conf_FIX_RATE']").val();
    //         } else if (scheduleType == 'FIX_DELAY') {
    //             scheduleConf = $("#updateModal .form input[name='schedule_conf_FIX_DELAY']").val();
    //         }
    //         $("#updateModal .form input[name='scheduleConf']").val(scheduleConf);
    //
    //         // post
    //         $.post(base_url + "/jobinfo/update", $("#updateModal .form").serialize(), function (data, status) {
    //             if (data.code == "200") {
    //                 $('#updateModal').modal('hide');
    //                 layer.open({
    //                     title: I18n.system_tips,
    //                     btn: [I18n.system_ok],
    //                     content: I18n.system_update_suc,
    //                     icon: '1',
    //                     end: function (layero, index) {
    //                         //window.location.reload();
    //                         jobTable.fnDraw();
    //                     }
    //                 });
    //             } else {
    //                 layer.open({
    //                     title: I18n.system_tips,
    //                     btn: [I18n.system_ok],
    //                     content: (data.msg || I18n.system_update_fail),
    //                     icon: '2'
    //                 });
    //             }
    //         });
    //     }
    // });
    // $("#updateModal").on('hide.bs.modal', function () {
    //     updateModalValidate.resetForm();
    //     $("#updateModal .form")[0].reset();
    //     $("#updateModal .form .form-group").removeClass("has-error");
    // });
    //
    // /**
    //  * find title by name, GlueType
    //  */
    // function findGlueTypeTitle(glueType) {
    //     var glueTypeTitle;
    //     $("#addModal .form select[name=glueType] option").each(function () {
    //         var name = $(this).val();
    //         var title = $(this).text();
    //         if (glueType == name) {
    //             glueTypeTitle = title;
    //             return false
    //         }
    //     });
    //     return glueTypeTitle;
    // }

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
