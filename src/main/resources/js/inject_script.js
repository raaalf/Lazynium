(function (scriptBody) {
    var script = document.createElement('script');
    var head = document.getElementsByTagName('head')[0];
    var done = false;
    script.onload = script.onreadystatechange = (function () {
        if (!done && (!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete')) {
            done = true;
            script.onload = script.onreadystatechange = null;
            head.removeChild(script);
        }
    });
    script.type = 'text/javascript';
    var text = document.createTextNode(scriptBody);
    script.appendChild(text);
    head.appendChild(script);
})(arguments[0]);