<html>
  <head><title>Pull - ${pull.path} - ${pull.pulltime?date}</title></head>
  <body>
    Ads found on ${pull.path} on ${pull.pulltime?string.medium}
    <p/>
    <table border="1" cellspacing="1" cellpadding="1">
      <#list images as i>
      <tr>
	<td rowspan="4">
	  <#if i.filename?ends_with("swf")>
<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="${cloudImagesPath}/${i.filename}" align="middle">
    <param name="movie" value="${cloudImagesPath}/${i.filename}"/>
    <!--[if !IE]>-->
    <object type="application/x-shockwave-flash" data="${cloudImagesPath}/${i.filename}" width="550" height="400">
        <param name="movie" value="${cloudImagesPath}/${i.filename}"/>
    <!--<![endif]-->
        <a href="http://www.adobe.com/go/getflash">
            <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player"/>
        </a>
    <!--[if !IE]>-->
    </object>
    <!--<![endif]-->
</object>
	  <#else>
	    <img src="${cloudImagesPath}/${i.filename}"/>
	  </#if>
	</td>
	<td>Created: ${i.createDate}</td>
      </tr>
      <tr>
	<td>Seen on sites: ${i.sites}</td>
      </tr>
      <tr>
	<td>A: b</td>
      </tr>
      <tr>
	<td>C: d</td>
      </tr>
      </#list>
    </table>
    <hr/>
    <hr/>
    <hr/>
    <hr/>
    <hr/>
    <img src="pulls/${pull.id}.png"/>
  </body>
</html>
