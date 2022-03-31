package chess.domain.piece;

import chess.domain.Team;
import chess.domain.position.Position;

import java.util.Arrays;
import java.util.List;

public class Bishop extends Piece {
    private static final String SYMBOL = "B";
    private static final double SCORE = 3;

    public Bishop(Team team, Position position) {
        super(team, SYMBOL, position, SCORE);
    }

    private Direction findDirection(Position destination) {
        int colDifference = destination.getColDifference(position.getCol());
        int rowDifference = destination.getRowDifference(position.getRow());
        return Direction.find(rowDifference, colDifference, getDirections());
    }

    @Override
    public List<Position> findPath(Position destination) {
        Direction direction = findDirection(destination);
        return getPath(destination, direction,
                position.getCol().plusColumn(direction.getXDegree()),
                position.getRow().plusRow(direction.getYDegree()));
    }

    private List<Direction> getDirections() {
        return Arrays.asList(Direction.NORTHEAST, Direction.SOUTHEAST, Direction.SOUTHWEST, Direction.NORTHWEST);
    }
}
