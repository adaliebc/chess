package chess;

import java.util.*;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
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
        int rowMin = 1;
        int colMin = 1;
        if (type == PieceType.BISHOP) {
            int currentCol = myPosition.getColumn();
            int currentRow = myPosition.getRow();
            // top right diagonal
            while (currentRow <= rowMax && currentCol <= colMax) {
                currentCol ++;
                currentRow ++;
                ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }

            int currentCol2 = myPosition.getColumn();
            int currentRow2 = myPosition.getRow();

            //top left diagonal
            while (currentRow2 <= rowMax && currentCol2 > colMin) {
                currentCol2 --;
                currentRow2 ++;
                ChessPosition coordinates = new ChessPosition(currentRow2, currentCol2);
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }

            //bottom left diagonal
            int currentCol3 = myPosition.getColumn();
            int currentRow3 = myPosition.getRow();

            while (currentRow3 > rowMin && currentCol3 > colMin) {
                currentCol3 --;
                currentRow3 --;
                ChessPosition coordinates = new ChessPosition(currentRow3, currentCol3);
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }

            //bottom right diagonal
            int currentCol4 = myPosition.getColumn();
            int currentRow4 = myPosition.getRow();

            while (currentRow4 > rowMin && currentCol <= colMax) {
                currentCol4 ++;
                currentRow4 --;
                ChessPosition coordinates = new ChessPosition(currentRow4, currentCol4);
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }
        }
        //return valid moves
        return validMoves;
    }
}
