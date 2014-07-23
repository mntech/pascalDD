<#macro defaultSearchPeriod>
  <#-- set the default dates to this month,
       and include last month if day < 10th -->
 
  <#assign dnow = .now>
  <#assign d = dnow?string("d")?number>
  <#assign defaultStartYear = dnow?string("yyyy")>
  <#-- if we're after the tenth day, just use this month -->
  <#if 10 < d>
    <#assign defaultStartMonthIdx = dnow?string("M")?number - 1>
    <#-- otherwise, use the previous month also -->
  <#else>
    <#assign defaultStartMonthIdx = (dnow?string("M")?number - 2)>
    <#if defaultStartMonthIdx == -1>
      <#assign defaultStartMonthIdx = 11>
    </#if>
    <#-- if we had to go back a month to december, use the previous year -->
    <#if defaultStartMonthIdx == 11>
      <#assign defaultStartYear = (dnow?string("yyyy")?number - 1)?c>
    </#if>
  </#if>
  <#assign defaultEndMonthIdx = dnow?string("M")?number - 1>
  <#assign defaultEndYear = dnow?string("yyyy")>
</#macro>
