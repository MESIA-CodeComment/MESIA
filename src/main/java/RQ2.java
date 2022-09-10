import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import util.ReadWriteUtil;

import java.util.*;

/**
 * investigate existing approaches's capability to generate
 * supplementary code comments with different MESIA.
 */
public class RQ2 {
    public static void analyze(String trainResultPath, String testPath){
        String result = ReadWriteUtil.readFile(trainResultPath);
        String s = ReadWriteUtil.readFile(testPath);
        List<CodeComment> mesias = JSON.parseObject(s,new TypeReference<List<CodeComment>>(){});
        String[] lines = result.split("\n");
        HashMap<String, Double> Bleu = new HashMap<>();
        HashMap<String, Double> Rouge_l = new HashMap<>();
        HashMap<String, Double> MESIA = new HashMap<>();

        int linenum = 0;
        for (String r:lines) {
            JSONObject line = JSON.parseObject(r);
            String prediction = line.getString("predictions").substring(2, line.getString("predictions").length() - 2);
            String reference = line.getString("references").substring(2, line.getString("references").length() - 2);
            String bleu = line.getString("bleu");
            String rouge_l = line.getString("rouge_l");
            String id = line.getString("id");
            Bleu.put(id, Double.valueOf(bleu));
            Rouge_l.put(id, Double.valueOf(rouge_l));
            MESIA.put(id, mesias.get(linenum).MESIA);
            linenum++;
        }

        // rank the test set according to MESIA
        List<Map.Entry<String, Double>> sortMESIA = new ArrayList<Map.Entry<String, Double>>(MESIA.entrySet());
        Collections.sort(sortMESIA, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        //divide the test set into 10 parts and investigate the result
        int size = sortMESIA.size()/10;
        double bleu[] = new double[10];
        double rouge_l[] = new double[10];
        double mesia[] = new double[10];
        for(int i=0;i<10;++i){
            bleu[i]=0;
            rouge_l[i]=0;
            mesia[i]=0;
        }
        for(int i=0;i<size*10;++i){
            bleu[i/size]+=Bleu.get(sortMESIA.get(i).getKey());
            rouge_l[i/size]+=Rouge_l.get(sortMESIA.get(i).getKey());
            mesia[i/size]+=sortMESIA.get(i).getValue();
        }

        for(int i=0;i<10;++i){
            bleu[i]/=size;
            rouge_l[i]/=size;
            mesia[i]/=size;
        }

        System.out.println("BLEU:");
        for(int i=0;i<10;++i){
            System.out.println(bleu[i]);
        }

        System.out.println("ROUGE_L:");
        for(int i=0;i<10;++i){
            System.out.println(rouge_l[i]);
        }

        System.out.println("MESIA:");
        for(int i=0;i<10;++i){
            System.out.println(mesia[i]);
        }

        System.out.println("------------------------------------");
    }

    public static void main(String args[]){
        analyze(Config.seq2seqAlltrainResult,Config.dedupTestPath);
        analyze(Config.transformerAlltrainResult,Config.dedupTestPath);
    }
}
