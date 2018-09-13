$(window).on('load', function () {
    $('#myModal').modal('show');
});

$(function(){

$('#vendorbtn').click(function(){
  $('#myModal').modal('show');
  return false;
});

});

//$( window ).unload(function() {
//
//    $.post( "BrowserCloseServlet", function( ) {
//
//    });
//    return "Goodbye";
//});
//window.onbeforeunload = (function(){
//    //Ajax request to update the database
//    $.ajax({
//        type: "POST",
//        url: "BrowserCloseServlet"
//    });
//    return ("Click leave this page to proceed");
//});
//        var textfield1 ="";
//      function selectConfirm(){
//       if (document.getElementById("confirm").checked === false ) {
//        textfield1 += '\r' + 'Please select at least one Vendor';
//        alert (textfield1);
//       // event.preventDefault();
//        onst = false;
//    }
//      return onst; 
//      
//    }

var count = 0; // To count blank fields.
function validation(event) {
    var radio_check = document.getElementsByName('gender'); // Fetching radio button by name.
    var input_field = document.getElementsByClassName('text_field'); // Fetching all inputs with same class name text_field and an html tag textarea.
    var input_field2 = document.getElementsByClassName('form-control');
    var text_area = document.getElementsByTagName('textarea');
// Validating radio button.
    if (radio_check[0].checked === false && radio_check[1].checked === false) {
        var y = 0;
    } else {
        var y = 1;
    }
// For loop to count blank inputs.
    for (var i = input_field.length; i > count; i--) {
        if (input_field[i - 1].value === '' || text_area.value === '') {
            count = count + 1;
        } else {
            count = 0;
        }
    }

    if (count !== 0 || y === 0) {
        alert("*All Fields are mandatory*"); // Notifying validation
        event.preventDefault();
    } else {
        return true;
    }
}
/*---------------------------------------------------------*/
// Function that executes on click of first next button.

// $(document).ready(function() {
//        $.active = false;
//        $('body').bind('click keypress', function() { $.active = true; });
//        checkActivity(60000, 60000, 0); // timeout = 30 minutes, interval = 1 minute.
//    });
//
//
// function checkActivity(timeout, interval, elapsed) {
//        if ($.active) {
//            elapsed = 0;
//            $.active = false;
//            $.get('heartbeat');
//        }
//        if (elapsed < timeout) {
//            elapsed += interval;
//            setTimeout(function() {
//                checkActivity(timeout, interval, elapsed);
//            }, interval);
//        } else {
//            window.location = 'http://localhost:8080/VendorPortal/login.jsp'; // Redirect to "session expired" page.
//        }
//    }
var IDLE_TIMEOUT = 900; //seconds
var _localStorageKey = 'global_countdown_last_reset_timestamp';
var _idleSecondsTimer = null;
var _lastResetTimeStamp = (new Date()).getTime();
var _localStorage = null;

AttachEvent(document, 'click', ResetTime);
AttachEvent(document, 'mousemove', ResetTime);
AttachEvent(document, 'keypress', ResetTime);
AttachEvent(window, 'load', ResetTime);

try {
    _localStorage = window.localStorage;
}
catch (ex) {
}

_idleSecondsTimer = window.setInterval(CheckIdleTime, 1000);

function GetLastResetTimeStamp() {
    var lastResetTimeStamp = 0;
    if (_localStorage) {
        lastResetTimeStamp = parseInt(_localStorage[_localStorageKey], 10);
        if (isNaN(lastResetTimeStamp) || lastResetTimeStamp < 0)
            lastResetTimeStamp = (new Date()).getTime();
    } else {
        lastResetTimeStamp = _lastResetTimeStamp;
    }

    return lastResetTimeStamp;
}

function SetLastResetTimeStamp(timeStamp) {
    if (_localStorage) {
        _localStorage[_localStorageKey] = timeStamp;
    } else {
        _lastResetTimeStamp = timeStamp;
    }
}

function ResetTime() {
    SetLastResetTimeStamp((new Date()).getTime());
}

function AttachEvent(element, eventName, eventHandler) {
    if (element.addEventListener) {
        element.addEventListener(eventName, eventHandler, false);
        return true;
    } else if (element.attachEvent) {
        element.attachEvent('on' + eventName, eventHandler);
        return true;
    } else {
        //nothing to do, browser too old or non standard anyway
        return false;
    }
}

