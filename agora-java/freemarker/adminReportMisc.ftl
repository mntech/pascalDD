<#include "showDataTable.ftl">
<html>
  <head>
    <title>Misc Admin Reports</title>
  </head>
  <body>
    <h1>Misc/Ad-Hoc Admin Reports</h1>
      <a href="keywordSearch.m">Back to Keyword Search</a><br/>
      <a href="adminReportList.m">Back to Report List</a><br/>
    <#list reports?keys as reportName>
      <p/><hr/><p/>
      <h2>${reportName}</h2>
      <@showDataTable rows=reports[reportName]/>
    </#list>
  </body>
</html>
