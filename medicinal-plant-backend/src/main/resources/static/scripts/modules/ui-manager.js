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

        // Settings modal
        setupSettingsModal();
        
        // Review form
        setupReviewForm();
    }

    // Setup review form
    function setupReviewForm() {
        const reviewForm = document.getElementById('review-form');
        if (!reviewForm) return;

        // Load existing reviews on page load
        loadReviews();

        reviewForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const reviewerName = document.getElementById('reviewer-name').value;
            const reviewerEmail = document.getElementById('reviewer-email').value;
            const reviewRating = document.getElementById('review-rating').value;
            const reviewText = document.getElementById('review-text').value;
            
            if (!reviewerName || !reviewerEmail || !reviewRating || !reviewText) {
                showToast('Please fill in all fields', 'error');
                return;
            }
            
            try {
                // Submit to backend
                const response = await fetch('http://localhost:8080/api/reviews', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        userName: reviewerName,
                        userEmail: reviewerEmail,
                        rating: parseInt(reviewRating),
                        experience: reviewText
                    })
                });

                if (!response.ok) {
                    throw new Error('Failed to submit review');
                }

                const savedReview = await response.json();
                
                // Add to UI
                addReviewToUI(savedReview);
                
                // Reset form
                reviewForm.reset();
                
                // Show success message
                showToast('Thank you for sharing your experience!', 'success');
                
            } catch (error) {
                console.error('❌ Error submitting review:', error);
                showToast('Failed to submit review. Please try again.', 'error');
            }
        });
    }

    // Load reviews from backend
    async function loadReviews() {
        try {
            const response = await fetch('http://localhost:8080/api/reviews');
            if (!response.ok) {
                throw new Error('Failed to load reviews');
            }
            
            const reviews = await response.json();
            const reviewsContainer = document.getElementById('reviews-container');
            
            if (!reviewsContainer) return;
            
            // Clear existing reviews (except the form)
            const existingReviews = reviewsContainer.querySelectorAll('.review');
            existingReviews.forEach(review => review.remove());
            
            // Add each review to UI
            reviews.forEach(review => addReviewToUI(review, false));
            
            console.log(`✅ Loaded ${reviews.length} reviews`);
        } catch (error) {
            console.error('❌ Error loading reviews:', error);
        }
    }

    // Add review to UI
    function addReviewToUI(review, animate = true) {
        const reviewsContainer = document.getElementById('reviews-container');
        if (!reviewsContainer) return;
        
        const newReview = document.createElement('div');
        newReview.className = 'review';
        if (animate) {
            newReview.style.animation = 'fadeInUp 0.5s ease-out';
        }
        
        const stars = '⭐'.repeat(review.rating);
        
        newReview.innerHTML = `
            <div class="review-header">
                <div class="user-info">
                    <span class="user-name">${review.userName}</span>
                    <span class="user-email">${review.userEmail || ''}</span>
                </div>
                <span class="review-date">${review.timeAgo}</span>
            </div>
            <div class="review-rating">${stars}</div>
            <p class="review-text">${review.experience}</p>
        `;
        
        // Insert at the beginning
        reviewsContainer.insertBefore(newReview, reviewsContainer.firstChild);
        
        if (animate) {
            // Scroll to the new review
            newReview.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
    }

    // Setup settings modal
    function setupSettingsModal() {
        const settingsBtn = document.getElementById('settings-btn');
        const settingsModal = document.getElementById('settings-modal');
        const closeSettingsModal = document.getElementById('close-settings-modal');
        const cancelSettings = document.getElementById('cancel-settings');
        const saveSettings = document.getElementById('save-settings');
        const clearHistoryBtn = document.getElementById('clear-history-btn');
        const resetSettingsBtn = document.getElementById('reset-settings-btn');

        if (!settingsBtn || !settingsModal) return;

        // Open settings modal
        settingsBtn.addEventListener('click', () => {
            loadSettings();
            settingsModal.classList.add('active');
        });

        // Close settings modal
        const closeModal = () => {
            settingsModal.classList.remove('active');
        };

        if (closeSettingsModal) {
            closeSettingsModal.addEventListener('click', closeModal);
        }

        if (cancelSettings) {
            cancelSettings.addEventListener('click', closeModal);
        }

        // Close on outside click
        settingsModal.addEventListener('click', (e) => {
            if (e.target === settingsModal) {
                closeModal();
            }
        });

        // Save settings
        if (saveSettings) {
            saveSettings.addEventListener('click', () => {
                saveUserSettings();
                closeModal();
                showToast('Settings saved successfully!', 'success');
            });
        }

        // Clear history
        if (clearHistoryBtn) {
            clearHistoryBtn.addEventListener('click', () => {
                if (confirm('Are you sure you want to clear your search history?')) {
                    UserPreferences.clearSearchHistory();
                    showToast('Search history cleared', 'info');
                }
            });
        }

        // Reset settings
        if (resetSettingsBtn) {
            resetSettingsBtn.addEventListener('click', () => {
                if (confirm('Are you sure you want to reset all settings to default?')) {
                    resetToDefaults();
                    loadSettings();
                    showToast('Settings reset to defaults', 'info');
                }
            });
        }
    }

    // Load settings from UserPreferences
    function loadSettings() {
        const state = UserPreferences.getState();

        // Appearance settings
        const themeSelect = document.getElementById('theme-select');
        if (themeSelect) {
            themeSelect.value = state.theme || 'light';
        }

        const fontSizeSelect = document.getElementById('font-size-select');
        if (fontSizeSelect) {
            fontSizeSelect.value = state.fontSize || 'medium';
        }

        // Search preferences
        const autoSearch = document.getElementById('auto-search');
        if (autoSearch) {
            autoSearch.checked = state.autoSearch !== false;
        }

        const showScientificNames = document.getElementById('show-scientific-names');
        if (showScientificNames) {
            showScientificNames.checked = state.showScientificNames !== false;
        }

        const resultsPerPage = document.getElementById('results-per-page');
        if (resultsPerPage) {
            resultsPerPage.value = state.resultsPerPage || '12';
        }

        // Notifications
        const enableNotifications = document.getElementById('enable-notifications');
        if (enableNotifications) {
            enableNotifications.checked = state.enableNotifications !== false;
        }

        const enableSounds = document.getElementById('enable-sounds');
        if (enableSounds) {
            enableSounds.checked = state.enableSounds !== false;
        }

        // Data & Privacy
        const saveSearchHistory = document.getElementById('save-search-history');
        if (saveSearchHistory) {
            saveSearchHistory.checked = state.saveSearchHistory !== false;
        }
    }

    // Save user settings
    function saveUserSettings() {
        const themeSelect = document.getElementById('theme-select');
        const fontSizeSelect = document.getElementById('font-size-select');
        const autoSearch = document.getElementById('auto-search');
        const showScientificNames = document.getElementById('show-scientific-names');
        const resultsPerPage = document.getElementById('results-per-page');
        const enableNotifications = document.getElementById('enable-notifications');
        const enableSounds = document.getElementById('enable-sounds');
        const saveSearchHistory = document.getElementById('save-search-history');

        // Save theme
        if (themeSelect) {
            const theme = themeSelect.value;
            UserPreferences.updateState({ theme });
            ThemeManager.setTheme(theme);
        }

        // Save font size
        if (fontSizeSelect) {
            const fontSize = fontSizeSelect.value;
            UserPreferences.updateState({ fontSize });
            applyFontSize(fontSize);
        }

        // Save search preferences
        if (autoSearch) {
            UserPreferences.updateState({ autoSearch: autoSearch.checked });
        }

        if (showScientificNames) {
            UserPreferences.updateState({ showScientificNames: showScientificNames.checked });
        }

        if (resultsPerPage) {
            UserPreferences.updateState({ resultsPerPage: parseInt(resultsPerPage.value) });
        }

        // Save notifications
        if (enableNotifications) {
            UserPreferences.updateState({ enableNotifications: enableNotifications.checked });
        }

        if (enableSounds) {
            UserPreferences.updateState({ enableSounds: enableSounds.checked });
        }

        // Save data privacy
        if (saveSearchHistory) {
            UserPreferences.updateState({ saveSearchHistory: saveSearchHistory.checked });
        }
    }

    // Apply font size to document
    function applyFontSize(size) {
        const root = document.documentElement;
        switch(size) {
            case 'small':
                root.style.fontSize = '14px';
                break;
            case 'large':
                root.style.fontSize = '18px';
                break;
            default:
                root.style.fontSize = '16px';
        }
    }

    // Reset settings to defaults
    function resetToDefaults() {
        UserPreferences.updateState({
            theme: 'light',
            fontSize: 'medium',
            autoSearch: true,
            showScientificNames: true,
            resultsPerPage: 12,
            enableNotifications: true,
            enableSounds: true,
            saveSearchHistory: true
        });
        
        ThemeManager.setTheme('light');
        applyFontSize('medium');
    }

    // Show toast notification
    function showToast(message, type = 'info') {
        const state = UserPreferences.getState();
        if (!state.enableNotifications) return;

        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        toast.textContent = message;
        
        const toastContainer = document.getElementById('toast-container');
        if (toastContainer) {
            toastContainer.appendChild(toast);
            
            setTimeout(() => {
                toast.classList.add('show');
            }, 100);
            
            setTimeout(() => {
                toast.classList.remove('show');
                setTimeout(() => {
                    if (toastContainer.contains(toast)) {
                        toastContainer.removeChild(toast);
                    }
                }, 300);
            }, 3000);
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
        const usesTags = uses.slice(0, 3).map(use => `<span class="use-tag">${use}</span>`).join('');
        const isFavorited = UserPreferences.isFavorite(plant.id);
        
        // Get emoji from plant data or use default
        const plantEmoji = plant.imageEmoji || plant.emoji || '🌿';
        
        // Handle different name formats (name vs commonName)
        const plantName = plant.name || plant.commonName || 'Unknown Plant';

        card.innerHTML = `
            <div class="plant-emoji">${plantEmoji}</div>
            <div class="plant-info">
                <h3 class="plant-name">${plantName}</h3>
                <p class="plant-scientific">${plant.scientificName || plant.scientific || ''}</p>
            </div>
            <div class="plant-uses-footer">
                ${usesTags || '<span class="use-tag">No uses listed</span>'}
            </div>
        `;

        // Make card clickable to show details
        card.addEventListener('click', () => {
            showPlantDetails(plant);
        });

        return card;
    }

    // Show plant details in modal
    function showPlantDetails(plant) {
        const modal = document.createElement('div');
        modal.className = 'plant-modal';
        
        // Handle different name formats
        const plantName = plant.name || plant.commonName || 'Unknown Plant';
        const warning = plant.warning || plant.precautions || 'Consult a healthcare professional before use';
        
        modal.innerHTML = `
            <div class="modal-content">
                <span class="close-modal">&times;</span>
                <div class="plant-emoji-large">${plant.imageEmoji || plant.emoji || '🌿'}</div>
                <h2>${plantName}</h2>
                <p class="scientific-name">${plant.scientificName || ''}</p>
                <div class="plant-details">
                    <h3>Medicinal Uses</h3>
                    <div class="uses-list">
                        ${(plant.medicinalUses || []).map(use => `<span class="use-tag">${use}</span>`).join('')}
                    </div>
                    <h3>Description</h3>
                    <p>${plant.description || 'No description available'}</p>
                    <h3>Dosage</h3>
                    <p>${plant.dosage || 'Consult healthcare provider'}</p>
                    <h3>Warning</h3>
                    <p class="warning-text">${warning}</p>
                </div>
            </div>
        `;
        document.body.appendChild(modal);
        
        modal.querySelector('.close-modal').addEventListener('click', () => {
            document.body.removeChild(modal);
        });
        
        modal.addEventListener('click', (e) => {
            if (e.target === modal) {
                document.body.removeChild(modal);
            }
        });
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

