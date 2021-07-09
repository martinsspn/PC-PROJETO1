import java.util.ArrayList;

public class Imagem {
	private ArrayList<Float> arrayOfFeatures;
	private String classe;
	
	public Imagem(ArrayList<Float> arrayOfFeatures, String classe) {
		this.arrayOfFeatures = arrayOfFeatures;
		this.classe = classe;
	}
	
	public ArrayList<Float> getLista(){
		return this.arrayOfFeatures;
	}
	
	public String getClasse() {
		return this.classe; 
	}
}