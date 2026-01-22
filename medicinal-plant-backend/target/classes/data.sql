-- ========================================
-- MediPlant Database Initialization
-- Seed data for medicinal plants
-- ========================================

-- Aloe Vera
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'Aloe Vera',
    'Aloe barbadensis miller',
    'Aloe Vera is a succulent plant species that has been used medicinally for thousands of years. The gel inside its leaves contains vitamins, minerals, amino acids, and antioxidants with powerful healing properties.',
    'SUCCULENT',
    'SAFE',
    'https://images.unsplash.com/photo-1596548438137-d51ea5c83ca5?w=400',
    'Apply gel topically 2-3 times daily to affected area. For internal use: 50-200mg of aloe latex daily, consult healthcare provider first.',
    'May cause allergic reactions in sensitive individuals. Latex form can cause cramps and diarrhea. Not recommended during pregnancy.',
    'CULTIVATED',
    4.8,
    234,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'Aloe Vera'), 'Burn treatment'),
    ((SELECT id FROM plants WHERE name = 'Aloe Vera'), 'Skin healing'),
    ((SELECT id FROM plants WHERE name = 'Aloe Vera'), 'Digestive aid'),
    ((SELECT id FROM plants WHERE name = 'Aloe Vera'), 'Anti-inflammatory'),
    ((SELECT id FROM plants WHERE name = 'Aloe Vera'), 'Wound healing'),
    ((SELECT id FROM plants WHERE name = 'Aloe Vera'), 'Moisturizing');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'Aloe Vera'), 'Aloin'),
    ((SELECT id FROM plants WHERE name = 'Aloe Vera'), 'Polysaccharides'),
    ((SELECT id FROM plants WHERE name = 'Aloe Vera'), 'Vitamins A, C, E');

-- Ginger
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'Ginger',
    'Zingiber officinale',
    'Ginger is a flowering plant whose rhizome is widely used as a spice and in traditional medicine. It contains gingerol, a bioactive compound with powerful medicinal properties.',
    'HERB',
    'SAFE',
    'https://images.unsplash.com/photo-1615485500834-bc10199bc768?w=400',
    '1-3g dried root daily or 1-2 inches fresh root. Can be consumed as tea, in cooking, or as supplement.',
    'May interact with blood thinning medications (warfarin). High doses may cause heartburn or upset stomach.',
    'CULTIVATED',
    4.7,
    189,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'Ginger'), 'Nausea relief'),
    ((SELECT id FROM plants WHERE name = 'Ginger'), 'Anti-inflammatory'),
    ((SELECT id FROM plants WHERE name = 'Ginger'), 'Digestive aid'),
    ((SELECT id FROM plants WHERE name = 'Ginger'), 'Pain relief'),
    ((SELECT id FROM plants WHERE name = 'Ginger'), 'Immune booster'),
    ((SELECT id FROM plants WHERE name = 'Ginger'), 'Circulation improvement');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'Ginger'), 'Gingerol'),
    ((SELECT id FROM plants WHERE name = 'Ginger'), 'Shogaol'),
    ((SELECT id FROM plants WHERE name = 'Ginger'), 'Zingerone');

-- Turmeric
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'Turmeric',
    'Curcuma longa',
    'Turmeric is a bright yellow spice derived from the Curcuma longa plant. Curcumin, its active compound, has potent anti-inflammatory and antioxidant effects.',
    'HERB',
    'SAFE',
    'https://images.unsplash.com/photo-1615485290382-441e4d049cb5?w=400',
    '500-1000mg curcumin extract daily with meals. Best absorbed with black pepper (piperine).',
    'May increase bleeding risk and interact with blood thinners. Can cause stomach upset in high doses.',
    'CULTIVATED',
    4.9,
    312,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'Turmeric'), 'Anti-inflammatory'),
    ((SELECT id FROM plants WHERE name = 'Turmeric'), 'Antioxidant'),
    ((SELECT id FROM plants WHERE name = 'Turmeric'), 'Pain relief'),
    ((SELECT id FROM plants WHERE name = 'Turmeric'), 'Immune support'),
    ((SELECT id FROM plants WHERE name = 'Turmeric'), 'Liver health'),
    ((SELECT id FROM plants WHERE name = 'Turmeric'), 'Brain health');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'Turmeric'), 'Curcumin'),
    ((SELECT id FROM plants WHERE name = 'Turmeric'), 'Turmerone'),
    ((SELECT id FROM plants WHERE name = 'Turmeric'), 'Demethoxycurcumin');