function WriteProgress(msg) {
    var oPanel = document.getElementById("SecondsUntilExpire");
    if (oPanel)
         oPanel.innerHTML = msg;
    else if (console)
        console.log(msg);
}

function CheckIdleTime() {
    var currentTimeStamp = (new Date()).getTime();
    var lastResetTimeStamp = GetLastResetTimeStamp();
    var secondsDiff = Math.floor((currentTimeStamp - lastResetTimeStamp) / 1000);
    if (secondsDiff <= 0) {
        ResetTime();
        secondsDiff = 0;
    }
    WriteProgress((IDLE_TIMEOUT - secondsDiff) + "");
    if (secondsDiff >= IDLE_TIMEOUT) {
        window.clearInterval(_idleSecondsTimer);
        ResetTime();
       // $.get('heartbeat');
        
     //   alert("Your Session has expired! \r Please login again!!!");
     document.location.href = "http://localhost:8080/VendorPortalBE/";
      //  
     // Ajax request to update the database
//   $.ajax({      
//       type: "POST",
//        url: "Logout"
//    });
        document.getElementById("timeout").style.display = "block";
    }
}

//function filter(){
//       //Ajax request to filter
//   $.ajax({      
//       type: "POST",
//        url: "Filter"
//    });
//}


function mycountry() {
    var country = document.getElementById('country');
    var y = country.options[country.selectedIndex].value;
    console.log(y);
    if (y !== 'Nigeria') {
        document.getElementById("countryy").style.display = "none";
    } else {
        document.getElementById("countryy").style.display = "block";
    }
}



function next_step1(a, b) {

    document.getElementById(a).style.display = "none";
    document.getElementById(b).style.display = "block";
}
var a,b,c;

function formValidate1(){
   
    if(FormValidation1()){
        next_step1('first', 'second');
    }
}

function FormValidation1() {
    var onst = true;
    var textfield1 ='';
  
    var suppliername = document.getElementById("suppliername").value;

    if (suppliername === "") {
        onst=false;
        
        textfield1 += 'Please Enter supplier Name';
        document.getElementById('suppliername').style.borderColor = "red";
        
    } else {
        document.getElementById('suppliername').style.borderColor = "green";
    }
    
    var selectsup = document.getElementById("selectsup").value;

    if (selectsup === "") {
        onst=false;
        textfield1 += '\r' + 'Please Select Supplier Type';
        document.getElementById('selectsup').style.borderColor = "red";
        
    } else {
        document.getElementById('selectsup').style.borderColor = "green";
    }
    var business = document.getElementById("business").value;

    if (business === "") {
        onst=false;
         textfield1+= '\r' +'Please Select Business Type';
        document.getElementById('business').style.borderColor = "red";
        
    } else {
        document.getElementById('business').style.borderColor = "green";
    }
    
     if(document.getElementById('nigerian').checked) {
    
    
        var tax = document.getElementById("tax");
    //alert(tax.value);
    if(tax.value===""){
        onst = false;
        textfield1 += '\r'+ 'Please input a correct TIN';
        document.getElementById('tax').style.borderColor = "red";
    }
    if(tax.value !== ''){
        var re = /^\d{8}-0001$/;
       if (!re.test(tax.value)){
           onst=false;
           textfield1 += '\r'+ 'Please input a correct TIN';
        document.getElementById('tax').style.borderColor = "red";
        
    } else {
        document.getElementById('tax').style.borderColor = "green";
    }
        }
    
    }
    
    if(document.getElementById('multinational').checked) {
    
    
        var tax = document.getElementById("tax2");
    //alert(tax.value);
    if(tax.value===""){
        onst = false;
        textfield1 += '\r'+ 'Please input a correct international TIN';
        document.getElementById('tax2').style.borderColor = "red";
    }else {
        document.getElementById('tax2').style.borderColor = "green";
    }
//    if(tax.value !== ''){
//        var re = /^\d{8}-0001$/;
//       if (!re.test(tax.value)){
//           onst=false;
//           textfield1 += '\r'+ 'Please input a correct TIN';
//        document.getElementById('tax2').style.borderColor = "red";
//        
//    } else {
//        document.getElementById('tax2').style.borderColor = "green";
//    }
//        }
    
    }
    
 
    if (document.getElementById("nigerian").checked === false && document.getElementById("multinational").checked === false){
       textfield1 += '\r'+'Please select at least one of Nigerian or Multinational';
        onst = false;
    }
    

       
        if(!onst ){
            alert (textfield1);
           
           
        }
         return onst;
}

