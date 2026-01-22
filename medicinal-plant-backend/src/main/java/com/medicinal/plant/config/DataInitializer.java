package com.medicinal.plant.config;

import com.medicinal.plant.model.entity.Plant;
import com.medicinal.plant.model.entity.PlantSymptom;
import com.medicinal.plant.model.entity.Symptom;
import com.medicinal.plant.repository.PlantRepository;
import com.medicinal.plant.repository.SymptomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class DataInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner loadData(PlantRepository plantRepository, SymptomRepository symptomRepository) {
        return args -> {
            // Skip if data already exists
            if (plantRepository.count() > 0) {
                logger.info("🌿 Database already contains {} plants, skipping initialization", plantRepository.count());
                return;
            }
            
            logger.info("🌱 Initializing database with medicinal plant data...");
            
            try {
                // Create symptoms first
                List<Symptom> symptoms = createSymptoms(symptomRepository);
                logger.info("✅ Created {} symptoms", symptoms.size());
                
                // Create a map for easy lookup
                Map<String, Symptom> symptomMap = symptoms.stream()
                    .collect(Collectors.toMap(Symptom::getName, s -> s));
                
                // Create plants with symptom relationships
                List<Plant> plants = createPlantsWithSymptoms(symptomMap);
                plantRepository.saveAll(plants);
                logger.info("✅ Created {} plants with symptom relationships", plants.size());
                
                logger.info("🌿 Database initialization complete!");
                
            } catch (Exception e) {
                logger.error("❌ Failed to initialize database", e);
            }
        };
    }
    
    private List<Symptom> createSymptoms(SymptomRepository symptomRepository) {
        List<Symptom> symptoms = new ArrayList<>();
        
        String[] symptomNames = {
            "Burns", "Skin irritation", "Wounds", "Digestive issues", "Inflammation",
            "Nausea", "Pain", "Headache", "Joint pain", "Muscle pain", "Fever",
            "Cough", "Cold symptoms", "Respiratory issues", "Anxiety", "Stress",
            "Insomnia", "Sleep disorders", "Depression", "Fatigue", "Immune weakness",
            "Infections", "High blood pressure", "High cholesterol", "Acne"
        };
        
        for (String name : symptomNames) {
            Symptom symptom = new Symptom();
            symptom.setName(name);
            symptoms.add(symptom);
        }
        
        return symptomRepository.saveAll(symptoms);
    }
    
    private List<Plant> createPlantsWithSymptoms(Map<String, Symptom> symptomMap) {
        List<Plant> plants = new ArrayList<>();
        
        // Aloe Vera
        Plant aloeVera = new Plant();
        aloeVera.setName("Aloe Vera");
        aloeVera.setScientificName("Aloe barbadensis miller");
        aloeVera.setDescription("Succulent plant widely used for skin healing and digestive health. Contains over 75 active compounds including vitamins, minerals, enzymes, and amino acids.");
        aloeVera.setDosage("Apply gel topically 2-3 times daily. For internal use: 1-2 tablespoons of pure aloe juice before meals.");
        aloeVera.setPrecautions("May cause allergic reactions in sensitive individuals. Avoid internal use during pregnancy. Can interact with diabetes medications.");
        aloeVera.setImageUrl("/assets/images/plants/aloe-vera.jpg");
        aloeVera.setMedicinalUses(List.of("Burn treatment", "Skin healing", "Digestive aid", "Anti-inflammatory", "Wound healing", "Moisturizing"));
        aloeVera.setActiveCompounds(List.of("Aloin", "Polysaccharides", "Vitamins A, C, E"));
        addSymptomRelationships(aloeVera, symptomMap, 
            Map.of("Burns", 0.95, "Skin irritation", 0.90, "Wounds", 0.85, "Digestive issues", 0.75, "Inflammation", 0.80));
        plants.add(aloeVera);
        
        // Ginger
        Plant ginger = new Plant();
        ginger.setName("Ginger");
        ginger.setScientificName("Zingiber officinale");
        ginger.setDescription("Tropical plant with powerful anti-inflammatory and antioxidant effects. Used for thousands of years in traditional medicine.");
        ginger.setDosage("Fresh: 1-2 grams daily. Dried powder: 0.5-1 gram daily. Tea: 1-2 cups daily.");
        ginger.setPrecautions("May interact with blood thinners. High doses may cause heartburn. Avoid before surgery.");
        ginger.setImageUrl("/assets/images/plants/ginger.jpg");
        ginger.setMedicinalUses(List.of("Nausea relief", "Anti-inflammatory", "Digestive aid", "Pain relief", "Immune booster", "Circulation improvement"));
        ginger.setActiveCompounds(List.of("Gingerol", "Shogaol", "Zingerone"));
        addSymptomRelationships(ginger, symptomMap,
            Map.of("Nausea", 0.95, "Inflammation", 0.85, "Digestive issues", 0.90, "Pain", 0.75, "Headache", 0.70));
        plants.add(ginger);
        
        // Turmeric
        Plant turmeric = new Plant();
        turmeric.setName("Turmeric");
        turmeric.setScientificName("Curcuma longa");
        turmeric.setDescription("Golden spice with potent anti-inflammatory and antioxidant properties. Main active compound is curcumin.");
        turmeric.setDosage("500-2000 mg of turmeric extract daily. Fresh root: 1.5-3 grams daily. Best absorbed with black pepper.");
        turmeric.setPrecautions("May interact with blood thinners. High doses may cause digestive upset. Avoid before surgery.");
        turmeric.setImageUrl("/assets/images/plants/turmeric.jpg");
        turmeric.setMedicinalUses(List.of("Anti-inflammatory", "Antioxidant", "Pain relief", "Immune support", "Liver health", "Brain health"));
        turmeric.setActiveCompounds(List.of("Curcumin", "Turmerone", "Demethoxycurcumin"));
        addSymptomRelationships(turmeric, symptomMap,
            Map.of("Inflammation", 0.95, "Pain", 0.85, "Joint pain", 0.90, "Digestive issues", 0.70));
        plants.add(turmeric);
        
        // Lavender
        Plant lavender = new Plant();
        lavender.setName("Lavender");
        lavender.setScientificName("Lavandula angustifolia");
        lavender.setDescription("Aromatic herb known for calming and relaxation properties. Used in aromatherapy and traditional medicine.");
        lavender.setDosage("Tea: 1-2 cups daily. Essential oil: 2-4 drops in diffuser. Topical: Dilute 2-3% in carrier oil.");
        lavender.setPrecautions("Generally safe. May cause drowsiness. Avoid during pregnancy (essential oil). Can interact with sedatives.");
        lavender.setImageUrl("/assets/images/plants/lavender.jpg");
        lavender.setMedicinalUses(List.of("Relaxation", "Sleep aid", "Anxiety relief", "Antiseptic", "Headache relief", "Stress reduction"));
        lavender.setActiveCompounds(List.of("Linalool", "Linalyl acetate", "Camphor"));
        addSymptomRelationships(lavender, symptomMap,
            Map.of("Anxiety", 0.90, "Stress", 0.90, "Insomnia", 0.85, "Sleep disorders", 0.85, "Headache", 0.75));
        plants.add(lavender);
        
        // Peppermint
        Plant peppermint = new Plant();
        peppermint.setName("Peppermint");
        peppermint.setScientificName("Mentha × piperita");
        peppermint.setDescription("Hybrid mint plant with cooling and soothing properties. Excellent for digestive and respiratory issues.");
        peppermint.setDosage("Tea: 1-2 cups daily. Essential oil: 1-2 drops internally or 2-4 drops in diffuser. Capsules: 0.2-0.4ml 3 times daily.");
        peppermint.setPrecautions("May worsen acid reflux. Avoid in gallbladder disease. Essential oil toxic in large amounts.");
        peppermint.setImageUrl("/assets/images/plants/peppermint.jpg");
        peppermint.setMedicinalUses(List.of("Digestive aid", "Headache relief", "Nausea relief", "Muscle pain relief", "Respiratory relief", "Antimicrobial"));
        peppermint.setActiveCompounds(List.of("Menthol", "Menthone", "Rosmarinic acid"));
        addSymptomRelationships(peppermint, symptomMap,
            Map.of("Digestive issues", 0.90, "Headache", 0.85, "Nausea", 0.90, "Muscle pain", 0.75, "Respiratory issues", 0.80, "Cough", 0.75));
        plants.add(peppermint);
        
        // Chamomile
        Plant chamomile = new Plant();
        chamomile.setName("Chamomile");
        chamomile.setScientificName("Matricaria chamomilla");
        chamomile.setDescription("Gentle herb with anti-inflammatory and calming properties. One of the oldest medicinal herbs.");
        chamomile.setDosage("Tea: 2-3 cups daily. Extract: 300-400mg 3 times daily. Topical: Apply diluted tea or cream.");
        chamomile.setPrecautions("May cause allergic reactions in those allergic to ragweed. Can interact with blood thinners.");
        chamomile.setImageUrl("/assets/images/plants/chamomile.jpg");
        chamomile.setMedicinalUses(List.of("Relaxation", "Digestive aid", "Anti-inflammatory", "Sleep aid", "Anxiety relief", "Skin soothing"));
        chamomile.setActiveCompounds(List.of("Apigenin", "Chamazulene", "Bisabolol"));
        addSymptomRelationships(chamomile, symptomMap,
            Map.of("Anxiety", 0.85, "Digestive issues", 0.85, "Inflammation", 0.75, "Insomnia", 0.90, "Sleep disorders", 0.90, "Skin irritation", 0.80));
        plants.add(chamomile);
        
        // Echinacea
        Plant echinacea = new Plant();
        echinacea.setName("Echinacea");
        echinacea.setScientificName("Echinacea purpurea");
        echinacea.setDescription("Purple coneflower known for immune-boosting properties. Popular for preventing and treating colds.");
        echinacea.setDosage("Tea: 2-3 cups daily. Extract: 300mg 3 times daily. Use for max 8 weeks, then take a break.");
        echinacea.setPrecautions("Avoid in autoimmune diseases. May cause allergic reactions. Not recommended for prolonged use.");
        echinacea.setImageUrl("/assets/images/plants/echinacea.jpg");
        echinacea.setMedicinalUses(List.of("Immune support", "Cold prevention", "Anti-inflammatory", "Wound healing", "Antiviral", "Infection fighter"));
        echinacea.setActiveCompounds(List.of("Echinacoside", "Cichoric acid", "Polysaccharides"));
        addSymptomRelationships(echinacea, symptomMap,
            Map.of("Immune weakness", 0.90, "Cold symptoms", 0.85, "Inflammation", 0.75, "Wounds", 0.80, "Infections", 0.85, "Cough", 0.70));
        plants.add(echinacea);
        
        // Garlic
        Plant garlic = new Plant();
        garlic.setName("Garlic");
        garlic.setScientificName("Allium sativum");
        garlic.setDescription("Pungent bulb with powerful antimicrobial and cardiovascular benefits. Used for centuries in medicine.");
        garlic.setDosage("Fresh: 1-2 cloves daily. Aged extract: 600-1200mg daily. Best consumed raw or lightly cooked.");
        garlic.setPrecautions("May cause breath odor and digestive upset. Can interact with blood thinners. Avoid before surgery.");
        garlic.setImageUrl("/assets/images/plants/garlic.jpg");
        garlic.setMedicinalUses(List.of("Heart health", "Immune support", "Antibacterial", "Antiviral", "Blood pressure reduction", "Cholesterol management"));
        garlic.setActiveCompounds(List.of("Allicin", "Diallyl disulfide", "S-allyl cysteine"));
        addSymptomRelationships(garlic, symptomMap,
            Map.of("Immune weakness", 0.85, "Infections", 0.90, "High blood pressure", 0.85, "High cholesterol", 0.80, "Cold symptoms", 0.75));
        plants.add(garlic);
        
        // Holy Basil (Tulsi)
        Plant holyBasil = new Plant();
        holyBasil.setName("Holy Basil");
        holyBasil.setScientificName("Ocimum sanctum");
        holyBasil.setDescription("Sacred herb in Ayurveda known as an adaptogen. Helps body cope with stress and promotes balance.");
        holyBasil.setDosage("Tea: 2-3 cups daily. Extract: 300-600mg daily. Fresh leaves: 4-5 leaves chewed daily.");
        holyBasil.setPrecautions("May lower blood sugar. Avoid during pregnancy. Can interact with anticoagulant medications.");
        holyBasil.setImageUrl("/assets/images/plants/holy-basil.jpg");
        holyBasil.setMedicinalUses(List.of("Stress relief", "Immune support", "Anti-inflammatory", "Respiratory support", "Antioxidant", "Adaptogen"));
        holyBasil.setActiveCompounds(List.of("Eugenol", "Ursolic acid", "Rosmarinic acid"));
        addSymptomRelationships(holyBasil, symptomMap,
            Map.of("Stress", 0.95, "Immune weakness", 0.85, "Inflammation", 0.80, "Respiratory issues", 0.85, "Fatigue", 0.80, "Cough", 0.75));
        plants.add(holyBasil);
        
        // Neem
        Plant neem = new Plant();
        neem.setName("Neem");
        neem.setScientificName("Azadirachta indica");
        neem.setDescription("Versatile medicinal tree used in Ayurveda for over 4000 years. Powerful antibacterial, antiviral, and antifungal properties.");
        neem.setDosage("Leaf powder: 500mg-1g twice daily. Oil: For external use only. Tea: 1-2 cups daily.");
        neem.setPrecautions("Avoid during pregnancy and breastfeeding. Can lower blood sugar. Not for long-term internal use.");
        neem.setImageUrl("/assets/images/plants/neem.jpg");
        neem.setMedicinalUses(List.of("Antibacterial", "Antiviral", "Antifungal", "Skin health", "Dental health", "Immune support", "Blood purifier"));
        neem.setActiveCompounds(List.of("Azadirachtin", "Nimbin", "Nimbidin"));
        addSymptomRelationships(neem, symptomMap,
            Map.of("Skin irritation", 0.90, "Wounds", 0.85, "Fever", 0.75, "Immune weakness", 0.80, "Acne", 0.90, "Infections", 0.85));
        plants.add(neem);
        
        // Ashwagandha
        Plant ashwagandha = new Plant();
        ashwagandha.setName("Ashwagandha");
        ashwagandha.setScientificName("Withania somnifera");
        ashwagandha.setDescription("Powerful adaptogen in Ayurvedic medicine. Helps reduce stress and anxiety while boosting energy and concentration.");
        ashwagandha.setDosage("300-500mg extract twice daily with meals. Root powder: 1-2 grams daily.");
        ashwagandha.setPrecautions("May lower blood pressure. Avoid during pregnancy. Can interact with thyroid medications.");
        ashwagandha.setImageUrl("/assets/images/plants/ashwagandha.jpg");
        ashwagandha.setMedicinalUses(List.of("Stress relief", "Adaptogen", "Energy boost", "Anti-inflammatory", "Immune support"));
        ashwagandha.setActiveCompounds(List.of("Withanolides", "Withaferin A"));
        addSymptomRelationships(ashwagandha, symptomMap,
            Map.of("Stress", 0.90, "Fatigue", 0.85, "Anxiety", 0.85, "Immune weakness", 0.80));
        plants.add(ashwagandha);
        
        // Ginseng
        Plant ginseng = new Plant();
        ginseng.setName("Ginseng");
        ginseng.setScientificName("Panax ginseng");
        ginseng.setDescription("Traditional Asian herb used for energy, focus, and immune support. Known as the 'King of Herbs'.");
        ginseng.setDosage("200-400mg extract daily. Do not exceed 3 months continuous use.");
        ginseng.setPrecautions("May cause insomnia or nervousness. Avoid with blood thinners. Not for children.");
        ginseng.setImageUrl("/assets/images/plants/ginseng.jpg");
        ginseng.setMedicinalUses(List.of("Energy boost", "Immune support", "Cognitive enhancer", "Adaptogen", "Antioxidant"));
        ginseng.setActiveCompounds(List.of("Ginsenosides", "Panaxosides"));
        addSymptomRelationships(ginseng, symptomMap,
            Map.of("Fatigue", 0.90, "Immune weakness", 0.85, "Stress", 0.80));
        plants.add(ginseng);
        
        // Eucalyptus
        Plant eucalyptus = new Plant();
        eucalyptus.setName("Eucalyptus");
        eucalyptus.setScientificName("Eucalyptus globulus");
        eucalyptus.setDescription("Australian native tree with powerful respiratory benefits. Leaves contain eucalyptol with antimicrobial properties.");
        eucalyptus.setDosage("Steam inhalation: 3-5 drops in hot water. Topical: diluted essential oil. Do not ingest.");
        eucalyptus.setPrecautions("Toxic if ingested. Must dilute before topical use. Keep away from children and pets.");
        eucalyptus.setImageUrl("/assets/images/plants/eucalyptus.jpg");
        eucalyptus.setMedicinalUses(List.of("Respiratory relief", "Antiseptic", "Decongestant", "Anti-inflammatory", "Pain relief", "Antimicrobial"));
        eucalyptus.setActiveCompounds(List.of("Eucalyptol", "Alpha-pinene", "Limonene"));
        addSymptomRelationships(eucalyptus, symptomMap,
            Map.of("Respiratory issues", 0.90, "Cough", 0.85, "Cold symptoms", 0.85, "Muscle pain", 0.75));
        plants.add(eucalyptus);
        
        // St. John's Wort
        Plant stJohnsWort = new Plant();
        stJohnsWort.setName("St. John's Wort");
        stJohnsWort.setScientificName("Hypericum perforatum");
        stJohnsWort.setDescription("Traditional herb for mild to moderate depression. Contains compounds that affect neurotransmitters.");
        stJohnsWort.setDosage("300mg extract 3 times daily. Takes 4-6 weeks for full effect.");
        stJohnsWort.setPrecautions("Major drug interactions including birth control, antidepressants. Increases sun sensitivity.");
        stJohnsWort.setImageUrl("/assets/images/plants/st-johns-wort.jpg");
        stJohnsWort.setMedicinalUses(List.of("Depression relief", "Anxiety relief", "Mood enhancement", "Wound healing", "Anti-inflammatory"));
        stJohnsWort.setActiveCompounds(List.of("Hypericin", "Hyperforin", "Flavonoids"));
        addSymptomRelationships(stJohnsWort, symptomMap,
            Map.of("Depression", 0.85, "Anxiety", 0.80, "Wounds", 0.70));
        plants.add(stJohnsWort);
        
        // Valerian
        Plant valerian = new Plant();
        valerian.setName("Valerian");
        valerian.setScientificName("Valeriana officinalis");
        valerian.setDescription("Root used for centuries as a natural sedative. Helps with sleep and anxiety disorders.");
        valerian.setDosage("300-600mg root extract before bedtime. Tea: 1-2 cups evening.");
        valerian.setPrecautions("May cause drowsiness. Don't combine with alcohol or sedatives. Avoid before driving.");
        valerian.setImageUrl("/assets/images/plants/valerian.jpg");
        valerian.setMedicinalUses(List.of("Sleep aid", "Anxiety relief", "Sedative", "Relaxation"));
        valerian.setActiveCompounds(List.of("Valerenic acid", "Valepotriates"));
        addSymptomRelationships(valerian, symptomMap,
            Map.of("Insomnia", 0.90, "Sleep disorders", 0.90, "Anxiety", 0.85, "Stress", 0.80));
        plants.add(valerian);
        
        // Milk Thistle
        Plant milkThistle = new Plant();
        milkThistle.setName("Milk Thistle");
        milkThistle.setScientificName("Silybum marianum");
        milkThistle.setDescription("Liver protective herb with powerful antioxidant properties. Contains silymarin which heals liver cells.");
        milkThistle.setDosage("200-400mg silymarin extract daily with meals.");
        milkThistle.setPrecautions("Generally safe. May cause digestive upset. Can affect blood sugar.");
        milkThistle.setImageUrl("/assets/images/plants/milk-thistle.jpg");
        milkThistle.setMedicinalUses(List.of("Liver health", "Detoxification", "Antioxidant", "Anti-inflammatory"));
        milkThistle.setActiveCompounds(List.of("Silymarin", "Silibinin"));
        addSymptomRelationships(milkThistle, symptomMap,
            Map.of("Inflammation", 0.75, "Digestive issues", 0.70));
        plants.add(milkThistle);
        
        // Ginkgo Biloba
        Plant ginkgo = new Plant();
        ginkgo.setName("Ginkgo Biloba");
        ginkgo.setScientificName("Ginkgo biloba");
        ginkgo.setDescription("Ancient tree species used for memory and cognitive function. Improves blood circulation.");
        ginkgo.setDosage("120-240mg extract daily in 2-3 divided doses.");
        ginkgo.setPrecautions("May increase bleeding risk. Interactions with blood thinners. Avoid before surgery.");
        ginkgo.setImageUrl("/assets/images/plants/ginkgo.jpg");
        ginkgo.setMedicinalUses(List.of("Memory improvement", "Cognitive support", "Antioxidant", "Improved circulation"));
        ginkgo.setActiveCompounds(List.of("Ginkgolides", "Bilobalide", "Flavonoids"));
        addSymptomRelationships(ginkgo, symptomMap,
            Map.of("Fatigue", 0.70, "Anxiety", 0.65));
        plants.add(ginkgo);
        
        // Feverfew
        Plant feverfew = new Plant();
        feverfew.setName("Feverfew");
        feverfew.setScientificName("Tanacetum parthenium");
        feverfew.setDescription("Traditional remedy for migraines and inflammatory conditions. Reduces frequency of headaches.");
        feverfew.setDosage("50-150mg dried leaf extract daily. Fresh leaves: 2-3 leaves daily.");
        feverfew.setPrecautions("May cause mouth ulcers. Not safe during pregnancy. Can interact with blood thinners.");
        feverfew.setImageUrl("/assets/images/plants/feverfew.jpg");
        feverfew.setMedicinalUses(List.of("Migraine relief", "Fever reduction", "Anti-inflammatory", "Arthritis support"));
        feverfew.setActiveCompounds(List.of("Parthenolide", "Tanetin"));
        addSymptomRelationships(feverfew, symptomMap,
            Map.of("Headache", 0.90, "Fever", 0.80, "Inflammation", 0.75, "Joint pain", 0.75));
        plants.add(feverfew);
        
        // Lemon Balm
        Plant lemonBalm = new Plant();
        lemonBalm.setName("Lemon Balm");
        lemonBalm.setScientificName("Melissa officinalis");
        lemonBalm.setDescription("Calming mint family herb with mild antiviral effects. Soothes anxiety and promotes sleep.");
        lemonBalm.setDosage("300-500mg extract daily. Tea: 1-2 cups daily.");
        lemonBalm.setPrecautions("Generally safe. Can cause drowsiness at high doses. May affect thyroid function.");
        lemonBalm.setImageUrl("/assets/images/plants/lemon-balm.jpg");
        lemonBalm.setMedicinalUses(List.of("Relaxation", "Anxiety relief", "Digestive aid", "Sleep aid", "Antiviral"));
        lemonBalm.setActiveCompounds(List.of("Rosmarinic acid", "Citral"));
        addSymptomRelationships(lemonBalm, symptomMap,
            Map.of("Anxiety", 0.85, "Stress", 0.85, "Digestive issues", 0.75, "Insomnia", 0.80));
        plants.add(lemonBalm);
        
        // Dandelion
        Plant dandelion = new Plant();
        dandelion.setName("Dandelion");
        dandelion.setScientificName("Taraxacum officinale");
        dandelion.setDescription("Common weed with powerful detoxifying properties. Supports liver and kidney function.");
        dandelion.setDosage("1-2g dried root or leaves as tea twice daily. Fresh leaves in salads.");
        dandelion.setPrecautions("May interact with diuretics or lithium. Can cause allergic reactions.");
        dandelion.setImageUrl("/assets/images/plants/dandelion.jpg");
        dandelion.setMedicinalUses(List.of("Diuretic", "Digestive aid", "Detoxification", "Anti-inflammatory"));
        dandelion.setActiveCompounds(List.of("Taraxacin", "Inulin", "Sesquiterpene lactones"));
        addSymptomRelationships(dandelion, symptomMap,
            Map.of("Digestive issues", 0.80, "Inflammation", 0.70));
        plants.add(dandelion);
        
        // Licorice Root
        Plant licorice = new Plant();
        licorice.setName("Licorice Root");
        licorice.setScientificName("Glycyrrhiza glabra");
        licorice.setDescription("Sweet root used for soothing sore throats and aiding digestion. Has anti-inflammatory properties.");
        licorice.setDosage("200-400mg extract or tea twice daily. Don't exceed 6 weeks continuous use.");
        licorice.setPrecautions("High doses can raise blood pressure. Avoid in heart disease, kidney disease, pregnancy.");
        licorice.setImageUrl("/assets/images/plants/licorice.jpg");
        licorice.setMedicinalUses(List.of("Cough relief", "Digestive aid", "Anti-inflammatory", "Immune support", "Adrenal support"));
        licorice.setActiveCompounds(List.of("Glycyrrhizin", "Glycyrrhetinic acid"));
        addSymptomRelationships(licorice, symptomMap,
            Map.of("Cough", 0.85, "Digestive issues", 0.80, "Inflammation", 0.75, "Respiratory issues", 0.80));
        plants.add(licorice);
        
        // Saffron
        Plant saffron = new Plant();
        saffron.setName("Saffron");
        saffron.setScientificName("Crocus sativus");
        saffron.setDescription("Valuable spice known for mood-supporting and antioxidant properties. Used in traditional medicine for depression.");
        saffron.setDosage("15-30mg daily in tea or supplement form.");
        saffron.setPrecautions("High doses can be toxic. Use with caution during pregnancy. Very expensive.");
        saffron.setImageUrl("/assets/images/plants/saffron.jpg");
        saffron.setMedicinalUses(List.of("Mood enhancement", "Anti-depressant", "Antioxidant", "Menstrual relief"));
        saffron.setActiveCompounds(List.of("Crocin", "Safranal", "Picrocrocin"));
        addSymptomRelationships(saffron, symptomMap,
            Map.of("Depression", 0.85, "Anxiety", 0.80, "Stress", 0.75));
        plants.add(saffron);
        
        // Moringa
        Plant moringa = new Plant();
        moringa.setName("Moringa");
        moringa.setScientificName("Moringa oleifera");
        moringa.setDescription("Nutrient-rich tree with leaves packed with vitamins, minerals, and protein. Known as the 'miracle tree'.");
        moringa.setDosage("1-2g powder daily. Fresh leaves: handful in meals. Capsules: 500-1000mg daily.");
        moringa.setPrecautions("High doses may cause digestive upset. Avoid root/bark during pregnancy.");
        moringa.setImageUrl("/assets/images/plants/moringa.jpg");
        moringa.setMedicinalUses(List.of("Nutrient rich", "Anti-inflammatory", "Immune support", "Antioxidant"));
        moringa.setActiveCompounds(List.of("Isothiocyanates", "Quercetin", "Chlorogenic acid"));
        addSymptomRelationships(moringa, symptomMap,
            Map.of("Immune weakness", 0.85, "Inflammation", 0.80, "Fatigue", 0.75));
        plants.add(moringa);
        
        // Brahmi
        Plant brahmi = new Plant();
        brahmi.setName("Brahmi");
        brahmi.setScientificName("Bacopa monnieri");
        brahmi.setDescription("Ayurvedic herb prized for enhancing memory and cognitive function. Powerful brain tonic.");
        brahmi.setDosage("250-500mg extract daily with meals.");
        brahmi.setPrecautions("May cause digestive upset. Can interact with thyroid medications. Takes weeks for effects.");
        brahmi.setImageUrl("/assets/images/plants/brahmi.jpg");
        brahmi.setMedicinalUses(List.of("Memory enhancement", "Anxiety relief", "Antioxidant", "Anti-inflammatory"));
        brahmi.setActiveCompounds(List.of("Bacosides", "Bacopasaponins"));
        addSymptomRelationships(brahmi, symptomMap,
            Map.of("Anxiety", 0.85, "Stress", 0.85, "Fatigue", 0.70));
        plants.add(brahmi);
        
        // Gotu Kola
        Plant gotuKola = new Plant();
        gotuKola.setName("Gotu Kola");
        gotuKola.setScientificName("Centella asiatica");
        gotuKola.setDescription("Ayurvedic herb for wound healing and cognitive support. Promotes collagen synthesis.");
        gotuKola.setDosage("500-1000mg extract daily. Tea: 1-2 cups daily.");
        gotuKola.setPrecautions("High doses may cause liver toxicity. Avoid during pregnancy. Can cause drowsiness.");
        gotuKola.setImageUrl("/assets/images/plants/gotu-kola.jpg");
        gotuKola.setMedicinalUses(List.of("Wound healing", "Cognitive support", "Anxiety relief", "Skin health"));
        gotuKola.setActiveCompounds(List.of("Asiaticoside", "Madecassoside", "Triterpenes"));
        addSymptomRelationships(gotuKola, symptomMap,
            Map.of("Wounds", 0.85, "Anxiety", 0.80, "Skin irritation", 0.80));
        plants.add(gotuKola);
        
        // Sage
        Plant sage = new Plant();
        sage.setName("Sage");
        sage.setScientificName("Salvia officinalis");
        sage.setDescription("Aromatic herb used in cooking and medicine. Antimicrobial and cognitive-supporting properties.");
        sage.setDosage("1-2g dried leaf in tea daily. Extract: 150-300mg daily.");
        sage.setPrecautions("Avoid high doses in pregnancy. Can interact with diabetes and seizure medications.");
        sage.setImageUrl("/assets/images/plants/sage.jpg");
        sage.setMedicinalUses(List.of("Digestive aid", "Cognitive support", "Antimicrobial", "Anti-inflammatory"));
        sage.setActiveCompounds(List.of("Thujone", "Rosmarinic acid", "Carnosic acid"));
        addSymptomRelationships(sage, symptomMap,
            Map.of("Digestive issues", 0.75, "Inflammation", 0.70, "Cold symptoms", 0.70));
        plants.add(sage);
        
        // Rosemary
        Plant rosemary = new Plant();
        rosemary.setName("Rosemary");
        rosemary.setScientificName("Rosmarinus officinalis");
        rosemary.setDescription("Aromatic herb with memory-boosting and antioxidant properties. Improves concentration.");
        rosemary.setDosage("1-2g dried leaf in tea daily. Essential oil: aromatherapy only.");
        rosemary.setPrecautions("May raise blood pressure in high doses. Essential oil not for internal use.");
        rosemary.setImageUrl("/assets/images/plants/rosemary.jpg");
        rosemary.setMedicinalUses(List.of("Cognitive support", "Digestive aid", "Antioxidant", "Antimicrobial"));
        rosemary.setActiveCompounds(List.of("Rosmarinic acid", "Carnosol", "Carnosic acid"));
        addSymptomRelationships(rosemary, symptomMap,
            Map.of("Fatigue", 0.75, "Headache", 0.70, "Stress", 0.75, "Digestive issues", 0.70));
        plants.add(rosemary);
        
        // Thyme
        Plant thyme = new Plant();
        thyme.setName("Thyme");
        thyme.setScientificName("Thymus vulgaris");
        thyme.setDescription("Culinary and medicinal herb with strong antimicrobial action. Excellent for respiratory health.");
        thyme.setDosage("1-2g dried herb in tea daily. Extract: 200-400mg daily.");
        thyme.setPrecautions("May interact with thyroid medications. High doses can cause digestive upset.");
        thyme.setImageUrl("/assets/images/plants/thyme.jpg");
        thyme.setMedicinalUses(List.of("Antimicrobial", "Respiratory support", "Antioxidant", "Digestive aid"));
        thyme.setActiveCompounds(List.of("Thymol", "Carvacrol", "Rosmarinic acid"));
        addSymptomRelationships(thyme, symptomMap,
            Map.of("Cough", 0.85, "Respiratory issues", 0.85, "Infections", 0.80, "Digestive issues", 0.70));
        plants.add(thyme);
        
        // Yarrow
        Plant yarrow = new Plant();
        yarrow.setName("Yarrow");
        yarrow.setScientificName("Achillea millefolium");
        yarrow.setDescription("Traditional wound healing herb used to stop bleeding. Anti-inflammatory and fever-reducing properties.");
        yarrow.setDosage("2-4g dried herb per day in tea or poultice for wounds.");
        yarrow.setPrecautions("Can cause allergies in sensitive people. Avoid during pregnancy. May interact with sedatives.");
        yarrow.setImageUrl("/assets/images/plants/yarrow.jpg");
        yarrow.setMedicinalUses(List.of("Wound healing", "Anti-inflammatory", "Fever reduction", "Menstrual support"));
        yarrow.setActiveCompounds(List.of("Achilleine", "Flavonoids", "Volatile oils"));
        addSymptomRelationships(yarrow, symptomMap,
            Map.of("Wounds", 0.85, "Fever", 0.80, "Inflammation", 0.75));
        plants.add(yarrow);
        
        // Elderberry
        Plant elderberry = new Plant();
        elderberry.setName("Elderberry");
        elderberry.setScientificName("Sambucus nigra");
        elderberry.setDescription("Traditional cold and flu remedy with powerful antiviral properties. Rich in antioxidants.");
        elderberry.setDosage("10-15ml syrup 2-3 times daily during illness. Extract: 300mg daily.");
        elderberry.setPrecautions("Raw berries/seeds can be toxic—use processed forms only. May cause digestive upset.");
        elderberry.setImageUrl("/assets/images/plants/elderberry.jpg");
        elderberry.setMedicinalUses(List.of("Immune support", "Cold relief", "Antioxidant", "Antiviral"));
        elderberry.setActiveCompounds(List.of("Anthocyanins", "Flavonoids", "Vitamin C"));
        addSymptomRelationships(elderberry, symptomMap,
            Map.of("Cold symptoms", 0.90, "Immune weakness", 0.85, "Fever", 0.80, "Cough", 0.75));
        plants.add(elderberry);
        
        // Nettle
        Plant nettle = new Plant();
        nettle.setName("Nettle");
        nettle.setScientificName("Urtica dioica");
        nettle.setDescription("Mineral-rich plant valued for allergies, arthritis, and as iron supplement. Very nutritious.");
        nettle.setDosage("2-4g dried leaf tea or 300-600mg extract once/twice daily.");
        nettle.setPrecautions("Fresh plant stings. Consult physician for long-term use. May affect blood pressure.");
        nettle.setImageUrl("/assets/images/plants/nettle.jpg");
        nettle.setMedicinalUses(List.of("Allergy relief", "Anti-inflammatory", "Diuretic", "Iron supplement"));
        nettle.setActiveCompounds(List.of("Histamine", "Serotonin", "Minerals"));
        addSymptomRelationships(nettle, symptomMap,
            Map.of("Joint pain", 0.80, "Inflammation", 0.80, "Fatigue", 0.70));
        plants.add(nettle);
        
        // Passionflower
        Plant passionflower = new Plant();
        passionflower.setName("Passionflower");
        passionflower.setScientificName("Passiflora incarnata");
        passionflower.setDescription("Calming flower with sedative effects. Used for sleep and relaxation.");
        passionflower.setDosage("250-400mg extract or 1-2g dried herb in tea at bedtime.");
        passionflower.setPrecautions("May cause drowsiness. Don't combine with sedatives. Avoid during pregnancy.");
        passionflower.setImageUrl("/assets/images/plants/passionflower.jpg");
        passionflower.setMedicinalUses(List.of("Anxiety relief", "Sleep aid", "Stress relief", "Sedative"));
        passionflower.setActiveCompounds(List.of("Flavonoids", "Alkaloids", "Maltol"));
        addSymptomRelationships(passionflower, symptomMap,
            Map.of("Anxiety", 0.85, "Insomnia", 0.90, "Sleep disorders", 0.90, "Stress", 0.85));
        plants.add(passionflower);
        
        // Cinnamon
        Plant cinnamon = new Plant();
        cinnamon.setName("Cinnamon");
        cinnamon.setScientificName("Cinnamomum verum");
        cinnamon.setDescription("Aromatic spice with blood sugar regulating and anti-inflammatory properties.");
        cinnamon.setDosage("1-3g powder or 300-500mg extract daily with food.");
        cinnamon.setPrecautions("High doses may be toxic. Can affect blood sugar. Use Ceylon variety preferred.");
        cinnamon.setImageUrl("/assets/images/plants/cinnamon.jpg");
        cinnamon.setMedicinalUses(List.of("Blood sugar control", "Digestive aid", "Anti-inflammatory", "Antioxidant"));
        cinnamon.setActiveCompounds(List.of("Cinnamaldehyde", "Cinnamic acid", "Eugenol"));
        addSymptomRelationships(cinnamon, symptomMap,
            Map.of("Digestive issues", 0.75, "Inflammation", 0.80, "Cold symptoms", 0.70));
        plants.add(cinnamon);
        
        // Cardamom
        Plant cardamom = new Plant();
        cardamom.setName("Cardamom");
        cardamom.setScientificName("Elettaria cardamomum");
        cardamom.setDescription("Aromatic seeds valued for digestive and respiratory relief. Used in Ayurvedic medicine.");
        cardamom.setDosage("200-500mg extract or 1-2g seeds daily in tea or food.");
        cardamom.setPrecautions("Excess can cause digestive issues. Generally safe in food amounts.");
        cardamom.setImageUrl("/assets/images/plants/cardamom.jpg");
        cardamom.setMedicinalUses(List.of("Digestive aid", "Antioxidant", "Anti-inflammatory", "Breath freshener"));
        cardamom.setActiveCompounds(List.of("Cineole", "Terpinyl acetate", "Limonene"));
        addSymptomRelationships(cardamom, symptomMap,
            Map.of("Digestive issues", 0.80, "Nausea", 0.70, "Respiratory issues", 0.70));
        plants.add(cardamom);
        
        // Black Pepper
        Plant blackPepper = new Plant();
        blackPepper.setName("Black Pepper");
        blackPepper.setScientificName("Piper nigrum");
        blackPepper.setDescription("King of Spices aiding digestion and enhancing nutrient absorption. Contains piperine.");
        blackPepper.setDosage("1-2g powder or 10-20 drops extract per day with meals.");
        blackPepper.setPrecautions("Excess intake can cause stomach irritation. Generally safe as spice.");
        blackPepper.setImageUrl("/assets/images/plants/black-pepper.jpg");
        blackPepper.setMedicinalUses(List.of("Digestive aid", "Antioxidant", "Anti-inflammatory", "Absorption enhancer"));
        blackPepper.setActiveCompounds(List.of("Piperine", "Chavicine", "Essential oils"));
        addSymptomRelationships(blackPepper, symptomMap,
            Map.of("Digestive issues", 0.75, "Cold symptoms", 0.65, "Inflammation", 0.70));
        plants.add(blackPepper);
        
        // Clove
        Plant clove = new Plant();
        clove.setName("Clove");
        clove.setScientificName("Syzygium aromaticum");
        clove.setDescription("Powerful antiseptic flower buds used for dental care and digestion. Strong antimicrobial.");
        clove.setDosage("1-2 whole cloves daily. Oil: diluted for topical use only.");
        clove.setPrecautions("Oil is very strong—always dilute. Avoid excessive internal use. Can cause allergic reactions.");
        clove.setImageUrl("/assets/images/plants/clove.jpg");
        clove.setMedicinalUses(List.of("Dental pain relief", "Antiseptic", "Antimicrobial", "Anti-inflammatory"));
        clove.setActiveCompounds(List.of("Eugenol", "Acetyl eugenol", "Beta-caryophyllene"));
        addSymptomRelationships(clove, symptomMap,
            Map.of("Pain", 0.80, "Digestive issues", 0.70, "Respiratory issues", 0.70, "Infections", 0.80));
        plants.add(clove);
        
        // Fenugreek
        Plant fenugreek = new Plant();
        fenugreek.setName("Fenugreek");
        fenugreek.setScientificName("Trigonella foenum-graecum");
        fenugreek.setDescription("Seeds promote healthy blood sugar and lactation. Used in Indian cuisine and Ayurveda.");
        fenugreek.setDosage("500-2000mg seed powder daily with meals.");
        fenugreek.setPrecautions("Avoid high doses when pregnant. May lower blood sugar. Can cause maple syrup odor.");
        fenugreek.setImageUrl("/assets/images/plants/fenugreek.jpg");
        fenugreek.setMedicinalUses(List.of("Blood sugar control", "Lactation support", "Digestive aid", "Anti-inflammatory"));
        fenugreek.setActiveCompounds(List.of("Trigonelline", "Diosgenin", "Saponins"));
        addSymptomRelationships(fenugreek, symptomMap,
            Map.of("Digestive issues", 0.75, "Inflammation", 0.70));
        plants.add(fenugreek);
        
        // Hibiscus
        Plant hibiscus = new Plant();
        hibiscus.setName("Hibiscus");
        hibiscus.setScientificName("Hibiscus sabdariffa");
        hibiscus.setDescription("Bright tart tea supporting heart and vessel health. Helps reduce blood pressure.");
        hibiscus.setDosage("1500-2000mg flower extract or 1-2 cups tea daily.");
        hibiscus.setPrecautions("May lower blood pressure excessively with medication. Can affect blood sugar.");
        hibiscus.setImageUrl("/assets/images/plants/hibiscus.jpg");
        hibiscus.setMedicinalUses(List.of("Blood pressure reduction", "Antioxidant", "Anti-inflammatory", "Diuretic"));
        hibiscus.setActiveCompounds(List.of("Anthocyanins", "Organic acids", "Polyphenols"));
        addSymptomRelationships(hibiscus, symptomMap,
            Map.of("High blood pressure", 0.85, "Inflammation", 0.70));
        plants.add(hibiscus);
        
        // Frankincense
        Plant frankincense = new Plant();
        frankincense.setName("Frankincense");
        frankincense.setScientificName("Boswellia serrata");
        frankincense.setDescription("Aromatic resin with anti-inflammatory power, especially for arthritis. Sacred in ancient times.");
        frankincense.setDosage("300-500mg extract of boswellic acids twice daily.");
        frankincense.setPrecautions("May interact with immunosuppressive drugs. Can cause digestive upset.");
        frankincense.setImageUrl("/assets/images/plants/frankincense.jpg");
        frankincense.setMedicinalUses(List.of("Anti-inflammatory", "Arthritis relief", "Stress relief", "Immune modulator"));
        frankincense.setActiveCompounds(List.of("Boswellic acids", "Incensole acetate"));
        addSymptomRelationships(frankincense, symptomMap,
            Map.of("Joint pain", 0.85, "Inflammation", 0.90, "Stress", 0.70));
        plants.add(frankincense);
        
        // Myrrh
        Plant myrrh = new Plant();
        myrrh.setName("Myrrh");
        myrrh.setScientificName("Commiphora myrrha");
        myrrh.setDescription("Ancient resin prized for wound healing and oral care since biblical times.");
        myrrh.setDosage("250-500mg extract or diluted tincture for oral/topical use.");
        myrrh.setPrecautions("Large doses can be toxic. Monitor for allergy with topical use. Not for pregnant women.");
        myrrh.setImageUrl("/assets/images/plants/myrrh.jpg");
        myrrh.setMedicinalUses(List.of("Antiseptic", "Anti-inflammatory", "Oral health", "Wound healing"));
        myrrh.setActiveCompounds(List.of("Terpenoids", "Sesquiterpenes", "Furanoeudesma"));
        addSymptomRelationships(myrrh, symptomMap,
            Map.of("Wounds", 0.80, "Inflammation", 0.75, "Infections", 0.75));
        plants.add(myrrh);
        
        return plants;
    }
    
    private void addSymptomRelationships(Plant plant, Map<String, Symptom> symptomMap, Map<String, Double> symptomEffectiveness) {
        List<PlantSymptom> plantSymptoms = new ArrayList<>();
        
        for (Map.Entry<String, Double> entry : symptomEffectiveness.entrySet()) {
            Symptom symptom = symptomMap.get(entry.getKey());
            if (symptom != null) {
                PlantSymptom ps = new PlantSymptom();
                ps.setPlant(plant);
                ps.setSymptom(symptom);
                ps.setEffectivenessScore(entry.getValue());
                plantSymptoms.add(ps);
            }
        }
        
        plant.setPlantSymptoms(plantSymptoms);
    }
}
