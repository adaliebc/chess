package chess;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] squares = new ChessPiece[9][9];
    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
       return squares[position.getRow()][position.getColumn()];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    @Override
    public String toString() {
        String box = "";
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                ChessPiece item = getPiece(new ChessPosition(i, j));
                if(item != null) {
                    box += item.toString();
                } else {
                    box += " ";
                }
            }
            box += "\n";
        }
        return box;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {

        //Create Pieces
        ChessPiece blackQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        ChessPiece blackKing= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPiece blackKnight = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece blackBishop = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece blackRook = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece blackPawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);

        ChessPiece whiteQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPiece whiteKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPiece whiteKnight = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece whiteBishop = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece whiteRook = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece whitePawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);

        //Create Positions
        ChessPosition place = new ChessPosition(8, 1);
        addPiece(place, blackRook);
        place = new ChessPosition(8, 8);
        addPiece(place, blackRook);
        place = new ChessPosition(8, 2);
        addPiece(place, blackKnight);
        place = new ChessPosition(8, 7);
        addPiece(place, blackKnight);
        place = new ChessPosition(8, 3);
        addPiece(place, blackBishop);
        place = new ChessPosition(8, 6);
        addPiece(place, blackBishop);
        place = new ChessPosition(8, 4);
        addPiece(place, blackQueen);
        place = new ChessPosition(8, 5);
        addPiece(place, blackKing);

        for (int i = 1; i < 9; i ++) {
            place = new ChessPosition(7, i);
            addPiece(place, blackPawn);
        }
        place = new ChessPosition(1, 1);
        addPiece(place, whiteRook);
        place = new ChessPosition(1, 8);
        addPiece(place, whiteRook);
        place = new ChessPosition(1, 2);
        addPiece(place, whiteKnight);
        place = new ChessPosition(1, 7);
        addPiece(place, whiteKnight);
        place = new ChessPosition(1, 3);
        addPiece(place, whiteBishop);
        place = new ChessPosition(1, 6);
        addPiece(place, whiteBishop);
        place = new ChessPosition(1, 4);
        addPiece(place, whiteQueen);
        place = new ChessPosition(1, 5);
        addPiece(place, whiteKing);

        for (int i = 1; i < 9; i ++) {
            place = new ChessPosition(2, i);
            addPiece(place, whitePawn);
        }
        }

    public ChessPosition getKing(ChessGame.TeamColor team) {
        for (int x = 1; x < 9; x++){
            for (int y = 1; y < 9; y++){
                ChessPosition place = new ChessPosition(x, y);
                ChessPiece piece = getPiece(place);
                    if (piece != null){
                        if (piece.getTeamColor() == team && piece.getPieceType() == ChessPiece.PieceType.KING) {
                            return place;
                        }
                    }
                }
            }
        return null;
    }

    public Collection<ChessPosition> getOtherPieces(ChessGame.TeamColor team) {

        Collection<ChessPosition> pieceLocations = new HashSet<ChessPosition>();
        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                ChessPosition place = new ChessPosition(x, y);
                ChessPiece piece = getPiece(place);
                if (piece != null){
                    if (piece.getTeamColor() == team) {
                        pieceLocations.add(place);
                    }
                }
            }
        }
        return pieceLocations;
    }
    }
