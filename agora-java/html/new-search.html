<!DOCTYPE html>
<html>
<head>
	<title>Agora - Search</title>
    
	<link rel="shortcut icon" href="agora_icon.ico">
    
	<script type="text/javascript" src="jq.js"></script>
	<script type="text/javascript" src="jq.quicksearch.js"></script>
	<script type="text/javascript" src="jq.multiselect.js"></script>
	<link href="new.css" rel="stylesheet" type="text/css" />
	
</head>
<body id="template">

  <#include "header.ftl"/>
  <#include "dataUtil.ftl"/>
	
	<div id="page_content">


		<div id="internal">
			<div class="infobox" style="display: none;">
				Keyword Explorer allows you to generate reports based on specific date ranges, keywords, or domains. Use the fields below to filter your search results as desired.
				<div class="micro">
					<a href="#">Click here to hide this box forever. Click Help in the upper right for future assistance.</a>
				</div>
			</div>

			<form action="search.m" method="post" id="search_form">
			
		      <input type="hidden" name="removeHouseAds" value="false"/>
		    <#if Session.tab == "tileAndCompareTab">
		      <input type="hidden" name="aspectRatio" value="tower"/>
		      <#else>
		      <input type="hidden" name="aspectRatio" value=""/>
		      </#if>
			<div class="half_right">
				<div class="int_padding">
					<div style="margin-bottom: 29px;">
						<label for="search_time">Search Dates</label>
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
						</select><br><br>
					</div>
  					<label for="domains">Domains</label>
  					<div id="fake_hider" style="display: none; position: absolute; height: 30px; width: 438px; background-color: rgba(0, 0, 0, 0.2); z-index: 999; border-radius: 10px;"></div>
  					<select class="full" multiple="multiple" id="domains" name="domains[]" size="4">
					  <#list domainList as d>
					    <option value="${d.id}">${d.title}</option>
					  </#list>
					</select>
					<div style="margin-top: 22px; position:relative">
						<input type="checkbox" class="checkbox" name="useDomainSet" id="useDomainSet"> <label for="useDomainSet" class="inliner">Only display results from my Domain Set. <div class="help_wrapper"><a href="#" style="text-decoration: none" id="link_help_bubble"><span class="help_bubble">?</span></a><div class="help_info" id="help_info_id">Your Domain Set allows you to save a list of commonly used domains and quickly create reports from them.</div></div></label>
					</div>
					
  				</div>
  			</div>

			<#include "defaultSearchPeriod.ftl"/>
			<@defaultSearchPeriod/>
			<script type="text/javascript">
				document.getElementById("link_help_bubble").onmouseover=function() {
					document.getElementById("help_info_id").style.display="block";
				}
				document.getElementById("link_help_bubble").onmouseout=function() {
					document.getElementById("help_info_id").style.display="none";
				}
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
			  $(resetDates);

                          $(function() {
			      // end date must not be smaller than start date.
			      // it may be equal
			      function validDates($form) {
				  var startM = $form.find('[name=startMonth]').val(),
				  startY = $form.find('[name=startYear]').val(),
				  endM = $form.find('[name=endMonth]').val(),
				  endY = $form.find('[name=endYear]').val();

				  var startDate = new Date(startM + ' ' + startY)
				  var endDate = new Date(endM + ' ' + endY)

				  if(endDate < startDate) {
				      return false;
				  } else {
				      return true;
				  }
			      }
			      $('#search_form, #search').submit(function(e) {
				  var $this = $(this)
				  // check the date values
				  if(!validDates($this)) {
				      // stop the form from submitting
				      e.preventDefault()
				      alert('Check your search date range. Start date must not come after the end date')
				  }
			      })
			  });
			</script>
			<div class="half_left">

				<label for="test_text">Advertiser</label>
  				<input class="full" type="text" name="advertiser" id="advertiser" />
  				<label for="test_text">Keywords</label>
  				<input class="full" type="text" name="keywords" id="keywords" />
				<label for="test_text">Domain Name</label>
				<input class="full" type="text" name="domain" id="domain" />
			</div>

  			<div style="clear: both;">&nbsp;</div>
  			<div style="float: right;width:112px;">
  				<button type="button" onclick="clearForm();" style="width:112px">Reset Filters</button>
  			</div>
  			<button type="submit" class="big_submit">Generate Report</button>
  			  <div class="clear">&nbsp;</div>

			</form>

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
	$("#search_form").submit(function() {
		$("#domain").removeAttr("disabled");
	});	
});

/*$('#domains').click(function() {
	if($('#domains').is(":checked")) {
		$("#domain").attr("disabled", "disabled");
		$("#fake_hider").css("display", "inline");
	} else {
		$("#domain").removeAttr("disabled");
		$("#fake_hider").css("display", "none");
	}
	
});*/

</script>
<!--[if IE 6]>
<script type="text/javascript" src="supersleight.plugin.js"></script>
<script type="text/javascript">
	$('body').supersleight({shim: 'x.gif'});
</script>
<![endif]-->
<!--[if lte IE 7]>
<script type="text/javascript">
  $('#domains').trigger('click');
  $('#domains').trigger('click');	
</script>
<![endif]-->
<script type="text/javascript">
$('#advertiser').trigger('focus');
</script>
<script type="text/javascript" src="global.js"></script></body>
</html>
