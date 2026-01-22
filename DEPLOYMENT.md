# 🚀 Deployment Guide

## Vercel Deployment (Frontend)

### Quick Deploy

[![Deploy with Vercel](https://vercel.com/button)](https://vercel.com/new/clone?repository-url=https://github.com/yourusername/Med_Plant_Analysis)

### Manual Deployment Steps

#### 1. Install Vercel CLI

```bash
npm i -g vercel
```

#### 2. Login to Vercel

```bash
vercel login
```

#### 3. Deploy Frontend

```bash
# From project root
cd medicinal-plant-identifier
vercel

# Or deploy from root with config
cd ..
vercel --prod
```

#### 4. Configure Environment Variables

In Vercel Dashboard, add these environment variables if needed:

```
API_BASE_URL=https://your-backend-api.com/api
```

### Configuration Files

- **Root vercel.json**: Deploys entire project with frontend focus
- **Frontend vercel.json**: Optimized for standalone frontend deployment
- **.vercelignore**: Excludes backend and unnecessary files

---

## Backend Deployment Options

Since Vercel doesn't support Java/Spring Boot, deploy the backend separately:

### Option 1: Railway

```bash
# Install Railway CLI
npm i -g @railway/cli

# Login and deploy
railway login
cd medicinal-plant-backend
railway init
railway up
```

**Configure Environment:**
```
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:mysql://host:port/db
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=pass
JWT_SECRET=your-secret-key
```

### Option 2: Heroku

```bash
# Install Heroku CLI
# https://devcenter.heroku.com/articles/heroku-cli

# Login and create app
heroku login
cd medicinal-plant-backend
heroku create mediplant-api

# Add MySQL addon
heroku addons:create jawsdb:kitefin

# Deploy
git push heroku main
```

**Heroku Procfile:**
```
web: java -Dserver.port=$PORT -jar target/medicinal-plant-backend-1.0.0.jar
```

### Option 3: AWS Elastic Beanstalk

```bash
# Install EB CLI
pip install awsebcli

# Initialize and deploy
cd medicinal-plant-backend
eb init -p java-17 mediplant-api
eb create mediplant-api-env
eb deploy
```

### Option 4: Google Cloud Platform

```bash
# Using App Engine
cd medicinal-plant-backend

# Create app.yaml
cat > app.yaml << EOF
runtime: java17
instance_class: F2
EOF

# Deploy
gcloud app deploy
```

### Option 5: Docker (Any Cloud Provider)

```bash
cd medicinal-plant-backend

# Build and push to registry
docker build -t mediplant-backend .
docker tag mediplant-backend:latest registry.com/mediplant-backend:latest
docker push registry.com/mediplant-backend:latest

# Deploy to your cloud provider
```

---

## Full Stack Deployment Architecture

```
┌─────────────────────────────────────────────────────┐
│                    Vercel (Frontend)                 │
│              https://mediplant.vercel.app            │
│                                                      │
│  • Static HTML/CSS/JS                                │
│  • CDN Edge Network                                  │
│  • Auto SSL/HTTPS                                    │
└──────────────────┬──────────────────────────────────┘
                   │
                   │ REST API Calls
                   │
┌──────────────────▼──────────────────────────────────┐
│           Backend API (Choose Provider)              │
│         https://api.mediplant.com                    │
│                                                      │
│  • Spring Boot Application                           │
│  • JWT Authentication                                │
│  • Image Processing                                  │
└──────────────────┬──────────────────────────────────┘
                   │
                   │ Database Connection
                   │
┌──────────────────▼──────────────────────────────────┐
│              MySQL Database                          │
│        (AWS RDS / Railway / PlanetScale)             │
│                                                      │
│  • Plant Data                                        │
│  • User Information                                  │
│  • Reviews & Ratings                                 │
└─────────────────────────────────────────────────────┘
```

---

## Post-Deployment Configuration

### 1. Update API URLs in Frontend

Edit `medicinal-plant-identifier/scripts/modules/api-client.js`:

```javascript
const API_BASE_URL = 'https://your-backend-api.com/api';
```

### 2. Configure CORS in Backend

Edit `src/main/java/com/medicinal/plant/config/SecurityConfig.java`:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(
        "https://mediplant.vercel.app",
        "http://localhost:3000"
    ));
    // ... rest of configuration
}
```

### 3. Environment Variables

**Frontend (Vercel):**
- None required (API URL hardcoded or use build-time env vars)

**Backend:**
- `SPRING_PROFILES_ACTIVE=prod`
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION`

