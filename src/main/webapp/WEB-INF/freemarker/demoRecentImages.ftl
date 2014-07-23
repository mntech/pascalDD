<!DOCTYPE html>
<#--


TODO
TODO
TODO
TODO
TODO
TODO
TODO

make sure the border on the images and flash both look somewhat uniformly applied

try to get the lightbox plugin to fit the proper dimensions of the flash files

-->
<html>
<head>
	<title>Essentia: AGORA</title>
	
	<link rel="shortcut icon" href="agora_icon.ico"/>

	<script type="text/javascript" src="jq.js"></script>
	<script type="text/javascript" src="fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-1.3.4.css" media="screen" />
	<link href="new.css" rel="stylesheet" type="text/css" />
	
</head>
<body id="template">
	
	<div id="top_bar">
		
		<div id="userinfo">

			<img src="user.png" style="width: 12px; height: auto; margin-right: 2px;"/> <a href="login.m">Log In</a>

		</div>

		<a href="#" class="logo" title="Essentia"><img src="logo_essentia.png"/></a>
		
	</div>
	
	<div id="page_content">

		<div class="home_intro">
		<img src="logo_agora_large.png" style="display: block; width: 85%; margin: 0px auto 15px;"/>
			Essentia is building the knowledge and infrastructure to meet our clients' growing needs for meaningful competitive data about e-marketing activity. AGORA is one piece in ESSENTIA's suite of eTOOLS for online media tracking. These are tools unlike anything else available and are specially developed by the tech marketing professionals and IT staff at ESSENTIA. If you have need for more information about electronic media usage, talk to us about our syndicated and custom systems. Contact us for information about access to the AgoraSearch Tools.
		</div>
		
		<div id="home_examples">
			<div style="padding: 10px; font-size: 18px; line-height: 24px;">
				These images are a sample from:<br/>
				<span style="font-weight: bold;">Life Sciences / Pharma Market</span>
			</div>
			<div class="clear"></div>
			<span class="home_ad_type" id="tab_towers">Towers</span>
			<span class="home_ad_type" id="tab_leaderboards">Leaderboards</span>
			<span class="home_ad_type" id="tab_rectangles">Rectangles</span>
			<div class="clear"></div>
			<div style="text-align: center; margin-top: 7px; font-style: italic; color: #888;">
				Ads shown at a reduced size.
			</div>
			<#macro showImage i maxWidth maxHeight class>
			  <#if i.filename?ends_with("swf")>
			    <div style="z-index: 0; position: absolute; width: ${i.size?split("x")[0]}px; height: ${i.size?split("x")[1]}px;" class="bordered ${class}">
			      <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="adimages/${i.filename}" align="middle" width="${i.size?split("x")[0]}" height="${i.size?split("x")[1]}">
				<param name="movie" value="adimages/${i.filename}"/>
				<!--[if !IE]>-->
				<object type="application/x-shockwave-flash" data="adimages/${i.filename}" width="${i.size?split("x")[0]}" height="${i.size?split("x")[1]}" wmode="opaque">
				  <param name="movie" value="adimages/${i.filename}"/>
                  <param name="wmode" value="opaque" />
				  <!--<![endif]-->
				  <a href="http://www.adobe.com/go/getflash">
				    <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player"/>
				  </a>
				  <!--[if !IE]>-->
				</object>
				<!--<![endif]-->
			      </object>
			    </div>
			    <div style="z-index: 1; position: absolute">
			      <a class="lightbox" href="adimages/${i.filename}">
				<img class="${class}" width="${i.size?split("x")[0]}" height="${i.size?split("x")[1]}" src="fancybox/blank.gif" style="opacity: 0; filter: alpha(opacity = 0)"/>
			      </a>
			    </div>
			    <#else>
			      <a class="lightbox" href="adimages/${i.filename}">
				<#-- set the size here explicitely because the css stretchs them out and makes them have odd aspect ratios -->
<#--				     width="${i.size?split("x")[0]}" height="${i.size?split("x")[1]}"-->
				<img class="${class}" style="height:${i.size?split("x")[1]}"
				     src="imageresizer?maxwidth=${maxWidth}&maxheight=${maxHeight}&flip=n&filename=${i.filename}"/>
			      </a>
			    </#if>
			</#macro>
			<div id="home_image_preview">

				<div id="images_tower">
				  <table cellspacing="4" cellpadding="4">
				    <tr align="center">
				      <#list tower as i>
					<#if i_index == 7>
				          </tr><tr align="center">
				        </#if>
						<#if i.filename?ends_with("swf")>
							<td style="vertical-align: top;" align="left">
						<#else>
							<td>
                        </#if>
					  <@showImage i 0 200 "tower_small"/>
					</td>
				      </#list>
				    </tr>
				  </table>
				</div>

				<div id="images_leaderboard" style="display: none;">	
				  <table cellspacing="4" cellpadding="4">
				    <tr align="center">
				      <#list banner as i>
					<#if i_index % 2 == 0>
				          </tr><tr align="center">
				        </#if>
				        <#if i.filename?ends_with("swf")>
							<td style="vertical-align: top;" align="left">
						<#else>
							<td>
                        </#if>
					  <@showImage i 238 0 "leaderboard_small"/>
					</td>
				      </#list>
				    </tr>
				  </table>
				</div>

				<div id="images_rectangle" style="display: none;">
				  <table cellspacing="4" cellpadding="4">
				    <tr align="center">
				      <#list rect as i>
					<#if i_index % 4 == 0>
				          </tr><tr align="center">
				        </#if>
   						<#if i.filename?ends_with("swf")>
							<td style="vertical-align: top;" align="left">
						<#else>
							<td>
                        </#if>                        
					  <@showImage i 112 112 "rectangle_small"/>
					</td>
				      </#list>
				    </tr>
				  </table>
				</div>
			</div>

		</div>

<#include "footer.ftl"/>
	
	</div>

	<script>

	$('#tab_towers').click( function() {
		$('.home_ad_type').removeClass('home_ad_active');
		$('#tab_towers').addClass('home_ad_active');
		$('#images_tower').show();
		$('#images_leaderboard').hide();
		$('#images_rectangle').hide();
	});
	$('#tab_leaderboards').click( function() {
		$('.home_ad_type').removeClass('home_ad_active');
		$('#tab_leaderboards').addClass('home_ad_active');
		$('#images_leaderboard').show();
		$('#images_tower').hide();
		$('#images_rectangle').hide();
	});
	$('#tab_rectangles').click( function() {
		$('.home_ad_type').removeClass('home_ad_active');
		$('#tab_rectangles').addClass('home_ad_active');
		$('#images_rectangle').show();
		$('#images_tower').hide();
		$('#images_leaderboard').hide();
	});

	$("a.lightbox").fancybox({
		'overlayShow'	: true,
		'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic'
	});
	</script>
<!--[if IE 6]>
<script type="text/javascript" src="supersleight.plugin.js"></script>
<script type="text/javascript">
	$('body').supersleight({shim: 'x.gif'});
</script>
<![endif]-->
<script type="text/javascript" src="global.js"></script></body>
</html>
