scraper = function(id, depth) { jQuery.fn.outerHTML = function() { return $('<div>').append( this.eq(0).clone() ).html(); };
// The above line MUST be line #1 for the line numbers in the log ("injected code") to match the line numbers in this file

// rules to match ads
// id, selector rule
var selectors = [
	 [ 0,"#FLASH_AD"] // genengnews.com
        ,[ 1,"#Leaderboard_container > a:has(img)"] // genengnews.com
        ,[ 2,"div[class^='banner'] > a:has(img)"] // genengnews.com
        ,[ 3,"a:has(img)[href*='doubleclick']"] // nature.com
        ,[ 4,"a:has(img)[href*='/ads/']"] // genomeweb
        ,[ 5,"a:has(img[alt='Corporate Banner'])"] // ADME T
        ,[ 6,"a:has(img[alt='Satellite Banner'])"]
        ,[ 7,"a:has(img[alt='Skyscraper Banner'])"]
        ,[ 8,"a:has(img)[href*='/AdManager/']"] // American Biotechnology Laboratory
        ,[ 9,"object:has(param[value*='/Media/banners/'])"]
        ,[10,"a.steveAd:has(img)"] // lab manager
        ,[11,"#leftcolads a:has(img)"] // lab x
        ,[12,"a.ASPBanner_Ad_Text:has(img)"]
        ,[13,"div.BannerDesc a:has(img)"]
        ,[14,"div.HomePageBanner a:has(img)"]
        ,[15,"div.ads a:has(img)"] // separations now
        ,[16,"a[id*='_lnkAd_']:has(img)"] // Americanpharmaceuticalreview
	,[17,"object[id^='ctl00_divAd_']"] // TODO needs checked when updating flash
        ,[18,"div.ad_wrapper a:has(img)"] // LCGC Mag (aka chromatographyonline.findanalytichem.com)
        ,[19,"div#ads a:has(img)"] // Spectroscopynow.com
        ,[20,"div#ad_boxes a:has(img)"] // aaas.org
        ,[21,"div.headerContainer1 a:has(img:not([src*='empty']):not([src*='subscribe']))"] // biotechniques.com
        ,[22,"div#btn_LRWraper a:has(img:not([src*='empty']):not([src*='email']))"]
        ,[23,"div#btn_RRWraper a:has(img)"]
        ,[24,"div.moreBioTechs a:has(img)"]
        ,[25,"object[id^='OAS_RMF_'][id$='_FLASH']"]
        ,[26,"div#right a[href]:has(img)"] // drugdiscoverytoday
        ,[27,"div#middle a:has(img[src*='banner'])"]
        ,[28,"#fancybox-frame a:has(img)"] // europeanpharmaceuticalreview
        ,[29,"#fancybox-frame embed"]
        ,[30,"div#banner1 a:has(img)"]
        ,[31,"div[id^='ftdivid'] embed"]
        ,[32,".adspace a:has(img)"] // labmate-online.com
        ,[33,"a:has(img[src*='adimage.php'])"] // drugdiscoverynews
        ,[34,"a:has(img[title='Advertisement'])"] // biocompare
	 ,[35,"div.ad embed"]
	 ,[36,"a[href*='bannerid'][href*='oadest']:has(img)"]
	 ,[37,"a[target='Advert'][style^='background-image:url']"] // chromocyte
	 ,[38,"div#FeaturedAd a:has(img)"]
	 ,[39,"div.advertBar a:has(img)"]
	 ,[40,"div#ads1 a:has(img)"] // chemspider
	,[41,"a[id*='AdRotator']"] // microscopy.info
	,[42,"a[baseURI*='microscopy.info']:has(img)"]
	,[43,"a[href*='adjuggler']:has(img)"] // fiercepharma
	,[44,"td:contains('Sponsors') a:has(img)"] // microscopy-news
	,[46,"div.ad_wrapper a:has(img)"] // pharmtech
	,[47,"left_auxilary a:has(img)"]
	,[48,"table:contains('sponsors') a:has(img)"] // chemspider
	,[49,"div#home_left a[baseURI*='microscopy-today']:has(img)"] // microscopy-today
	,[50,"embed[flashvars*='ebAdID']"] // biocompare
	,[51,"div.right-module:has(h6.callout-underline) a:has(img)"] // ieee
	,[52,"a:has(img[src*='/advertising/'])"] // specchemonline
	,[53,"div.adSpace a:has(img)"] // pubs.acs.org
	,[54,"div.sectiontitle:contains('Industry Partners') + div.sectionbody a:has(img)"] // bioresearch online
	,[55,"div#homesponsor a:has(img)"] // ispe
	,[56,"div.rotating_billboard_wrap a:has(img)"]
	,[57,"div#leftsponsors a:has(img)"]
	,[58,"div#rightsponsors a:has(img)"]
	,[59,"div#promoimage a:has(img)"]
	,[60,".google-ad a:has(img)"]
	,[61,".google-ad embed"]
	,[62,"div[class^='yui-'] > div[class^='sidebar-'] a:has(img)"] // portal.acs.org (not front page)
	,[63,"a:has(img)[href*='ads.plos.org']"] // plosone.org
	,[64,"embed[src*='ads.plos.org']"]

	,[66,"div[id*='adWrapper'] embed"] // biocompare
	,[67,"img[onClick^='gEbStdBanners']"] // genengnews - javascript click , no <a>
	,[68,"div.Box_ad a:has(img)"] // genengnews
	,[69,"embed[src*='ds.serving-sys.com']"] // life science leader
	,[70,"td.sidecol a:has(img), td.sidecol embed"]
	,[71,"div.featured a:has(img)"] // biocompare
        ,[72,".adContainer a:has(img), .adContainer object, .adContainer embed"] // biocompare
        ,[73,".productReview .articleListThumbnail a:has(img)"] // biocompare
        ,[74,".auctionEvents a:has(img)"] // labx
        ,[75,".extraBanners a:has(img)"] // labx
        ,[76,"a.bannerImage:has(img)"] // labx
        ,[77,"td:contains('Sponsors') embed"] // SEQanswers
        ,[78,"embed[flashVars*='bannerid']"] // SEQanswers
	 ,[79,".skyscraper-ad object"] // bio search online .com
	 ,[80,".skyscraper-ad embed"] // bio search online .com
	 ,[81,"div#Leaderboard_container object"] // genengnews
	 ,[82,"div#Leaderboard_container embed"] // genengnews
	 ,[83,".Box_ad object"] // genengnews
	 ,[84,".Box_ad embed"] // genengnews
	 ,[85,"a:has(img.sponsor_logo)"] // genengnews
	 ,[86,"div.block.block-block a:has(img)"] // laboratoryequipment.com
	 ,[87,"embed[flashVars*='pagead']"] // dddmag
	 ,[88,"a:has(img)[href*='adScheduleNo']"] // labmanager
	 ,[89,"a:has(img[src*='impressionNo'])"] // labmanager
	 ,[90,"a:has(img[src*='realmedia.com'])"] // biotechniques
	 ,[91,"object[id^='ebStdBannerFlash']"] // biotechniques
	 ,[92,"embed[flashVars*='bannerads']"] // pharmtech
	 ,[93,".verticalcomponent a:has(img)"] // pharmtech
	 ,[94,"a[href*='subscribe']:has(img)"] // ddw-online
	 ,[95,"embed[src*='CHROMacademyLiveExternal']"] // chromatography online
	 ,[96,"embed[src*='checkm8']"] // chromatography online
	 ,[97,"a:has(img[src*='currentissue'])"] // the scientist
	 ,[98,"a[href*='SignUp']:has(img)"] // globalspec
	 ,[99,"div[id*='HouseAds'] a:has(img)"] // aacc
	 ,[100,"div[id*='ProductShowcase'] a:has(img)"] // ACS chemical buyers guide
	 ,[101,"a.adPeel:has(img.adLarge)"] // ACS chemical buyers guide
	 ,[102,"div[class*='ads'] a:has(img)"] // aacc

	// 103-156 new selectors for "thermo" domains, added July 2013
	 ,[103,"div[class*='bottom-ads-container'] a:has(img)"] //pollutiononline
	 ,[104,"div[class*='ad-white-canvas'] a:has(img)"]  //pollutiononline
	 ,[105,"a[id*='aw0']:has(img[src*='pagead2'])"]  //pulpandpaperonline
	 ,[107,"div[class*='ad-display'] object"]  //ogj
	 ,[108,"div[class*='desktop-ad'] object"]  //ogj
	 ,[118,"div[class*='top-ad-container'] a:has(img)"] 
	 ,[119,"div[class*='col three'] a:has(img)"] // aiha.org
	 ,[120,"div[class*='ad-row'] a:has(img)"] //aiha.org
	 ,[121,"div[class*='right-sidebar'] a:has(img)"] //aiha.org
	 ,[122,"div[class*='topAdArea'] a:has(img)"] //rimbach.com
	 ,[123,"div[class*='sideAdSpace'] a:has(img)"] //rimbach.com
	 ,[124,"div[class*='bodyContent'] a:has(img[src*='RimPub/banners']) "] //rimbach.com
	 ,[125,"div[class*='bodyContent'] a:has(img[src*='rimpub/banners']) "] //rimbach.com
	 ,[126,"div[id*='ContentLeft'] a:has(img[src*='industrial'])"] //rimbach.com
	 ,[127,"#leftAds a:has(img[src*='advertiser'])"] //rimbach.com
	 ,[128,"div[class*='bodyContent'] a:has(img[src*='IHN/cards']) "] //rimbach.com
	 ,[129,"div[id*='ContentLeft'] a:has(img[src*='advertiser']) "]//pollutionequipmentdirectory.com
	 ,[130,"a:has(img[src*='cdn.atdmt'])"]  //laserfocusworld
	 ,[134,"a[href*='burkert']:has(img)"] //gasesmag
	 ,[135,"a[href*='semi-gas']:has(img)"] // gasesmag
	 ,[135,"a[href*='ald-avs']:has(img)"] // gasesmag
	 ,[136,"object[id^='FLASH_200_22_flash_expanding_0']"] // mdtmag
	 ,[136,"object[id^='FLASH_200_22_flash_floating_0']"] // mdtmag
	 ,[136,"object[id^='jcornerBigObject']"] // worldcement
	 ,[137,".advert a:has(img)"]  // worldcement
	 ,[138,"#ad-expander a[href*='shaletech']:has(img)"]  //worldoil
	 ,[139,"a:has(img[src*='adtechus'])"] //worldoil
	 ,[140,"a:has(img[src*='pagepeel'])"] //worldoil
	 ,[141,"div[id*='google_flash'] object"] //pipelineandgasjournal
	 ,[142,"div[id*='google_ads'] object"] //pipelineandgasjournal
	 ,[143,"div[id*='banner_conatiner'] a:has(img)"] // eptq  (spelled wrong on purpose)
	 ,[144,"a[href*='news.aist']:has(img[src*='banners'])"] //aist
	 ,[145,"embed[src*='usweb.dotomi']"] //newequipment
	 ,[146,"div[id*='d_banner'] a:has(img)"]  //ans.org
	 ,[147,"div[class*='ad-slot'] a:has(embed[id*='ban'])"]  //pharmamanufacturing
	 ,[148,"div[id*='bannerAds'] a:has(img)"]
	 ,[149,"div[class*='homeSection'] a[onclick*='bannerads']:has(img)"] //isa
	 ,[150,"div[class*='homeSection'] a[onclick*='Ad']:has(img)"] //isa
	 ,[151,"div[class*='ads-right'] a:has(embed)"] //processingmagazine
	 ,[152,"embed[flashvars*='googleadservices']"] // http://www.pharmaceutical-technology.com/
	 ,[153,"object[data*='2mdn.net/ads']"]  //www.pharmaceutical-technology.com/
	 ,[154,"a[id*='aw0']:has(img[class*='img_ad'])"] //pollutiononline
	 ,[155,"div[class*='leaderboard'] embed"]  //pharmamanufacturing
	 ,[156,"div[class*='leaderboard'] a:has(img)"]  //pharmamanufacturing
	 ,[157,"div[class*='PromoBoxHeadLeft'] a:has(img)"]  //laserfocusworld
     ,[158,"a:has(img[src*='PEmobil'])"] //power-eng.com
     ,[159,"div[class*='standard'] a:has(img[src*='Click_To_Subscribe'])"] //power-eng.com
     ,[160,"div[class*='parbase'] a[href*='coal-gen']:has(img)"]  //power-eng.com
     ,[161,"a:has(i.sourcing-help)"]  //poweronline & pollutiononline
     ,[162,"div[class*='img-canvas'] a:has(img[src*='baseline'])"]  //safetyonline
     ,[163,"embed[src*='Lexmark']"]// safeteyonline
     ,[164,"object[id*='google_flash_embed']"] //ehstoday
     ,[165,"a:has(img[src*='cdn.adnxs'])"] //ehstoday
     ,[166,"a:has(img[src*='/ext/resources/Partners/'])"] //ishn
     ,[167,"div a:has(img[src*='gasesmag'])"]//gassesmag
     ,[168,"div[class*='AD-WebPart'] a:has(img)"] // safetysourceinc
     ,[169,"div[class*='section'] a[href*='energyhub']:has(img)"] //pennenergy
     ,[170,"div[id*='SubBreadPic'] a[href*='omeda']:has(img)"]
     ,[171,"img[onClick^='EBG.ads']"] //ogj.com  no <a> referenced selector #67
     ,[172,"a[href*='wglnetwork']:has(img)"] //hydrocarbonprocessing
     ,[173,"div a[href*='gulfpub']:has(img[src*='Maintenance'])"] //hydrocarbonprocessing
     ,[174,"a[href*='cvent.com/events']:has(img)"]  //hydrocarbonprocessing
     ,[175,"a[href*='adfarm']:has(img[src*='img-cdn'])"] //hydrocarbonprocessing
     ,[176,"object[id^='curlypage_flag3']"] // pipelinandgasjournal
     ,[177,"div[class*='Content'] a[href*='controlglobal']:has(img)"] //controlglobal
     ,[178,"a: has(img[src*='aka-cdn'])"] //chemicalprocessing
     ,[179,"div[class*='image'] a:has(img[src*='//www.flowcontrolnetwork.com/ext/resources/magazine-issues/2013/FC-0813/thumb/FC-0813-Cover.jpg?1377880684'])"] //flowcontrolnetwork
     ,[180,"div[class*='homeSection'] a[href*='Advertisement']:has(img)"] //isa.org
     ,[181,"object[data*='isa.org/graphics/intech']"] //isa.org
     ,[182,"div[class*='homeSection'] a:has(img[src*='Training-Camp'])"] //isa.org
     ,[183,"object[id^='ebRichBannerFlash']"] //processingmagazine

// keep at bottom until not matching anything, use more selective rules
	,[45,"object:has(embed)"] // pharmtech // TODO fix!
];

var ads = [];
var seenAds = [];
$.expr[':'].internal = function(link){
	return !link.href.match(/^mailto\:/)
	&& (link.hostname.indexOf(location.hostname) != -1)
	&& (link.href.indexOf("#disqus_thread") == -1)
	&& (link.href.indexOf("#discuss") == -1)
	&& (link.href.indexOf("#Comments") == -1)
	&& (link.href.indexOf("#") == -1);
};

// combine all iframe content recursively
var pages = $("body");
var body = $("body");
var iframes = $("iframe", body);
while (iframes.length > 0) {
	$.merge(pages, iframes.contents());
	// get next set of iframes
	body = $("body", iframes.contents());
	iframes = $("iframe", body);
}

	for (var i = 0; i < selectors.length; ++i) {
	    var foundTags = $(selectors[i][1], pages);
	    if (foundTags.length == 0)
	    	continue;

		console.log("Rule num " + selectors[i][0] + " matched " + foundTags.length + " tags");
		var fImg = function() {
		    if (seenAds.indexOf($(this)[0]) > -1) {
			return null;
		    }
		    seenAds.push($(this)[0]);
		    return {selId: selectors[i][0],
			    html: $(this).outerHTML(),
			    src: $(this).find("img")[0].src,
			    dest: $(this)[0].href};
		};
		ads = ads.concat(foundTags.filter("a:has(img[src]^='')").map(fImg).toArray());
		var fNotImg = function() { // only seen on chromocyte.com
		    if (seenAds.indexOf($(this)[0]) > -1) {
			return null;
		    }
		    seenAds.push($(this)[0]);
		    return {selId: selectors[i][0],
			    html: $(this).outerHTML(),
			    src: window.location.host.toLowerCase() + $(this).attr("style").replace(new RegExp("^background-image:url\\("), "").replace(new RegExp("\\)$"), ""),
			    dest: $(this)[0].href};
		};
		// the selector in the filter() like this is buggy, so  we need to do it manually
// 		ads = ads.concat(foundTags.filter("a[style^='background-image:url']").map(fNotImg).toArray());
		try {
		    ads = ads.concat(
			foundTags.filter("[style]").filter(
			    function() {
				// filter where we have a style attribute with a background property (the image url)
				return $(this).attr("style") &&
				    $(this).attr("style").indexOf("background") != -1;
			    }
			).map(fNotImg).toArray()
		    );
		} catch(err) {
		    console.log("ERROR: " + err);
		    console.log("continuing");
		}
		var fObj = function() {
		    if (seenAds.indexOf($(this)[0]) > -1) {
			return null;
		    }
		    seenAds.push($(this)[0]);
		    return {selId: selectors[i][0],
			    html: $(this).outerHTML(),
			    src: $(this).find("embed")[0].src,
			    dest: "<unknown>"}; // TODO
		};
		ads = ads.concat(foundTags.filter("object").map(fObj).toArray());
		var fEmbed = function() {
		    if (seenAds.indexOf($(this)[0]) > -1) {
			return null;
		    }
		    seenAds.push($(this)[0]);
		    return {selId: selectors[i][0],
			    html: $(this).outerHTML(),
			    src: $(this)[0].src,
			    dest: "<unknown>"}; // TODO
		};
		ads = ads.concat(foundTags.filter("embed:not(object embed)").map(fEmbed).toArray());

		// for the specific JS-click ads match by rule #67
		var fJsClick = function() {
		    if (seenAds.indexOf($(this)[0]) > -1) {
			return null;
		    }
		    seenAds.push($(this)[0]);
		    return {selId: selectors[i][0],
			    html: $(this).outerHTML(),
			    src: $(this)[0].src,
			    dest: eval(/.*(gEbStdBanners\[\d+\]).*/.exec(""+$(this)[0].onclick)[1] + ".origJumpUrl")};
		};
		ads = ads.concat(foundTags.filter("img[onClick^='gEbStdBanners']").map(fJsClick).toArray());
	}
    return {id: id, depth: depth, ads: ads, url: $(location).attr('href')};
};

