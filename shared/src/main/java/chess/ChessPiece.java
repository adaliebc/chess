package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pieceColor = null;
    ChessPiece.PieceType type = null;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();
        int rowMax = 7;
        int colMax = 7;
        int rowMin = 0;
        int colMin = 0;
        if (type == PieceType.BISHOP) {
            // top right diagonal
            // while col is between colMin and colMax, and row is between rowMin and rowMax
            //      col++ and row++
            //      add coordinates to validMoves
            //      return col and row

            //top left diagonal
            //while col is between colMin and colMax, and row is between rowMin and rowMax
            //      col = col -1 and row ++
            //      add coordinates to validMoves
            //      return col and row

            //bottom left diagonal
            //while col is between colMin and colMax, and row is between rowMin and rowMax
            //      col = col-1 and row = row-1
            //      add coordinates to validMoves
            //      return col and row

            //bottom right diagonal
            //while col is between colMin and colMax, and row is between rowMin and rowMax
            //      col = col-1 and row++
            //      add coordinated to validMoves
            //      return col and row
        }
        //return valid moves
        return validMoves;
    }
}
