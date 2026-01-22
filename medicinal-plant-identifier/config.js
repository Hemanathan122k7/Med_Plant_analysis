// Configuration for MediPlant Frontend
// This file can be modified for different environments

const CONFIG = {
    // API Configuration
    api: {
        // Backend API base URL
        // Update this after deploying your backend
        baseUrl: window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1'
            ? 'http://localhost:8080/api'
            : 'https://your-backend-api.com/api',
        
        // API timeout in milliseconds
        timeout: 30000,
        
        // Retry attempts for failed requests
        retryAttempts: 3
    },
    
    // Feature Flags
    features: {
        // Enable/disable features
        imageRecognition: true,
        voiceSearch: true,
        community: true,
        favorites: true,
        comparison: true,
        
        // Use mock data when backend is not available
        useMockData: true
    },
    
    // UI Configuration
    ui: {
        // Theme
        defaultTheme: 'light',
        
        // Animation
        enableAnimations: true,
        
        // Results per page
        resultsPerPage: 12,
        
        // Skeleton loading
        skeletonItems: 6
    },
    
    // Cache Configuration
    cache: {
        // Cache duration in milliseconds
        duration: 5 * 60 * 1000, // 5 minutes
        
        // Enable/disable caching
        enabled: true
    },
    
    // App Metadata
    app: {
        name: 'MediPlant',
        version: '1.0.0',
        description: 'Advanced Medicinal Plant Identifier',
        author: 'MediPlant Team'
    }
};

// Make CONFIG available globally
if (typeof window !== 'undefined') {
    window.APP_CONFIG = CONFIG;
}

// Export for modules
if (typeof module !== 'undefined' && module.exports) {
    module.exports = CONFIG;
}
