<!DOCTYPE html>
<html>
<head>
    <#import "./common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <!-- daterangepicker -->
    <link rel="stylesheet"
          href="${request.contextPath}/static/adminlte/bower_components/bootstrap-daterangepicker/daterangepicker.css">
    <title>Dtimer-Day调度中心</title>
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
                                    <th name="id">ID</th>
                                    <th name="host">主机地址</th>
                                    <th name="port">端口</th>
                                    <th name="enabled">启用</th>
                                    <th name="liveStatus">状态</th>
                                    <th name="startTime">启动时间</th>
                                    <th name="lastAliveTime">AliveTime</th>
                                    <th name="execTimeout">ExecTimeout(min)</th>
                                    <th name="alarmTimeout">AlarmTimeout(min)</th>
                                    <th name="maxQueueSize">最大任务队列</th>
                                    <th name="maxRetry">最大重试次数</th>
                                    <th name="queue">Queue</th>
                                    <th name="run">Run</th>
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


    <!-- 更新.模态框 -->
    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">更新节点配置信息</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal form" role="form">

                        <p style="margin: 0 0 10px;text-align: left;border-bottom: 1px solid #e5e5e5;color: gray;">
                            主机信息</p>

                        <div class="form-group">
                            <label for="firstname" class="col-sm-2 control-label">主机地址</label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="host" maxlength="100">
                            </div>

                            <label for="lastname" class="col-sm-2 control-label">端口</label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="port" maxlength="50">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">启用</label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="enabled"
                                                         placeholder="启用" maxlength="50"></div>
                            <label for="lastname" class="col-sm-2 control-label">状态</label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="liveStatus"
                                                         placeholder="状态" maxlength="100"></div>
                        </div>

                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">启动时间</label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="startTime"
                                                         placeholder="启动时间" maxlength="50"></div>
                            <label for="lastname" class="col-sm-2 control-label">AliveTime</label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="lastAliveTime"
                                                         placeholder="AliveTime" maxlength="100"></div>
                        </div>

                        <br>
                        <p style="margin: 0 0 10px;text-align: left;border-bottom: 1px solid #e5e5e5;color: gray;">
                            超时配置</p> <#-- 调度配置 -->
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">执行超时间隔(min)<font
                                        color="red">*</font></label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="execTimeout"
                                                         placeholder="执行超时间隔(min)" maxlength="50"></div>
                            <label for="lastname" class="col-sm-2 control-label">报警时间间隔(min)<font
                                        color="black">*</font></label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="alarmTimeout"
                                                         placeholder="报警时间间隔(min)" maxlength="100"></div>
                        </div>

                        <br>
                        <p style="margin: 0 0 10px;text-align: left;border-bottom: 1px solid #e5e5e5;color: gray;">
                            其他参数</p> <#-- 任务配置 -->

                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">最大任务队列<font
                                        color="red">*</font></label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="maxQueueSize"
                                                         placeholder="最大任务队列" maxlength="50"></div>
                            <label for="lastname" class="col-sm-2 control-label">最大重试次数<font
                                        color="black">*</font></label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="maxRetry"
                                                         placeholder="最大重试次数" maxlength="100"></div>
                        </div>

                        <hr>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">
                                <button type="submit" class="btn btn-primary">保存</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                <input type="hidden" name="id">
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
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
<#--<script src="${request.contextPath}/static/adminlte/bower_components/moment/moment.min.js"></script>-->
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
