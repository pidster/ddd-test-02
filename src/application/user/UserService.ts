import { User } from '../../domain/model/user/User';
import { UserId } from '../../domain/model/user/UserId';
import { Email } from '../../domain/model/user/Email';
import { UserRepository } from '../../domain/model/user/UserRepository';
import { CreateUserDto } from './dto/CreateUserDto';
import { UserDto } from './dto/UserDto';

export class UserService {
  private userRepository: UserRepository;

  constructor(userRepository: UserRepository) {
    this.userRepository = userRepository;
  }

  public async createUser(createUserDto: CreateUserDto): Promise<UserDto> {
    const email = Email.create(createUserDto.email);
    
    // Check if user with email already exists
    const existingUser = await this.userRepository.findByEmail(email);
    if (existingUser) {
      throw new Error('User with this email already exists');
    }

    // Create new user
    const userId = UserId.create();
    const user = new User(userId, email, createUserDto.name);
    
    // Save user
    await this.userRepository.save(user);
    
    // Return DTO
    return {
      id: user.getId().getValue(),
      email: user.getEmail().getValue(),
      name: user.getName(),
      createdAt: user.getCreatedAt(),
      updatedAt: user.getUpdatedAt()
    };
  }

  public async getUserById(id: string): Promise<UserDto | null> {
    const userId = UserId.fromString(id);
    const user = await this.userRepository.findById(userId);
    
    if (!user) {
      return null;
    }
    
    return {
      id: user.getId().getValue(),
      email: user.getEmail().getValue(),
      name: user.getName(),
      createdAt: user.getCreatedAt(),
      updatedAt: user.getUpdatedAt()
    };
  }
}
