public class Config {

    /**
     * Path of the TL-codesum dataset used in our study.
     * We use the de-noised version of TL-codesum (see https://github.com/BuiltOntheRock/FSE22_BuiltOntheRock)
     * Download the datasets provided in https://drive.google.com/file/d/1m4uZi0hoInYxkgrSlF23EjVasSgaXOXy/view
     * The de-noised version of TL-codesum is in the subdirectory: FSE_dataset/dataset/clean/tlcodesum
     */
    public static String dataPath = "D:\\ICSE2023\\MESIA\\tlcodesum";

    public static String trainPath = dataPath+"\\train\\tlcodesum.train";
    public static String validPath = dataPath+"\\valid\\tlcodesum.valid";
    public static String testPath = dataPath+"\\test\\tlcodesum.test";


    public static String frequencyPath = dataPath + "\\tlcodesumWordFrequency.json";
    public static String probabilityPath = dataPath + "\\tlcodesumWordProbability.json";

    public static String trainWithMESIADataPath = dataPath+"\\train.json";
    public static String validWithMESIADataPath = dataPath+"\\valid.json";
    public static String testWithMESIADataPath = dataPath+"\\test.json";

    public static String dedupTrainPath = dataPath +"\\dedupTrain.json";
    public static String dedupValidPath = dataPath +"\\dedupValid.json";
    public static String dedupTestPath = dataPath +"\\dedupTest.json";

    public static String dedupHTrainPath = dataPath +"\\dedupHTrain.json";
    public static String dedupMTrainPath = dataPath +"\\dedupMTrain.json";
    public static String dedupLTrainPath = dataPath +"\\dedupLTrain.json";

    public static String trainingResultPath = "D:\\ICSE2023\\MESIA\\trainingResult";
    public static String seq2seqAlltrainResult = trainingResultPath+"\\rnn_dedup_train_beam.json";
    public static String seq2seqLtrainResult = trainingResultPath+"\\rnn_dedup_Ltrain_beam.json";
    public static String seq2seqMtrainResult = trainingResultPath+"\\rnn_dedup_Mtrain_beam.json";
    public static String seq2seqHtrainResult = trainingResultPath+"\\rnn_dedup_Htrain_beam.json";
    public static String transformerAlltrainResult = trainingResultPath+"\\tfm_dedup_train_beam.json";
    public static String transformerLtrainResult = trainingResultPath+"\\tfm_dedup_Ltrain_beam.json";
    public static String transformerMtrainResult = trainingResultPath+"\\tfm_dedup_Mtrain_beam.json";
    public static String transformerHtrainResult = trainingResultPath+"\\tfm_dedup_Htrain_beam.json";
}
