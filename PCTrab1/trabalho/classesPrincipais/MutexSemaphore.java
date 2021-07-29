package classesPrincipais;

import ClassesThreads.ThreadMTXSEM;
import classesComuns.Imagem;

import classesComuns.LerCSV;

import java.io.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MutexSemaphore{

	public static File folder = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset");
	public static ArrayList<Imagem> imagens = new ArrayList<Imagem>();
	public static int index=0;
	public static Set<Integer> teste = new HashSet<>();

	public static void main(String[] args) {
		imagens = LerCSV.lerCSV();
		for(int i=0;i<7;i++){
			ThreadMTXSEM t = new ThreadMTXSEM();
			t.start();
		}
	}
}
