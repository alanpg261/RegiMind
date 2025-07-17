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

    // Event listeners para botones
    const viewUsersBtn = document.getElementById('view-users-btn');
    const addUserBtn = document.getElementById('add-user-btn');
    const viewPatentsBtn = document.getElementById('view-patents-btn');
    const addPatentBtn = document.getElementById('add-patent-btn');

    if (viewUsersBtn) {
        viewUsersBtn.addEventListener('click', function() {
            alert('Función de ver usuarios próximamente disponible');
        });
    }

    if (addUserBtn) {
        addUserBtn.addEventListener('click', function() {
            alert('Función de agregar usuario próximamente disponible');
        });
    }

    if (viewPatentsBtn) {
        viewPatentsBtn.addEventListener('click', function() {
            alert('Función de ver patentes próximamente disponible');
        });
    }

    if (addPatentBtn) {
        addPatentBtn.addEventListener('click', function() {
            alert('Función de agregar patente próximamente disponible');
        });
    }

    function loadStats() {
        // Simular carga de estadísticas
        setTimeout(() => {
            const totalUsers = document.getElementById('total-users');
            const totalPatentes = document.getElementById('total-patentes');
            const busquedasHoy = document.getElementById('busquedas-hoy');
            
            if (totalUsers) totalUsers.textContent = '25';
            if (totalPatentes) totalPatentes.textContent = '1,234';
            if (busquedasHoy) busquedasHoy.textContent = '156';
            
            // Cargar actividad reciente
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
                            <p class="text-xs text-gray-500">Hace 1 hora</p>
                        </div>
                    </div>
                `;
            }
        }, 1000);
    }
}); 