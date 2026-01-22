// Theme Manager Module
// Handles theme switching and transitions

const ThemeManager = (() => {
    let currentTheme = 'light';
    const htmlElement = document.documentElement;
    const themeToggleBtn = document.getElementById('theme-toggle');
    const themeTransition = document.getElementById('theme-transition');

    // Initialize theme
    function init() {
        const savedTheme = UserPreferences.getState().theme;
        setTheme(savedTheme);
        setupEventListeners();
    }

    // Setup event listeners
    function setupEventListeners() {
        if (themeToggleBtn) {
            themeToggleBtn.addEventListener('click', toggleTheme);
        }
    }

    // Set theme
    function setTheme(theme) {
        currentTheme = theme;
        htmlElement.dataset.theme = theme;
        UserPreferences.setTheme(theme);
    }

    // Toggle theme with transition
    function toggleTheme() {
        // Show transition overlay
        if (themeTransition) {
            themeTransition.classList.add('active');
        }
        
        setTimeout(() => {
            const newTheme = currentTheme === 'light' ? 'dark' : 'light';
            setTheme(newTheme);
            
            // Hide transition overlay
            setTimeout(() => {
                if (themeTransition) {
                    themeTransition.classList.remove('active');
                }
            }, 300);
        }, 300);
    }

    // Get current theme
    function getCurrentTheme() {
        return currentTheme;
    }

    return {
        init,
        setTheme,
        toggleTheme,
        getCurrentTheme
    };
})();
