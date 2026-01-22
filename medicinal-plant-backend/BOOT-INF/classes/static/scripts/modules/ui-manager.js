// UI Manager Module
// Manages UI updates and interactions

const UIManager = (() => {
    const elements = {
        searchHint: null,
        resultsSection: null,
        resultsContainer: null,
        resultsCount: null,
        noResults: null,
        comparisonTool: null,
        comparisonGrid: null,
        onboardingSlider: null
    };

    // Initialize UI Manager
    function init() {
        cacheElements();
        setupEventListeners();
        setupOnboarding();
        setupFeaturedPlants();
    }

    // Cache DOM elements
    function cacheElements() {
        elements.searchHint = document.getElementById('search-hint');
        elements.resultsSection = document.getElementById('results');
        elements.resultsContainer = document.getElementById('results-container');
        elements.resultsCount = document.getElementById('results-count');
        elements.noResults = document.getElementById('no-results');
        elements.comparisonTool = document.getElementById('comparison-tool');
        elements.comparisonGrid = document.getElementById('comparison-grid');
        elements.onboardingSlider = document.getElementById('onboarding');
    }

    // Setup event listeners
    function setupEventListeners() {
        // Comparison tool
        const compareBtn = document.getElementById('compare-btn');
        if (compareBtn) {
            compareBtn.addEventListener('click', toggleComparisonTool);
        }

        const clearComparisonBtn = document.getElementById('clear-comparison');
        if (clearComparisonBtn) {
            clearComparisonBtn.addEventListener('click', clearComparison);
        }
    }

    // Setup onboarding
    function setupOnboarding() {
        if (!elements.onboardingSlider) return;

        const userState = UserPreferences.getState();
        if (userState.onboardingCompleted) {
            elements.onboardingSlider.style.display = 'none';
            return;
        }

        const slides = elements.onboardingSlider.querySelectorAll('.slide');
        const dots = elements.onboardingSlider.querySelectorAll('.slider-dot');
        const skipBtn = elements.onboardingSlider.querySelector('.skip-onboarding');
        let currentSlide = 0;

        function showSlide(index) {
            for (const slide of slides) {
                slide.classList.remove('active');
            }
            for (const dot of dots) {
                dot.classList.remove('active');
            }
            
            if (slides[index]) slides[index].classList.add('active');
            if (dots[index]) dots[index].classList.add('active');
            currentSlide = index;
        }

        for (let index = 0; index < dots.length; index++) {
            const dot = dots[index];
            dot.addEventListener('click', () => showSlide(index));
        }

        if (skipBtn) {
            skipBtn.addEventListener('click', () => {
                elements.onboardingSlider.style.display = 'none';
                UserPreferences.setOnboardingCompleted(true);
            });
        }

        // Auto-advance slides
        setInterval(() => {
            let nextSlide = (currentSlide + 1) % slides.length;
            showSlide(nextSlide);
        }, 5000);
    }

    // Setup featured plants
    function setupFeaturedPlants() {
        for (const preview of document.querySelectorAll('.plant-preview')) {
            preview.addEventListener('click', () => {
                const plantId = preview.dataset.plant;
                displayPlantResult(plantId);
            });
        }
    }

    // Display search results
    function displaySearchResults(results) {
        if (!elements.resultsContainer || !elements.resultsSection) return;

        // Hide search hint and show results section
        if (elements.searchHint) elements.searchHint.style.display = 'none';
        elements.resultsSection.classList.add('active');

        // Show skeleton loading
        showSkeletonLoading();

        // Simulate loading delay
        setTimeout(() => {
            elements.resultsContainer.innerHTML = '';

            if (results.length === 0) {
                if (elements.noResults) elements.noResults.style.display = 'block';
                if (elements.resultsCount) elements.resultsCount.textContent = '0 plants found';
                return;
            }

            if (elements.noResults) elements.noResults.style.display = 'none';
            if (elements.resultsCount) {
                elements.resultsCount.textContent = `${results.length} plant${results.length !== 1 ? 's' : ''} found`;
            }

            let index = 0;
            for (const plant of results) {
                const plantCard = createPlantCard(plant);
                plantCard.style.animationDelay = `${index * 0.1}s`;
                elements.resultsContainer.appendChild(plantCard);
                index++;
            }

            // Scroll to results
            elements.resultsSection.scrollIntoView({ behavior: 'smooth' });
        }, 800);
    }

    // Display specific plant
    function displayPlantResult(plantId) {
        const plant = PlantDatabase.getPlantById(plantId);
        if (!plant) return;

        if (elements.searchHint) elements.searchHint.style.display = 'none';
        if (elements.resultsSection) elements.resultsSection.classList.add('active');

        if (elements.resultsContainer) {
            elements.resultsContainer.innerHTML = '';
            const plantCard = createPlantCard({ id: plantId, ...plant });
            elements.resultsContainer.appendChild(plantCard);
        }

        if (elements.resultsCount) {
            elements.resultsCount.textContent = '1 plant found';
        }
        if (elements.noResults) {
            elements.noResults.style.display = 'none';
        }

        if (elements.resultsSection) {
            elements.resultsSection.scrollIntoView({ behavior: 'smooth' });
        }

        UserPreferences.addToRecentlyViewed(plantId);
    }

    // Create plant card
    function createPlantCard(plant) {
        const card = document.createElement('div');
        card.className = 'plant-card plant-card-enhanced';
        card.dataset.plant = plant.id;

        // Handle both backend format (medicinalUses) and old format (uses)
        const uses = plant.medicinalUses || plant.uses || [];
        const usesTags = uses.map(use => `<span class="use-tag">${use}</span>`).join('');
        const starRating = plant.rating ? '⭐'.repeat(Math.floor(plant.rating)) + (plant.rating % 1 >= 0.5 ? '½' : '') : '';
        const isFavorited = UserPreferences.isFavorite(plant.id);
        
        // Handle both imageUrl (backend) and image (old format)
        const imageUrl = plant.imageUrl || plant.image;
        const hasRealImage = imageUrl && !imageUrl.includes('placeholder');
        
        // Plant emoji mapping
        const plantEmojis = {
            'Aloe Vera': '🌵',
            'Ginger': '🫚',
            'Turmeric': '🟡',
            'Lavender': '💜',
            'Peppermint': '🌿',
            'Chamomile': '🌼',
            'Echinacea': '🌸',
            'Garlic': '🧄',
            'Holy Basil': '🌿',
            'Neem': '🌳'
        };
        const plantEmoji = plantEmojis[plant.name] || '🌿';
        
        // Handle safety rating format
        const safetyText = plant.safetyRating === 'SAFE' ? 'Generally Safe' : 
                          plant.safetyRating === 'GENERALLY_SAFE' ? 'Generally Safe' :
                          plant.safety === 'safe' ? 'Generally Safe' : 'Use Caution';
        const safetyCss = (plant.safetyRating || plant.safety || '').toLowerCase();
        
        // Handle availability format
        const availability = plant.availability || 'unknown';
        const availabilityText = availability.charAt(0).toUpperCase() + availability.slice(1).toLowerCase();

        card.innerHTML = `
            <div class="plant-header">
                ${hasRealImage ? 
                    `<img src="${imageUrl}" alt="${plant.name}" class="plant-image">` :
                    `<div class="plant-image" style="display: flex; align-items: center; justify-content: center; height: 200px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; font-size: 4rem; border-radius: 8px 8px 0 0;">${plantEmoji}</div>`}
                <div class="plant-badges">
                    <span class="safety-badge ${safetyCss}">${safetyText}</span>
                    <span class="availability-badge ${availability.toLowerCase()}">${availabilityText}</span>
                </div>
            </div>
            <div class="plant-info">
                <h3 class="plant-name">${plant.name}</h3>
                <div class="plant-meta">
                    <span class="plant-scientific">${plant.scientificName || plant.scientific || ''}</span>
                    <div class="rating">
                        ${starRating} <span class="review-count">(${plant.reviewCount || plant.reviews || 0})</span>
                    </div>
                </div>
                <p class="plant-description">${plant.description || ''}</p>
                
                <div class="quick-actions">
                    <button class="action-btn favorite ${isFavorited ? 'active' : ''}" title="Add to favorites" data-plant-id="${plant.id}">
                        <i class="${isFavorited ? 'fas' : 'far'} fa-heart"></i>
                    </button>
                    <button class="action-btn share" title="Share">
                        <i class="fas fa-share-alt"></i>
                    </button>
                    <button class="action-btn compare" title="Add to comparison" data-plant-id="${plant.id}">
                        <i class="fas fa-balance-scale"></i>
                    </button>
                </div>
                
                <div class="plant-uses">
                    <h4>Medicinal Uses:</h4>
                    ${usesTags || '<p>No uses listed</p>'}
                </div>
                
                <div class="precautions">
                    <h4>Precautions:</h4>
                    <p>${plant.precautions || 'Consult a healthcare professional before use.'}</p>
                </div>
            </div>
        `;

        // Add event listeners
        const favoriteBtn = card.querySelector('.action-btn.favorite');
        const compareBtn = card.querySelector('.action-btn.compare');

        if (favoriteBtn) {
            favoriteBtn.addEventListener('click', (e) => {
                e.stopPropagation();
                toggleFavorite(favoriteBtn, plant.id);
            });
        }

        if (compareBtn) {
            compareBtn.addEventListener('click', (e) => {
                e.stopPropagation();
                addToComparison(plant.id);
            });
        }

        return card;
    }

    // Show skeleton loading
    function showSkeletonLoading() {
        if (!elements.resultsContainer) return;

        elements.resultsContainer.innerHTML = '';
        
        for (let i = 0; i < 3; i++) {
            const skeletonCard = document.createElement('div');
            skeletonCard.className = 'skeleton-card';
            skeletonCard.innerHTML = `
                <div class="skeleton skeleton-image"></div>
                <div class="skeleton-content">
                    <div class="skeleton-line short"></div>
                    <div class="skeleton-line medium"></div>
                    <div class="skeleton-line"></div>
                    <div class="skeleton-line"></div>
                    <div class="skeleton-line short"></div>
                </div>
            `;
            elements.resultsContainer.appendChild(skeletonCard);
        }
    }

    // Toggle favorite
    function toggleFavorite(button, plantId) {
        UserPreferences.toggleFavorite(plantId);
        button.classList.toggle('active');
        
        const icon = button.querySelector('i');
        if (icon) {
            icon.classList.toggle('far');
            icon.classList.toggle('fas');
        }
    }

    // Add to comparison
    function addToComparison(plantId) {
        const added = UserPreferences.addToComparison(plantId);
        if (added) {
            updateComparisonTool();
            if (elements.comparisonTool) {
                elements.comparisonTool.classList.add('active');
            }
        }
    }

    // Update comparison tool
    function updateComparisonTool() {
        if (!elements.comparisonGrid) return;

        elements.comparisonGrid.innerHTML = '';
        const comparedPlants = UserPreferences.getComparedPlants();

        comparedPlants.forEach(plantId => {
            const plant = PlantDatabase.getPlantById(plantId);
            if (!plant) return;

            const comparisonCard = document.createElement('div');
            comparisonCard.className = 'comparison-card';
            comparisonCard.innerHTML = `
                <img src="${plant.image}" alt="${plant.name}">
                <h4>${plant.name}</h4>
                <p>${plant.uses.slice(0, 2).join(', ')}</p>
                <button class="action-btn remove-from-comparison" data-plant="${plantId}">
                    <i class="fas fa-times"></i> Remove
                </button>
            `;
            elements.comparisonGrid.appendChild(comparisonCard);

            const removeBtn = comparisonCard.querySelector('.remove-from-comparison');
            if (removeBtn) {
                removeBtn.addEventListener('click', () => {
                    UserPreferences.removeFromComparison(plantId);
                    updateComparisonTool();
                    
                    if (UserPreferences.getComparedPlants().length === 0) {
                        if (elements.comparisonTool) {
                            elements.comparisonTool.classList.remove('active');
                        }
                    }
                });
            }
        });
    }

    // Show error message
    function showError(message) {
        showToast(message, 'error');
    }

    // Show success message
    function showSuccess(message) {
        showToast(message, 'success');
    }

    // Show toast notification
    function showToast(message, type = 'info') {
        // Remove any existing toasts
        const existingToast = document.querySelector('.toast-notification');
        if (existingToast) {
            existingToast.remove();
        }

        // Create toast element
        const toast = document.createElement('div');
        toast.className = `toast-notification toast-${type}`;
        
        const icon = type === 'error' ? '❌' : type === 'success' ? '✅' : 'ℹ️';
        toast.innerHTML = `
            <span class="toast-icon">${icon}</span>
            <span class="toast-message">${message}</span>
            <button class="toast-close">&times;</button>
        `;
        
        document.body.appendChild(toast);
        
        // Trigger animation
        setTimeout(() => toast.classList.add('show'), 10);
        
        // Close button handler
        const closeBtn = toast.querySelector('.toast-close');
        closeBtn.addEventListener('click', () => {
            toast.classList.remove('show');
            setTimeout(() => toast.remove(), 300);
        });
        
        // Auto-remove after 5 seconds
        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => toast.remove(), 300);
        }, 5000);
    }

    // Toggle comparison tool
    function toggleComparisonTool() {
        if (elements.comparisonTool) {
            elements.comparisonTool.classList.toggle('active');
        }
    }

    // Clear comparison
    function clearComparison() {
        UserPreferences.clearComparison();
        if (elements.comparisonTool) {
            elements.comparisonTool.classList.remove('active');
        }
        updateComparisonTool();
    }

    return {
        init,
        displaySearchResults,
        displayPlantResult,
        updateComparisonTool,
        showError,
        showSuccess,
        showToast
    };
})();