-- Eucalyptus
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'Eucalyptus',
    'Eucalyptus globulus',
    'Eucalyptus is a fast-growing evergreen tree native to Australia. Its leaves contain eucalyptol (cineole), which provides respiratory and antimicrobial benefits.',
    'TREE',
    'USE_WITH_CAUTION',
    'https://images.unsplash.com/photo-1584126459330-0e15a7cefbbc?w=400',
    'Use as steam inhalation (3-5 drops in hot water) or topical application (diluted essential oil). Do not ingest.',
    'Toxic if ingested. Essential oil must be diluted before topical use. Keep away from children and pets.',
    'CULTIVATED',
    4.5,
    145,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'Eucalyptus'), 'Respiratory relief'),
    ((SELECT id FROM plants WHERE name = 'Eucalyptus'), 'Antiseptic'),
    ((SELECT id FROM plants WHERE name = 'Eucalyptus'), 'Decongestant'),
    ((SELECT id FROM plants WHERE name = 'Eucalyptus'), 'Anti-inflammatory'),
    ((SELECT id FROM plants WHERE name = 'Eucalyptus'), 'Pain relief'),
    ((SELECT id FROM plants WHERE name = 'Eucalyptus'), 'Antimicrobial');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'Eucalyptus'), 'Eucalyptol (Cineole)'),
    ((SELECT id FROM plants WHERE name = 'Eucalyptus'), 'Alpha-pinene'),
    ((SELECT id FROM plants WHERE name = 'Eucalyptus'), 'Limonene');

-- Lavender
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'Lavender',
    'Lavandula angustifolia',
    'Lavender is a flowering plant in the mint family, prized for its calming fragrance and therapeutic properties. Contains linalool and linalyl acetate.',
    'SHRUB',
    'SAFE',
    'https://images.unsplash.com/photo-1611419010196-e30d6b5b3a9e?w=400',
    '1-2 drops essential oil in diffuser or on pillow. 1-2 cups lavender tea daily. Topical: diluted oil for wounds.',
    'May cause drowsiness. Avoid before driving or operating machinery. Can cause allergic skin reactions.',
    'CULTIVATED',
    4.8,
    267,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'Lavender'), 'Relaxation'),
    ((SELECT id FROM plants WHERE name = 'Lavender'), 'Sleep aid'),
    ((SELECT id FROM plants WHERE name = 'Lavender'), 'Anxiety relief'),
    ((SELECT id FROM plants WHERE name = 'Lavender'), 'Antiseptic'),
    ((SELECT id FROM plants WHERE name = 'Lavender'), 'Headache relief'),
    ((SELECT id FROM plants WHERE name = 'Lavender'), 'Stress reduction');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'Lavender'), 'Linalool'),
    ((SELECT id FROM plants WHERE name = 'Lavender'), 'Linalyl acetate'),
    ((SELECT id FROM plants WHERE name = 'Lavender'), 'Camphor');

-- Echinacea
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'Echinacea',
    'Echinacea purpurea',
    'Echinacea, also known as purple coneflower, is a group of herbaceous flowering plants. It stimulates the immune system and has anti-inflammatory properties.',
    'HERB',
    'SAFE',
    'https://images.unsplash.com/photo-1531450807634-52b1c82b4d6a?w=400',
    '300-500mg extract 3 times daily during illness. Start at first sign of symptoms. Do not use for more than 8 weeks.',
    'May cause allergic reactions in people with plant allergies (daisy family). Not recommended for autoimmune conditions.',
    'CULTIVATED',
    4.6,
    198,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'Echinacea'), 'Immune support'),
    ((SELECT id FROM plants WHERE name = 'Echinacea'), 'Cold prevention'),
    ((SELECT id FROM plants WHERE name = 'Echinacea'), 'Anti-inflammatory'),
    ((SELECT id FROM plants WHERE name = 'Echinacea'), 'Wound healing'),
    ((SELECT id FROM plants WHERE name = 'Echinacea'), 'Antiviral'),
    ((SELECT id FROM plants WHERE name = 'Echinacea'), 'Infection fighter');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'Echinacea'), 'Echinacoside'),
    ((SELECT id FROM plants WHERE name = 'Echinacea'), 'Cichoric acid'),
    ((SELECT id FROM plants WHERE name = 'Echinacea'), 'Polysaccharides');

