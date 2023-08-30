package com.timy.javautils;

import java.nio.charset.StandardCharsets;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import retrofit2.Response;

public class DownloadFile {

//    public ResponseEntity<?> download() {
//        final String csv = "123,456,789";
//        System.out.println("test1  " + new String(csv.getBytes(StandardCharsets.UTF_8),
//                StandardCharsets.UTF_8));
//
//        final String filename = "track_" + StringUtils.defaultIfBlank(year, "all") + ".csv";
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//                .contentType(MediaType.parseMediaType("application/csv"))
//                .body(new ByteArrayResource(csv.getBytes(StandardCharsets.UTF_8)));
//    }

//    public ResponseEntity<?> downloadFromOtherApi(){
//        final Response<ResponseBody> execute = taxesMetadataApiPort.downloadTrackCsv(year).execute();
//        if (execute.isSuccessful()) {
//            try (ResponseBody responseBody = execute.body()) {
//                if (responseBody != null) {
//                    final String contentDisposition = execute.headers().get(HttpHeaders.CONTENT_DISPOSITION);
//                    final String contentType = execute.headers().get(HttpHeaders.CONTENT_TYPE);
//                    return ResponseEntity.ok()
//                            .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                            .contentType(MediaType.parseMediaType(contentType))
//                            .body(new ByteArrayResource(responseBody.bytes()));
//                }
//            }
//        }
//        try (ResponseBody errorBody = execute.errorBody()) {
//            throw new Exception("downloadTrackCsv call api error : " + errorBody.string());
//        }
//    }

}
