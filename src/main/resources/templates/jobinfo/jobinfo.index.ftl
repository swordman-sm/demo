<!DOCTYPE html>
<html>
<head>
    <#import "../common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <!-- DataTables -->
    <link rel="stylesheet"
          href="${request.contextPath}/static/adminlte/bower_components/bootstrap-daterangepicker/daterangepicker.css">
    <link rel="stylesheet"
          href="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
    <title>任务调度中心</title>
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["xxljob_adminlte_settings"]?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if>">
<div class="wrapper">
    <!-- header -->
    <@netCommon.commonHeader />
    <!-- left -->
    <@netCommon.commonLeft "jobinfo" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>任务管理</h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-xs-2">
                    <div class="input-group">
                        <span class="input-group-addon">状态</span>
                        <select class="form-control" id="jobStatus">
                            <option value="wait">等待</option>
                            <option value="queue">队列</option>
                            <option value="run">执行</option>
                            <option value="stop">停止</option>
                        </select>
                    </div>
                </div>

                <#--                <div class="col-xs-2">-->
                <#--                    <label for="glueRemark" class="col-sm-2 control-label">TaskId</label>-->
                <div class="col-xs-3">
                    <label for="lastname" class="col-sm-3 control-label">启用</label>
                    <div class="col-sm-4"><input type="text" class="form-control" name="enabled"
                                                 placeholder="启用" maxlength="50"></div>
                    <label for="lastname" class="col-sm-3 control-label">状态</label>
                    <div class="col-sm-4"><input type="text" class="form-control" name="liveStatus"
                                                 placeholder="状态" maxlength="100"></div>
                </div>
                <#--&lt;#&ndash;                <div class="col-xs-2">&ndash;&gt;-->
                <#--&lt;#&ndash;                    <label for="glueRemark" class="col-sm-2 control-label">taks</label>&ndash;&gt;-->
                <#--&lt;#&ndash;                    <input type="text" class="form-control" id="glueRemark" placeholder="task" maxlength="64">&ndash;&gt;-->
                <#--&lt;#&ndash;                </div>&ndash;&gt;-->
                <#--                &lt;#&ndash;                </div>&ndash;&gt;-->
                <#--                &lt;#&ndash;                <div class="col-xs-1">&ndash;&gt;-->
                <#--                &lt;#&ndash;                    <div class="input-group">&ndash;&gt;-->
                <#--                &lt;#&ndash;                        <select class="form-control" id="triggerStatus">&ndash;&gt;-->
                <#--                &lt;#&ndash;                            <option value="-1">${I18n.system_all}</option>&ndash;&gt;-->
                <#--                &lt;#&ndash;                            <option value="0">${I18n.jobinfo_opt_stop}</option>&ndash;&gt;-->
                <#--                &lt;#&ndash;                            <option value="1">${I18n.jobinfo_opt_start}</option>&ndash;&gt;-->
                <#--                &lt;#&ndash;                        </select>&ndash;&gt;-->
                <#--                &lt;#&ndash;                    </div>&ndash;&gt;-->
                <#--                &lt;#&ndash;                </div>&ndash;&gt;-->
                <#--                &lt;#&ndash;                <div class="col-xs-2">&ndash;&gt;-->
                <#--                &lt;#&ndash;                    <div class="input-group">&ndash;&gt;-->
                <#--                &lt;#&ndash;                        <input type="text" class="form-control" id="jobDesc"&ndash;&gt;-->
                <#--                &lt;#&ndash;                               placeholder="${I18n.system_please_input}${I18n.jobinfo_field_jobdesc}">&ndash;&gt;-->
                <#--                &lt;#&ndash;                    </div>&ndash;&gt;-->
                <#--                &lt;#&ndash;                </div>&ndash;&gt;-->
                <#--                &lt;#&ndash;                <div class="col-xs-2">&ndash;&gt;-->
                <#--                &lt;#&ndash;                    <div class="input-group">&ndash;&gt;-->
                <#--                &lt;#&ndash;                        <input type="text" class="form-control" id="executorHandler"&ndash;&gt;-->
                <#--                &lt;#&ndash;                               placeholder="${I18n.system_please_input}JobHandler">&ndash;&gt;-->
                <#--                &lt;#&ndash;                    </div>&ndash;&gt;-->
                <#--                &lt;#&ndash;                </div>&ndash;&gt;-->
                <#--                &lt;#&ndash;                <div class="col-xs-2">&ndash;&gt;-->
                <#--                &lt;#&ndash;                    <div class="input-group">&ndash;&gt;-->
                <#--                &lt;#&ndash;                        <input type="text" class="form-control" id="author"&ndash;&gt;-->
                <#--                &lt;#&ndash;                               placeholder="${I18n.system_please_input}${I18n.jobinfo_field_author}">&ndash;&gt;-->
                <#--                &lt;#&ndash;                    </div>&ndash;&gt;-->
                <#--                &lt;#&ndash;                </div>&ndash;&gt;-->
                <div class="col-xs-1">
                    <button class="btn btn-block btn-info" id="searchBtn">搜索</button>
                </div>
                <div class="col-xs-1">
                    <button class="btn btn-block btn-success add" type="button">添加</button>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <#--<div class="box-header hide">
                            <h3 class="box-title">调度列表</h3>
                        </div>-->
                        <div class="box-body">
                            <table id="job_list" class="table table-bordered table-striped" width="100%">
                                <thead>
                                <tr>
                                    <th name="id">ID</th>
                                    <th name="taskId">TaskId</th>
                                    <th name="dpId">DpId</th>
                                    <th name="date">Date</th>
                                    <th name="priority">Priority</th>
                                    <th name="host">Host</th>
                                    <th name="port">Port</th>
                                    <th name="extendPara">ExtendPara</th>
                                    <th name="status">Status</th>
                                    <th name="remark">Remark</th>
                                    <th name="token">Token</th>
                                    <th name="autoRetry">AutoRetry</th>
                                    <th name="retry">Retry</th>
                                    <th name="createTime">CreateTime</th>
                                    <th name="updateTime">UpdateTime</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                                <tfoot></tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <!-- footer -->
    <@netCommon.commonFooter />
</div>
<@netCommon.commonScript />
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<!-- moment -->
<script src="${request.contextPath}/static/adminlte/bower_components/moment/moment.min.js"></script>
<#-- cronGen -->
<#--<script src="${request.contextPath}/static/plugins/cronGen/cronGen<#if I18n.admin_i18n?default('')?length gt 0 >_${I18n.admin_i18n}</#if>.js"></script>-->
<script src="${request.contextPath}/static/js/jobinfo.index.1.js"></script>
</body>
</html>
