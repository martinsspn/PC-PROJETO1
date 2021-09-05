package classesPrincipais;

import classesComuns.Chebychev;
import classesComuns.Imagem;
import classesComuns.LerCSV;
import classesComuns.TratamentoImagem;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spark {

    public static void main(String[] args) {
        File folder = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\PC-PROJETO1\\dataset_2019_1\\dataset");
        TratamentoImagem tratamento = new TratamentoImagem();
        Chebychev a = new Chebychev();
        ArrayList<Imagem> imagens = LerCSV.lerCSV();
        List<File> files = Arrays.asList(folder.listFiles());
        SparkConf conf = new SparkConf().setAppName("Programação Concorrente - Spark").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        long inicio = System.currentTimeMillis();
        JavaRDD<File> fileRDD = sc.parallelize(files);
        List<File> filesRDD = fileRDD.collect();
        for (File file: filesRDD) {
            System.out.println(a.KnnFunction(5, imagens, tratamento.TratamentodaImagem(file.getAbsolutePath())));
        }
        long fim  = System.currentTimeMillis();
        System.out.println("Tempo de duracao:" + (fim - inicio ));
    }
}
