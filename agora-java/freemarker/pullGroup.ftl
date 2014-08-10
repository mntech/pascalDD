<html>
  <head><title>Pull Group</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.js"></script>
<script type="text/javascript" src="jquery.headershow.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.headershow.css" media="screen" />
<script type="text/javascript">
            // Activate the plugin here.
            $(function() {
                $('td:has(img)').headershow({
                    id: 1,
                    container: '#img-container',
                    hidecontent: false,
                    txtoverlay: true,
                    delay: 2500,
	    height: 150,
	    width: 350,
                });
            });
</script>
</head>
  <body>
    <div id="img-container"></div>
    <p/>
    <table border="1" cellspacing="1" cellpadding="1">
      <tr>
        <th>URL</th><th>Pull Time</th>
      </tr>
      <#list pulls as p>
        <tr><td>${p.path}</td><td>${p.pulltime?string.medium}</td></tr>
      </#list>
    </table>
    <p/>
    <table border="1" cellspacing="1" cellpadding="1">
      <#list placements as i>
      <tr>
        <td rowspan="2">rownum</td>
	<td rowspan="2">
	  <#if i.filename?ends_with("swf")>
<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="adimages/${i.filename}" align="middle">
    <param name="movie" value="adimages/${i.filename}"/>
    <!--[if !IE]>-->
    <object type="application/x-shockwave-flash" data="adimages/${i.filename}" width="550" height="400">
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
	    <img width="200" height="150" src="adimages/${i.filename}"/>
	  </#if>
	</td>
	<td>Company</td>
	<td>Headline</td>
	<td>Metric</td>
	<td>Date From: ${i.createDate}</td>
	<td rowspan="2">Full text</td>
	<td>Co Type</td>
	<td>Area px</td>
      </tr>
      <tr>
        <td>Product</td>
	<td>Seen on sites: {i.sites}</td>
	<td>Metric</td>
	<td>Date To</td>
	<td>Ad type</td>
	<td>Filetype</td>
      </tr>
      </#list>
    </table>
    <hr/>
    <hr/>
    <hr/>
    <hr/>
    <hr/>
  </body>
</html>
