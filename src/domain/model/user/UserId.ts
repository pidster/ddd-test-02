import { v4 as uuidv4 } from 'uuid';

/**
 * Value object representing a unique user identifier
 */
export class UserId {
  private readonly value: string;

  private constructor(value: string) {
    this.value = value;
  }

  public static create(): UserId {
    return new UserId(uuidv4());
  }

  public static fromString(id: string): UserId {
    return new UserId(id);
  }

  public getValue(): string {
    return this.value;
  }

  public equals(other: UserId): boolean {
    return this.value === other.value;
  }
}
