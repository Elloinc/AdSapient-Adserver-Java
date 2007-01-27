////////////////////////////
////////// common //////////
////////////////////////////

////
// Classes and Objects

// Disabled widget class
function uiCommon_Disabled(widget, color) {
    this.widget = widget;
    this.color = color;
}

// Wrapper class for 'common' global variables
function uiCommon_Globals() {
    this.disabledWidgets = new Array();

    // Browser detection.
    this.isOpera = window.opera ? true : false;
    this.isIe = document.all && !this.isOpera;
}

var uiCommon_globals = new uiCommon_Globals();

////
// Functions

// in Mozilla 1.7, cloneNode() does not copy 'defaultSelected' 
// and 'selected' attributes
function uiCommon_cloneSelectOption(opt) {
    return new Option(opt.text, opt.value,
            opt.defaultSelected, opt.selected);
}

// Returns the location (index) of opt in list. If not found, -1 is returned.
function uiCommon_findSelectOption(list, opt) {
    for (var i = 0; i < list.length; ++i) {
        if (list[i].value == opt.value &&
            list[i].text == opt.text) {
            return i;
        }
    }
    return -1;
}
function transform_data(obj) {
    transform_data(obj, false);
}
// concat all options from target menu 
//and add result to hidden input field
function transform_data(obj, templateEnable) {
    var src = document.getElementById(obj.targetId).options;
    var obj_result = "";
    for (var i = 0; i < src.length; ++i) {
        obj_result = obj_result + src[i].value + ",";
    }
    document.getElementById("selectedCountrysId").value = obj_result;

     //(vs) - uncomment the following to transfer cities selections required for targeting by cities
    /**
    var cities = document.getElementById("uiOptionTransfer_target2").options;
    var cities_result = "";
    for (var i = 0; i < cities.length; ++i) {
        cities_result = cities_result + cities[i].value + ",";
    }
    document.getElementById("selectedCitiesId").value = cities_result;
       **/

    // copy template name value (if enable)
    if (templateEnable) {
        document.getElementById("newTemplateName").value = document.getElementById("oldTemplateName").value;
    }

}
// transfor data for categorys collection
function transform_categorys(obj, templateEnable) {
    var src = document.getElementById(obj.targetId).options;
    var obj_result = "";
    for (var i = 0; i < src.length; ++i) {
        var catId = src[i].value;
        var catPriority = 0;
        if (sliderValuesArray[catId] != undefined) {
            catPriority = sliderValuesArray[catId];
        }
        obj_result = obj_result + ":" + catId + "-" + catPriority + ":";
    }


    var src2 = document.getElementById("uiOptionTransfer_target2").options;
    var obj_result2 = "";
    for (var i = 0; i < src2.length; ++i) {
        obj_result2 = obj_result2 + ":" + src2[i].value + ":";
    }

    var src3 = document.getElementById("uiOptionTransfer_target3").options;
    var obj_result3 = "";
    for (var i = 0; i < src3.length; ++i) {
        obj_result3 = obj_result3 + ":" + src3[i].value + ":";
    }


    document.getElementById("selectedCategorysId").value = obj_result;
    document.getElementById("selectedPositionsId").value = obj_result2;
    document.getElementById("selectedPlacesId").value = obj_result3;


    // copy template name value (if enable)
    if (templateEnable) {

        document.getElementById("newTemplateName").value = document.getElementById("oldTemplateName").value;
    }

}

//todo: refactor this strange 'copy-and-paste' routine
function transform_site_categorys(obj, templateEnable) {
    var src = document.getElementById(obj.targetId).options;
    var obj_result = "";
    for (var i = 0; i < src.length; ++i) {
        var catId = src[i].value;
        var catPriority = 0;
        if (sliderValuesArray[catId] != undefined) {
            catPriority = sliderValuesArray[catId];
        }
        obj_result = obj_result + ":" + catId + "-" + catPriority + ":";
    }
    document.getElementById("selectedCategorysId").value = obj_result;

    document.getElementById("descriptionId").value = document.getElementById("descriptionField").value;
    document.getElementById("urlId").value = document.getElementById("urlField").value;
    document.getElementById("type").value = document.getElementById("typeField").value;

    //alert(document.getElementById("urlId").value);

}

function transform_place_categorys(obj, templateEnable) {
    var src = document.getElementById(obj.targetId).options;
    var obj_result = "";
    for (var i = 0; i < src.length; ++i) {
        var catId = src[i].value;
        var catPriority = 0;
        if (sliderValuesArray[catId] != undefined) {
            catPriority = sliderValuesArray[catId];
        }
        obj_result = obj_result + ":" + catId + "-" + catPriority + ":";
    }
    document.getElementById("selectedCategorysId").value = obj_result;
    document.getElementById("type").value = document.getElementById("typeField").value;
    /*
    var src = document.getElementById(obj.targetId).options;
    var obj_result = "";
    for (var i = 0; i < src.length; ++i) {
        obj_result = obj_result + ":" + src[i].value + ":";
    }
    document.getElementById("selectedCategorysNameId").value = obj_result;
    */

}


