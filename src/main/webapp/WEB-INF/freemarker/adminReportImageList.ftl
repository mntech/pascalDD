<html>
  <head>
</head>
  <body>
    <table border="1" cellspacing="1" cellpadding="1">
      <#assign counter=0>

      <#list images as i>
      <#assign counter=counter+1>
      <tr>
        <td rowspan="2">${counter}</td>
	<td rowspan="2">
	  <#if i.filename?ends_with("swf")>
<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="adimages/${i.filename}" align="middle">
    <param name="movie" value="adimages/${i.filename}"/>
    <!--[if !IE]>-->
    <object type="application/x-shockwave-flash" data="adimages/${i.filename}" width="200" height="72">
        <param name="movie" value="adimages/${i.filename}"/>
    <!--<![endif]-->
        <a href="http://www.adobe.com/go/getflash">
            <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player"/>
        </a>
    <!--[if !IE]>-->
    </object>
    <!--<![endif]-->
</object>
	  <#else>
	    <a class="img" href="adimages/${i.filename}"><img width="200" height="72" alt="${i.imageFileId}" src="adimages/${i.filename}"/></a>
	  </#if>
	</td>
	<td><a href="adminReportViewImage.m?image_num=${i.imageFileId}"># ${i.imageFileId}</a></td>
	<td></td>
	<td>Added: ${i.createDate}</td>
      </tr>
      <tr>
	<td></td>
	<td></td>
	<td></td>
      </tr>
      </#list>
    </table>
<a href="?startImage=${counter+offset}">Next Page</a><br/>
<a href="adminReportList.m">Back to Admin Reports</a>
  </body>
</html>
