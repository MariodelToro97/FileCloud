$('#formAceptar').submit(function() {
		$.ajax({
			type: 'POST',
			url: '/updateUser',
			data: $('#formAceptar').serialize(),
			success: function(data) {				
				if (data == 'La inserción se completó satisfactoriamente') {
					alertify.success(data);
					$('#tablaUsers').load(" #tablaUsers");
				} else {
					alertify.error("No se pudieron insertar los datos");
				}
			},
			error: function(r) {
				alertify.error(r);
			}
		});
		return false;
});

$('#formRechazar').submit(function() {
		$.ajax({
			type: 'POST',
			url: '/deleteUser',
			data: $('#formRechazar').serialize(),
			success: function(data) {				
				if (data == 'La inserción se completó satisfactoriamente') {
					alertify.success(data);
					$('#tablaUsers').load(" #tablaUsers");
				} else {
					alertify.error("No se pudieron insertar los datos");
				}
			},
			error: function(r) {
				alertify.error(r);
			}
		});
		return false;
});