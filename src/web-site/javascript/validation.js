////////////////////////////////////////////////////////////////////////////////
//
// JS Auto Form Validator version 1.0.3 (19.03.2007)
// Author: Leumas Naypoka <leumas.a@gmail.com>
// Lisence: GNU GPL
// Site: http://phpbuilder.blogspot.com
//
////////////////////////////////////////////////////////////////////////////////
//
// Usage:
// -----
// //*** copy this line between <head> and </head> tags
// <script type="text/JavaScript" src="form.scripts.js"></script>
//
// //*** copy this lines between before your </form> tag
// <input type="submit" name="button" value="Submit"
//        onClick="return onSubmitCheck(document.forms['form_name']);">
//
////////////////////////////////////////////////////////////////////////////////

var digits="0123456789";
var digits1="0123456789.";
var digits2="0123456789,";
var digits3="0123456789.,";
var textchars="/'\"[]{}()*&^%$#@!~?<>-_+=|\\ \r\t\n.,:;";
var lwr="abcdefghijklmnopqrstuvwxyz";
var upr="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
var diac_lwr="??????????????????";
var diac_upr="??????????????????";
var rtypes="rs";
var vtypes="nifatepylzv";

function makeArray(n){for(var i=1; i<=n;i++){this[i]=0;}return this;};
var dInM=makeArray(12);dInM[1]=31;dInM[2]=29;dInM[3]=31;dInM[4]=30;dInM[5]=31;dInM[6]=30;dInM[7]=31;dInM[8]=31;dInM[9]=30;dInM[10]=31;dInM[11]=30;dInM[12]=31;
var PassLength=6;
var LoginLength=6;

var bgcolor_error = "#ff8822";
var bgcolor_normal_1 = "#ffffff";
var bgcolor_normal_2 = "#fcfaf6";
var MaxInt=13
var MaxString=30;
var MaxAdress=200;
var MaxCP=15;
var whitespace=" \t\n\r";
var decimalPointDelimiter=".";
var phoneNumberDelimiters="()- ";
var validPhoneChars=digits + phoneNumberDelimiters;
var validWorldPhoneChars=digits + phoneNumberDelimiters + "+";
var SSNDelimiters="- ";
var validSSNChars=digits + SSNDelimiters;  // intr-un nr. SSN
var digitsInSocialSecurityNumber=9;
var digitsInPhoneNumber=9;
var digitsInMinPhoneNumber=5;
var ZIPCodeDelimiters="-";
var validZIPCodeChars=digits + ZIPCodeDelimiters;
var digitsInZIPCode1=5;
var digitsInZIPCode2=9;
var creditCardDelimiters=" "
var USStateCodeDelimiter="|";
var DEOK=false;