function formValidate2(){
   
    if(FormValidation2()){
        next_step1('second', 'third');
    }
}

function FormValidation2() {
    var onst = true;
    var textfield2 ='';
    
    var site = document.getElementById("site").value;

    if (site === "") {
        onst=false;
        textfield2 += 'Please Enter site name';
        document.getElementById('site').style.borderColor = "red";
        
    } else {
        document.getElementById('site').style.borderColor = "green";
    }
    var country = document.getElementById("country").value;

    if (country === "") {
        onst=false;
        textfield2 += '\r'+ 'Please Select country';
        document.getElementById('country').style.borderColor = "red";
       
    } else {
        document.getElementById('country').style.borderColor = "green";
    }
    var addressline1 = document.getElementById("addressline1").value;

    if (addressline1 === "") {
        onst=false;
        textfield2 += '\r' +'Please Enter Primary Addressline'
        document.getElementById('addressline1').style.borderColor = "red";
       
    } else {
        document.getElementById('addressline1').style.borderColor = "green";
    }

    var city = document.getElementById("city").value;

    if (city === "") {
        onst=false;
        textfield2 += '\r'+'Please Enter City Name';
        document.getElementById('city').style.borderColor = "red";
        
    } else {
        document.getElementById('city').style.borderColor = "green";
    }
    
  var count =  document.getElementById("country").value;
  
        if (count === "Nigeria"){
            
           var customerResidState = document.getElementById("customerResidState").value;

    if(customerResidState === ""){
        onst=false;
        textfield2+='\r'+'Please Enter State Name';
        document.getElementById('customerResidState').style.borderColor = "red";
       
    }else{
        document.getElementById('customerResidState').style.borderColor = "green";
    }
        var customerResidCity = document.getElementById("customerResidCity").value;
   
    if(customerResidCity === ""){
        onst=false;
        textfield2+='\r'+'Please Enter LGA';
        document.getElementById('customerResidCity').style.borderColor = "red";
        
    } else{
        document.getElementById('customerResidCity').style.borderColor = "green";
    }
        var unit = document.getElementById("unit").value;
   
    if(unit === ""){
        onst=false;
        textfield2+='\r'+'Please Enter Unit Name';
        document.getElementById('unit').style.borderColor = "red";
        
    }else{
        document.getElementById('unit').style.borderColor = "green";
    }
        }  
    

    var Salutation = document.getElementById("Salutation").value;

    if (Salutation === "") {
        onst = false;
        textfield2+='\r'+'Please Enter valid Salutation';
        document.getElementById('Salutation').style.borderColor = "red";
        
    } else {
        document.getElementById('Salutation').style.borderColor = "green";
    }
    var fname = document.getElementById("fname").value;

    if (fname === "") {
        onst = false;
        textfield2+='\r'+'Please Enter valid First Name';
        document.getElementById('fname').style.borderColor = "red";
        
    } else {
        document.getElementById('fname').style.borderColor = "green";
    }
    var lname = document.getElementById("lname").value;

    if (lname === "") {
        onst = false;
        textfield2+='\r'+'Please Enter valid Last Name';
        document.getElementById('lname').style.borderColor = "red";
        
    } else {
        document.getElementById('lname').style.borderColor = "green";
    }
    var dept = document.getElementById("dept").value;

    if (dept === "") {
        onst=false;
        textfield2+='\r'+'Please Enter valid Department';
        document.getElementById('dept').style.borderColor = "red";
    } else {
        document.getElementById('dept').style.borderColor = "green";
    }
    var child_1 = document.getElementById("child_1").value;

    if (child_1 === "") {
        onst=false;
        textfield2+='\r'+'Please Enter valid Phone Number';
        document.getElementById('child_1').style.borderColor = "red";
        
    } else {
        document.getElementById('child_1').style.borderColor = "green";
    }
    var chil_1 = document.getElementById("chil_1").value;




    var atpos = chil_1.indexOf("@");
    var dotpos = chil_1.lastIndexOf(".");

    if (chil_1 === "" || atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= chil_1.length) {
        onst=false;
        textfield2+='\r'+'Please Enter valid Email Address';
        document.getElementById('chil_1').style.borderColor = "red";
        
    } else {
        document.getElementById('chil_1').style.borderColor = "green";
    }

     if(!onst ){
            alert (textfield2);
           
           
        }
         return onst;

}

