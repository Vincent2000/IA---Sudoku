import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static Case[][] sudoku = new Case[9][9];
	public static boolean rec = false;

	public static void chargerSudoku(String nomFichier) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(nomFichier));
		String ligneLue = in.readLine();

		int i = 0;
		while (ligneLue != null) {

			String chaine = ligneLue.trim();
			String[] chaines = chaine.split(" ");
			for (int j = 0; j < 9; j++)
				sudoku[i][j] = new Case(Integer.parseInt(chaines[j]));

			i++;
			ligneLue = in.readLine();
		}
		in.close();
	}

	public static void Afficher() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(sudoku[i][j].getValeur() + " ");
				if (j % 3 == 2)
					System.out.print("|");
			}
			System.out.println();
			if (i % 3 == 2)
				System.out.println("---------------------");
		}

	}

	public static void AfficherC(Case[][] c) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(c[i][j].getValeur() + " ");
				if (j % 3 == 2)
					System.out.print("|");
			}
			System.out.println();
			if (i % 3 == 2)
				System.out.println("---------------------");
		}
		System.out.println("******************************");

	}

	// MRV sur CARRE
	//On recherche la case qui a le plus de voisins dans son carre
	public static int[] MRV(int posX, int posY, Case[][] copieSudoku) {
		int comptCasesPleines = 0;
		int comptMax = 0;
		int numLigne = posY;
		for (int i = posY; i < posY + 3; i++) {
			for (int j = posX; j < posX + 3; j++) {
				if (copieSudoku[i][j].getValeur() != 0)
					comptCasesPleines++;

			}
			//Si la ligne n'est pas pleine alors on peut la considérer
			if (comptMax < comptCasesPleines && comptCasesPleines < 3) {
				comptMax = comptCasesPleines;
				numLigne = i;
			}
			//Si la ligne est vide alors on choisit la dernière ligne vide
			else if (comptCasesPleines == 0) {
				comptMax = comptCasesPleines;
				numLigne = i;
			}
			comptCasesPleines = 0;
		}
		return RechercheCaseVide(numLigne, posX, copieSudoku);

	}

	//On renvoie le carre qui a le plus de cases pleines
	public static int RechercheCasesPleines(int posX, int posY, Case[][] copieSudoku) {
		int comptCasesPleines = 0;
		for (int i = posY; i < posY + 3; i++) {
			for (int j = posX; j < posX + 3; j++) {
				if (copieSudoku[i][j].getValeur() != 0)
					comptCasesPleines++;
			}
		}
		//Si le carre est rempli cela ne sert à rien d'y aller 
		if (comptCasesPleines == 9)
			comptCasesPleines = 0;
		return comptCasesPleines;
	}
	//On recherche une case qui est vide dans un carre
	public static int[] RechercheCaseVide(int numLigneSudoku, int posX, Case[][] copieSudoku) {
		int[] aRetourner = new int[2];
		for (int j = posX; j < posX + 3; j++) {
			if (copieSudoku[numLigneSudoku][j].getValeur() == 0)
				aRetourner = new int[] { numLigneSudoku, j };
		}
		return aRetourner;
	}

	//On recherche le carre qui a le plus de contrainte
	public static int[] DH(Case[][] copieSudoku) {
		int maxValue = 0;
		int[] posCarre = new int[] { 0, 0 };
		for (int i = 0; i < 9; i += 3) {
			for (int j = 0; j < 9; j += 3) {
				if (maxValue < RechercheCasesPleines(j, i, copieSudoku)) {
					maxValue = RechercheCasesPleines(j, i, copieSudoku);
					posCarre = new int[] { i, j };
				}
			}
		}
		return posCarre;
	}

	public static int[] debutCarre(int posX, int posY) {
		int[] temp = new int[2];
		if (posX < 3)
			temp[1] = 0;
		if (posX >= 3 && posX < 6)
			temp[1] = 3;
		if (posX >= 6)
			temp[1] = 6;
		if (posY < 3)
			temp[0] = 0;
		if (posY >= 3 && posY < 6)
			temp[0] = 3;
		if (posY >= 6)
			temp[0] = 6;
		return temp;
	}

	public static Case[][] Copie(Case[][] tabACopier) {
		Case[][] Copie = new Case[9][9];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				Copie[i][j] = new Case(tabACopier[i][j].getValeur());
		return Copie;
	}

	public static boolean BacktrackingSearch() {
		return RecursiveBacktracking();
	}

	@SuppressWarnings("unchecked")
	public static boolean RecursiveBacktracking(/* int[][] copieSudoku */) {
		if (Complet(sudoku)) {
			return true;
		}
		int[] temp = DH(sudoku);
		int[] temp2 = MRV(temp[1], temp[0], sudoku);
		if(!rec)
			AC3(temp2[1],temp2[0]);
		ArrayList<Integer> valeursPossiblestemp = new ArrayList<Integer>();
		valeursPossiblestemp = (ArrayList<Integer>) (sudoku[temp2[0]][temp2[1]].getvaleursPossibles()).clone();
		ArrayList<Integer> valeursPossibles = sudoku[temp2[0]][temp2[1]].Reduire(temp2[1], temp2[0], sudoku);
		if (valeursPossibles.size() == 0 && rec) {
			sudoku[temp2[0]][temp2[1]].setvaleursPossibles(valeursPossiblestemp);
			return false;
		} else if (valeursPossibles.size() == 0) {
			return false;
		} else if (valeursPossibles.size() == 1) {
			sudoku[temp2[0]][temp2[1]].setValeur(valeursPossibles.get(0));
			AfficherC(sudoku);
			boolean aRetourner = RecursiveBacktracking();
			if (!rec)
				return aRetourner;
			else if (rec && !aRetourner) {
				sudoku[temp2[0]][temp2[1]].setValeur(0);
				sudoku[temp2[0]][temp2[1]].setvaleursPossibles(valeursPossiblestemp);
				return aRetourner;
			} else
				return aRetourner;
		} else {
			for (int valeur : valeursPossibles) {

				sudoku[temp2[0]][temp2[1]].setValeur(valeur);
				rec = true;
				System.out.println("Récursion avec val : " + valeur);
				AfficherC(sudoku);
				System.out.println("======================");
				if (RecursiveBacktracking()) {
					rec = false;
					return true;
				}
			}
			sudoku[temp2[0]][temp2[1]].setValeur(0);
			sudoku[temp2[0]][temp2[1]].setvaleursPossibles(valeursPossiblestemp);
			return false;
		}
	}

	public static boolean Complet(Case[][] copieSudoku) {
		boolean aRetourner = true;
		for (int i = 0; i < 9 && aRetourner; i++)
			for (int j = 0; j < 9 && aRetourner; j++)
				if (copieSudoku[i][j].getValeur() == 0)
					aRetourner = false;
		return aRetourner;
	}

	@SuppressWarnings("unchecked")
	public static boolean RemovedIV(Integer[] posCase) {
		boolean removed = false;
		Case[][] tempSudoku = Copie(sudoku);
		ArrayList<Integer> tempValP=(ArrayList<Integer>) sudoku[posCase[1]][posCase[0]].getvaleursPossibles().clone();
		for (int i : sudoku[posCase[1]][posCase[0]].getvaleursPossibles()) {
			tempSudoku[posCase[1]][posCase[0]] = new Case(i);
			ArrayList<Integer> tempValJ=(ArrayList<Integer>) sudoku[posCase[3]][posCase[2]].getvaleursPossibles().clone();
			if (tempSudoku[posCase[3]][posCase[2]].Reduire(posCase[2], posCase[3], tempSudoku).isEmpty()) {
				tempSudoku[posCase[3]][posCase[2]].setvaleursPossibles(tempValJ);
				if(tempValP.contains(i))
				{
					tempValP.remove(tempValP.indexOf(i));
					removed = true;
				}
			}
		}
		sudoku[posCase[1]][posCase[0]].setvaleursPossibles(tempValP);
		return removed;
	}

	public static ArrayList<Integer[]> TrouverContrainte(int posX, int posY, Case[][] copieSudoku) {

		ArrayList<Integer[]> pile = new ArrayList<Integer[]>();

		// Ligne
		for (int j = 0; j < 9; j++) {
			if (copieSudoku[posY][j].getValeur() == 0 && j != posX) {
				pile.add(new Integer[] { posX, posY, j, posY });
			}

		}
		// colonne
		for (int i = 0; i < 9; i++) {
			if (copieSudoku[i][posX].getValeur() == 0 && i != posY)
				pile.add(new Integer[] { posX, posY, posX, i });
		}
		// carre
		int[] posCarre = Main.debutCarre(posX, posY);
		for (int i = posCarre[0]; i < posCarre[0] + 3; i++) {
			for (int j = posCarre[1]; j < posCarre[1] + 3; j++) {
				if (copieSudoku[i][j].getValeur() == 0 && i != posY && j != posX)
					pile.add(new Integer[] { posX, posY, j, i });
			}
		}

		return pile;
	}

	public static void AC3(int posX, int posY) {
		ArrayList<Integer[]> pile = new ArrayList<Integer[]>();
		pile.addAll(TrouverContrainte(posX, posY, sudoku));
		while (!pile.isEmpty()) {
			if (RemovedIV(pile.get(0))) {
				pile.addAll(TrouverContrainte(pile.get(0)[2], pile.get(0)[3], sudoku));
			}
			pile.remove(0);
		}
	}

	public static void main(String args[]) {
		try {
			chargerSudoku("Sudoku2.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Avant : ");
		Afficher();
		System.out.println("***********************************");
		BacktrackingSearch();
		System.out.println("Apres : ");
		Afficher();

	}

}