function transform_any_categorys(obj)
{
    for (i = 0; i < obj.length; i++)
    {
        var src = document.getElementById(obj[i].targetId).options;
        var obj_result = "";
        for (var j = 0; j < src.length; ++j) {
            obj_result += src[j].value;
            if (j != src.length - 1)
                obj_result += ";";
        }

        document.getElementById("selected_" + obj[i].targetId).value = obj_result;
    }
}


// Adds the supplied opt to the list of Option objects (it's up to the
// function invoker to make sure that it won't end up with duplicated
// option.
function uiCommon_addSelectOption(list, opt, selected) {
    // This trick is needed to make IE work
    var i = list.length++;
    list[i].text = opt.text;
    list[i].value = opt.value;
    list[i].selected = selected;
}

// Adds to the list of Option objects only if the supplied opt is not
// already there
function uiCommon_addUniqueSelectOption(list, opt, selected) {
    if (uiCommon_findSelectOption(list, opt) == -1) {
        // This trick is needed to make IE work
        var i = list.length++;
        list[i].text = opt.text;
        list[i].value = opt.value;
        list[i].selected = selected;
    }
}

// Removes the supplied opt from the list of Option objects
function uiCommon_removeSelectOption(list, opt) {
    var i = uiCommon_findSelectOption(list, opt);
    list[i] = null;
}

// Returns 1 if first is greater than second, 0 if both are equals, -1 otherwise
function uiCommon_compareSelectOptionTexts(first, second) {
    if (first.text == second.text) {
        return 0;
    }
    return (first.text > second.text) ? 1 : -1;
}

function uiCommon_sortSelectOptions(opts, compare, reverse) {
    var tempArr = new Array(opts.length);
    for (var i = 0; i < opts.length; ++i) {
        tempArr[i] = uiCommon_cloneSelectOption(opts[i]);
    }
    tempArr.sort(compare);
    if (reverse) {
        tempArr.reverse(compare);
    }
    for (var i = 0; i < opts.length; ++i) {
        opts[i] = tempArr[i];
    }
}

// Removes all Option objects in opts
function uiCommon_clearSelectOptions(opts) {
    while (opts.length > 0) {
        opts[0] = null;
    }
}

// Removes selected Option objects in opts
function uiCommon_removeSelectedOptions(opts) {
    for (var i = opts.length - 1; i >= 0; --i) {
        if (opts[i].selected) {
            opts[i] = null;
        }
    }
}

// Selects (highlights) all Option objects in opts
function uiCommon_selectAllOptions(opts) {
    for (var i = 0; i < opts.length; ++i) {
        opts[i].selected = true;
    }
}

// Inverts selection in opts
function uiCommon_invertSelectOptions(opts) {
    for (var i = 0; i < opts.length; ++i) {
        if (opts[i].selected) {
            opts[i].selected = false;
        }
        else {
            opts[i].selected = true;
        }
    }
}

// Prepends an event handler to a form's 'submit' handler list
function uiCommon_prependSubmitHandler(form, handler) {
    var old = form.onsubmit;
    if (old) {
        form.onsubmit = function(e) {
            handler();
            return old();
        }
    }
    else {
        form.onsubmit = handler;
    }
}

// Appends an event handler to a form's 'submit' handler list
function uiCommon_appendSubmitHandler(form, handler) {
    var old = form.onsubmit;
    if (old) {
        form.onsubmit = function(e) {
            var retVal = old();
            handler();
            return retVal;
            // Return old handler's return value
        }
    }
    else {
        form.onsubmit = handler;
    }
}

// Appends an event handler to an element's 'click' handler list
function uiCommon_appendClickHandler(element, handler) {
    if (element.onclick) {
        var old = element.onclick;
        element.onclick = function(e) {
            var retVal = old();
            handler();
            return retVal;
            // Return old handler's return value
        }
    }
    else {
        element.onclick = handler;
    }
}

// Appends an event handler to an element's 'change' handler list
function uiCommon_appendChangeHandler(element, handler) {
    if (element.onchange) {
        var old = element.onchange;
        element.onchange = function(e) {
            var retVal = old();
            handler();
            return retVal;
            // Return old handler's return value
        }
    }
    else {
        element.onchange = handler;
    }
}

// Appends an event handler to an element's 'mousedown' handler list
function uiCommon_appendMouseDownHandler(element, handler) {
    if (element.onmousedown) {
        var old = element.onmousedown;
        element.onmousedown = function(e) {
            var retVal = old();
            handler();
            return retVal;
            // Return old handler's return value
        }
    }
    else {
        element.onmousedown = handler;
    }
}

// Appends an event handler to an element's 'load' handler list
function uiCommon_appendLoadHandler(element, handler) {
    if (element.onload) {
        var old = element.onload;
        element.onload = function() {
            var retVal = old();
            handler();
            return retVal;
            // Return old handler's return value
        }
    }
    else {
        element.onload = handler;
    }
}

// Generic function for appending an event handler to any type of widgets
function uiCommon_appendWidgetHandler(obj, hnd) {
    var prevHandler;

    // it's not possible to have multiple select boxes with the same name
    // so we assume that 'obj' would never be an array of 'select'
    if (uiCommon_getWidgetType(obj) == 'select') {
        uiCommon_appendChangeHandler(obj, hnd);
        return;
    }

    if (!uiCommon_isArray(obj)) {
        obj = new Array(obj);
    }

    for (var i = 0; i < uiCommon_countObjects(obj); i++) {
        uiCommon_appendClickHandler(obj[i], hnd);
    }
}

