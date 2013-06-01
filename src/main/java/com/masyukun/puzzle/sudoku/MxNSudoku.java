/**
 * MxN Sudoku Solver, v 0.20130424
 * https://github.com/masyukun/mxnsudoku
 * 
 * Copyright (c) 2013, Matthew Royal
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies, 
 * either expressed or implied, of the FreeBSD Project.
 * 
 */

package com.masyukun.puzzle.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class MxNSudoku {

  // Default values
	public static int HORZ = 9;
	public static int VERT = 9;
	public static int ROWS_IN_SQUARE = 3;
	public static int COLUMNS_IN_SQUARE = 3;
	public static int NUM_COL_SQUARES = 3;
	public static int NUM_ROW_SQUARES = 3;
	public static String EMPTY = "";
	
	public static ArrayList<String> NUMBERS 
		= new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9"));

	public static int MAX_SYMBOL_LENGTH = 1;
	
	/**
	 * Produce an empty grid, initialized with EMPTY Strings.
	 * @param horz number of columns
	 * @param vert number of rows
	 * @return the grid
	 */
	private static String[][] gridFactory(int horz, int vert) {
		// Create the grid
		String grid[][] = new String[horz][vert];
		
		// Fill the grid with blank strings
		for (int ii = 0; ii < horz; ++ii) {
			for (int jj = 0; jj < vert; ++jj) {
				grid[ii][jj] = EMPTY;
			}
		}

		return grid;
	}
	
	/**
	 * Initialize the Sudoku grid with a game. 0 is a blank board, as are unallocated game numbers.
	 * Numbers do not relate to difficulty; they are random and follow no rhyme or reason.
	 * @param gameNum
	 * @return
	 */
	static String[][] initGrid(Integer gameNum) {

		// Environment changes
		switch(gameNum) {
			case 12:
				HORZ = 12;
				VERT = 12;
				ROWS_IN_SQUARE = 3;
				COLUMNS_IN_SQUARE = 4;
				NUM_COL_SQUARES = 3;
				NUM_ROW_SQUARES = 4;
				NUMBERS.clear();
				NUMBERS = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9","A","B","C"));
				MAX_SYMBOL_LENGTH = 1;
				break;

			case 13:
				HORZ = 12;
				VERT = 12;
				ROWS_IN_SQUARE = 3;
				COLUMNS_IN_SQUARE = 4;
				NUM_COL_SQUARES = 3;
				NUM_ROW_SQUARES = 4;
				NUMBERS.clear();
				NUMBERS = new ArrayList<String>(Arrays.asList("A","Ab","B","Bb","C","C#","D","E","Eb","F","F#","G"));
				MAX_SYMBOL_LENGTH = 2;
				break;

			default:
				HORZ = 9;
				VERT = 9;
				ROWS_IN_SQUARE = 3;
				COLUMNS_IN_SQUARE = 3;
				NUM_COL_SQUARES = 3;
				NUM_ROW_SQUARES = 3;
				NUMBERS.clear();
				NUMBERS = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9"));
				MAX_SYMBOL_LENGTH = 1;
				break;
		}
		
		// Create the grid
		String grid[][] = gridFactory(HORZ, VERT);
		
		
		// Assign the symbols to the grid
		switch(gameNum) {
			case 1:
				// Case 1 - ???
				grid[0][0] = "3"; grid[0][3] = "9";
				grid[1][0] = "5"; grid[1][2] = "2"; grid[1][8] = "4";
				grid[2][1] = "9"; grid[2][2] = "4"; grid[2][5] = "8";
				grid[3][0] = "8"; grid[3][4] = "1"; grid[3][5] = "4"; grid[3][8] = "5";
				grid[4][2] = "1"; grid[4][4] = "5"; grid[4][6] = "4";
				grid[5][0] = "2"; grid[5][3] = "6"; grid[5][4] = "9"; grid[5][8] = "7";
				grid[6][3] = "5"; grid[6][6] = "1"; grid[6][7] = "6";
				grid[7][0] = "6"; grid[7][6] = "9"; grid[7][8] = "2";
				grid[8][8] = "3";
				break;

			case 2:
				// Case 2 - EASY
				grid[0][1] = "7"; grid[0][3] = "1"; grid[0][5] = "2"; grid[0][7] = "9";
				grid[1][0] = "3"; grid[1][2] = "2"; grid[1][3] = "8"; grid[1][4] = "6"; grid[1][8] = "7";
				grid[2][2] = "4"; grid[2][5] = "9"; grid[2][6] = "5"; grid[2][7] = "6"; 
				grid[3][0] = "4"; grid[3][2] = "5"; grid[3][7] = "2"; grid[3][8] = "6";
				grid[4][1] = "2"; grid[4][4] = "3"; grid[4][7] = "8";
				grid[5][0] = "9"; grid[5][1] = "1"; grid[5][6] = "7"; grid[5][8] = "4";
				grid[6][1] = "6"; grid[6][2] = "1"; grid[6][3] = "2"; grid[6][6] = "3";
				grid[7][0] = "7"; grid[7][4] = "8"; grid[7][5] = "3"; grid[7][6] = "2"; grid[7][8] = "1";
				grid[8][1] = "5"; grid[8][3] = "4"; grid[8][5] = "1"; grid[8][7] = "7";
				break;
				
			case 3:
				// Case 3 - MEDIUM
				grid[0][0] = "5"; grid[0][8] = "6"; 
				grid[1][0] = "1"; grid[1][2] = "9"; grid[1][4] = "3"; grid[1][6] = "8"; grid[1][8] = "7"; 
				grid[2][2] = "3"; grid[2][3] = "6"; grid[2][4] = "4"; grid[2][5] = "9"; grid[2][6] = "5";  
				grid[3][3] = "4"; grid[3][4] = "6"; grid[3][5] = "1"; 
				grid[4][0] = "6"; grid[4][8] = "3"; 
				grid[5][3] = "7"; grid[5][4] = "9"; grid[5][5] = "3"; 
				grid[6][2] = "4"; grid[6][3] = "2"; grid[6][4] = "8"; grid[6][5] = "6"; grid[6][6] = "1"; 
				grid[7][0] = "8"; grid[7][2] = "6"; grid[7][4] = "5"; grid[7][6] = "9"; grid[7][8] = "2"; 
				grid[8][0] = "2"; grid[8][8] = "4"; 
				break;
				
			case 4:
				// Case 4 - HARD
				grid[0][2] = "9"; grid[0][6] = "4"; grid[0][8] = "8"; 
				grid[1][0] = "4"; grid[1][3] = "1"; grid[1][7] = "2"; 
				grid[2][1] = "2"; grid[2][4] = "5"; grid[2][7] = "3"; 
				grid[3][1] = "6"; grid[3][2] = "2"; grid[3][5] = "4"; 
				grid[4][0] = "3"; grid[4][8] = "6"; 
				grid[5][3] = "7"; grid[5][6] = "8"; grid[5][7] = "5"; 
				grid[6][1] = "5"; grid[6][4] = "4"; grid[6][7] = "7"; 
				grid[7][1] = "3"; grid[7][5] = "9"; grid[7][8] = "5"; 
				grid[8][0] = "2"; grid[8][2] = "7"; grid[8][6] = "6"; 
				break;
				
			case 5:
				// Case 5 - EXTREME
				grid[0][1] = "9"; grid[0][2] = "4"; grid[0][4] = "8"; grid[0][6] = "2"; grid[0][7] = "1"; 
				grid[2][0] = "7"; grid[2][3] = "1"; grid[2][5] = "6"; grid[2][8] = "5"; 
				grid[3][1] = "5"; grid[3][3] = "2"; grid[3][5] = "9"; grid[3][7] = "7"; 
				grid[4][4] = "6"; 
				grid[5][1] = "2"; grid[5][3] = "3"; grid[5][5] = "5"; grid[5][7] = "4"; 
				grid[6][0] = "9"; grid[6][3] = "6"; grid[6][5] = "1"; grid[6][8] = "7"; 
				grid[8][1] = "3"; grid[8][2] = "1"; grid[8][4] = "2"; grid[8][6] = "8"; grid[8][7] = "9"; 
				break;
				
			case 12:
				// Case 12 - 12-tone Sudoku http://euge.ca/2012/07/06/dodecaphony/

				// Fill it in. 1=A,2=Ab,3=B,4=Bb,5=C,6=C#,7=D,8=E,9=Eb,A=F,B=F#,C=G
				grid[0][2] = "1"; grid[0][9] = "7";  
				grid[1][1] = "B"; grid[1][4] = "5"; grid[1][5] = "A";  grid[1][6] = "1"; grid[1][9] = "6";  grid[1][10] = "2";
				grid[2][0] = "9"; grid[2][1] = "2"; grid[2][3] = "8"; grid[2][4] = "C"; grid[2][7] = "B"; grid[2][11] = "A"; 
				grid[3][4] = "2"; grid[3][5] = "8"; grid[3][7] = "5"; grid[3][9] = "3"; 
				grid[4][2] = "3"; grid[4][3] = "9"; grid[4][8] = "2"; grid[4][9] = "8"; grid[4][10] = "5";
				grid[5][1] = "8"; grid[5][5] = "B"; grid[5][6] = "C"; grid[5][8] = "7"; grid[5][10] = "A"; 
				grid[6][1] = "C"; grid[6][3] = "1"; grid[6][5] = "3"; grid[6][6] = "9"; grid[6][10] = "8"; 
				grid[7][1] = "3"; grid[7][2] = "9"; grid[7][3] = "6"; grid[7][8] = "A"; grid[7][9] = "4"; 
				grid[8][2] = "2"; grid[8][4] = "4"; grid[8][6] = "6"; grid[8][7] = "C"; 
				grid[9][0] = "3"; grid[9][4] = "1"; grid[9][7] = "2"; grid[9][8] = "5"; grid[9][10] = "9"; grid[9][11] = "6"; 
				grid[10][1] = "6"; grid[10][2] = "A"; grid[10][5] = "5"; grid[10][6] = "8"; grid[10][7] = "7"; grid[10][10] = "3"; 
				grid[11][2] = "C"; grid[11][9] = "A";
				break;
					
			case 13:
				// Case 12 - 12-tone Sudoku with note names http://euge.ca/2012/07/06/dodecaphony/

				// Fill it in. 1=A,2=Ab,3=B,4=Bb,5=C,6=C#,7=D,8=E,9=Eb,A=F,B=F#,C=G
				grid[0][2] = "A"; grid[0][9] = "D";  
				grid[1][1] = "F#"; grid[1][4] = "C"; grid[1][5] = "F";  grid[1][6] = "A"; grid[1][9] = "C#";  grid[1][10] = "Ab";
				grid[2][0] = "Eb"; grid[2][1] = "Ab"; grid[2][3] = "E"; grid[2][4] = "G"; grid[2][7] = "F#"; grid[2][11] = "F"; 
				grid[3][4] = "Ab"; grid[3][5] = "E"; grid[3][7] = "C"; grid[3][9] = "B"; 
				grid[4][2] = "B"; grid[4][3] = "Eb"; grid[4][8] = "Ab"; grid[4][9] = "E"; grid[4][10] = "C";
				grid[5][1] = "E"; grid[5][5] = "F#"; grid[5][6] = "G"; grid[5][8] = "D"; grid[5][10] = "F"; 
				grid[6][1] = "G"; grid[6][3] = "A"; grid[6][5] = "B"; grid[6][6] = "Eb"; grid[6][10] = "E"; 
				grid[7][1] = "B"; grid[7][2] = "Eb"; grid[7][3] = "C#"; grid[7][8] = "F"; grid[7][9] = "Bb"; 
				grid[8][2] = "Ab"; grid[8][4] = "Bb"; grid[8][6] = "C#"; grid[8][7] = "G"; 
				grid[9][0] = "B"; grid[9][4] = "A"; grid[9][7] = "Ab"; grid[9][8] = "C"; grid[9][10] = "Eb"; grid[9][11] = "C#"; 
				grid[10][1] = "C#"; grid[10][2] = "F"; grid[10][5] = "C"; grid[10][6] = "E"; grid[10][7] = "D"; grid[10][10] = "B"; 
				grid[11][2] = "G"; grid[11][9] = "F";
				break;
					
			default:
				break;
		}
	
		return grid;
	}
	
	/**
	 * Initialize an MxN matrix of possible moves of the same order as the Sudoku grid with blank ArrayLists.
	 * @param grid
	 * @return
	 */
	static ArrayList<String>[][] initPossibles(String[][] grid) {

		// Create the possibles grid
		@SuppressWarnings("unchecked")
		ArrayList<String>[][] possibles = new ArrayList[HORZ][VERT];
		
		// Fill the grid with blank ArrayLists
		for (int ii = 0; ii < HORZ; ++ii) {
			for (int jj = 0; jj < VERT; ++jj) {
				possibles[ii][jj] = new ArrayList<String>();
			}
		}

		
		return possibles;
	}
	
	/**
	 * Print the Sudoku grid, assuming 9x9 board.
	 * @param grid
	 */
	static void printGrid(String[][] grid) {
		printGrid(grid, ROWS_IN_SQUARE, COLUMNS_IN_SQUARE);
	}
	
	/**
	 * Print the Sudoku grid
	 * @param grid String[][] representation of the Sudoku board
	 * @param horzSquare How many rows belong to a square
	 * @param vertSquare How many columns belong to a square
	 */
	static void printGrid(String[][] grid, int horzSquare, int vertSquare) {
		
		System.out.println("\nsudoku grid, current state");
		
		// For each row
		for (int ii = 0; ii < grid.length; ++ii) {

			// Print the horizontal bars
			for (int hCount = 0; 
					hCount < (grid[ii].length); 
					++hCount) {
				if (ii % horzSquare > 0) { 
					System.out.print("----");
					if (0 == hCount) {
						for (int barPadding = 0; barPadding < (grid[ii].length / horzSquare); ++barPadding) {
							System.out.print("-");
						}
					}
				} else {
					System.out.print("====");
					if (0 == hCount) {
						for (int barPadding = 0; barPadding < (grid[ii].length / horzSquare); ++barPadding) {
							System.out.print("=");
						}
					}
				}
			}
			
			// Print the numbers
			System.out.print("\n");
			for (int jj = 0; jj < grid[ii].length; ++jj) {
				System.out.printf("%s %s ", 
						(jj % vertSquare > 0) ? "|" : "||",
						(grid[ii][jj].equals(EMPTY)) 
							? ((MAX_SYMBOL_LENGTH == 1) ? " " : "  ") 
							: ((MAX_SYMBOL_LENGTH == 1 || (MAX_SYMBOL_LENGTH == grid[ii][jj].length())) 
									? grid[ii][jj] 
									: grid[ii][jj] + " ") );
 			}
			System.out.printf("%s\n", (grid[grid.length - 1].length % vertSquare > 0) ? "|" : "||");
		}

		// Print the last line
		for (int hCount = 0; 
				hCount < (grid[grid.length - 1].length); 
				++hCount) {
			System.out.print("====");
		}
		for (int barPadding = 0; barPadding < (grid[grid.length - 1].length / horzSquare); ++barPadding) {
			System.out.print("=");
		}
		System.out.printf("\n");
	}

	/**
	 * Print the matrix of possible moves, assuming 9x9 board.
	 * @param possibles
	 */
	static void printGrid(@SuppressWarnings("rawtypes") ArrayList[][] possibles) {
		printGrid(possibles, ROWS_IN_SQUARE, COLUMNS_IN_SQUARE);
	}
	
	/**
	 * Print the matrix of possible moves.
	 * @param possibles 
	 * @param horzSquare How many rows belong to a square
	 * @param vertSquare How many columns belong to a square
	 */
	@SuppressWarnings("unchecked")
	static void printGrid(@SuppressWarnings("rawtypes") ArrayList[][] possibles, int horzSquare, int vertSquare) {
		System.out.println("\npossible values, current state");

		// Tally the collection length for each column
		int barLength = 0;
		ArrayList<Integer> columnLengths = new ArrayList<Integer>();

		for (int jj = 0; jj < possibles.length; ++jj) {
			int maxBar = 0;

			for (int ii = 0; ii < possibles.length; ++ii) {
				if (possibles[ii][jj].size() > maxBar) {
					maxBar = possibles[ii][jj].size();
				}
			}
			
			columnLengths.add(maxBar);
			barLength += maxBar; 
		}
		
		// For each row 1:3, 2:6, 3:9, 4:12 [1, 2, 3, 4]
		for (int ii = 0; ii < possibles.length; ++ii) {
			
			// Print the horizontal bars
			if (ii % horzSquare > 0) { 
				// Print main line length
				for (int barCount = 0; barCount < barLength*3; ++barCount) {
					System.out.print("-");
				}
				// Add lines for cell padding and vertical cell wall
				for (int barPadding = 0; barPadding < (possibles[ii].length / horzSquare); ++barPadding) {
					System.out.print("-");
				}
			} else {
				// Print main line length
				for (int barCount = 0; barCount < barLength*3; ++barCount) {
					System.out.print("=");
				}
				
				// Add lines for cell padding and vertical cell wall
				for (int barPadding = 0; barPadding < (possibles[ii].length / horzSquare); ++barPadding) {
					System.out.print("=");
				}
			}
			
			
			// Print the numbers
			System.out.print("\n");
			for (int jj = 0; jj < possibles[ii].length; ++jj) {
				System.out.printf("%s ", (jj % vertSquare > 0) ? "|" : "||");
				printArray(possibles[ii][jj]);
				System.out.print(" ");
 			}
			System.out.printf("%s\n", (possibles[possibles.length - 1].length % vertSquare > 0) ? "|" : "||");
		}

		// Print the last line
		for (int barCount = 0; barCount < barLength*3; ++barCount) {
			System.out.print("=");
		}
		for (int barPadding = 0; barPadding < (possibles[possibles.length - 1].length / horzSquare); ++barPadding) {
			System.out.print("=");
		}
		System.out.printf("\n");

	}

	
	/**
	 * Get the nth vertical row of values from the Sudoku grid.
	 * @param grid
	 * @param index
	 * @return
	 */
	static ArrayList<String> getNthVertical(String[][] grid, Integer index) {
		ArrayList<String> vertresult = new ArrayList<String>();
		
		for (int ii = 0; ii < grid.length; ++ii) {
			if ( grid[ii][index] != EMPTY ) {
				vertresult.add(grid[ii][index]);
			} 
		}
		
		return vertresult;
	}
	
	/**
	 * Get the nth horizontal row of values from the Sudoku grid.
	 * @param grid
	 * @param index
	 * @return
	 */
	static ArrayList<String> getNthHorizontal(String[][] grid, Integer index) {
		ArrayList<String> horzresult = new ArrayList<String>();
		
		for (int ii = 0; ii < grid[0].length; ++ii) {
			if ( grid[index][ii] != EMPTY ) {
				horzresult.add(grid[index][ii]);
			}
		}
		
		return horzresult;
	}
	
	/**
	 * Default for a 9x9 grid
	 * @param grid
	 * @param index
	 * @return
	 */
	static ArrayList<String> getNthSquare(String[][] grid, Integer index) {
		return getNthSquare(grid, index, NUM_ROW_SQUARES, NUM_COL_SQUARES, ROWS_IN_SQUARE, COLUMNS_IN_SQUARE);
	}
	
	/**
	 * Get Nth square of an MxN Sudoku grid. Assumes 3x3 squares on board.
	 * @param grid
	 * @param index
	 * @param horzSquare How many rows belong to a square
	 * @param vertSquare How many columns belong to a square
	 * @param numRows Number of rows of squares are in the grid
	 * @param numColumns Number of columns of squares are in the grid
	 * @return
	 */
	static ArrayList<String> getNthSquare(String[][] grid, Integer index, Integer horzSquare, Integer vertSquare, Integer numRows, Integer numColumns) {
		ArrayList<String> sqrresult = new ArrayList<String>();
		boolean doneCopyingSquare = false;
		
		// Iterate through the squares
		Integer squareCounter = 0;
		for (Integer iibeg = 0, iiend = (numRows-1); iiend < (horzSquare*numRows); iibeg += numRows, iiend += numRows) {
			for (Integer jjbeg = 0, jjend = (numColumns-1); jjend < (vertSquare*numColumns); jjbeg += numColumns, jjend += numColumns, ++squareCounter) {
		
				// Only process the square we're interested in
				if (squareCounter != index) {
					continue;
				}
				
				// Serialize the square
				for (int ii = iibeg; ii <= iiend; ++ii) {
					for (int jj = jjbeg; jj <= jjend; ++jj) {
						if ( grid[ii][jj] != EMPTY ) {
							sqrresult.add(grid[ii][jj]);
						}
					}
				}
				
				doneCopyingSquare = true;
				break;
			}
			
			if (doneCopyingSquare) {
				break;
			}
		}

		return sqrresult;
	}

	/**
	 * Report the square number containing the absolute coordinate.
	 * Default is 9x9 matrix.
	 * @param m vertical
	 * @param n horizontal
	 * @return square number 0-8
	 */
	static Integer inWhichSquare(Integer m, Integer n) {
		return inWhichSquare(m, n, ROWS_IN_SQUARE, COLUMNS_IN_SQUARE, NUM_ROW_SQUARES, NUM_COL_SQUARES);
	}
	
	/**
	 * Report the square number containing the absolute coordinate.
	 * Square are numbered starting with 0 and are indexed from top left, across the columns, then down the rows.
	 * @param m
	 * @param n
	 * @param horzSquare How many rows belong to a square
	 * @param vertSquare How many columns belong to a square
	 * @param numRows Number of rows of squares are in the grid
	 * @param numColumns Number of columns of squares are in the grid
	 * @return
	 */
	static Integer inWhichSquare(Integer m, Integer n, Integer horzSquare, Integer vertSquare, Integer numRows, Integer numColumns) {
		Integer result = 0;
		boolean finished = false;

		
		// Do some figgering
		for (int ii = 1; ii <= numRows; ++ii) {
			for (int jj = 1; jj <= numColumns; ++jj) {
				if (m < (horzSquare*ii)) {
					if (n < (vertSquare*jj)) {
						finished = true;
						break;
					} else {
						result += 1;
					}
				} else {
					result += 1;
				}
			}

			// Nailed it
			if (finished) break;
		}
					
		return result;
	}
	

	/**
	 * Update the matrix of possible moves from the Sudoku grid.
	 * @param grid
	 * @param possibles
	 */
	static void updatePossibles(String[][] grid, ArrayList<String>[][] possibles) {
		for (int ii = 0; ii < grid.length; ++ii) {
			for (int jj = 0; jj < grid[ii].length; ++jj) {
				// Reset grid
				possibles[ii][jj].clear();
				
				if ( grid[ii][jj].equals(EMPTY) ) {
					possibles[ii][jj].addAll(NUMBERS);
					
					// Remove all the things it CAN'T be
					possibles[ii][jj].removeAll(getNthHorizontal(grid, ii));
					possibles[ii][jj].removeAll(getNthVertical(grid, jj));
					possibles[ii][jj].removeAll(getNthSquare(grid, inWhichSquare(ii, jj)));
				}
			}
		}
	}
	
	/**
	 * Play a move on the Sudoku grid and update the possibles matrix.
	 * @param grid
	 * @param horz
	 * @param vert
	 * @param symbol
	 * @param possibles
	 */
	@SuppressWarnings("unchecked")
	static void updateSquare(String[][] grid, int horz, int vert, String symbol, @SuppressWarnings("rawtypes") ArrayList[][] possibles) {
		
		// Sanity checking
		boolean insane = false;
		if (horz < 0 && horz >= HORZ) {
			System.out.println("updateSquare: horz ("+horz+") must be between 0 and " + (HORZ-1));
			insane = true;
		}
		if (vert < 0 && vert >= VERT) {
			System.out.println("updateSquare: vert ("+vert+") must be between 0 and " + (VERT-1));
			insane = true;
		}
		if (!NUMBERS.contains(symbol)) {
			System.out.println("updateSquare: symbol must be one of these: ");
			printArray(NUMBERS);
			insane = true;
		}
		if (insane) {
			return;
		}
		
		// Assign the symbol
		grid[horz][vert] = symbol;
		
		// Update the possibles matrix
		updatePossibles(grid, possibles);
	}
	
	/**
	 * Prints the contents of an array with beginning and ending brackets and nice commas.
	 * @param printMe
	 */
	static void printArray(ArrayList<String> printMe) {

		System.out.print("[");
		for (int ii = 0; ii < printMe.size(); ++ii) {
			if ((ii + 1) != printMe.size()) {
				System.out.printf("%s, ", printMe.get(ii));
			} else {
				System.out.printf("%s", printMe.get(ii));
			}
		}
		System.out.print("]");

	}
	

	/**
	 * Solve the easy ones -- where there's only 1 possibility
	 * @param grid
	 * @param poss
	 * @return true when it solved something
	 */
	@SuppressWarnings("rawtypes")
	static boolean solve(String[][] grid, ArrayList[][] poss) {
		boolean solvedSomething = false;
		
		for (int ii = 0; ii < poss.length; ++ii) {
			for (int jj = 0; jj < poss[ii].length; ++jj) {
				if (poss[ii][jj].size() == 1) {
					// Solved it! Check out my hook while updateSquare resolves it.
					System.out.println("grid["+(ii+1)+"]["+(jj+1)+"] = " + (String)poss[ii][jj].get(0) + " oneline");
					updateSquare(grid, ii, jj, (String)poss[ii][jj].get(0), poss);
					solvedSomething = true;
					break;
				}
			}
			if (solvedSomething) break;
		}
		
		
		return solvedSomething;
	}
	
	/**
	 * Sort of meta... Looks through the possibles matrix for values unique within their domain (horz, vert, square (TODO)) 
	 * for a unique value and plays it.
	 * @param grid
	 * @param poss
	 * @return boolean true if it found something
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static boolean solveExclusions(String[][] grid, ArrayList[][] poss) {
		boolean solvedIt = false;
		
		// Iterate horizontally through every possibles cell
		for (int ii = 0; ii < poss.length; ++ii) {
			for (int jj = 0; jj < poss[ii].length; ++jj) {
				
				// Skip the blanks
				if (poss[ii][jj].size() > 0) {
					ArrayList<String> solution = new ArrayList<String>();
					solution.addAll(poss[ii][jj]);
					
					// Subtract the other cells' possibles from this one
					for (int rowCount = 0; rowCount < poss[ii].length; ++rowCount) {
						if (rowCount == jj) {
							// Don't remove the current cell!
							continue;
						} else {
							solution.removeAll(poss[ii][rowCount]);
						}
					}
					
					if (solution.size() == 1) {
						// Solved it! Check out my hook while updateSquare resolves it.
						System.out.println("grid["+(ii+1)+"]["+(jj+1)+"] = " + solution.get(0) + " horz exclusion");
						updateSquare(grid, ii, jj, solution.get(0), poss);
						solvedIt = true;
						break;
					}
				}
			}
			if (solvedIt) break;
		}

		// Iterate vertically through every possibles cell
		for (int jj = 0; jj < poss.length; ++jj) {
			for (int ii = 0; ii < poss.length; ++ii) {
				
				// Skip the blanks
				if (poss[ii][jj].size() > 0) {
					ArrayList<String> solution = new ArrayList<String>();
					solution.addAll(poss[ii][jj]);
					
					// Subtract the other cells' possibles from this one
					for (int rowCount = 0; rowCount < poss.length; ++rowCount) {
						if (rowCount == ii) {
							// Don't remove the current cell!
							continue;
						} else {
							solution.removeAll(poss[rowCount][jj]);
						}
					}
					
					if (solution.size() == 1) {
						// Solved it! Check out my hook while updateSquare resolves it.
						System.out.println("grid["+(ii+1)+"]["+(jj+1)+"] = " + solution.get(0) + " vert exclusion");
						updateSquare(grid, ii, jj, solution.get(0), poss);
						solvedIt = true;
						break;
					}
				}
			}
			if (solvedIt) break;
		}
		

		return solvedIt;
	}
	
	/**
	 * Read in a puzzle file
	 * @param args
	 * @return a grid. Null if something terrible happened.
	 */
	private static String[][] readPuzzleFile(String[] args) {
		String[][] grid = null;
		BufferedReader sudokuFile = null;
		boolean chideUser = false;
		
		if (args[0].equals("-f")) {
			String filename = args[1];
			
			try {
				FileInputStream fis = new FileInputStream(filename);
				InputStreamReader in = new InputStreamReader(fis);
		        sudokuFile = new BufferedReader(in);
		        
		        // Read SudoCue files
		        if (filename.toLowerCase().contains(".sdk")) {
		        	Stack<String> puzzle = new Stack<String>();
		        	int puzzleWidth = -1;
		        	int puzzleHeight = 0;
		        	
		        	String line;
			        while((line = sudokuFile.readLine()) != null) {
			        	// Ignore comments 
			        	if (line.length() > 3 && line.trim().charAt(0) != '#') {
			        		
			            	// Get initial length read
			        		if (-1 == puzzleWidth) {
			            		puzzleWidth = line.length();
			            	} else if (puzzleWidth != line.length()) {
			            		// Puzzle is wibbly-wobbly
			            		System.out.println(String.format("The puzzle in \"%s\" is messed up.", filename));
			            		chideUser = true;
			            		break;
			            	}
			        		
			        		// Store the puzzle
			        		puzzle.push(line);
			        		puzzleHeight += 1;
			            }
			        }
			        
			        // Everything seems cool.
			        if (!chideUser) {
			        	// Init an empty grid
			        	grid = gridFactory(puzzleWidth, puzzleHeight);
			        	
			        	// Read puzzle coordinates backwards into the grid
			        	int ii;
			        	for (ii = puzzleHeight - 1; ii >= 0 && puzzle.size() > 0; --ii) {
			        		line = puzzle.pop();
			        		
			        		for (int jj = 0; jj < puzzleWidth; ++jj) {
			        			// Ignore blanks
			        			if (line.charAt(jj) != '.') {
			        				grid[ii][jj] = String.valueOf( line.charAt(jj) );
			        			}
			        		}
			        	}
			        	
			        	// Error check -- did we finish the puzzle?
			        	if (ii >= 0) {
			        		System.out.println(String.format("Puzzle height was weird, and I don't know why.\nWait -- I'm seeing something! It's the number... %d.\nDoes that mean something to you?", ii));
			        		chideUser = true;
			        	} else if (puzzle.size() > 0) {
			        		System.out.println(String.format("Puzzle height was weird... the top %d rows of the puzzle might be missing.", puzzle.size()));
			        		chideUser = true;			        		
			        	}
			        }
		        } else {
		        	System.out.println(String.format("\nThis version of mxnsudoku doesn't know how to read \"%s\"", filename));
		        	System.out.println("It can read: 1) SudoCue .SDK files");
            		chideUser = true;
		        }
		        
		        // Clean up time
		        sudokuFile.close();
				
			} catch (FileNotFoundException e) {
				System.out.println(String.format("\n\nThe file \"%s\" does not exist.\n", args[1]));
				e.printStackTrace();
        		chideUser = true;
			} catch (IOException e) {
				System.out.println(String.format("\n\nSomething wonky happened while trying to read the file \"%s\"\n", args[1]));
				e.printStackTrace();
        		chideUser = true;
			} 
		} else {
			System.out.println(String.format("\n Hmmm... I don't recognize the \"%s\" command-line option.", args[0]));
    		chideUser = true;
		}

		// Shut 'er down.
		if (chideUser) {
			grid = null;
		}
		
		return grid;
	}
	
	public static void main (String[] args) {
		
		String[][] grid = null;
		ArrayList<String>[][] poss;
		
		boolean chideUser = false;
		
		
		if (args.length == 1) {

			// Init the Sudoku grid
			try {
				grid = initGrid(Integer.parseInt(args[0]));

			} catch(NumberFormatException e) {
				chideUser = true;
			}
			
		} else if (args.length == 2) {
			// Read a file instead of using a built-in game
			grid = readPuzzleFile(args);
			
			if (null == grid) {
				// herp derp
				chideUser = true;
			}
		} else {
			chideUser = true;
		}

		
		// Chide the user
		if (chideUser) {
			System.out.println("\n\n   Usage: ./Sudoku game_number");
			System.out.println("\n\n   Usage: ./Sudoku -f sudocue_file.sdk\n\n");
			return;
		}
		

		// Show the user what we've got
		printGrid(grid);
		System.out.println("\n");

		// Init the possibles matrix
		poss = initPossibles(grid);
		updatePossibles(grid, poss);
		printGrid(poss);

		// Play the game
		int moves = 0;
		boolean oneLine = false;
		boolean exclusions = false;
		do {
			while ( (oneLine = solve(grid, poss)) || (exclusions = solveExclusions(grid, poss)) ) {
				System.out.println("{move " + ++moves + "} "
						+ ((exclusions) ? "exclusion " : "") 
						+ ((oneLine) ? "oneline " : ""));
				
			}
		} while(oneLine && exclusions);
		System.out.println("\n");

		
		// Print the end state
		printGrid(grid);
		printGrid(poss);
		
	}
	
	
	
}