if (phantom.args.length < 4) {
	console.log("Error: not enough arguments.");
	phantom.exit();
}

var path = phantom.args[0];
var id = phantom.args[1];
var depth = phantom.args[2];
var outfile = phantom.args[3];
var logfile = phantom.args[4];

// pjscrape and its dependencies (in lib and client)
// are in the same dir as this script because libraryPath,
// when set in this script,
// doesn't allow pjscrape to load the deps from there
phantom.injectJs('pjscrape.js');

pjs.config({ 
	log: 'file', // 'stdout', 'file' (set in config.logFile) or 'none'
	format: 'json', // 'json' or 'csv'
	writer: 'file', // 'stdout' or 'file' (set in config.outFile)
	logFile: logfile,
	outFile: outfile
});

var page = new WebPage();
page.onConsoleMessage = function (msg) {
    console.log("CLIENT: " + msg);
};

pjs.addSuite({
	url: path,
	maxDepth: depth,
        moreUrls: 'a:internal',
//	moreUrls: 'a:not([href^="http"])', // *MUST* use internal double quotes here due to pjscrape code (line 597 "return _pjs.getAnchorUrls('" + s.opts.moreUrls + "');")
	scraper: new Function("var x=" + scraper + ";return x('" + id + "');")
});
pjs.init();

