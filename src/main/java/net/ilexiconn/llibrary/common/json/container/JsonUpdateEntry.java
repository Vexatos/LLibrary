package net.ilexiconn.llibrary.common.json.container;

public class JsonUpdateEntry {
    public final String url;
    public final String modid;
    public final String name;
    public final String file;
    public final String version;

    public JsonUpdateEntry(String url, String modid, String name, String file, String version) {
        this.url = url;
        this.modid = modid;
        this.name = name;
        this.file = file;
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public String getModid() {
        return modid;
    }

    public String getName() {
        return name;
    }

    public String getFile() {
        return file;
    }

    public String getVersion() {
        return version;
    }
}
