package dcc.diegosousa;

public class Place {

    private Integer row;
    private Integer col;
    private Direction direction;

    public Place(Integer row, Integer col, Direction direction) {

        this.col = col;
        this.row = row;
        this.direction = direction;

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

}