function formValidate3(){
   
    if(FormValidation3()){
        next_step1('third', 'fourth');
    }
}

function FormValidation3() {
    var onst=true;
    var textfield3  = '';
    var FBNdepartment = document.getElementById("FBNdepartment").value;

    if (FBNdepartment === "") {
        onst=false;
        textfield3+='Please Select a Valid Department';
        document.getElementById('FBNdepartment').style.borderColor = "red";
        
    } else {
        document.getElementById('FBNdepartment').style.borderColor = "green";
    }
    var staffname = document.getElementById("staffname").value;

    if (staffname === "") {
        onst = false;
        textfield3+='\r'+'Please Enter Correct First Bank Staff Name';
        document.getElementById('staffname').style.borderColor = "red";
        
    } else {
        document.getElementById('staffname').style.borderColor = "green";
    }
     if(!onst ){
            alert (textfield3);
           
           
        }
         return onst;
}


function formValidate4(){
   
    if(FormValidation4()){
        next_step1('fourth', 'fifth');
    }
}


function FormValidation4() {
     var onst=true;
    var textfield4  = '';
    var payment = document.getElementById("payment").value;

    if (payment === "") {
        onst=false;
        textfield4+='Please Select Valid Payment Method';
        document.getElementById('payment').style.borderColor = "red";
        
    } else {
        document.getElementById('payment').style.borderColor = "green";
    }
    var deliveryMethod = document.getElementById("deliveryMethod").value;

    if (deliveryMethod === "") {
        onst=false;
        textfield4+='\r'+'Please Select Valid Delivery Method';
        document.getElementById('deliveryMethod').style.borderColor = "red";
        
    } else {
        document.getElementById('deliveryMethod').style.borderColor = "green";
    }
    
    
    var bankname = document.getElementById('bank_name').value;
    if(bankname === "Others"){
    
    var swiftcode = document.getElementById("swiftcode").value;
    
    

    if (swiftcode === "") {
        onst=false;
        textfield4+='\r'+'Please Enter Valid Swift Code';
        document.getElementById('swiftcode').style.borderColor = "red";
        
    } else {
        document.getElementById('swiftcode').style.borderColor = "green";
    }
        
    var others4 = document.getElementById("others4").value;
    
     if (others4 === "") {
        onst=false;
        textfield4+='\r'+'Please Enter Valid Foreign Bank Name';
        document.getElementById('others4').style.borderColor = "red";
        
    } else {
        document.getElementById('others4').style.borderColor = "green";
    }
    
    }

    var eftemail = document.getElementById("eftemail").value;
    var atpos = eftemail.indexOf("@");
    var dotpos = eftemail.lastIndexOf(".");
    if (eftemail === "" || atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= eftemail.length) {
        onst=false;
        textfield4+='\r'+'Please Enter Valid Email Address';
        document.getElementById('eftemail').style.borderColor = "red";
        
    } else {
        document.getElementById('eftemail').style.borderColor = "green";
    }

    var Currencies = document.getElementById("Currencies").value;

    if (Currencies === "") {
        onst=false;
        textfield4+='\r'+'Please Select Valid Currency';
        document.getElementById('Currencies').style.borderColor = "red";
        
    } else {
        document.getElementById('Currencies').style.borderColor = "green";
    }

    var paymentTerms = document.getElementById("paymentTerms").value;

    if (paymentTerms === "") {
        onst=false;
        textfield4+='\r'+'Please Select Payment Terms';
        document.getElementById('paymentTerms').style.borderColor = "red";
        
    } else {
        document.getElementById('paymentTerms').style.borderColor = "green";
    }
    var bank_name = document.getElementById("bank_name").value;

    if (bank_name === "") {
        onst=false;
        textfield4+='\r'+'Please Select Bank Name';
        document.getElementById('bank_name').style.borderColor = "red";
        
    } else {
        document.getElementById('bank_name').style.borderColor = "green";
    }
    var bank_branch_name = document.getElementById("bank_branch_name").value;

    if (bank_branch_name === "") {
        onst=false;
        textfield4+='\r'+'Please Enter Bank Branch Name';
        document.getElementById('bank_branch_name').style.borderColor = "red";
        
    } else {
        document.getElementById('bank_branch_name').style.borderColor = "green";
    }
    var accountnumber = document.getElementById("accountnumber").value;

    if (accountnumber === "") {
        onst=false;
        textfield4+='\r'+'Please Enter valid Account Number';
        document.getElementById('accountnumber').style.borderColor = "red";
        
    } else {
        document.getElementById('accountnumber').style.borderColor = "green";
    }
    var accountname = document.getElementById("accountname").value;

    if (accountname === "") {
        onst=false;
        textfield4+='\r'+'Please Enter valid Account Name';
        document.getElementById('accountname').style.borderColor = "red";
        
    } else {
        document.getElementById('accountname').style.borderColor = "green";
    }
    var accountcurrency = document.getElementById("accountcurrency").value;

    if (accountcurrency === "") {
        onst=false;
        textfield4+='\r'+'Please Select Valid Account Currency';
        document.getElementById('accountcurrency').style.borderColor = "red";
        
    } else {
        document.getElementById('accountcurrency').style.borderColor = "green";
    }
    
         if(!onst ){
            alert (textfield4);
           
           
        }
         return onst;
}


