<#include "showDataTable.ftl">
<html>
  <head>
    <title>Admin - Host Administration</title>
  </head>
  <body>
    <h1>Host Administration</h1>
      <li><a href="adminReportList.m">Back to Admin Reports</a></li>
      <li>Note: New hosts are not automatically sync'd to the scraping server.</li>
      <p/><hr/><p/>
      <h2>Add Host</h2>
      <form action="adminHostAdd.m" method="post">
	URL: <input type="text" name="path"/> <br/>
	Title: <input type="text" name="title"/> <br/>
	Comment: <input type="text" name="comment"/> <br/>
	<button type="submit">Add Host</button>
      </form>
      <p/><hr/><p/>

      <#function isActive path>
	<#if path.active>
	  <#return 1>
	<#else>
	  <#return 0>
	</#if>
      </#function>

      <#function isNotActive path>
	<#if path.active>
	  <#return 0>
	<#else>
	  <#return 1>
	</#if>
      </#function>

<#macro showPathsTable cond>
  <table border="1" cellspacing="1">
    <tr><th>URL</th><th>Title</th><th>Comment</th><th>id</th></tr>
    <#list paths as row>
      <#if .vars[cond](row) == 1>
	<tr>
	  <td>${row.path}</td><td>${row.title}</td><td>${row.comment}</td><td>${row.id}</td>
	</tr>
      </#if>
    </#list>
  </table>
</#macro>

<#macro showPathsTable1>
  <table border="1" cellspacing="1">
    <tr><th>URL</th></tr>
    <#list paths as row>
	<tr>
	  <td>${row.path}</td>
	</tr>
    </#list>
  </table>
</#macro>

<#macro showPathsTable2>
  <table border="1" cellspacing="1">
    <tr><th>Title</th></tr>
    <#list paths as row>
	<tr>
	  <td>${row.title}</td>
	</tr>
    </#list>
  </table>
</#macro>

      <h2>Inactive Hosts</h2>
      <@showPathsTable "isNotActive"/>

      <p/><hr/><p/>

      <h2>Active Hosts</h2>
      <@showPathsTable "isActive"/>

      <p/><hr/><p/>

      <h2>Host URLs</h2>
      <@showPathsTable1/>

      <p/><hr/><p/>

      <h2>Host Names</h2>
      <@showPathsTable2/>
  </body>
</html>
