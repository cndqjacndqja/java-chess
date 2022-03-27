package chess.domain;

import chess.domain.piece.Blank;
import chess.domain.piece.Piece;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Row, Rank> board = new EnumMap<>(Row.class);

    public Board() {
        board.put(Row.EIGHT, Rank.createPiecesExceptPawn(Team.BLACK, 8));
        board.put(Row.SEVEN, Rank.createPawn(Team.BLACK, 7));
        for (int i = 3; i <= 6; i++) {
            board.put(Row.find(i), Rank.createBlank(i));
        }
        board.put(Row.TWO, Rank.createPawn(Team.WHITE, 2));
        board.put(Row.ONE, Rank.createPiecesExceptPawn(Team.WHITE, 1));
    }

    public Piece getPiece(Position position) {
        return board.get(position.getRow()).getPiece(position.getCol());
    }

    public void validateMovePiece(String source, String destination, Team team) {
        Piece piece = getPiece(Position.from(source));
        validateTeam(team, piece);
        List<Position> positions = piece.findPath(Position.from(destination));
        validateMovingPath(source, destination, piece, positions);
    }

    public boolean isKingDead(Piece piece) {
        return piece.isKing();
    }

    public Piece movePiece(String source, String destination, Team team) {
        Piece srcPiece = getPiece(Position.from(source));
        Piece dstPiece = getPiece(Position.from(destination));
        validateMovePiece(source, destination, team);
        srcPiece.move(Position.from(destination));
        changePiece(Position.from(source), Position.from(destination), srcPiece);
        return dstPiece;
    }

    private void validateTeam(Team team, Piece piece) {
        if (piece.getTeam() != team) {
            throw new IllegalArgumentException("다른 팀 말을 옮길 수 없습니다.");
        }
    }

    private void changePiece(Position source, Position destination, Piece piece) {
        board.get(source.getRow()).changePiece(source.getCol(), new Blank(Team.NONE, source));
        board.get(destination.getRow()).changePiece(destination.getCol(), piece);
    }

    private void validateMovingPath(String source, String destination, Piece piece, List<Position> positions) {
        if (getPiece(Position.from(source)).getTeam() == getPiece(Position.from(destination)).getTeam()) {
            throw new IllegalArgumentException("같은 팀은 kill 할 수 없습니다.");
        }
        if (piece.isPawn() && isDiagonal(Position.from(source), Position.from(destination))){
            validatePawnAttemptKill(destination);
            return;
        }
        validateExistOtherPiece(positions);
    }

    private void validatePawnAttemptKill(String destination) {
        if (getPiece(Position.from(destination)).isBlank()) {
            throw new IllegalArgumentException("잡을 수 있는 말이 없습니다.");
        }
    }

    private boolean isDiagonal(Position source, Position destination) {
        return Math.abs(source.getRow().getDifference(destination.getRow())) == 1
                && Math.abs(source.getCol().getDifference(destination.getCol())) == 1;
    }

    private void validateExistOtherPiece(List<Position> positions) {
        for (Position position : positions) {
            if (!getPiece(position).isBlank()) {
                throw new IllegalArgumentException("해당 위치로 말이 이동할 수 없습니다.");
            }
        }
    }

    public Map<Row, Rank> getBoard() {
        return board;
    }

    public double getTeamScore(Team team) {
        double totalScore = 0;
        Map<Column, Integer> pawnNeighbors = new EnumMap<>(Column.class);
        for (Row row : board.keySet()) {
            totalScore += board.get(row).calculateWhiteTotalScore(team);
            for (Column column : Column.values()) {
                if (board.get(row).isPawn(team, column)) {
                    pawnNeighbors.put(column, pawnNeighbors.getOrDefault(column, 0) + 1);
                }
            }
        }

        for (Column col : pawnNeighbors.keySet()) {
            int pawnCount = pawnNeighbors.get(col);
            if (pawnCount > 1) {
                totalScore -= pawnCount * 0.5;
            }
        }
        return totalScore;
    }
}
