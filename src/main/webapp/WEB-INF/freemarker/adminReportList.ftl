<html>
  <head>
    <title>Admin Reports</title>
  </head>
  <body>
    <h1>Admin Reports</h1>
    <h2><a href="/appindex.html">APPLICATION INDEX</a></h2>
    <hr>
    <ul>
      <li><a href="keywordSearch.m">Back to Keyword Search</a></li>
      <li><a href="adminReportDeStatus.m">Data Entry Status</a></li>
      <li><a href="adminReportImageList.m?startImage=0">Image List</a></li>
      <li><a href="adminReportOverview.m">System Statistics</a></li>
      <li><a href="adminReportMisc.m">Ad-Hoc Reports</a></li>
      <li><form action="adminReportViewImage.m">View Image number: <input type="text" name="image_num"/><button type="submit">View</button></form></li>
      <li><form action="adminReportUpdateWelcomeText.m">
	  Update welcome text: <textarea rows="10" cols="50" name="welcome_text">${welcomeText}</textarea>
	  <button type="submit">Update</button>
      </form></li>
      <li><a href="adminHosts.m">Host Administration</a></li>
      <li><a href="adminScraperRuntimeStatus.m">Scraper Dashboard</a></li>
    </ul>
  </body>
</html>
