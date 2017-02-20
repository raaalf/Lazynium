(function () {
    try {
        if (document.readyState !== 'complete' && document.readyState !== "loaded") {
            return false; // Page not loaded yet
        }
        if (window.jQuery) {
            if (window.jQuery.active) {
                return false;
            } else if (window.jQuery.ajax && window.jQuery.ajax.active) {
                return false;
            }
        }
        return true;
    } catch (ex) {
        return false;
    }
})();