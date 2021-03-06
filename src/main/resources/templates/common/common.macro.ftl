<#macro commonStyle>

<#-- favicon -->
    <link rel="icon" href="${request.contextPath}/static/favicon.ico"/>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap -->
    <link rel="stylesheet"
          href="${request.contextPath}/static/adminlte/bower_components/bootstrap/css/bootstrap.min.css">
    <!--bootstrap-switch-->
    <link rel="stylesheet"
          href="${request.contextPath}/static/adminlte/bower_components/bootstrap-switch/bootstrap-switch.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="${request.contextPath}/static/adminlte/bower_components/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/bower_components/Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/dist/css/skins/_all-skins.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- pace -->
    <link rel="stylesheet"
          href="${request.contextPath}/static/adminlte/bower_components/PACE/themes/blue/pace-theme-flash.css">

<#-- i18n -->
<#--	<#global I18n = I18nUtil.getMultString()?eval />-->

</#macro>

<#macro commonScript>
    <!-- jQuery -->
    <script src="${request.contextPath}/static/adminlte/bower_components/jquery/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="${request.contextPath}/static/adminlte/bower_components/bootstrap/js/bootstrap.min.js"></script>
    <!--bootstrap-switch-->
    <script src="${request.contextPath}/static/adminlte/bower_components/bootstrap-switch/bootstrap-switch.min.js"></script>
    <!-- FastClick -->
    <script src="${request.contextPath}/static/adminlte/bower_components/fastclick/fastclick.js"></script>
    <!-- AdminLTE App -->
    <script src="${request.contextPath}/static/adminlte/dist/js/adminlte.min.js"></script>
    <!-- jquery.slimscroll -->
    <script src="${request.contextPath}/static/adminlte/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>

    <!-- pace -->
    <script src="${request.contextPath}/static/adminlte/bower_components/PACE/pace.min.js"></script>
<#-- jquery cookie -->
    <script src="${request.contextPath}/static/plugins/jquery/jquery.cookie.js"></script>
<#-- jquery.validate -->
    <script src="${request.contextPath}/static/plugins/jquery/jquery.validate.min.js"></script>

<#-- layer -->
    <script src="${request.contextPath}/static/plugins/layer/layer.js"></script>

<#-- common -->
    <script src="${request.contextPath}/static/js/common.1.js"></script>
    <script>
        var base_url = '${request.contextPath}';
        <#--var I18n = ${I18nUtil.getMultString()};-->
    </script>

</#macro>

<#macro commonHeader>
    <header class="main-header">
        <a href="${request.contextPath}/" class="logo">
            <span class="logo-mini"><b>DTM</b></span>
            <span class="logo-lg"><b>Dtimer????????????</b></span>
        </a>
        <nav class="navbar navbar-static-top" role="navigation">

            <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>

            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <#-- login user -->
                    <a id="userid" style="visibility:hidden">${Request["DTIMER_DAY_LOGIN_IDENTITY"].id}</a>
                    <li class="dropdown">
                        <a href="javascript:" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            ?????? ${Request["DTIMER_DAY_LOGIN_IDENTITY"].username}
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li id="updatePwd"><a href="javascript:">????????????</a></li>
                            <li id="logoutBtn"><a href="javascript:">??????</a></li>
                        </ul>
                    </li>
                </ul>
            </div>

        </nav>
    </header>

    <!-- ????????????.????????? -->
    <div class="modal fade" id="updatePwdModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog ">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">????????????</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal form" role="form">
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">?????????<font
                                        color="red">*</font></label>
                            <div class="col-sm-10"><input type="text" class="form-control" name="password"
                                                          placeholder="??????????????????"
                                                          maxlength="18"></div>
                        </div>
                        <hr>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">
                                <button type="submit" class="btn btn-primary">??????</button>
                                <button type="button" class="btn btn-default"
                                        data-dismiss="modal">??????
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</#macro>

