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

<#--<style type="text/css">-->
<#--    .table th, .table td {-->
<#--        text-align: center;-->
<#--        vertical-align: middle !important;-->
<#--    }-->

<#--    table {-->
<#--        width: 100px;-->
<#--        table-layout: fixed; /* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */-->
<#--    }-->

<#--    td {-->
<#--        width: 100%;-->
<#--        word-break: keep-all; /* 不换行 */-->
<#--        white-space: nowrap; /* 不换行 */-->
<#--        overflow: hidden; /* 内容超出宽度时隐藏超出部分的内容 */-->
<#--        text-overflow: ellipsis; /* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用*/-->
<#--        -o-text-overflow: ellipsis;-->
<#--        -icab-text-overflow: ellipsis;-->
<#--        -khtml-text-overflow: ellipsis;-->
<#--        -moz-text-overflow: ellipsis;-->
<#--        -webkit-text-overflow: ellipsis;-->
<#--    }-->

<#--    .table {-->
<#--        table-layout: fixed;-->
<#--    }-->
<#--</style>-->

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

                <div class="col-xs-2" id="doned" hidden="hidden">
                    <div class="input-group">
                        <span class="input-group-addon">类型</span>
                        <select class="form-control" id="done">
                            <option value="all">全部</option>
                            <option value="true">正常</option>
                            <option value="false">异常</option>
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
                            <table id="job_list" style="table-layout:fixed" class="table table-bordered table-hover table-striped" width="100%">
                                <thead>
                                <tr>
                                    <th name="id">ID</th>
                                    <th name="taskId">任务ID</th>
                                    <th name="dpId">连锁ID</th>
                                    <th name="date">报表时间</th>
                                    <th name="priority">优先级</th>
                                    <th name="host">节点地址</th>
                                    <th name="port">端口</th>
                                    <th name="extendPara">扩展参数</th>
                                    <th name="status">状态</th>
                                    <th name="remark">备注</th>
                                    <th name="token">密钥</th>
                                    <th name="autoRetry">自动重试</th>
                                    <th name="retry">重试次数</th>
                                    <th name="createTime">创建时间</th>
                                    <th name="updateTime">更新时间</th>
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
