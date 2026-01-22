package com.medicinal.plant.service.impl;

import com.medicinal.plant.exception.ImageProcessingException;
import com.medicinal.plant.model.dto.response.ImageRecognitionResponse;
import com.medicinal.plant.model.entity.Plant;
import com.medicinal.plant.repository.PlantRepository;
import com.medicinal.plant.service.ImageRecognitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Image Recognition Service Implementation
 * NOTE: This is a simplified implementation for demonstration.
 * For production, integrate with ML models like TensorFlow, PyTorch, or cloud APIs.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageRecognitionServiceImpl implements ImageRecognitionService {

    private final PlantRepository plantRepository;
    
    // Known medicinal plant color profiles (simplified)
    private static final Map<String, int[]> PLANT_COLOR_PROFILES = new HashMap<>();
    
    static {
        // RGB ranges for different plant types (simplified heuristic)
        PLANT_COLOR_PROFILES.put("Aloe Vera", new int[]{50, 150, 50}); // Green
        PLANT_COLOR_PROFILES.put("Lavender", new int[]{150, 100, 200}); // Purple
        PLANT_COLOR_PROFILES.put("Turmeric", new int[]{200, 150, 50}); // Yellow/Orange
        PLANT_COLOR_PROFILES.put("Ginger", new int[]{180, 140, 100}); // Brown/Tan
        PLANT_COLOR_PROFILES.put("Eucalyptus", new int[]{80, 120, 90}); // Green
        PLANT_COLOR_PROFILES.put("Chamomile", new int[]{240, 240, 200}); // White/Yellow
        PLANT_COLOR_PROFILES.put("Peppermint", new int[]{60, 140, 70}); // Green
        PLANT_COLOR_PROFILES.put("Holy Basil", new int[]{70, 130, 60}); // Green
        PLANT_COLOR_PROFILES.put("Neem", new int[]{80, 140, 80}); // Dark Green
    }

    @Override
    public ImageRecognitionResponse recognizePlant(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new ImageProcessingException("Image file is empty");
        }
        
        // Validate file type
        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ImageProcessingException("File must be an image (JPG, PNG, or WEBP)");
        }
        
        try {
            // Read the image
            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
            if (bufferedImage == null) {
                throw new ImageProcessingException("Failed to process image. Please upload a valid image file.");
            }
            
            // Check if image contains plant-like colors (basic validation)
            boolean isLikelyPlant = detectPlantLikeColors(bufferedImage);
            
            if (!isLikelyPlant) {
                log.warn("Image does not appear to contain plant-like colors");
                // Return empty results for non-plant images
                return ImageRecognitionResponse.builder()
                        .matches(Collections.emptyList())
                        .imageUrl("/uploads/temp.jpg")
                        .confidence(0.0)
                        .build();
            }
            
            // Analyze dominant colors
            int[] dominantColor = extractDominantColor(bufferedImage);
            log.info("Dominant color detected: R={}, G={}, B={}", 
                    dominantColor[0], dominantColor[1], dominantColor[2]);
            
            // Match against plant database
            List<ImageRecognitionResponse.PlantMatch> matches = matchPlantsFromDatabase(dominantColor);
            
            if (matches.isEmpty()) {
                log.info("No matching plants found for the uploaded image");
            }
            
            return ImageRecognitionResponse.builder()
                    .matches(matches)
                    .imageUrl("/uploads/temp.jpg")
                    .confidence(matches.isEmpty() ? 0.0 : matches.get(0).getMatchScore())
                    .build();
                    
        } catch (IOException e) {
            log.error("Error processing image", e);
            throw new ImageProcessingException("Failed to process image: " + e.getMessage());
        }
    }
    
    /**
     * Detect if image contains plant-like colors (greens, purples, yellows)
     */
    private boolean detectPlantLikeColors(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int sampleSize = 100; // Sample 100 pixels
        int plantColorPixels = 0;
        
        Random random = new Random();
        
        for (int i = 0; i < sampleSize; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            
            Color color = new Color(image.getRGB(x, y));
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            
            // Check for plant-like colors (green dominant or nature-like hues)
            boolean isGreenish = green > red && green > blue && green > 40;
            boolean isPurplish = blue > 100 && red > 80;
            boolean isYellowish = red > 150 && green > 100 && blue < 150;
            
            if (isGreenish || isPurplish || isYellowish) {
                plantColorPixels++;
            }
        }
        
        // If at least 20% of sampled pixels are plant-like colors
        return (plantColorPixels * 100.0 / sampleSize) >= 20.0;
    }
    
    /**
     * Extract dominant color from image
     */
    private int[] extractDominantColor(BufferedImage image) {
        long redSum = 0, greenSum = 0, blueSum = 0;
        int pixelCount = 0;
        
        int width = image.getWidth();
        int height = image.getHeight();
        
        // Sample every 10th pixel for performance
        for (int y = 0; y < height; y += 10) {
            for (int x = 0; x < width; x += 10) {
                Color color = new Color(image.getRGB(x, y));
                redSum += color.getRed();
                greenSum += color.getGreen();
                blueSum += color.getBlue();
                pixelCount++;
            }
        }
        
        return new int[]{
            (int)(redSum / pixelCount),
            (int)(greenSum / pixelCount),
            (int)(blueSum / pixelCount)
        };
    }
    
    /**
     * Match plants from database based on color similarity
     */
    private List<ImageRecognitionResponse.PlantMatch> matchPlantsFromDatabase(int[] dominantColor) {
        List<Plant> allPlants = plantRepository.findAll();
        List<ImageRecognitionResponse.PlantMatch> matches = new ArrayList<>();
        
        for (Plant plant : allPlants) {
            // Get color profile for this plant
            int[] plantColor = PLANT_COLOR_PROFILES.getOrDefault(plant.getName(), null);
            
            if (plantColor != null) {
                // Calculate color similarity (inverse of Euclidean distance)
                double distance = Math.sqrt(
                    Math.pow(dominantColor[0] - plantColor[0], 2) +
                    Math.pow(dominantColor[1] - plantColor[1], 2) +
                    Math.pow(dominantColor[2] - plantColor[2], 2)
                );
                
                // Normalize to 0-1 score (max distance is ~441 for RGB)
                double matchScore = Math.max(0.0, 1.0 - (distance / 441.0));
                
                // Only include matches with score > 0.5
                if (matchScore > 0.5) {
                    matches.add(ImageRecognitionResponse.PlantMatch.builder()
                            .plantId(plant.getId())
                            .plantName(plant.getName())
                            .scientificName(plant.getScientificName())
                            .matchScore(matchScore)
                            .build());
                }
            }
        }
        
        // Sort by match score descending
        matches.sort((a, b) -> Double.compare(b.getMatchScore(), a.getMatchScore()));
        
        // Return top 3 matches
        return matches.subList(0, Math.min(3, matches.size()));
    }

    @Override
    public String uploadPlantImage(MultipartFile image, Long plantId) {
        if (image.isEmpty()) {
            throw new ImageProcessingException("Image file is empty");
        }
        
        // Return dummy image URL
        return "/uploads/plant_" + plantId + "_" + image.getOriginalFilename();
    }
}
