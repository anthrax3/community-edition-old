/*
 * Copyright (C) 2005 Alfresco, Inc.
 *
 * Licensed under the Mozilla Public License version 1.1 
 * with a permitted attribution clause. You may obtain a
 * copy of the License at
 *
 *   http://www.alfresco.org/legal/license.txt
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */
package org.alfresco.repo.admin.patch.impl;

import org.alfresco.i18n.I18NUtil;
import org.alfresco.model.ContentModel;

/**
 * The permission 'Guest' has been renamed to 'Consumer'.
 *
 * @author David Caruana
 * @author Derek Hulley
 */
public class UpdateGuestPermissionPatch extends AbstractPermissionChangePatch
{
    private static final String MSG_SUCCESS = "patch.updateGuestPermission.result";
    
    @Override
    protected String applyInternal() throws Exception
    {
        int updateCount = super.renamePermission(ContentModel.TYPE_CMOBJECT, "Guest", ContentModel.TYPE_CMOBJECT, "Consumer");
        
        // build the result message
        String msg = I18NUtil.getMessage(MSG_SUCCESS, updateCount);
        // done
        return msg;
    }
}