-- Chamomile
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'Chamomile',
    'Matricaria chamomilla',
    'Chamomile is a daisy-like plant that has been used for centuries as a natural remedy. Contains apigenin, an antioxidant that binds to brain receptors.',
    'HERB',
    'SAFE',
    'https://images.unsplash.com/photo-1563630423918-b58f07336ac6?w=400',
    '1-4 cups chamomile tea daily. Topical: cooled tea or diluted oil for skin conditions.',
    'May cause allergic reactions in people allergic to ragweed. Avoid if taking blood thinners.',
    'CULTIVATED',
    4.7,
    221,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'Chamomile'), 'Relaxation'),
    ((SELECT id FROM plants WHERE name = 'Chamomile'), 'Digestive aid'),
    ((SELECT id FROM plants WHERE name = 'Chamomile'), 'Anti-inflammatory'),
    ((SELECT id FROM plants WHERE name = 'Chamomile'), 'Sleep aid'),
    ((SELECT id FROM plants WHERE name = 'Chamomile'), 'Anxiety relief'),
    ((SELECT id FROM plants WHERE name = 'Chamomile'), 'Skin soothing');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'Chamomile'), 'Apigenin'),
    ((SELECT id FROM plants WHERE name = 'Chamomile'), 'Chamazulene'),
    ((SELECT id FROM plants WHERE name = 'Chamomile'), 'Bisabolol');

-- Peppermint
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'Peppermint',
    'Mentha piperita',
    'Peppermint is a hybrid mint plant with high menthol content. It has been used for thousands of years for digestive and respiratory issues.',
    'HERB',
    'SAFE',
    'https://images.unsplash.com/photo-1628556270448-4d4e4148e1b1?w=400',
    '1-2 cups peppermint tea daily. For IBS: enteric-coated capsules 0.2-0.4ml 3 times daily. Topical: diluted oil for pain.',
    'May worsen acid reflux. Can interact with certain medications. Avoid applying oil directly to face of infants.',
    'CULTIVATED',
    4.8,
    256,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'Peppermint'), 'Digestive aid'),
    ((SELECT id FROM plants WHERE name = 'Peppermint'), 'Headache relief'),
    ((SELECT id FROM plants WHERE name = 'Peppermint'), 'Nausea relief'),
    ((SELECT id FROM plants WHERE name = 'Peppermint'), 'Muscle pain relief'),
    ((SELECT id FROM plants WHERE name = 'Peppermint'), 'Respiratory relief'),
    ((SELECT id FROM plants WHERE name = 'Peppermint'), 'Antimicrobial');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'Peppermint'), 'Menthol'),
    ((SELECT id FROM plants WHERE name = 'Peppermint'), 'Menthone'),
    ((SELECT id FROM plants WHERE name = 'Peppermint'), 'Rosmarinic acid');

-- St. John's Wort
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'St. Johns Wort',
    'Hypericum perforatum',
    'St. John''s Wort is a flowering plant traditionally used for mental health conditions. Contains hypericin and hyperforin with antidepressant properties.',
    'HERB',
    'USE_WITH_CAUTION',
    'https://images.unsplash.com/photo-1598453421679-8c5b3c6c6a2f?w=400',
    '300mg extract 3 times daily for depression. Topical application for wounds. Takes 4-6 weeks for full effect.',
    'Interacts with many medications including antidepressants, birth control, and blood thinners. Increases sun sensitivity.',
    'CULTIVATED',
    4.4,
    167,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'St. Johns Wort'), 'Depression relief'),
    ((SELECT id FROM plants WHERE name = 'St. Johns Wort'), 'Anxiety relief'),
    ((SELECT id FROM plants WHERE name = 'St. Johns Wort'), 'Mood enhancement'),
    ((SELECT id FROM plants WHERE name = 'St. Johns Wort'), 'Wound healing'),
    ((SELECT id FROM plants WHERE name = 'St. Johns Wort'), 'Anti-inflammatory'),
    ((SELECT id FROM plants WHERE name = 'St. Johns Wort'), 'Nerve pain relief');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'St. Johns Wort'), 'Hypericin'),
    ((SELECT id FROM plants WHERE name = 'St. Johns Wort'), 'Hyperforin'),
    ((SELECT id FROM plants WHERE name = 'St. Johns Wort'), 'Flavonoids');

