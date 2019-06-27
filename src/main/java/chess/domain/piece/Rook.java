package chess.domain.piece;

import java.util.ArrayList;
import java.util.List;

import chess.domain.*;

public class Rook extends NormalPiece {
	private static final double ROOK_SCORE = 5;
	private static final int MAX_DISTANCE_OF_ROOK = 7;

	private Rook(Player player, Type type, List<MovementInfo> movementInfos, Position currentPosition) {
		super(player, type, movementInfos, currentPosition, new Score(ROOK_SCORE));
	}

	public static Rook valueOf(Player player, Position currentPosition) {
		List<MovementInfo> movementInfos = new ArrayList<>();
		Direction.getRookDirection().forEach(direction ->
				movementInfos.add(new MovementInfo(direction, MAX_DISTANCE_OF_ROOK)));
		return new Rook(player, Type.ROOK, movementInfos, currentPosition);
	}
}