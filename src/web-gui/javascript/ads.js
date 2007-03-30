// INITALIZE ALL THE SLIDERS TO BE SHOWN

function initSlider()
{
    mySlider_relevate = new Bs_Slider();
    mySlider_relevate.width = 121;
    mySlider_relevate.height = 19;
    mySlider_relevate.minVal = 0;
    mySlider_relevate.maxVal = 100;
    mySlider_relevate.valueDefault = 50;
    mySlider_relevate.valueInterval = 1;
    mySlider_relevate.imgDir = 'images/';
    mySlider_relevate.setBackgroundImage('slider_bg.gif', 'no-repeat');
    mySlider_relevate.setSliderIcon('slider.gif', 13, 22);
    mySlider_relevate.useInputField = 1;
    mySlider_relevate.colorbar = new Object();
    mySlider_relevate.colorbar['color'] = '#0194CB';
    mySlider_relevate.colorbar['height'] = 5;
    mySlider_relevate.colorbar['widthDifference'] = 0;
    mySlider_relevate.colorbar['offsetLeft'] = 4;
    mySlider_relevate.colorbar['offsetTop'] = 1;
    mySlider_relevate.styleValueTextClass = 'txtFacet';
    mySlider_relevate.showValue = false;
    mySlider_relevate.draw('slider');
}

function initPriorities() {
    var catDuos = document.getElementById("categoriesPriorities").value.split(":");
    for (var i = 0; i < catDuos.length; i++) {
        var catId = catDuos[i].split("-")[0];
        var catPriority = catDuos[i].split("-")[1];
        if (catPriority != 'undefined') {
            sliderValuesArray[catId] = catPriority;
        }
    }
}

function selectionChanged() {
    var control = document.getElementById("uiOptionTransfer_target1");

    var opts = control.options;
    var selectedCount = 0;
    var selectedOption;

    for (var i = 0; i < opts.length; i++) {
        //alert(opts[i].value);
        if (opts[i].selected) {
            ++selectedCount;
            selectedOption = opts[i];
        }
        if (selectedCount > 1) {
            break;
        }
    }

    if (selectedCount != 1) {
        //hide

        mySlider_relevate.setDisabled(true);

        if (selectedOption != undefined) {
            //alert(selectedOption);
            if (sliderValuesArray[selectedOption.value] != 'undefined') {
                //alert(sliderValuesArray[selectedIndex]);
                sliderValuesArray[currentlySelectedCategoryIndex] = mySlider_relevate.getValue();
                //alert(selectedIndex + ':' +sliderValuesArray[selectedIndex]);
            }
        }

    } else {

        sliderValuesArray[currentlySelectedCategoryIndex] = mySlider_relevate.getValue();
        currentlySelectedCategoryIndex = selectedOption.value;
        mySlider_relevate.setDisabled(false);
        if (sliderValuesArray[selectedOption.value] != 'undefined') {
            mySlider_relevate.setValue(sliderValuesArray[selectedOption.value]);
        }
    }


}