package chess.domain.state;

import chess.domain.piece.Pieces;

public class StateFactory {
    private StateFactory() {
    }

    public static State initialization(final Pieces pieces) {
        if (pieces.isBlack()) {
            return new Finished(pieces);
        }
        return new Running(pieces);
    }
}