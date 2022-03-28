package chess.domain.piece;

import chess.domain.*;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Team team, Position position) {
        super(team, "B", position, 3);
    }

    private Direction findDirection(Position destination) {
        return Direction.diagonalDirection().stream()
                .filter(direction -> isCorrectDirection(destination, direction))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 말이 움직일 수 없습니다."));
    }

    private boolean isCorrectDirection(Position destination, Direction direction) {
        return (destination.getCol().getDifference(position.getCol()) * direction.getXDegree()
                == (destination.getRow().getDifference(position.getRow()) * direction.getYDegree())
                && (destination.getRow().getDifference(position.getRow()) * direction.getYDegree()) > 0);
    }

    @Override
    public List<Position> findPath(Position destination) {
        Direction direction = findDirection(destination);
        return getPath(destination, direction,
                position.getCol().plusColumn(direction.getXDegree()),
                position.getRow().plusRow(direction.getYDegree()));
    }

    private List<Position> getPath(Position destination, Direction direction, Column col, Row row) {
        List<Position> positions = new ArrayList<>();
        while(!(col == destination.getCol() && row == destination.getRow())) {
            positions.add(new Position(col, row));
            col = col.plusColumn(direction.getXDegree());
            row = row.plusRow(direction.getYDegree());
        }
        return positions;
    }
}
