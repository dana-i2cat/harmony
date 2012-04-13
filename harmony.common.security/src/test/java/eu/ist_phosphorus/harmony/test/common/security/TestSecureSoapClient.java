/**
*  This code is part of the Harmony System implemented in Work Package 1 
*  of the Phosphorus project. This work is supported by the European 
*  Comission under the Sixth Framework Programme with contract number 
*  IST-034115.
*
*  Copyright (C) 2006-2009 Phosphorus WP1 partners. Phosphorus Consortium.
*  http://ist-phosphorus.eu/
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package eu.ist_phosphorus.harmony.test.common.security;

import java.io.IOException;

import org.apache.muse.ws.addressing.soap.SoapFault;
import org.junit.Test;
import org.w3c.dom.Document;

import eu.ist_phosphorus.harmony.common.security.authn.SignatureFactory;
import eu.ist_phosphorus.harmony.common.security.client.SecureSoapClient;
import eu.ist_phosphorus.harmony.common.utils.Config;
import eu.ist_phosphorus.harmony.common.utils.FileHelper;

public class TestSecureSoapClient {
	SecureSoapClient soapClient;
	
	public TestSecureSoapClient () {
		soapClient = new SecureSoapClient();   
	}
	
    @Test
    public final void testIsEntityRequest() throws Exception {
        final String createRequest =
            FileHelper.readFile("resources/data/CreateReservation2.xml");
        Document request = SignatureFactory.String2DOM(createRequest);
    	System.out.println("REQUEST: "+soapClient.isEntityRequest(request));
    	
    }
    
    @Test
    public final void testIsEntityResponse() throws Exception {
        String createResponse = FileHelper.readFile("resources/data/CreateReservationResponse2.xml");    
        Document response =   SignatureFactory.String2DOM(createResponse);
    	System.out.println("RESPONSE: "+soapClient.isEntityResponse(response));
    	

    	
    }

}