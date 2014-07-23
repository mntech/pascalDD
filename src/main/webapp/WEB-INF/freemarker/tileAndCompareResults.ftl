<!DOCTYPE html>
<html>
<head>
	<title>Agora - Tile &amp; Compare Creative</title>
	
	<link rel="shortcut icon" href="agora_icon.ico">

	<script type="text/javascript" src="jq.js"></script>
	<script type="text/javascript" src="jq.quicksearch.js"></script>
	<script type="text/javascript" src="jq.multiselect.js"></script>
	<script type="text/javascript" src="jquery.qtip-1.0.0-rc3.min.js"></script>

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
			
		  <form id="search" method="post" class="pagedForm">
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

				</div>
  			</div>

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
  			<div style="float: right;">
  				<button type="clear" style="padding: 5px;">Reset Filters</button>
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
		      <td><@resultRangeText /></td>
		      <td align="right"><@pageNumberLinks /></td>
		    </tr>
		  </table>

		</#if>

		<div id="tcc_results">
			<span class="home_ad_type_tcc" id="tab_tower">Towers</span>
			<span class="home_ad_type_tcc" id="tab_banner">Leaderboards</span>
			<span class="home_ad_type_tcc" id="tab_rect">Rectangles</span>
			<div class="clear"></div>
			<div id="home_image_preview">

<#-- *******************************************************************
        FROM THE DEMO PAGE
     ******************************************************************* -->
			<#macro showImage i maxWidth maxHeight class>
			  <#assign dispSize = Session.util.getSizeWithMax(i.size, maxWidth, maxHeight) />
			  <#if i.filename?ends_with("swf")>
			    <#--			    <div style="z-index: 0; position: absolute; width: ${dispSize?split("x")[0]}px; height: ${dispSize?split("x")[1]}px;" class="bordered ${class}">-->
			    <div style="z-index: 0; position: absolute;" class="bordered ${class}">
			      <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="adimages/${i.filename}" align="middle">
				<param name="movie" value="adimages/${i.filename}"/>
				<!--[if !IE]>-->
				<object type="application/x-shockwave-flash" data="adimages/${i.filename}" width="${dispSize?split("x")[0]}" height="${dispSize?split("x")[1]}" wmode="opaque">
				  <param name="movie" value="adimages/${i.filename}"/>
				  <!--[endif]-->
				  <a href="http://www.adobe.com/go/getflash">
				    <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player"/>
				  </a>
				  <!--[if !IE]>-->
				</object>
				<!--[endif]-->
			      </object>
			    </div>
			    <div style="z-index: 1; position: absolute; width: ${dispSize?split("x")[0]}px; height: ${dispSize?split("x")[1]}px;">
			      <a class="lightbox" href="adimages/${i.filename}">
				<img class="${class}" id="img_${i.image_num?c}" width="${dispSize?split("x")[0]}" height="${dispSize?split("x")[1]}" src="fancybox/blank.gif" style="opacity: 0"/>
			      </a>
			    </div>
			    <#-- layout hack: if we ONLY have flash files they all wind up overlapping in weird ways, this keeps it from happening -->
			    <div><img class="${class}" src="fancybox/blank.gif" style="opacity: 0"/></div>
			    <#else>
			      <a class="lightbox" href="adimages/${i.filename}">
				<#-- set the size here explicitely because the css stretchs them out and makes them have odd aspect ratios -->
<#--				     width="${i.size?split("x")[0]}" height="${i.size?split("x")[1]}"-->
<#--				     width="${dispSize?split("x")[0]}px" height="${dispSize?split("x")[1]}px"-->
				<img class="${class} bordered" id="img_${i.image_num?c}"
				     src="imageresizer?maxwidth=${maxWidth}&maxheight=${maxHeight}&flip=n&filename=${i.filename}"/>
			      </a>
			    </#if>
			</#macro>


				<div id="images">
				  <table cellspacing="4" cellpadding="4">
				    <tr align="center">
				      <#assign row = 0 />
				      <#list results as i>
                                        <#if aspectRatio == "tower">
					  <#assign imgPerRow = 12 />
                                          <#-- should align with CSS #tcc_results .tower_small -->
					  <#assign imgWidth = 54 />
					  <#assign imgHeight = 208 />
					  <#assign dispClass = "tower_small" />
					<#elseif aspectRatio == "banner">
					  <#assign imgPerRow = 3 />
                                          <#-- should align with CSS #tcc_results .leaderboard_small -->
					  <#assign imgWidth = 284 />
					  <#assign imgHeight = 35 />
					  <#assign dispClass = "leaderboard_small" />
					<#elseif aspectRatio == "rect">
					  <#assign imgPerRow = 6 />
                                          <#-- should align with CSS #tcc_results .rectangle_small -->
					  <#assign imgWidth = 130 />
					  <#assign imgHeight = 130 />
					  <#assign dispClass = "rectangle_small" />
					</#if>
					<#if (i_index != 0) && (i_index % imgPerRow) == 0>
					  <#assign row = row + 1 />
				          </tr><tr align="center">
				        </#if>
                                          <td><div style="position:static;">
					    <@showImage i imgWidth imgHeight dispClass /></div>
