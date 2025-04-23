import express from 'express';
import { setupRoutes } from './interface/rest/routes';

// Create Express app
const app = express();
const port = process.env.PORT || 3000;

// Middleware
app.use(express.json());

// Routes
app.use('/api', setupRoutes());

// Start server
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