function chang() {
    var others = document.getElementById('selectsup');
    var y = others.options[others.selectedIndex].value;
    if (y === "32") {
        var field = document.getElementById("visib").style.display = "block";
        field.type = 'text';
    } else if (y !== "32") {
        var field = document.getElementById("visib").style.display = "none";
    }
}




function chang2() {
    var others = document.getElementById('business');
    var y = others.options[others.selectedIndex].value;
    if (y === "8") {
        var field = document.getElementById("visib2").style.display = "block";
        field.type = 'text';
    } else if (y !== "8") {
        var field = document.getElementById("visib2").style.display = "none";
    }
}



function chang4() {
    var others = document.getElementById('bank_name');
    var y = others.options[others.selectedIndex].value;
    if (y === 'Others') {
        var field = document.getElementById("visib4").style.display = "block";
        field.type = 'text';
    } else if (y !== 'Others') {
        var field = document.getElementById("visib4").style.display = "none";
    }
}



var i = 1;
function addnumber() {

    if (i <= 3) {
        i++;
        var div = document.createElement('div');
        div.innerHTML = '<label>Contact Number (Optional):</label><input type="text" class="form-control" name="child_' + i + '"><button type="button" class="btn btn-primary btn-sm" id="add_kid" onClick="addnumber()">add</button>&emsp;<button type="button" class="btn btn-primary btn-sm" onClick="removeKid(this)" style="padding-left:10px;">remove</button>';
        document.getElementById('addnumbers').append(div);
    }
}

function removeKid(div) {
    document.getElementById('addnumbers').removeChild(div.parentNode);
    i--;
}


