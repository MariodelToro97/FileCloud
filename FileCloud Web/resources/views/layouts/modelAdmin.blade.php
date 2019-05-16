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