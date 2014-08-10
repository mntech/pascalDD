<!DOCTYPE html>
<html>
<head>
	<title>Agora - Top Advertisers</title>
	
	<link rel="shortcut icon" href="agora_icon.ico">

    <link rel="stylesheet" type="text/css" href="forms.css">

	<script type="text/javascript" src="jq.js"></script>
	<script type="text/javascript" src="jquery.tablesorter.min.js"></script>
	<script type="text/javascript" src="jq.multiselect.js"></script>
	<!--<script type="text/javascript" src="jquery.tablesorter.pager.js"></script>-->
	<link href="new.css" rel="stylesheet" type="text/css" />
		
</head>
<body id="template">

  <#include "header.ftl"/>
  <#include "pagination.ftl"/>

	<div id="page_content">

<#--
        <div style="padding: 15px; margin-bottom: 15px; border-radius: 10px; color: #111; background-color: #eee;"><strong>This report is 
                       under construction, please check back soon for updates.</strong></div>
-->
		<div id="internal">

    <#include "topAdvertisersForm.ftl">

        <hr />

        <div class="column-descriptions">
          <div class="hide-legend"><a href="javascript:$('.column-descriptions').hide()">Hide</a></div>
	  <div>
            <h3>Column Descriptions</h3>
	    <ul>
	      <!--
	      <li>Domains Seen On - Domains the ad was observed on during scraping.</li>
              <li>Advertiser - Company or organization being advertised.</li>
	      -->
	      <li>Chan - Number of unique site/program combinations.</li>
	      <li>[S]ites - Number of sites used in this ad program.</li>
	      <li>[P]rograms - Number of advertising programs (ad images).</li>
	      <li>[L]iteral Occurrences - Relative number of times this ad was seen by the Agora System within the parameters searched.</li>
	      <li>[A]rea - Total pixel area for all images from this advertiser (in millions).</li>
	      <li>[S]tatistical Occurrences - A relative value of ad occurrences corrected for the number of different ads seen on this website at the same time.</li>
	      <li>[H]eterogeneity - A program diversification factor based on file type and shape.</li>
	      <li>D1/D2/D3 - Literal occurrences for chosen domains.</li>
            </ul>
	  </div>
	    <hr/>
        </div>

		<#if resultCount == 0>
		  <h1>No results found</h1>
		<#else>
		  <table width="100%">
		    <tr>
		      <td>

		<@resultRangeText />
				    
		  <span class="pipebreak">|</span>
<#-- http://stackoverflow.com/a/6713917 -->
		  <script type="text/javascript">
(function($,undefined) {
    $.fn.cloneSelects = function(withDataAndEvents, deepWithDataAndEvents) {
        var $clone = this.clone(withDataAndEvents, deepWithDataAndEvents);
        var $origSelects = $('select', this);
        var $clonedSelects = $('select', $clone);
        $origSelects.each(function(i) {
            $clonedSelects.eq(i).val($(this).val());
        });
        return $clone;
    }
})(jQuery);
		  </script>
		  Export: <a href="javascript:$('#top-advertisers-form').cloneSelects().attr('action', 'topAdvertisersCsv.m').submit();">CSV</a><#-- - <a href="#">XLS</a> -->

		      </td>
		      <td align="right"><@pageNumberLinks /></td>
		    </tr>
		  </table>

		</#if>

        <table class="advertisers">
				<thead>
				<tr>
					<th class="left">Domains Seen On</th>
					<th class="left sortable" data-column="company_name">Advertiser</th>
					<th class="center sortable" data-column="channels">Chan</th>
					<th class="center" data-column="programs+channels">P+Chan</th>
					<th class="center sortable" data-column="sites">S</th>
					<th class="center sortable" data-column="programs">P</th>
					<th class="center sortable" data-column="ad_count">L</th>
					<th class="center sortable" data-column="ad_area">A</th>
					<th class="center sortable" data-column="ad_count">S</th>
					<th class="center sortable" data-column="heterogeneity">H</th>
					<th class="center sortable" data-column="splash">SPLASH<br />Score</th>
					<th class="center sortable highlight" data-column="domain1s1" title="${domain1name!''}">D1</th>
					<th class="center sortable highlight"></th>
					<th class="center sortable highlight" data-column="domain2s1" title="${domain2name!''}">D2</th>
					<th class="center sortable highlight"></th>
					<th class="center sortable highlight" data-column="domain3s1" title="${domain3name!''}">D3</th>
					<th class="center sortable highlight"></th>
                </tr>
				</thead>
				<tbody>
				  <#list results as i>
				<tr>
					<td class="left domain_list">
					<#--<div class="overflow">${i.domains_seen_on?replace(",", "<br/>")}</div>-->
                        <div>
                            <table>
                                <tbody>
				  <#list i.domains_seen_on?split(",") as d>
                                    <tr>
                                        <td>${d}</td><td><#--value1--></td><td><#--value2--></td></tr>
                                    </tr>
                                  </#list>
                                </tbody>
                            </table>
                        </div>
					</td>
					<td class="left">${i.company_name}</td>
					<#-- Chan  -->
					<td class="center">${i.channels}</td>
					<#-- P+Chan-->
					<td class="center">${i.programs+i.channels}</td>

					<#-- S_1   -->
					<td class="center">${i.sites}</td>
					<#-- P     -->
					<td class="center">${i.programs}</td>
					<#-- L     -->
					<td class="center">${i.adCount}</td>
					<#-- A     -->
					<td class="center">${(i.ad_area/1000000)?string("0.##")}</td>
					   <#-- or average -->
					   <#--
					   <td class="center">${(i.ad_area / i.adCount / 1000)?floor}</td>
					   -->
					<#-- S_2   -->
					<td class="center">${i.percentAdCount}</td>
					<#-- H     -->
					<td class="center">${i.heterogeneity}</td>
					<#-- SPLASH -->
					<td class="center">${i.splash}</td>

					<td class="center highlight">
					  <#if domain1??>
						${i.domain1s1}
					  </#if>
					</td>
					<td class="center highlight">
					  <#if domain1??>
						${i.domain1s2!''}
					  </#if>
					</td>

					<td class="center highlight">
					  <#if domain2??>
						${i.domain2s1}
					  </#if>
					</td>
					<td class="center highlight">
					  <#if domain2??>
						${i.domain2s2!''}
					  </#if>
					</td>

					<td class="center highlight">
					  <#if domain3??>
						${i.domain3s1}
					  </#if>
					</td>
					<td class="center highlight">
					  <#if domain3??>
						${i.domain3s2!''}
					  </#if>
					</td>
			</tr>
				</#list>

				</tbody>
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

<script type="text/javascript" src="global.js"></script></body>
</html>
