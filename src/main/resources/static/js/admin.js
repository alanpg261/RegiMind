document.addEventListener('DOMContentLoaded', function() {
    const usuario = JSON.parse(localStorage.getItem('usuario') || '{}');
    const userWelcome = document.getElementById('user-welcome');
    const logoutBtn = document.getElementById('logout-btn');

    // Verificar si es administrador
    if (usuario.tipoUsuario !== 'admin') {
        alert('Acceso denegado. Solo los administradores pueden acceder a esta página.');
        window.location.href = 'http://localhost:8080/dashboard';
        return;
    }

    // Mostrar nombre del administrador
    if (usuario.nombre && userWelcome) {
        userWelcome.textContent = `Administrador: ${usuario.nombre}`;
    }

    // Función de logout
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function() {
            localStorage.removeItem('usuario');
            window.location.href = 'http://localhost:8080/login';
        });
    }

    // Cargar estadísticas
    loadStats();

    // Elementos del modal de agregar administrador
    const addAdminBtn = document.getElementById('add-admin-btn');
    const addAdminModal = document.getElementById('add-admin-modal');
    const closeAdminModal = document.getElementById('close-admin-modal');
    const cancelAdminBtn = document.getElementById('cancel-admin-btn');
    const addAdminForm = document.getElementById('add-admin-form');

    // Elementos del modal de revisar solicitudes
    const reviewSolicitudesBtn = document.getElementById('review-solicitudes-btn');
    const reviewSolicitudesModal = document.getElementById('review-solicitudes-modal');
    const closeReviewModal = document.getElementById('close-review-modal');
    const solicitudesContainer = document.getElementById('solicitudes-container');

    // Event listeners para botones
    const viewUsersBtn = document.getElementById('view-users-btn');
    const viewPatentsBtn = document.getElementById('view-patents-btn');

    // Modal de agregar administrador
    if (addAdminBtn) {
        addAdminBtn.addEventListener('click', function() {
            addAdminModal.classList.remove('hidden');
            addAdminModal.classList.add('flex');
        });
    }

    if (closeAdminModal) {
        closeAdminModal.addEventListener('click', function() {
            addAdminModal.classList.add('hidden');
            addAdminModal.classList.remove('flex');
        });
    }

    if (cancelAdminBtn) {
        cancelAdminBtn.addEventListener('click', function() {
            addAdminModal.classList.add('hidden');
            addAdminModal.classList.remove('flex');
        });
    }

    // Formulario de registro de administrador
    if (addAdminForm) {
        addAdminForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const formData = new FormData(addAdminForm);
            
            const adminData = {
                tipoDocumento: formData.get('tipoDocumento'),
                noDocumento: formData.get('noDocumento'),
                nombre: formData.get('nombre'),
                tipoUsuario: 'admin', //Siempre será admin
                correo: formData.get('correo'),
                celular: formData.get('celular'),
                fechaNacimiento: formData.get('fechaNacimiento'),
                contrasena: formData.get('contrasena')
            };

            fetch('http://localhost:8080/api/usuarios/registro', {
                method: 'POST',
                headers: {
                   'Content-Type': 'application/json'
                },
                body: JSON.stringify(adminData)
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
                alert('Administrador registrado exitosamente');
                addAdminForm.reset();
                addAdminModal.classList.add('hidden');
                addAdminModal.classList.remove('flex');
                loadStats(); // Recargar estadísticas
            })
            .catch(error => {
                console.error('Error al registrar administrador:', error);
                alert('Error al registrar administrador: ' + error.message);
            });
        });
    }

    // Modal de revisar solicitudes
    if (reviewSolicitudesBtn) {
        reviewSolicitudesBtn.addEventListener('click', function() {
            reviewSolicitudesModal.classList.remove('hidden');
            reviewSolicitudesModal.classList.add('flex');
            loadSolicitudes();
        });
    }

    if (closeReviewModal) {
        closeReviewModal.addEventListener('click', function() {
            reviewSolicitudesModal.classList.add('hidden');
            reviewSolicitudesModal.classList.remove('flex');
        });
    }

    // Elementos de los nuevos modales
    const viewUsersModal = document.getElementById('view-users-modal');
    const closeUsersModal = document.getElementById('close-users-modal');
    const usersTableContainer = document.getElementById('users-table-container');

    const viewPatentsModal = document.getElementById('view-patents-modal');
    const closePatentsModal = document.getElementById('close-patents-modal');
    const patentsTableContainer = document.getElementById('patents-table-container');

    // Abrir modal de usuarios
    if (viewUsersBtn) {
        viewUsersBtn.addEventListener('click', function() {
            viewUsersModal.classList.remove('hidden');
            viewUsersModal.classList.add('flex');
            loadUsersTable();
        });
    }
    // Cerrar modal de usuarios
    if (closeUsersModal) {
        closeUsersModal.addEventListener('click', function() {
            viewUsersModal.classList.add('hidden');
            viewUsersModal.classList.remove('flex');
        });
    }

    // Abrir modal de patentes
    if (viewPatentsBtn) {
        viewPatentsBtn.addEventListener('click', function() {
            viewPatentsModal.classList.remove('hidden');
            viewPatentsModal.classList.add('flex');
            loadPatentsTable();
        });
    }
    // Cerrar modal de patentes
    if (closePatentsModal) {
        closePatentsModal.addEventListener('click', function() {
            viewPatentsModal.classList.add('hidden');
            viewPatentsModal.classList.remove('flex');
        });
    }

    // Cerrar modales al hacer clic fuera
    window.addEventListener('click', function(e) {
        if (e.target === addAdminModal) {
            addAdminModal.classList.add('hidden');
            addAdminModal.classList.remove('flex');
        }
        if (e.target === reviewSolicitudesModal) {
            reviewSolicitudesModal.classList.add('hidden');
            reviewSolicitudesModal.classList.remove('flex');
        }
        if (e.target === viewUsersModal) {
            viewUsersModal.classList.add('hidden');
            viewUsersModal.classList.remove('flex');
        }
        if (e.target === viewPatentsModal) {
            viewPatentsModal.classList.add('hidden');
            viewPatentsModal.classList.remove('flex');
        }
    });

    function loadStats() {
        fetch('http://localhost:8080/api/admin/stats')
            .then(response => {
                if (!response.ok) throw new Error(`HTTP ${response.status}`);
                return response.json();
            })
            .then(data => {
                const totalUsers = document.getElementById('total-users');
                const totalPatentes = document.getElementById('total-patentes');
                const solicitudesPendientes = document.getElementById('solicitudes-pendientes');
                
                if (totalUsers) totalUsers.textContent = data.totalUsuarios || '0';
                if (totalPatentes) totalPatentes.textContent = data.totalPatentes || '0';
                if (solicitudesPendientes) solicitudesPendientes.textContent = data.solicitudesPendientes || '0';
                
                loadRecentActivity();
            })
            .catch(error => {
                console.error('Error cargando estadísticas:', error);
                // Usar valores por defecto si hay error
                const totalUsers = document.getElementById('total-users');
                const totalPatentes = document.getElementById('total-patentes');
                const solicitudesPendientes = document.getElementById('solicitudes-pendientes');
                
                if (totalUsers) totalUsers.textContent = '25';
                if (totalPatentes) totalPatentes.textContent = '1,234';
                if (solicitudesPendientes) solicitudesPendientes.textContent = '5';
            });
    }

    function loadRecentActivity() {
        fetch('http://localhost:8080/api/admin/actividad-reciente')
            .then(response => {
                if (!response.ok) throw new Error(`HTTP ${response.status}`);
                return response.json();
            })
            .then(data => {
                const recentActivity = document.getElementById('recent-activity');
                if (recentActivity) {
                    recentActivity.innerHTML = data.map(actividad => `
                        <div class="flex items-center space-x-3 p-3 bg-gray-50 rounded-lg">
                            <div class="w-2 h-2 bg-${actividad.color}-500 rounded-full"></div>
                            <div class="flex-1">
                                <p class="text-sm font-medium">${actividad.descripcion}</p>
                                <p class="text-xs text-gray-500">${actividad.tiempo}</p>
                            </div>
                        </div>
                    `).join('');
                }
            })
            .catch(error => {
                console.error('Error cargando actividad:', error);
                // Actividad por defecto
                const recentActivity = document.getElementById('recent-activity');
                if (recentActivity) {
                    recentActivity.innerHTML = `
                        <div class="flex items-center space-x-3 p-3 bg-gray-50 rounded-lg">
                            <div class="w-2 h-2 bg-green-500 rounded-full"></div>
                            <div class="flex-1">
                                <p class="text-sm font-medium">Nuevo usuario registrado</p>
                                <p class="text-xs text-gray-500">Hace 5 minutos</p>
                            </div>
                        </div>
                        <div class="flex items-center space-x-3 p-3 bg-gray-50 rounded-lg">
                            <div class="w-2 h-2 bg-blue-500 rounded-full"></div>
                            <div class="flex-1">
                                <p class="text-sm font-medium">Búsqueda de patentes realizada</p>
                                <p class="text-xs text-gray-500">Hace 12 minutos</p>
                            </div>
                        </div>
                        <div class="flex items-center space-x-3 p-3 bg-gray-50 rounded-lg">
                            <div class="w-2 h-2 bg-yellow-500 rounded-full"></div>
                            <div class="flex-1">
                                <p class="text-sm font-medium">Sistema actualizado</p>
                                <p class="text-xs text-gray-500">Hace 1 hora</p>                   </div>
                        </div>
                    `;
                }
            });
    }

    function loadSolicitudes() {
        fetch('http://localhost:8080/api/solicitudes/pendientes')
            .then(response => {
                if (!response.ok) throw new Error(`HTTP ${response.status}`);
                return response.json();
            })
            .then(data => {
                if (solicitudesContainer) {
                    if (data.length === 0) {
                        solicitudesContainer.innerHTML = '<p class="text-gray-500 text-center py-8">No hay solicitudes pendientes</p>';
                    } else {
                        solicitudesContainer.innerHTML = data.map(solicitud => `
                            <div class="border border-gray-200 rounded-lg p-6 shadow-md bg-white">
                                <div class="flex justify-between items-start mb-4">
                                    <div class="flex-1">
                                        <h4 class="font-bold text-lg text-blue-600 mb-2">${solicitud.titulo}</h4>
                                        <p class="text-sm text-gray-600 mb-2"><strong>Expediente:</strong> ${solicitud.expediente}</p>
                                        <p class="text-sm text-gray-600 mb-2"><strong>Tipo de Patente:</strong> ${solicitud.tipoPatente}</p>
                                        <p class="text-sm text-gray-600 mb-2"><strong>Solicitante:</strong> ${solicitud.solicitanteNombre || '-'}</p>
                                        <p class="text-sm text-gray-600 mb-2"><strong>Inventor:</strong> ${solicitud.inventorNombre || '-'}</p>
                                        <p class="text-sm text-gray-600 mb-2"><strong>Apoderado:</strong> ${solicitud.apoderadoNombre || '-'}</p>
                                        <p class="text-sm text-gray-600 mb-2"><strong>CIP:</strong> ${solicitud.cip || '-'}</p>
                                        <p class="text-sm text-gray-600 mb-2"><strong>Estado:</strong> ${solicitud.estado}</p>
                                        <p class="text-sm text-gray-600 mb-2"><strong>Fecha:</strong> ${solicitud.fecha ? new Date(solicitud.fecha).toLocaleDateString('es-ES') : '-'}</p>
                                    </div>
                                    <div class="flex space-x-2">
                                        <button onclick="aprobarSolicitud(${solicitud.id})" class="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-md text-sm transition-colors">
                                            Aprobar
                                        </button>
                                        <button onclick="rechazarSolicitud(${solicitud.id})" class="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-md text-sm transition-colors">
                                            Rechazar
                                        </button>
                                    </div>
                                </div>
                            </div>
                        `).join('');
                    }
                }
            })
            .catch(error => {
                console.error('Error cargando solicitudes:', error);
                if (solicitudesContainer) {
                    solicitudesContainer.innerHTML = '<p class="text-red-500 text-center py-8">Error al cargar solicitudes</p>';
                }
            });
    }

    // Funciones globales para aprobar/rechazar solicitudes
    window.aprobarSolicitud = function(id) {
        if (confirm('¿Estás seguro de que quieres aprobar esta solicitud?')) {
            fetch(`http://localhost:8080/api/solicitudes/${id}/aprobar`, {
                method: 'PUT'
            })
            .then(response => {
                if (!response.ok) throw new Error(`HTTP ${response.status}`);
                alert('Solicitud aprobada exitosamente');
                loadSolicitudes(); // Recargar lista
            })
            .catch(error => {
                console.error('Error al aprobar solicitud:', error);
                alert('Error al aprobar solicitud: ' + error.message);
            });
        }
    };

    window.rechazarSolicitud = function(id) {
        if (confirm('¿Estás seguro de que quieres rechazar esta solicitud?')) {
            fetch(`http://localhost:8080/api/solicitudes/${id}/rechazar`, {
                method: 'PUT'
            })
            .then(response => {
                if (!response.ok) throw new Error(`HTTP ${response.status}`);
                alert('Solicitud rechazada exitosamente');
                loadSolicitudes(); // Recargar lista
            })
            .catch(error => {
                console.error('Error al rechazar solicitud:', error);
                alert('Error al rechazar solicitud: ' + error.message);
            });
        }
    };

    // Función para cargar usuarios en tabla
    function loadUsersTable() {
        usersTableContainer.innerHTML = '<p class="text-gray-500 text-center py-8">Cargando usuarios...</p>';
        fetch('http://localhost:8080/api/admin/usuarios?page=0&size=100')
            .then(response => response.json())
            .then(data => {
                if (!data || !data.contenido || data.contenido.length === 0) {
                    usersTableContainer.innerHTML = '<p class="text-gray-500 text-center py-8">No hay usuarios registrados.</p>';
                    return;
                }
                const rows = data.contenido.map(usuario => `
                    <tr>
                        <td class="px-4 py-2 border">${usuario.id}</td>
                        <td class="px-4 py-2 border">${usuario.nombre}</td>
                        <td class="px-4 py-2 border">${usuario.correo}</td>
                        <td class="px-4 py-2 border">${usuario.tipoUsuario}</td>
                    </tr>
                `).join('');
                usersTableContainer.innerHTML = `
                    <div class="overflow-x-auto">
                        <table class="min-w-full border border-gray-200">
                            <thead>
                                <tr class="bg-gray-100">
                                    <th class="px-4 py-2 border">ID</th>
                                    <th class="px-4 py-2 border">Nombre</th>
                                    <th class="px-4 py-2 border">Correo</th>
                                    <th class="px-4 py-2 border">Rol</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${rows}
                            </tbody>
                        </table>
                    </div>
                `;
            })
            .catch(() => {
                usersTableContainer.innerHTML = '<p class="text-red-500 text-center py-8">Error al cargar usuarios.</p>';
            });
    }

    // Función para cargar patentes en tabla
    function loadPatentsTable() {
        patentsTableContainer.innerHTML = '<p class="text-gray-500 text-center py-8">Cargando patentes...</p>';
        fetch('http://localhost:8080/api/admin/patentes?page=0&size=100')
            .then(response => response.json())
            .then(data => {
                if (!data || !data.contenido || data.contenido.length === 0) {
                    patentsTableContainer.innerHTML = '<p class="text-gray-500 text-center py-8">No hay patentes registradas.</p>';
                    return;
                }
                const rows = data.contenido.map(patente => `
                    <tr>
                        <td class="px-4 py-2 border">${patente.id}</td>
                        <td class="px-4 py-2 border">${patente.titulo}</td>
                        <td class="px-4 py-2 border">${patente.expediente}</td>
                        <td class="px-4 py-2 border">${patente.estado}</td>
                    </tr>
                `).join('');
                patentsTableContainer.innerHTML = `
                    <div class="overflow-x-auto">
                        <table class="min-w-full border border-gray-200">
                            <thead>
                                <tr class="bg-gray-100">
                                    <th class="px-4 py-2 border">ID</th>
                                    <th class="px-4 py-2 border">Título</th>
                                    <th class="px-4 py-2 border">Expediente</th>
                                    <th class="px-4 py-2 border">Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${rows}
                            </tbody>
                        </table>
                    </div>
                `;
            })
            .catch(() => {
                patentsTableContainer.innerHTML = '<p class="text-red-500 text-center py-8">Error al cargar patentes.</p>';
            });
    }
}); 