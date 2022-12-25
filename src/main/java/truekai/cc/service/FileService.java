package truekai.cc.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 作者：熊凯凯
 * 日期：2022-12-25 20:57
 */
public interface FileService {
    String upload(MultipartFile file);
}
