package com.example.chapter07_client.entity;

public class ImageInfo {
    public long id;
    public String name;
    public long size;
    public String path;

    @Override
    public String toString() {
        return "ImageInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", path='" + path + '\'' +
                '}';
    }
}
