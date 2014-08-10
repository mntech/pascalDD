<#include "showDataTable.ftl">

<html>
  <head>
    <title>DE Status Report</title>
  </head>
  <body>
    <h1>DE Status Report</h1>
    <p/>
    <table border="0" cellspacing="1">
      <tr><td>Ad images:</td><td>${imageFileCount}</td></tr>
      <tr><td>DE entered:</td><td>${dataEntryCount}</td></tr>
      <tr><td>DE remaining:</td><td>${dataEntryRemaining}</td></tr>
      <tr><td colspan="2"><hr/></td></tr>
      <tr><td>DE submitted:</td><td>${deSubmitted}</td></tr>
      <tr><td>DE hold:</td><td>${deHold}</td></tr>
      <tr><td>DE non-ad:</td><td>${deNonAd}</td></tr>
      <tr><td>DE video:</td><td>${deVideo}</td></tr>
      <tr><td>Images not in DE:</td>
	<td>N/A
	</td></tr>
    </table>
    <hr/>
    <h2>Recent DE</h2>
    <@showDataTable rows=recentDeOverview/>
    <p/>
    <hr/>
    <h2>Recent DE with Status</h2>
    <@showDataTable rows=recentDeStatus/>
    <p/>
    <hr/>
    <h2>Recent DE with Staff</h2>
    <@showDataTable rows=recentDeStaff/>
    <p/>
  </body>
</html>
