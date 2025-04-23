import { Router } from 'express';
import { UserController } from './UserController';
import { UserService } from '../../application/user/UserService';
import { InMemoryUserRepository } from '../../infrastructure/persistence/InMemoryUserRepository';

export function setupRoutes(): Router {
  const router = Router();
  
  // Set up dependencies
  const userRepository = new InMemoryUserRepository();
  const userService = new UserService(userRepository);
  const userController = new UserController(userService);
  
  // Define routes
  router.post('/users', (req, res) => userController.createUser(req, res));
  router.get('/users/:id', (req, res) => userController.getUser(req, res));
  
  return router;
}