### 4. Database Setup

**Create Tables:**
```bash
# Connect to your production database
mysql -h hostname -u username -p database_name < src/main/resources/data.sql
```

### 5. SSL/HTTPS

- **Vercel**: Automatic SSL
- **Backend**: Use Let's Encrypt or cloud provider SSL

---

## Testing Deployment

### Frontend

```bash
# Check if site loads
curl https://mediplant.vercel.app

# Test static assets
curl https://mediplant.vercel.app/scripts/main.js
```

### Backend

```bash
# Health check
curl https://your-backend-api.com/actuator/health

# Test API
curl https://your-backend-api.com/api/plants/all

# Test with authentication
curl -X POST https://your-backend-api.com/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test123"}'
```

---

## Continuous Deployment

### Vercel (Frontend)

Automatic deployment on git push:

1. Connect GitHub repository in Vercel Dashboard
2. Configure branch: `main`
3. Every push triggers automatic deployment

### Backend CI/CD

**GitHub Actions Example:**

```yaml
# .github/workflows/deploy-backend.yml
name: Deploy Backend

on:
  push:
    branches: [main]
    paths:
      - 'medicinal-plant-backend/**'

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 21
        uses: actions/setup-java@v2
        with:
          java-version: '21'
      - name: Build with Maven
        run: |
          cd medicinal-plant-backend
          mvn clean package
      - name: Deploy to Railway
        run: railway up
```

---

## Monitoring & Logs

### Vercel

```bash
# View deployment logs
vercel logs

# View function logs
vercel logs --follow
```

### Backend

```bash
# Railway
railway logs

# Heroku
heroku logs --tail --app mediplant-api

# AWS
aws logs tail /aws/elasticbeanstalk/mediplant-api-env
```

---

## Troubleshooting

### Common Issues

**1. CORS Errors**
- Update backend CORS configuration
- Add Vercel URL to allowed origins

**2. API Connection Failed**
- Check backend URL in frontend code
- Verify backend is running
- Check firewall/security groups

**3. 404 on Refresh**
- Ensure SPA rewrites are configured in vercel.json
- Check routes configuration

**4. Slow Loading**
- Enable caching headers
- Use CDN for static assets
- Optimize images

**5. Database Connection Failed**
- Verify connection string
- Check database credentials
- Ensure database allows remote connections

---

## Rollback

### Vercel

```bash
# List deployments
vercel ls

# Rollback to previous deployment
vercel rollback [deployment-url]
```

### Backend

```bash
# Railway
railway rollback

# Heroku
heroku rollback --app mediplant-api

# Docker
docker pull registry.com/mediplant-backend:previous-tag
docker service update --image registry.com/mediplant-backend:previous-tag
```

---

## Performance Optimization

### Frontend (Vercel)

- ✅ Automatic CDN distribution
- ✅ Brotli/Gzip compression
- ✅ HTTP/2 support
- ✅ Edge caching

### Backend

- Configure connection pooling
- Enable response caching
- Use database indexes
- Implement rate limiting

---

## Security Checklist

- [ ] HTTPS enabled (both frontend and backend)
- [ ] Environment variables secured
- [ ] Database credentials encrypted
- [ ] JWT secret strong and unique
- [ ] CORS properly configured
- [ ] API rate limiting enabled
- [ ] Input validation implemented
- [ ] SQL injection prevention
- [ ] XSS protection headers set
- [ ] Regular security updates

---

## Cost Estimation

### Vercel (Frontend)
- **Free Tier**: Up to 100GB bandwidth/month
- **Pro**: $20/month - Unlimited bandwidth

### Railway (Backend)
- **Free Tier**: $5 credit/month
- **Developer**: $10/month
- **Team**: $20/month

### Database
- **Railway MySQL**: Included in plan
- **AWS RDS**: ~$15-50/month
- **PlanetScale**: Free tier available

**Total Estimated Cost:**
- Small scale: $0-10/month (using free tiers)
- Medium scale: $30-60/month
- Large scale: $100+/month

---

## Support

For deployment issues:
- 📧 Email: support@mediplant.com
- 💬 Discord: [Join our community]
- 📖 Docs: [Full documentation]
- 🐛 Issues: [GitHub Issues]

---

**Last Updated:** January 22, 2026
