
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {


	public static int[][] sudoku = new int[9][9];
	
	public static void chargerSudoku(String nomFichier) throws IOException
	{
			BufferedReader in = new BufferedReader(new FileReader(nomFichier));
			String ligneLue=in.readLine();

			int i=0;
			while(ligneLue!=null)
			{
				
				String chaine=ligneLue.trim();
				String[] chaines=chaine.split(" ");
				for (int j=0;j<9;j++)
					sudoku[i][j]=Integer.parseInt(chaines[j]);
	
					i++;
					ligneLue=in.readLine();
			}
	}

	public static void Afficher()
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				System.out.print(sudoku[i][j]+" ");
			}
			System.out.println();
		}
			
	}

	public static void main(String args[]) {

		try {
			chargerSudoku("Sudoku1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Afficher();

	}



}