var st = 1;
function addcurrency() {

    if (st <= 4) {
        st++;
        var div = document.createElement('div');
        div.innerHTML = '<br /><select class="form-control" id="accountcurrency" name="currency' + st + '"><option selected value="Nigerian NGN">Nigerian NGN</option><option value="America (United States) Dollars - USD">America (United States) Dollars – USD</option><option value="Afghanistan Afghanis - AFN">Afghanistan Afghanis – AFN</option><option value="Albania Leke - ALL">Albania Leke – ALL</option><option value="Algeria Dinars - DZD">Algeria Dinars – DZD</option><option value="Argentina Pesos - ARS">Argentina Pesos – ARS</option><option value="Australia Dollars - AUD">Australia Dollars – AUD</option><option value="Austria Schillings - ATS">Austria Schillings – ATS</OPTION><option value="Bahamas Dollars - BSD">Bahamas Dollars – BSD</option><option value="Bahrain Dinars - BHD">Bahrain Dinars – BHD</option><option value="Bangladesh Taka - BDT">Bangladesh Taka – BDT</option><option value="Barbados Dollars - BBD">Barbados Dollars – BBD</option><option value="Belgium Francs - BEF">Belgium Francs – BEF</OPTION><option value="Bermuda Dollars - BMD">Bermuda Dollars – BMD</option><option value="Brazil Reais - BRL">Brazil Reais – BRL</option><option value="Bulgaria Leva - BGN">Bulgaria Leva – BGN</option><option value="Canada Dollars - CAD">Canada Dollars – CAD</option><option value="CFA BCEAO Francs - XOF">CFA BCEAO Francs – XOF</option><option value="CFA BEAC Francs - XAF">CFA BEAC Francs – XAF</option><option value="Chile Pesos - CLP">Chile Pesos – CLP</option><option value="China Yuan Renminbi - CNY">China Yuan Renminbi – CNY</option><option value="RMB (China Yuan Renminbi) - CNY">RMB (China Yuan Renminbi) – CNY</option><option value="Colombia Pesos - COP">Colombia Pesos – COP</option><option value="CFP Francs - XPF">CFP Francs – XPF</option><option value="Costa Rica Colones - CRC">Costa Rica Colones – CRC</option><option value="Croatia Kuna - HRK">Croatia Kuna – HRK</option><option value="Cyprus Pounds - CYP">Cyprus Pounds – CYP</option><option value="Czech Republic Koruny - CZK">Czech Republic Koruny – CZK</option><option value="Denmark Kroner - DKK">Denmark Kroner – DKK</option><option value="Deutsche (Germany) Marks - DEM">Deutsche (Germany) Marks – DEM</OPTION><option value="Dominican Republic Pesos - DOP">Dominican Republic Pesos – DOP</option><option value="Dutch (Netherlands) Guilders - NLG">Dutch (Netherlands) Guilders – NLG</OPTION><option value="Eastern Caribbean Dollars - XCD">Eastern Caribbean Dollars – XCD</option><option value="Egypt Pounds - EGP">Egypt Pounds – EGP</option><option value="Estonia Krooni - EEK">Estonia Krooni – EEK</option><option value="Euro - EUR">Euro – EUR</option><option value="Fiji Dollars - FJD">Fiji Dollars – FJD</option><option value="Finland Markkaa - FIM">Finland Markkaa – FIM</OPTION><option value="France Francs - FRF*">France Francs – FRF*</OPTION><option value="Germany Deutsche Marks - DEM">Germany Deutsche Marks – DEM</OPTION><option value="Gold Ounces - XAU">Gold Ounces – XAU</option><option value="Greece Drachmae - GRD">Greece Drachmae – GRD</OPTION><option value="Guatemalan Quetzal - GTQ">Guatemalan Quetzal – GTQ</OPTION><option value="Holland (Netherlands) Guilders - NLG">Holland (Netherlands) Guilders – NLG</OPTION><option value="Hong Kong Dollars - HKD">Hong Kong Dollars – HKD</option><option value="Hungary Forint - HUF">Hungary Forint – HUF</option><option value="Iceland Kronur - ISK">Iceland Kronur – ISK</option><option value="IMF Special Drawing Right - XDR">IMF Special Drawing Right – XDR</option><option value="India Rupees - INR">India Rupees – INR</option><option value="Indonesia Rupiahs - IDR">Indonesia Rupiahs – IDR</option><option value="Iran Rials - IRR">Iran Rials – IRR</option><option value="Iraq Dinars - IQD">Iraq Dinars – IQD</option><option value="Ireland Pounds - IEP*">Ireland Pounds – IEP*</OPTION><option value="Israel New Shekels - ILS">Israel New Shekels – ILS</option><option value="Italy Lire - ITL*">Italy Lire – ITL*</OPTION><option value="Jamaica Dollars - JMD">Jamaica Dollars – JMD</option><option value="Japan Yen - JPY">Japan Yen – JPY</option><option value="Jordan Dinars - JOD">Jordan Dinars – JOD</option><option value="Kenya Shillings - KES">Kenya Shillings – KES</option><option value="Korea (South) Won - KRW">Korea (South) Won – KRW</option><option value="Kuwait Dinars - KWD">Kuwait Dinars – KWD</option><option value="Lebanon Pounds - LBP">Lebanon Pounds – LBP</option><option value="Luxembourg Francs - LUF">Luxembourg Francs – LUF</OPTION><option value="Malaysia Ringgits - MYR">Malaysia Ringgits – MYR</option><option value="Malta Liri - MTL">Malta Liri – MTL</option><option value="Mauritius Rupees - MUR">Mauritius Rupees – MUR</option><option value="Mexico Pesos - MXN">Mexico Pesos – MXN</option><option value="Morocco Dirhams - MAD">Morocco Dirhams – MAD</option><option value="Netherlands Guilders - NLG">Netherlands Guilders – NLG</OPTION><option value="New Zealand Dollars - NZD">New Zealand Dollars – NZD</option><option value="Norway Kroner - NOK">Norway Kroner – NOK</option><option value="Oman Rials - OMR">Oman Rials – OMR</option><option value="Pakistan Rupees - PKR">Pakistan Rupees – PKR</option><option value="Palladium Ounces - XPD">Palladium Ounces – XPD</option><option value="Peru Nuevos Soles - PEN">Peru Nuevos Soles – PEN</option><option value="Philippines Pesos - PHP">Philippines Pesos – PHP</option><option value="Platinum Ounces - XPT">Platinum Ounces – XPT</option><option value="Poland Zlotych - PLN">Poland Zlotych – PLN</option><option value="Portugal Escudos - PTE">Portugal Escudos – PTE</OPTION><option value="Qatar Riyals - QAR">Qatar Riyals – QAR</option><option value="Romania New Lei - RON">Romania New Lei – RON</option><option value="Romania Lei - ROL">Romania Lei – ROL</option><option value="Russia Rubles - RUB">Russia Rubles – RUB</option><option value="Saudi Arabia Riyals - SAR">Saudi Arabia Riyals – SAR</option><option value="Silver Ounces - XAG">Silver Ounces – XAG</option><option value="Singapore Dollars - SGD">Singapore Dollars – SGD</option><option value="Slovakia Koruny - SKK">Slovakia Koruny – SKK</option><option value="Slovenia Tolars - SIT">Slovenia Tolars – SIT</option><option value="South Africa Rand - ZAR">South Africa Rand – ZAR</option><option value="South Korea Won - KRW">South Korea Won – KRW</option><option value="Spain Pesetas - ESP">Spain Pesetas – ESP</OPTION><option value="Special Drawing Rights (IMF) - XDR">Special Drawing Rights (IMF) – XDR</option><option value="Sri Lanka Rupees - LKR">Sri Lanka Rupees – LKR</option><option value="Sudan Dinars - SDD">Sudan Dinars – SDD</option><option value="Sweden Kronor - SEK">Sweden Kronor – SEK</option><option value="Switzerland Francs - CHF">Switzerland Francs – CHF</option><option value="Taiwan New Dollars - TWD">Taiwan New Dollars – TWD</option><option value="Thailand Baht - THB">Thailand Baht – THB</option><option value="Trinidad and Tobago Dollars - TTD">Trinidad and Tobago Dollars – TTD</option><option value="Tunisia Dinars - TND">Tunisia Dinars – TND</option><option value="Turkey New Lira - TRY">Turkey New Lira – TRY</option><option value="United Arab Emirates Dirhams - AED">United Arab Emirates Dirhams – AED</option><option value="United Kingdom Pounds - GBP">United Kingdom Pounds – GBP</option><option value="United States Dollars - USD">United States Dollars – USD</option><option value="Venezuela Bolivares - VEB">Venezuela Bolivares – VEB</option><option value="Vietnam Dong - VND">Vietnam Dong – VND</option><option value="Zambia Kwacha - ZMK">Zambia Kwacha – ZMK</option></select></div><button type="button" class="btn btn-primary btn-sm" id="add_kid" onClick="addcurrency()">add</button>&emsp;<button type="button" class="btn btn-primary btn-sm" onClick="removecurr(this)" style="padding-left:10px;">remove</button><br />';
        document.getElementById('addcurr').append(div);
    }
}
function removecurr(div) {
    document.getElementById('addcurr').removeChild(div.parentNode);
    st--;
}

