<!DOCTYPE html>
<html>
<head>
    <#import "./common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <!-- daterangepicker -->
    <link rel="stylesheet"
          href="${request.contextPath}/static/adminlte/bower_components/bootstrap-daterangepicker/daterangepicker.css">
    <title>${I18n.admin_name}</title>
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
                <#--执行器-->
                <div class="col-xs-3">
                    <div class="input-group">
                        <span class="input-group-addon">${I18n.jobinfo_field_jobgroup}</span>
                        <select class="form-control" id="jobGroup">
                            <#list JobGroupList as group>
                                <option value="${group.id}"
                                        <#if jobGroup==group.id>selected</#if> >${group.title}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <#--状态-->
                <div class="col-xs-1">
                    <div class="input-group">
                        <select class="form-control" id="triggerStatus">
                            <option value="-1">${I18n.system_all}</option>
                            <option value="0">${I18n.jobinfo_opt_stop}</option>
                            <option value="1">${I18n.jobinfo_opt_start}</option>
                        </select>
                    </div>
                </div>
                <#--任务描述-->
                <div class="col-xs-2">
                    <div class="input-group">
                        <input type="text" class="form-control" id="jobDesc"
                               placeholder="${I18n.system_please_input}${I18n.jobinfo_field_jobdesc}">
                    </div>
                </div>
                <#--请输入JobHandler-->
                <div class="col-xs-2">
                    <div class="input-group">
                        <input type="text" class="form-control" id="executorHandler"
                               placeholder="${I18n.system_please_input}JobHandler">
                    </div>
                </div>
                <#--请输入负责人-->
                <div class="col-xs-2">
                    <div class="input-group">
                        <input type="text" class="form-control" id="author"
                               placeholder="${I18n.system_please_input}${I18n.jobinfo_field_author}">
                    </div>
                </div>
                <#--搜索-->
                <div class="col-xs-1">
                    <button class="btn btn-block btn-info" id="searchBtn">${I18n.system_search}</button>
                </div>
                <#--新增-->
                <div class="col-xs-1">
                    <button class="btn btn-block btn-success add" type="button">${I18n.jobinfo_field_add}</button>
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
                                    <th name="id">${I18n.jobinfo_field_id}</th>
                                    <th name="jobGroup">${I18n.jobinfo_field_jobgroup}</th>
                                    <th name="jobDesc">${I18n.jobinfo_field_jobdesc}</th>
                                    <th name="scheduleType">${I18n.schedule_type}</th>
                                    <th name="glueType">${I18n.jobinfo_field_gluetype}</th>
                                    <th name="executorParam">${I18n.jobinfo_field_executorparam}</th>
                                    <th name="addTime">addTime</th>
                                    <th name="updateTime">updateTime</th>
                                    <th name="author">${I18n.jobinfo_field_author}</th>
                                    <th name="alarmEmail">${I18n.jobinfo_field_alarmemail}</th>
                                    <th name="triggerStatus">${I18n.system_status}</th>
                                    <th>${I18n.system_opt}</th>
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
<!-- daterangepicker -->
<script src="${request.contextPath}/static/adminlte/bower_components/moment/moment.min.js"></script>
<script src="${request.contextPath}/static/adminlte/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
<#-- echarts -->
<script src="${request.contextPath}/static/plugins/echarts/echarts.common.min.js"></script>
<script src="${request.contextPath}/static/js/index.js"></script>
</body>
</html>
