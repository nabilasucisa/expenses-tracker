package enigma.expenses_tracker.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    public static <T> ResponseEntity<?> renderJson1(HttpStatus httpStatus) {
        WebResponse<?> response = WebResponse.builder()
                .status(httpStatus.value() + " " + httpStatus.getReasonPhrase())
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<?> renderJson(HttpStatus httpStatus, T data) {
        WebResponse<?> response = WebResponse.builder()
                .status(httpStatus.value() + " " + httpStatus.getReasonPhrase())
                .body(data)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }

//    public static <T> ResponseEntity<?> renderJson(T data, String message, HttpStatus httpStatus) {
//        WebResponse<?> response = WebResponse.builder()
//                .status(httpStatus.getReasonPhrase())
//                .message(message)
//                .data(data)
//                .build();
//
//        return ResponseEntity.status(httpStatus).body(response);
//    }
//
    public static <T> ResponseEntity<?> renderJson2(String message, HttpStatus httpStatus) {
        WebResponse<?> response = WebResponse.builder()
                .status(httpStatus.getReasonPhrase())
                .message(message)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }

}