function isEmpty(s){return((s==null)||(s.length==0))}
function isShorter(str_text, str_length){s_length=(str_length==null) ? "1" : str_length;if(str_text.length < s_length) return true;else return false;}
function isValid(parm,val){if(parm=="")return true;for(i=0;i<parm.length;i++){if(val.indexOf(parm.charAt(i),0)==-1)return false;}return true;}
function isSubmitReqType(parm){return isLower(parm) && isValid(parm,rtypes);}
function isSubmitVarType(parm){return isLower(parm) && isValid(parm,vtypes);}
function isNumeric(parm,type){ptype=(type==null)?"0":type; pdigits=-1;switch(ptype){case 0:pdigits=digits;break;case1:pdigits=digits1;break;case 2:pdigits=digits2;break;case 3:pdigits=digits3;break;default:pdigits=digits;break;}return isValid(parm,pdigits);}
function isLower(parm){return isValid(parm,lwr);}
function isUpper(parm){return isValid(parm,upr);}
function isAlpha(parm){return isValid(parm,lwr + upr);}
function isAlphaNumeric(parm){return isValid(parm,lwr + upr + digits);}
function isText(parm){return isValid(parm,lwr + upr + digits3 + textchars + diac_lwr + diac_upr);}
function isAny(parm){return true;}
function isWhitespace(s){i=0;if(isEmpty(s)) return true; for(i=0;i< s.length;i++){c=s.charAt(i);if(whitespace.indexOf(c)==-1) return false;} return true;}
function isLetter(c){return (((c>="a")&&(c<="z"))||((c>="A")&&(c<="Z")))}
function isDigit(c){return ((c>="0")&&(c<="9"))}
function isLetterOrDigit(c){return (isLetter(c)||isDigit(c))}
function isInteger(s){i;if(isEmpty(s)) if(isInteger.arguments.length==1) return DEOK; else return (isInteger.arguments[1]==true);for(i=0;i< s.length;i++){c=s.charAt(i);if(!isDigit(c)) return false;} return true;}
function isSignedInteger(s){if(isEmpty(s)){if(isSignedInteger.arguments.length==1) return DEOK;else return (isSignedInteger.arguments[1]==true);}else{startPos=0;secondArg=DEOK;if(isSignedInteger.arguments.length>1) secondArg=isSignedInteger.arguments[1];if((s.charAt(0)=="-") || (s.charAt(0)=="+")) startPos=1;return (isInteger(s.substring(startPos,s.length),secondArg));}}
function isPositiveInteger(s){secondArg=DEOK;if(isPositiveInteger.arguments.length > 1) secondArg=isPositiveInteger.arguments[1];return (isSignedInteger(s,secondArg) && ((isEmpty(s) && secondArg) || (parseInt(s) > 0)));}
function isNonnegativeInteger(s){secondArg=DEOK;if(isNonnegativeInteger.arguments.length > 1) secondArg=isNonnegativeInteger.arguments[1];return (isSignedInteger(s,secondArg) && ((isEmpty(s) && secondArg) || (parseInt(s) >=0)));}
function isNegativeInteger(s){secondArg=DEOK;if(isNegativeInteger.arguments.length > 1) secondArg=isNegativeInteger.arguments[1]; return (isSignedInteger(s,secondArg) && ((isEmpty(s) && secondArg) || (parseInt(s) < 0)));}
function isNonpositiveInteger(s){secondArg=DEOK;if(isNonpositiveInteger.arguments.length > 1) secondArg=isNonpositiveInteger.arguments[1];return (isSignedInteger(s,secondArg) && ((isEmpty(s) && secondArg) || (parseInt (s) <=0)));}
function isFloat(s){i=0;seenDecimalPoint=false; if(isEmpty(s)) if (isFloat.arguments.length==1) return DEOK; else return (isFloat.arguments[1]==true); if(s==decimalPointDelimiter) return false; for(i=0; i < s.length; i++){ c=s.charAt(i); if((c==decimalPointDelimiter) && !seenDecimalPoint) seenDecimalPoint=true; else if(!isDigit(c)) return false; } return true; }
function isSignedFloat(s){if(isEmpty(s))if(isSignedFloat.arguments.length==1) return DEOK;else return (isSignedFloat.arguments[1]==true);else{startPos=0;secondArg=DEOK;if(isSignedFloat.arguments.length > 1) secondArg=isSignedFloat.arguments[1];if((s.charAt(0)=="-") || (s.charAt(0)=="+")) startPos=1;return (isFloat(s.substring(startPos, s.length), secondArg))}}
function isIntegerInRange(s,a,b){if(isEmpty(s))if(isIntegerInRange.arguments.length==1) return DEOK;else return (isIntegerInRange.arguments[1]==true);if(!isInteger(s, false)) return false;num=parseInt(s);return ((num >=a) && (num <=b));}
function isAlphabetic(s){i=0;if(isEmpty(s))if(isAlphabetic.arguments.length==1) return DEOK;else return (isAlphabetic.arguments[1]==true);for(i=0;i<s.length;i++){c=s.charAt(i);if(!isLetter(c)) return false;}return true;}
function isAlphanumeric(s){i=0;if(isEmpty(s))if(isAlphanumeric.arguments.length==1) return DEOK;else return (isAlphanumeric.arguments[1]==true);for(i=0;i<s.length;i++){c=s.charAt(i);if(!(isLetter(c) || isDigit(c))) return false;}return true;}
function isZipCode(s){return isValid(s,validZIPCodeChars);}

function Trim(fld){result="";c=0; for(i=0;i<fld.length;i++){if (fld.charAt(i) !=" " || c > 0){result +=fld.charAt(i);if (fld.charAt(i) !=" ") c=result.length;}}return result.substr(0,c);}
function isEmail(s){if(isEmpty(s))if(isEmail.arguments.length==1) return DEOK;else return(isEmail.arguments[1]==true);if(isWhitespace(s)) return false;i=1;sLength=s.length;while((i<sLength) && (s.charAt(i) !="@")){i++};if((i >=sLength) || (s.charAt(i) !="@")) return false;else i +=2;while((i < sLength) && (s.charAt(i) !=".")){i++};if((i >=sLength - 1) || (s.charAt(i) !=".")) return false;else return true;}
function isPassword(s){return !isShorter(s,PassLength) && isValid(s,lwr+upr + digits);};
function isLogin(s){return (!isShorter(s,LoginLength) && isValid(s.charAt(0),lwr + upr) && isValid(s,lwr + upr + digits));};
function validField(fld){fld=stripBlanks(fld);if(fld=='') return false;return true;}

