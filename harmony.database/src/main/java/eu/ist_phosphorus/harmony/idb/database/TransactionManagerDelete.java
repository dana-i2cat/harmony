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


package eu.ist_phosphorus.harmony.idb.database;

import eu.ist_phosphorus.harmony.idb.exceptions.database.DatabaseException;

/**
 * This is a specific TransactionManager used to delete objects from the
 * database. The object to be deleted is specified in the constructor.
 *
 * @author Christian de Waal
 *
 */
public class TransactionManagerDelete extends TransactionManager {

    /**
     * Constructor.
     *
     * @param arg Object to be deleted from the database.
     * @throws DatabaseException
     */
    public TransactionManagerDelete(Object arg) throws DatabaseException {
        super(arg);
    }

    /* (non-Javadoc)
     * @see eu.ist_phosphorus.harmony.idb.database.TransactionManager#dbOperation()
     */
    @Override
    protected void dbOperation() throws Exception {
        this.session.delete(this.arg);
    }

}
