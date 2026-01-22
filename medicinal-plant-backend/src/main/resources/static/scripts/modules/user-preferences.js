// User Preferences Module
// Manages user data, favorites, history, and preferences

const UserPreferences = (() => {
    const state = {
        recentlyViewed: [],
        favoritePlants: [],
        searchHistory: [],
        comparedPlants: [],
        onboardingCompleted: false,
        theme: 'light',
        preferences: {
            fontSize: 'medium',
            highContrast: false,
            reducedMotion: false
        }
    };

    // Initialize from localStorage
    function init() {
        loadFromStorage();
    }

    // Load data from localStorage
    function loadFromStorage() {
        const saved = localStorage.getItem('userPreferences');
        if (saved) {
            try {
                const data = JSON.parse(saved);
                Object.assign(state, data);
            } catch (error) {
                console.error('Error loading user preferences:', error);
            }
        }

        // Check individual items for backward compatibility
        state.onboardingCompleted = localStorage.getItem('onboardingCompleted') === 'true';
        state.theme = localStorage.getItem('theme') || 'light';
        
        const savedFavorites = localStorage.getItem('favoritePlants');
        if (savedFavorites) {
            state.favoritePlants = JSON.parse(savedFavorites);
        }
    }

    // Save data to localStorage
    function saveToStorage() {
        localStorage.setItem('userPreferences', JSON.stringify(state));
        localStorage.setItem('onboardingCompleted', state.onboardingCompleted.toString());
        localStorage.setItem('theme', state.theme);
        localStorage.setItem('favoritePlants', JSON.stringify(state.favoritePlants));
    }

    // Add to recently viewed
    function addToRecentlyViewed(plantId) {
        if (!state.recentlyViewed.includes(plantId)) {
            state.recentlyViewed.push(plantId);
            if (state.recentlyViewed.length > 5) {
                state.recentlyViewed.shift();
            }
            saveToStorage();
        }
    }

    // Toggle favorite
    function toggleFavorite(plantId) {
        const index = state.favoritePlants.indexOf(plantId);
        if (index > -1) {
            state.favoritePlants.splice(index, 1);
        } else {
            state.favoritePlants.push(plantId);
        }
        saveToStorage();
        return index === -1; // Return true if added
    }

    // Check if plant is favorite
    function isFavorite(plantId) {
        return state.favoritePlants.includes(plantId);
    }

    // Add to search history
    function addToSearchHistory(query) {
        state.searchHistory.unshift({
            query,
            timestamp: Date.now()
        });
        if (state.searchHistory.length > 20) {
            state.searchHistory.pop();
        }
        saveToStorage();
    }

    // Add to comparison
    function addToComparison(plantId) {
        if (!state.comparedPlants.includes(plantId) && state.comparedPlants.length < 4) {
            state.comparedPlants.push(plantId);
            saveToStorage();
            return true;
        }
        return false;
    }

    // Remove from comparison
    function removeFromComparison(plantId) {
        const index = state.comparedPlants.indexOf(plantId);
        if (index > -1) {
            state.comparedPlants.splice(index, 1);
            saveToStorage();
        }
    }

    // Clear comparison
    function clearComparison() {
        state.comparedPlants = [];
        saveToStorage();
    }

    // Set onboarding completed
    function setOnboardingCompleted(completed = true) {
        state.onboardingCompleted = completed;
        saveToStorage();
    }

    // Set theme
    function setTheme(theme) {
        state.theme = theme;
        saveToStorage();
    }

    // Get all state
    function getState() {
        return { ...state };
    }

    // Get compared plants
    function getComparedPlants() {
        return [...state.comparedPlants];
    }

    // Get favorites
    function getFavorites() {
        return [...state.favoritePlants];
    }

    // Update state with multiple properties
    function updateState(updates) {
        Object.assign(state, updates);
        saveToStorage();
    }

    // Clear search history
    function clearSearchHistory() {
        state.searchHistory = [];
        saveToStorage();
    }

    return {
        init,
        addToRecentlyViewed,
        toggleFavorite,
        isFavorite,
        addToSearchHistory,
        addToComparison,
        removeFromComparison,
        clearComparison,
        setOnboardingCompleted,
        setTheme,
        getState,
        getComparedPlants,
        getFavorites,
        updateState,
        clearSearchHistory
    };
})();
