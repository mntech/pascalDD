<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />

<div id="top_bar">
  
  <div id="userinfo">

    <@sec.authorize ifAnyGranted="Agora_Admin">
      <a id="button_admin_reports" href="adminReportList.m">Admin Reports</a>
    </@sec.authorize>

    <img src="user.png" style="width: 12px; height: auto; margin-right: 2px;"> Logged in as
    <a href="#" title="Logged in as <@sec.authentication property='principal.firstName' /> <@sec.authentication property='principal.lastName' />">
      <@sec.authentication property="principal.firstName" /> <@sec.authentication property="principal.lastName" />
    </a> <span style="padding: 0px 10px">|</span> <a href="logout">Log out</a>

  </div>

  <a href="#" class="logo" title="Essentia: AGORA"><img src="logo_agora.png"></a>
  
</div>


<ul class="navigation" style="float:right; margin-right: 20px;">
    <!--
    <li><a href="#">Password Help</a></li>
    -->
    <li id="brandScopeTab" style="margin-right: 20px;">
        <a style="display: block;" href="http://enter-agora.com/A3lifesci/bs/search">
            <img style="display: block; max-height: 17px; padding: 5px 0;" src="logo_brandscope_large.png" />
        </a>
    </li>
    <li id="helpTab"><a href="help.m">Help</a></li>
</ul>

<script>
  $(function(){
  <#if tab??>
  $("#${tab}").attr("class", "active");
  <#elseif Session.tab??>
  $("#${Session.tab}").attr("class", "active");
  </#if>
  });
</script>

<ul class="navigation">
  <li id="homeTab" style="margin-right: 20px;"><a style="display: block;" href="welcome.m"><img style="display: block; max-height: 17px; padding: 5px 0;" src="tab-logo.png" /></a></li>
  <li id="keywordExplorerTab"><a href="keywordSearch.m">Keyword Explorer</a></li>
  <li id="tileAndCompareTab"><a href="tileAndCompareSearch.m">Tile & Compare Creative</a></li>
  <li id="domainSetsTab"><a href="domainSet.m">Domain Set</a></li>
  <li style="width: 10px; border: 0; border-right: 1px solid #000"></li>
</ul>

<#assign prem1="premium.m">
<#assign prem2="premium.m">
<#assign prem3="premium.m">
<#assign prem4="premium.m">
<#assign prem5="premium.m">
<@sec.authorize ifAnyGranted="Agora_Admin,Agora_Search+">
    <#assign prem1="placementDetailAndMetricSearch.m">
    <#assign prem2="newCreative.m">
    <#assign prem3="topAdvertisers.m">
    <#assign prem4="missingAdvertisersInput.m">
    <#assign prem5="timelineSearch.m">
</@sec.authorize>

<ul class="navigation premium">
    <li id="placementDetailAndMetricTab">
        <a href="${prem1}">Placement Detail & Metric</a>
    </li>
    <li id="newCreativeTab">
        <a href="${prem2}">New Creative</a>
    </li>
    <li id="topAdvertisersTab">
        <a href="${prem3}">Top Advertisers</a>
    </li>
    <li id="missingAdvertisersTab">
        <a href="${prem4}">Missing Advertisers</a>
    </li>
    <li id="campaignTimelineTab">
        <a href="${prem5}">Campaign Timeline</a>
    </li>
</ul>
<div class="nav-label">
Premium
</div>