function isMobPhoneNumber(s){if(isEmpty(s))if(isMobPhoneNumber.arguments.length==1) return DEOK; else return (isMobPhoneNumber.arguments[1]==true); return (isInteger(s)  && s.length==digitsInPhoneNumber);}
function isFixPhoneNumber(s){if(isEmpty(s))if(isFixPhoneNumber.arguments.length==1) return DEOK; else return (isFixPhoneNumber.arguments[1]==true); return (isInteger(s) && s.length==digitsInPhoneNumber);}
function isInternationalPhoneNumber(s){if(isEmpty(s))if(isInternationalPhoneNumber.arguments.length==1) return DEOK; else return (isInternationalPhoneNumber.arguments[1]==true);  return (isPositiveInteger(s)); }
function isYear(s){if(isEmpty(s))if(isYear.arguments.length==1)return DEOK; else return (isYear.arguments[1]==true); if (!isNonnegativeInteger(s)) return false; return (s.length==4);}
function isMonth(s){if(isEmpty(s))if(isMonth.arguments.length==1)return DEOK;else return (isMonth.arguments[1]==true);return isIntegerInRange(s,1,12);}
function isDay(s){if(isEmpty(s))if(isDay.arguments.length==1)return DEOK;else return (isDay.arguments[1]==true);return isIntegerInRange(s, 1, 31);}
function daysInFebruary(year){return(((year % 4==0) && ((!(year % 100==0)) || (year % 400==0) ) ) ? 29 : 28 );}
function isDate(year,month,day){if(!(isYear(year,false) && isMonth(month, false) && isDay(day, false))) return false; intYear=parseInt(year); intMonth=parseInt(month); intDay=parseInt(day); if (intDay > dInM[intMonth]) return false; if ((intMonth==2) && (intDay > daysInFebruary(intYear))) return false; return true; }

function getProValidateFieldValue(frm,p_ind){cur_field_name=frm.elements[p_ind].name.substring(2,frm.elements[p_ind].name.length);cur_field_prefics = frm.elements[p_ind].name.substring(0,2);found_field_ind=-1;for(gvind=0;((gvind<frm.elements.length) && (found_field_ind==-1));gvind++){if((cur_field_name==frm.elements[gvind].name.substring(2, frm.elements[gvind].name.length)) && (cur_field_prefics != frm.elements[gvind].name.substring(0,2))){found_field_ind=gvind; break;}}if(found_field_ind !=-1) return frm.elements[found_field_ind].value;else return -1;}
function getValidateField(frm,p_ind,ret_type){cur_field_name=frm.elements[p_ind].name.substring(2,frm.elements[p_ind].name.length);found_field_ind=-1;for(gvind=0;((gvind<frm.elements.length) && (found_field_ind==-1));gvind++){if(cur_field_name==frm.elements[gvind].name.substring(2, frm.elements[gvind].name.length))found_field_ind=gvind;}if(found_field_ind !=-1){if(ret_type=="type") return frm.elements[found_field_ind].name.charAt(1);else return frm.elements[found_field_ind].title;}else{return 0;}}
function isValidateField(frm,p_ind){validation_result=false;cur_field_name=frm.elements[p_ind].name.substring(2,frm.elements[p_ind].name.length);cur_field_type=frm.elements[p_ind].name.charAt(1);found_field_ind=-1;for(vind=0;((vind<frm.elements.length)&&(found_field_ind==-1));vind++){if((cur_field_type !=frm.elements[vind].name.charAt(1)) && (cur_field_name==frm.elements[vind].name.substring(2, frm.elements[vind].name.length)))found_field_ind=vind;}if(found_field_ind !=-1){if(frm.elements[found_field_ind].name.charAt(1)=="e"){validation_result=isEmail(frm.elements[p_ind].value);}else if(frm.elements[found_field_ind].name.charAt(1)=="p"){validation_result=isPassword(frm.elements[p_ind].value);}else{validation_result=false;}}else{validation_result=false;}return validation_result;}
function equalValidateField(frm,p_ind){validation_result=false;cur_field_name=frm.elements[p_ind].name.substring(2,frm.elements[p_ind].name.length);cur_field_type=frm.elements[p_ind].name.charAt(0);found_field_ind=-1;for(evind=0;((evind<frm.elements.length) && (found_field_ind==-1)); evind++){ if((cur_field_type !=frm.elements[evind].name.charAt(1)) && (cur_field_name==frm.elements[evind].name.substring(2, frm.elements[evind].name.length))) found_field_ind=evind; }if(found_field_ind !=-1){validation_result=(frm.elements[p_ind].value==frm.elements[found_field_ind].value);}else{validation_result=false;}return validation_result;}

