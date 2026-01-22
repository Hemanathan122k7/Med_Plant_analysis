// Animation Utilities
// Functions for managing animations and transitions

const AnimationUtils = (() => {
    // Fade in element
    function fadeIn(element, duration = 300) {
        element.style.opacity = '0';
        element.style.display = 'block';
        
        let start = null;
        function animate(timestamp) {
            if (!start) start = timestamp;
            const progress = timestamp - start;
            const opacity = Math.min(progress / duration, 1);
            
            element.style.opacity = opacity.toString();
            
            if (progress < duration) {
                requestAnimationFrame(animate);
            }
        }
        
        requestAnimationFrame(animate);
    }

    // Fade out element
    function fadeOut(element, duration = 300) {
        let start = null;
        const initialOpacity = parseFloat(window.getComputedStyle(element).opacity);
        
        function animate(timestamp) {
            if (!start) start = timestamp;
            const progress = timestamp - start;
            const opacity = Math.max(initialOpacity - (progress / duration), 0);
            
            element.style.opacity = opacity.toString();
            
            if (progress < duration) {
                requestAnimationFrame(animate);
            } else {
                element.style.display = 'none';
            }
        }
        
        requestAnimationFrame(animate);
    }

    // Slide down element
    function slideDown(element, duration = 300) {
        element.style.display = 'block';
        const height = element.scrollHeight;
        element.style.height = '0';
        element.style.overflow = 'hidden';
        
        let start = null;
        function animate(timestamp) {
            if (!start) start = timestamp;
            const progress = timestamp - start;
            const currentHeight = Math.min((progress / duration) * height, height);
            
            element.style.height = `${currentHeight}px`;
            
            if (progress < duration) {
                requestAnimationFrame(animate);
            } else {
                element.style.height = 'auto';
                element.style.overflow = 'visible';
            }
        }
        
        requestAnimationFrame(animate);
    }

    // Slide up element
    function slideUp(element, duration = 300) {
        const height = element.scrollHeight;
        element.style.height = `${height}px`;
        element.style.overflow = 'hidden';
        
        let start = null;
        function animate(timestamp) {
            if (!start) start = timestamp;
            const progress = timestamp - start;
            const currentHeight = Math.max(height - ((progress / duration) * height), 0);
            
            element.style.height = `${currentHeight}px`;
            
            if (progress < duration) {
                requestAnimationFrame(animate);
            } else {
                element.style.display = 'none';
                element.style.overflow = 'visible';
            }
        }
        
        requestAnimationFrame(animate);
    }

    // Shake element (for error indication)
    function shake(element, distance = 10, duration = 500) {
        const originalTransform = element.style.transform;
        let start = null;
        
        function animate(timestamp) {
            if (!start) start = timestamp;
            const progress = timestamp - start;
            const percent = progress / duration;
            
            if (percent < 1) {
                const offset = Math.sin(percent * Math.PI * 4) * distance * (1 - percent);
                element.style.transform = `translateX(${offset}px)`;
                requestAnimationFrame(animate);
            } else {
                element.style.transform = originalTransform;
            }
        }
        
        requestAnimationFrame(animate);
    }

    // Pulse element (for attention)
    function pulse(element, scale = 1.1, duration = 300) {
        const originalTransform = element.style.transform;
        let start = null;
        
        function animate(timestamp) {
            if (!start) start = timestamp;
            const progress = timestamp - start;
            const percent = progress / duration;
            
            if (percent < 0.5) {
                const currentScale = 1 + ((scale - 1) * (percent * 2));
                element.style.transform = `scale(${currentScale})`;
                requestAnimationFrame(animate);
            } else if (percent < 1) {
                const currentScale = scale - ((scale - 1) * ((percent - 0.5) * 2));
                element.style.transform = `scale(${currentScale})`;
                requestAnimationFrame(animate);
            } else {
                element.style.transform = originalTransform;
            }
        }
        
        requestAnimationFrame(animate);
    }

    // Bounce element
    function bounce(element, height = 20, duration = 600) {
        const originalTransform = element.style.transform;
        let start = null;
        
        function animate(timestamp) {
            if (!start) start = timestamp;
            const progress = timestamp - start;
            const percent = progress / duration;
            
            if (percent < 1) {
                const bounceValue = Math.abs(Math.sin(percent * Math.PI * 2)) * height * (1 - percent);
                element.style.transform = `translateY(-${bounceValue}px)`;
                requestAnimationFrame(animate);
            } else {
                element.style.transform = originalTransform;
            }
        }
        
        requestAnimationFrame(animate);
    }

    // Counter animation (for numbers)
    function animateCounter(element, start, end, duration = 1000) {
        let startTime = null;
        
        function animate(timestamp) {
            if (!startTime) startTime = timestamp;
            const progress = timestamp - startTime;
            const percent = Math.min(progress / duration, 1);
            
            const current = Math.floor(start + (end - start) * percent);
            element.textContent = current.toString();
            
            if (percent < 1) {
                requestAnimationFrame(animate);
            }
        }
        
        requestAnimationFrame(animate);
    }

    // Stagger animation for multiple elements
    function staggerAnimation(elements, animationFn, delay = 100) {
        elements.forEach((element, index) => {
            setTimeout(() => {
                animationFn(element);
            }, index * delay);
        });
    }

    // Check if animations are supported
    function supportsAnimations() {
        const element = document.createElement('div');
        return typeof element.style.animation !== 'undefined';
    }

    // Check if user prefers reduced motion
    function prefersReducedMotion() {
        return window.matchMedia('(prefers-reduced-motion: reduce)').matches;
    }

    return {
        fadeIn,
        fadeOut,
        slideDown,
        slideUp,
        shake,
        pulse,
        bounce,
        animateCounter,
        staggerAnimation,
        supportsAnimations,
        prefersReducedMotion
    };
})();
