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
package org.alfresco.repo.module.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.alfresco.repo.module.ModuleDetailsImpl;

import de.schlichtherle.io.File;
import de.schlichtherle.io.FileInputStream;
import de.schlichtherle.io.FileOutputStream;

/**
 * Module details helper used by the module mangement tool
 * 
 * @author Roy Wetherall
 */
public class ModuleDetailsHelper extends ModuleDetailsImpl
{    
    /**
     * Constructor
     * 
     * @param is    input stream
     */
    public ModuleDetailsHelper(InputStream is)
    {
        super(is);
    }

    /**
     * Creates a module details helper object based on a file location.
     * 
     * @param location  file location
     * @return           module details helper object
     */
    public static ModuleDetailsHelper create(String location)
    {
        ModuleDetailsHelper result = null;
        try
        {
            File file = new File(location, ModuleManagementTool.defaultDetector);
            if (file.exists() == true)
            {
                result = new ModuleDetailsHelper(new FileInputStream(file));
            }
        }
        catch (IOException exception)
        {
            throw new ModuleManagementToolException("Unable to load module details from property file.", exception);
        }
        return result;
    }
    
    /**
     * Creates a module details helper object based on a war location and the module id
     * 
     * @param warLocation   the war location
     * @param moduleId      the module id
     * @return              the module details helper
     */
    public static ModuleDetailsHelper create(String warLocation, String moduleId)
    {
        return ModuleDetailsHelper.create(ModuleDetailsHelper.getFileLocation(warLocation, moduleId));
    }
    
    /**
     * Gets the file location
     * 
     * @param warLocation   the war location
     * @param moduleId      the module id
     * @return              the file location
     */
    private static String getFileLocation(String warLocation, String moduleId)
    {
        return warLocation + ModuleManagementTool.MODULE_DIR + "/" + moduleId + "/" + "module.properties";
    }
    
    /**
     * Saves the module detailsin to the war in the correct location based on the module id
     * 
     * @param warLocation   the war location
     * @param moduleId      the module id
     */
    public void save(String warLocation, String moduleId)
    {
        try
        {
            File file = new File(getFileLocation(warLocation, moduleId), ModuleManagementTool.defaultDetector);
            if (file.exists() == false)
            {
                file.createNewFile();               
            }  
            
            OutputStream os = new FileOutputStream(file);
            try
            {
                Date now = new Date();
                this.properties.setProperty(PROP_INSTALL_DATE, now.toString());
                this.properties.store(os, null);
            }
            finally
            {
                os.close();
            }
        }
        catch (IOException exception)
        {
            throw new ModuleManagementToolException("Unable to save module details into WAR file.", exception);
        }
    }

}
