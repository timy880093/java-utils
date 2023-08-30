package com.timy.javautils.usecase;

import java.io.IOException;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.core.io.Resource;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class UploadFile {

  // Retrofit (caller) 的 interface 接口
//  @Headers("Content-Type:application/json; charset=UTF-8")
//  @Multipart
//  @POST("/v1/voucher/file/{type}")
//  Call<ResultBean> importWebMetadata(@Part MultipartBody.Part file);

  // Spring (callee) 的 controller 接口
//  @PostMapping(value = "/v1/voucher/file")
//  public ResponseEntity<?> importFile(
//      HttpServletRequest request,
//      @RequestParam("file") MultipartFile file
//      throws Exception {
//
//  }

  // 轉換成 Retrofit 的 MultipartBody.Part
//  MultipartBody.Part toMultipart(Resource resource) throws IOException {
//    final byte[] byteArray = IOUtils.toByteArray(resource.getInputStream());
//    RequestBody fileRequestBody =
//        RequestBody.create(okhttp3.MediaType.parse("application/octet-stream"), byteArray);
//    return MultipartBody.Part.createFormData("file", resource.getFilename(), fileRequestBody);
//  }

  // 轉換成 Retrofit 的 MultipartBody.Part
//  MultipartBody.Part toMultipart(MultipartFile multipartFile) throws IOException {
//    return toMultipart(multipartFile.getResource());
//  }
}
