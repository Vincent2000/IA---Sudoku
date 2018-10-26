
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


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
			in.close();
	}

	public static void Afficher()
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				System.out.print(sudoku[i][j]+" ");
				if(j%3==2)
					System.out.print("|");
			}
			System.out.println();
			if(i%3==2)
				System.out.println("---------------------");
		}
		
			
	}

	public static void AfficherC(int[][] c)
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				System.out.print(c[i][j]+" ");
				if(j%3==2)
					System.out.print("|");
			}
			System.out.println();
			if(i%3==2)
				System.out.println("---------------------");
		}
		System.out.println("******************************");
			
	}

	//MRV sur CARRE 
	public static int[] MRV(int posX, int posY, int[][] copieSudoku)
	{
		int comptCasesPleines=0;
		int comptMax=0;
		int numLigne=posY;
		for(int i=posY;i<posY+3;i++)
		{
			for(int j=posX; j<posX+3;j++)
			{
				if(copieSudoku[i][j]!=0)
					comptCasesPleines++;
				
			}
			if(comptMax<comptCasesPleines&&comptCasesPleines<3)
			{
				comptMax=comptCasesPleines;
				numLigne=i;
			}
			comptCasesPleines=0;
		}
		return RechercheCaseVide(numLigne,posX,copieSudoku);
		
	}
	public static int RechercheCasesPleines (int posX, int posY, int[][]copieSudoku)
	{
		int comptCasesPleines=0;
		for(int i=posY;i<posY+3;i++)
		{
			for(int j=posX; j<posX+3;j++)
			{
				if(copieSudoku[i][j]!=0)
					comptCasesPleines++;
			}
		}
		if(comptCasesPleines==9)
			comptCasesPleines=0;
		return comptCasesPleines;
	}
	
	public static int[] RechercheCaseVide(int numLigneSudoku, int posX,int[][]copieSudoku)
	{
		int[] aRetourner=new int[2];
		for(int j=posX;j<posX+3;j++)
		{
			if(copieSudoku[numLigneSudoku][j]==0)
				aRetourner=new int[]{numLigneSudoku,j};
		}
		return aRetourner;
	}
	
	public static int[] DH(int[][]copieSudoku)

	{
		int maxValue=0;
		int[] posCarre=new int[] {0,0};
		for(int i=0;i<9;i+=3)
		{
			for(int j=0;j<9;j+=3)
			{
				if(maxValue<RechercheCasesPleines(j,i,copieSudoku))
				{
					maxValue=RechercheCasesPleines(j,i,copieSudoku);
					posCarre=new int[] {i,j};
				}
			}
		}
		return posCarre;
	}
	

	public static ArrayList<Integer> Reduire(int posX, int posY,int[][]copieSudoku)
	{
		ArrayList<Integer> valeursPossibles=new ArrayList<Integer>();
		for(int i=1;i<=9;i++)
			valeursPossibles.add(i);
		//Ligne
		for(int j=0; j<9;j++)
		{
			int temp=copieSudoku[posY][j];
			if(valeursPossibles.contains(copieSudoku[posY][j]))
			{
				valeursPossibles.remove(valeursPossibles.indexOf(copieSudoku[posY][j]));
			}
				
		}
		//colonne
		for(int i=0; i<9;i++)
		{
			int temp =copieSudoku[i][posX];
			if(valeursPossibles.contains(copieSudoku[i][posX]))
				valeursPossibles.remove(valeursPossibles.indexOf(copieSudoku[i][posX]));
		}
		//carre
		int[]posCarre=debutCarre(posX,posY);
		for(int i=posCarre[0];i<posCarre[0]+3;i++)
		{
			for(int j=posCarre[1];j<posCarre[1]+3;j++)
			{
				int temp =copieSudoku[i][j];
				if(valeursPossibles.contains(copieSudoku[i][j]))
					valeursPossibles.remove(valeursPossibles.indexOf(copieSudoku[i][j]));
			}
		}
		
		return valeursPossibles;
	}

	public static int[] debutCarre(int posX, int posY)
	{
		int[]temp=new int[2];
		if(posX<3)
			temp[1]=0;
		if(posX>=3&&posX<6)
			temp[1]=3;
		if(posX>=6)
			temp[1]=6;
		if(posY<3)
			temp[0]=0;
		if(posY>=3&&posY<6)
			temp[0]=3;
		if(posY>=6)
			temp[0]=6;
		return temp;
	}
	public static int[][]Copie(int[][]tabACopier)
	{
		int[][]Copie= new int[9][9];
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				Copie[i][j]=tabACopier[i][j];
		return Copie;
	}
	public static boolean BacktrackingSearch()
	{
		return RecursiveBacktracking();
	}
	public static boolean RecursiveBacktracking(/*int[][] copieSudoku*/)
	{
		if(Complet(sudoku))
		{
			sudoku=Copie(sudoku);
			return true;
		}
		int[] temp=DH(sudoku);
		int[]temp2=MRV(temp[1],temp[0],sudoku);
		ArrayList<Integer> valeursPossibles= Reduire(temp2[1],temp2[0],sudoku);
		if(valeursPossibles.size()==0)
			return false;
		else if (valeursPossibles.size()==1)
		{
			sudoku[temp2[0]][temp2[1]]=valeursPossibles.get(0);
			//int[][]tempTab=Copie(sudoku);
			AfficherC(sudoku);
			return RecursiveBacktracking();
		}
		else 
		{
			//int[][]copieCopieSudoku=Copie(copieSudoku);
			for (int valeur : valeursPossibles) {
				sudoku[temp2[1]][temp2[0]]=valeur;
				if(RecursiveBacktracking())	
				{
					//copieSudoku=Copie(sudoku);
					return true;
				}	
			}
			return false;
		}
	}
	public static boolean Complet(int[][]copieSudoku)
	{
		boolean aRetourner=true;
		for(int i=0;i<9&&aRetourner;i++)
			for(int j=0;j<9&&aRetourner;j++)
				if(copieSudoku[i][j]==0)
					aRetourner=false;
		return aRetourner;
	}
	public static void main(String args[]) {

		try {
			chargerSudoku("Sudoku1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Avant : ");
		Afficher();
		BacktrackingSearch();
		System.out.println("Apr�s : ");
		Afficher();

	}



}