// \n\



var j = 1;
function addmail() {

    if (j <= 3) {
        j++;
        var div = document.createElement('div');
        div.innerHTML = '<label>Email Address (Optional):</label><input type="text" class="form-control" name="chil_' + j + '"><button type="button" class="btn btn-primary btn-sm" id="add_kid" onClick="addmail()">add</button>&emsp;<button type="button" class="btn btn-primary btn-sm" onClick="removeKid1(this)" style="padding-left:10px;">remove</button>';
        document.getElementById('addmail').append(div);
    }
}


function removeKid1(div) {
    document.getElementById('addmail').removeChild(div.parentNode);
    j--;
}



function addSiteName() {
    document.getElementById("hide").style.display = "block";
    document.getElementById("add_site").style.display = "none";
}
function removeSiteName() {
    document.getElementById("hide").style.display = "none";
    document.getElementById("add_site").style.display = "block";
}



function addSecDetails() {
    document.getElementById("hide3").style.display = "block";
    document.getElementById("add_sec").style.display = "none";
}

function removeSecDetails() {
    document.getElementById("hide3").style.display = "none";
    document.getElementById("add_sec").style.display = "block";
}

function displayState(checkbox) {

    if (checkbox.checked === true) {
        document.getElementById("hide2").style.display = "block";
    } else {
        document.getElementById("hide2").style.display = "none";
    }
}