-- Garlic
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'Garlic',
    'Allium sativum',
    'Garlic is a species in the onion family known for its medicinal and culinary uses. Contains allicin, a sulfur compound with powerful health benefits.',
    'HERB',
    'SAFE',
    'https://images.unsplash.com/photo-1583090778628-cb3d27d16c52?w=400',
    '1-2 raw cloves daily or 600-1200mg aged garlic extract. Best consumed raw for maximum benefit.',
    'May increase bleeding risk. Can cause bad breath and body odor. May interact with blood thinning medications.',
    'CULTIVATED',
    4.6,
    203,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'Garlic'), 'Heart health'),
    ((SELECT id FROM plants WHERE name = 'Garlic'), 'Immune support'),
    ((SELECT id FROM plants WHERE name = 'Garlic'), 'Antibacterial'),
    ((SELECT id FROM plants WHERE name = 'Garlic'), 'Antiviral'),
    ((SELECT id FROM plants WHERE name = 'Garlic'), 'Blood pressure reduction'),
    ((SELECT id FROM plants WHERE name = 'Garlic'), 'Cholesterol management');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'Garlic'), 'Allicin'),
    ((SELECT id FROM plants WHERE name = 'Garlic'), 'Diallyl disulfide'),
    ((SELECT id FROM plants WHERE name = 'Garlic'), 'S-allyl cysteine');

-- Holy Basil (Tulsi)
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'Holy Basil',
    'Ocimum sanctum',
    'Holy Basil, or Tulsi, is a sacred plant in Hinduism and Ayurvedic medicine. It is an adaptogen that helps the body cope with stress and has antimicrobial properties.',
    'HERB',
    'SAFE',
    'https://images.unsplash.com/photo-1617532116828-10bab0bc8a56?w=400',
    '300-600mg extract daily. 1-2 cups tulsi tea daily. Fresh leaves can be chewed (2-3 leaves).',
    'May lower blood sugar. Avoid during pregnancy. May slow blood clotting.',
    'CULTIVATED',
    4.9,
    289,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'Holy Basil'), 'Stress relief'),
    ((SELECT id FROM plants WHERE name = 'Holy Basil'), 'Immune support'),
    ((SELECT id FROM plants WHERE name = 'Holy Basil'), 'Anti-inflammatory'),
    ((SELECT id FROM plants WHERE name = 'Holy Basil'), 'Respiratory support'),
    ((SELECT id FROM plants WHERE name = 'Holy Basil'), 'Antioxidant'),
    ((SELECT id FROM plants WHERE name = 'Holy Basil'), 'Adaptogen');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'Holy Basil'), 'Eugenol'),
    ((SELECT id FROM plants WHERE name = 'Holy Basil'), 'Ursolic acid'),
    ((SELECT id FROM plants WHERE name = 'Holy Basil'), 'Rosmarinic acid');

