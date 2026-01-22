# Vercel Deployment Instructions

## Issue: 404 Error

If you're getting a 404 error, you need to configure Vercel to use the correct root directory.

## Solution: Configure Vercel Dashboard

1. **Go to Vercel Dashboard**:
   - Visit: https://vercel.com/dashboard
   - Select your project: `med-plant-analysis`

2. **Update Project Settings**:
   - Go to: Settings → General
   - Scroll to: "Build & Development Settings"
   - Set **Root Directory**: `medicinal-plant-identifier`
   - Click "Save"

3. **Redeploy**:
   - Go to: Deployments tab
   - Click on the latest deployment
   - Click "Redeploy"

## Alternative: Deploy from Subdirectory

If the above doesn't work, you can deploy directly from the subdirectory:

```bash
cd medicinal-plant-identifier
vercel --prod
```

## OR: Use Vercel CLI with Custom Settings

```bash
# From project root
vercel --prod --cwd medicinal-plant-identifier
```

## Vercel Configuration Files

- **Root vercel.json**: Simple rewrite rule pointing to subdirectory
- **medicinal-plant-identifier/vercel.json**: Full configuration for that folder

## Quick Fix Commands

```bash
# Commit current changes
git add .
git commit -m "Update Vercel configuration"
git push origin main

# Then in Vercel Dashboard:
# 1. Settings → Root Directory: medicinal-plant-identifier
# 2. Redeploy
```

---

**Note**: The root directory setting in Vercel Dashboard overrides the vercel.json configuration for directory structure.
