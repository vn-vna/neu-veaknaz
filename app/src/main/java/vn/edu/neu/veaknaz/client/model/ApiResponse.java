package vn.edu.neu.veaknaz.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse<ResultType> {

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public ResultType getResult() {
    return result;
  }

  public void setResult(ResultType result) {
    this.result = result;
  }

  @JsonProperty("success")
  private boolean success;
  @JsonProperty("error_message")
  private String errorMessage;
  @JsonProperty("result")
  private ResultType result;
}