-- Neem
INSERT INTO plants (name, scientific_name, description, plant_type, safety_rating, image_url, dosage, precautions, availability, rating, review_count, created_at, updated_at)
VALUES (
    'Neem',
    'Azadirachta indica',
    'Neem is a tree native to India with powerful medicinal properties. Nearly every part of the tree has therapeutic uses, from leaves to bark to seeds.',
    'TREE',
    'USE_WITH_CAUTION',
    'https://images.unsplash.com/photo-1592813704384-5e0c0b03df99?w=400',
    'Topical: neem oil or paste for skin conditions. Oral: neem leaf extract 500mg twice daily. Neem toothpaste for dental health.',
    'Not for pregnant or breastfeeding women. May lower blood sugar and blood pressure. Can cause infertility in high doses.',
    'CULTIVATED',
    4.5,
    178,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO plant_medicinal_uses (plant_id, medicinal_use) VALUES 
    ((SELECT id FROM plants WHERE name = 'Neem'), 'Antibacterial'),
    ((SELECT id FROM plants WHERE name = 'Neem'), 'Antiviral'),
    ((SELECT id FROM plants WHERE name = 'Neem'), 'Antifungal'),
    ((SELECT id FROM plants WHERE name = 'Neem'), 'Skin health'),
    ((SELECT id FROM plants WHERE name = 'Neem'), 'Dental health'),
    ((SELECT id FROM plants WHERE name = 'Neem'), 'Immune support'),
    ((SELECT id FROM plants WHERE name = 'Neem'), 'Blood purifier');

INSERT INTO plant_active_compounds (plant_id, compound) VALUES 
    ((SELECT id FROM plants WHERE name = 'Neem'), 'Azadirachtin'),
    ((SELECT id FROM plants WHERE name = 'Neem'), 'Nimbin'),
    ((SELECT id FROM plants WHERE name = 'Neem'), 'Nimbidin');

-- Add symptoms data
INSERT INTO symptoms (name, description, category) VALUES
('Burns', 'Thermal injuries to skin tissue', 'Skin'),
('Cuts', 'Minor wounds and lacerations', 'Skin'),
('Digestive issues', 'General digestive discomfort', 'Digestive'),
('Inflammation', 'Swelling and redness of tissue', 'General'),
('Skin irritation', 'Redness, itching, or discomfort of skin', 'Skin'),
('Nausea', 'Feeling of sickness with urge to vomit', 'Digestive'),
('Pain', 'Physical discomfort or soreness', 'General'),
('Headache', 'Pain in head or upper neck', 'Neurological'),
('Arthritis', 'Joint inflammation and pain', 'Musculoskeletal'),
('Cough', 'Reflex to clear airways', 'Respiratory'),
('Cold', 'Upper respiratory tract infection', 'Respiratory'),
('Respiratory congestion', 'Blocked nasal passages', 'Respiratory'),
('Anxiety', 'Excessive worry and nervousness', 'Mental Health'),
('Insomnia', 'Difficulty falling or staying asleep', 'Sleep'),
('Stress', 'Mental or emotional strain', 'Mental Health'),
('Wounds', 'Injuries breaking the skin', 'Skin'),
('Immune weakness', 'Reduced immune system function', 'Immune'),
('Flu', 'Influenza viral infection', 'Respiratory'),
('Sore throat', 'Pain or irritation in throat', 'Respiratory'),
('Indigestion', 'Difficulty digesting food', 'Digestive'),
('Depression', 'Persistent sadness and loss of interest', 'Mental Health'),
('High blood pressure', 'Elevated arterial pressure', 'Cardiovascular'),
('High cholesterol', 'Elevated blood lipid levels', 'Cardiovascular'),
('Fever', 'Elevated body temperature', 'General'),
('Fatigue', 'Extreme tiredness', 'General');

-- Link plants to symptoms
-- Aloe Vera symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'Aloe Vera'), (SELECT id FROM symptoms WHERE name = 'Burns'), 0.95),
((SELECT id FROM plants WHERE name = 'Aloe Vera'), (SELECT id FROM symptoms WHERE name = 'Cuts'), 0.85),
((SELECT id FROM plants WHERE name = 'Aloe Vera'), (SELECT id FROM symptoms WHERE name = 'Digestive issues'), 0.70),
((SELECT id FROM plants WHERE name = 'Aloe Vera'), (SELECT id FROM symptoms WHERE name = 'Inflammation'), 0.75),
((SELECT id FROM plants WHERE name = 'Aloe Vera'), (SELECT id FROM symptoms WHERE name = 'Skin irritation'), 0.90),
((SELECT id FROM plants WHERE name = 'Aloe Vera'), (SELECT id FROM symptoms WHERE name = 'Wounds'), 0.85);

