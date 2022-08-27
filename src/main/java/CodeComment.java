/**
 * Specify each code comment with a MESIA score.
 */
public class CodeComment {
    public String code;
    public String comment;
    public Double MESIA;

    public CodeComment() {}
    public CodeComment(String code, String comment, double MESIA){
        this.code = code;
        this.comment = comment;
        this.MESIA = MESIA;
    }
}
