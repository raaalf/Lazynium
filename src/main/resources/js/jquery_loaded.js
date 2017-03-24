var jQueryLoaded = function() {
    try {
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
};
return jQueryLoaded();