<#include "showDataTable.ftl">
<html>
  <head>
    <title>View Image</title>
  </head>
  <body>
    <h1>View Image</h1>
      <a href="keywordSearch.m">Back to Keyword Search</a><br/>
      <a href="adminReportList.m">Back to Report List</a><br/>

      <p/><hr/><p/>

      <#assign filename=details["filename"]>
	  <#if filename?ends_with("swf")>
<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="adimages/${filename}" align="middle">
    <param name="movie" value="adimages/${filename}"/>
    <!--[if !IE]>-->
    <object type="application/x-shockwave-flash" data="adimages/${filename}" width="200" height="72">
        <param name="movie" value="adimages/${filename}"/>
    <!--<![endif]-->
        <a href="http://www.adobe.com/go/getflash">
            <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player"/>
        </a>
    <!--[if !IE]>-->
    </object>
    <!--<![endif]-->
</object>
	  <#else>
	    <img alt="${details['id']}" src="adimages/${filename}"/>
	  </#if>

      <p/><hr/><p/>
      <h2>Flag for Followup</h2><p/>
      <form action="adminReportViewImage.m" method="post">
	<input type="hidden" name="id" value="${details['id']}"/>
	Reason: <input type="text" name="followup_reason"/> <button type="submit">Submit</button>
      </form>
      <p/><hr/><p/>
      <h2>Image Details</h2>
      <table border="1" cellspacing="1">
        <#list details?keys as prop>
	  <tr>
	    <th>${prop}</th>
	    <td><#if details[prop]??>${details[prop]?xhtml}<#else>NULL</#if></td>
	  </tr>
      	</#list>
      </table>

      <p/><hr/><p/>
      <h2>Placement Overview</h2>
      <@showDataTable rows=placementOverview/>
  </body>
</html>