// Init sticking an element to a fixed position. IE has its own behavior.
var uiCommon_fixedElement, uiCommon_fixedElementWidth, uiCommon_fixedElementHeight;
function uiCommon_initFixing(id, delay) {
    uiCommon_fixedElement = document.getElementById(id);

    // IE needs a JS script solution rather than CSS because the latter makes
    // all absolutely-positioned objects stick to the viewport.
    if (uiCommon_globals.isIe) {
        uiCommon_fixedElement.style.position = 'absolute';
        uiCommon_fixedElement.style.zIndex = 10;
        uiCommon_fixedElementWidth = uiCommon_fixedElement.offsetWidth;
        uiCommon_fixedElementHeight = uiCommon_fixedElement.offsetHeight;

        uiCommon_fixPosition();
        window.onscroll = uiCommon_fixPosition;
    }
    else {
        uiCommon_fixedElement.style.position = 'fixed';
        uiCommon_fixedElement.style.top = '0px';
        uiCommon_fixedElement.style.right = '0px';
        uiCommon_fixedElement.style.zIndex = 10;
    }
}

// Repeatedly called (perhaps by window.setInterval) to fix an element's position
function uiCommon_fixPosition() {
    if (uiCommon_globals.isIe) {
        uiCommon_fixedElement.style.pixelLeft =
        document.body.scrollLeft + document.body.clientWidth - uiCommon_fixedElementWidth;
        uiCommon_fixedElement.style.pixelTop =
        document.body.scrollTop;

        // document.body properties are moved to document.documentElement
        // when DOCTYPE 4.01 transitional/strict or 4.0 strict is present.
        if (uiCommon_fixedElement.style.pixelTop == 0) {
            uiCommon_fixedElement.style.pixelTop = document.documentElement.scrollTop;
        }
    }
}

// Checks if a radio button/checkbox/select option is selected.
function uiCommon_isToggleOn(obj) {
    switch (uiCommon_getWidgetType(obj)) {
        case 'radio'   :
        case 'checkbox': return obj.checked;
        case 'option'  : return obj.selected;
        default        : return false;
    }
}

// Returns a string representing the type of the widget.
function uiCommon_getWidgetType(obj) {
    var tag = obj.tagName;
    if (tag == null) {
        return 'undefined';
    }
    tag = tag.toLowerCase();
    switch (tag) {
        case 'select'  :
        case 'option'  :
        case 'textarea': return tag;
        case 'input'   : // submit, button, text, radio, checkbox
            return obj.type.toLowerCase();
        default        : return 'undefined';
    }
}

// Checks if a radio button/checkbox/select option that is part of a larger
// collection is selected.
function uiCommon_isParticularToggleOn(obj, val) {
    if (uiCommon_isArray(obj)) {
        for (var i = 0; i < obj.length; ++i) {
            if (obj[i].value == val) {
                return uiCommon_isToggleOn(obj[i]);
            }
        }
    }
    else {    // Single-sized toggle, ignore supplied value
        return uiCommon_isToggleOn(obj);
    }
}

// Disables supplied obj (can be a multi- or single-sized widget).
function uiCommon_disableWidgets(obj) {
    if (uiCommon_isArray(obj)) {
        for (var i = 0; i < obj.length; ++i) {
            uiCommon_disableSingleWidget(obj[i]);
        }
    }
    else {
        uiCommon_disableSingleWidget(obj);
    }
}

function uiCommon_disableSingleWidget(widget) {
    widget.disabled = true;

    if (uiCommon_globals.isIe) {
        var arr = uiCommon_globals.disabledWidgets;
        arr.push(new uiCommon_Disabled(widget, widget.style.backgroundColor));
        widget.style.backgroundColor = '#cccccc';
    }
}

// Enables supplied obj (can be a multi- or single-sized widget).
function uiCommon_enableWidgets(obj) {
    if (uiCommon_isArray(obj)) {
        for (var i = 0; i < obj.length; i++) {
            uiCommon_enableSingleWidget(obj[i]);
        }
    }
    else {
        uiCommon_enableSingleWidget(obj);
    }
}

function uiCommon_enableSingleWidget(widget) {
    widget.disabled = false;

    if (uiCommon_globals.isIe) {
        var arr = uiCommon_globals.disabledWidgets;
        // search for the particular widget
        for (i = 0; i < arr.length; i++) {
            if (arr[i].widget == widget) {
                widget.style.backgroundColor = arr[i].color;
                uiCommon_removeArrayElement(arr, i);
                break;
            }
        }
    }
}

// Returns the number of elements in the supplied object.
function uiCommon_countObjects(obj) {
    if (!obj) {
        return 0;
    }
    else if (uiCommon_isArray(obj)) {
        return obj.length;
    }
    return 1;
}

// Checks whether the supplied object is an array.
function uiCommon_isArray(obj) {
    return obj != null && obj[0] != null;
}

// Removes element at 'index' from array
function uiCommon_removeArrayElement(arr, index) {
    // remove one element starts from 'index'
    arr.splice(index, 1);
}