<#macro commonLeft pageName >
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu">
                <li class="header">??????</li>
                <li class="nav-click <#if pageName == "index">active</#if>"><a href="${request.contextPath}/"><i
                                class="fa fa-circle-o text-aqua"></i><span>????????????</span></a></li>
                <li class="nav-click <#if pageName == "jobinfo">active</#if>"><a
                            href="${request.contextPath}/jobinfo"><i
                                class="fa fa-circle-o text-yellow"></i><span>????????????</span></a></li>
                <#--                <li class="nav-click <#if pageName == "joblog">active</#if>"><a href="${request.contextPath}/joblog"><i-->
                <#--                                class="fa fa-circle-o text-green"></i><span>????????????</span></a></li>-->
                <#--                &lt;#&ndash;                <#if Request["XXL_JOB_LOGIN_IDENTITY"].role == 1>&ndash;&gt;-->
                <#--                <#if 1 == 1>-->
                <#--                    <li class="nav-click <#if pageName == "jobgroup">active</#if>"><a-->
                <#--                                href="${request.contextPath}/jobgroup"><i-->
                <#--                                    class="fa fa-circle-o text-red"></i><span>???????????????</span></a></li>-->
                <#--                    <li class="nav-click <#if pageName == "user">active</#if>"><a href="${request.contextPath}/user"><i-->
                <#--                                    class="fa fa-circle-o text-purple"></i><span>????????????</span></a></li>-->
                <#--                </#if>-->
                <#--                <li class="nav-click <#if pageName == "help">active</#if>"><a href="${request.contextPath}/help"><i-->
                <#--                                class="fa fa-circle-o text-gray"></i><span>????????????</span></a></li>-->
            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>
</#macro>

<#macro commonControl >
    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Create the tabs -->
        <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
            <li class="active"><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
            <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <!-- Home tab content -->
            <div class="tab-pane active" id="control-sidebar-home-tab">
                <h3 class="control-sidebar-heading">????????????</h3>
                <ul class="control-sidebar-menu">
                    <li>
                        <a href="javascript::;">
                            <i class="menu-icon fa fa-birthday-cake bg-red"></i>
                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">?????????????????????</h4>
                                <p>2015-09-10</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript::;">
                            <i class="menu-icon fa fa-user bg-yellow"></i>
                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Frodo ???????????????</h4>
                                <p>?????????????????? +1(800)555-1234</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript::;">
                            <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>
                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Nora ??????????????????</h4>
                                <p>nora@example.com</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript::;">
                            <i class="menu-icon fa fa-file-code-o bg-green"></i>
                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">001?????????????????????</h4>
                                <p>5????????????</p>
                            </div>
                        </a>
                    </li>
                </ul>
                <!-- /.control-sidebar-menu -->
            </div>
            <!-- /.tab-pane -->

            <!-- Settings tab content -->
            <div class="tab-pane" id="control-sidebar-settings-tab">
                <form method="post">
                    <h3 class="control-sidebar-heading">????????????</h3>
                    <div class="form-group">
                        <label class="control-sidebar-subheading"> ?????????????????????
                            <input type="checkbox" class="pull-right" checked>
                        </label>
                        <p>??????????????????????????????</p>
                    </div>
                    <!-- /.form-group -->

                </form>
            </div>
            <!-- /.tab-pane -->
        </div>
    </aside>
    <!-- /.control-sidebar -->
    <!-- Add the sidebar's background. This div must be placed immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
</#macro>

<#macro commonFooter >
    <footer class="main-footer">
        Powered by <b>DTIMER-DAY</b> 2.3.1-SNAPSHOT
        <div class="pull-right hidden-xs">
            <strong>Copyright &copy; 2021-${.now?string('yyyy')} &nbsp;
                <a href="https://a.cunjk.com/" target="_blank">Modify By Timer</a>
                &nbsp;
                <a href="https://a.cunjk.com/" target="_blank">github</a>
            </strong><!-- All rights reserved. -->
        </div>
    </footer>
</#macro>