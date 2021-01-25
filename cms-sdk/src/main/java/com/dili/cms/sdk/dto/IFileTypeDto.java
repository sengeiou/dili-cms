/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * IFileTypeDto created on 2021/1/23 17:26 by Ron.Peng
 */
package com.dili.cms.sdk.dto;

import com.dili.cms.sdk.domain.IFileType;

import java.beans.Transient;

/**
* <pre>
* Description
* TODO 文件分类Dto
*
* @author Ron.Peng
* @since 1.0
*
* Change History
* Date  Modifier  DESC.
* 2021/1/23  Ron.Peng  Initial version.
* </pre>
*/
public interface IFileTypeDto extends IFileType {

    String getTreeShow();

    void setTreeShow(String treeShow);
}