//////////////////////////////
////////// fastTick //////////
//////////////////////////////


// Wrapper class for 'fasttick' global variables
function uiFastTick_Globals() {
    // Remembers whether we toggled checkboxes on/off when the "all" button was
    // last clicked.
    this.doToggleAll = false;
}

var uiFastTick_globals = new uiFastTick_Globals();

// Toggles selection of supplied checkboxes on and off alternately.
function uiFastTick_toggleAll(checkboxes) {
    uiFastTick_globals.doToggleAll = !uiFastTick_globals.doToggleAll;
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked != uiFastTick_globals.doToggleAll) {
            // Simulate clicking so events can be triggered
            checkboxes[i].click();
        }
    }
}

// Inverts the value of the checkboxes' "checked" property.
function uiFastTick_invertSelection(checkboxes) {
    for (var i = 0; i < checkboxes.length; i++) {
        // Simulate clicking so events can be triggered
        checkboxes[i].click();
    }
}

// Checks checkboxes that are between a pair of checked ones.
function uiFastTick_selectRange(checkboxes) {
    var nowTicking = false;

    for (var i = 0; i < checkboxes.length; i++) {

        // Do stuff at the end of a series of ticked checkboxes
        if (checkboxes[i].checked &&
            (i + 1 < checkboxes.length) && !checkboxes[i + 1].checked) {
            nowTicking = !nowTicking;

            // Find the match for current pair
            var endPairIndex = -1;
            for (var j = i + 2; j < checkboxes.length; j++) {
                if (checkboxes[j].checked) {
                    endPairIndex = j;
                    break;
                }
            }

            // If current pair is unmatched, return
            if (endPairIndex == -1) {
                return;
            }

            if (nowTicking) {
                for (var j = i + 1; j < endPairIndex; j++) {
                    checkboxes[j].click();
                }
            }

            i = endPairIndex - 1;
        }
    }
}


////////////////////////////
////////// search //////////
////////////////////////////

// Wrapper class for 'search' global variables
function uiSearch_Globals() {
    // Opens the search window and saves the reference to the opened window
    this.searchWindow = new Array();

    // Global list of Option objects containing the search results. It's a bit
    // weird to represent these as Options, but it makes the code somewhat
    // easier to write and understand.
    this.results = new Array();

    // Keeps track of the length of the above array. This is needed as Mozilla
    // sometimes doesn't get the length of associative array right.
    this.resultsCount = 0;
}

var uiSearch_globals = new uiSearch_Globals();

function uiSearch_openWindow(url, windowOptions, isQuick, instanceId) {
    document.getElementById('uiSearch_isQuickHidden' + instanceId).value = isQuick;
    var searchWindow = window.open(url, 'uiSearch_searchWindow' + instanceId, windowOptions);
    searchWindow.focus();

    // Assign reference
    uiSearch_globals.searchWindow[instanceId] = searchWindow;
}

// Clears selection in the requesting select box, and closes the search window (if opened).
// Tried clearing the search window's select boxes instead of closing the window, but
// didn't work.
function uiSearch_clearSelection(selectBox, searchWindow) {
    uiCommon_removeSelectedOptions(selectBox.options);
    if (searchWindow != null) {
        searchWindow.close();
    }
}


// Returns the value by analyzing the window name
function uiSearch_getInstanceIdValue() {
    // The number that follows the string "uiSearch_searchWindow".
    return window.name.substring(21);
}

// Returns the "uiSearch_isQuickHidden" hidden element in the parent window
function uiSearch_getIsQuickHidden() {
    var pdoc = window.opener.document;
    return pdoc.getElementById('uiSearch_isQuickHidden' + uiSearch_getInstanceIdValue());
}

// Does the job of a "quick search": if we have search results, populate
// the requesting select box, and close this search window.
//
// @param onFail the error message to display when there are more than one
//               search result yet the requesting select box only takes one value
function uiSearch_performQuickSearchDuty(onFail) {
    // If we have displayed the search results, and this is a quick search,
    // we have to transfer all search results to the parent window, then
    // close this search window.
    var isQuick = uiSearch_getIsQuickHidden().value;

    if (uiSearch_globals.resultsCount > 0 && isQuick == 'true') {
        var pselectBox = uiSearch_requestingSelectBox;
        // If the requesting select box is not big enough to hold all returned
        // results, error.
        if (pselectBox.size <= 1 && uiSearch_globals.resultsCount > 1) {
            alert(onFail);
        }
        // Otherwise, populate the requesting select box
        else {
            // Single-sized select box (size value may be -1 if unspecified),
            // clear previous value
            if (pselectBox.size <= 1) {
                var opts = uiSearch_requestingSelectBox.options;
                uiCommon_removeSelectOption(opts, opts[0]);
            }

            for (var val in uiSearch_globals.results) {
                uiCommon_addUniqueSelectOption(pselectBox.options, uiSearch_globals.results[val], true);
            }
            uiSearch_closeOwnWindow();
            return;
        }
    }

    // Focus the window, so the user can view the search results (if any).
    // This is especially important if we're performing a quick search and
    // we come back empty-handed with no seach result. This window gaining
    // focus is how we inform the user of this fact.
    document.write('<script language="javascript">window.focus();</script>');
}

