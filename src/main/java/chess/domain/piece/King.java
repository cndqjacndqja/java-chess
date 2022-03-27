package chess.domain.piece;

import chess.domain.Position;
import chess.domain.Team;

import java.util.List;

public class King extends Piece {

    public King(Team team, Position position) {
        super(team, King.class.getSimpleName(), position, 0);
    }

    public void validateIsPossible(Position destination) {
        if (Math.abs(position.getCol().getDifference(destination.getCol())) <= 1
                && Math.abs(position.getRow().getDifference(destination.getRow())) <= 1) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 말이 움직일 수 없습니다.");
    }

    @Override
    public List<Position> findPath(Position destination) {
        validateIsPossible(destination);
        return List.of();
    }

    @Override
    public boolean isKing() {
        return true;
    }
}