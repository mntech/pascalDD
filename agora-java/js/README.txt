=== Scraper ===
A simple scraper to crawl a site for ads. JQuery selectors are used to identify
ads on a page. Only HTML snippets and image locations are collected. No image
files or HTML files are saved.


=== Requirements ===
* PhantomJS (pre-1.5)
** depends on quite new version of Qt w/WebKit
** **NOTE** I've modified Qt's included WebKit code to allowed access
** to frames/iframes from different domains. (see JSDOMWindowCustom.h)
* pjscrape (http://nrabinowitz.github.com/pjscrape/)
** included here w/edits
* Flash plugin


=== Summary ===
The main script, scrape_site.js, is run by PhantomJS and passed arguments
including the path and a unique identifier. For example:

$ phantomjs --load-images=no --ignore-ssl-errors=yes \
	scrape_site.js <path> <id> <outfile> <logfile>

The script will crawl the given path and create the output and log files.

Parameters:
	path - the base web site to crawl, eg. www.nature.com
	id - an arbitrary identifier that is included in the json output
	outfile - an array of collected ad data, in json format
	logfile - log messages from pjscrape
