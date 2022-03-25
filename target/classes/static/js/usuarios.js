// Call the dataTables jQuery plugin
$(document).ready(function() {
cargarUsuarios();
  $('#usuarios').DataTable();
  actualizarEmailUsuario();
});
function actualizarEmailUsuario(){

document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarUsuarios()
{
  const request = await fetch('api/usuarios/', {
    method: 'GET',
    headers: getHeaders()
  });
  const usuarios = await request.json();
  let listadoHTML = '';

  for( let usuario of usuarios){

    let botonEliminar ='<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle">  <i class="fas fa-trash"></i></a> ';
    let = telefono = usuario.telefono == null ? '-' : usuario.telefono;
    let usuarioHTML=' <tr> <td>'+usuario.id+'</td> <th>'+usuario.nombre+'</th> <th>'+usuario.email+'</th> <th>'+telefono+'</th>' +
                    ' <th> '+botonEliminar+' </th> </tr>';
    listadoHTML += usuarioHTML;
  }

document.querySelector('#usuarios tbody').outerHTML = listadoHTML;
  console.log("log-->"+usuarios);
}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Authorization': localStorage.token
   };
}


async function eliminarUsuario(id){

   if (!confirm('¿Desea eliminar este usuario?')) {
     return;
   }
   const request = await fetch('api/usuarios/'+id, {
     method: 'DELETE',
     headers:  getHeaders()
   });

   location.reload()
}