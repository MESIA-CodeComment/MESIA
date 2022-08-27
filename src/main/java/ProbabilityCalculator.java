import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import util.NLPUtil;
import util.ReadWriteUtil;

import java.util.*;

/**
 *  calculate the frequency and probability for each word in code comments of TL-codesum
 */
public class ProbabilityCalculator {
    public static void calculate(){
        String trainComment = ReadWriteUtil.readFile(Config.trainPath);
        String testComment = ReadWriteUtil.readFile(Config.testPath);
        String devComment = ReadWriteUtil.readFile(Config.validPath);

        String trainComments[] = trainComment.split("\n");
        String testComments[] = testComment.split("\n");
        String devComments[] = devComment.split("\n");

        Set<String> vocabulary = new HashSet<>();   //vocabulary of the comments in the dataset
        HashMap<String,Integer> counter = new HashMap<>(); //count frequency for each word

        for(String line:trainComments){
            Item item = JSON.parseObject(line,new TypeReference<Item>(){});
            List<String> words = NLPUtil.processComment(item.comment);
            for(String w:words){
                vocabulary.add(w);
                if(counter.containsKey(w)){
                    counter.put(w,counter.get(w)+1);
                }else{
                    counter.put(w,1);
                }
            }
        }

        for(String line:testComments){
            Item item = JSON.parseObject(line,new TypeReference<Item>(){});
            List<String> words = NLPUtil.processComment(item.comment);
            for(String w:words){
                vocabulary.add(w);
                if(counter.containsKey(w)){
                    counter.put(w,counter.get(w)+1);
                }else{
                    counter.put(w,1);
                }
            }
        }

        for(String line:devComments){
            Item item = JSON.parseObject(line,new TypeReference<Item>(){});
            List<String> words = NLPUtil.processComment(item.comment);
            for(String w:words){
                vocabulary.add(w);
                if(counter.containsKey(w)){
                    counter.put(w,counter.get(w)+1);
                }else{
                    counter.put(w,1);
                }
            }
        }

        List<Map.Entry<String,Integer>> sortList = new ArrayList<>(counter.entrySet());
        Collections.sort(sortList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        double sum = 0; // The total frequency of words
        for(Map.Entry<String,Integer>entry:sortList){
            sum+=entry.getValue();
        }

        HashMap<String,Double>probability = new HashMap<>();
        for(Map.Entry<String,Integer>entry:sortList){
            probability.put(entry.getKey(),entry.getValue().doubleValue()/sum);
        }

        ReadWriteUtil.writeFile(Config.frequencyPath,JSON.toJSONString(counter,true));
        ReadWriteUtil.writeFile(Config.probabilityPath, JSON.toJSONString(probability, true));
    }

    public static void main(String args[]){
        calculate();
    }
}
