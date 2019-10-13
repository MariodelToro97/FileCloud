@extends('layouts.app')
@section('content')
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="{{URL::to('/')}}/css/bootstrap.min.css">
  <title>Requisiciones</title>
</head>
<body>
  <h1 class="font-weight-bolder text-center">Requisiciones hechas</h1>
  <div class="text-center">
    <a  type="button" href="{{URL::to('/home')}}" class="btn btn-outline-dark btn-lg" data-toggle="tooltip" data-placement="bottom" title="Regresar" >Regresar al inicio</a>
  </div>
  <div class="card text-center shadow-lg p-3 mb-10 bg-white rounded">
    <div class="card-header">
      Requisición
    </div>
    <div class="card-body">
      <h5 class="card-title">Requisición</h5>
      <p class="card-text">cuerpo: blblablablablbalbalbalbalblablablalablb</p>
      <a href="#modalVerReq" data-toggle="modal" data-target="#modalVerReq" class="btn btn-primary">Ver detalles...</a>
    </div>
    <div class="card-footer text-muted">
        hace dos días
    </div>
    <div class="card text-center">
      <div class="card-header">
          Requisición
      </div>
      <div class="card-body">
        <h5 class="card-title">Requisición</h5>
        <p class="card-text">cuerpo: blblablablablbalbalbalbalblablablalablb</p>
        <a href="#" class="btn btn-primary">Ver detalles...</a>
      </div>
      <div class="card-footer text-muted">
        hace dos días
      </div>
      <div class="card text-center">
        <div class="card-header">
            Requisición
        </div>
        <div class="card-body">
          <h5 class="card-title">Requisición</h5>
          <p class="card-text">cuerpo: blblablablablbalbalbalbalblablablalablb</p>
          <a href="#" class="btn btn-primary">Ver detalles...</a>
        </div>
        <div class="card-footer text-muted">
            hace dos días
        </div>
      </div>
</body>
<script src="{{URL::to('/')}}/js/bootstrap.min.js" type="text/javascript"></script>
<script src="{{URL::to('/')}}/js/jquery-3.3.1.slim.min.js" type="text/javascript"></script>
<script src="{{URL::to('/')}}/js/popper.min.js" type="text/javascript"></script>

<div class="modal fade bd-example-modal-lg" id="modalVerReq" tabindex="-1" role="dialog"
  aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="modal">
    <div class="modal-content">
      <div class="modal-body">
        <div class="modal-header">
          <h5 class="modal-title" id="modalVerReqLabel">Detalles</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>

        <div class="form-group">
          <label for="numReqq" class="col-form-label">Requisición</label>
          <input class="form-control shadow p-3 mb-5 bg-white rounded" type="text" name="numReq" id="numReq" readonly>
        </div>


        <div class="form-group">
          <label for="user" class="col-form-label">Usuario</label>
          <input class="form-control shadow p-3 mb-5 bg-white rounded" type="text" name="user" id="user" readonly>
        </div>


        <div class="form-group">
          <label for="body" class="col-form-label">Cuerpo</label>
          <textarea class="form-control shadow p-3 mb-5 bg-white rounded" id="message-text" readonly></textarea>
        </div>


        <div class="form-group">
          <label for="date" class="col-form-label">Fecha</label>
          <input class="form-control shadow p-3 mb-5 bg-white rounded" type="text" name="date" id="date" readonly>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
        </div>
        
      </div>
    </div>
  </div>
</div>
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
        <form>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Usuario</label>
            <input type="text" class="form-control shadow p-3 mb-5 bg-white rounded" id="recipient-name" required>
          </div>
          <div class="form-group">
            <label for="message-text" class="col-form-label">Mensaje:</label>
            <textarea class="form-control shadow p-3 mb-5 bg-white rounded" id="message-text" required ></textarea>
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
            <form class=""">
              <div class="row">
                <div class="col-md-4 mb-4">
                  <label for="firstName">Nombre</label>
                  <input type="text" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="firstName" placeholder="" required>
                </div>
                <div class="col-md-4 mb-4">
                  <label for="lastName">Apellido Paterno</label>
                  <input type="text" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="lastName" placeholder="" required>
                </div>
                <div class="col-md-4 mb-4">
                  <label for="lastName">Apellido Materno</label>
                  <input type="text" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="secondLastName" placeholder="" required>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6 mb-4">
                  <label for="username">Usuario</label>
                  <input type="text" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="username" placeholder="Tu usuario" required>
                </div>
                <div class="col-md-6 mb-4">
                  <label for="username">Contraseña</label>
                  <input type="text" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="username" placeholder="Tu contraseña" required>
                </div>
                <div class="col-md-3 mb-4">
                  <label for="email">Correo </label>
                  <input type="email" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="email" placeholder="Tu@ejemplo.com" required>
                </div>
                <div class="col-md-3 mb-4">
                  <label for="address">Número telefónico</label>
                  <input type="text" class="form-control shadow-sm p-3 mb-2 bg-white rounded" id="address" max="10" placeholder="1234567890" required>
                </div>
              </div>
              <div class="modal-footer">
                <button class="btn btn-danger" type="button" data-dismiss="modal">Cancelar</button>
                <button class="btn btn-success" type="submit">Registrar</button>
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