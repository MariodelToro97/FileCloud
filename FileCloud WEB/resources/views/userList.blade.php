@extends('layouts.app')
@section('content')
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Lista de usuarios</title>
    <link rel="stylesheet" href="{{URL::to('/')}}/css/bootstrap.min.css">
  <link rel="stylesheet" href="{{URL::to('/')}}/css/bootstrap.css">
  <link rel="stylesheet" href="{{URL::to('/')}}/css/Style.css">
  <script src="{{URL::to('/')}}/js/counters.js" type="text/javascript"></script>
</head>
<body>
<table class="table">
  <thead class="thead-dark">
    <tr>
      <th scope="col">Usuario</th>
      <th scope="col">Nombre </th>
      <th scope="col">Correo</th>
      <th scope="col">Fecha de Registro</th>
      <th scope="col">Acción</th>
    </tr>
  </thead>
  <tbody>
        @if(isset($users))
            @foreach($users as $user)
                @if($user['tipoUsuario']==1 || $user['tipoUsuario']==2)
                    @if($user['cuentaVerificada']==1)
                        <tr>
                            <td>{{$user['Usuario']}}</td>
                            <td>{{$user['Nombre']}}</td>
                            <td>{{$user['Correo']}}</td>
                            <td>{{$user['fechaRegistro']}}</td>
                            <td>
                            <form action="{{URL::to('/')}}/getDocuments" method="post">
                                {{ csrf_field() }}
                                <input  id="userSearch" name="userSearch" type="hidden" value="{{$user['Usuario']}}">
                                <button type="submit" class="btn btn-success">Ver documentos</button>
                            </form>
                            </td>
                        </tr>
                    @endif
                @endif
            @endforeach
        @else
            <h1 class="text-center">No se han encontrado nuevos alumnos</h1>
        @endif
    </tbody>
</table>
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
                  <span id="correoCheck" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">Introduzca un correo válido</span>
                  <input type="text" onkeyup="checkEmail(this)" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="email" name="email" pattern="[A-Z,a-z,-,_,.,0-9]+@[a-z]+\.[a-z]+" placeholder="Tu@ejemplo.com"  required>
                </div>
                <div class="col-md-6 mb-4">
                  <span>Contraseña</span>
                  <span id="contadorPassword" style="font-size: 12px; float: right;" class="text-success my-2 font-weight-bold">mínimo 8 caracteres</span>
                  <input type="password"  onkeyup="contadorPassword(this)"  minlength="8" maxlength="20" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="password" pattern="[0-9a-zA-Z]+" name="password" placeholder="Tu contraseña" required>
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
</html>
@endsection