// Closes this search window
function uiSearch_closeOwnWindow() {
    document.getElementById('uiSearch_searchWindowCloser').click();
}

// Sets the initial value of the "uiSearch_isQuickCheckbox" checkbox in the
// search window, mirroring the value of "uiSearch_isQuickHidden"
function uiSearch_initQuickCheckbox() {
    var isQuick = uiSearch_getIsQuickHidden().value;
    document.getElementById('uiSearch_isQuickCheckbox').checked = (isQuick == 'true');
}

// Returns the requesting select box in the parent window (the select box to be
// populated by the search feature). You should call this method once and
// cache the return value. It's expensive to keep doing this repeatedly.
function uiSearch_getRequestingSelectBox() {
    // Parent document
    var pdoc = window.opener.document;
    // Requesting form in parent window
    var pformName = pdoc.getElementById('uiSearch_requestingForm' + uiSearch_getInstanceIdValue()).value;
    var pform = pdoc.forms[pformName];
    // Requesting select box in parent window
    var pselectBoxName = pdoc.getElementById('uiSearch_requestingSelectBox' + uiSearch_getInstanceIdValue()).value;
    return pform[pselectBoxName];
}

// Encapsulates the action of adding an element into the "uiSearch_globals.results"
// array and incrementing its counter. Always use this when adding to the
// global array. See the comment above.
function uiSearch_addToResultsArray(val, opt) {
    uiSearch_globals.results[val] = opt;
    uiSearch_globals.resultsCount++;
}

// Creates and renders to the document one widget for one search result
// (the search result is represented by the supplied val and lbl). If the requesting
// select box is a multi select box, a checkbox is rendered; otherwise a
// radio button.
function uiSearch_createResultWidget(val, lbl, cls) {
    // Create an Option object wrapping value and label
    var opt = new Option(lbl, val);

    // Add the value and label to global array
    uiSearch_addToResultsArray(val, opt);

    var pselectBox = uiSearch_getRequestingSelectBox();

    // Multi select box. The "size" property is used rather than "multiple" as
    // it accurately tells us whether we can add more than one option to the
    // select box.
    if (pselectBox.size > 1) {
        document.write('<input type="checkbox" onClick="uiSearch_setRequestingMultiSelectBoxValue(this.value, this.checked);"');
    }
    // Single-sized select box (size value may be -1 if unspecified)
    else {
        document.write('<input type="radio" onClick="uiSearch_setRequestingSinglySelectBoxValue(this.value);"');
    }

    // Check the radio/checkbox if its value is already sitting in the requesting select box
    if (uiCommon_findSelectOption(pselectBox.options, opt) >= 0) {
        document.write(' checked="checked"');
    }

    // If the CSS class is specified, include it
    if (cls != '') {
        document.write(' class="' + cls + '"');
    }
    document.write(' name="uiSearch_selectedResults" id="' + val + '" value="' + val + '" />');
}

// Adds/removes the value from the requesting select box, depending on whether ticked is true/false.
function uiSearch_setRequestingMultiSelectBoxValue(val, ticked) {
    var opts = uiSearch_requestingSelectBox.options;
    var optToSet = uiSearch_globals.results[val];

    // If the checkbox is ticked, add the value to the requesting select box
    if (ticked) {
        uiCommon_addSelectOption(opts, optToSet, true);
    }
    // If the checkbox is not ticked, remove the value from the requesting select box
    else {
        uiCommon_removeSelectOption(opts, optToSet);
    }
}

// Replaces whatever value that is already in the requesting select box with the
// value.
function uiSearch_setRequestingSinglySelectBoxValue(val) {
    var opts = uiSearch_requestingSelectBox.options;
    if (opts.length > 0) {
        uiCommon_removeSelectOption(opts, opts[0]);
    }

    uiCommon_addSelectOption(opts, uiSearch_globals.results[val], true);
}


////////////////////////////////////
////////// optionTransfer //////////
////////////////////////////////////

// Category class containing items in the category
function uiOptionTransfer_Category(text, value, items) {
    this.text = text;
    this.value = value;
    this.items = items;
}

// Wrapper class for 'optionTransfer' global variables
function uiOptionTransfer_Globals(cat, catId, srcId, tgtId, distId) {
    this.categories = cat;
    this.categoryId = catId;
    this.sourceId = srcId;
    this.targetId = tgtId;
    this.shiftDistanceId = distId;
    this.optionColor = "black";
}

// Populates category select box
function uiOptionTransfer_fillCat(obj) {
    var catArr = obj.categories;
    var group = document.getElementById(obj.categoryId);
    for (var i = 0; i < catArr.length; ++i) {
        group.options.add(new Option(catArr[i].text, catArr[i].value));
    }
}

// Populates source select box containing items in the selected category
function uiOptionTransfer_fillSrc(obj) {
    var catArr = obj.categories;

    var src = document.getElementById(obj.sourceId);
    uiCommon_clearSelectOptions(src.options);
    var items;

    var group = document.getElementById(obj.categoryId);
    var selected = group.options[group.selectedIndex];
    for (var i = 0; i < catArr.length; ++i) {
        if (catArr[i].value == selected.value) {
            items = catArr[i].items;
            break;
        }
    }
    for (var i = 0; i < items.length; ++i) {
        src.options.add(items[i]);
    }
}

