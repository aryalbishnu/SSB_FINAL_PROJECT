const toggleNavbar = () => {
	$(".collapse").slideToggle();
}

function onlyNumberKey(evt) {
          
        // Only ASCII character in that range allowed
        var ASCIICode = (evt.which) ? evt.which : evt.keyCode
        if (ASCIICode > 31 && (ASCIICode < 48 || ASCIICode > 57))
            return false;
        return true;
    }
    
    function changeToUpperCase(event,obj) {
    charValue = (document.all) ? event.keyCode : event.which;
    if (charValue!="8" && charValue!="0" && charValue != "27"){
        obj.value += String.fromCharCode(charValue).toUpperCase();
        return false;
    }else{
        return true;
    }
}

//only login form3
function myFunction() {
//  const x = document.getElementById("flexRadioDefaultlicense1").value;
 var y = document.form3.drivingLicense.value;

if(y=="Yes"){
    $("#license").addClass("test2");
    $("#license").removeClass("test1");
    
     
}else if(y=="No"){
      $("#license").addClass("test1");
  $("#license").removeClass("test2");
   
form3.licenseNumber.value='';

 }
}

//only admin update
function adminFunction() {
 var y = document.admin.drivingLicense.value;

if(y=="Yes"){
     $("#adminlicense").removeClass("test3");
   
}else if(y=="No"){
  $("#adminlicense").addClass("test3");
   
admin.licenseNumber.value='';

 }
}
