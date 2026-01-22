// API Client Module
// Handles all communication with the backend Spring Boot API

// Automatically detect API base URL based on environment
const API_BASE_URL = (() => {
    // Use config if available
    if (typeof window !== 'undefined' && window.APP_CONFIG) {
        return window.APP_CONFIG.api.baseUrl;
    }
    
    // Fallback: Auto-detect based on hostname
    if (typeof window !== 'undefined') {
        const hostname = window.location.hostname;
        
        // Production: Use environment variable or default to a placeholder
        if (hostname !== 'localhost' && hostname !== '127.0.0.1') {
            // TODO: Replace with your actual backend URL after deploying
            console.warn('⚠️ Backend API not configured. Using mock data.');
            return null; // Will use local data
        }
    }
    
    // Development: Use local backend
    return 'http://localhost:8080/api';
})();

console.log('🌐 API Base URL:', API_BASE_URL || 'Using local mock data');

/**
 * API Client for Medicinal Plant Backend
 */
const ApiClient = (() => {
    'use strict';

    // Helper function to get auth token
    const getAuthToken = () => {
        return localStorage.getItem('authToken');
    };

    // Helper function to build headers
    const buildHeaders = (includeAuth = false, contentType = 'application/json') => {
        const headers = {};
        
        if (contentType) {
            headers['Content-Type'] = contentType;
        }
        
        if (includeAuth) {
            const token = getAuthToken();
            if (token) {
                headers['Authorization'] = `Bearer ${token}`;
            }
        }
        
        return headers;
    };

    // Generic fetch wrapper with error handling
    const fetchWithErrorHandling = async (url, options = {}) => {
        try {
            console.log('🌐 API Request:', url, options);
            if (typeof showLoadingSpinner === 'function') showLoadingSpinner();
            
            const response = await fetch(url, options);
            console.log('📡 API Response Status:', response.status, response.statusText);
            
            // Handle non-OK responses
            if (!response.ok) {
                const errorData = await response.json().catch(() => ({}));
                console.error('❌ API Error Response:', errorData);
                throw new Error(errorData.error || errorData.message || `HTTP ${response.status}: ${response.statusText}`);
            }
            
            const data = await response.json();
            console.log('✅ API Response Data:', data);
            if (typeof hideLoadingSpinner === 'function') hideLoadingSpinner();
            
            return data;
        } catch (error) {
            if (typeof hideLoadingSpinner === 'function') hideLoadingSpinner();
            console.error('💥 API Error:', error);
            
            // Only show toast if function exists
            if (typeof showToast === 'function') {
                showToast(error.message || 'Backend not available. Using local data.', 'warning');
            }
            throw error;
        }
    };

    // ============================================
    // PLANT ENDPOINTS
    // ============================================

    /**
     * Fetch all medicinal plants
     * GET /api/plants/all
     */
    const fetchAllPlants = async () => {
        // If no API configured, return empty (will use local data)
        if (!API_BASE_URL) {
            console.log('📦 Using local plant data (no backend configured)');
            return [];
        }
        
        try {
            const response = await fetchWithErrorHandling(`${API_BASE_URL}/plants/all`);
            return response.data || [];
        } catch (error) {
            console.error('Failed to fetch plants from backend, using local data:', error);
            return [];
        }
    };

    /**
     * Fetch a single plant by ID
     * GET /api/plants/{id}
     */
    const fetchPlantById = async (id) => {
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/plants/${id}`);
        return response.data;
    };

    /**
     * Search plants by name or scientific name
     * GET /api/plants/search?query=...
     */
    const searchPlantsByName = async (query) => {
        const response = await fetchWithErrorHandling(
            `${API_BASE_URL}/plants/search?query=${encodeURIComponent(query)}`
        );
        return response.data || [];
    };

    /**
     * Get top-rated plants
     * GET /api/plants/top-rated
     */
    const fetchTopRatedPlants = async () => {
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/plants/top-rated`);
        return response.data || [];
    };

    /**
     * Get plants by type
     * GET /api/plants/by-type?type=...
     */
    const fetchPlantsByType = async (type) => {
        const response = await fetchWithErrorHandling(
            `${API_BASE_URL}/plants/by-type?type=${encodeURIComponent(type)}`
        );
        return response.data || [];
    };

    // ============================================
    // SEARCH ENDPOINTS
    // ============================================

    /**
     * Perform general search
     * POST /api/search/general
     */
    const performGeneralSearch = async (searchData) => {
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/search/general`, {
            method: 'POST',
            headers: buildHeaders(false),
            body: JSON.stringify(searchData)
        });
        return response.data;
    };

    /**
     * Search plants by symptoms
     * POST /api/search/by-symptoms
     */
    const searchBySymptoms = async (symptomsOrDescription) => {
        // If it's a string, treat it as a natural language description
        // If it's an array, treat it as keyword list (legacy support)
        const requestBody = typeof symptomsOrDescription === 'string' 
            ? { description: symptomsOrDescription }
            : { symptoms: symptomsOrDescription };
            
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/search/by-symptoms`, {
            method: 'POST',
            headers: buildHeaders(false),
            body: JSON.stringify(requestBody)
        });
        return response.data;
    };

    /**
     * Search plants by visual features
     * POST /api/search/by-visual
     */
    const searchByVisualFeatures = async (visualData) => {
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/search/by-visual`, {
            method: 'POST',
            headers: buildHeaders(false),
            body: JSON.stringify(visualData)
        });
        return response.data;
    };

    // ============================================
    // IMAGE ENDPOINTS
    // ============================================

    /**
     * Upload and recognize plant from image
     * POST /api/images/recognize
     */
    const uploadPlantImage = async (imageFile) => {
        const formData = new FormData();
        formData.append('image', imageFile);

        const response = await fetchWithErrorHandling(`${API_BASE_URL}/images/recognize`, {
            method: 'POST',
            headers: buildHeaders(false, null), // Don't set Content-Type for FormData
            body: formData
        });
        return response.data;
    };

    /**
     * Upload plant image for a specific plant
     * POST /api/images/upload
     */
    const uploadPlantImageForPlant = async (imageFile, plantId) => {
        const formData = new FormData();
        formData.append('image', imageFile);
        formData.append('plantId', plantId);

        const response = await fetchWithErrorHandling(`${API_BASE_URL}/images/upload`, {
            method: 'POST',
            headers: buildHeaders(true, null), // Requires auth
            body: formData
        });
        return response.data;
    };

    // ============================================
    // USER & AUTHENTICATION ENDPOINTS
    // ============================================

    /**
     * Register a new user
     * POST /api/users/register
     */
    const registerUser = async (userData) => {
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/users/register`, {
            method: 'POST',
            headers: buildHeaders(false),
            body: JSON.stringify(userData)
        });
        
        showToast('Registration successful! Please login.', 'success');
        return response.data;
    };

    /**
     * Login user
     * POST /api/users/login
     */
    const loginUser = async (credentials) => {
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/users/login`, {
            method: 'POST',
            headers: buildHeaders(false),
            body: JSON.stringify(credentials)
        });
        
        // Store auth token
        if (response.data && response.data.token) {
            localStorage.setItem('authToken', response.data.token);
            localStorage.setItem('user', JSON.stringify(response.data.user));
            showToast('Login successful!', 'success');
        }
        
        return response.data;
    };

    /**
     * Logout user
     */
    const logoutUser = () => {
        localStorage.removeItem('authToken');
        localStorage.removeItem('user');
        showToast('Logged out successfully', 'info');
    };

    /**
     * Get current user info
     */
    const getCurrentUser = () => {
        const userStr = localStorage.getItem('user');
        return userStr ? JSON.parse(userStr) : null;
    };

    /**
     * Check if user is authenticated
     */
    const isAuthenticated = () => {
        return !!getAuthToken();
    };

    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    const fetchUserById = async (id) => {
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/users/${id}`, {
            headers: buildHeaders(true)
        });
        return response.data;
    };

    // ============================================
    // SYMPTOM ENDPOINTS
    // ============================================

    /**
     * Get all symptoms
     * GET /api/symptoms/all
     */
    const fetchAllSymptoms = async () => {
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/symptoms/all`);
        return response.data || [];
    };

    /**
     * Get symptom categories
     * GET /api/symptoms/categories
     */
    const fetchSymptomCategories = async () => {
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/symptoms/categories`);
        return response.data || [];
    };

    // ============================================
    // FEEDBACK ENDPOINTS
    // ============================================

    /**
     * Submit feedback/review for a plant
     * POST /api/feedback (or similar endpoint)
     */
    const submitFeedback = async (feedbackData) => {
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/feedback`, {
            method: 'POST',
            headers: buildHeaders(true),
            body: JSON.stringify(feedbackData)
        });
        
        showToast('Feedback submitted successfully!', 'success');
        return response.data;
    };

    // ============================================
    // ADMIN ENDPOINTS
    // ============================================

    /**
     * Delete a plant (Admin only)
     * DELETE /api/admin/plants/{id}
     */
    const deletePlant = async (id) => {
        const response = await fetchWithErrorHandling(`${API_BASE_URL}/admin/plants/${id}`, {
            method: 'DELETE',
            headers: buildHeaders(true)
        });
        
        showToast('Plant deleted successfully', 'success');
        return response.data;
    };

    // ============================================
    // UI HELPER FUNCTIONS
    // ============================================

    let loadingSpinnerCount = 0;

    const showLoadingSpinner = () => {
        loadingSpinnerCount++;
        const spinner = document.getElementById('loading-spinner');
        if (spinner) {
            spinner.style.display = 'flex';
        }
    };

    const hideLoadingSpinner = () => {
        loadingSpinnerCount = Math.max(0, loadingSpinnerCount - 1);
        if (loadingSpinnerCount === 0) {
            const spinner = document.getElementById('loading-spinner');
            if (spinner) {
                spinner.style.display = 'none';
            }
        }
    };

    const showToast = (message, type = 'info') => {
        // Check if toast container exists
        let toastContainer = document.getElementById('toast-container');
        if (!toastContainer) {
            toastContainer = document.createElement('div');
            toastContainer.id = 'toast-container';
            toastContainer.className = 'toast-container';
            document.body.appendChild(toastContainer);
        }

        // Create toast element
        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        
        const icon = {
            success: '✓',
            error: '✗',
            warning: '⚠',
            info: 'ℹ'
        }[type] || 'ℹ';
        
        toast.innerHTML = `
            <span class="toast-icon">${icon}</span>
            <span class="toast-message">${message}</span>
        `;

        toastContainer.appendChild(toast);

        // Animate in
        setTimeout(() => toast.classList.add('show'), 10);

        // Remove after 4 seconds
        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => toast.remove(), 300);
        }, 4000);
    };

    // ============================================
    // PUBLIC API
    // ============================================

    return {
        // Plant operations
        fetchAllPlants,
        fetchPlantById,
        searchPlantsByName,
        fetchTopRatedPlants,
        fetchPlantsByType,
        
        // Search operations
        performGeneralSearch,
        searchBySymptoms,
        searchByVisualFeatures,
        
        // Image operations
        uploadPlantImage,
        uploadPlantImageForPlant,
        
        // User & Auth operations
        registerUser,
        loginUser,
        logoutUser,
        getCurrentUser,
        isAuthenticated,
        fetchUserById,
        
        // Symptom operations
        fetchAllSymptoms,
        fetchSymptomCategories,
        
        // Feedback operations
        submitFeedback,
        
        // Admin operations
        deletePlant,
        
        // UI helpers
        showToast,
        showLoadingSpinner,
        hideLoadingSpinner
    };
})();

// Export for use in other modules
if (typeof window !== 'undefined') {
    window.ApiClient = ApiClient;
}
