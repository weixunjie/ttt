/**
  * Copyright 2019 bejson.com 
  */
package com.face.backend.pojo;

public class SearchResultInfo {

    private Result result;
    private String reason;
    private String error_code;
    public void setResult(Result result) {
         this.result = result;
     }
     public Result getResult() {
         return result;
     }

    public void setReason(String reason) {
         this.reason = reason;
     }
     public String getReason() {
         return reason;
     }

    public void setError_code(String error_code) {
         this.error_code = error_code;
     }
     public String getError_code() {
         return error_code;
     }

}