<div id="img_${i.image_num?c}_data" style="display:none">
#${i.image_num?c}<br/>Company: ${i.company_name}
<#if 0 < i.product?length><br/>Product: ${i.product}</#if>
    <br/>Ad text: <#if 50 < i.description?length>${i.description?substring(0, 50)}...<#else>${i.description}</#if>
    <br/>Occurrences: ${i.adCount}
    <br/>Share: ${i.percentAdCount}
    <br/>Seen: ${i.createdate} - ${i.lastDate}
</div>

					  <script>
					  $("#img_${i.image_num?c}").qtip({
					      content: $("#img_${i.image_num?c}_data").html(),
					      show: 'mouseover',
					      hide: 'mouseout',style: { name: 'dark', tip: true },   position: {
						  corner: {
						      <#if aspectRatio == "tower">
							  <#if (i_index % 12) < 7>
							  target: 'rightBottom',
						          tooltip: 'leftBottom'
							  <#else>
							  target: 'leftBottom',
						          tooltip: 'rightBottom'
						          </#if>
						      <#elseif aspectRatio == "banner">
							  <#if row < 2 && (i_index % 3) < 2>
							  target: 'rightTop',
						          tooltip: 'leftTop'
							  <#elseif row < 2>
							  target: 'leftTop',
						          tooltip: 'rightTop'
							  <#elseif (i_index % 3) < 2>
							  target: 'rightBottom',
						          tooltip: 'leftBottom'
							  <#else>
							  target: 'leftBottom',
						          tooltip: 'rightBottom'
						          </#if>
						      <#elseif aspectRatio == "rect">
							  <#if row == 0 && (i_index % 6) < 3>
							  target: 'rightTop',
						          tooltip: 'leftTop'
							  <#elseif row == 0>
							  target: 'leftTop',
						          tooltip: 'rightTop'
							  <#elseif (i_index % 6) < 3>
							  target: 'rightBottom',
						          tooltip: 'leftBottom'
							  <#else>
							  target: 'leftBottom',
						          tooltip: 'rightBottom'
						          </#if>
					              </#if>
						  }
					      }
					  });
					  </script>
					</td>
				      </#list>
				    </tr>
				  </table>
				</div>
			</div>
			
<table width="100%">
  <tr>
    <td><@resultRangeText /></td>
    <td align="right"><@pageNumberLinks /></td>
  </tr>
</table>

		</div>

		</div>

<#include "footer.ftl"/>
	
	</div>

<script>

$(document).ready(function(){
    $("#domains").multiSelect();
    <#if searchData.domains??>
      <#list searchData.domains as dId>
	$("input[value='${dId}']").attr("checked", true);
      </#list>
    </#if>
	$('.home_ad_type_tcc').removeClass('home_ad_active');
    $('#tab_${searchData.aspectRatio}').addClass('home_ad_active');
});

$("a.lightbox").fancybox({
	'overlayShow'	: true,
    'titlePosition' : 'inside',
    'titleFormat' : function(titleStr, selectedArray, selectedIndex) {
	var divname = $("img", selectedArray[selectedIndex]).attr("id") + "_data";
	return $("#" + divname).html();
    },
// make sure to hide the qtip so it doesnt interfere
'onStart' : function() { $(".qtip").hide(); },
	'transitionIn'	: 'elastic',
	'transitionOut'	: 'elastic'
});

        $('#tab_tower').click( function() {
	    $("input[name='aspectRatio']").attr("value", "tower");
	    // reset if moving to a different tab
	    if ("tower"!=="${searchData.aspectRatio}") {
		$("input[name='offset']").attr("value", "0");
	    }
	    $("#search").submit();
	});
	$('#tab_banner').click( function() {
	    $("input[name='aspectRatio']").attr("value", "banner");
	    // reset if moving to a different tab
	    if ("banner"!=="${searchData.aspectRatio}") {
		$("input[name='offset']").attr("value", "0");
	    }
	    $("#search").submit();
	});
	$('#tab_rect').click( function() {
	    $("input[name='aspectRatio']").attr("value", "rect");
	    // reset if moving to a different tab
	    if ("rect"!=="${searchData.aspectRatio}") {
		$("input[name='offset']").attr("value", "0");
	    }
	    $("#search").submit();
	});

</script>

<script type="text/javascript" src="global.js"></script></body>
</html>
