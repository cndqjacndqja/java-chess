package chess.domain.piece;

import chess.domain.Team;
import chess.domain.piece.Bishop;
import chess.domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BishopTest {
    @ParameterizedTest
    @ValueSource(strings = {"d4", "c5", "d6", "e5"})
    @DisplayName("출발 지점과 도착 지점을 입력 후, 경로 리스트를 조회한다.")
    void validateIsPossible(String input) {
        Position source = Position.from("d5");
        Bishop bishop = new Bishop(Team.BLACK);
        Assertions.assertThatThrownBy(() -> {
                    bishop.findPath(source, Position.from(input));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 말이 움직일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"h7", "a8", "b1", "h1"})
    @DisplayName("출발 지점과 도착 지점을 입력 후, 이동 가능한 경로인지 검증한다.")
    void isPossible(String input) {
        Position source = Position.from("e4");
        Bishop bishop = new Bishop(Team.BLACK);
        bishop.findPath(source, Position.from(input));
    }

    @Test
    @DisplayName("출발 지점과 도착 지점을 입력 후, 경로 리스트를 조회한다.")
    void findPath() {
        Position source = Position.from("e4");
        Bishop bishop = new Bishop(Team.BLACK);
        List<Position> path = bishop.findPath(source, Position.from("h7"));
        assertThat(path).containsExactly(Position.from("f5"), Position.from("g6"));
    }
}