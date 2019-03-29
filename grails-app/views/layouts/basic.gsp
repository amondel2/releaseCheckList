<!DOCTYPE html>
<html lang="en" class="no-js">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Release Planner</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${assetPath(src: 'Favicon.ico')}" type="image/x-icon">
		<asset:stylesheet src="mainjquery.css"/>
  		<asset:stylesheet src="mainapp.css"/>
		<asset:javascript src="main.js"/>
		<asset:stylesheet src="bootstrap-datepicker3.min.css"/>
		<asset:javascript src="bootstrap-datepicker.min.js" />
		<g:layoutHead/>
		<script>
        	var baseDir = "${request.contextPath}/${controllerName}/";
		</script>
	</head>
	<body>
		<span id="logoutLink" style="display:none">
                        <g:link elementId='_logout' controller='logout'/>
                        <a href="${request.contextPath}${securityConfig.logout.afterLogoutUrl}" id="_afterLogout"></a>
        </span>
		<nav class="navbar navbar-inverse navbar-fixed-top">
      	<div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="${request.contextPath}/">Home</a></li>
             <sec:ifLoggedIn>
             	<li><a href="${request.contextPath}/releaseManager">Manage My Releases</a></li>
				 <sec:ifAnyGranted roles="ROLE_ADMIN">
					<li class="dropdown">
          				<a class="dropdown-toggle" data-toggle="dropdown" href="#">Administration
        				<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${request.contextPath}/releaseName">Release Name</a> </li>
							<li><a href="${request.contextPath}/releaseItem">Release Items</a></li>
							<li><a href="${request.contextPath}/releasePackage">Release Package</a></li>
							<li><a href="${request.contextPath}/releasePackageItems">Release Package Item</a></li>
							<li><a href="${request.contextPath}/user">Users</a></li>
						 </ul>
					 </li>

					 </li>
				 </sec:ifAnyGranted>
             </sec:ifLoggedIn>
            </ul>
          <ul class="nav navbar-nav navbar-right"><li>
          <sec:ifLoggedIn>
          	<span class="navbar-brand">Welcome <ps:getUserFName /></span>
          	<g:link controller="logout" class="navbar-brand" elementId="logout"><g:message code='spring.security.ui.login.logout'/></g:link>
          </sec:ifLoggedIn>
         
          <sec:ifNotLoggedIn>
          	<g:link controller='login' action='auth'>Login</g:link>
       		</sec:ifNotLoggedIn>
       		</li></ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    <div class="container">
    	<div class="starter-template" style="margin-top:70px;">
		<g:layoutBody/>
		 </div>
     </div>
     <footer class="navbar-fixed-bottom navbar-inverse">
     		<div class="container" style="color:#FFFFFF;">&copy; 2015<script>new Date().getFullYear()>2010&&document.write("-"+new Date().getFullYear())</script>, Aaron Mondelblatt. <g:meta name="info.app.version"/>
     		</div>

    </footer>
	</body>
</html>
