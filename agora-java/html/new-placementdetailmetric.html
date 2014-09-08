<!doctype html>
<html>
<head>
	<title>Agora - Placement Detail &amp; Metric</title>

	<link rel="shortcut icon" href="agora_icon.ico">

	<script type="text/javascript" src="jq.js"></script>
	<script type="text/javascript" src="jq.quicksearch.js"></script>
	<script type="text/javascript" src="jq.multiselect.js"></script>
	<script type="text/javascript" src="fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-1.3.4.css" media="screen" />
	<link href="new.css" rel="stylesheet" type="text/css" />
</head>
<body id="template">
    <#include "header.ftl"/>
	
	<div id="page_content">

        <@sec.authorize ifAnyGranted="Agora_Admin,Agora_Search+">

		<div id="internal">
		<div style="padding: 5px; background-color: #ccc; margin-bottom: 15px;">
			&laquo; <a href="backToSearch.m">Back to Results</a>
		</div>
		<h2>Ad Details</h2>
		<table id="filter_results">
			<tr>
  <#assign i = ad />
				<td colspan="8" class="ad leaderboard" colspan="8">

					<div class="ad_company_product">
                        ${i.company_name}
                        <#if 0 < i.product?length>
                          <span class="pipebreak">|</span><span name="${i.product}" title="${i.product}">${i.product}</span>
                        </#if>
					</div> 

					<div class="ad_image_meta">

						<#if i.filename?ends_with("swf")>
						  <#-- same trick here with blank image to show in lightbox -->
						     <#assign dispSize = Session.util.getSizeWithMax(i.size, 200, 60) />
						  <div class="ad_image" style="z-index: 0; position: absolute; width: ${dispSize?split("x")[0]}px; height: ${dispSize?split("x")[1]}px;">
						  <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="adimages/${i.filename}" align="middle" width="${dispSize?split("x")[0]}" height="${dispSize?split("x")[1]}">
						    <param name="movie" value="adimages/${i.filename}"/>
                            <param name="wmode" value="opaque" />
						    <!--[if !IE]>-->
						    <object type="application/x-shockwave-flash" data="adimages/${i.filename}" width="${dispSize?split("x")[0]}" height="${dispSize?split("x")[1]}" wmode="opaque">
						      <param name="movie" value="adimages/${i.filename}"/>
						      <!--<![endif]-->
						      <a href="http://www.adobe.com/go/getflash">
							<img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player"/>
						      </a>
						      <!--[if !IE]>-->
						    </object>
						    <!--<![endif]-->
						  </object>
						  </div>
							<p>&nbsp;</p>
						  <div style="z-index: 1; position: absolute; margin: 8px 0px; padding: 5px;">
						    <a class="lightbox" href="adimages/${i.filename}">
						      <img width="${dispSize?split("x")[0]}" height="${dispSize?split("x")[1]}" src="fancybox/blank.gif" style="opacity: 0; filter: alpha(opacity = 0)"/>
						    </a>
						  </div>
						<#else>
						  <a href="adimages/${i.filename}" class="lightbox">
						    <img class="ad_image" src="imageresizer?maxwidth=200&maxheight=60&filename=${i.filename}"/>
						  </a>
						</#if>
						</div>
					</div>			
					<div class="ad_full_copy">
						<div class="complete_ad_text">
							<img src="adtext.png" title="Complete Ad Text"> Complete Ad Text
						</div>
						<div class="ad_copy_container">
							${i.description?html}
						</div>
					</div>
					<div class="ad_domain_list">
						<div class="ad_domains_label">
							<img src="domains.png" title="Found on these domains"> Found on domains:
						</div>
						<div class="ad_domains">
							${i.site_list?replace(",", "<br>")}
						</div>
					</div>
                    <div class="clear ad_footer_data">
						#${i.image_num?c} <span class="pipebreak">|</span> ${i.size} <span class="pipebreak">|</span> ${i.filename?split(".")?last} <span class="pipebreak">|</span> ${i.createdate} - ${i.lastDate} <#-- <span class="pipebreak">|</span>Share: {i.percentAdCount}<span class="pipebreak">|</span>Occurences: {i.adCount} -->
					</div>
				</td>
			</tr>
		</table>

		<h2>Ad Drilldown</h2>

		${results?size} Results

		<table class="pdm_table">
			<tr>
				<th>Row #</th>
				<th>Domain</th>
				<th>Date Range</th>
				<th>Metric</th>
				<th>Levels found on</th>
				<!--<th>URL</th>-->
			</tr>

			  <#assign row = 1 />
			  <#list results as r>
 			    <tr>
				<td>
					${row}
					<#assign row = row + 1 />
				</td>
				<td>
					${r.path}
				</td>
				<td>
					${r.min_month} - ${r.max_month}
				</td>
				<td>
					${r.ad_count}
				</td>
				<td>
					<!-- Home, Subpages -->${r.depths?replace("0, 0", "0")?replace("1, 1", "1")?replace("0", "Home")?replace("1", "Subpages")}
				</td>
				<!--
				<td class="pdm_domain_hover">
					<div class="pdm_domain_hidden">
						website.com<br>
						othersite.com<br>
						onemoresite.com
					</div>
					<div style="float: right; font-size: 12px">[hover]</div>
				</td>-->
			    </tr>
			  </#list>
			
		</table>

		</div>
        </@sec.authorize>
<#include "footer.ftl"/>
	
	</div>

<script>

$(document).ready(function(){
   $("#domain_list").multiSelect();
});

$("a.lightbox").fancybox({
	'overlayShow'	: true,
	'transitionIn'	: 'elastic',
	'transitionOut'	: 'elastic'
});

// Truncation code from http://stackoverflow.com/questions/2248742/jquery-text-truncation-read-more-style

function escapeString(str) {

return str.replace(/\n/g, "   ")
        .replace(/\'/g, " ")
        .replace(/\"/g, " ")
	.replace(/\&/g, " ")
	.replace(/\r/g, " ")
	.replace(/\t/g, "  ")
	.replace(/\b/g, " ")
	.replace(/</g, "\&lt;")
	.replace(/\f/g, " ");
}

$(".ad_copy_container").each( function() {
    var origtext = escapeString($.trim($(this).text()));	
    if (origtext.length > 120) {
		$(this).html($.trim($(this).html()).substring(0, 100).split(" ").slice(0, -1).join(" ") + "<br>... <a href=\"#\" title=\"" + origtext + "\" name=\"" + origtext + "\">(hover for more)</a>");		
    }
});

$(".ad_domains").each( function() {
    var origtext = escapeString($.trim($(this).text()));
    if (origtext.length > 100) {
		$(this).html($.trim($(this).html()).substring(0, 100).split(" ").slice(0, -1).join(" ") + "<br>... <a href=\"#\" title=\"" + origtext + "\" name=\"" + origtext + "\">(hover for more)</a>");
    }
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
