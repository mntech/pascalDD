// use this script to detect flash in the phantomjs setup
var page = new WebPage(),
    url = 'http://www.biocompare.com/';
page.onConsoleMessage = function (msg) {
    console.log("CLIENT: " + msg);
};

page.open(url, function (status) {
	if (status !== 'success') {
	    console.log('Unable to access network');
	} else {
	    var results = page.evaluate(function() {
		    var type = 'application/x-shockwave-flash';
		    var mimeTypes = navigator.mimeTypes;
		    if(mimeTypes && mimeTypes[type] && mimeTypes[type].enabledPlugin && mimeTypes[type].enabledPlugin.description){
			var version = mimeTypes[type].enabledPlugin.description;
			console.log("Detected flash version is ... : " + version);
		    } else {
			console.log("No flash detected");
		    }
		});
	}
	phantom.exit();
    });