// Attaches form submit event handler
function uiOptionTransfer_onSubmit(obj) {
    var tgt = document.getElementById(obj.targetId);
    var form = tgt.form;

    if (form != null) {
        uiCommon_prependSubmitHandler(form, function(e) {
            uiCommon_selectAllOptions(tgt.options);
        });
    }
}

// Produces the javascript code for transferring items from source to
// target select box
function uiOptionTransfer_transfer(obj, all) {
    var src = document.getElementById(obj.sourceId).options;
    //alert(src);
    var obj;
    for (var i = 0; i < src.length; ++i) {
        // with IE, it is still possible to transfer disabled option,
        // so we have to make sure to only transfer a non-disabled ones
        if ((all && !src[i].disabled) || (src[i].selected && !src[i].disabled)) {
            uiOptionTransfer_putTgtItem(obj, src[i]);
        }
    }
}

// Produces the javascript code for transferring all items from source
// to target select box
function uiOptionTransfer_transferAll(obj) {
    uiOptionTransfer_transfer(obj, true);
}

// Puts an item into the target select box
function uiOptionTransfer_putTgtItem(obj, item) {
    clone = uiCommon_cloneSelectOption(item);
    item.disabled = true;
    item.selected = false;
    // store the previous color first
    obj.optionColor = item.style.color;
    // IE does not disable the option, so we simulate it by
    // changing the color
    item.style.color = 'gray';

    var tgt = document.getElementById(obj.targetId).options;
    tgt.add(clone);
}

// Produces the javascript code for removing items from target select box
function uiOptionTransfer_return(obj, all) {
    var catArr = obj.categories;
    var src = document.getElementById(obj.sourceId).options;
    var tgt = document.getElementById(obj.targetId).options;
    var items;
    var index;
    for (var i = tgt.length - 1; i >= 0; --i) {
        if (all || tgt[i].selected) {
            if (catArr != null) {
                for (var j = 0; j < catArr.length; ++j) {
                    items = catArr[j].items;
                    index = uiCommon_findSelectOption(items, tgt[i]);

                    // some target items don't exist in the source item collection
                    if (index >= 0) {
                        items[index].disabled = false;
                        items[index].selected = true;
                        // because we change the option's color earlier, we need to
                        // to reset it
                        items[index].style.color = obj.optionColor;
                        tgt[i] = null;
                        break;
                    }
                }
            }
            else {
                index = uiCommon_findSelectOption(src, tgt[i]);
                // some target items don't exist in the source item collection
                if (index >= 0) {
                    src[index].disabled = false;
                    src[index].selected = true;
                    // because we change the option's color earlier, we need to
                    // to reset it
                    src[index].style.color = obj.optionColor;
                    tgt[i] = null;
                }
            }
        }
    }
}

// Produces the javascript code for removing all items from target select box
function uiOptionTransfer_returnAll(obj) {
    uiOptionTransfer_return(obj, true);
}

function uiOptionTransfer_selectAll(obj) {
    uiCommon_selectAllOptions(document.getElementById(obj.targetId).options);
}

function uiOptionTransfer_selectInvert(obj) {
    uiCommon_invertSelectOptions(document.getElementById(obj.targetId).options);
}

function uiOptionTransfer_shiftUp(obj) {
    var num;
    var tgt = document.getElementById(obj.targetId).options;
    var dist = document.getElementById(obj.shiftDistanceId);
    num = (dist == null) ? 1 : parseInt(dist.value);
    if (isNaN(num)) {
        return;
    }
    var obj1, obj2;
    for (var i = 0; i < tgt.length; ++i) {
        if (tgt[i].selected) {
            for (var j = 0; j < num; ++j) {
                if (i - j <= 0) {
                    num = j;
                    break;
                }
                obj1 = uiCommon_cloneSelectOption(tgt[i - j]);
                obj2 = tgt[i - j - 1];
                tgt[i - j - 1] = obj1;
                tgt[i - j] = obj2;
            }
        }
    }
}

function uiOptionTransfer_shiftDown(obj) {
    var num;
    var tgt = document.getElementById(obj.targetId).options;
    var dist = document.getElementById(obj.shiftDistanceId);
    num = (dist == null) ? 1 : parseInt(dist.value);
    if (isNaN(num)) {
        return;
    }
    var obj1, obj2;
    for (var i = tgt.length - 1; i >= 0; --i) {
        if (tgt[i].selected) {
            for (var j = 0; j < num; ++j) {
                if (i + j >= tgt.length - 1) {
                    num = j;
                    break;
                }
                obj1 = uiCommon_cloneSelectOption(tgt[i + j]);
                obj2 = tgt[i + j + 1];
                tgt[i + j + 1] = obj1;
                tgt[i + j] = obj2;
            }
        }
    }
}

function uiOptionTransfer_shiftFirst(obj) {
    var num;
    var tgt = document.getElementById(obj.targetId).options;
    num = tgt.length - 1
    var obj1, obj2;
    var reserved = 0;
    for (var i = 0; i < tgt.length; ++i) {
        if (tgt[i].selected) {
            for (var j = 0; j < num; ++j) {
                if (i - j <= reserved) {
                    ++reserved;
                    break;
                }
                obj1 = uiCommon_cloneSelectOption(tgt[i - j]);
                obj2 = tgt[i - j - 1];
                tgt[i - j - 1] = obj1;
                tgt[i - j] = obj2;
            }
        }
    }
    document.getElementById(obj.targetId).scrollTop = 0;
}

