// Plant Database Module
// Contains all plant data and search functionality

const PlantDatabase = (() => {
    // Hardcoded plant data removed - all data now comes from backend API
    const plants = {};

    // Get all plants
    function getAllPlants() {
        return plants;
    }

    // Get plant by ID
    function getPlantById(id) {
        return plants[id] || null;
    }

    // Search plants by name
    function searchByName(query) {
        const results = [];
        const lowerQuery = query.toLowerCase();
        
        for (const [id, plant] of Object.entries(plants)) {
            if (plant.name.toLowerCase().includes(lowerQuery) || 
                plant.scientific.toLowerCase().includes(lowerQuery)) {
                results.push({ id, ...plant });
            }
        }
        
        return results;
    }

    // Search plants by symptoms
    function searchBySymptoms(symptoms) {
        const results = [];
        const lowerSymptoms = symptoms.toLowerCase();
        
        for (const [id, plant] of Object.entries(plants)) {
            const usesMatch = plant.uses.some(use => 
                use.toLowerCase().includes(lowerSymptoms) ||
                lowerSymptoms.includes(use.toLowerCase())
            );
            
            if (usesMatch) {
                results.push({ id, ...plant });
            }
        }
        
        return results;
    }

    // Search plants by properties
    function searchByProperties(properties) {
        const results = [];
        
        for (const [id, plant] of Object.entries(plants)) {
            const hasProperty = properties.some(prop => 
                plant.properties.includes(prop)
            );
            
            if (hasProperty) {
                results.push({ id, ...plant });
            }
        }
        
        return results;
    }

    // Get random plants
    function getRandomPlants(count = 3) {
        const plantArray = Object.entries(plants);
        const shuffled = [...plantArray].sort(() => 0.5 - Math.random());
        return shuffled.slice(0, count).map(([id, plant]) => ({ id, ...plant }));
    }

    return {
        getAllPlants,
        getPlantById,
        searchByName,
        searchBySymptoms,
        searchByProperties,
        getRandomPlants
    };
})();
