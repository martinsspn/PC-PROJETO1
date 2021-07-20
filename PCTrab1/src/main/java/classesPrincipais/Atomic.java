package classesPrincipais;

import ClassesThreads.ThreadAtomic;
import classesComuns.Imagem;
import classesComuns.Index;
import classesComuns.Leitura;
import classesComuns.LerCSV;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Atomic{

	public static File folder = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset");
	public static ArrayList<Imagem> imagens = new ArrayList<Imagem>();
	public static volatile Index index = new Index();
	public static volatile AtomicInteger idx = new AtomicInteger(0);

	public static void main(String[] args) {
		imagens = LerCSV.lerCSV();
		for(int i=0;i<5;i++){
			ThreadAtomic t = new ThreadAtomic();
			t.start();
		}
	}
}
