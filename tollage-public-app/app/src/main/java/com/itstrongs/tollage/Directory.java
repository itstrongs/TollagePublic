package com.itstrongs.tollage;

import java.util.List;

/**
 * Created by itstrongs on 2017/7/22.
 */

public class Directory {

    /**
     * is_file : false
     * data : [{"extension":"pdf","id":"d087ac43-a886-406e-93c4-8e0d6d2121f9","name":"目录","parent_id":"07c4ff4c-ce35-40ce-8aae-8cee93d4ad69"},{"extension":"pdf","id":"a9105336-99c0-46a5-abf1-9d985b10d6da","name":"1　税务登记规范","parent_id":"07c4ff4c-ce35-40ce-8aae-8cee93d4ad69"},{"extension":"pdf","id":"56ac5cf3-b4da-419a-bbbc-dc659781cfab","name":"2　税务认定规范","parent_id":"07c4ff4c-ce35-40ce-8aae-8cee93d4ad69"},{"extension":"pdf","id":"1866eb6a-de1c-4062-a767-65b6ee722ffc","name":"4　申报纳税规范","parent_id":"07c4ff4c-ce35-40ce-8aae-8cee93d4ad69"},{"extension":"pdf","id":"8cc1832b-043d-40e4-b617-330906437d77","name":"6　证明办理规范","parent_id":"07c4ff4c-ce35-40ce-8aae-8cee93d4ad69"},{"extension":"pdf","id":"3b540396-3844-46fe-9870-0ccdc628de6e","name":"7 宣传咨询规范","parent_id":"07c4ff4c-ce35-40ce-8aae-8cee93d4ad69"},{"extension":"pdf","id":"55dbee05-b174-45de-a707-a2ecf87c9a8e","name":"8 权益维护规范","parent_id":"07c4ff4c-ce35-40ce-8aae-8cee93d4ad69"},{"extension":"pdf","id":"0e049d0c-022f-4a33-9d80-b8cbc0d73c68","name":"9 办税服务规范","parent_id":"07c4ff4c-ce35-40ce-8aae-8cee93d4ad69"},{"extension":"pdf","id":"1660adfa-9790-427a-8f45-1db4a86bbc57","name":"附件1、2","parent_id":"07c4ff4c-ce35-40ce-8aae-8cee93d4ad69"},{"extension":"pdf","id":"02b182c8-2ba6-4bb6-93a1-c51001d5fb11","name":"附件3","parent_id":"07c4ff4c-ce35-40ce-8aae-8cee93d4ad69"}]
     */

    private boolean is_file;
    private String file_url;
    private String file_name;
    private String extension;
    private List<DataBean> data;

    public boolean isIs_file() {
        return is_file;
    }

    public void setIs_file(boolean is_file) {
        this.is_file = is_file;
    }

    public List<DataBean> getData() {
        return data;
    }

    public boolean is_file() {
        return is_file;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * extension : pdf
         * id : d087ac43-a886-406e-93c4-8e0d6d2121f9
         * name : 目录
         * parent_id : 07c4ff4c-ce35-40ce-8aae-8cee93d4ad69
         */

        private String extension;
        private String id;
        private String name;
        private String parent_id;

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
}
