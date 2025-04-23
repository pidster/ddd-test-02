import { User } from './User';
import { UserId } from './UserId';
import { Email } from './Email';

/**
 * Repository interface for User entity
 */
export interface UserRepository {
  findById(id: UserId): Promise<User | null>;
  findByEmail(email: Email): Promise<User | null>;
  save(user: User): Promise<void>;
  remove(user: User): Promise<void>;
  findAll(): Promise<User[]>;
}