function setNormalBackground(frm, ind){
    if(frm.elements[ind].type.substring(0,6) !="select"){
        frm.elements[ind].style.background = bgcolor_normal_1;
    }else{
        frm.elements[ind].style.background = bgcolor_normal_2;
    }
}
function setErrorBackground(frm, ind){
    frm.elements[ind].style.background = bgcolor_error;
}
function getFieldTitle(frm,ind){title_field=frm.elements[ind].title;if(title_field=="")title_field=frm.elements[ind].name.substring(2,frm.elements[ind].name.length);return title_field;}
function onSubmit(frm){return true;}
function onReqAlert(frm,ind){
    title_of_field=getFieldTitle(frm,ind);
    alert("The <" + title_of_field + "> is a required field!\nPlease, enter a valid " + title_of_field + ".");
    frm.elements[ind].focus();
    setErrorBackground(frm, ind);
    if(frm.elements[ind].type.substring(0,6) !="select"){ frm.elements[ind].select(); }
    return false;
}

function onInvalidAlert(frm, ind, ftype){
    type_of_field="value";
    title_of_field=getFieldTitle(frm,ind);
    switch (ftype){
        case 'n': type_of_field="be a numeric value"; break;
        case 'i': type_of_field="be an integer value"; break;
        case 'f': type_of_field="be a float(real) value"; break;
        case 'a': type_of_field="be an alphabetic value"; break;
        case 't': type_of_field="be a text"; break;
        case 'p': type_of_field="be " + PassLength + " characters at least\nand consist of letters and digits"; break;
        case 'l': type_of_field="be " + LoginLength + " characters at least,\nstart from letter and consist of letters or digits"; break;
        case 'z': type_of_field="be a Zip code value"; break;
        case 'e': type_of_field="be in email format"; break;
        case 'v': if(getValidateField(frm, ind, "type")=="e")
                    type_of_field="be in email format";
                  else if(getValidateField(frm, ind, "type")=="p")
                    type_of_field="be " + PassLength + " characters at least";
                  else
                    type_of_field="be a required type";
                  break;
        default: break;
    }
    alert("The <" + title_of_field + "> field must " + type_of_field + "!\nPlease, renter.");
    frm.elements[ind].focus();
    setErrorBackground(frm, ind);
    if(frm.elements[ind].type.substring(0,6) !="select") frm.elements[ind].select();
    return false;
}

function onNotEqualAlert(frm,ind){
    type_of_field=getValidateField(frm, ind, "name");
    title_of_field=getFieldTitle(frm,ind);
    if(type_of_field==0) type_of_field="required field";
    alert("The <" + title_of_field + "> field must be match with " + type_of_field + "!\nPlease, renter.");
    frm.elements[ind].focus();
    setErrorBackground(frm, ind);
    if(frm.elements[ind].type.substring(0,6) !="select") frm.elements[ind].select();
    return false;
}


// parametr - check hidden fields+check display.none fileds
function onSubmitCheck(frm){
    is_required="";
    a_type="";
    for(ind=0;ind<frm.elements.length;ind++){
        is_required=frm.elements[ind].name.charAt(0);
        a_type=frm.elements[ind].name.charAt(1);
        true_value=true;
        if(isSubmitReqType(is_required) && isSubmitVarType(a_type) && (frm.elements[ind].style.display !="none")){
            field_value=frm.elements[ind].value; //trim
            if(is_required=='r'){
                if(isEmpty(field_value)){ return onReqAlert(frm,ind); } else { setNormalBackground(frm,ind); }
            };
            if(((is_required=='r') || ((is_required=='s') && (!isEmpty(field_value)))) ||
               ((a_type=='v') && (!isEmpty(getProValidateFieldValue(frm,ind))))
              ){
                switch (a_type){
                    case 'n': if(!isNumeric(field_value, 3)){ true_value=false; } break;
                    case 'i': if(!isInteger(field_value))   { true_value=false; } break;
                    case 'f': if(!isFloat(field_value))     { true_value=false; } break;
                    case 'a': if(!isAlphabetic(field_value)){ true_value=false; } break;
                    case 't': if(!isText(field_value))      { true_value=false; } break;
                    case 'e': if(!isEmail(field_value))     { true_value=false; } break;
                    case 'p': if(!isPassword(field_value))  { true_value=false; } break;
                    case 'y': if(!isAny(field_value))       { true_value=false; } break;
                    case 'l': if(!isLogin(field_value))     { true_value=false; } break;
		    case 'z': if(!isZipCode(field_value))   { true_value=false; } break;
                    case 'v': if(!isValidateField(frm, ind)){ true_value=false; }
                              else if(!equalValidateField(frm, ind)) {return onNotEqualAlert(frm, ind);}
                              break;
                    default: break;
                }
                if(!true_value) return onInvalidAlert(frm, ind, a_type);
            }
        }
    }
    return true;
}