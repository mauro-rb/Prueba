$(document).ready(()=>{

    const validarMatricula = () => {
        $('#matricula').on('blur', function () {
            const matriculaInput = $(this);
            const matricula = matriculaInput.val();

            if (matricula.length < 10) {
                matriculaInput.addClass('invalid-input');
            } else {
                matriculaInput.removeClass('invalid-input');
            }
        });
    }

    const registrarOdontologo = () =>{
            $('#registrarOdontologo').on('click',function(){
                const odontologo = {
                    matricula: $('#matricula').val(),
                    nombre: $('#nombre').val(),
                    apellido: $('#apellido').val()
                }
                $.ajax({

                    url: "http://localhost:8081/odontologos/registrar",
                    contentType: "application/json",
                    type: "POST",
                    data: JSON.stringify(odontologo),
                    dataType: "json",
                    success: (data) => {
                        $('#mensajes-odontologos').html('Odontologo guardado').css('display','block')
                        listarOdontologos();
                        borrarFormulario();
                        console.log("Odontologo guardado");
                    }
                });
            })
        }

    const listarOdontologos = () =>{
        $.ajax({
            url: "http://localhost:8081/odontologos/listar",
            type: "GET",
            dataType: "json",
            success: function(res){
                let data = '';
                res.forEach(element =>{
                    data+=`
                    <tr odontologoId = ${element.id}>
                        <td>${element.id}</td>
                        <td>${element.matricula}</td>
                        <td>${element.nombre}</td>
                        <td>${element.apellido}</td>
                        <td>
                            <button id="btn-eliminar-odontologo" class="btn btn-danger">Eliminar</button>
                        </td>
                        <td>
                            <button id="btn-actualizar-odontologo" class="btn btn-primary">Actualizar</button>
                        </td>
                    </tr>
                    `
                });

                $('#tbody-odontologos').html(data);
            }
        });
    }

    const formularioConInfoOdontologo = () => {
        $(document).on('click', '#btn-actualizar-odontologo', function () {
            let btnEditar = $(this)[0].parentElement.parentElement;
            let id = $(btnEditar).attr('odontologoId');
            $('#registrarOdontologo').hide();
            $('#actualizarOdontologo').show();

            $.ajax({
                url: `http://localhost:8081/odontologos/listar/${id}`,
                type: "GET",
                dataType: "json",
                success: (res) => {
                    $('#idOdontologo').val(res.id);
                    $('#matricula').val(res.matricula);
                    $('#nombre').val(res.nombre);
                    $('#apellido').val(res.apellido);
                    // Scroll hacia arriba
                    $('html, body').animate({
                        scrollTop: 0
                    }, 'slow');
                }
            })
        })
    }

    const actualizarOdontologo = () => {
        $('#actualizarOdontologo').on('click',function(){
            let id = $('#idOdontologo').val();
            $('#registrarOdontologo').css('display','none');
            $('#actualizarOdontologo').css('display','block');


            const odontologoAModificar = {
                matricula: $('#matricula').val(),
                nombre: $('#nombre').val(),
                apellido: $('#apellido').val()
            }
            console.log(id);

            $.ajax({
                url: `http://localhost:8081/odontologos/actualizar/${id}`,
                contentType: "application/json",
                type: "PUT",
                data: JSON.stringify(odontologoAModificar),
                dataType: "json",
                success: (res) => {
                    $('#mensajes-odontologos').html('Odontologo actualizado').css('display','block');
                    $('#actualizarOdontologo').css('display','none');
                    $('#registrarOdontologo').css('display','block');
                    borrarFormulario();
                    listarOdontologos();
                }

            })
        })
    }

        const eliminarOdontologo = () => {
            $(document).on('click','#btn-eliminar-odontologo',function(){
                if(confirm('¿Estas seguro de eliminar el odontologo?')){
                    let btnEliminarOdontologo = $(this)[0].parentElement.parentElement;
                    let id = $(btnEliminarOdontologo).attr('odontologoId');
                    $.ajax({
                        url: `http://localhost:8081/odontologos/eliminar/${id}`,
                        type: "DELETE",
                        dataType: "json",
                        success: (res) => {
                            $('#mensajes-odontologos').html('Odontologo eliminado').css('display','block')
                            listarOdontologos();
                        }
                    })
                }
            })
        }


    const borrarFormulario = () =>{
        $('#matricula').val('');
        $('#nombre').val('');
        $('#apellido').val('');
    }

validarMatricula();
listarOdontologos();
registrarOdontologo();
formularioConInfoOdontologo();
actualizarOdontologo();
eliminarOdontologo();

})
