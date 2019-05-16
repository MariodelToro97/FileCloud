@extends('layouts.app')
@section('content')
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width" class=" table">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Inicio</title>
  <link rel="stylesheet" href="{{URL::to('/')}}/css/bootstrap.min.css">
  <link rel="stylesheet" href="{{URL::to('/')}}/css/bootstrap.css">
  <link rel="stylesheet" href="{{URL::to('/')}}/css/Style.css">
</head>

<body class="">
  <div class="presentation">
    <h1 class="font-weight-bolder">Bienvenido a FileCloud</h1>
    <div class="input-group flex-nowrap col-md-10 mb-6" id="Title">
      <div class="input-group-prepend">
        <span class="input-group-text" id="addon-wrapping"><img src="{{URL::to('/')}}/images/userMale.png" width="20"
            height="20"></span>
      </div>
      <input type="text" class="form-control " placeholder="Usuario elegido" aria-label="Username"
        aria-describedby="addon-wrapping" disabled>
        <form class="form-inline">
        <input class="form-control mr-sm-2" type="search" placeholder="Ingrese usuario" aria-label="Search">
        <button class="btn btn-outline-primary my-2 my-sm-0" type="submit">Buscar</button>
        </form>
    </div>
    <br>
  </div>
  <table class="table">
    <thead class="thead-dark">
      <tr>
        <th scope="col">Id</th>
        <th scope="col">Nombre documento</th>
        <th scope="col">Achivo</th>
        <th scope="col">Fecha de subida</th>
        <th scope="col">Acción</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th scope="row">1</th>
        <td>Acta de nacimiento</td>
        <td></td>
        <td></td>
        <td><a href="#">Descargar</a></td>
      </tr>
      <tr>
        <th scope="row">2</th>
        <td>CURP</td>
        <td>CURP.pdf</td>
        <td>20/11/2018</td>
        <td><a href="#">Descargar</a></td>
      </tr>
      <tr>
        <th scope="row">3</th>
        <td>Comprobante de domicilio</td>
        <td>compdomu.pdf</td>
        <td>23/03/2018</td>
        <td><a href="#">Descargar</a></td>
      </tr>
      <tr>
        <th scope="row">4</th>
        <td>Documento 1</td>
        <td>example.docx</td>
        <td>17/02/2018</td>
        <td><a href="#">Descargar</a></td>
      </tr>
      <tr>
        <th scope="row">5</th>
        <td>Documento 2</td>
        <td>example2.docx</td>
        <td>02/01/2018</td>
        <td><a href="#">Descargar</a></td>
      </tr>
    </tbody>
  </table>
    <script src="{{URL::to('/')}}/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="{{URL::to('/')}}/js/jquery-3.3.1.slim.min.js" type="text/javascript"></script>
    <script src="{{URL::to('/')}}/js/popper.min.js" type="text/javascript"></script>
</body>

<div class="modal fade" id="modalRequisicion" name="modalRequisicion" tabindex="-1" role="dialog"
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
            <input type="text" class="form-control shadow p-3 mb-5 bg-white rounded" id="user" name="user"required>
          </div>
          <div class="form-group">
            <label for="message-text" class="col-form-label">Mensaje:</label>
            <textarea class="form-control shadow p-3 mb-5 bg-white rounded" id="message-text" name="message-text" required ></textarea>
            <input type="hidden" value="{{ Auth::user()->name }}" id="creator-message" name="creator-message">
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
            <button type="submit" class="btn btn-success">Enviar requisición</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<div class="modal fade bd-example-modal-xl" id="agregarAdmin" tabindex="-1" role="dialog"
  aria-labelledby="myExtraLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-xl">
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
                <div class="col-md-4 mb-4">
                  <label for="firstName">Nombre</label>
                  <input type="text" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="firstName" name="firstName" placeholder="" required>
                </div>
                <div class="col-md-4 mb-4">
                  <label for="lastName">Apellido Paterno</label>
                  <input type="text" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="lastName" name="lastName" placeholder="" required>
                </div>
                <div class="col-md-4 mb-4">
                  <label for="secondLastName">Apellido Materno</label>
                  <input type="text" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="secondLastName" name="secondLastName" placeholder="" required>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6 mb-4">
                  <label for="username">Usuario</label>
                  <input type="text" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="username" name="username" placeholder="Tu usuario" required>
                </div>
                <div class="col-md-6 mb-4">
                  <label for="pasword">Contraseña</label>
                  <input type="password" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="password" name="password" placeholder="Tu contraseña" required>
                </div>
                <div class="col-md-3 mb-4">
                  <label for="email">Correo </label>
                  <input type="email" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="email" name="email" placeholder="Tu@ejemplo.com" required>
                </div>
                <div class="col-md-3 mb-4">
                  <label for="address">Número telefónico</label>
                  <input type="text" class="form-control shadow-sm p-3 mb-2 bg-white rounded" name="address" max="10" id="address" placeholder="1234567890" required>
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
