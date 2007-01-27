function getTimeFilterValue(outputId) {
  for(var i = 1; i < 25; i++) {
  var resultvalue;
  if(document.getElementById("hour"+i).checked)
  {
  alert(i+"value checked");
  }
                   
     }
 
} 


 function report(value){
  document.getElementById("reportId").value=value;
}

function uiOptionTransfer_fillStates(obj) {
   var src = document.getElementById(obj.sourceId).options;
   var tgt = document.getElementById(obj.targetId).options;  
     for (var i = 0; i < src.length; ++i) { 
    for (var k = 0; k < tgt.length; ++k) { 
    if(src[i].value==tgt[k].value) {
    src[i].disabled=true;
    break;
    }
  }
}
}
                   
function transform_patterns(obj,templateEnable) { 
 var src = document.getElementById(obj.targetId).options;
  var obj_result="";
  for(var i = 0; i < src.length; ++i) {
    obj_result=":"+obj_result+src[i].value+":";
  } 
  document.getElementById("selectedElementsValueId").value=obj_result;

    if (templateEnable) {
      document.getElementById("newTemplateName").value=document.getElementById("oldTemplateName").value;
  }
 
}    

function transform_pattern(obj) { 
 var src = document.getElementById(obj.targetId).options;
  var obj_result="";
  for(var i = 0; i < src.length; ++i) {
    obj_result=obj_result+":"+src[i].value+":";
  } 
  document.getElementById("selectedCategorysIdNew").value=obj_result;
  document.getElementById("nameIdNew").value=document.getElementById("nameId").value
  document.getElementById("recencyIdNew").value=document.getElementById("recencyId").value
  document.getElementById("frequencyDaysIdNew").value=document.getElementById("frequencyDaysId").value
  document.getElementById("frequencyNumbersIdNew").value=document.getElementById("frequencyNumbersId").value
  document.getElementById("durationIdNew").value=document.getElementById("durationId").value
  document.getElementById("keywordsIdNew").value=document.getElementById("keyWordsId").value
  
 
}  


function undisableComboBoxes(theForm){
    theForm.placeTypeId.disabled=false;
    theForm.targetWindowId.disabled=false;

}


function targetSelect(theForm, ind)
{

	
}
function placeSelect(theForm, ind)
{

	
}
function loadingSelect(theForm, ind)
{
	if(document.placesEditActionForm.loadingTypeId.selectedIndex > ind)
	{
		theForm.targetWindowId.selectedIndex=ind+1;
		theForm.targetWindowId.disabled='disabled';
	}
	else
	{
		theForm.targetWindowId.disabled=undefined;
	}
	
}
