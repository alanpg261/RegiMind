document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const usuario = JSON.parse(localStorage.getItem('usuario') || '{}');
    const userWelcome = document.getElementById('user-welcome');
    const logoutBtn = document.getElementById('logout-btn');
    const searchForm = document.getElementById('search-form');
    const advancedSearchForm = document.getElementById('advanced-search-form');
    const clearAdvancedFormBtn = document.getElementById('clear-advanced-form');
    const resultsSection = document.getElementById('results-section');
    const loadingSection = document.getElementById('loading-section');
    const resultsContainer = document.getElementById('results-container');
    const resultsCount = document.getElementById('results-count');
    const prevPageBtn = document.getElementById('prev-page');
    const nextPageBtn = document.getElementById('next-page');
    const pageInfo = document.getElementById('page-info');

    // Estado de paginación
    let currentPage = 0;
    let totalPages = 0;
    let currentSearchParams = {};

    // Mostrar nombre del usuario
    if (usuario.nombre && userWelcome) {
        userWelcome.textContent = `Bienvenido, ${usuario.nombre}`;
    }

    // Logout
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function() {
            localStorage.removeItem('usuario');
            window.location.href = 'http://localhost:8080/login';
        });
    }

    // Mostrar/Ocultar loading
    function showLoading() {
        if (loadingSection) loadingSection.classList.remove('hidden');
        if (resultsSection) resultsSection.classList.add('hidden');
    }
    function hideLoading() {
        if (loadingSection) loadingSection.classList.add('hidden');
        if (resultsSection) resultsSection.classList.remove('hidden');
    }

    // Construir URL de búsqueda avanzada
    function buildSearchURL(endpoint, params) {
        const url = new URL(`http://localhost:8080/api/patentes/${endpoint}`);
        Object.keys(params).forEach(key => {
            if (params[key] !== undefined && params[key] !== null && params[key] !== '') {
                url.searchParams.append(key, params[key]);
            }
        });
        return url.toString();
    }

    // Mostrar resultados
    function displayResults(data) {
        if (!resultsContainer) return;
        const patentes = data.content || data;
        const totalElements = data.totalElements || patentes.length;
        totalPages = data.totalPages || 1;
        currentPage = data.number || 0;

        if (resultsCount) {
            resultsCount.textContent = `${totalElements} resultados encontrados`;
        }
        if (pageInfo && data.totalPages !== undefined) {
            pageInfo.textContent = `Página ${currentPage + 1} de ${totalPages}`;
        }
        if (prevPageBtn) {
            prevPageBtn.disabled = currentPage === 0;
        }
        if (nextPageBtn) {
            nextPageBtn.disabled = currentPage >= totalPages - 1;
        }
        if (!patentes || patentes.length === 0) {
            resultsContainer.innerHTML = '<p class="text-gray-500 text-center py-8">No se encontraron patentes que coincidan con tu búsqueda.</p>';
            return;
        }
        resultsContainer.innerHTML = patentes.map(patente => `
            <div class="border border-gray-200 rounded-lg p-6 shadow-md transition-shadow bg-white">
                <div class="flex justify-between items-start mb-3">
                    <h4 class="font-bold text-lg text-blue-600 mb-2 flex-1">${patente.titulo || 'Sin título'}</h4>
                    <span class="text-xs bg-blue-100 text-blue-800 px-2 py-1 rounded-full">${patente.estado || 'Sin estado'}</span>
                </div>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm text-gray-600">
                    <div>
                        <p><strong>Expediente:</strong> ${patente.expediente || 'No especificado'}</p>
                        <p><strong>Tipo:</strong> ${patente.tipoPatente || 'No especificado'}</p>
                        <p><strong>Fecha:</strong> ${patente.fecha ? new Date(patente.fecha).toLocaleDateString('es-ES') : 'No especificada'}</p>
                    </div>
                    <div>
                        <p><strong>Inventor:</strong> ${patente.inventor || 'No especificado'}</p>
                        <p><strong>Apoderado:</strong> ${patente.apoderado || 'No especificado'}</p>
                        <p><strong>CIP:</strong> ${patente.cip || 'No especificado'}</p>
                    </div>
                </div>
            </div>
        `).join('');
    }

    // Manejo de errores
    function handleError(error) {
        hideLoading();
        if (resultsContainer) {
            resultsContainer.innerHTML = `<div class="text-center py-8"><p class="text-red-500">Error al buscar patentes</p><p class="text-gray-600 mt-2">${error.message}</p></div>`;
        }
    }

    // Búsqueda simple
    if (searchForm) {
        searchForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const formData = new FormData(searchForm);
            const searchTerm = formData.get('searchTerm');
            const searchType = formData.get('searchType');
            const limit = formData.get('limit');
            if (!searchTerm.trim()) {
                alert('Por favor ingresa un término de búsqueda');
                return;
            }
            const searchParams = { page: 0, size: parseInt(limit) };
            if (searchType === 'titulo') searchParams.titulo = searchTerm;
            else if (searchType === 'expediente') searchParams.expediente = searchTerm;
            else if (searchType === 'inventor') searchParams.inventor = searchTerm;
            else if (searchType === 'cip') searchParams.cip = searchTerm;
            else if (searchType === 'tipoPatente') searchParams.tipoPatente = searchTerm;
            currentSearchParams = searchParams;
            currentPage = 0;
            showLoading();
            fetch(buildSearchURL('buscar-avanzada', searchParams))
                .then(response => {
                    if (!response.ok) throw new Error(`HTTP ${response.status}: ${response.statusText}`);
                    return response.json();
                })
                .then(data => { hideLoading(); displayResults(data); })
                .catch(handleError);
        });
    }

    // Búsqueda avanzada
    if (advancedSearchForm) {
        advancedSearchForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const formData = new FormData(advancedSearchForm);
            const searchParams = { page: 0, size: 25 };
            ['titulo','expediente','inventor','tipoPatente','estado','cip'].forEach(field => {
                const value = formData.get(field);
                if (value && value.trim() !== '') searchParams[field] = value.trim();
            });
            const hasValues = Object.keys(searchParams).some(key => !['page','size'].includes(key));
            if (!hasValues) {
                alert('Por favor ingresa al menos un criterio de búsqueda');
                return;
            }
            currentSearchParams = searchParams;
            currentPage = 0;
            showLoading();
            fetch(buildSearchURL('buscar-avanzada', searchParams))
                .then(response => {
                    if (!response.ok) throw new Error(`HTTP ${response.status}: ${response.statusText}`);
                    return response.json();
                })
                .then(data => { hideLoading(); displayResults(data); })
                .catch(handleError);
        });
    }

    // Limpiar formulario avanzado
    if (clearAdvancedFormBtn) {
        clearAdvancedFormBtn.addEventListener('click', function() {
            if (advancedSearchForm) advancedSearchForm.reset();
        });
    }

    // Navegación de páginas
    function navigatePage(direction) {
        if (direction === 'prev' && currentPage > 0) {
            currentPage--;
        } else if (direction === 'next' && currentPage < totalPages - 1) {
            currentPage++;
        } else {
            return;
        }
        const searchParams = { ...currentSearchParams, page: currentPage };
        showLoading();
        fetch(buildSearchURL('buscar-avanzada', searchParams))
            .then(response => {
                if (!response.ok) throw new Error(`HTTP ${response.status}: ${response.statusText}`);
                return response.json();
            })
            .then(data => { hideLoading(); displayResults(data); })
            .catch(handleError);
    }
    if (prevPageBtn) prevPageBtn.addEventListener('click', () => navigatePage('prev'));
    if (nextPageBtn) nextPageBtn.addEventListener('click', () => navigatePage('next'));
}); 