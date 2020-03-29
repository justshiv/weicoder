package com.weicoder.xml.input;

import java.io.File;
import java.io.InputStream;

import com.weicoder.xml.Document;

/**
 * XML 文件或流构建Document接口，用于读取XML数据
 * @author WD  
 */
public interface XMLRead {
	/**
	 * 使用输入流构建 Document
	 * @param in 输入流
	 * @return Document
	 */
	Document build(InputStream in);

	/**
	 * 使用输入流构建 Document
	 * @param file 文件
	 * @return Document
	 */
	Document build(File file);

	/**
	 * 使用输入流构建 Document
	 * @param systemId 文件名或XML字符串
	 * @return Document
	 */
	Document build(String systemId);
}
