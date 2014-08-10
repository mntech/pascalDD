<html>
  <head>
    <title>Scraper Runtime Dashboard</title>
    <link rel="stylesheet" type="text/css" href="jquery.tablescroll.css"/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript" src="jquery.tablescroll.js"></script>
    <script type="text/javascript">
    // adapted from http://www.jankoatwarpspeed.com/expand-table-rows-with-jquery-jexpand-plugin/
    (function($){
	$.fn.jExpand = function(){
            var element = this;
            //$(element).find("tr:odd").addClass("odd");
            $(element).find("tr.jobs").hide();
            //$(element).find("tr:first-child").show();
            $(element).find("tr.server").click(function() {
		$(this).next("tr").toggle();
            });
	}
    })(jQuery);
    $(function () {
	$('#jobQueue').tableScroll({height: 500});
	$("#servers").jExpand();
    });
    </script>
  </head>
  <body>
    <h1>Scraper Runtime Dashboard</h1>
    <p/>
    <h2>Servers Running <span style="font-size: 12px">(click to expand)</span></h2>
    <table id="servers" border="1" cellspacing="1">
      <thead>
	<tr>
	  <th>Hostname</th>
	  <th>Region</th>
	  <th>Location</th>
	  <th>Updated</th>
	  <th>Running</th>
	</tr>
      </thead>
      <tbody>
        <#list scraperServerList as scraperServer>
	  <tr class="server">
	    <td>${scraperServer.name}</td>
	    <td>${scraperServer.region}</td>
	    <td>${scraperServer.location}</td>
	    <td>${scraperServer.lastUpdated?string.short}</td>
	    <td>${scraperServer.scrapeJobs?size}</td>
	  </tr>
	  <tr class="jobs">
	    <td colspan="5">
	      <table id="x" border="1" bordercolor="blue" cellspacing="1">
		<tr>
		  <th>Run ID</th>
		  <th>Job ID</th>
		  <th>Path</th>
		  <th>Start Time</th>
		</tr>
		<#list scraperServer.scrapeJobs as j>
		  <tr>
		    <td>${j.runId?c}</td>
		    <td>${j.id?c}</td>
		    <td>${j.path.path}</td>
		    <td>${j.startTime?string.short}</td>
		  </tr>
		</#list>
	      </table>
	    </td>
	  </tr>
	</#list>
      </tbody>
    </table>
    <p/>
    <h2>Job Queue <span style="font-size: 12px">(${scrapeJobQueue?size} jobs)</span></h2>
    <table id="jobQueue" border="1" cellspacing="1" style="width: 90%;">
      <thead>
	<tr>
	  <th>Job Id</th>
	  <th>Host</th>
	  <th>Host Id</th><!-- small font -->
	  <th>Region</th>
	  <th>Run ID</th>
	  <th>Added</th>
	</tr>
      </thead>
      <tbody>
	<#list scrapeJobQueue as scrapeJob>
	  <tr>
	    <td>${scrapeJob.id?c}</td>
	    <td>${scrapeJob.path.path}</td>
	    <td><span style="font-size: 12px">${scrapeJob.hostId}</span></td>
	    <td>${scrapeJob.region}</td>
	    <td>${scrapeJob.runId?c}</td>
	    <td>${scrapeJob.createTime?string.short}</td>
	  </tr>
	</#list>
      </tbody>
    </table>
  </body>
</html>
