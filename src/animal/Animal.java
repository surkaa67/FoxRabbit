package animal;

import java.awt.*;

public abstract class Animal {

    /**
     * 记录年龄
     */
    private int age;
    private final int ageLimit;

    /**
     * 动物的体力，用于记录发生行为后的变化，范围（0 - 1）
     * 每次移动都会减少一点活力，
     * 生育则会清零
     * 而每次捕食都会根据吃到的增加一点活力
     */
    private double energy = 0;

    /**
     * 用于绘画用三基色
     */
    protected final int red;
    protected final int green;
    protected final int blue;

    /**
     * 生育最低的能量值，低于该值则不会生育
     */
    protected final double leastEnergyForBreed;

    /**
     * 移动最低的能量值，低于该值则不会移动
     */
    protected final double leastEnergyForMove;

    /**
     * 移动后扣取的能量
     */
    protected final double energyForMoveAction;

    public Animal(int age, int ageLimit, Color color, double leastEnergyForBreed, double leastEnergyForMove, double leastEnergyForMoveAction) {
        this.age = age;
        this.ageLimit = ageLimit;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.leastEnergyForBreed = leastEnergyForBreed;
        this.leastEnergyForMove = leastEnergyForMove;
        this.energyForMoveAction = leastEnergyForMoveAction;
    }

    public double getEnergy() {
        return energy;
    }

    public void clearEnergy() {
        energy = 0;
    }

    public void addEnergy(double energy) {
        this.energy += energy;
        if (this.energy > 1) {
            this.energy = 1;
        }
        if (this.energy < 0) {
            this.energy = 0;
        }
    }

    /**
     * 动物的活力，
     * 用于行为发生前的判断（实例：Math.random < vitality），范围（0 - 1）
     * 函数：vitality = 4 * age * (ageLimit - age) / (ageLimit * ageLimit)
     * 倒二次函数形状，模拟壮年时最具有活力
     */
    protected double getVitality() {
        return 4.0 * age * (ageLimit - age) / (ageLimit * ageLimit);
    }

    protected int getBrightness() {
        return (int) ((1 - (double) age / ageLimit) * 255);
    }

    public boolean grow() {
        age++;
        if (age >= ageLimit) {
            age = 0;
            return true;
        }
        return false;
    }

    public int getAge() {
        return age;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public boolean isEnoughSpaceForBreed(double numOfSpace) {
        // 为假就生育，有空间并且越空旷越生育
        return numOfSpace == 0 || Math.random() > (numOfSpace / 8);
    }

    public boolean isEnoughSpaceForMove(double numOfSpace) {
        // 为假就移动，有空间并且空间越挤越动
        return numOfSpace == 0 || Math.random() < (numOfSpace / 8);
    }
}
