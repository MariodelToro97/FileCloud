@extends('layouts.app')
@section('content')
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Inicio</title>
  <link rel="stylesheet" href="{{URL::to('/')}}/css/bootstrap.min.css">
  <link rel="stylesheet" href="{{URL::to('/')}}/css/bootstrap.css">
  <link rel="stylesheet" href="{{URL::to('/')}}/css/Style.css">
  <script src="{{URL::to('/')}}/js/counters.js" type="text/javascript"></script>
</head>

<body class="">
  <div class="presentation">
    <h1 class="font-weight-bolder">Bienvenido a FileCloud</h1>
    @if(isset($mensaje))
    <div class="alert alert-primary alert-dismissible fade show" role="alert">
      <strong>{{$mensaje}}</strong>
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
      <span aria-hidden="true">&times;</span>
      </button>
    </div>
    @endif
    <div class="input-group flex-nowrap col-md-10 mb-6" id="Title">
      <div class="input-group-prepend">
        <span class="input-group-text" id="addon-wrapping"><img src="{{URL::to('/')}}/images/userMale.png" width="20"
            height="20"></span>
      </div>
      @if(isset($documentos))
      <input type="text" class="form-control " placeholder="Usuario elegido" aria-label="Username" aria-describedby="addon-wrapping" value="{{$documentos['usuario']}}"disabled>
      @else
      <input type="text" class="form-control " placeholder="Usuario elegido" aria-label="Username" aria-describedby="addon-wrapping" disabled>
      @endif
        <form class="form-inline" action="{{URL::to('/')}}/getDocuments" method="post">
        {{ csrf_field() }}
        <input class="form-control mr-sm-2" id="userSearch" name="userSearch" type="search" placeholder="Ingrese usuario" aria-label="Search" required>
        <button class="btn btn-outline-primary my-2 my-sm-0" type="submit">Buscar</button>
        </form>
        <br>
    </div>
    <br>
    <label for="btnlist">Lista de usuarios</label>
    <br>
    <a type="button" id="btnlist"href="{{URL::to('/')}}/listUser" class="btn btn-outline-primary">Lista</a>
  </div>
  <table class="table">
    <thead class="thead-dark">
      <tr>
        <th scope="col">Id</th>
        <th scope="col">Nombre documento</th>
        <th scope="col">Fecha de subida</th>
        <th scope="col">Acción</th>
      </tr>
    </thead>
    <tbody>
      @if(isset($documentos))
        <tr>
          <th scope="row">1</th>
          <td>Acta</td>
          @if($documentos['fecha1']!=null)
          <td>{{$documentos['fecha1']}}</td>
          <td><a target="_blank" href="{{$documentos['url1']}}">Descargar</a></td>
          @else
          <td>No disponible</td>
          <td>Descarga no disponible</td>
          @endif
        </tr>
        <tr>
          <th scope="row">2</th>
          <td>CURP</td>
          @if($documentos['fecha2']!=null)
          <td>{{$documentos['fecha2']}}</td>
          <td><a target="_blank" href="{{$documentos['url2']}}">Descargar</a></td>
          @else
          <td>No disponible</td>
          <td>Descarga no disponible</td>
          @endif
        </tr>
        <tr>
          <th scope="row">3</th>
          <td>Certificado de calificaciones</td>
          @if($documentos['fecha3']!=null)
          <td>{{$documentos['fecha3']}}</td>
          <td><a target="_blank" href="{{$documentos['url3']}}">Descargar</a></td>
          @else
          <td>No disponible</td>
          <td>Descarga no disponible</td>
          @endif
        </tr>
        <tr>
          <th scope="row">4</th>
          <td>Recibo de Pago</td>
          @if($documentos['fecha4']!=null)
          <td>{{$documentos['fecha4']}}</td>
          <td><a target="_blank" href="{{$documentos['url4']}}">Descargar</a></td>
          @else
          <td>No disponible</td>
          <td>Descarga no disponible</td>
          @endif
        </tr>
      @else
      <h1 class="text-center">¡No se ha elegido un usuario aún!.</h1>
      @endif
    </tbody>
  </table>
    <script src="{{URL::to('/')}}/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="{{URL::to('/')}}/js/jquery-3.3.1.slim.min.js" type="text/javascript"></script>
    <script src="{{URL::to('/')}}/js/popper.min.js" type="text/javascript"></script>
</body>



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
                  <input type="text" onkeyup="checkName(this)" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="firstName" name="firstName" pattern="[a-zA-ZÁÉÍÓÚáéíóúÑñ]+(\s*[a-zA-ZÁÉÍÓÚáéíóúÑñ])[a-zA-ZÁÉÍÓÚáéíóúÑñ]+" placeholder="Nombre" required>
                </div>
                <div class="col-md-6 mb-4">
                  <span>Apellido Paterno</span>
                  <span id="apellidoPCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca su apellido paterno</span>
                  <input type="text" onkeyup="checkLastName(this)" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="lastName" name="lastName" pattern="[a-zA-ZÁÉÍÓÚáéíóúÑñ]+(\s*[a-zA-ZÁÉÍÓÚáéíóúÑñ])[a-zA-ZÁÉÍÓÚáéíóúÑñ]+" placeholder="Apellido paterno"  required>
                </div>
                <div class="col-md-6 mb-4">
                  <span>Apellido Materno</span>
                  <span id="apellidoMCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca su apellido materno</span>
                  <input type="text" onkeyup="checkSLastName(this)" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="secondLastName" name="secondLastName" pattern="[a-zA-ZÁÉÍÓÚáéíóúÑñ]+(\s*[a-zA-ZÁÉÍÓÚáéíóúÑñ])[a-zA-ZÁÉÍÓÚáéíóúÑñ]+" placeholder="Apellido materno" required>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6 mb-4">
                  <label for="email">Correo </label>
                  <span id="correoCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca un correo valido</span>
                  <input type="text" onkeyup="checkEmail(this)" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="email" name="email" pattern="[A-Z,a-z,-,_,.,0-9]+@[a-z]+\.[a-z]+" placeholder="Tu@ejemplo.com"  required>
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

<!-- <div class="modal fade" id="modalRequisicion" name="modalRequisicion" tabindex="-1" role="dialog"
  aria-labelledby="modalRequisicionLabel" aria-hidden="true">
  <div class="modal-dialog" role="modal">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalRequisicionLabel">Nueva requisición</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="{{URL::to('/')}}/Requisicion">
          {{ csrf_field() }}
          <div class="form-group">
            <label for="user" class="col-form-label">Usuario</label>
            <input type="text" class="form-control shadow p-3 mb-5 bg-white rounded" id="user" name="user" required>
          </div>
          <div class="form-group">
            <label for="message-text" class="col-form-label">Mensaje:</label>
            <textarea class="form-control shadow p-3 mb-5 bg-white rounded" id="message-text" name="message-text" required ></textarea>
            <input type="hidden" minlenght="1" value="{{ Auth::user()->name }}" id="creator-message" name="creator-message">
          </div>
          <div class="form-group" >
          <label for="documento">Elija el documento que desea solicitar</label>
          <select class="form-control" name="documento" id="documento" required>
            <option value="default" selected>Seleccionar...</option>
            <option value="ACTA">Acta de nacimiento</option>
            <option value="CURP">CURP</option>
            <option value="CERTIFICADO">Certificado de calificaciones</option>
            <option value="RECIBO">Recibo de pago</option>
          </select>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
            <button type="submit" class="btn btn-success">Enviar requisición</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div> -->
</html>
@endsection

@section('scripts')
<script>
$(document).ready(function (){
alert("pagina cargada");
});
</script>
@endsection