function uiOptionTransfer_shiftLast(obj) {
    var num;
    var tgt = document.getElementById(obj.targetId).options;
    num = tgt.length - 1
    var obj1, obj2;
    var reserved = tgt.length - 1;
    for (var i = tgt.length - 1; i >= 0; --i) {
        if (tgt[i].selected) {
            for (var j = 0; j < num; ++j) {
                if (i + j >= reserved) {
                    --reserved;
                    break;
                }
                obj1 = uiCommon_cloneSelectOption(tgt[i + j]);
                obj2 = tgt[i + j + 1];
                tgt[i + j + 1] = obj1;
                tgt[i + j] = obj2;
            }
        }
    }
    var box = document.getElementById(obj.targetId);
    box.scrollTop = box.scrollHeight;
}

function uiOptionTransfer_sortAsc(obj) {
    var tgt = document.getElementById(obj.targetId).options;
    uiCommon_sortSelectOptions(tgt, uiCommon_compareSelectOptionTexts, false);
}

function uiOptionTransfer_sortDesc(obj) {
    var tgt = document.getElementById(obj.targetId).options;
    uiCommon_sortSelectOptions(tgt, uiCommon_compareSelectOptionTexts, true);
}


//////////////////////////
////////// info //////////
//////////////////////////

function uiInfo_Toggle(buttonOn, buttonOff) {
    this.__buttonOn = buttonOn;
    this.__buttonOff = buttonOff;
    this.__on = true;
    this.__info = new Array();

    this._addInfo = function(info) {
        this.__info.push(info);
    }

    this._switch = function() {
        if (this.__on) {
            this._switchOff();
        }
        else {
            this._switchOn();
        }
    }

    this._switchOn = function() {
        this.__on = true;
        this.__buttonOff.style.display = 'none';
        this.__buttonOn.style.display = 'inline';

        for (var i = 0; i < this.__info.length; i++) {
            this.__info[i]._showButton();
        }
    }

    this._switchOff = function() {
        this.__on = false;
        this.__buttonOn.style.display = 'none';
        this.__buttonOff.style.display = 'inline';

        for (var i = 0; i < this.__info.length; i++) {
            this.__info[i]._hideButton();
        }
    }

    this._setButtons = function(buttonOn, buttonOff) {
        this.__buttonOn = buttonOn;
        this.__buttonOff = buttonOff;
    }
}

function uiInfo_Sticker(buttonOn, buttonOff) {
    this.__buttonOn = buttonOn;
    this.__buttonOff = buttonOff;

    if (buttonOn != null && buttonOff != null) {
        this._toggleOn = function() {
            this.__on = true;
            this.__buttonOff.style.display = 'none';
            this.__buttonOn.style.display = 'inline';
        }

        this._toggleOff = function() {
            this.__on = false;
            this.__buttonOn.style.display = 'none';
            this.__buttonOff.style.display = 'inline';
        }
    }
    else {
        this._toggleOn = function() {
            this.__on = true;
        }

        this._toggleOff = function() {
            this.__on = false;
        }
    }

    this._isOn = function() {
        return this.__on;
    }

    this._toggleOff();
}

function uiInfo_Info(id, button, panel, stick) {
    this.__id = id;
    this.__button = button;
    this.__panel = panel;
    this.__focus = false;
    this.__stick = stick;

    this.__initPanel = function() {
        var x = this.__button.offsetWidth;
        var y = 0;
        var curr = this.__button;
        while (curr.tagName != "BODY") {
            x += curr.offsetLeft;
            y += curr.offsetTop;
            curr = curr.offsetParent;
        }
        this.__panel.style.left = x + 'px';
        this.__panel.style.top = y + 'px';
        this.__panel.style.overflow = 'auto';
        this._setDepth(0);

        this.__panel.onmouseover = function(e) {
            uiInfo_driver._getInfo(id).__focus = true;
        };
        this.__panel.onmouseout = function() {
            uiInfo_driver._getInfo(id).__focus = false;
        };
    }

    this._hasFocus = function() {
        return this.__focus;
    }

    this._setDepth = function(num) {
        this.__panel.style.zIndex = 10 + num;
    }

    this._incrementDepth = function() {
        this.__panel.style.zIndex += 1;
    }

    this._decrementDepth = function() {
        this.__panel.style.zIndex -= 1;
    }

    this._showButton = function() {
        this.__button.style.visibility = 'visible';
    }

    this._hideButton = function() {
        this.__button.style.visibility = 'hidden';
    }

    this._showPanel = function() {
        this.__panel.style.visibility = 'visible';
    }

    this._hidePanel = function(force) {
        if (force || !this.__stick._isOn()) {
            this.__panel.style.visibility = 'hidden';
            return true;
        }
        return false;
    }

    this._getSticker = function() {
        return this.__stick;
    }

    this.__initPanel();
}

