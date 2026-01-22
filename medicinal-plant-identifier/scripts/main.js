// Main Application Script
// Initialize and coordinate all modules

(function() {
    'use strict';

    // Initialize application
    async function initApp() {
        console.log('🌿 Initializing MediPlant Application...');

        // Initialize modules in order
        UserPreferences.init();
        ThemeManager.init();
        UIManager.init();
        SearchEngine.init();

        // Load plants from backend API
        await loadPlantsFromBackend();

        console.log('✅ MediPlant Application initialized successfully!');
    }

    // Load plants from backend and display them
    async function loadPlantsFromBackend() {
        try {
            console.log('📡 Fetching plants from backend API...');
            
            // Fetch all plants from the backend
            const plants = await ApiClient.fetchAllPlants();
            
            if (plants && plants.length > 0) {
                console.log(`✅ Loaded ${plants.length} plants from backend`);
                
                // Display plants in featured section
                displayFeaturedPlants(plants);
                
                // Update plant database for search functionality
                updatePlantDatabase(plants);
            } else {
                console.warn('⚠️ No plants received from backend, using local data');
            }
        } catch (error) {
            console.error('❌ Failed to load plants from backend:', error);
            console.log('ℹ️ Falling back to local plant data');
        }
    }

    // Display featured plants in the UI
    function displayFeaturedPlants(plants) {
        const featuredSection = document.querySelector('.featured-plants');
        if (!featuredSection) return;

        // Clear existing plants
        featuredSection.innerHTML = '<h2 class="section-title">Featured Medicinal Plants</h2>';

        // Create grid container
        const grid = document.createElement('div');
        grid.className = 'plant-grid';

        // Display up to 6 plants
        const plantsToDisplay = plants.slice(0, 6);
        
        plantsToDisplay.forEach(plant => {
            const card = createPlantCard(plant);
            grid.appendChild(card);
        });

        featuredSection.appendChild(grid);
    }

    // Create a plant card element
    function createPlantCard(plant) {
        const card = document.createElement('div');
        card.className = 'plant-card';
        card.dataset.plantId = plant.id;

        const imageUrl = plant.imageUrl || 'https://via.placeholder.com/300x200?text=' + encodeURIComponent(plant.name);
        
        card.innerHTML = `
            <div class="plant-image">
                <img src="${imageUrl}" alt="${plant.name}" loading="lazy">
                <div class="plant-badge">${plant.safetyRating || 'Safe'}</div>
            </div>
            <div class="plant-content">
                <h3 class="plant-name">${plant.name}</h3>
                <p class="plant-scientific-name">${plant.scientificName || ''}</p>
                <p class="plant-description">${truncateText(plant.description, 100)}</p>
                <div class="plant-uses">
                    ${plant.medicinalUses ? plant.medicinalUses.slice(0, 3).map(use => 
                        `<span class="use-tag">${use}</span>`
                    ).join('') : ''}
                </div>
                <div class="plant-footer">
                    <div class="plant-rating">
                        <span class="stars">${generateStars(plant.rating || 0)}</span>
                        <span class="rating-count">(${plant.reviewCount || 0})</span>
                    </div>
                    <button class="btn-view-details" data-plant-id="${plant.id}">View Details</button>
                </div>
            </div>
        `;

        // Add click event for view details
        const viewBtn = card.querySelector('.btn-view-details');
        if (viewBtn) {
            viewBtn.addEventListener('click', () => showPlantDetails(plant.id));
        }

        return card;
    }

    // Show plant details modal
    async function showPlantDetails(plantId) {
        try {
            const plant = await ApiClient.fetchPlantById(plantId);
            
            // Create modal with plant details
            const modal = document.createElement('div');
            modal.className = 'modal plant-details-modal';
            modal.innerHTML = `
                <div class="modal-content">
                    <span class="modal-close">&times;</span>
                    <h2>${plant.name}</h2>
                    <p class="scientific-name">${plant.scientificName}</p>
                    <img src="${plant.imageUrl || 'https://via.placeholder.com/600x400'}" alt="${plant.name}">
                    <div class="plant-info">
                        <h3>Description</h3>
                        <p>${plant.description}</p>
                        
                        <h3>Medicinal Uses</h3>
                        <ul>
                            ${plant.medicinalUses ? plant.medicinalUses.map(use => `<li>${use}</li>`).join('') : '<li>No uses listed</li>'}
                        </ul>
                        
                        <h3>Active Compounds</h3>
                        <ul>
                            ${plant.activeCompounds ? plant.activeCompounds.map(compound => `<li>${compound}</li>`).join('') : '<li>None listed</li>'}
                        </ul>
                        
                        ${plant.precautions ? `
                            <h3>⚠️ Precautions</h3>
                            <p class="warning">${plant.precautions}</p>
                        ` : ''}
                        
                        ${plant.dosage ? `
                            <h3>Recommended Dosage</h3>
                            <p>${plant.dosage}</p>
                        ` : ''}
                    </div>
                </div>
            `;

            document.body.appendChild(modal);
            modal.style.display = 'block';

            // Close modal
            const closeBtn = modal.querySelector('.modal-close');
            closeBtn.addEventListener('click', () => {
                modal.remove();
            });

            modal.addEventListener('click', (e) => {
                if (e.target === modal) {
                    modal.remove();
                }
            });
        } catch (error) {
            ApiClient.showToast('Failed to load plant details', 'error');
        }
    }

    // Update plant database for local search
    function updatePlantDatabase(plants) {
        // This function can be used to update the local PlantDatabase
        // for offline search functionality
        if (window.PlantDatabase && typeof PlantDatabase.updatePlants === 'function') {
            PlantDatabase.updatePlants(plants);
        }
    }

    // Helper function to truncate text
    function truncateText(text, maxLength) {
        if (!text) return '';
        if (text.length <= maxLength) return text;
        return text.substring(0, maxLength) + '...';
    }

    // Helper function to generate star rating
    function generateStars(rating) {
        const fullStars = Math.floor(rating);
        const hasHalfStar = rating % 1 >= 0.5;
        let stars = '';
        
        for (let i = 0; i < fullStars; i++) {
            stars += '★';
        }
        if (hasHalfStar) {
            stars += '⯪';
        }
        const emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);
        for (let i = 0; i < emptyStars; i++) {
            stars += '☆';
        }
        
        return stars;
    }

    // Wait for DOM to be fully loaded
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initApp);
    } else {
        // DOM is already loaded
        initApp();
    }

    // Handle errors globally
    window.addEventListener('error', (event) => {
        console.error('Application Error:', event.error);
    });

    // Handle unhandled promise rejections
    window.addEventListener('unhandledrejection', (event) => {
        console.error('Unhandled Promise Rejection:', event.reason);
    });

    // Expose API for debugging (remove in production)
    if (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1') {
        window.MediPlantAPI = {
            PlantDatabase,
            UserPreferences,
            ThemeManager,
            SearchEngine,
            UIManager,
            Helpers,
            AnimationUtils
        };
        console.log('🔧 Debug API available at window.MediPlantAPI');
    }
})();
