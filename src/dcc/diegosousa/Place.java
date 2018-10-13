package dcc.diegosousa;

public class Place {

    private Integer row;
    private Integer col;
    private Direction direction;
    private boolean isAvailable;

    public Place(Integer row, Integer col, Direction direction) {

        this.col = col;
        this.row = row;
        this.direction = direction;
        this.isAvailable = true;

    }

    public Integer getRow() {

        return row;

    }

    public Integer getCol() {

        return col;

    }

    public Direction getDirection() {

        return direction;

    }

    public boolean isAvailable() {

        return this.isAvailable;

    }

    public void setAvailable(boolean available) {

        this.isAvailable = available;

    }
}
