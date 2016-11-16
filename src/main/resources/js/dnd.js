var DndSimulatorDataTransfer = function () {
    this.data = {};
};

DndSimulatorDataTransfer.prototype.dropEffect = "move";

DndSimulatorDataTransfer.prototype.effectAllowed = "all";

DndSimulatorDataTransfer.prototype.files = [];

DndSimulatorDataTransfer.prototype.items = [];

DndSimulatorDataTransfer.prototype.types = [];

DndSimulatorDataTransfer.prototype.clearData = function (format) {
    if (format) {
        delete this.data[format];

        var index = this.types.indexOf(format);
        delete this.types[index];
        delete this.data[index];
    } else {
        this.data = {};
    }
};

DndSimulatorDataTransfer.prototype.setData = function (format, data) {
    this.data[format] = data;
    this.items.push(data);
    this.types.push(format);
};

DndSimulatorDataTransfer.prototype.getData = function (format) {
    if (format in this.data) {
        return this.data[format];
    }

    return "";
};

DndSimulatorDataTransfer.prototype.setDragImage = function (img, xOffset, yOffset) {
};

DndSimulator = {
    simulate: function (sourceElement, targetElement, sourceSelector, selectorTarget) {
        switch(sourceSelector) {
            case "css":
                sourceElement = document.querySelector(sourceElement);
                break;
            case "xpath":
                sourceElement = document.evaluate(sourceElement, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
                break;
        }
        switch(selectorTarget) {
            case "css":
                targetElement = document.querySelector(targetElement);
                break;
            case "xpath":
                targetElement = document.evaluate(targetElement, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
                break;
        }


        var sourceCoordinates = sourceElement.getBoundingClientRect();
        var targetCoordinates = targetElement.getBoundingClientRect();

        var mouseDownEvent = this.createEvent(
            "mousedown",
            {
                clientX: sourceCoordinates.left,
                clientY: sourceCoordinates.top
            }
        );

        sourceElement.dispatchEvent(mouseDownEvent);

        var dragStartEvent = this.createEvent(
            "dragstart",
            {
                clientX: sourceCoordinates.left,
                clientY: sourceCoordinates.top,
                dataTransfer: new DndSimulatorDataTransfer()
            }
        );

        sourceElement.dispatchEvent(dragStartEvent);

        var dragEvent = this.createEvent(
            "drag",
            {
                clientX: sourceCoordinates.left,
                clientY: sourceCoordinates.top
            }
        );

        sourceElement.dispatchEvent(dragEvent);

        var dragEnterEvent = this.createEvent(
            "dragenter",
            {
                clientX: targetCoordinates.left,
                clientY: targetCoordinates.top,
                dataTransfer: dragStartEvent.dataTransfer
            }
        );

        targetElement.dispatchEvent(dragEnterEvent);

        var dragOverEvent = this.createEvent(
            "dragover",
            {
                clientX: targetCoordinates.left,
                clientY: targetCoordinates.top,
                dataTransfer: dragStartEvent.dataTransfer
            }
        );

        targetElement.dispatchEvent(dragOverEvent);

        var dropEvent = this.createEvent(
            "drop",
            {
                clientX: targetCoordinates.left,
                clientY: targetCoordinates.top,
                dataTransfer: dragStartEvent.dataTransfer
            }
        );

        targetElement.dispatchEvent(dropEvent);

        var dragEndEvent = this.createEvent(
            "dragend",
            {
                clientX: targetCoordinates.left,
                clientY: targetCoordinates.top,
                dataTransfer: dragStartEvent.dataTransfer
            }
        );

        sourceElement.dispatchEvent(dragEndEvent);

        var mouseUpEvent = this.createEvent(
            "mouseup",
            {
                clientX: targetCoordinates.left,
                clientY: targetCoordinates.top
            }
        );

        return targetElement.dispatchEvent(mouseUpEvent);
    },

    createEvent: function (eventName, options) {
        var event = document.createEvent("CustomEvent");
        event.initCustomEvent(eventName, true, true, null);

        event.view = window;
        event.detail = 0;
        event.ctlrKey = false;
        event.altKey = false;
        event.shiftKey = false;
        event.metaKey = false;
        event.button = 0;
        event.relatedTarget = null;

        if (options.clientX && options.clientY) {
            event.screenX = window.screenX + options.clientX;
            event.screenY = window.screenY + options.clientY;
        }

        for (var prop in options) {
            event[prop] = options[prop];
        }

        return event;
    }
};