package chess.domain;

import chess.domain.piece.*;

import java.util.EnumMap;
import java.util.Map;

public class Rank {
    private final Map<Column, Piece> pieces;

    private Rank(EnumMap<Column, Piece> pieces) {
        this.pieces = pieces;
    }

    public Map<Column, Piece> getPieces() {
        return pieces;
    }

    public static Rank createBlank(int row) {
        return new Rank(Blank.from(row, Team.NONE));
    }

    public static Rank createPawn(Team team, int row) {
        return new Rank(Pawn.from(row, team));
    }

    public static Rank createPiecesExceptPawn(Team team, int row) {
        EnumMap<Column, Piece> pieces = new EnumMap<>(Column.class);
        pieces.put(Column.A, new Rook(team, new Position(Column.A, Row.find(row))));
        pieces.put(Column.B, new Knight(team, new Position(Column.B, Row.find(row))));
        pieces.put(Column.C, new Bishop(team, new Position(Column.C, Row.find(row))));
        pieces.put(Column.D, new Queen(team, new Position(Column.D, Row.find(row))));
        pieces.put(Column.E, new King(team, new Position(Column.E, Row.find(row))));
        pieces.put(Column.F, new Bishop(team, new Position(Column.F, Row.find(row))));
        pieces.put(Column.G, new Knight(team, new Position(Column.G, Row.find(row))));
        pieces.put(Column.H, new Rook(team, new Position(Column.H, Row.find(row))));
        return new Rank(pieces);
    }

    public Piece getPiece(Column column) {
        return pieces.get(column);
    }

    public void changePiece(Column column, Piece piece) {
        pieces.put(column, piece);
    }

    public double calculateWhiteTotalScore(Team team) {
        double totalScore = 0;
        for (Column column : pieces.keySet()) {
            if (pieces.get(column).getTeam() == team) {
                totalScore += pieces.get(column).getScore();
            }
        }
        return totalScore;
    }

    public boolean isPawn(Team team, Column column) {
        if (pieces.get(column).getTeam() == team) {
            return pieces.get(column).isPawn();
        }
        return false;
    }
}
