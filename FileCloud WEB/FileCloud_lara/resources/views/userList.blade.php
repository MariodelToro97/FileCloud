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
</html>
@endsection