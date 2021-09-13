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

                <div class="col-xs-2">
                    <div class="input-group">
                		<span class="input-group-addon">
	                  		连锁ID
	                	</span>
                        <input type="text" class="form-control" id="dpId">
                    </div>
                </div>

                <div class="col-xs-2">
                    <div class="input-group">
                		<span class="input-group-addon">
	                  		任务ID
	                	</span>
                        <input type="text" class="form-control" id="taskId">
                    </div>
                </div>

                <div class="col-xs-1">
                    <button class="btn btn-block btn-info" id="searchBtn">搜索</button>
                </div>
<#--                <div class="col-xs-1">-->
<#--                    <button class="btn btn-block btn-success add" type="button">添加</button>-->
<#--                </div>-->
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
