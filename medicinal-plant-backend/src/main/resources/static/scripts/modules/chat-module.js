// Chat Module for Symptoms Search

const ChatModule = (function() {
    'use strict';

    let chatMessages = [];
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

    function init() {
        setupEventListeners();
        console.log('💬 Chat Module initialized');
    }

    function setupEventListeners() {
        const sendBtn = document.getElementById('send-symptoms-btn');
        const chatInput = document.getElementById('symptoms-chat-input');
        const suggestionChips = document.querySelectorAll('.suggestion-chip');

        if (sendBtn) {
            sendBtn.addEventListener('click', handleSendMessage);
        }

        if (chatInput) {
            chatInput.addEventListener('keypress', (e) => {
                if (e.key === 'Enter' && !e.shiftKey) {
                    e.preventDefault();
                    handleSendMessage();
                }
            });

            // Auto-resize textarea
            chatInput.addEventListener('input', () => {
                chatInput.style.height = 'auto';
                chatInput.style.height = chatInput.scrollHeight + 'px';
            });
        }

        // Handle suggestion chips
        suggestionChips.forEach(chip => {
            chip.addEventListener('click', () => {
                if (chatInput) {
                    chatInput.value = chip.textContent;
                    chatInput.focus();
                }
            });
        });
    }

    async function handleSendMessage() {
        const chatInput = document.getElementById('symptoms-chat-input');
        const message = chatInput.value.trim();

        if (!message) return;

        // Add user message to chat
        addMessage(message, 'user');
        chatInput.value = '';
        chatInput.style.height = 'auto';

        // Show loading indicator
        const loadingId = addLoadingMessage();

        try {
            // Call AI API to analyze symptoms
            const response = await fetch('/api/ai/analyze-symptoms', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ description: message })
            });

            if (!response.ok) {
                throw new Error('Failed to analyze symptoms');
            }

            const data = await response.json();
            
            // Remove loading message
            removeMessage(loadingId);

            // Add AI response
            addMessage(data.analysis || 'I understand your symptoms. Let me find suitable medicinal plants for you.', 'assistant');

            // Extract symptoms from AI response or use simple keyword extraction
            const symptoms = extractSymptoms(message, data);

            // Search for plants
            if (symptoms.length > 0) {
                await searchPlantsBySymptoms(symptoms);
            } else {
                addMessage('I couldn\'t identify specific symptoms. Could you provide more details? For example, describe where you feel discomfort and what kind of sensation it is.', 'assistant');
            }

        } catch (error) {
            console.error('Error analyzing symptoms:', error);
            removeMessage(loadingId);
            
            // Fallback: try keyword-based search
            const fallbackSymptoms = extractSymptomsSimple(message);
            
            if (fallbackSymptoms.length > 0) {
                addMessage(`I'll search for plants that may help with: ${fallbackSymptoms.join(', ')}`, 'assistant');
                await searchPlantsBySymptoms(fallbackSymptoms);
            } else {
                addMessage('I had trouble analyzing your message. Please try describing your symptoms more clearly, for example: "I have a headache" or "I feel stressed"', 'assistant');
            }
        }
    }

    function extractSymptoms(message, aiData) {
        // Try to extract from AI response first
        if (aiData && aiData.symptoms && Array.isArray(aiData.symptoms)) {
            return aiData.symptoms;
        }

        // Fallback to simple extraction
        return extractSymptomsSimple(message);
    }

    function extractSymptomsSimple(message) {
        const symptomKeywords = [
            'headache', 'fever', 'pain', 'ache', 'nausea', 'vomiting', 
            'diarrhea', 'constipation', 'cough', 'cold', 'flu', 'sore throat',
            'stress', 'anxiety', 'depression', 'insomnia', 'fatigue',
            'inflammation', 'swelling', 'rash', 'irritation', 'itching',
            'indigestion', 'bloating', 'heartburn', 'stomach', 'digestive'
        ];

        const foundSymptoms = [];
        const lowerMessage = message.toLowerCase();

        symptomKeywords.forEach(symptom => {
            if (lowerMessage.includes(symptom)) {
                foundSymptoms.push(symptom);
            }
        });

        return foundSymptoms;
    }

    async function searchPlantsBySymptoms(symptoms) {
        try {
            // Join symptoms for search
            const searchQuery = symptoms.join(',');
            
            const response = await fetch(`/api/plants/search/symptoms?symptoms=${encodeURIComponent(searchQuery)}`);
            
            if (!response.ok) {
                throw new Error('Search failed');
            }

            const plants = await response.json();

            if (plants && plants.length > 0) {
                addMessage(`I found ${plants.length} medicinal plant${plants.length > 1 ? 's' : ''} that may help:`, 'assistant');
                addPlantResults(plants);
                
                // Also update the main results section
                if (window.UIManager && window.UIManager.displaySearchResults) {
                    window.UIManager.displaySearchResults(plants);
                }
            } else {
                addMessage('I couldn\'t find any plants matching those symptoms. Try describing your symptoms differently or check the spelling.', 'assistant');
            }

        } catch (error) {
            console.error('Error searching plants:', error);
            addMessage('Sorry, I encountered an error while searching. Please try again.', 'assistant');
        }
    }

    function addMessage(content, type = 'assistant') {
        const chatMessagesContainer = document.getElementById('chat-messages');
        if (!chatMessagesContainer) return null;

        const messageId = 'msg-' + Date.now();
        const messageDiv = document.createElement('div');
        messageDiv.className = `chat-message ${type}`;
        messageDiv.id = messageId;

        const avatar = document.createElement('div');
        avatar.className = 'message-avatar';
        avatar.textContent = type === 'user' ? '👤' : '🤖';

        const messageContent = document.createElement('div');
        messageContent.className = 'message-content';
        
        const textP = document.createElement('p');
        textP.textContent = content;
        messageContent.appendChild(textP);

        messageDiv.appendChild(avatar);
        messageDiv.appendChild(messageContent);

        chatMessagesContainer.appendChild(messageDiv);
        
        // Scroll to bottom
        chatMessagesContainer.scrollTop = chatMessagesContainer.scrollHeight;

        chatMessages.push({ id: messageId, content, type });
        
        return messageId;
    }

    function addLoadingMessage() {
        const chatMessagesContainer = document.getElementById('chat-messages');
        if (!chatMessagesContainer) return null;

        const messageId = 'loading-' + Date.now();
        const messageDiv = document.createElement('div');
        messageDiv.className = 'chat-message assistant';
        messageDiv.id = messageId;

        const avatar = document.createElement('div');
        avatar.className = 'message-avatar';
        avatar.textContent = '🤖';

        const messageContent = document.createElement('div');
        messageContent.className = 'message-content';
        
        const loadingDiv = document.createElement('div');
        loadingDiv.className = 'message-loading';
        loadingDiv.innerHTML = '<span></span><span></span><span></span>';
        messageContent.appendChild(loadingDiv);

        messageDiv.appendChild(avatar);
        messageDiv.appendChild(messageContent);

        chatMessagesContainer.appendChild(messageDiv);
        chatMessagesContainer.scrollTop = chatMessagesContainer.scrollHeight;

        return messageId;
    }

    function removeMessage(messageId) {
        if (!messageId) return;
        const messageEl = document.getElementById(messageId);
        if (messageEl) {
            messageEl.remove();
        }
        chatMessages = chatMessages.filter(msg => msg.id !== messageId);
    }

    function addPlantResults(plants) {
        const chatMessagesContainer = document.getElementById('chat-messages');
        if (!chatMessagesContainer) return;

        const messageDiv = document.createElement('div');
        messageDiv.className = 'chat-message assistant';

        const avatar = document.createElement('div');
        avatar.className = 'message-avatar';
        avatar.textContent = '🤖';

        const messageContent = document.createElement('div');
        messageContent.className = 'message-content';
        
        const resultsGrid = document.createElement('div');
        resultsGrid.className = 'plant-results-in-chat';

        plants.slice(0, 6).forEach(plant => {
            const plantCard = document.createElement('div');
            plantCard.className = 'plant-card-mini';
            plantCard.onclick = () => {
                // Show plant details
                if (window.UIManager && window.UIManager.showPlantDetails) {
                    window.UIManager.showPlantDetails(plant);
                }
            };

            const emoji = plantEmojis[plant.name] || '🌿';
            
            plantCard.innerHTML = `
                <div class="plant-emoji">${emoji}</div>
                <div class="plant-name">${plant.name}</div>
            `;

            resultsGrid.appendChild(plantCard);
        });

        messageContent.appendChild(resultsGrid);
        messageDiv.appendChild(avatar);
        messageDiv.appendChild(messageContent);

        chatMessagesContainer.appendChild(messageDiv);
        chatMessagesContainer.scrollTop = chatMessagesContainer.scrollHeight;
    }

    return {
        init,
        addMessage,
        searchPlantsBySymptoms
    };
})();

// Auto-initialize when DOM is ready
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', ChatModule.init);
} else {
    ChatModule.init();
}
