package chess.domain.piece;

import chess.domain.Team;
import chess.domain.position.Position;

import java.util.Arrays;
import java.util.List;

public class Rook extends Piece {
    private static final String SYMBOL = "R";
    private static final float SCORE = 5.0f;

    public Rook(Team team, Position position) {
        super(team, SYMBOL, position, SCORE);
    }

    @Override
    public List<Position> findPath(Position destination) {
        Direction direction = findDirection(destination);
        return getPath(destination, direction,
                position.getCol().plusColumn(direction.getXDegree()),
                position.getRow().plusRow(direction.getYDegree()));
    }

    @Override
    protected List<Direction> getDirections() {
        return Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    }
}