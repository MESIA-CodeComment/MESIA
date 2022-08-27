import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import util.ReadWriteUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Split the training set into 10 equal sets according to MESIA and construct 3 new training sets:
 * (1) L-training: 1-8
 * (2) M-training: 2-9
 * (3) H-training: 3-10
 */
public class DataSplitor {

    public static void split(List<CodeComment>data,String Lpath,String Mpath,String Hpath){
        data.sort(new Comparator<CodeComment>() {
            @Override
            public int compare(CodeComment o1, CodeComment o2) {
                return o1.MESIA.compareTo(o2.MESIA);
            }
        });

        List<CodeComment>Ltrain = new ArrayList<>();
        List<CodeComment>Mtrain = new ArrayList<>();
        List<CodeComment>Htrain = new ArrayList<>();
        System.out.println(data.size());
        int batchSize = data.size()/10;
        for(int i=0;i<data.size();++i){
            if(i<batchSize*8){
                Ltrain.add(data.get(i));
            }
            if(i>=batchSize && i<batchSize*9){
                Mtrain.add(data.get(i));
            }
            if(i>=2*batchSize && i<batchSize*10){
                Htrain.add(data.get(i));
            }
        }

        System.out.println(Ltrain.size());
        System.out.println(Mtrain.size());
        System.out.println(Htrain.size());

        ReadWriteUtil.writeFile(Lpath,JSON.toJSONString(Ltrain,true));
        ReadWriteUtil.writeFile(Mpath,JSON.toJSONString(Mtrain,true));
        ReadWriteUtil.writeFile(Hpath,JSON.toJSONString(Htrain,true));
    }

    public static void main(String args[]){
        String dedulpicateTraindata = ReadWriteUtil.readFile(Config.dedupTrainPath);
        List<CodeComment> deduplicateTrain = JSON.parseObject(dedulpicateTraindata,new TypeReference<List<CodeComment>>(){});
        split(deduplicateTrain,Config.dedupLTrainPath,Config.dedupMTrainPath,Config.dedupHTrainPath);
    }
}
