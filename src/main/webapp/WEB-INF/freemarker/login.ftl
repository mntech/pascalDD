<!DOCTYPE html>
<html>
<head>
	<title>Agora - Log in</title>
	
	<link rel="shortcut icon" href="agora_icon.ico">

	<script type="text/javascript" src="jq.js"></script>
	<script type="text/javascript" src="jq.quicksearch.js"></script>
	<script type="text/javascript" src="jq.multiselect.js"></script>
	<link href="new.css" rel="stylesheet" type="text/css" />
	
</head>
<body id="template">
	
	<div id="top_bar">
		
		<div id="userinfo">

		</div>

		<a href="#" class="logo" title="Essentia"><img src="logo_agora.png"></a>
		
	</div>


	
	
	<div id="page_content">

		<h1 style="width: 550px; margin: 10px auto;">User Login</h1>

		<div id="internal" style="width: 535px; margin: 0px auto;">

			<p style="margin-bottom:24px">This system is the property of Essentia Communications, LLC (hereinafter "Essentia" or “company”) 
			and is provided for Essentia-authorized subscribers only. Unauthorized use is prohibited and may 
			be subject to discipline, civil suit and criminal prosecution. By using this system you agree to 
			comply with all applicable written policies, procedures and guidelines for system use and 
			protection of company information or information that the company has an obligation to protect, 
			including but not limited to proprietary information, personally identifiable information, and 
			export controlled information.</p>
			
		  <#if Session.SPRING_SECURITY_LAST_EXCEPTION?? && Session.SPRING_SECURITY_LAST_EXCEPTION.message?has_content>
			<div class="login_error">
				<span>Error:</span> ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
			</div>
		  </#if>
			
			<form id="login-form" action="j_spring_security_check" method="post" style="width:435px; margin:auto;">
				
				<label for="username">
					Username:
				</label>
						
				<input type="text" tabindex="1" maxlength="255" value="" class="field text full" name="j_username" id="username" />
				
				<label for="password">
					Password:
				</label>
				
				<input type="password" tabindex="1" maxlength="255" value="" class="field text full" name="j_password" id="password" />

				<label for="tos">
					<input id="tos-box" type="checkbox" value="1" name="tos" />
					I have read, and understand and agree with, the Essentia terms of use.
				</label>
				<p><a href="tos.m" target="_blank">Terms of use</a></p>
						
				<!--<div style="text-align: left; float: left; width: 220px; padding-top: 8px;">
					<a href="#">Forgot password?</a>
				</div>-->

				<button class="big_submit" type="submit" style="float: right; width: 200px;">Log in</button>
					
				<div class="clear"></div>
			</form>

			<p style="margin-top:24px">Your password is your key to protecting your account as well as your employer's and/or your 
			organization's interests. It is provided for your exclusive use. It should be protected from 
			accidental disclosure and changed if you fear that it has been compromised. Your password will 
			expire periodically after which you will be prompted to change your password or use a new one. 
			It is further required that you notify us before you leave the employment of the subscribing 
			company and cease using the password after your severance.</p>
	
		</div>

<#include "footer.ftl"/>
	
	</div>
<!--[if IE 6]>
<script type="text/javascript" src="supersleight.plugin.js"></script>
<script type="text/javascript">
	$('body').supersleight({shim: 'x.gif'});
</script>
<![endif]-->
<script type="text/javascript">
	// Login form validation
	$('#login-form').submit(function(e) {
			var $tosbox = $('#tos-box');

			if (! $tosbox.attr('checked')) {
				e.preventDefault();
				alert('You must agree to the terms of use.');
			}
	});
</script>

<script type="text/javascript" src="global.js"></script></body>
</html>
