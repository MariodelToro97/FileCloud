@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header text-center">{{ __('Iniciar Sesión') }}</div>
                <div class="card-body">
                <div class="text-center">
                <img src="{{URL::to('/')}}/images/logo-Filecloud.png" alt="" width="90" height="90">
                </div>
                    <form method="POST" action="{{ route('login') }}">
                        @csrf

                        <div class="text-center">
                            <label for="email" class="">{{ __('Correo') }}</label>

                            <div class="text-center">
                                <input id="email" type="email" class="form-control{{ $errors->has('email') ? ' is-invalid' : '' }}" name="email" value="{{ old('email') }}" required autocomplete="email" autofocus>

                                @if ($errors->has('email'))
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $errors->first('email') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <div class="text-center">
                            <!-- <label for="password" class="col-md-4 col-form-label text-md-right">{{ __('Contraseña') }}</label> -->
                                <label for="password" class="">{{ __('Contraseña') }}</label>
                                <input id="password" type="password" class="form-control{{ $errors->has('password') ? ' is-invalid' : '' }}" name="password" required autocomplete="current-password">

                                @if ($errors->has('password'))
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $errors->first('password') }}</strong>
                                    </span>
                                @endif
        
                        </div>

                        <div class="form-group row">
                        </div>

                        <div class="text-center">
                            <div class="text-center">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    {{ __('Ingresar') }}
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
