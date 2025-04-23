import { User } from '../../domain/model/user/User';
import { UserId } from '../../domain/model/user/UserId';
import { Email } from '../../domain/model/user/Email';
import { UserRepository } from '../../domain/model/user/UserRepository';

export class InMemoryUserRepository implements UserRepository {
  private users: Map<string, User> = new Map();

  async findById(id: UserId): Promise<User | null> {
    const user = this.users.get(id.getValue());
    return user || null;
  }

  async findByEmail(email: Email): Promise<User | null> {
    for (const user of this.users.values()) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    return null;
  }

  async save(user: User): Promise<void> {
    this.users.set(user.getId().getValue(), user);
  }

  async remove(user: User): Promise<void> {
    this.users.delete(user.getId().getValue());
  }

  async findAll(): Promise<User[]> {
    return Array.from(this.users.values());
  }
}
