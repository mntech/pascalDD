// old code from 'dav'
// template for js script to fetch and save a web site using iMacros
// after iMacros script is complete sends a JSON string to a URL with fetch results

var jsLF = "\n";   // used to make the iMacro
var reply = {     // the data set sent back to the system
    id : "[% ID %]"  // the id of the pull we are running
};
var xmlHttpReq = false;	  // will become the request object
var macro;
var retcode;

/////[% LOGIN %]   // for sites that require authentication this will be replaced...

macro = "CODE:";        // required by iMacros
macro += "SET !TIMEOUT_MACRO 120" + jsLF;     // quit with error after timeout
macro += "URL GOTO=[% URL %] " + jsLF;     // fetch the URL
macro += "SAVEAS TYPE=CPL FOLDER=* FILE=[% ID %] " + jsLF; // save as complete page in FILE
macro += "SAVEAS TYPE=PNG FOLDER=* FILE=[% ID %] "; // screenshot


retcode = iimPlay(macro);     // run the macro
if (retcode < 0) {      // failed
    reply.result = 'failed';
    reply.msg = '' ; // iimGetLastError(); // TODO this needs work.
} else {       // success...
    reply.result = 'success';
    reply.msg = '';
}

// send the reply
macro = "CODE:";
macro += "TAB T=1" + jsLF ;
macro += "SET !TIMEOUT_MACRO 4" + jsLF;     // quit with error after 20 sec
macro += "URL GOTO=[% RETURNURL %]/?id=[% ID %]";
macro += '&result=' + reply.result + '&msg=' + reply.msg + jsLF;
macro += 'TAB CLOSE' + jsLF ;
iimPlay(macro);

// window.close();     // close the window

