/**
 * investigate the influence of different training set on the result of supplementary code comment generation.
 */
public class RQ3 {
    public static void main(String args[]){
        RQ2.analyze(Config.seq2seqLtrainResult,Config.dedupTestPath);
        RQ2.analyze(Config.seq2seqMtrainResult,Config.dedupTestPath);
        RQ2.analyze(Config.seq2seqHtrainResult,Config.dedupTestPath);
        RQ2.analyze(Config.transformerLtrainResult,Config.dedupTestPath);
        RQ2.analyze(Config.transformerMtrainResult,Config.dedupTestPath);
        RQ2.analyze(Config.transformerHtrainResult,Config.dedupTestPath);
    }
}
