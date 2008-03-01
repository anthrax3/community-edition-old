/*
 * Copyright (C) 2005-2007 Alfresco Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in Alfresco's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.alfresco.com/legal/licensing"
 */
package org.alfresco.web.scripts;

import org.alfresco.util.Content;
import org.alfresco.web.scripts.Description.FormatStyle;


/**
 * Web Script Request
 * 
 * @author davidc
 */
public interface WebScriptRequest
{
    /**
     * Gets the matching API Service for this request
     * 
     * @return  the service match
     */
    public Match getServiceMatch();
    
    /**
     * Get server portion of the request
     *
     * e.g. scheme://host:port
     *  
     * @return  server path
     */
    public String getServerPath();
    
    /**
     * Gets the Alfresco Context Path
     * 
     * @return  context url e.g. /alfresco
     */
    public String getContextPath();
    
    /**
     * Gets the Alfresco Web Script Context Path
     * 
     * @return  service url  e.g. /alfresco/service
     */
    public String getServiceContextPath();

    /**
     * Gets the Alfresco Service Path
     * 
     * @return  service url  e.g. /alfresco/service/search/keyword
     */
    public String getServicePath();

    /**
     * Gets the full request URL
     * 
     * @return  request url e.g. /alfresco/service/search/keyword?q=term
     */
    public String getURL();

    /**
     * Gets the service specific path
     * 
     * @return  request path e.g. /search/keyword
     */
    public String getPathInfo();
    
    /**
     * Gets the query String
     * 
     * @return  query string  e.g. q=alfresco&format=atom
     */
    public String getQueryString();
    
    /**
     * Gets the names of all parameters on the Url
     * 
     * @return  the names (empty, if none)
     */
    public String[] getParameterNames();

    /**
     * Gets the value of the named parameter
     *
     * @param name  parameter name
     * @return  parameter value (or null, if parameter does not exist)
     */
    public String getParameter(String name);

    /**
     * Gets the (array) value of the named parameter
     * 
     * Note: An array of one item is returned when a "single value" named parameter 
     *       is requested
     *       
     * @param  name  parameter name
     * @return  array of values (or null, if parameter does not exist)
     */
    public String[] getParameterValues(String name);
        
    /**
     * Gets the names of all headers for this request
     * 
     * @return  the names (empty, if none)
     */
    public String[] getHeaderNames();

    /**
     * Gets the value of the named header
     *
     * @param name  header name
     * @return  header value (or null, if header does not exist)
     */
    public String getHeader(String name);

    /**
     * Gets the (array) value of the named header
     * 
     * Note: An array of one item is returned when a "single value" named header 
     *       is requested
     *       
     * @param  name  header name
     * @return  array of values (or null, if header does not exist)
     */
    public String[] getHeaderValues(String name);
    
    /**
     * Gets the path extension beyond the path registered for this service
     * 
     * e.g.
     * a) service registered path = /search/engine
     * b) request path = /search/engine/external
     * 
     * => /external
     * 
     * @return  extension path
     */
    public String getExtensionPath();
    
    /**
     * Gets the mimetype of the request
     * 
     * @return  request content mimetype
     */
    public String getContentType();
    
    /**
     * Gets the request body as content
     * 
     * @return  request content (or null, if none)
     */
    public Content getContent();
    
    /**
     * Gets the request body as a parsed entity
     * 
     * @return  the parsed entity (or null, if no content, or the content type cannot be parsed)
     */
    public Object parseContent();
    
    /**
     * Determine if Guest User?
     * 
     * @return  true => guest user
     */
    public boolean isGuest();
    
    /**
     * Get Requested Format
     * 
     * @return  content type requested
     */
    public String getFormat();
 
    /**
     * Get the style the Format was specified in
     * 
     * @return  format style (excludes any)
     */
    public FormatStyle getFormatStyle();
 
    /**
     * Get User Agent
     * 
     * TODO: Expand on known agents
     * 
     * @return  MSIE / Firefox
     */
    public String getAgent();

    /**
     * Get the JSON callback method
     * 
     * @return  method (or null, if not specified)
     */
    public String getJSONCallback();

    /**
     * Force response to return SUCCESS (200) code
     * 
     * Note: This is to support clients who cannot support non-success codes
     *       e.g. Flash player
     *       
     * @return true => force return of 200, otherwise return status explicitly set
     */
    public boolean forceSuccessStatus();
    
    /**
     * Gets the initiating runtime
     * 
     * @return  runtime that constructed this request
     */
    public Runtime getRuntime();
}
