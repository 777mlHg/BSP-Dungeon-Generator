package src.dungeonmap.entities;

public abstract class Character extends Entity
{
  protected String name;
  protected int maxHealth;
  protected int health;
  protected int attackDamage;
  protected boolean alive;

  public Character(String name, int maxHealth, int attackDamage, int posX, int posY)
  {
    super(posX, posY);
    this.name = name;
    this.maxHealth = maxHealth;
    this.health = maxHealth;
    this.attackDamage = attackDamage;
    this.alive = true;
  }

  public void takeDamage(int damage)
  {
    if (health > 0)
    {
      health -= damage;
      if (health <= 0)
      {
        alive = false;
      }
    }
  }

  public void heal(int amount)
  {
    int totalAfterHeal = health + amount;
    health = Math.min(totalAfterHeal, maxHealth);
  }

  public void attack(Character enemyCharacter)
  {
    enemyCharacter.takeDamage(attackDamage);
  }

  public void move(int mx, int my)
  {
    this.posX += mx;
    this.posY += my;
  }

  public String getName()
  {
    return name;
  }

  public int getHealth()
  {
    return health;
  }

  public int getAttackDamage()
  {
    return attackDamage;
  }

  public boolean isAlive()
  {
    return alive;
  }

  public int getMaxHealth()
  {
    return maxHealth;
  }
}
