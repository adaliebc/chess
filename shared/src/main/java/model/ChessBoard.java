package model;

import chess.ChessGame;

import java.util.Objects;

public record ChessBoard(chess.ChessBoard chessGame) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.equals(chessGame, that.chessGame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chessGame);
    }
}
