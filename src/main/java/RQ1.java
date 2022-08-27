import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import util.ReadWriteUtil;

import java.util.List;

/**
 * investigate the MESIA score for each code comment
 */
public class RQ1 {
    public static void calculateMESIA(){
        String train = ReadWriteUtil.readFile(Config.dedupTrainPath);
        List<CodeComment> traindata = JSON.parseObject(train,new TypeReference<List<CodeComment>>(){});
        String test = ReadWriteUtil.readFile( Config.dedupTestPath);
        List<CodeComment>testdata = JSON.parseObject(test,new TypeReference<List<CodeComment>>(){});
        String valid = ReadWriteUtil.readFile(Config.dedupValidPath);
        List<CodeComment>validdata = JSON.parseObject(valid,new TypeReference<List<CodeComment>>(){});

        int trainnum[] = new int[11];
        int testnum[] = new int[11];
        int validnum[] = new int[11];
        for(int i=0;i<11;++i){
            trainnum[i]=0;
            testnum[i]=0;
            validnum[i]=0;
        }

        for(CodeComment pair:traindata){
            if(pair.MESIA<=1){
                trainnum[0]++;
            }else if(pair.MESIA<=2){
                trainnum[1]++;
            }else if(pair.MESIA<=3){
                trainnum[2]++;
            }else if(pair.MESIA<=4){
                trainnum[3]++;
            }else if(pair.MESIA<=5){
                trainnum[4]++;
            }else if(pair.MESIA<=6){
                trainnum[5]++;
            }else if(pair.MESIA<=7){
                trainnum[6]++;
            }else if(pair.MESIA<=8){
                trainnum[7]++;
            }else if(pair.MESIA<=9){
                trainnum[8]++;
            }else if(pair.MESIA<=10){
                trainnum[9]++;
            }else {
                trainnum[10]++;
            }
        }

        for(CodeComment pair:testdata){
            if(pair.MESIA<=1){
                testnum[0]++;
            }else if(pair.MESIA<=2){
                testnum[1]++;
            }else if(pair.MESIA<=3){
                testnum[2]++;
            }else if(pair.MESIA<=4){
                testnum[3]++;
            }else if(pair.MESIA<=5){
                testnum[4]++;
            }else if(pair.MESIA<=6){
                testnum[5]++;
            }else if(pair.MESIA<=7){
                testnum[6]++;
            }else if(pair.MESIA<=8){
                testnum[7]++;
            }else if(pair.MESIA<=9){
                testnum[8]++;
            }else if(pair.MESIA<=10){
                testnum[9]++;
            }else {
                testnum[10]++;
            }
        }

        for(CodeComment pair:validdata){
            if(pair.MESIA<=1){
                validnum[0]++;
            }else if(pair.MESIA<=2){
                validnum[1]++;
            }else if(pair.MESIA<=3){
                validnum[2]++;
            }else if(pair.MESIA<=4){
                validnum[3]++;
            }else if(pair.MESIA<=5){
                validnum[4]++;
            }else if(pair.MESIA<=6){
                validnum[5]++;
            }else if(pair.MESIA<=7){
                validnum[6]++;
            }else if(pair.MESIA<=8){
                validnum[7]++;
            }else if(pair.MESIA<=9){
                validnum[8]++;
            }else if(pair.MESIA<=10){
                validnum[9]++;
            }else {
                validnum[10]++;
            }
        }

        for(int i=0;i<=10;++i){
            System.out.println(trainnum[i]);
        }
        System.out.println("----------------------------------");

        for(int i=0;i<=10;++i){
            System.out.println(testnum[i]);
        }
        System.out.println("----------------------------------");

        for(int i=0;i<=10;++i){
            System.out.println(validnum[i]);
        }
        System.out.println("----------------------------------");

        for(int i=0;i<=10;++i){
            System.out.println(trainnum[i]+testnum[i]+validnum[i]);
        }
    }

    public static void main(String args[]){
        calculateMESIA();
    }
}