-- Ginger symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'Ginger'), (SELECT id FROM symptoms WHERE name = 'Nausea'), 0.90),
((SELECT id FROM plants WHERE name = 'Ginger'), (SELECT id FROM symptoms WHERE name = 'Digestive issues'), 0.85),
((SELECT id FROM plants WHERE name = 'Ginger'), (SELECT id FROM symptoms WHERE name = 'Inflammation'), 0.80),
((SELECT id FROM plants WHERE name = 'Ginger'), (SELECT id FROM symptoms WHERE name = 'Pain'), 0.75),
((SELECT id FROM plants WHERE name = 'Ginger'), (SELECT id FROM symptoms WHERE name = 'Headache'), 0.70),
((SELECT id FROM plants WHERE name = 'Ginger'), (SELECT id FROM symptoms WHERE name = 'Arthritis'), 0.75);

-- Turmeric symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'Turmeric'), (SELECT id FROM symptoms WHERE name = 'Inflammation'), 0.95),
((SELECT id FROM plants WHERE name = 'Turmeric'), (SELECT id FROM symptoms WHERE name = 'Pain'), 0.85),
((SELECT id FROM plants WHERE name = 'Turmeric'), (SELECT id FROM symptoms WHERE name = 'Arthritis'), 0.90),
((SELECT id FROM plants WHERE name = 'Turmeric'), (SELECT id FROM symptoms WHERE name = 'Digestive issues'), 0.75);

-- Eucalyptus symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'Eucalyptus'), (SELECT id FROM symptoms WHERE name = 'Cough'), 0.85),
((SELECT id FROM plants WHERE name = 'Eucalyptus'), (SELECT id FROM symptoms WHERE name = 'Cold'), 0.80),
((SELECT id FROM plants WHERE name = 'Eucalyptus'), (SELECT id FROM symptoms WHERE name = 'Respiratory congestion'), 0.90),
((SELECT id FROM plants WHERE name = 'Eucalyptus'), (SELECT id FROM symptoms WHERE name = 'Sore throat'), 0.75),
((SELECT id FROM plants WHERE name = 'Eucalyptus'), (SELECT id FROM symptoms WHERE name = 'Pain'), 0.70);

-- Lavender symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'Lavender'), (SELECT id FROM symptoms WHERE name = 'Anxiety'), 0.90),
((SELECT id FROM plants WHERE name = 'Lavender'), (SELECT id FROM symptoms WHERE name = 'Insomnia'), 0.85),
((SELECT id FROM plants WHERE name = 'Lavender'), (SELECT id FROM symptoms WHERE name = 'Stress'), 0.88),
((SELECT id FROM plants WHERE name = 'Lavender'), (SELECT id FROM symptoms WHERE name = 'Wounds'), 0.75),
((SELECT id FROM plants WHERE name = 'Lavender'), (SELECT id FROM symptoms WHERE name = 'Headache'), 0.80);

-- Echinacea symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'Echinacea'), (SELECT id FROM symptoms WHERE name = 'Cold'), 0.85),
((SELECT id FROM plants WHERE name = 'Echinacea'), (SELECT id FROM symptoms WHERE name = 'Flu'), 0.80),
((SELECT id FROM plants WHERE name = 'Echinacea'), (SELECT id FROM symptoms WHERE name = 'Immune weakness'), 0.90),
((SELECT id FROM plants WHERE name = 'Echinacea'), (SELECT id FROM symptoms WHERE name = 'Sore throat'), 0.75),
((SELECT id FROM plants WHERE name = 'Echinacea'), (SELECT id FROM symptoms WHERE name = 'Wounds'), 0.70);

-- Chamomile symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'Chamomile'), (SELECT id FROM symptoms WHERE name = 'Anxiety'), 0.85),
((SELECT id FROM plants WHERE name = 'Chamomile'), (SELECT id FROM symptoms WHERE name = 'Insomnia'), 0.88),
((SELECT id FROM plants WHERE name = 'Chamomile'), (SELECT id FROM symptoms WHERE name = 'Digestive issues'), 0.80),
((SELECT id FROM plants WHERE name = 'Chamomile'), (SELECT id FROM symptoms WHERE name = 'Inflammation'), 0.75),
((SELECT id FROM plants WHERE name = 'Chamomile'), (SELECT id FROM symptoms WHERE name = 'Stress'), 0.82),
((SELECT id FROM plants WHERE name = 'Chamomile'), (SELECT id FROM symptoms WHERE name = 'Skin irritation'), 0.70);

