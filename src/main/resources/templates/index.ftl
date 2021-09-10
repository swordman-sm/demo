<!DOCTYPE html>
<html>
<head>
    <#import "./common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <!-- daterangepicker -->
    <link rel="stylesheet"
          href="${request.contextPath}/static/adminlte/bower_components/bootstrap-daterangepicker/daterangepicker.css">
    <title>调度中心</title>
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["xxljob_adminlte_settings"]?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if> ">
<div class="wrapper">
    <!-- header -->
    <@netCommon.commonHeader />
    <!-- left -->
    <@netCommon.commonLeft "index" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>节点信息</h1>
        </section>

        <!-- Main content -->
        <section class="content">

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
                                    <th name="host">主机地址</th>
                                    <th name="port">端口</th>
                                    <th name="enabled">启用</th>
                                    <th name="liveStatus">状态</th>
                                    <th name="startTime">启动时间</th>
                                    <th name="lastAliveTime">AliveTime</th>
                                    <th name="execTimeout">执行超时间隔</th>
                                    <th name="alarmTimeout">报警时间间隔</th>
                                    <th name="maxQueueSize">最大任务队列</th>
                                    <th name="autoRetry">自动重试</th>
                                    <th name="maxRetry">最大重试次数</th>
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
    <!-- /.content-wrapper -->

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
<#--<script src="${request.contextPath}/static/js/jobinfo.index.1.js"></script>-->
<script src="${request.contextPath}/static/js/jobinfo.index.2.js"></script>
<!-- daterangepicker -->
<script src="${request.contextPath}/static/adminlte/bower_components/moment/moment.min.js"></script>
<script src="${request.contextPath}/static/adminlte/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
<#-- echarts -->
<#--<script src="${request.contextPath}/static/plugins/echarts/echarts.common.min.js"></script>-->
<script src="${request.contextPath}/static/js/index.js"></script>
</body>
</html>
