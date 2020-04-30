package org.apdoer.encrypt.server.controller;

import org.apdoer.encrypt.server.utils.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author apdoer
 */
@RestController
@RequestMapping("/")
public class EncryptController {

    private Logger logger = LoggerFactory.getLogger(EncryptController.class);

    @Autowired
    private EncryptUtil encryptUtil;

    /**
     * 加密
     * @param sStr 需要加密的字符串
     * @return String
     */
    @RequestMapping("/encrypt")
    public String encrypt(@RequestParam("sStr") String sStr) {
        try {
            return encryptUtil.encrypt(sStr);
        } catch (Exception e) {
            logger.error("加密错误："+e.getMessage());
            return "";
        }
    }

    /**
     * 解密
     * @param sStr 需要解密的字符串
     * @return String
     */
    @RequestMapping("/decrypt")
    public String decrypt(@RequestParam("sStr") String sStr) {
        try {
            return encryptUtil.decrypt(sStr);
        } catch (Exception e) {
            logger.error("解密错误："+e.getMessage());
            return "";
        }
    }
}
