$(document).ready(function(){
alert("pagina cargada");
});

function checkEmail(obj) {
  var inputText = obj.value;
  emailRegex = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;
    if(inputText.length==0){
      document.getElementById("correoCheck").innerHTML = '<span id="correoCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca un correo valido</span>';
    }else{
    if (emailRegex.test(inputText)) {
      document.getElementById("correoCheck").innerHTML = '<span id="correoCheck" style="font-size: 12px; float: right;" class="text-success font-weight-bold">Dirección válida</span>';
    } else {
      document.getElementById("correoCheck").innerHTML = '<span id="correoCheck" style="font-size: 12px; float: right;" class="text-danger font-weight-bold">Dirección no válida</span>';
    }
  }
}

function checkLastName(obj) {
  var inputText = obj.value;
  nameRegex = /^[a-zA-ZÁÉÍÓÚáéíóúÑñ]+(\s*[a-zA-ZÁÉÍÓÚáéíóúÑñ])[a-zA-ZÁÉÍÓÚáéíóúÑñ]+$/;
  if (nameRegex.test(inputText)) {
      document.getElementById("apellidoPCheck").innerHTML = '<span id="apellidoPCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca su apellido paterno.</span>';
    } else {
      document.getElementById("apellidoPCheck").innerHTML = '<span id="apellidoPCheck" style="font-size: 12px; float: right;" class="text-danger font-weight-bold">Apellido no válido</span>';
    }
}

function checkName(obj) {
  var inputText = obj.value;
  nameRegex = /^[a-zA-ZÁÉÍÓÚáéíóúÑñ]+(\s*[a-zA-ZÁÉÍÓÚáéíóúÑñ])[a-zA-ZÁÉÍÓÚáéíóúÑñ]+$/;
    if (nameRegex.test(inputText)) {
      document.getElementById("nombreCheck").innerHTML = '<span id="nombreCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca su nombre.</span>';
    } else {
      document.getElementById("nombreCheck").innerHTML = '<span id="nombreCheck" style="font-size: 12px; float: right;" class="text-danger font-weight-bold">Nombre no válido</span>';
    }
}


function checkSLastName(obj) {
  var inputText = obj.value;
  nameRegex = /^[a-zA-ZÁÉÍÓÚáéíóúÑñ]+(\s*[a-zA-ZÁÉÍÓÚáéíóúÑñ])[a-zA-ZÁÉÍÓÚáéíóúÑñ]+$/;
      if(inputText.length==0){
      document.getElementById("apellidoMCheck").innerHTML = '<span id="apellidoMCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca su apellido materno</span>';
    }else{
    if (nameRegex.test(inputText)) {
      document.getElementById("apellidoMCheck").innerHTML = '<span id="apellidoMCheck" style="font-size: 12px; float: right;" class="text-success font-weight-bold">Introduzca su apellido materno</span>';
    } else {
      document.getElementById("apellidoMCheck").innerHTML = '<span id="apellidoMCheck" style="font-size: 12px; float: right;" class="text-danger font-weight-bold">Apellido no válido</span>';
    }
  }
}

function contadorTelefono(obj) {
    var maxLength = 10;
    var strLength = obj.value.length;
    var charRemain = (maxLength - strLength);
  
    if (charRemain <= 0) {
      document.getElementById("contadorTelefono").innerHTML = '<span id="contadorTelefono" style="font-size: 12px; float: right;" class="text-danger font-weight-bold">Límite alcanzado</span>';
    } else {
      document.getElementById("contadorTelefono").innerHTML = '<span id="contadorTelefono" style="font-size: 12px; float: right;" class="text-success font-weight-bold">' + charRemain + ' restantes</span>';
    }
  }

function contadorPassword(obj) {
    var maxLength = 20;
    var minLength = 8;
    var strLength = obj.value.length;
    var strValue = obj.value;
    passwordRegex = /^[0-9a-zA-Z]+$/;
  
    if(strLength == 0)
    {
      document.getElementById("contadorPassword").innerHTML = '<span id="contadorPassword" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">mínimo 8 caracteres</span>';
    }else{
    if(passwordRegex.test(strValue)){
    if (strLength < minLength) {
      document.getElementById("contadorPassword").innerHTML = '<span id="contadorPassword" style="font-size: 12px; float: right;" class="text-danger font-weight-bold">Contraseña demasiado corta</span>';
    } else if(strLength >= minLength && strLength <= 15)  {
      document.getElementById("contadorPassword").innerHTML = '<span id="contadorPassword" style="font-size: 12px; float: right;" class="text-warning font-weight-bold">Seguridad: Mediana</span>';
    }else if(strLength>15 && strLength <maxLength){
      document.getElementById("contadorPassword").innerHTML = '<span id="contadorPassword" style="font-size: 12px; float: right;" class="text-success font-weight-bold">Seguridad: Alta</span>';
    }else if(strLength==maxLength){
      document.getElementById("contadorPassword").innerHTML = '<span id="contadorPassword" style="font-size: 12px; float: right;" class="text-danger font-weight-bold">Ha excedido el limite de 20 caracteres.</span>';
    }
  }else{
    document.getElementById("contadorPassword").innerHTML = '<span id="contadorPassword" style="font-size: 12px; float: right;" class="text-danger font-weight-bold">Contraseña debe contra solo con letras y números</span>';
  }
  }
}