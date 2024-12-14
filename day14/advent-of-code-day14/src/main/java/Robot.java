public class Robot {
    private Cord position;
    private Cord velocity;

    public Robot(Cord position, Cord velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void move(int maxX, int maxY) {
        position.setPosX(position.getPosX() + velocity.getPosX());
        position.setPosY(position.getPosY() + velocity.getPosY());

        if (position.getPosX() < 0) {
            position.setPosX(maxX + position.getPosX());
        }

        if (position.getPosY() < 0) {
            position.setPosY(maxY + position.getPosY());
        }

        if (position.getPosX() >= maxX) {
            position.setPosX(position.getPosX() - maxX);
        }

        if (position.getPosY() >= maxY) {
            position.setPosY(position.getPosY() - maxY);
        }

    }

    public Cord getPosition() {
        return position;
    }

    public void setPosition(Cord position) {
        this.position = position;
    }

    public Cord getVelocity() {
        return velocity;
    }

    public void setVelocity(Cord velocity) {
        this.velocity = velocity;
    }
}
