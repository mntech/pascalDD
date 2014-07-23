<!doctype html>
<html>
<head>
	<title>Agora - Search Results</title>
	<link rel="shortcut icon" href="agora_icon.ico">
	<script type="text/javascript" src="jq.js"></script>
	<script type="text/javascript" src="fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-1.3.4.css" media="screen" />
	<link href="new.css" rel="stylesheet" type="text/css" />
</head>
<body id="template">
    <#include "header.ftl"/>
    <#include "pagination.ftl"/>
    <#assign offset = searchData.offset />
	<div id="page_content">
        <div id="internal">
		<#include "newCreativeForm.ftl"/>
        <hr>
		<#if resultCount == 0>
		  <h1>No results found</h1>
		<#else>
		  <table width="100%">
		    <tr>
		      <td>

		<@resultRangeText />
				    
		  <span class="pipebreak">|</span> Order results by 
		  <select name="sort_results_by" id="sort_results_by">
		    <option name="company">Company</option>
		    <option name="date">Date</option>
		  </select>
		  <span class="pipebreak">|</span>
		  Export: <a href="searchCsv.m">CSV</a><#-- - <a href="#">XLS</a> -->

		      </td>
		      <td align="right"><@pageNumberLinks /></td>
		    </tr>
		  </table>

		</#if>

		<table id="filter_results">
		  <#list results as i>
		    <tr>
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
						  <div class="ad_image" style="z-index: 0; position: absolute; width: ${i.smallSize?split("x")[0]}px; height: ${i.smallSize?split("x")[1]}px;">
						  <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="adimages/${i.filename}" align="middle" width="${i.smallSize?split("x")[0]}" height="${i.smallSize?split("x")[1]}" >
						    <param name="movie" value="adimages/${i.filename}"/>
                            <param name="wmode" value="opaque" />
						    <!--[if !IE]>-->
						    <object type="application/x-shockwave-flash" data="adimages/${i.filename}" width="${i.smallSize?split("x")[0]}" height="${i.smallSize?split("x")[1]}" wmode="opaque">
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
						  <div style="z-index: 1; position: absolute; padding: 5px;">
						    <a class="lightbox" href="adimages/${i.filename}">
						      <img width="${i.smallSize?split("x")[0]}" height="${i.smallSize?split("x")[1]}" src="fancybox/blank.gif" style="opacity: 0; filter: alpha(opacity = 0)"/>
						    </a>
						  </div>
						  <p>&nbsp;</p>
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
					  <#-- placement detail and metric link is only visible in Agora+ report -->
					  <#if Session.tab == "placementDetailAndMetricTab">
						<div class="ad_details_label">
							<img src="metrics.png" title="Pacement Details & Metrics">
							Placement Details & Metrics: <a href="adDrilldown.m?imageNum=${i.image_num?c}"><span style="font-style: normal; font-size: 200%;">&raquo;</span></a>
						</div>
					  </#if>
						<div class="ad_domains_label">
							<img src="domains.png" title="Found on these domains"> Found on domains:
						</div>
						<div class="ad_domains">
							${i.site_list?replace(",", "<br>")}
						</div>
					</div>
                    
                    <div class="clear ad_footer_data">
						#${i.image_num?c} <span class="pipebreak">|</span> ${i.size} <span class="pipebreak">|</span> ${i.filename?split(".")?last} <span class="pipebreak">|</span> ${i.createdate} - ${i.lastDate} <span class="pipebreak">|</span>Share: ${i.percentAdCount}<span class="pipebreak">|</span>Occurences: ${i.adCount}
					</div>

				</td>
			</tr>
		    </#list>
		</table>

<table width="100%">
  <tr>
    <td><@resultRangeText /></td>
    <td align="right"><@pageNumberLinks /></td>
  </tr>
</table>

		</div>


<#include "footer.ftl"/>

	</div>

<script>

$(document).ready(function(){
	var agora_total_selected=$(".multiSelectOptions input:checkbox:checked").length;
	$("#domains span").text(agora_total_selected+" selected");
    $("a.lightbox").fancybox({
	'overlayShow'	: true,
	'transitionIn'	: 'elastic',
	'transitionOut'	: 'elastic'
    });

	$("#filter_results tr:odd").css({ 'background-color': '#eee'});
	$("#filter_results tr:even").css({ 'background-color': '#ddd'});	
	$("#filter_results tr").hover(function(){		
			original_color_agora=$(this).css('background-color');	
			$(this).css({ 'background-color': '#fff'});	
	}, function(){
			$(this).css({ 'background-color': original_color_agora});	
	});
	$("#search").submit(function() {
		$("#domain").removeAttr("disabled");
	});	
});

// Truncation code from http://stackoverflow.com/questions/2248742/jquery-text-truncation-read-more-style

function escapeString(str) {

return str.replace(/\n/g, "   ")
        .replace(/\'/g, " ")
        .replace(/\"/g, " ")/*"*/
	.replace(/\&/g, " ")
	.replace(/\r/g, " ")
	.replace(/\t/g, "  ")
	.replace(/\b/g, " ")
	.replace(/</g, "\&lt;")
	.replace(/\f/g, " ");
}
$(".ad_copy_container").each( function() {
    var origtext = escapeString($.trim($(this).text()));
    var maxLen = 200;
    var txtLen = origtext.replace(/\s+/g, ' ').length;
    if ((txtLen - maxLen) > 12) {
		$(this).html($.trim($(this).html()).substring(0, maxLen - 8).split(" ").slice(0, -1).join(" ") + "<br>... <a href=\"javascript:void(0);\" title=\"" + origtext + "\" name=\"" + origtext + "\">(hover for more)</a>");
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
<!--[if le IE 7]>
$('#domains').trigger('click');
$('#domains').trigger('click');	
<![endif]-->
<script type="text/javascript">
$('#advertiser').trigger('focus');
</script>
<script type="text/javascript" src="global.js"></script></body>
</html>
