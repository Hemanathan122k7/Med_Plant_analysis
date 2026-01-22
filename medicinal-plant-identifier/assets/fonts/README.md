# Fonts Directory

This directory contains custom fonts for the MediPlant application.

## Default Fonts

The application uses system fonts by default:
- Primary: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif

## Adding Custom Fonts

If you want to add custom fonts:

1. Place font files (.woff, .woff2, .ttf) in this directory
2. Update `styles/main.css` with @font-face declarations:

```css
@font-face {
    font-family: 'CustomFont';
    src: url('../assets/fonts/custom-font.woff2') format('woff2'),
         url('../assets/fonts/custom-font.woff') format('woff');
    font-weight: normal;
    font-style: normal;
    font-display: swap;
}
```

3. Update CSS variables in `styles/themes/light.css` and `styles/themes/dark.css`

## Recommended Fonts

For medicinal/health applications, consider:
- **Body text**: Inter, Open Sans, Roboto
- **Headings**: Poppins, Montserrat, Source Sans Pro
- **Accent**: Playfair Display, Lora (for scientific names)

## Font Performance

- Use WOFF2 format for best compression
- Implement font-display: swap to prevent FOIT (Flash of Invisible Text)
- Subset fonts to include only necessary characters
- Use system fonts when possible for faster loading