-- Peppermint symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'Peppermint'), (SELECT id FROM symptoms WHERE name = 'Digestive issues'), 0.90),
((SELECT id FROM plants WHERE name = 'Peppermint'), (SELECT id FROM symptoms WHERE name = 'Nausea'), 0.85),
((SELECT id FROM plants WHERE name = 'Peppermint'), (SELECT id FROM symptoms WHERE name = 'Headache'), 0.80),
((SELECT id FROM plants WHERE name = 'Peppermint'), (SELECT id FROM symptoms WHERE name = 'Indigestion'), 0.88),
((SELECT id FROM plants WHERE name = 'Peppermint'), (SELECT id FROM symptoms WHERE name = 'Pain'), 0.70),
((SELECT id FROM plants WHERE name = 'Peppermint'), (SELECT id FROM symptoms WHERE name = 'Respiratory congestion'), 0.75);

-- St. John's Wort symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'St. Johns Wort'), (SELECT id FROM symptoms WHERE name = 'Depression'), 0.85),
((SELECT id FROM plants WHERE name = 'St. Johns Wort'), (SELECT id FROM symptoms WHERE name = 'Anxiety'), 0.80),
((SELECT id FROM plants WHERE name = 'St. Johns Wort'), (SELECT id FROM symptoms WHERE name = 'Wounds'), 0.70),
((SELECT id FROM plants WHERE name = 'St. Johns Wort'), (SELECT id FROM symptoms WHERE name = 'Inflammation'), 0.70);

-- Garlic symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'Garlic'), (SELECT id FROM symptoms WHERE name = 'High blood pressure'), 0.85),
((SELECT id FROM plants WHERE name = 'Garlic'), (SELECT id FROM symptoms WHERE name = 'High cholesterol'), 0.80),
((SELECT id FROM plants WHERE name = 'Garlic'), (SELECT id FROM symptoms WHERE name = 'Cold'), 0.75),
((SELECT id FROM plants WHERE name = 'Garlic'), (SELECT id FROM symptoms WHERE name = 'Flu'), 0.75),
((SELECT id FROM plants WHERE name = 'Garlic'), (SELECT id FROM symptoms WHERE name = 'Immune weakness'), 0.85);

-- Holy Basil symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'Holy Basil'), (SELECT id FROM symptoms WHERE name = 'Stress'), 0.90),
((SELECT id FROM plants WHERE name = 'Holy Basil'), (SELECT id FROM symptoms WHERE name = 'Anxiety'), 0.85),
((SELECT id FROM plants WHERE name = 'Holy Basil'), (SELECT id FROM symptoms WHERE name = 'Immune weakness'), 0.88),
((SELECT id FROM plants WHERE name = 'Holy Basil'), (SELECT id FROM symptoms WHERE name = 'Cough'), 0.75),
((SELECT id FROM plants WHERE name = 'Holy Basil'), (SELECT id FROM symptoms WHERE name = 'Fever'), 0.80),
((SELECT id FROM plants WHERE name = 'Holy Basil'), (SELECT id FROM symptoms WHERE name = 'Inflammation'), 0.80),
((SELECT id FROM plants WHERE name = 'Holy Basil'), (SELECT id FROM symptoms WHERE name = 'Fatigue'), 0.75);

-- Neem symptoms
INSERT INTO plant_symptoms (plant_id, symptom_id, effectiveness_score) VALUES
((SELECT id FROM plants WHERE name = 'Neem'), (SELECT id FROM symptoms WHERE name = 'Skin irritation'), 0.90),
((SELECT id FROM plants WHERE name = 'Neem'), (SELECT id FROM symptoms WHERE name = 'Wounds'), 0.85),
((SELECT id FROM plants WHERE name = 'Neem'), (SELECT id FROM symptoms WHERE name = 'Fever'), 0.75),
((SELECT id FROM plants WHERE name = 'Neem'), (SELECT id FROM symptoms WHERE name = 'Immune weakness'), 0.80);
((SELECT id FROM plants WHERE name = 'Neem'), (SELECT id FROM symptoms WHERE name = 'Inflammation'), 0.78);