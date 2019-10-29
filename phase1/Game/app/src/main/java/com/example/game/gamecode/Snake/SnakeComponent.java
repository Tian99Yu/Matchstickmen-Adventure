package com.example.game.gamecode.Snake;

/**
 * A generic part of the snake character.
 */
class SnakeComponent extends SnakeObject {
    /**
     * The direction that this component is traveling.
     */
    Direction direction = Direction.UP;

    /**
     * The snake component that is following this snake component, null if this is the last component.
     */
    private SnakeComponent next;

    /**
     * Constructs a new snake component.
     *
     * @param x          the initial x coordinate of this snake object
     * @param y          the initial y coordinate of this snake object
     * @param appearance the appearance of this snake object
     * @param size       the side length of this snake object
     */
    SnakeComponent(int x, int y, String appearance, int size) {
        super(x, y, appearance, size);
    }

    /**
     * Set the direction of this snake component to direction.
     * @param direction The direction this snake component is going.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Move this snake component and all the snake component following this snake component
     */
    public void move(){
        // Move this snake component.
        switch (direction){
            case UP:
                y += 1;
                break;
            case DOWN:
                y -= 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
        }
        // Move the next snake component
        if (this.next != null) {
            this.next.move();
            this.next.direction = this.direction;
        }
    }

    public void addComponent(){
        SnakeComponent snakeComponent;
        switch (direction){
            case UP:
                snakeComponent = new SnakeComponent(x, y-1, "@", 1);
                break;
            case DOWN:
                snakeComponent = new SnakeComponent(x, y+1, "@", 1);
                break;
            case LEFT:
                snakeComponent = new SnakeComponent(x+1, y, "@", 1);
                break;
            case RIGHT:
                snakeComponent = new SnakeComponent(x-1, y, "@", 1);
                break;
        }
    }
}
