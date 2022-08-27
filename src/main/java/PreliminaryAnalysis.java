import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import util.NLPUtil;
import util.ReadWriteUtil;

import java.util.*;

/**
 * Conduct a two-step analysis for code comments in TL-codesum
 * We want to find out what is the proportion of words that is remaining in a given code comment.
 */

public class PreliminaryAnalysis {

    /**
     * This is the English stop words provided by nltk.
     * can be accessed from here:
     * https://raw.githubusercontent.com/nltk/nltk_data/gh-pages/packages/corpora/stopwords.zip
     */
    public static String[]stopwords = {
            "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "you're", "you've", "you'll", "you'd", "your",
            "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "she's", "her", "hers", "herself", "it",
            "it's", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this",
            "that", "that'll", "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had",
            "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above",
            "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then", "once",
            "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some",
            "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don",
            "don't", "should", "should've", "now", "d", "ll", "m", "o", "re", "ve", "y", "ain", "aren", "aren't", "couldn", "couldn't",
            "didn", "didn't", "doesn", "doesn't", "hadn", "hadn't", "hasn", "hasn't", "haven", "haven't", "isn", "isn't", "ma",
            "mightn", "mightn't", "mustn", "mustn't", "needn", "needn't", "shan", "shan't", "shouldn", "shouldn't", "wasn", "wasn't",
            "weren", "weren't", "won", "won't", "wouldn", "wouldn't"
    };

    public static void analysis(){
        List<String>Stopwords = Arrays.asList(stopwords);
        String trainComment = ReadWriteUtil.readFile(Config.trainPath);
        String testComment = ReadWriteUtil.readFile(Config.testPath);
        String devComment = ReadWriteUtil.readFile(Config.validPath);

        String trainComments[] = trainComment.split("\n");
        String testComments[] = testComment.split("\n");
        String devComments[] = devComment.split("\n");

        HashMap<Integer,Integer>remaining2num = new HashMap<>();
        HashMap<Double,Integer>ratio2num = new HashMap<>();

        for(String line:trainComments){
            Item item = JSON.parseObject(line,new TypeReference<Item>(){});
            List<String> signatureTokens = new ArrayList<>();
            signatureTokens.addAll(NLPUtil.getSignatureTokens(item.code));
            List<String>commentTokens = NLPUtil.processComment(item.comment);
            List<String>stemCode = NLPUtil.Stem(signatureTokens);

            int remaining = 0;    //number of words remaining in a comment after removing stop words and tokens in API signature
            for(String w:commentTokens){
                if(stemCode.contains(w))continue;
                if(Stopwords.contains(w))continue;
                remaining++;
            }
            if(remaining2num.containsKey(remaining)){
                remaining2num.put(remaining,remaining2num.get(remaining)+1);
            }else{
                remaining2num.put(remaining,1);
            }

            double ratio = ((double)remaining)/commentTokens.size();
            if(ratio2num.containsKey(ratio)){
                ratio2num.put(ratio,ratio2num.get(ratio)+1);
            }else{
                ratio2num.put(ratio,1);
            }
        }


        for(String line:devComments){
            Item item = JSON.parseObject(line,new TypeReference<Item>(){});
            List<String> signatureTokens = new ArrayList<>();
            signatureTokens.addAll(NLPUtil.getSignatureTokens(item.code));
            List<String>commentTokens = NLPUtil.processComment(item.comment);
            List<String>stemCode = NLPUtil.Stem(signatureTokens);

            int remaining = 0;    //number of words remaining in a comment after removing stop words and tokens in API signature
            for(String w:commentTokens){
                if(stemCode.contains(w))continue;
                if(Stopwords.contains(w))continue;
                remaining++;
            }
            if(remaining2num.containsKey(remaining)){
                remaining2num.put(remaining,remaining2num.get(remaining)+1);
            }else{
                remaining2num.put(remaining,1);
            }

            double ratio = ((double)remaining)/commentTokens.size();
            if(ratio2num.containsKey(ratio)){
                ratio2num.put(ratio,ratio2num.get(ratio)+1);
            }else{
                ratio2num.put(ratio,1);
            }
        }


        for(String line:testComments){
            Item item = JSON.parseObject(line,new TypeReference<Item>(){});
            List<String> signatureTokens = new ArrayList<>();
            signatureTokens.addAll(NLPUtil.getSignatureTokens(item.code));
            List<String>commentTokens = NLPUtil.processComment(item.comment);
            List<String>stemCode = NLPUtil.Stem(signatureTokens);

            int remaining = 0;    //number of words remaining in a comment after removing stop words and tokens in API signature
            for(String w:commentTokens){
                if(stemCode.contains(w))continue;
                if(Stopwords.contains(w))continue;
                remaining++;
            }
            if(remaining2num.containsKey(remaining)){
                remaining2num.put(remaining,remaining2num.get(remaining)+1);
            }else{
                remaining2num.put(remaining,1);
            }

            double ratio = ((double)remaining)/commentTokens.size();
            if(ratio2num.containsKey(ratio)){
                ratio2num.put(ratio,ratio2num.get(ratio)+1);
            }else{
                ratio2num.put(ratio,1);
            }
        }

        List<Map.Entry<Integer,Integer>> remaining2numList= new ArrayList<>(remaining2num.entrySet());
        remaining2numList.sort(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getKey()-o2.getKey();
            }
        });

        printRemainingNumResult(remaining2numList);
        printRemainingRatioResult(ratio2num);
    }

    public static void printRemainingNumResult(List<Map.Entry<Integer,Integer>> remaining2numList){
        for(Map.Entry<Integer,Integer>entry:remaining2numList){
            System.out.println(entry.getKey());
        }
        System.out.println("----------------------------");
        for(Map.Entry<Integer,Integer>entry:remaining2numList){
            System.out.println(entry.getValue());
        }
    }

    public static void printRemainingRatioResult( HashMap<Double,Integer>ratio2num){
        int ratioNum[] = new int[10];
        for(int i=0;i<10;++i){
            ratioNum[i]=0;
        }

        for(Map.Entry<Double,Integer>entry:ratio2num.entrySet()){
            if(entry.getKey()<=0.1){
                ratioNum[0]+=entry.getValue();
            }else if(entry.getKey()<=0.2){
                ratioNum[1]+=entry.getValue();
            }else if(entry.getKey()<=0.3){
                ratioNum[2]+=entry.getValue();
            }else if(entry.getKey()<=0.4){
                ratioNum[3]+=entry.getValue();
            }else if(entry.getKey()<=0.5){
                ratioNum[4]+=entry.getValue();
            }else if(entry.getKey()<=0.6){
                ratioNum[5]+=entry.getValue();
            }else if(entry.getKey()<=0.7){
                ratioNum[6]+=entry.getValue();
            }else if(entry.getKey()<=0.8){
                ratioNum[7]+=entry.getValue();
            }else if(entry.getKey()<=0.9){
                ratioNum[8]+=entry.getValue();
            }else{
                ratioNum[9]+=entry.getValue();
            }
        }

        for(int i=0;i<10;++i){
            System.out.println(ratioNum[i]);
        }
    }

    public static void main(String args[]){
        analysis();
    }
}