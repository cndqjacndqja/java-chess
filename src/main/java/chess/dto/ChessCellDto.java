package chess.dto;

import java.util.Objects;

public class ChessCellDto {
    private String position;
    private String teamColor;
    private String pieceType;
    private boolean alive;

    public ChessCellDto(String position, String teamColor, String pieceType, boolean alive) {
        this.position = position;
        this.teamColor = teamColor;
        this.pieceType = pieceType;
        this.alive = alive;
    }

    public String getPosition() {
        return position;
    }

    public String getTeamColor() {
        return teamColor;
    }

    public String getPieceType() {
        return pieceType;
    }

    public boolean getAlive() {
        return alive;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessCellDto that = (ChessCellDto) o;
        return Objects.equals(position, that.position) && Objects.equals(teamColor, that.teamColor) && Objects.equals(pieceType, that.pieceType) && Objects.equals(alive, that.alive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, teamColor, pieceType, alive);
    }
}