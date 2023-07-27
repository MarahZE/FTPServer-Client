public class File {

    private String Id;
    private String extension;
    private String name;
    private byte[] size;

    public File(String id, String extension, String name, byte[] size) {
        this.Id = id;
        this.extension = extension;
        this.name = name;
        this.size = size;
    }
}
