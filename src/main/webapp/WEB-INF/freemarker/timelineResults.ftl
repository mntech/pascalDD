<!doctype html>
<html>
<head>
	<title>Agora - Search Results</title>

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
  <#include "pagination.ftl"/>
  <#include "dataUtil.ftl"/>

  <#assign offset = searchData.offset />

	<div id="page_content">
		

		<div id="internal">
			
		  <form id="search" method="post" action="search.m" class="pagedForm">
			  <input type="hidden" name="offset" value="0"/>
			  <input type="hidden" name="aspectRatio" value="${searchData.aspectRatio}"/>
			  <input type="hidden" name="removeHouseAds" value="${searchData.removeHouseAds?string}"/>
			<div class="half_right">
				<div class="int_padding">
				  <script>
				    $(function() {
					$("select[name='startMonth']").val("${searchData.startMonth}");
					$("select[name='startYear']").val("${searchData.startYear?c}");
					$("select[name='endMonth']").val("${searchData.endMonth}");
					$("select[name='endYear']").val("${searchData.endYear?c}");
				    });
				  </script>

				<label for="search_time">Search Dates:</label>
				<select name="startMonth">
				  <option>Jan<option>Feb<option>Mar<option>Apr<option>May<option>Jun<option>Jul<option>Aug<option>Sep<option>Oct<option>Nov<option>Dec
				</select>

				<select name="startYear">
				  <@date_options/>
				</select>
				 &nbsp;&nbsp;&nbsp;through&nbsp;&nbsp;&nbsp;

				 <select name="endMonth">
				   <option>Jan<option>Feb<option>Mar<option>Apr<option>May<option>Jun<option>Jul<option>Aug<option>Sep<option>Oct<option>Nov<option>Dec
				</select>

				<select name="endYear">
				  <@date_options/>
				</select>
				<div style="margin-top: 30px;">
  					<div style="float: right; padding-top: 22px;">
  						<input type="checkbox" class="checkbox" id="useDomainSet" name="useDomainSet"<#if useDomainSet??>checked="on"</#if>> <label for="useDomainSet" class="inliner">Only my Domain Set <div class="help_info"><span class="help_bubble">?</span><div class="help_info">Your Domain Set allows you to save a list of commonly used domains and quickly create reports from them.</div></div></label>
  					</div>
  					<label for="domains">Search Domains:</label>
  					<div id="fake_hider" style="display: none; position: absolute; height: 30px; width: 203px; background-color: rgba(0, 0, 0, 0.2); z-index: 999; border-radius: 10px;"></div>
  					<select style="width: 190px;" multiple="multiple" id="domains" name="domains[]" size="4">
					  <#list domainList as d>
					    <option value="${d.id}">${d.title}</option>
					  </#list>
					</select>
  				</div>

				<#--
				<div style="padding-bottom:18px">
				  <div style="font-weight:bold">Order results by</div> 
				  <select name="sort_results_by" style="width:200px">
				    <option name="company">Company</option>
				    <option name="date">Date</option>
				  </select>
				</div>
				-->
			    </div>
  			</div>

   			<#include "defaultSearchPeriod.ftl"/>
			<@defaultSearchPeriod/>
   			<script type="text/javascript">
			  function resetDates() {
			      var sm = $("select[name='startMonth']");
			      sm.val(sm.get(0).options[${defaultStartMonthIdx}].value);
			      $("select[name='startYear']").val("${defaultStartYear}");
			      var em = $("select[name='endMonth']");
			      em.val(em.get(0).options[${defaultEndMonthIdx}].value);
			      $("select[name='endYear']").val("${defaultEndYear}");
			  }
			  function clearForm() {
			      resetDates();
			      $("input[name='keywords']").val("");
			      $("input[name='advertiser']").val("");
			      $("input[name='domain']").val("");
			      $("input[name='useDomainSet']").attr("checked", false);
				  /*$("#domains").multiSelect("deselect_all");*/
				  $(".multiSelectOptions input:checkbox:checked").each(function() {
					  $(this).trigger("click");
				  });
				  $("#domains span").text("Select options");
				  $("#domain").removeAttr("disabled");
			  }
			</script>
			<div class="half_left">

				<div style="float: left; width: 50%;">
					<label for="advertiser">Advertiser:</label>
  					<input style="margin-bottom: 0; width: 200px;" type="text" name="advertiser" id="advertiser" value="${searchData.advertiser?html}" />
  				</div>
  				<label for="keywords">Keywords:</label>
  				<input style="width: 202px; margin-bottom: 0;" type="text" name="keywords" id="keywords" value="${searchData.keywords?html}" />
  				<br><br>
  				<label for="test_text">Domain Name:</label>
  				<input class="full" style="margin-bottom: 0;" type="text" name="domain" id="domain" value="${searchData.domain?html}" />
			</div>

			<div style="clear: both; margin-top: 15px;">
  			<div style="float: right;width:112px;">
  				<button type="button" onClick="clearForm();" style="padding: 5px; width:112px;">Reset Filters</button>
  			</div>

  			<button type="submit" class="big_submit" style="padding: 5px; width: 200px;">Regenerate Report</button>
  			</div>
  			<div class="clear">&nbsp;</div>

		</form>
		
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

                    <div class="rangebar" data-start="${i.begin_percent}" data-end="${i.end_percent}"></div>

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
	$("#domains").multiSelect({selectAll: false}, function() {
		if (($(".multiSelectOptions input:checkbox:checked").length)>0) {
			$("#domain").attr("disabled", "disabled");			
//			$("#fake_hider").css("display", "inline");			
		} else {
			$("#domain").removeAttr("disabled");
//			$("#fake_hider").css("display", "none");			
		}
    });
    <#if searchData.domains??>
      <#list searchData.domains as dId>
		$("input[value='${dId}']").trigger("click");
<#-- $("input[value='${dId}']").attr("checked", true); -->
      </#list>
    </#if>
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
	console.log(txtLen - maxLen);
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
