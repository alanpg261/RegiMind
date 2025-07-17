document.addEventListener('DOMContentLoaded', function() {
    const abrir_modal_registro = document.getElementById("abrir-modal-registro");
    const modal_registro = document.getElementById("registro-modal");
    const cerrar_modal_registro = document.getElementById("cerrar-modal-registro");
    const boton_registro = document.getElementById("boton-registro");
    const loginForm = document.getElementById("login-form");

    // lógica para abrir el modal de registro
    if (abrir_modal_registro) {
        abrir_modal_registro.addEventListener("click", ()=> {
            modal_registro.style.display = "flex";
        });
    }

    if (cerrar_modal_registro) {
        cerrar_modal_registro.addEventListener("click", ()=> {
            modal_registro.style.display = "none";
        });
    }

    //lógica para el registro de usuario
    if (boton_registro) {
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

            fetch("http://localhost:8080/api/usuarios/registro", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dataToSend)
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text || `HTTP ${response.status}`);
                    });
                }
                return response.json();
            })
            .then(data => {
                console.log("Respuesta del servidor:", data);
                alert("Registro exitoso. Por favor, inicia sesión.");
                modal_registro.style.display = "none";
                form.reset();
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Error en el registro: " + error.message);
            });
        });
    }

    // Lógica para el login
    if (loginForm) {
        loginForm.addEventListener("submit", (e) => {
            e.preventDefault();
            
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;
            
            const loginData = {
                correo: email,
                contrasena: password
            };
            
            fetch("http://localhost:8080/api/usuarios/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(loginData)
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text || `HTTP ${response.status}`);
                    });
                }
                return response.json();
            })
            .then(data => {
                console.log("Login exitoso:", data);
                localStorage.setItem("usuario", JSON.stringify(data));
                // Redirigir al dashboard según el tipo de usuario
                if (data.tipoUsuario === "admin") {
                    window.location.href = "http://localhost:8080/admin/dashboard";
                } else {
                    window.location.href = "http://localhost:8080/dashboard";
                }
            })
            .catch(error => {
                console.error("Error en login:", error);
                alert("Error en el login: " + error.message);
            });
        });
    }
}); 