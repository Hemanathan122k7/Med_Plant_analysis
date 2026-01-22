// Plant Database Module
// Contains all plant data and search functionality

const PlantDatabase = (() => {
    const plants = {
        aloe: {
            name: "Aloe Vera",
            scientific: "Aloe barbadensis miller",
            description: "Aloe vera is a succulent plant species of the genus Aloe. It is widely distributed, and is considered an invasive species in many world regions.",
            uses: ["Burns", "Skin irritation", "Digestive issues", "Wound healing"],
            precautions: "Do not ingest aloe latex as it may cause serious side effects. Topical use is generally safe, but test on a small area first.",
            image: "https://via.placeholder.com/200/4caf50/ffffff?text=Aloe+Vera",
            safety: "safe",
            availability: "common",
            rating: 4.8,
            reviews: 142,
            properties: ["anti-inflammatory", "antioxidant", "skin-healing"]
        },
        turmeric: {
            name: "Turmeric",
            scientific: "Curcuma longa",
            description: "Turmeric is a flowering plant of the ginger family, native to the Indian subcontinent and Southeast Asia.",
            uses: ["Anti-inflammatory", "Antioxidant", "Arthritis relief", "Digestive aid"],
            precautions: "High doses may cause stomach upset. People with gallbladder problems should avoid turmeric supplements.",
            image: "https://via.placeholder.com/200/ff9800/ffffff?text=Turmeric",
            safety: "safe",
            availability: "common",
            rating: 4.6,
            reviews: 89,
            properties: ["anti-inflammatory", "antioxidant", "digestive"]
        },
        peppermint: {
            name: "Peppermint",
            scientific: "Mentha × piperita",
            description: "Peppermint is a hybrid mint, a cross between watermint and spearmint. Indigenous to Europe and the Middle East.",
            uses: ["Headache relief", "Digestive issues", "Respiratory problems", "Muscle pain"],
            precautions: "May cause heartburn in some individuals. Not recommended for infants or people with GERD.",
            image: "https://via.placeholder.com/200/4caf50/ffffff?text=Peppermint",
            safety: "safe",
            availability: "common",
            rating: 4.5,
            reviews: 76,
            properties: ["digestive", "calming", "analgesic"]
        },
        lavender: {
            name: "Lavender",
            scientific: "Lavandula angustifolia",
            description: "Lavender is a flowering plant in the mint family, native to the Mediterranean region, but now cultivated worldwide.",
            uses: ["Anxiety relief", "Insomnia", "Stress reduction", "Skin conditions"],
            precautions: "May cause skin irritation in some individuals. Avoid during pregnancy as it may stimulate uterine contractions.",
            image: "https://via.placeholder.com/200/9c27b0/ffffff?text=Lavender",
            safety: "caution",
            availability: "common",
            rating: 4.7,
            reviews: 103,
            properties: ["calming", "skin-healing"]
        },
        ginger: {
            name: "Ginger",
            scientific: "Zingiber officinale",
            description: "Ginger is a flowering plant whose rhizome is widely used as a spice and folk medicine.",
            uses: ["Nausea relief", "Anti-inflammatory", "Digestive aid", "Cold and flu"],
            precautions: "May interact with blood thinners. High doses may cause heartburn or mouth irritation.",
            image: "https://via.placeholder.com/200/ff9800/ffffff?text=Ginger",
            safety: "safe",
            availability: "common",
            rating: 4.4,
            reviews: 67,
            properties: ["anti-inflammatory", "digestive", "analgesic"]
        },
        chamomile: {
            name: "Chamomile",
            scientific: "Matricaria chamomilla",
            description: "Chamomile is a daisy-like plant that has been used for centuries for its calming and medicinal properties.",
            uses: ["Anxiety relief", "Insomnia", "Digestive issues", "Skin inflammation"],
            precautions: "May cause allergic reactions in people sensitive to ragweed. Avoid during pregnancy.",
            image: "https://via.placeholder.com/200/ffeb3b/333333?text=Chamomile",
            safety: "caution",
            availability: "common",
            rating: 4.3,
            reviews: 58,
            properties: ["calming", "digestive", "skin-healing"]
        }
    };

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
