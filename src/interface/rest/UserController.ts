import { Request, Response } from 'express';
import { UserService } from '../../application/user/UserService';
import { CreateUserDto } from '../../application/user/dto/CreateUserDto';

export class UserController {
  private userService: UserService;

  constructor(userService: UserService) {
    this.userService = userService;
  }

  async createUser(req: Request, res: Response): Promise<void> {
    try {
      const createUserDto: CreateUserDto = {
        email: req.body.email,
        name: req.body.name
      };

      const userDto = await this.userService.createUser(createUserDto);
      res.status(201).json(userDto);
    } catch (error) {
      res.status(400).json({ message: error.message });
    }
  }

  async getUser(req: Request, res: Response): Promise<void> {
    try {
      const userId = req.params.id;
      const userDto = await this.userService.getUserById(userId);
      
      if (!userDto) {
        res.status(404).json({ message: 'User not found' });
        return;
      }
      
      res.status(200).json(userDto);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  }
}
