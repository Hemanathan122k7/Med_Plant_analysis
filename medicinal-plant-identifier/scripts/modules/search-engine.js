// Search Engine Module
// Handles all search functionality

const SearchEngine = (() => {
    let currentSearchType = 'symptoms';
    let currentResults = [];

    // Initialize search
    function init() {
        setupEventListeners();
    }

    // Setup event listeners
    function setupEventListeners() {
        // Search buttons
        for (const button of document.querySelectorAll('.search-button')) {
            button.addEventListener('click', performSearch);
        }

        // Tab switching
        for (const tab of document.querySelectorAll('.tab')) {
            tab.addEventListener('click', () => {
                switchTab(tab.dataset.tab);
            });
        }

        // Visual search options
        document.querySelectorAll('.visual-option').forEach(option => {
            option.addEventListener('click', () => {
                option.classList.toggle('selected');
            });
        });

        // Image upload
        const uploadArea = document.getElementById('upload-area');
        const imageUpload = document.getElementById('image-upload');
        
        if (uploadArea && imageUpload) {
            uploadArea.addEventListener('click', () => {
                imageUpload.click();
            });
            
            uploadArea.addEventListener('dragover', handleDragOver);
            uploadArea.addEventListener('dragleave', handleDragLeave);
            uploadArea.addEventListener('drop', handleDrop);
            
            imageUpload.addEventListener('change', (e) => {
                if (e.target.files.length) {
                    handleImageUpload(e.target.files[0]);
                }
            });
        }

        // Search input suggestions
        for (const input of document.querySelectorAll('.search-input')) {
            input.addEventListener('input', Helpers.debounce(showSearchSuggestions, 300));
            input.addEventListener('focus', showSearchSuggestions);
        }

        // Voice search
        const voiceSearchBtn = document.querySelector('.voice-search-btn');
        if (voiceSearchBtn) {
            voiceSearchBtn.addEventListener('click', toggleVoiceSearch);
        }

        // Advanced filters toggle
        const filterToggle = document.querySelector('.filter-toggle');
        if (filterToggle) {
            filterToggle.addEventListener('click', toggleAdvancedFilters);
        }

        // Hide suggestions when clicking outside
        document.addEventListener('click', (e) => {
            if (!e.target.closest('.search-input-container')) {
                hideSearchSuggestions();
            }
        });
    }

    // Switch search tab
    function switchTab(tabName) {
        for (const t of document.querySelectorAll('.tab')) {
            t.classList.remove('active');
        }
        for (const c of document.querySelectorAll('.tab-content')) {
            c.classList.remove('active');
        }
        
        const selectedTab = document.querySelector(`[data-tab="${tabName}"]`);
        const selectedContent = document.getElementById(`${tabName}-tab`);
        
        if (selectedTab) selectedTab.classList.add('active');
        if (selectedContent) selectedContent.classList.add('active');
        
        currentSearchType = tabName;
    }

    // Perform search
    async function performSearch() {
        const activeTab = document.querySelector('.tab.active');
        if (!activeTab) return;

        const searchType = activeTab.dataset.tab;
        let results = [];

        // Show loading state
        const buttons = document.querySelectorAll('.search-button');
        buttons.forEach(button => {
            button.classList.add('loading');
            button.disabled = true;
        });

        try {
            if (searchType === 'symptoms' || searchType === 'name') {
                const input = document.querySelector(`#${searchType}-tab .search-input`);
                if (input && input.value.trim()) {
                    const query = input.value.trim();
                    UserPreferences.addToSearchHistory(query);
                    
                    if (searchType === 'symptoms') {
                        // PRIORITY: Use backend API for symptom search with natural language
                        console.log('🔍 Searching symptoms in backend (AI-powered):', query);
                        results = await ApiClient.searchBySymptoms(query); // Send as string for AI analysis
                        console.log('✅ Backend results:', results);
                    } else {
                        // PRIORITY: Use backend API for name search
                        console.log('🔍 Searching name in backend:', query);
                        results = await ApiClient.searchPlantsByName(query);
                        console.log('✅ Backend results:', results);
                    }
                }
            } else if (searchType === 'visual') {
                // Get selected visual options
                const selectedOptions = document.querySelectorAll('.visual-option.selected');
                if (selectedOptions.length > 0) {
                    // Use backend visual search API
                    const features = Array.from(selectedOptions).map(opt => opt.dataset.feature);
                    console.log('🔍 Visual search with features:', features);
                    results = await ApiClient.searchByVisualFeatures({ features });
                }
            } else if (searchType === 'image') {
                // Image search will be triggered separately via handleImageUpload
                console.log('ℹ️ Image search triggered from upload handler');
                return;
            }

            currentResults = results;

            // Display results with AI recommendation if available
            const aiRecommendation = results.aiRecommendation || null;
            const plants = results.plants || results; // Handle both response formats
            UIManager.displaySearchResults(plants, aiRecommendation);
            
        } catch (error) {
            console.error('❌ Search failed:', error);
            UIManager.showError('Search failed. Please try again.');
            currentResults = [];
        } finally {
            // Remove loading state
            buttons.forEach(button => {
                button.classList.remove('loading');
                button.disabled = false;
            });
        }
    }

    // Handle image upload
    async function handleImageUpload(file) {
        const preview = document.getElementById('preview-img');
        const imagePreview = document.getElementById('image-preview');
        const progressFill = document.querySelector('.progress-fill');
        const uploadProgress = document.querySelector('.upload-progress');
        
        if (!preview || !imagePreview || !progressFill || !uploadProgress) return;
        
        // Validate file type
        const validTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/webp'];
        if (!validTypes.includes(file.type)) {
            UIManager.showError('Please upload a valid image file (JPG, PNG, or WEBP)');
            return;
        }
        
        // Validate file size (max 5MB)
        const maxSize = 5 * 1024 * 1024; // 5MB
        if (file.size > maxSize) {
            UIManager.showError('Image file size must be less than 5MB');
            return;
        }
        
        // Show upload progress
        uploadProgress.style.display = 'block';
        
        // Animate upload progress
        let progress = 0;
        const progressInterval = setInterval(() => {
            progress += 10;
            if (progress <= 90) {
                progressFill.style.width = `${progress}%`;
            }
        }, 100);
        
        try {
            // Show image preview
            const reader = new FileReader();
            reader.onload = (e) => {
                preview.src = e.target.result;
                imagePreview.style.display = 'block';
            };
            reader.readAsDataURL(file);
            
            // Upload to backend for recognition
            console.log('🖼️ Uploading image to backend for plant recognition...');
            const results = await ApiClient.uploadPlantImage(file);
            
            // Complete progress
            clearInterval(progressInterval);
            progressFill.style.width = '100%';
            
            setTimeout(() => {
                uploadProgress.style.display = 'none';
                progressFill.style.width = '0%';
            }, 500);
            
            console.log('✅ Plant recognition results:', results);
            
            // Display results
            if (results && results.length > 0) {
                currentResults = results;
                UIManager.displaySearchResults(results);
                UIManager.showSuccess(`Found ${results.length} matching plant(s)!`);
            } else {
                UIManager.showError('No matching plants found. The image may not contain a recognizable medicinal plant.');
                currentResults = [];
                UIManager.displaySearchResults([]);
            }
            
        } catch (error) {
            clearInterval(progressInterval);
            uploadProgress.style.display = 'none';
            progressFill.style.width = '0%';
            
            console.error('❌ Image recognition failed:', error);
            UIManager.showError('Failed to recognize plant from image. Please try again or ensure the image contains a clear view of a medicinal plant.');
            currentResults = [];
            UIManager.displaySearchResults([]);
        }
    }

    // Handle drag over
    function handleDragOver(e) {
        e.preventDefault();
        e.currentTarget.classList.add('dragover');
    }

    // Handle drag leave
    function handleDragLeave(e) {
        e.currentTarget.classList.remove('dragover');
    }

    // Handle drop
    function handleDrop(e) {
        e.preventDefault();
        e.currentTarget.classList.remove('dragover');
        
        if (e.dataTransfer.files.length) {
            handleImageUpload(e.dataTransfer.files[0]);
        }
    }

    // Show search suggestions
    function showSearchSuggestions(e) {
        const input = e.target;
        const suggestions = input.closest('.search-input-container')?.querySelector('.search-suggestions');
        if (!suggestions) return;

        const activeTab = document.querySelector('.tab.active')?.dataset.tab;
        
        let suggestionItems = [];
        if (activeTab === 'symptoms') {
            suggestionItems = ["Headache", "Fever", "Stomach pain", "Skin irritation", "Anxiety"];
        } else if (activeTab === 'name') {
            suggestionItems = ["Aloe Vera", "Turmeric", "Ginger", "Lavender", "Peppermint"];
        }
        
        suggestions.innerHTML = '';
        suggestionItems.forEach(item => {
            const div = document.createElement('div');
            div.className = 'suggestion-item';
            div.textContent = item;
            div.addEventListener('click', () => {
                input.value = item;
                suggestions.style.display = 'none';
            });
            suggestions.appendChild(div);
        });
        
        suggestions.style.display = 'block';
    }

    // Hide search suggestions
    function hideSearchSuggestions() {
        document.querySelectorAll('.search-suggestions').forEach(s => {
            s.style.display = 'none';
        });
    }

    // Toggle voice search
    function toggleVoiceSearch() {
        const voiceBtn = document.querySelector('.voice-search-btn');
        if (!voiceBtn) return;

        const isListening = voiceBtn.classList.contains('listening');
        
        if (isListening) {
            voiceBtn.classList.remove('listening');
            const icon = voiceBtn.querySelector('i');
            if (icon) {
                icon.classList.remove('fa-stop');
                icon.classList.add('fa-microphone');
            }
        } else {
            voiceBtn.classList.add('listening');
            const icon = voiceBtn.querySelector('i');
            if (icon) {
                icon.classList.remove('fa-microphone');
                icon.classList.add('fa-stop');
            }
            
            // Simulate voice input
            setTimeout(() => {
                const searchInput = document.querySelector('.tab-content.active .search-input');
                if (searchInput) {
                    searchInput.value = "headache and fever";
                    performSearch();
                }
                
                voiceBtn.classList.remove('listening');
                const icon = voiceBtn.querySelector('i');
                if (icon) {
                    icon.classList.remove('fa-stop');
                    icon.classList.add('fa-microphone');
                }
            }, 3000);
        }
    }

    // Toggle advanced filters
    function toggleAdvancedFilters() {
        const advancedFilters = document.querySelector('.advanced-filters');
        if (advancedFilters) {
            advancedFilters.classList.toggle('active');
        }
    }

    // Get current results
    function getCurrentResults() {
        return [...currentResults];
    }

    return {
        init,
        performSearch,
        getCurrentResults
    };
})();
