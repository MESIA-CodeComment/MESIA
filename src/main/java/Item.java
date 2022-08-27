/**
 * Specify each item in the denoised TL-codeSum dataset
 */
public class Item {
    public String id;
    public String raw_code;
    public String raw_comment;
    public String code;
    public String comment;
    public Item(){}
    public Item(String id, String raw_code, String raw_comment, String code, String comment){
        this.id = id;
        this.raw_code = raw_code;
        this.raw_comment = raw_comment;
        this.code = code;
        this.comment = comment;
    }
}
