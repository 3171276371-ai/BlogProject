package cxt.cn.utils;

/**
 * <h3>Software_development_studio</h3>
 * <p></p>
 *
 * @author : zhengyue
 * @date : 2020-06-08 05:51
 **/
public class FileNameUtil {
    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName){
        return UUIDUtils.getUUID() + FileNameUtil.getSuffix(fileOriginName);
    }
}
