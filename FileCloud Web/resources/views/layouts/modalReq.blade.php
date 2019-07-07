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
          <div class="form-group" >
          <label for="documento">Elija el documento que desea solicitar</label>
          <select class="form-control" name="documento" id="documento">
            <option value="0" selected>Seleccionar...</option>
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
</div>
</html>