function uiInfo_Driver() {
    this.__toggle = new uiInfo_Toggle();
    this.__info = new Array();
    this.__shown = new Array();

    uiCommon_appendMouseDownHandler(document, function() {
        uiInfo_driver.hideAll();
    });

    this.__getArrayIndex = function(id) {
        return id - 1;
    }

    this._getInfo = function(id) {
        return this.__info[this.__getArrayIndex(id)];
    }

    this.initToggle = function() {
        this.__toggle._setButtons(
                document.getElementById('uiInfo_toggleButtonOn'),
                document.getElementById('uiInfo_toggleButtonOff'));
    }

    this.toggleOn = function() {
        this.__toggle._switchOn();
    }

    this.toggleOff = function() {
        this.__toggle._switchOff();
    }

    this.hideAll = function() {
        this.hideAllExcept(null);
    }

    this.hideAllExcept = function(index) {
        var except;
        if (index != null) {
            except = this._getInfo(index);
        }
        for (var i = 0; i < this.__shown.length; i++) {
            if (this.__shown[i] != except && !this.__shown[i]._hasFocus()) {
                if (this.__shown[i]._hidePanel(false)) {
                    uiCommon_removeArrayElement(this.__shown, i);
                    i--;
                    // reprocess the same index
                }
            }
        }
        // reset the panels' depth
        for (var i = 0; i < this.__shown.length; i++) {
            this.__shown[i]._setDepth(i);
        }
    }

    this.__remove = function(info) {
        var delIndex = -1;
        for (var i = 0; i < this.__shown.length; i++) {
            if (delIndex < 0) {
                if (info == this.__shown[i]) {
                    delIndex = i;
                }
            }
            else {  // the Info object to be deleted has been found
                this.__shown[i]._decrementDepth();
            }
        }
        if (delIndex >= 0) {
            uiCommon_removeArrayElement(this.__shown, delIndex);
        }
    }

    this.hide = function(index) {
        var info = this._getInfo(index);
        info._hidePanel(true);

        this.__remove(info);
    }

    this.show = function(index) {
        var info = this._getInfo(index);
        info._showPanel();

        this.__remove(info);
        this.__shown.push(info);
        info._setDepth(this.__shown.length - 1);
    }

    this.stickOn = function(index) {
        this._getInfo(index)._getSticker()._toggleOn();
    }

    this.stickOff = function(index) {
        this._getInfo(index)._getSticker()._toggleOff();
    }

    this.create = function(alwaysOn) {
        var index = this.__info.length + 1;
        var stick = new uiInfo_Sticker(
                document.getElementById('uiInfo_stickButtonOn' + index),
                document.getElementById('uiInfo_stickButtonOff' + index));
        var info = new uiInfo_Info(index,
                document.getElementById('uiInfo_infoButton' + index),
                document.getElementById('uiInfo_infoContent' + index), stick);
        this.__info.push(info);

        if (!alwaysOn) {  // only for buttons that can be toggled
            this.__toggle._addInfo(info);
        }
    }
}

uiInfo_driver = new uiInfo_Driver();

function helpAction(action)
{

    if (1 == 1) {
        return true;
    }

    //links = "http://adsapient.com/wiki/index.php/Main_Page";
    links = "http://adsapient.com";
    base = "docs/en/adnetwork/";

    /*	if(action=="account.management")
          links="http://adsapient.com/wiki/index.php/Account_Management";
      if(action=="reports")
          links="http://adsapient.com/wiki/index.php?title=Reports&action=edit";
      if(action=="newuser")
          links="http://adsapient.com/wiki/index.php/Miscellaneous";
      if(action=="site.management")
          links="http://adsapient.com/wiki/index.php/Publishing_Management";
      if(action=="usermanagement")
          links="using/admin/usermanagement/index.html";
      if(action=="syssettings")
          links=base+"using/admin/systemsettings/index.html";
      if(action=="login")
          links="http://adsapient.com/wiki/index.php/Login";
      if (action=="miscellaneous")
            links="http://adsapient.com/wiki/index.php/Miscellaneous";
    */
    //	if(link.length==0)
    //		link="docs/en/index.html";
    //	if(window.helpPage != null)
    //	{
    //		helpPage.close();
    //		helpPage = null;
    //	}
    helpPage = window.open("about:blank", "Help", "width=630,height=470,resizable=yes,menubar=no,status=no,toolbar=no,scrollbars=yes");
    helpPage.location.href = links;
    helpPage.focus();

    return true;
}
function show_wait(formobj)
{
    /*
     formobj.submit();

     var text = "<table width=\"100%\" border=\"0\"><tr><td height="10" class="tabledata" colspan="2"><b>Very honored customer, we look for you for the most favorable trips with the different organizers. In few seconds you see the results.</b>";
     text = text + "<table width=\"100%\" height=\"80\"><tr><td align=\"center\" id=\"working_pic\"></td></tr></table></td></tr></table>";

     var img = document.createElement("img");
     img.src = "images/working.gif";
     img.height = 20;
     img.width = 220;
     img.align = "absmiddle";
     img.border = 0;

     document.getElementById("upload_button").innerHTML = text;

     imageobj = img;
     window.setTimeout("document.getElementById('working_pic').appendChild(imageobj);", 500);
     */
}
