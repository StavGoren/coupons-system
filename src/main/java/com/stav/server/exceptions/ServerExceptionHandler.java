//package com.stav.server.exceptions;
//
//import com.stav.server.dto.ServerErrorData;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.servlet.http.HttpServletResponse;
//
//@RestControllerAdvice
//public class ServerExceptionHandler {
//
//    @ExceptionHandler
//    @ResponseBody
//    public ServerErrorData serverErrorResponse(Exception exception, HttpServletResponse httpServletResponse) {
//        if(exception instanceof ServerException){
//            ServerException serverException = (ServerException) exception;
//            int errorCode = serverException.getErrorType().getInternalErrorNumber();
//            String errorType = String.valueOf(serverException.getErrorType());
//            String errorMessage = serverException.getMessage();
//            httpServletResponse.setStatus(errorCode);
//
//            if(serverException.getErrorType().isShowStackTrace()){
//                serverException.printStackTrace();
//            }
//            return new ServerErrorData(errorCode, errorType, errorMessage);
//        }
//        httpServletResponse.setStatus(600);
//        return new ServerErrorData(600, "General error", " something went wrong");
//    }
//}
