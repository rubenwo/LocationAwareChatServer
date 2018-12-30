package com.Entities;

public class Audio {
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String extension;
    /**
     *
     */
    private byte[] data;

    /**
     * @param name
     * @param extension
     * @param data
     */
    public Audio(String name, String extension, byte[] data) {
        this.name = name;
        this.extension = extension;
        this.data = data;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @return
     */
    public byte[] getData() {
        return data;
    }
}
