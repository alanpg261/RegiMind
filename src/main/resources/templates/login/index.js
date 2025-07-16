const abrir_modal_registro = document.getElementById("abrir-modal-registro");
const modal_registro = document.getElementById("registro-modal");
const cerrar_modal_registro = document.getElementById("cerrar-modal-registro");
const boton_registro = document.getElementById("boton-registro");


// lógica para abrir el modal de registro
abrir_modal_registro.addEventListener("click", ()=> {
    modal_registro.style.display = "flex";
});

cerrar_modal_registro.addEventListener("click", ()=> {
    modal_registro.style.display = "none";
});



//lógica para el registro de usuario
boton_registro.addEventListener("click", (e) => {
    e.preventDefault();
    const form = document.getElementById("register-form");

    // Construir el objeto con la estructura requerida
    const dataToSend = {
        tipoDocumento: form["tipodocumento"].value,
        noDocumento: form["Documento"].value,
        nombre: form["nombre"].value,
        tipoUsuario: "user",
        correo: form["email"].value,
        celular: form["celular"].value,
        fechaNacimiento: form["fechaNacimiento"].value,
        contrasena: form["password"].value
    };

    fetch("http://localhost:8081/api/usuarios/registro", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(dataToSend)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            localStorage.setItem("usuario", JSON.stringify(data.usuario || data));
            alert("Registro exitoso. Por favor, inicia sesión.");
            modal_registro.style.display = "none";
        } else {
            alert("Error en el registro: " + data.message);
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("Ocurrió un error al registrar el usuario.");
    });
});