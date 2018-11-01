import java.util.ArrayList;

public class Case {

private int valeur; 
private ArrayList<Integer> valeursPossibles= new ArrayList<Integer>();

public int getValeur() {
	return valeur;
}
public void setValeur(int valeur) {
	this.valeur = valeur;
}
public ArrayList<Integer> getvaleursPossibles() {
	return valeursPossibles;
}
public void setvaleursPossibles(ArrayList<Integer> valeursPossibles) {
	this.valeursPossibles = valeursPossibles;
}

public Case(int v)
{
	setValeur(v);
	if(v==0)
		for(int i=1;i<=9;i++)
			valeursPossibles.add(i);
}
public ArrayList<Integer> Reduire(int posX, int posY,Case[][]copieSudoku)
{
	
	//Ligne
	for(int j=0; j<9;j++)
	{
		if(valeursPossibles.contains(copieSudoku[posY][j].getValeur()))
		{
			valeursPossibles.remove(valeursPossibles.indexOf(copieSudoku[posY][j].getValeur()));
		}
			
	}
	//colonne
	for(int i=0; i<9;i++)
	{
		if(valeursPossibles.contains(copieSudoku[i][posX].getValeur()))
			valeursPossibles.remove(valeursPossibles.indexOf(copieSudoku[i][posX].getValeur()));
	}
	//carre
	int[]posCarre=Main.debutCarre(posX,posY);
	for(int i=posCarre[0];i<posCarre[0]+3;i++)
	{
		for(int j=posCarre[1];j<posCarre[1]+3;j++)
		{
			if(valeursPossibles.contains(copieSudoku[i][j].getValeur()))
				valeursPossibles.remove(valeursPossibles.indexOf(copieSudoku[i][j].getValeur()));
		}
	}
	
	return valeursPossibles;
}

}
