import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import util.NLPUtil;
import util.ReadWriteUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Calculate the MESIA metric
 *
 * Notice: the MESIA metric is concerned with three factors:
 * (1) the probability of each word to appear in code comments
 *
 * (2) the length of the code comment
 *
 * (3) API method's signature
 */
public class MESIAcalculator {
    private String probabilityPath; // the path where the probability is saved
    private String probabilityResult;
    private HashMap<String,Double>pMap; //the mesia metric need a probability of each word appearing in code comments.

    public MESIAcalculator(String probabilityPath){
        this.probabilityPath = probabilityPath;
        this.probabilityResult = ReadWriteUtil.readFile(this.probabilityPath);
        this.pMap = JSON.parseObject(this.probabilityResult, new TypeReference<HashMap<String, Double>>(){});
    }

    /**
     *  MESIA = (sigma -log(p(w|code,W)))/len
     * @param comment the given comment
     * @param codeTokens tokens in API method's signature
     * @return MESIA result
     */
    public double getMESIA(String comment, List<String> codeTokens){
        if(comment.trim().equals(""))return 0;
        List<String>commentTokens = NLPUtil.processComment(comment);
        List<String>stemCode = NLPUtil.Stem(codeTokens);
        if(commentTokens.size()==0)return 0;
        double MESIA = 0;
        for(String w:commentTokens){
            if(stemCode.contains(w))continue;
            if(!pMap.containsKey(w))continue;
            MESIA-=Math.log(pMap.get(w));
        }
        return MESIA/commentTokens.size();
    }

    public static void main(String args[]){
        MESIAcalculator calculator = new MESIAcalculator(Config.probabilityPath);
        String trainComment = ReadWriteUtil.readFile(Config.trainPath);
        String testComment = ReadWriteUtil.readFile(Config.testPath);
        String devComment = ReadWriteUtil.readFile(Config.validPath);

        String trainComments[] = trainComment.split("\n");
        String testComments[] = testComment.split("\n");
        String devComments[] = devComment.split("\n");

        List<CodeComment>traindata = new ArrayList<>();
        for(String line:trainComments){
            Item item = JSON.parseObject(line,new TypeReference<Item>(){});
            List<String> signatureTokens = new ArrayList<>();
            signatureTokens.addAll(NLPUtil.getSignatureTokens(item.code));
            double MESIA = calculator.getMESIA(item.comment,signatureTokens);
            traindata.add(new CodeComment(item.code,item.comment,MESIA));
        }

        List<CodeComment>validdata = new ArrayList<>();
        for(String line:devComments){
            Item item = JSON.parseObject(line,new TypeReference<Item>(){});
            List<String> signatureTokens = new ArrayList<>();
            signatureTokens.addAll(NLPUtil.getSignatureTokens(item.code));
            double MESIA = calculator.getMESIA(item.comment,signatureTokens);
            validdata.add(new CodeComment(item.code,item.comment,MESIA));
        }

        List<CodeComment>testdata = new ArrayList<>();
        for(String line:testComments){
            Item item = JSON.parseObject(line,new TypeReference<Item>(){});
            List<String> signatureTokens = new ArrayList<>();
            signatureTokens.addAll(NLPUtil.getSignatureTokens(item.code));
            double MESIA = calculator.getMESIA(item.comment,signatureTokens);
            testdata.add(new CodeComment(item.code,item.comment,MESIA));
        }

        ReadWriteUtil.writeFile(Config.trainWithMESIADataPath,JSON.toJSONString(traindata,true));
        ReadWriteUtil.writeFile(Config.validWithMESIADataPath,JSON.toJSONString(validdata,true));
        ReadWriteUtil.writeFile(Config.testWithMESIADataPath,JSON.toJSONString(testdata,true));
    }
}
