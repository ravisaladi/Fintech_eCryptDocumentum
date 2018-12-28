
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-92595-2']);
  _gaq.push(['_setDomainName', 'hersam.com']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

// Load the given script
function loadJSElement(scriptPath) {
    var scriptElement = document.createElement("script");
    scriptElement.src = scriptPath;
    document.body.appendChild(scriptElement);
}

// Add a script element as a child of the body
function downloadJSOnload() {
    loadJSElement('//cdn.hersam.com/js/aatw-1534541793.min.js');

    if (document.getElementById('socialbuttons')) {
        loadJSElement('https://apis.google.com/js/plusone.js');
        loadJSElement('//connect.facebook.net/en_US/all.js#xfbml=1&status=0');
        loadJSElement('https://platform.twitter.com/widgets.js');
    }
}

// Check for browser support of event handling capability
if (window.addEventListener) {
    window.addEventListener("load", downloadJSOnload, false);
} else if (window.attachEvent) {
    window.attachEvent("onload", downloadJSOnload);
} else {
    window.onload = downloadJSOnload;
}

$(document).ready(function() {
  // If 'runWhenReady' is defined, run it when document is loaded.
  if (typeof runWhenReady === 'function') {
      runWhenReady()
  }
});