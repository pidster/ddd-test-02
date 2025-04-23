import { UserId } from './UserId';
import { Email } from './Email';

/**
 * User entity representing a user in the system
 */
export class User {
  private readonly id: UserId;
  private email: Email;
  private name: string;
  private createdAt: Date;
  private updatedAt: Date;

  constructor(id: UserId, email: Email, name: string) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.createdAt = new Date();
    this.updatedAt = new Date();
  }

  public getId(): UserId {
    return this.id;
  }

  public getEmail(): Email {
    return this.email;
  }

  public getName(): string {
    return this.name;
  }

  public updateEmail(email: Email): void {
    this.email = email;
    this.updatedAt = new Date();
  }

  public updateName(name: string): void {
    this.name = name;
    this.updatedAt = new Date();
  }

  public getCreatedAt(): Date {
    return this.createdAt;
  }

  public getUpdatedAt(): Date {
    return this.updatedAt;
  }
}
