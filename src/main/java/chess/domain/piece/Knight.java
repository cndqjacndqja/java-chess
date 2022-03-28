package chess.domain.piece;

import chess.domain.Direction;
import chess.domain.Position;
import chess.domain.Team;

import java.util.List;

public class Knight extends Piece {

    public Knight(Team team, Position position) {
        super(team, "N", position, 2.5);
    }

    private void validateIsPossible(Position destination) {
        Direction.knightDirection().stream()
                .filter(direction -> isCorrectDirection(destination, direction))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 말이 움직일 수 없습니다."));
    }

    private boolean isCorrectDirection(Position destination, Direction direction) {
        return (position.getRow().getDifference(destination.getRow()) == direction.getYDegree()
                && position.getCol().getDifference(destination.getCol()) == direction.getXDegree());
    }

    @Override
    public List<Position> findPath(Position destination) {
        validateIsPossible(destination);
        return List.of();
    }

    @Override
    public boolean isBlank() {
        return false;
    }
}