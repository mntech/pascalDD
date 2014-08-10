<html>
  <head><title>Agora Overview</title></head>
  <body>

    <table>
      <tr>
	<td colspan="7" style="font-size: 40">System Statistics</td>
      </tr>
      <tr style="background-color: #c0e7f5">
	<td>Hosts: ${hostCount}</td>
	<td>Pulls: {pullCount}</td>
	<td>Pulls in last 24h: {pulls24h}</td>
	<td>Placements: {placementCount}</td>
	<td>Placements in last 24h: xx</td>
	<td>Images: {imageCount}</td>
	<td>New images in last 24h: xx</td>
      </tr>
    </table>

    <table>
      <tr>
	<td colspan="3" style="font-size: 30">Latest Pulls</td>
      </tr>
      <tr>
	<td>Site</td>
	<td>Date</td>
	<td>URLs</td>
	<td>Placements</td>
      </tr>
      <#--
      <#list latestPullData as pullData>
	<tr style="background-color: #c0c0c0">
	  <td><a href="pullGroup.m?id=${pullData.pullGroupId}">${pullData.path}</a></td>
	  <td>${pullData.pulltime?string.medium}</td>
	  <td>${pullData.urls}</td>
	  <td>${pullData.placements}</td>
	</tr>
      </#list>
      -->
    </table>

  </body>
</html>