function displayBtn(checkbox) {

    if (checkbox.checked === true) {
        document.getElementById("hide0").style.display = "block";
    } else {
        document.getElementById("hide0").style.display = "none";
    }
}

function removeBtn(checkbox) {

    if (checkbox.checked === true) {
        document.getElementById("hide0").style.display = "none";
    }
}

function localtax(checkbox) {

    if (checkbox.checked === true) {
        document.getElementById("tinhide1").style.display = "block";
    }
    else{
       document.getElementById("tinhide1").style.display = "none"; 
    }
}

function multitax(checkbox) {

    if (checkbox.checked === true) {
        document.getElementById("tinhide2").style.display = "block";
    }
    else{
       document.getElementById("tinhide2").style.display = "none"; 
    }
}



function getChecked() {
    var checks = document.getElementsByClassName("source");
    var str = '';
    var i;
    for (i = 0; i <= 36; i++)
        if (checks[i].checked === true) {
            str += checks[i].value + ' ';
        }
    //  alert (str);
    document.getElementById("checkedstate").value = str;
    
    
}

function mustcheck(){
    var bool = true;
    var nigerian = document.getElementById("nigerian").value;
    var multinational = document.getElementById("multinational").value;
    if (nigerian ==="" || multinational ===""){
        alert ("Please select at least one of Nigerian or Multinational");
        bool = false;
    }
    return bool;
}

function getChecked2() {
    var checks = document.getElementsByClassName("source2");
    var str = '';
    var i;
    for (i = 0; i < 2; i++)
        if (checks[i].checked === true) {
            str += checks[i].value + ' ';
        }
    // alert (str);
    document.getElementById("checkedcompany").value = str;
}
// Function that executes on click of first previous button.
function prev_step1(a, b) {

    document.getElementById(a).style.display = "block";
    document.getElementById(b).style.display = "none";



}




    $(document).ready(function () {
        $('input[type=file]').change(function () {
            var val = $(this).val().toLowerCase();
            var regex = new RegExp("(.*?)\.(pdf)$");
            if (!(regex.test(val))) {
                $(this).val('');
                alert('Please select correct file format');
                
                document.getElementById('attachment').style.borderColor = "red";
                
            }
        });


    });

function chk(event) {

//var onst = true;
//var textfield5='';


    if (document.getElementById("attachment").value !== '') {
        var size = 16177215;
        var file_size = document.getElementById('attachment').files[0].size;
        if (file_size >= size) {
            alert('File too large');
            event.preventDefault();
            document.getElementById('attachment').style.borderColor = "red";
           
        }
    }


    if (document.getElementById("attachment").value === '') {
        alert('Please attach a file');
        event.preventDefault();
        document.getElementById('attachment').style.borderColor = "red";
        
    }
}


function confirmVendor() {
    var checks = document.getElementsByClassName("confirm");
    var str = '';
    var i;
    for (i = 0; i <= checks.length-1; i++)
        
        if (checks[i].checked === true) {
            str += checks[i].value + ' ';
        }
     // alert (str);
    document.getElementById("confirmedIds").value = str;
    
}

function displaySwift(){
    var bankname = document.getElementById("bank_name").value;
     if (bankname === "Others") {
        document.getElementById("swift").style.display = "block";
    } else {
        document.getElementById("swift").style.display = "none";
    }
}


//function confirmApprovalCheckboxClick(){
//    var bool = true;
//    var checks = document.getElementsByClassName("confirm");
//    var str = "";
//    var i;
//    for (i = 0; i <= checks.length-1; i++)
//        
//        if (checks[i].checked === true) {
//            str += checks[i].value + ' ';
//        }
//    if (str !== ""){
//    alert ("Please select at least one vendor to confirm");
//        bool = false;
//    }
//    return bool;
//     // alert (str);
//
//}
