/*
 * Copyright (C) 2005-2010 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.wcm.client.viewresolver;

import java.io.InputStream;
import java.util.Locale;

import org.alfresco.wcm.client.Asset;
import org.alfresco.wcm.client.ContentStream;
import org.alfresco.wcm.client.exception.EditorialException;
import org.alfresco.wcm.client.view.StreamedAssetView;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.mvc.AbstractWebFrameworkViewResolver;
import org.springframework.extensions.surf.mvc.PageView;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.surf.types.Page;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * View resolver that uses the template defined for a page in the repository.
 * 
 * @author Chris Lack
 */

public class DynamicPageViewResolver extends AbstractWebFrameworkViewResolver
{
    public DynamicPageViewResolver()
    {
        // The default caching provided by the AbstractCachingViewResolver class
        // from which this
        // class is derived
        setCache(false);
    }

    /**
     * @see org.springframework.web.servlet.view.UrlBasedViewResolver#canHandle(java.lang.String,
     *      java.util.Locale)
     */
    protected boolean canHandle(String viewName, Locale locale)
    {
        // This view resolver is a catch-all which will handle all
        // remaining urls that correspond to an asset.
        RequestContext requestContext = ThreadLocalRequestContext.getRequestContext();
        Asset asset = (Asset) requestContext.getValue("asset");
        return asset != null;
    }

    /**
     * @see org.springframework.web.servlet.view.UrlBasedViewResolver#buildView(java.lang.String)
     */
    protected AbstractUrlBasedView buildView(String viewName) throws Exception
    {
        PageView view = null;
        RequestContext requestContext = ThreadLocalRequestContext.getRequestContext();
        Asset asset = (Asset) requestContext.getValue("asset");

        // Check if the template page name has been supplied as a URL parameter
        String template = requestContext.getParameter("view");
        if (template == null)
        {
            // else ask the asset what its template should be
            template = asset.getTemplate();

            if (template == null)
            {
                //The controller should have filtered this request before it reaches us
                //but we'll try to do something moderately sensible and cause the asset
                //to be streamed "raw".
                return createStreamView(asset);
            }
        }

        Page page = lookupPage(template);
        if (page == null)
            throw new EditorialException("Invalid template page \"" + template + "\" specified for "
                    + asset.getContainingSection().getPath() + asset.getName(), "template.none", asset
                    .getContainingSection().getPath()
                    + asset.getName() + asset.getName());

        view = new PageView(getServiceRegistry());
        view.setUrl(template);
        view.setPage(page);
        return view;
    }

    private AbstractUrlBasedView createStreamView(Asset asset)
    {
        InputStream stream = null;
        String mimeType = null;
        ContentStream contentStream = asset.getContentAsInputStream();
        if (contentStream != null) 
        {
            stream = contentStream.getStream();
            mimeType = contentStream.getMimeType();
        }
        if (stream == null)
        {
            throw new EditorialException("No template and no content for "+asset.getContainingSection().getPath()+asset.getName(), 
                    "template.none",asset.getContainingSection().getPath()+asset.getName()+asset.getName());
        }
        return new StreamedAssetView(asset.getId(), stream, mimeType);
    }
}
