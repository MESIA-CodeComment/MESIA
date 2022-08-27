import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import util.ReadWriteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * deduplicate the dataset
 * (1) for the training set, remove duplicate data (only reserve one for duplicate data).
 * (2) for the valid set, remove duplicate data (only reserve one for duplicate data).
 * also, remove data in valid set that appear in training set.
 * (3) for the test set, remove duplicate data (only reserve one for duplicate data).
 * also, remove data in test set that appear in training set or valid set.
 */
public class DataDeduplicator {

    /**
     * remove duplicate data in codeComments (only reserve one for duplicate data).
     */
    public static List<CodeComment> selfDeduplicate(List<CodeComment>codeComments){
        List<CodeComment> result = new ArrayList<>();
        List<String> data = new ArrayList<>();

        for(CodeComment pair:codeComments){
            if(data.contains(pair.code.trim()+pair.comment.trim())) continue;
            result.add(pair);
            data.add(pair.code.trim()+pair.comment.trim());
        }
        return result;
    }

    /**
     * remove data in codeComments that appear in target.
     */
    public static List<CodeComment>deduplicate(List<CodeComment>codeComments,List<CodeComment>target){
        List<CodeComment> result = new ArrayList<>();
        List<String>data = new ArrayList<>();

        for(CodeComment pair:target){
            data.add(pair.code.trim()+pair.comment.trim());
        }

        for(CodeComment pair:codeComments){
            if(data.contains(pair.code.trim()+pair.comment.trim()))continue;
            result.add(pair);
        }
        return result;
    }

    public static void deduplicateTrain(){
        String traindata = ReadWriteUtil.readFile(Config.trainWithMESIADataPath);
        List<CodeComment> train = JSON.parseObject(traindata,new TypeReference<List<CodeComment>>(){});
        System.out.println("original train size:"+train.size());
        List<CodeComment> deduplicateTrain = selfDeduplicate(train);
        System.out.println("deduplicate train size:"+deduplicateTrain.size());
        ReadWriteUtil.writeFile(Config.dedupTrainPath,JSON.toJSONString(deduplicateTrain,true));
    }


    public static void deduplicateValid(){
        String traindata = ReadWriteUtil.readFile(Config.trainWithMESIADataPath);
        List<CodeComment> train = JSON.parseObject(traindata,new TypeReference<List<CodeComment>>(){});

        String validdata = ReadWriteUtil.readFile(Config.validWithMESIADataPath);
        List<CodeComment> valid = JSON.parseObject(validdata,new TypeReference<List<CodeComment>>(){});

        System.out.println("original valid size:"+valid.size());
        List<CodeComment>deduplicateValid = selfDeduplicate(valid);
        deduplicateValid = deduplicate(deduplicateValid,train);
        System.out.println("deduplicate valid size:"+deduplicateValid.size());

        ReadWriteUtil.writeFile(Config.dedupValidPath,JSON.toJSONString(deduplicateValid,true));
    }

    public static void deduplicateTest(){
        String traindata = ReadWriteUtil.readFile(Config.trainWithMESIADataPath);
        List<CodeComment> train = JSON.parseObject(traindata,new TypeReference<List<CodeComment>>(){});

        String validdata = ReadWriteUtil.readFile(Config.validWithMESIADataPath);
        List<CodeComment> valid = JSON.parseObject(validdata,new TypeReference<List<CodeComment>>(){});

        String testdata = ReadWriteUtil.readFile(Config.testWithMESIADataPath);
        List<CodeComment> test = JSON.parseObject(testdata,new TypeReference<List<CodeComment>>(){});

        System.out.println("original test size:"+test.size());
        List<CodeComment>deduplicateTest = selfDeduplicate(test);
        deduplicateTest = deduplicate(deduplicateTest,train);
        deduplicateTest = deduplicate(deduplicateTest,valid);
        System.out.println("deduplicate test size:"+deduplicateTest.size());

        ReadWriteUtil.writeFile(Config.dedupTestPath,JSON.toJSONString(deduplicateTest,true));
    }

    public static void main(String args[]){
        deduplicateTrain();
        deduplicateValid();
        deduplicateTest();
    }
}
