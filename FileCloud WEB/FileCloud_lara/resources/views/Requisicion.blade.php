@extends('layouts.app')
@section('content')
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Nueva Requisición</title>
    <link rel="stylesheet" href="{{URL::to('/')}}/css/bootstrap.min.css">
    <link rel="stylesheet" href="{{URL::to('/')}}/css/bootstrap.css">
    <link rel="stylesheet" href="{{URL::to('/')}}/css/requisicionStyle.css">
    <script src="{{URL::to('/')}}/js/counters.js" type="text/javascript"></script>
</head>
<body>
<h1 class="text-center">Nueva Requisición</h1>
        <form method="post" action="{{URL::to('/')}}/Requisicion" id="container" class="shadow-lg p-3 mb-5 bg-white rounded">
            {{ csrf_field() }}
            <div class="form-group">
                <label for="user" class="col-form-label">Usuario</label>
                <select name="user" id="user" class="form-control" required>
                    <option value="" selected>Seleccione un usuario...</option>
                    @foreach($allUsers as $user)
                    @if($user['tipoUsuario']==1 || $user['tipoUsuario']==2)
                    <option value="{{$user['Usuario']}}">{{$user['Nombre']}}</option>
                    @endif
                    @endforeach
                </select>
            </div>
            <div class="form-group">
                <label for="message-text" class="col-form-label">Mensaje:</label>
                <textarea class="form-control" id="message-text" name="message-text" required ></textarea>
                <input type="hidden" minlenght="1" value="{{ Auth::user()->name }}" id="creator-message" name="creator-message">
            </div>
            <div class="form-group" >
                <label for="documento">Elija el documento que desea solicitar</label>
                <select class="form-control" name="documento" id="documento" required>
                    <option value="" selected>Seleccionar...</option>
                    <option value="ACTA">Acta de nacimiento</option>
                    <option value="CURP">CURP</option>
                    <option value="CERTIFICADO">Certificado de calificaciones</option>
                    <option value="RECIBO">Recibo de pago</option>
                </select>
            </div>
            <div class="form-group text-right">
                <button type="submit" class="btn btn-success">Enviar requisición</button>
            </div>
        </form>
</body>
</html>
<div class="modal fade bd-example-modal-lg" id="agregarAdmin" tabindex="-1" role="dialog"
  aria-labelledby="myExtraLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Registrar Administrativo</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="container">
        <div>
          <div class="container-registration">
            <form method="post" action="{{URL::to('/')}}/RegistrarAdmin">
            {{ csrf_field() }}
              <div class="row">
                <div class="col-md-6 mb-4">
                  <span>Nombre</span>
                  <span id="nombreCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca su nombre</span>
                  <input type="text" onkeyup="checkName(this)" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="firstName" name="firstName"  placeholder="Nombre" required>
                </div>
                <div class="col-md-6 mb-4">
                  <span>Apellido Paterno</span>
                  <span id="apellidoPCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca su apellido paterno</span>
                  <input type="text" onkeyup="checkLastName(this)" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="lastName" name="lastName" placeholder="Apellido paterno"  required>
                </div>
                <div class="col-md-6 mb-4">
                  <span>Apellido Materno</span>
                  <span id="apellidoMCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca su apellido materno</span>
                  <input type="text" onkeyup="checkSLastName(this)" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="secondLastName" name="secondLastName" placeholder="Apellido materno" required>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6 mb-4">
                  <label for="email">Correo </label>
                  <span id="correoCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca un correo valido</span>
                  <input type="text" onkeyup="checkEmail(this)" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="email" name="email" pattern="[A-Z,a-z,-,_,.]+@[a-z]+\.[a-z]+" placeholder="Tu@ejemplo.com"  required>
                </div>
                <div class="col-md-6 mb-4">
                  <span>Contraseña</span>
                  <span id="contadorPassword" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">mínimo 8 caracteres</span>
                  <input type="password"  onkeyup="contadorPassword(this)"  minlength="8" maxlength="20" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="password" name="password" placeholder="Tu contraseña" required>
                </div>
                <div class="col-md-3 mb-4">
                  <div>
                  <span>Teléfono</span>
                  <span id="contadorTelefono" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">10 restantes</span>
                  </div>
                  <input type="tel" onkeyup="contadorTelefono(this)" class="form-control shadow-sm p-3 mb-2 bg-white rounded" name="address" maxlength="10" id="address" placeholder="1234567890" required pattern="[0-9]+">
                </div>
              </div>
              <div class="modal-footer">
                <button class="btn btn-danger" type="button" data-dismiss="modal">Cancelar</button>
                <button class="btn btn-success" onClick="" type="submit">Registrar</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
@endsection