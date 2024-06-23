package com.scm.Helper;

public class ResourcesNotFound extends RuntimeException{



    public ResourcesNotFound() {
        super("Resources Not Found  !!!");
    }

    public ResourcesNotFound(String message) {
        super(message);
    }
}
